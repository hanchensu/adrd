package com.sohu.adrd.data.hive.io;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
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
import org.apache.hadoop.mapred.SequenceFileRecordReader;
import org.apache.hadoop.mapred.SequenceFileAsBinaryInputFormat.SequenceFileAsBinaryRecordReader;
import org.apache.hadoop.util.ReflectionUtils;

import com.sohu.ADRD.AudienceTargeting.type.basic.TagsWritable;
import com.sohu.ADRD.AudienceTargeting.type.basic.UserWritable;
import com.sohu.adrd.data.common.LogSchema;
import com.sohu.adrd.data.mapred.SkipLineRecordReader;

public class UserTagsInputFormat extends BaseSequenceFileInputFormat<UserWritable, TagsWritable>
		implements InputFormat<UserWritable, BytesWritable> {

	@Override
	public RecordReader<UserWritable, BytesWritable> getRecordReader(InputSplit split, JobConf job,
			Reporter reporter) throws IOException {
		reporter.setStatus(split.toString());

	    return new UserTagsRecordReader(job, (FileSplit) split);
	}

}
