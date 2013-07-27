package sessionlog.zebra.add;

import java.io.Closeable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.permission.*;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.zebra.tfile.TFile;
import org.apache.hadoop.zebra.tfile.Utils;
import org.apache.hadoop.zebra.tfile.ByteArray;
import org.apache.hadoop.zebra.tfile.RawComparable;
import org.apache.hadoop.zebra.types.CGSchema;
import org.apache.hadoop.zebra.io.TableInserter;
import org.apache.hadoop.zebra.parser.ParseException;
import org.apache.hadoop.zebra.schema.Schema;
import org.apache.hadoop.zebra.types.TypesUtils;
import org.apache.hadoop.zebra.types.TypesUtils.TupleWriter;
import org.apache.pig.data.Tuple;

class ColumnGroup {
	
	static Log LOG = LogFactory.getLog(ColumnGroup.class);
	private final static String CONF_COMPRESS = "table.output.tfile.compression";
	private final static String DEFAULT_COMPRESS = "gz";
	private final static String CONF_MIN_BLOCK_SIZE = "table.tfile.minblock.size";
	private final static int DEFAULT_MIN_BLOCK_SIZE = 1024 * 1024;
	private final static String CONF_MIN_SPLIT_SIZE = "table.input.split.minSize";
	private final static int DEFAULT_MIN_SPLIT_SIZE = 64 * 1024;
	static final double SPLIT_SLOP = 1.1; // 10% slop
	private final static String CONF_NON_DATAFILE_PREFIX = "table.cg.nondatafile.prefix";
	private final static String SPECIAL_FILE_PREFIX = ".";
	private final static String SCHEMA_FILE = ".schema";
	final static String META_FILE = ".meta";
	static final String BLOCK_NAME_INDEX = "ColumnGroup.index";

	static Path makeMetaFilePath(Path parent) {
		return new Path(parent, META_FILE);
	}

	static String getCompression(Configuration conf) {
		return conf.get(CONF_COMPRESS, DEFAULT_COMPRESS);
	}

	static int getMinBlockSize(Configuration conf) {
		return conf.getInt(CONF_MIN_BLOCK_SIZE, DEFAULT_MIN_BLOCK_SIZE);
	}

	static String getNonDataFilePrefix(Configuration conf) {
		return conf.get(CONF_NON_DATAFILE_PREFIX, SPECIAL_FILE_PREFIX);
	}

	static int getMinSplitSize(Configuration conf) {
		return conf.getInt(CONF_MIN_SPLIT_SIZE, DEFAULT_MIN_SPLIT_SIZE);
	}

	static CGIndex buildIndex(FileSystem fs, Path path, boolean dirty, Configuration conf) throws IOException {
		CGIndex ret = new CGIndex();
		CGPathFilter cgPathFilter = new CGPathFilter();
		CGPathFilter.setConf(conf);
		FileStatus[] files = fs.listStatus(path, cgPathFilter);
		Comparator<RawComparable> comparator = null;
		for (FileStatus f : files) {
			if (dirty) {
				ret.add(f.getLen(), f.getPath().getName());
			} else {
				FSDataInputStream dis = null;
				TFile.Reader tr = null;
				try {
					dis = fs.open(f.getPath());
					tr = new TFile.Reader(dis, f.getLen(), conf);
					if (comparator == null) {
						comparator = tr.getComparator();
					}
					if (tr.getEntryCount() > 0) {
						CGIndexEntry range = new CGIndexEntry(f.getPath().getName(), tr.getEntryCount(),
						tr.getFirstKey(), tr.getLastKey());
						ret.add(f.getLen(), tr.getEntryCount(), range);
					}
				} catch (IOException e) {
					e.printStackTrace(System.err);
				} finally {
					if (tr != null) {
						tr.close();
					}
					if (dis != null) {
						dis.close();
					}
				}
			}
		}
		ret.sort(comparator);
		int idx = 0;
		for (CGIndexEntry e : ret.getIndex()) {
			e.setIndex(idx++);
		}
		return ret;
	}


	public static class Writer implements Closeable {
		
		Path path;
		Path finalOutputPath;
		Configuration conf;
		FileSystem fs;
		CGSchema cgschema;
		private boolean finished, closed;
		CGIndex index;

		public Writer(Path path, String schema, boolean sorted, String name,
		String serializer, String compressor, String owner,
		String group, short perm, boolean overwrite, Configuration conf) throws IOException, ParseException {
			this(path, new Schema(schema), sorted, null, name, serializer,
			compressor, owner, group, perm, overwrite, conf);
		}

		public Writer(Path path, Schema schema, boolean sorted, String name,
		String serializer, String compressor, String owner,
		String group, short perm, boolean overwrite, Configuration conf) throws IOException, ParseException {
			this(path, schema, sorted, null, name, serializer, compressor,
			owner, group, perm, overwrite, conf);
		}

		public Writer(Path path, String schema, boolean sorted,
		String comparator, String name, String serializer,
		String compressor, String owner, String group, short perm,
		boolean overwrite, Configuration conf) throws IOException, ParseException {
			this(path, new Schema(schema), sorted, comparator, name,
			serializer, compressor, owner, group, perm, overwrite, conf);
		}

		public Writer(Path path, Schema schema, boolean sorted,
		String comparator, String name, String serializer,
		String compressor, String owner, String group, short perm,
		boolean overwrite, Configuration conf) throws IOException, ParseException {
			this.path = path;
			this.conf = conf;
			this.finalOutputPath = path;
			fs = path.getFileSystem(conf);
			checkMetaFile(path);
			if (overwrite) {
				fs.delete(path, true);
			}
			checkPath(path, true);
			Path parent = path.getParent();
			Path tmpPath1 = new Path(parent, "_temporary");
			Path tmpPath2 = new Path(tmpPath1, name);
			checkPath(tmpPath2, true);
			cgschema = new CGSchema(schema, sorted, comparator, name, serializer, compressor, owner, group, perm);
			CGSchema sfNew = CGSchema.load(fs, path);
			if (sfNew != null) {
				if (!sfNew.equals(cgschema)) {
					throw new IOException("Schema passed in is different from the one on disk");
				}
			} else {
				cgschema.create(fs, path);
			}
		}

		public Writer(Path finalPath, Path workPath, Configuration conf) throws IOException, ParseException {
			this.path = workPath;
			finalOutputPath = finalPath;
			this.conf = conf;
			fs = path.getFileSystem(conf);
			checkPath(finalOutputPath, false);
			checkPath(path, true);
			checkMetaFile(finalOutputPath);
			cgschema = CGSchema.load(fs, finalOutputPath);
		}

		public Writer(Path finalPath, Path workPath, CGSchema cgschema, Configuration conf) throws IOException, ParseException {
			this.path = workPath;
			finalOutputPath = finalPath;
			this.conf = conf;
			fs = path.getFileSystem(conf);
			this.cgschema = cgschema;
		}

		public Writer(Path path, Configuration conf) throws IOException, ParseException {
			this.path = path;
			finalOutputPath = path;
			this.conf = conf;
			fs = path.getFileSystem(conf);
			checkPath(path, false);
			checkMetaFile(path);
			// read the schema file
			cgschema = CGSchema.load(fs, path);
		}

		public void finish() {
			if (!finished) {
				finished = true;
			}
		}

		public void close() throws IOException {
			if (!finished) {
				finish();
			}
			if (!closed) {
				closed = true;
				createIndex();
			}
		}

		public Schema getSchema() {
			return cgschema.getSchema();
		}

		public TableInserter getInserter(String name, boolean finishWriter) throws IOException {
			return getInserter(name, finishWriter, true);
		}

		public TableInserter getInserter(String name, boolean finishWriter, boolean checkType) throws IOException {
			if (finished) {
				throw new IOException("ColumnGroup has been closed for insertion.");
			}
			return new CGInserter(name, finishWriter, checkType);
		}

		private void createIndex() throws IOException {
			MetaFile.Writer metaFile = MetaFile.createWriter(makeMetaFilePath(finalOutputPath), conf);
			index = buildIndex(fs, finalOutputPath, false, conf);
			DataOutputStream dos = metaFile.createMetaBlock(BLOCK_NAME_INDEX);
			try {
				index.write(dos);
			} finally {
				dos.close();
			}
			metaFile.close();
		}

		private void checkPath(Path p, boolean createNew) throws IOException {
			if (!fs.exists(p)) {
				if (createNew) {
					fs.mkdirs(p);
				} else {
					throw new IOException("Path doesn't exists for appending: " + p);
				}
			}
			if (!fs.getFileStatus(p).isDir()) {
				throw new IOException("Path exists but not a directory: " + p);
			}
		}

		private void checkMetaFile(Path p) throws IOException {
			Path pathMeta = new Path(p, META_FILE);
			if (fs.exists(pathMeta)) {
				throw new IOException("Index meta file already exists: " + pathMeta);
			}
		}

		class CGInserter implements TableInserter {
			
			String name;
			String tmpName;
			boolean finishWriter;
			FSDataOutputStream out;
			TFile.Writer tfileWriter;
			TupleWriter tupleWriter;
			boolean closed = true;
			boolean checkType = true;

			private void createTempFile() throws IOException {
				int maxTrial = 10;
				String prefix = ".tmp." + name + ".";
				Random random = new Random();
				while (true) {
					random.setSeed(System.nanoTime() * Thread.currentThread().getId() * Runtime.getRuntime().freeMemory());
					try {
						tmpName = prefix + String.format("%08X", random.nextInt());
						Path tmpPath = new Path(path, tmpName);
						fs.mkdirs(path);
						if (cgschema.getOwner() != null || cgschema.getGroup() != null) {
							fs.setOwner(path, cgschema.getOwner(), cgschema.getGroup());
						}
						FsPermission permission = null;
						if (cgschema.getPerm() != -1) {
							permission = new FsPermission((short) cgschema.getPerm());
							fs.setPermission(path, permission);
						}
						out = fs.create(tmpPath, false);
						if (cgschema.getOwner() != null || cgschema.getGroup() != null) {
							fs.setOwner(tmpPath, cgschema.getOwner(), cgschema.getGroup());
						}
						if (cgschema.getPerm() != -1) {
							fs.setPermission(tmpPath, permission);
						}
						return;
					} catch (IOException e) {
						--maxTrial;
						if (maxTrial == 0) {
							throw e;
						}
						Thread.yield();
					}
				}
			}

			CGInserter(String name, boolean finishWriter, boolean checkType) throws IOException {
				this.name = name;
				this.finishWriter = finishWriter;
				this.tupleWriter = new TupleWriter(getSchema());
				this.checkType = checkType;
				try {
					createTempFile();
					tfileWriter = new TFile.Writer(out, getMinBlockSize(conf),
					cgschema.getCompressor(), cgschema.getComparator(), conf);
					closed = false;
				} finally {
					if (closed) {
						if (tfileWriter != null) {
							try {
								tfileWriter.close();
							} catch (Exception e) {
							}
						}
						if (out != null) {
							try {
								out.close();
							} catch (Exception e) {
							}
						}
						if (tmpName != null) {
							try {
								fs.delete(new Path(path, tmpName), false);
							} catch (Exception e) {
							}
						}
					}
				}
			}

			public Schema getSchema() {
				return ColumnGroup.Writer.this.getSchema();
			}

			public void insert(BytesWritable key, Tuple row) throws IOException {
				if (checkType == true) {
					TypesUtils.checkCompatible(row, getSchema());
					checkType = false;
				}
				DataOutputStream outKey = tfileWriter.prepareAppendKey(key.getLength());
				try {
					outKey.write(key.getBytes(), 0, key.getLength());
				} finally {
					outKey.close();
				}
				DataOutputStream outValue = tfileWriter.prepareAppendValue(-1);
				try {
					tupleWriter.put(outValue, row);
				} finally {
					outValue.close();
				}
			}

			public void close() throws IOException {
				if (closed) {
					return;
				}
				closed = true;
				try {
					tfileWriter.close();
					tfileWriter = null;
					out.close();
					out = null;
					fs.rename(new Path(path, tmpName), new Path(finalOutputPath, name));
					tmpName = null;
					if (finishWriter) {
						finish();
					}
				} finally {
					if (tfileWriter != null) {
						try {
							tfileWriter.close();
						} catch (Exception e) {
						}
					}
					if (out != null) {
						try {
							out.close();
						} catch (Exception e) {
						}
					}
					if (tmpName != null) {
						try {
							fs.delete(new Path(path, tmpName), false);
						} catch (Exception e) {
						}
					}
					if (finishWriter) {
						try {
							finish();
						} catch (Exception e) {
						}
					}
				}
			}
		}

	}

	static class CGIndexEntry implements RawComparable, Writable {
		int index;
		String name;
		long rows, bytes;
		RawComparable firstKey;
		RawComparable lastKey;

		public CGIndexEntry() {
		}

		public CGIndexEntry(String name, long rows, RawComparable firstKey, RawComparable lastKey) {
			this.name = name;
			this.rows = rows;
			this.firstKey = firstKey;
			this.lastKey = lastKey;
		}

		public int getIndex() {
			return index;
		}

		public String getName() {
			return name;
		}

		public long getRows() {
			return rows;
		}

		public RawComparable getFirstKey() {
			return firstKey;
		}

		public RawComparable getLastKey() {
			return lastKey;
		}

		void setIndex(int idx) {
			this.index = idx;
		}

		public byte[] buffer() {
			return (lastKey != null) ? lastKey.buffer() : null;
		}

		public int offset() {
			return (lastKey != null) ? lastKey.offset() : 0;
		}

		public int size() {
			return (lastKey != null) ? lastKey.size() : 0;
		}
		
		public void readFields(DataInput in) throws IOException {
			name = Utils.readString(in);
			rows = Utils.readVLong(in);
			if (rows == 0) {
				firstKey = null;
				lastKey = null;
			} else {
				int firstKeyLen = Utils.readVInt(in);
				byte[] firstKeyBuffer = new byte[firstKeyLen];
				in.readFully(firstKeyBuffer);
				int lastKeyLen = Utils.readVInt(in);
				byte[] lastKeyBuffer = new byte[lastKeyLen];
				in.readFully(lastKeyBuffer);
				firstKey = new ByteArray(firstKeyBuffer);
				lastKey = new ByteArray(lastKeyBuffer);
			}
		}

		public void write(DataOutput out) throws IOException {
			Utils.writeString(out, name);
			Utils.writeVLong(out, rows);
			if (rows > 0) {
				if ((firstKey == null) && (lastKey == null)) {
					throw new IOException("In-memory only entry");
				}
				Utils.writeVInt(out, firstKey.size());
				out.write(firstKey.buffer(), firstKey.offset(), firstKey.size());
				Utils.writeVInt(out, lastKey.size());
				out.write(lastKey.buffer(), lastKey.offset(), lastKey.size());
			}
		}
	}

	static class CGIndex implements Writable {
		boolean dirty = false;
		boolean sorted = true;
		BasicTableStatus status;
		ArrayList<CGIndexEntry> index;

		CGIndex() {
			status = new BasicTableStatus();
			index = new ArrayList<CGIndexEntry>();
		}

		int getFileIndex(Path path) throws IOException {
			String filename = path.getName();
			if (index.isEmpty()) return -1;
			for (CGIndexEntry cgie : index) {
				if (cgie.getName().equals(filename)) {
					return cgie.getIndex();
				}
			}
			return -1;
		}

		int size() {
			return index.size();
		}

		CGIndexEntry get(int i) {
			return index.get(i);
		}

		List<CGIndexEntry> getIndex() {
			return index;
		}

		Path getPath(int i, Path parent) {
			return new Path(parent, index.get(i).getName());
		}

		void sort(final Comparator<RawComparable> comparator) throws IOException {
			if (dirty && comparator != null) {
				throw new IOException("Cannot sort dirty index");
			}
			if (comparator != null) {
				Collections.sort(index, new Comparator<CGIndexEntry>() {
					public int compare(CGIndexEntry o1, CGIndexEntry o2) {
						if ((o1.getRows() == 0) && (o2.getRows() == 0)) {
							return o1.getName().compareTo(o2.getName());
						}
						if (o1.getRows() == 0) return -1;
						if (o2.getRows() == 0) return 1;
						int cmprv = comparator.compare(o1.lastKey, o2.lastKey);
						if (cmprv == 0) {
							cmprv = comparator.compare(o1.firstKey, o2.firstKey);
							if (cmprv == 0) {
								cmprv = o1.getName().compareTo(o2.getName());
							}
						}
						return cmprv;
					}
				});
				for (int i = 0; i < index.size() - 1; ++i) {
					RawComparable prevLastKey = index.get(i).lastKey;
					RawComparable nextFirstKey = index.get(i + 1).firstKey;
					if (nextFirstKey == null) {
						continue;
					}
					if (comparator.compare(prevLastKey, nextFirstKey) > 0) {
						throw new IOException("Overlapping key ranges");
					}
				}
			} else {
				Collections.sort(index, new Comparator<CGIndexEntry>() {
					public int compare(CGIndexEntry o1, CGIndexEntry o2) {
						return o1.name.compareTo(o2.name);
					}
				});
			}
			if ((!dirty) && (index.size() > 0)) {
				RawComparable keyFirst = index.get(0).getFirstKey();
				status.beginKey = new BytesWritable();
				status.beginKey.set(keyFirst.buffer(), keyFirst.offset(), keyFirst.size());
				RawComparable keyLast = index.get(index.size() - 1).getLastKey();
				status.endKey = new BytesWritable();
				status.endKey.set(keyLast.buffer(), keyLast.offset(), keyLast.size());
			}
			sorted = true;
		}

		void add(long bytes, long rows, CGIndexEntry range) {
			status.size += bytes;
			status.rows += rows;
			index.add(range);
			sorted = false;
			range.bytes = bytes;
		}

		void add(long bytes, String name) {
			dirty = true;
			status.rows = -1; // reset rows to -1.
			status.size += bytes;
			CGIndexEntry next = new CGIndexEntry();
			next.name = name;
			index.add(next);
			sorted = false;
			next.bytes = bytes;
		}

		int lowerBound(RawComparable key, final Comparator<RawComparable> comparator) throws IOException {
			if ((key == null) || (comparator == null)) {
				throw new IllegalArgumentException("CGIndex.lowerBound");
			}
			if (!sorted) {
				sort(comparator);
			}
			return Utils.lowerBound(index, key,
				new Comparator<RawComparable>() {
					public int compare(RawComparable o1, RawComparable o2) {
						if ((o1.buffer() == null) && (o2.buffer() == null)) {
							return 0;
						}
						if (o1.buffer() == null) return -1;
						if (o2.buffer() == null) return 1;
						return comparator.compare(o1, o2);
					}
				}
			);
		}

		public void readFields(DataInput in) throws IOException {
			int n = Utils.readVInt(in);
			index.clear();
			index.ensureCapacity(n);
			for (int i = 0; i < n; ++i) {
				CGIndexEntry range = new CGIndexEntry();
				range.readFields(in);
				range.setIndex(i);
				index.add(range);
			}
			status.readFields(in);
			dirty = false;
			sorted = true;
		}

		public void write(DataOutput out) throws IOException {
			if (dirty) {
				throw new IOException("Cannot write dirty index");
			}
			if (!sorted) {
				throw new IOException("Please sort index before calling write");
			}
			Utils.writeVInt(out, index.size());
			for (int i = 0; i < index.size(); ++i) {
				index.get(i).write(out);
			}
			status.write(out);
		}
	}

	public static class CGPathFilter implements PathFilter {
		private static Configuration conf;

		public static void setConf(Configuration c) {
			conf = c;
		}

		public boolean accept(Path p) {
			return p.getName().equals(META_FILE)
			|| p.getName().equals(SCHEMA_FILE)
			|| p.getName().startsWith(".tmp.")
			|| p.getName().startsWith("_")
			|| p.getName().startsWith("ttt")
			|| p.getName().startsWith(getNonDataFilePrefix(conf)) ? false : true;
		}
	}

}
