package safe;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.LineRecordReader;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class SkipLineRecordReader implements RecordReader<LongWritable, Text> {
	
	private boolean initError = false;
	private LineRecordReader realReader = null;
	private static final Log LOG = LogFactory.getLog(SkipLineRecordReader.class);
	
	

	public SkipLineRecordReader(JobConf job, FileSplit split) throws IOException {
	}


	@Override
	public boolean next(LongWritable key, Text value) throws IOException {
		
		return false;
	}



	@Override
	public float getProgress() {
		return 1.0f;
	}


	@Override
	public LongWritable createKey() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Text createValue() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public long getPos() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

//	public boolean nextKeyValue() throws IOException, InterruptedException {
//		if (initError) return false;
//		return this.realReader.nextKeyValue();
//	}
//
//	public LongWritable getCurrentKey() throws IOException, InterruptedException {
//		return this.realReader.getCurrentKey();
//	}
//
//	public Text getCurrentValue() throws IOException, InterruptedException {
//		return this.realReader.getCurrentValue();
//	}
//
//	public float getProgress() throws IOException, InterruptedException {
//		if (initError) return 1.0f;
//		return this.realReader.getProgress();
//	}
//
//	public void close() throws IOException {
//		if (!initError) this.realReader.close();
//	}

}
