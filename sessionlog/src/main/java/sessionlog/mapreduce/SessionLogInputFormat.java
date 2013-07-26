package sessionlog.mapreduce;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;
import org.apache.hadoop.util.ReflectionUtils;
import sessionlog.config.PathConfig;
import sessionlog.config.SessionLogConfig;
import sessionlog.util.Util;

public class SessionLogInputFormat extends FileInputFormat<LongWritable, Text> {
	
	private SessionLogConfig config = null;
	private static final Log LOG = LogFactory.getLog(SessionLogInputFormat.class);
	
	public List<InputSplit> getSplits(JobContext job) throws IOException {
		return super.getSplits(job);
	}
	
	protected boolean isSplitable(JobContext context, Path file) {
		return false;
	}

	@SuppressWarnings("unchecked")
	public RecordReader<LongWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context) {
	    try {
		    config = Util.loadConfig(context.getConfiguration());
		    if (config == null || !config.check()) throw new IOException("sessionlog.config logic error");
		    String splitPath = ((FileSplit)split).getPath().getParent().toString();
		    for (PathConfig pathConfig : config.getPathConfigs()) {
			    if (splitPath.startsWith(pathConfig.getLocation()) 
			    && Util.isNotBlank(pathConfig.getRecordReader())) {
				    @SuppressWarnings("rawtypes")
				    Class recordReaderClazz = null;
				    try {
					    recordReaderClazz = Class.forName(pathConfig.getRecordReader());
				    } catch (ClassNotFoundException e) {
					    throw new IOException("Class:" + pathConfig.getRecordReader() + " not found, " +
					    "check distributedCache clas path configed");
				    }
				    return (RecordReader<LongWritable, Text>)ReflectionUtils.newInstance(recordReaderClazz, context.getConfiguration());
			    }
		    }
		} catch (Exception e) {
			LOG.warn("load or parse config error, " + e.toString());
		}
		return new SafeLineRecordReader(new LineRecordReader());
	}
	
}
