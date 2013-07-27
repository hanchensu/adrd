package sessionlog.zebra.add;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.zebra.io.TableInserter;
import org.apache.hadoop.zebra.parser.TableSchemaParser;
import org.apache.hadoop.zebra.schema.Schema;
import org.apache.hadoop.zebra.tfile.TFile;
import org.apache.hadoop.zebra.tfile.Utils;
import org.apache.hadoop.zebra.tfile.Utils.Version;
import org.apache.hadoop.zebra.types.CGSchema;
import org.apache.hadoop.zebra.types.Partition;
import org.apache.hadoop.zebra.types.SortInfo;
import org.apache.hadoop.zebra.types.TypesUtils;
import org.apache.hadoop.zebra.types.ZebraConf;
import org.apache.pig.data.Tuple;

public class AddBasicTable {
	
	static Log LOG = LogFactory.getLog(AddBasicTable.class);
	private final static String BT_SCHEMA_FILE = ".btschema";
	private final static String BT_SCHEMA_BAK_FILE = ".btschema.bak";
	private final static Version SCHEMA_VERSION = new Version((short) 1, (short) 1);
	private final static String DELETED_CG_PREFIX = ".deleted-";
	public final static String DELETED_CG_SEPARATOR_PER_TABLE = ",";
	
	public static class Writer implements Closeable {
		
		private Path actualOutputPath;
		private Configuration writerConf;
		private SchemaFile schemaFile;
		private boolean closed = true;
		ColumnGroup.Writer[] colGroups;
		Partition partition;
		boolean sorted;
		Tuple[] cgTuples;
		private boolean finished;
		private long record = 0;
		
		public Writer(Path path, String btSchemaString, String btStorageString, Configuration conf) 
		throws IOException {
			try {
				actualOutputPath = path;
				writerConf = conf;
				schemaFile = new SchemaFile(path, null, conf);
				record = schemaFile.recordCount;
				int base = schemaFile.getNumOfPhysicalSchemas();
				schemaFile.addCG(btSchemaString, btStorageString);
				partition = schemaFile.getPartition();
				int numCGs = schemaFile.getNumOfPhysicalSchemas();
				colGroups = new ColumnGroup.Writer[numCGs - base];
				cgTuples = new Tuple[numCGs];
				sorted = schemaFile.isSorted();
				for (int nx = base; nx < numCGs; nx++) {
					colGroups[nx - base] = new ColumnGroup.Writer(new Path(path, schemaFile.getName(nx)),
					schemaFile.getPhysicalSchema(nx),
					sorted, schemaFile.comparator,
					schemaFile.getName(nx),
					schemaFile.getSerializer(nx),
					schemaFile.getCompressor(nx),
					schemaFile.getOwner(nx), schemaFile.getGroup(nx),
					schemaFile.getPerm(nx), false, conf);
					cgTuples[nx - base] = TypesUtils.createTuple(colGroups[nx - base].getSchema());
				}
				partition.setSource(cgTuples);
				closed = false;
			} catch (Exception e) {
				throw new IOException("ColumnGroup.Writer constructor failed : " + e.getMessage());
			} finally {
				if (!closed) return;
				if (colGroups != null) {
					for (int i = 0; i < colGroups.length; ++i) {
						if (colGroups[i] != null) {
							try {
								colGroups[i].close();
							} catch (Exception e) {
							}
						}
					}
				}
			}
		}
		
		public void finish() throws IOException {
			if (finished) return;
			finished = true;
			try {
				for (int nx = 0; nx < colGroups.length; nx++) {
					if (colGroups[nx] != null) {
						colGroups[nx].finish();
					}
				}
			} finally {
				for (int i = 0; i < colGroups.length; ++i) {
					try {
						colGroups[i].finish();
					} catch (Exception e) {
					}
				}
			}
		}
		
		private void cleanupTempDir() throws IOException {
			FileSystem fileSys = actualOutputPath.getFileSystem(writerConf);
			Path pathToRemove = new Path(actualOutputPath, "_temporary");
			if (fileSys.exists(pathToRemove)) {
				if (!fileSys.delete(pathToRemove, true)) {
					LOG.error("Failed to delete the temporary output" + " directory: " + pathToRemove.toString());
				}
			}
		}

		public void close() throws IOException {
			cleanupTempDir();
			if (closed) return;
			closed = true;
			if (!finished) finish();
			try {
				ColumnGroup.CGIndex firstCGIndex = null, cgIndex;
				int first = -1;
				for (int nx = 0; nx < colGroups.length; nx++) {
					if (colGroups[nx] != null) {
						colGroups[nx].close();
						if (first == -1) {
							first = nx;
							firstCGIndex = colGroups[nx].index;
						} else {
							cgIndex = colGroups[nx].index;
							if (cgIndex.size() != firstCGIndex.size())
								throw new IOException("Column Group " + colGroups[nx].path.getName()
								+ " has different number of files than in column group " + colGroups[first].path.getName());
							int size = firstCGIndex.size();
							for (int i = 0; i < size; i++) {
								if (!cgIndex.get(i).name.equals(firstCGIndex.get(i).name))
									throw new IOException("File[" + i + "] in Column Group " + colGroups[nx].path.getName()
									+ " has a different name: " + cgIndex.get(i).name + " than "
									+ firstCGIndex.get(i).name + " in column group " + colGroups[first].path.getName());
								if (cgIndex.get(i).rows != firstCGIndex.get(i).rows)
									throw new IOException("File " + cgIndex.get(i).name + "Column Group "
									+ colGroups[nx].path.getName() + " has a different number of rows, "
									+ cgIndex.get(i).rows + ", than " + firstCGIndex.get(i).rows
									+ " in column group " + colGroups[first].path.getName());
							}
						}
					}
				}
				schemaFile.createSchemaFile(actualOutputPath, writerConf);
			} finally {
				for (int i = 0; i < colGroups.length; ++i) {
					try {
						colGroups[i].close();
					} catch (Exception e) {
					}
				}
			}
		}
		
		public Schema getSchema() {
			return schemaFile.getLogical();
		}

		public boolean isSorted() {
			return sorted;
		}

		public SortInfo getSortInfo() {
			return schemaFile.getSortInfo();
		}
		
		public TableInserter getInserter(String name, boolean finishWriter) throws IOException {
			return this.getInserter(name, finishWriter, true);
		}
		
		public TableInserter getInserter(String name, boolean finishWriter, boolean checkType) throws IOException {
			if (closed) {
				throw new IOException("BasicTable closed");
			}
			return new BTInserter(name, finishWriter, partition, checkType);
		}
		
		public long getRecord() {
			return record;
		}

		private class BTInserter implements TableInserter {

			private TableInserter cgInserters[];
			private boolean sClosed = true;
			private boolean finishWriter;
			private Partition partition = null;

			BTInserter(String name, boolean finishWriter, Partition partition, boolean checkType) throws IOException {
				try {
					cgInserters = new ColumnGroup.Writer.CGInserter[colGroups.length];
					for (int nx = 0; nx < colGroups.length; nx++) {
						cgInserters[nx] = colGroups[nx].getInserter(name, false, checkType);
					}
					this.finishWriter = finishWriter;
					this.partition = partition;
					sClosed = false;
				} catch (Exception e) {
					throw new IOException("BTInsert constructor failed :" + e.getMessage());
				} finally {
					if (sClosed) {
						if (cgInserters != null) {
							for (int i = 0; i < cgInserters.length; ++i) {
								if (cgInserters[i] != null) {
									try {
										cgInserters[i].close();
									} catch (Exception e) {
									}
								}
							}
						}
					}
				}
			}

			public Schema getSchema() {
				return Writer.this.getSchema();
			}

			public void insert(BytesWritable key, Tuple row) throws IOException {
				if (sClosed) {
					throw new IOException("Inserter already closed");
				}
				try {
					partition.insert(key, row);
				} catch (Exception e) {
					throw new IOException("insert failed : " + e.getMessage());
				}
				for (int nx = 0; nx < colGroups.length; nx++) {
					Tuple subTuple = cgTuples[nx];
					cgInserters[nx].insert(key, subTuple);
				}
			}

			public void close() throws IOException {
				if (sClosed) return;
				sClosed = true;
				try {
					for (TableInserter ins : cgInserters) {
						ins.close();
					}
					if (finishWriter) {
						AddBasicTable.Writer.this.finish();
					}
				} finally {
					for (TableInserter ins : cgInserters) {
						try {
							ins.close();
						} catch (Exception e) {
						}
					}
					if (finishWriter) {
						try {
							AddBasicTable.Writer.this.finish();
						} catch (Exception e) {
						}
					}
				}
			}
		}
	}
	
    static class SchemaFile {
		
		private Version version;
		String comparator;
		Schema logical;
		Schema[] physical;
		Partition partition;
		boolean sorted;
		String sortStr = null;
		SortInfo sortInfo = null;
		String storage;
		CGSchema[] cgschemas;
		boolean[] cgDeletedFlags;
		long recordCount = 0;
		
		public void addCG(String schema, String storage) throws IOException {
			if (schema == null || schema.equals("") || storage == null || storage.equals("")) {
				throw new IllegalArgumentException("schema/storage is null or empty.");
			}
			String logic[] = schema.split(",");
			for (String str : logic) {
				String name = str.substring(0, str.indexOf(":"));
				if (logical.toProjectionString().indexOf(name) > 0) {
					throw new IOException("Duplicate logical cg, check config");
				}
			}
			String newBtSchemaStr = logical.toString() + "," + schema;
			String newBtStorageStr = this.storage + ";" + storage;
			this.storage = newBtStorageStr;
			String sortStr = null;
			try {
				partition = new Partition(newBtSchemaStr, newBtStorageStr, comparator.equals("") ? null : comparator, sortStr);
			} catch (Exception e) {
				throw new IOException("Partition constructor failed :" + e.getMessage());
			}
			logical = partition.getSchema();
			cgschemas = partition.getCGSchemas();
			physical = new Schema[cgschemas.length];
			for (int nx = 0; nx < cgschemas.length; nx++) {
				physical[nx] = cgschemas[nx].getSchema();
			}
			if (cgDeletedFlags != null) {
			    boolean newCgDeletedFlags[] = new boolean[physical.length];
			    for (int i = 0; i < cgDeletedFlags.length; i++) {
			    	newCgDeletedFlags[i] = cgDeletedFlags[i];
			    }
			    cgDeletedFlags = newCgDeletedFlags;
			}
		}

		public SchemaFile(Path path, String[] deletedCGs, Configuration conf) throws IOException {
			readSchemaFile(path, deletedCGs, conf);
		}

		public SchemaFile(Configuration conf) throws IOException {
			String logicalStr = ZebraConf.getOutputSchema(conf);
			storage = ZebraConf.getOutputStorageHint(conf);
			String sortColumns = ZebraConf.getOutputSortColumns(conf) != null ? ZebraConf.getOutputSortColumns(conf) : "";
			comparator = ZebraConf.getOutputComparator(conf) != null ? ZebraConf.getOutputComparator(conf) : "";
			version = SCHEMA_VERSION;
			try {
				logical = new Schema(logicalStr);
			} catch (Exception e) {
				throw new IOException("Schema build failed :" + e.getMessage());
			}
			try {
				partition = new Partition(logicalStr, storage, comparator, sortColumns);
			} catch (Exception e) {
				throw new IOException("Partition constructor failed :" + e.getMessage());
			}
			cgschemas = partition.getCGSchemas();
			physical = new Schema[cgschemas.length];
			for (int nx = 0; nx < cgschemas.length; nx++) {
				physical[nx] = cgschemas[nx].getSchema();
			}
			this.sortInfo = partition.getSortInfo();
			this.sorted = partition.isSorted();
			this.comparator = (this.sortInfo == null ? null : this.sortInfo.getComparator());
			if (this.comparator == null) this.comparator = "";
			String[] sortColumnStr = sortColumns.split(",");
			if (sortColumnStr.length > 0) {
				sortInfo = SortInfo.parse(SortInfo.toSortString(sortColumnStr), logical, comparator);
			}
		}

		public Schema[] getPhysicalSchema() {
			return physical;
		}

		public SchemaFile(Path path, String btSchemaStr, String btStorageStr,
		String sortColumns, String btComparator, Configuration conf) throws IOException {
			storage = btStorageStr;
			try {
				partition = new Partition(btSchemaStr, btStorageStr, btComparator, sortColumns);
			} catch (Exception e) {
				throw new IOException("Partition constructor failed :" + e.getMessage());
			}
			this.sortInfo = partition.getSortInfo();
			this.sorted = partition.isSorted();
			this.comparator = (this.sortInfo == null ? null : this.sortInfo.getComparator());
			if (this.comparator == null) this.comparator = "";
			logical = partition.getSchema();
			cgschemas = partition.getCGSchemas();
			physical = new Schema[cgschemas.length];
			for (int nx = 0; nx < cgschemas.length; nx++) {
				physical[nx] = cgschemas[nx].getSchema();
			}
			cgDeletedFlags = new boolean[physical.length];
			version = SCHEMA_VERSION;
			//createSchemaFile(path, conf);
		}

		public String getComparator() {
			return comparator;
		}

		public Partition getPartition() {
			return partition;
		}

		public boolean isSorted() {
			return sorted;
		}

		public SortInfo getSortInfo() {
			return sortInfo;
		}

		public Schema getLogical() {
			return logical;
		}

		public int getNumOfPhysicalSchemas() {
			return physical.length;
		}

		public Schema getPhysicalSchema(int nx) {
			return physical[nx];
		}

		public String getName(int nx) {
			return cgschemas[nx].getName();
		}

		public String getSerializer(int nx) {
			return cgschemas[nx].getSerializer();
		}

		public String getCompressor(int nx) {
			return cgschemas[nx].getCompressor();
		}

		int getCGByName(String cgName) {
			for (int i = 0; i < physical.length; i++) {
				if (cgName.equals(getName(i))) {
					return i;
				}
			}
			return -1;
		}

		boolean isCGDeleted(int idx) {
			return cgDeletedFlags[idx];
		}

		public String getOwner(int nx) {
			return cgschemas[nx].getOwner();
		}

		public String getGroup(int nx) {
			return cgschemas[nx].getGroup();
		}

		public short getPerm(int nx) {
			return cgschemas[nx].getPerm();
		}

		public String getBTSchemaString() {
			return logical.toString();
		}

		public String getStorageString() {
			return storage;
		}
		
		public long getRecordCount() {
			return recordCount;
		}

		public void createSchemaFile(Path path, Configuration conf) throws IOException {
			FileSystem fs = path.getFileSystem(conf);
			Path schemaPath = makeSchemaFilePath(path);
			if (fs.exists(schemaPath)) {
				fs.rename(schemaPath, makeSchemaBakFilePath(path));
			}
			FSDataOutputStream outSchema = fs.create(schemaPath, true);
			version.write(outSchema);
			WritableUtils.writeString(outSchema, comparator);
			WritableUtils.writeString(outSchema, logical.toString());
			WritableUtils.writeString(outSchema, storage);
			WritableUtils.writeVInt(outSchema, physical.length);
			for (int nx = 0; nx < physical.length; nx++) {
				WritableUtils.writeString(outSchema, physical[nx].toString());
			}
			WritableUtils.writeVInt(outSchema, sorted ? 1 : 0);
			WritableUtils.writeVInt(outSchema, sortInfo == null ? 0 : sortInfo.size());
			if (sortInfo != null && sortInfo.size() > 0) {
				String[] sortedCols = sortInfo.getSortColumnNames();
				for (int i = 0; i < sortInfo.size(); i++) {
					WritableUtils.writeString(outSchema, sortedCols[i]);
				}
			}
			outSchema.close();
		}

		private void readSchemaFile(Path path, String[] deletedCGs, Configuration conf) throws IOException {
			Path pathSchema = makeSchemaFilePath(path);
			FileSystem fs = path.getFileSystem(conf);
			if (!fs.exists(pathSchema)) {
				throw new IOException("BT Schema file doesn't exist: " + pathSchema);
			}
			FSDataInputStream in = fs.open(pathSchema);
			version = new Version(in);
			if (!version.compatibleWith(SCHEMA_VERSION)) {
				new IOException("Incompatible versions, expecting: " + SCHEMA_VERSION + "; found in file: " + version);
			}
			comparator = WritableUtils.readString(in);
			String logicalStr = WritableUtils.readString(in);
			try {
				logical = new Schema(logicalStr);
			} catch (Exception e) {
				throw new IOException("Schema build failed :" + e.getMessage());
			}
			storage = WritableUtils.readString(in);
			try {
				partition = new Partition(logicalStr, storage, comparator);
			} catch (Exception e) {
				throw new IOException("Partition constructor failed :" + e.getMessage());
			}
			cgschemas = partition.getCGSchemas();
			int numCGs = WritableUtils.readVInt(in);
			physical = new Schema[numCGs];
			cgDeletedFlags = new boolean[physical.length];
			TableSchemaParser parser;
			String cgschemastr;
			try {
				for (int nx = 0; nx < numCGs; nx++) {
					cgschemastr = WritableUtils.readString(in);
					parser = new TableSchemaParser(new StringReader(cgschemastr));
					physical[nx] = parser.RecordSchema(null);
				}
			} catch (Exception e) {
				throw new IOException("parser.RecordSchema failed :" + e.getMessage());
			}
			sorted = WritableUtils.readVInt(in) == 1 ? true : false;
			if (deletedCGs == null) setCGDeletedFlags(path, conf);
			else {
				for (String deletedCG : deletedCGs) {
					for (int i = 0; i < cgschemas.length; i++) {
						if (cgschemas[i].getName().equals(deletedCG)) cgDeletedFlags[i] = true;
					}
				}
			}
			if (version.compareTo(new Version((short) 1, (short) 0)) > 0) {
				int numSortColumns = WritableUtils.readVInt(in);
				if (numSortColumns > 0) {
					String[] sortColumnStr = new String[numSortColumns];
					for (int i = 0; i < numSortColumns; i++) {
						sortColumnStr[i] = WritableUtils.readString(in);
					}
					sortStr = SortInfo.toSortString(sortColumnStr);
					sortInfo = SortInfo.parse(sortStr, logical, comparator);
				}
			}
			Path filePath = new Path(path, cgschemas[0].getName());
			FileStatus parts[] = fs.listStatus(filePath, new PathFilter() {
				public boolean accept(Path subpath) {
					return subpath.toString().indexOf("part-") > 0;
				}
			});
			FSDataInputStream fsdis = null;
			TFile.Reader metaFile = null;
			for (FileStatus status : parts) {
			    fsdis = fs.open(status.getPath());
			    metaFile = new TFile.Reader(fsdis, fs.getFileStatus(status.getPath()).getLen(), conf);
			    DataInputStream subin = metaFile.getMetaBlock("TFile.meta");
			    subin.skip(4);
			    recordCount += Utils.readVLong(subin);
			    subin.close();
			    metaFile.close();
			    fsdis.close();
			}
			in.close();
		}

		private static Path makeSchemaFilePath(Path parent) {
			return new Path(parent, BT_SCHEMA_FILE);
		}
		
		private static Path makeSchemaBakFilePath(Path parent) {
			return new Path(parent, BT_SCHEMA_BAK_FILE);
		}

		void setCGDeletedFlags(Path path, Configuration conf) throws IOException {
			Set<String> deletedCGs = new HashSet<String>();
			for (FileStatus file : path.getFileSystem(conf).listStatus(path)) {
				if (!file.isDir()) {
					String fname = file.getPath().getName();
					if (fname.startsWith(DELETED_CG_PREFIX)) {
						deletedCGs.add(fname.substring(DELETED_CG_PREFIX.length()));
					}
				}
			}
			for (int i = 0; i < physical.length; i++) {
				cgDeletedFlags[i] = deletedCGs.contains(getName(i));
			}
		}

		String getDeletedCGs() {
			StringBuilder sb = new StringBuilder();
			boolean first = true;
			for (int i = 0; i < physical.length; i++) {
				if (cgDeletedFlags[i]) {
					if (first) first = false;
					else {
						sb.append(DELETED_CG_SEPARATOR_PER_TABLE);
					}
					sb.append(getName(i));
				}
			}
			return sb.toString();
		}
	}

}
