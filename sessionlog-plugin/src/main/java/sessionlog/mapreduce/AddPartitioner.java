package sessionlog.mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class AddPartitioner<V> extends Partitioner<LongWritable, V> {
	
	public int getPartition(LongWritable key, V value, int numPartitions) {
		long id = key.get();
		return (int)(id >> 40);
	}

}
