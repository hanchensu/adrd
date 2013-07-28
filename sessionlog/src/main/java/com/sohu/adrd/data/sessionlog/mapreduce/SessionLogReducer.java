package com.sohu.adrd.data.sessionlog.mapreduce;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.hadoop.zebra.io.BasicTable;
import org.apache.hadoop.zebra.io.TableInserter;
import org.apache.hadoop.zebra.types.TypesUtils;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;
import org.apache.pig.parser.QueryParser.null_check_cond_return;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.mortbay.io.ByteArrayBuffer.CaseInsensitive;

import com.sohu.adrd.data.common.Util;
import com.sohu.adrd.data.sessionlog.config.ConfigLoader;
import com.sohu.adrd.data.sessionlog.config.OpConfig;
import com.sohu.adrd.data.sessionlog.config.SessionLogConfig;
import com.sohu.adrd.data.sessionlog.config.SortConfig;
import com.sohu.adrd.data.sessionlog.config.ZebraConfig;
import com.sohu.adrd.data.sessionlog.util.IdCreater;
import com.sohu.adrd.data.sessionlog.util.PriorityQueue;
import com.sohu.adrd.data.sessionlog.util.Processor;
import com.sohu.adrd.data.sessionlog.util.ProcessorEntry;
import com.sohu.adrd.data.sessionlog.thrift.operation.CountinfoOperation;
import com.sohu.adrd.data.sessionlog.thrift.operation.OperationType;
import com.sohu.adrd.data.sessionlog.thrift.operation.PVOperation;
import com.sohu.adrd.data.sessionlog.thrift.operation.SearchOperation;


public class SessionLogReducer extends Reducer<Text, BytesWritable, Text, Text> {
	
	private SessionLogConfig config = null;
	private Path ouputPath = null;
	private int part = 0;
	private int cgNumber = 0;
	private String cgName[] = null;
	private BasicTable.Writer writer = null;
	private HashMap<String, PriorityQueue> queues = null;
	private HashMap<String, Processor> processors = null;
	private Inserter inserter = null;
	private IdCreater idCreater = null;
	private boolean index = false;
	
	public static final BytesWritable emptyValue = new BytesWritable();
	public static String DEFAULT_INSERTER_NAME = "part";
	private static final Log LOG = LogFactory.getLog(SessionLogReducer.class);
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
	
	@SuppressWarnings("unchecked")
	protected void setup(Context context) throws IOException, InterruptedException {
		Configuration conf = context.getConfiguration();
		config = ConfigLoader.loadConfig(conf);
		if (config == null || !config.check()) throw new IOException("sessionlog.config logic error");
		String schema = config.getZebraConfig().getSchema();
		String storage = config.getZebraConfig().getStorage();
		LOG.warn("zebra table schema : " + schema);
		LOG.warn("zebra table storage : " + storage);
		part = config.getZebraConfig().getPart();
		String parts[] = schema.split(",");
		cgName = new String[parts.length];
		queues = new HashMap<String, PriorityQueue>();
		processors = new HashMap<String, Processor>();
		for (int i = 1; i < parts.length; i++) {
			cgName[i] = parts[i].substring(0, parts[i].lastIndexOf(":"));
			queues.put(cgName[i], new PriorityQueue(i, OperationType.findByOperateName(cgName[i])));
		}
		for (int i = 0; i < config.getOpConfigs().size(); i++) {
			OpConfig opConfig = config.getOpConfigs().get(i);
			try {
				Class<Processor> processClass = (Class<Processor>)Class.forName(opConfig.getProcessor());
				processors.put(opConfig.getOperation(), (Processor)ReflectionUtils.newInstance(processClass, null));
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Class for processor" + " not found, " +
				"check distributedCache class path configed");
			}
		}
		cgNumber = cgName.length;
		inserter = new Inserter(SessionLogReducer.DEFAULT_INSERTER_NAME);
	}
	
	@SuppressWarnings("unchecked")
	protected void init(String userid, Context context) throws IOException {
		FileOutputCommitter committer = (FileOutputCommitter)context.getOutputCommitter();
		ouputPath = new Path(committer.getWorkPath(), "SUB-" + getFormat().format((new Text(userid).hashCode() & Integer.MAX_VALUE) % context.getNumReduceTasks()));
		LOG.warn("zebra table write path : " + ouputPath.toUri().toString());
		String schema = config.getZebraConfig().getSchema();
		String storage = config.getZebraConfig().getStorage();
		ZebraConfig zebra = config.getZebraConfig();
		if (zebra != null) {
			SortConfig sortConfig = zebra.getSortConfig();
			if (sortConfig != null) {
				if (sortConfig.isSort() && Util.isBlank(sortConfig.getIdCreater())) {
					throw new RuntimeException("Class for idcreater" + " not configed");
				} else if (sortConfig.isSort() && Util.isNotBlank(sortConfig.getIdCreater())) {
					try {
						index = true;
						writer = new BasicTable.Writer(ouputPath, schema, storage, "user", "jclass:" 
						+ LongWritable.Comparator.class.getName(), context.getConfiguration());
						Class<IdCreater> idCreaterClass = (Class<IdCreater>)Class.forName(sortConfig.getIdCreater());
						idCreater = (IdCreater)ReflectionUtils.newInstance(idCreaterClass, null);
					} catch (ClassNotFoundException e) {
						throw new RuntimeException("Class for idcreater" + " not found, " +
						"check distributedCache class path configed");
					}
				} else {
					writer = new BasicTable.Writer(ouputPath, schema, storage, context.getConfiguration());
				}
			}
		}
	}

	protected void reduce(Text key, Iterable<BytesWritable> values, Context context) throws IOException, InterruptedException {
		//output deleted log
		String userKey = key.toString();
		if((userKey.contains(SessionLogMapper.DEL_MARK))) {
			Iterator<BytesWritable> valueIt = values.iterator();
			while (valueIt.hasNext()) {
				valueIt.next();
				context.write(key, new Text(" "));
			}
			return;
		}
		
		
		Iterator<PriorityQueue> queueIt = queues.values().iterator();
		while (queueIt.hasNext()) {
			PriorityQueue queue = queueIt.next();
			queue.clear();
		}
		
		if (writer == null) init(userKey, context);
		Iterator<BytesWritable> valueIt = values.iterator();
		
		
		while (valueIt.hasNext()) {
			BytesWritable value = new BytesWritable();
			value.set(valueIt.next());
			byte data[] = value.getBytes();
			if (data.length <= 9) continue;
			OperationType operateType = OperationType.findByOperateId(data[0]);
			if (operateType == null) continue;
			String strOp = operateType.getOperateName();
			if (queues.get(strOp) == null) continue;
			ProcessorEntry entry = new ProcessorEntry(Util.readLog(data, 1), data, 9, value.getLength() - 9);
			PriorityQueue queue = queues.get(strOp);
			if (queue.size() > 10000) {
				
				String badUserInfo;
				try {
					badUserInfo = getUserKyes(data);
				} catch (TException e) {
					badUserInfo = e.getClass().toString();
				}
				
				context.write(new Text("BadUser_"+badUserInfo), new Text(" "));
				
				return;
			}
			queue.add(entry);
			
		}

		
		Iterator<Entry<String, PriorityQueue>> entryIt = queues.entrySet().iterator();
		while (entryIt.hasNext()) {
			Entry<String, PriorityQueue> entry = entryIt.next();
			if (entry.getValue().size() == 0) continue;
			Iterator<ProcessorEntry> queueEntryIt = entry.getValue().iterator();
			Processor processor = processors.get(entry.getKey());
			if (processor != null && processor.acceptTypes().contains(entry.getValue().getOperationType())) {
				queueEntryIt = processor.process(queueEntryIt);
			}
			if (queueEntryIt == null) continue;
			while (queueEntryIt.hasNext()) {
				ProcessorEntry processorEntry = queueEntryIt.next();
				String tag = processorEntry.getTag();
				if (!ProcessorEntry.DELETE_TAG.equals(tag)) {
				    inserter.add(processorEntry);
            	}
            	
			}
			inserter.tupleSet(entry.getValue().getCgIndex(), null);
		}
		inserter.tupleSet(0, userKey);
		inserter.insert();
	}

	private String getUserKyes(byte[] data) throws TException {
		TProtocol protocol;
		TMemoryInputTransport inputTransport;
		inputTransport = new TMemoryInputTransport();
		protocol = new TBinaryProtocol(inputTransport);
		OperationType operateType = OperationType.findByOperateId(data[0]);
		
		inputTransport.reset(data, 9, data.length - 9);
		
		String yyid = null,suv = null ,ip = null,agent = null;
		switch (operateType) {
		case PV:
			PVOperation pvOp = new PVOperation();
			pvOp.read(protocol);
			yyid = pvOp.getYyid();
			suv = pvOp.getSuv();
			ip = pvOp.getIp();
			agent = pvOp.getUseragent();
			break;
		case HB_CLICK:
		case HB_DISPLAY:
		case AD_CLICK:
		case AD_DISPLAY:
		case NEWS_CLICK:
		case NEWS_DISPLAY:
		case ARRIVE:
		case REACH:
		case ERR:
			CountinfoOperation countinfoOp = new CountinfoOperation();
			countinfoOp.read(protocol);
			yyid = countinfoOp.getYyId();
			suv = countinfoOp.getSuv();
			ip = countinfoOp.getUserIp();
			agent = countinfoOp.getUserAgent();
			break;
		case SEARCH:
			SearchOperation searchOp = new SearchOperation();
			searchOp.read(protocol);
			yyid = searchOp.getYyid();
			suv = searchOp.getIp();
			ip = searchOp.getIp();
			agent = searchOp.getUseragent();
			break;
		}
		
		return "YYID: "+ yyid +"\tSUV: "+ suv +"\tIP: "+ ip +"\tUA:"+ agent;
	}

	protected void cleanup(Context context) throws IOException, InterruptedException {
		if (inserter != null) {
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
		private final DataByteArray EMPTY_DATA = new DataByteArray(emptyValue.getBytes());
		
		public Inserter(String name) {
			this.name = name;
			try {
				rowTuple = TypesUtils.createTuple(cgNumber);
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
		
		public void add(ProcessorEntry processorEntry) {
			buffer.write(processorEntry.getData(), processorEntry.getOffset(), processorEntry.getLength());
		}
		
		public void tupleSet(int cgIndex, String userKey) {
			try {
				if (cgIndex == 0) rowTuple.set(cgIndex, userKey);
				else {
					rowTuple.set(cgIndex, new DataByteArray(buffer.toByteArray()));
				}
			} catch (ExecException e) {
				throw new RuntimeException("reset tuple error", e);
			}
			bufferReset();
		}
		
		public void insert() throws IOException {
			if (inserter == null) {
				counter = 0;
				inserter = writer.getInserter(name + "-" + getFormat().format(current), false);
			}
			if (SessionLogReducer.this.index) {
				inserter.insert(idCreater.nextId((String)rowTuple.get(0)), rowTuple);
			} else {
				inserter.insert(emptyValue, rowTuple);
			}
			if (part > 0 && part == counter) {
				current++;
				inserter.close();
				inserter = null;
			}
			counter++;
			this.tupleReset();
		}
		
		public void close() throws IOException {
			if (inserter != null) {
				inserter.close();
			}
		}
		
	}

}