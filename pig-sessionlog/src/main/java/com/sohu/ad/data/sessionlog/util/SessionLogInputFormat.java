package com.sohu.ad.data.sessionlog.util;

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
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

public class SessionLogInputFormat extends FileInputFormat<LongWritable, Text> {
	
	private static final Log LOG = LogFactory.getLog(SessionLogInputFormat.class);
	
	public List<InputSplit> getSplits(JobContext job) throws IOException {
		return super.getSplits(job);
	}
	
	protected boolean isSplitable(JobContext context, Path file) {
		return false;
	}

	@SuppressWarnings("unchecked")
	public RecordReader<LongWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context) {
	    
		return new SafeLineRecordReader(new LineRecordReader());
	}
	
}
