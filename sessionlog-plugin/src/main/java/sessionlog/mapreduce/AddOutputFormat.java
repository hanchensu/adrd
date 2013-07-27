package sessionlog.mapreduce;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileAlreadyExistsException;
import org.apache.hadoop.mapred.InvalidJobConfException;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;

public class AddOutputFormat<K, V> extends NullOutputFormat<K, V> {
	
	public Path getOutputPath(JobContext job) {
		String name = job.getConfiguration().get("mapred.output.dir");
		return name == null ? null : new Path(name);
	}
	
	public void checkOutputSpecs(JobContext context) {
		try {
			Path outDir = getOutputPath(context);
			if (outDir == null) {
				throw new InvalidJobConfException("Output directory not set.");
			}
			if (!outDir.getFileSystem(context.getConfiguration()).exists(outDir)) {
				throw new FileAlreadyExistsException("Output directory "
				+ outDir + " not exists");
			}
		} catch (Exception e) {
			throw new RuntimeException("SessionLogAddOutputFormat need " +
			"table data path configed and exists ");
		}
	}
	
}