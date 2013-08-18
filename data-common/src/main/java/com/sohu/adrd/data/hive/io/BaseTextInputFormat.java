package com.sohu.adrd.data.hive.io;

import java.io.IOException;

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
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;

public abstract class BaseTextInputFormat extends FileInputFormat<LongWritable, Text>
		implements InputFormat<LongWritable, Text> {

	private static final Log LOG = LogFactory.getLog(BaseTextInputFormat.class);

	@Override
	public abstract RecordReader<LongWritable, Text> getRecordReader(InputSplit split,
			JobConf job, Reporter arg2) throws IOException;

	protected boolean isSplitable(FileSystem fs, Path filename) {
		return false;
	}

	@Override
	public InputSplit[] getSplits(JobConf arg0, int arg1) throws IOException {
		return super.getSplits(arg0, arg1);
	}

}
