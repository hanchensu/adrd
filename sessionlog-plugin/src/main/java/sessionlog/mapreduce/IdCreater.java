package sessionlog.mapreduce;

import org.apache.hadoop.io.BytesWritable;

public interface IdCreater {
	
	public BytesWritable nextId(String userid);

}
