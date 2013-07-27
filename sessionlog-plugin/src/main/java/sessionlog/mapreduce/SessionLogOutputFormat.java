package sessionlog.mapreduce;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

@InterfaceAudience.Public
@InterfaceStability.Stable
public class SessionLogOutputFormat<K, V> extends FileOutputFormat<K, V> {

	public RecordWriter<K, V> getRecordWriter(TaskAttemptContext context) {
		return new RecordWriter<K, V>() {
			public void write(K key, V value) {
			}
			public void close(TaskAttemptContext context) {
			}
		};
	}

}
