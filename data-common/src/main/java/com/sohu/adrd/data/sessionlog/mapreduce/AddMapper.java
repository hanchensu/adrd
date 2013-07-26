package com.sohu.adrd.data.sessionlog.mapreduce;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.pig.data.Tuple;

import sessionlog.util.DataUtil;
import sessionlog.util.NamedList;
import sessionlog.util.ThriftProtocol;
import sessionlog.util.Util;
//woria
public class AddMapper<VALUEIN, VALUEOUT> extends Mapper<BytesWritable, Tuple, VALUEIN, VALUEOUT> {
	protected int reduceNum = 1;
	protected String projection = null;
	protected long id = 0;
	protected String userid = null;
	protected NamedList<Object> list;
	protected ThriftProtocol protocol;
	private Text hashText = new Text();
	private static final Log LOG = LogFactory.getLog(AddMapper.class);
	
	protected void setup(Context context) throws IOException, InterruptedException {
		Configuration conf = context.getConfiguration();
		projection = conf.get("mapreduce.lib.table.input.projection");
		if (projection == null || projection.indexOf("user") == -1) {
			throw new IOException("excepted user cg need config in projection");
		}
		boolean flag = false;
		try {
			if (context.getPartitionerClass() == null 
			|| !context.getPartitionerClass().getName().equals(AddPartitioner.class.getName())) {
				flag = true;
			}
		} catch (ClassNotFoundException e) {
			flag = true;
		}
		if (flag) throw new IOException("sessionlog.mapreduce.AddPartitioner need configed"); 
		reduceNum = context.getNumReduceTasks();
		list = new NamedList<Object>();
		protocol = new ThriftProtocol();
	}
	
	protected void map(BytesWritable key, Tuple value, Context context) throws IOException, 
	InterruptedException {
	}
	
	protected void decode(BytesWritable key, Tuple value) throws IOException {
		id = DataUtil.trasfromId(key);
		try {
			DataUtil.extractorValue(list, protocol, value, this.projection);
			userid = (String)list.get("user");
		} catch (Exception e) {
			LOG.warn("decode tuple error", e);
			throw new IOException(e);
		}
		if (Util.isBlank(userid)) throw new IOException("userid value is null");
		hashText.set(userid);
		long part = (hashText.hashCode() & Integer.MAX_VALUE) % reduceNum;
		id = (part << 40) + id;
	}
	
	protected void cleanup(Context context) throws IOException, InterruptedException {
		list.clear();
		protocol.close();
	}

}
