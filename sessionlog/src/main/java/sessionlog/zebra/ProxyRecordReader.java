package sessionlog.zebra;

import java.io.IOException;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.zebra.types.Projection;
import org.apache.hadoop.zebra.types.TypesUtils;
import org.apache.pig.data.Tuple;
import org.apache.thrift.TException;

import sessionlog.util.DataUtil;
import sessionlog.util.ThriftProtocol;

@Deprecated
public class ProxyRecordReader implements RecordReader<Text, Text> {
	
	private RecordReader<BytesWritable, Tuple> reader = null;
	private BytesWritable realKey = null;
	private Tuple realValue = null;
	private Text key = null, value = null;
	private String project = null;
	private ThriftProtocol protocol;
	private Tuple extracter = null;
	
	public ProxyRecordReader(RecordReader<BytesWritable, Tuple> reader, String project) {
		this.reader = reader;
		this.project = project;
		realKey = new BytesWritable();
		try {
			extracter = TypesUtils.createTuple(2);
			realValue = TypesUtils.createTuple(Projection.getNumColumns(project));
		} catch (IOException e) {
			throw new RuntimeException("create real value tuple error", e);
		}
		key = new Text("");
		value = new Text("");
		protocol = new ThriftProtocol();
	}

	public void close() throws IOException {
		this.reader.close();
	}

	public Text createKey() {
		return key;
	}

	public Text createValue() {
		return value;
	}

	public long getPos() throws IOException {
		return this.reader.getPos();
	}

	public float getProgress() throws IOException {
		return this.reader.getProgress();
	}
	
	public boolean next(Text key, Text value) throws IOException {
		boolean hasNext = this.reader.next(realKey, realValue);
		if (hasNext) {
			try {
				DataUtil.extractorStr(protocol, realValue, project, extracter);
				key.set((String)extracter.get(0));
				value.set((String)extracter.get(1));
			} catch (TException e) {
				key.set("");
				value.set("");
			}
		}
		return hasNext;
	}

}
