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
import org.apache.hadoop.mapred.JobConfigurable;
import org.apache.hadoop.mapred.LineRecordReader;

import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;

import com.sohu.adrd.data.common.LogSchema;


public class FixedCountinfoInputFormat extends FileInputFormat<LongWritable, Text>
{

	private static final Log LOG = LogFactory.getLog(FixedCountinfoInputFormat.class);

	@Override
	public RecordReader<LongWritable, Text> getRecordReader(InputSplit split,
			JobConf job, Reporter reporter) throws IOException {
		
		return new FixedAdserverLineRecordReader(job, (FileSplit) split,LogSchema.COUNTINFO_SCHEMA);
	}

	protected boolean isSplitable(FileSystem fs, Path filename) {
		return false;
	}

	@Override
	public InputSplit[] getSplits(JobConf arg0, int arg1) throws IOException {
		return super.getSplits(arg0, arg1);
	}

}
