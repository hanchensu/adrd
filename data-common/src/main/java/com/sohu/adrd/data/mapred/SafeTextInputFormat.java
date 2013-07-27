package com.sohu.adrd.data.mapred;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.InputFormat;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.LineRecordReader;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.SequenceFileAsBinaryInputFormat.SequenceFileAsBinaryRecordReader;
import org.apache.hadoop.util.ReflectionUtils;

public class SafeTextInputFormat extends FileInputFormat<LongWritable, Text>
		implements InputFormat<LongWritable, Text> {

	private static final Log LOG = LogFactory.getLog(SafeTextInputFormat.class);

	@Override
	public RecordReader<LongWritable, Text> getRecordReader(InputSplit split,
			JobConf job, Reporter arg2) throws IOException {
		try {
			return new LineRecordReader(job, (FileSplit) split);
		} catch (IOException e) {
			return new SkipLineRecordReader(job, (FileSplit) split);
		}
	}

	protected boolean isSplitable(FileSystem fs, Path filename) {
		return false;
	}

	@Override
	public InputSplit[] getSplits(JobConf arg0, int arg1) throws IOException {
		return super.getSplits(arg0, arg1);
	}

}
