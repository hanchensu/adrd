package com.sohu.adrd.data.sessionlog.mapreduce;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.zebra.io.TableInserter;
import org.apache.hadoop.zebra.types.TypesUtils;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;

import sessionlog.util.DataUtil;
import sessionlog.util.Util;
import sessionlog.zebra.add.AddBasicTable;

public class AddReducer<VALUEIN> extends Reducer<LongWritable, VALUEIN, Text, Text> {
	
	private Path outDir = null, ouputPath = null;
	private long currentId = 0;
	private String schema = null;
	private String storage = null;
	private int cgNumber = 0;
	protected AddBasicTable.Writer writer = null;
	protected Inserter inserter = null;
	private int part = 0;
	public static long ID_MARK = 0x000000FFFFFFFFFFL;
	public static final BytesWritable emptyValue = new BytesWritable();
	public static final String SESSIONLOG_ADD_SCHEMA = "sessionlong.add.schema";
	public static final String SESSIONLOG_ADD_STORAGE = "sessionlong.add.storage";
	public static final String SESSIONLOG_ADD_PART = "sessionlong.add.part";
	private static final Log LOG = LogFactory.getLog(AddReducer.class);
	protected static final ThreadLocal<NumberFormat> formats = new ThreadLocal<NumberFormat>();
	
	protected NumberFormat getFormat() {
		NumberFormat format = formats.get();
		if (format == null) {
			format = NumberFormat.getInstance();
			format.setGroupingUsed(false);
			format.setMinimumIntegerDigits(4);
			formats.set(format);
		}
		return format;
	}
	
	protected void setup(Context context) throws IOException, InterruptedException {
		Configuration conf = context.getConfiguration();
		schema = conf.get(SESSIONLOG_ADD_SCHEMA);
		storage = conf.get(SESSIONLOG_ADD_STORAGE);
		if (Util.isBlank(schema) || Util.isBlank(SESSIONLOG_ADD_STORAGE)) {
			throw new IOException("schema and storage need configed");
		}
		part = conf.getInt("", 1000000);
		LOG.warn("add schema is : " + schema + ", add storage is : " + storage);
		LOG.warn("add part is : " + part);
		cgNumber = schema.split(",").length;
		outDir = FileOutputFormat.getOutputPath(context);
		//ouputPath = new Path(outDir, "SUB-" + getFormat().format(context.getTaskAttemptID().getTaskID().getId()));
		LOG.warn("add part ouputPath is : " + ouputPath);
		//writer = new AddBasicTable.Writer(ouputPath, schema, storage, conf);
		inserter = new Inserter(SessionLogReducer.DEFAULT_INSERTER_NAME);
	}
	
	public void init(LongWritable key, Configuration conf) throws IOException {
		long id = key.get();
		id = id >> 40;
		ouputPath = new Path(outDir, "SUB-" + getFormat().format((int)id));
		writer = new AddBasicTable.Writer(ouputPath, schema, storage, conf);
	}
	
	protected void reduce(LongWritable key, Iterable<VALUEIN> values, Context context) throws IOException, InterruptedException {
		long id = key.get();
		id = id & (ID_MARK);
	}
	
	protected void cleanup(Context context) throws IOException, InterruptedException {
		if (inserter != null) {
			while (currentId < writer.getRecord()) {
				inserter.insertInner(true);
			}
			inserter.close();
		}
		if (writer != null) {
			writer.finish(); writer.close();
		}
	}
	
    public class Inserter {
		
		private TableInserter inserter = null;
		private String name = null;
		private Tuple rowTuple = null;
		private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		private int counter = 0, current = 0;
		private Tuple emptyTuple = null;
		private final DataByteArray EMPTY_DATA = new DataByteArray(emptyValue.getBytes());
		
		public Inserter(String name) {
			this.name = name;
			try {
				rowTuple = TypesUtils.createTuple(cgNumber);
				emptyTuple = TypesUtils.createTuple(cgNumber);
				for (int i = 0; i < cgNumber; i++) emptyTuple.set(i, EMPTY_DATA);
			} catch (IOException e) {
				throw new RuntimeException("create tuple error", e);
			}
			if (rowTuple != null) this.tupleReset();
		}
		
		private void tupleReset() {
			for (int i = 1; i < cgNumber; i++) {
				try {
					rowTuple.set(i, EMPTY_DATA);
				} catch (ExecException e) {
				    throw new RuntimeException("reset tuple error", e);
				}
			}
		}
		
		private void bufferReset() {
			buffer.reset();
		}
		
		public void add(byte data[], int offset, int length) {
			if (data == null || data.length < offset + length) {
				throw new RuntimeException("error data to add");
			}
			buffer.write(data, offset, length);
		}
		
		public void tupleSet(int cgIndex) {
			try {
				rowTuple.set(cgIndex, new DataByteArray(buffer.toByteArray()));
			} catch (ExecException e) {
				throw new RuntimeException("reset tuple error", e);
			}
			bufferReset();
		}
		
		public void insert(long id) throws IOException {
		    while (id > currentId) insertInner(true);
		    insertInner(false);
		}
		
		public void insertInner(boolean empty) throws IOException {
			if (inserter == null) {
				counter = 0;
				inserter = writer.getInserter(name + "-" + getFormat().format(current), false);
			}
			DataUtil.trastoId(currentId, key);
			if (empty) inserter.insert(key, emptyTuple);
			else inserter.insert(key, rowTuple);
			if (part > 0 && part == counter) {
				current++;
				inserter.close();
				inserter = null;
			}
			counter++;
			if (!empty) this.tupleReset();
			currentId++;
		}
		
		public void close() throws IOException {
			if (inserter != null) {
				inserter.close();
			}
		}
		
		byte longBuffer[] = new byte[8];
		BytesWritable key = new BytesWritable(longBuffer);
		
	}

}
