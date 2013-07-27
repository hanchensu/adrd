package sessionlog.mapreduce;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.pig.data.Tuple;

import com.sohu.adrd.data.sessionlog.util.NamedList;
import com.sohu.adrd.data.sessionlog.util.ThriftProtocol;

import sessionlog.util.DataUtil;

public class BaseMapper<VALUEIN, VALUEOUT> extends Mapper<BytesWritable, Tuple, VALUEIN, VALUEOUT> {
	protected String projection = null;
	protected long id = 0;
	protected NamedList<Object> list;
	private ThriftProtocol protocol;
	private static final Log LOG = LogFactory.getLog(BaseMapper.class);

	protected void setup(Context context) throws IOException, InterruptedException {
		Configuration conf = context.getConfiguration();
		projection = conf.get("mapreduce.lib.table.input.projection");
		list = new NamedList<Object>();
		protocol = new ThriftProtocol();
	}

	protected void map(BytesWritable key, Tuple value, Context context) throws IOException, InterruptedException {
		
	}

	protected void decode(BytesWritable key, Tuple value) throws IOException {
		id = DataUtil.trasfromId(key);
		try {
			DataUtil.extractorValue(list, protocol, value, this.projection);
		} catch (Exception e) {
			LOG.warn("decode tuple error", e);
			throw new IOException(e);
		}
	}

	protected void cleanup(Context context) throws IOException, InterruptedException {
		list.clear();
		protocol.close();
	}
	
}
