package com.sohu.adrd.data.pig.loader;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.mapred.lib.MultipleInputs;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileRecordReader;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.security.User;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.pig.LoadFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigSplit;
import org.apache.pig.data.BagFactory;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;

import com.sohu.ADRD.AudienceTargeting.type.basic.TagsWritable;
import com.sohu.ADRD.AudienceTargeting.type.basic.UserWritable;



@SuppressWarnings("deprecation")
public class UserTagLoader extends LoadFunc {
	
	private SequenceFileRecordReader<UserWritable,TagsWritable> reader;
	private static TupleFactory tupleFactory = TupleFactory.getInstance();
	private static BagFactory bagFactory = BagFactory.getInstance();
	
	@Override
	public InputFormat getInputFormat() throws IOException {
		return new SequenceFileInputFormat<UserWritable, TagsWritable>();
	}
	
	@Override
	public void prepareToRead(RecordReader reader, PigSplit split)
			throws IOException {
		this.reader = (SequenceFileRecordReader<UserWritable, TagsWritable>) reader;
	}

	@Override
	public void setLocation(String location, Job job) throws IOException {
		FileInputFormat.setInputPaths(job, location);
	}

	@Override
	public Tuple getNext() throws IOException {
		try {
			
			long tag_mask = 0xffffffffffff0000l;
			
			
			if(!reader.nextKeyValue()) {
				return null;
			} else {
				UserWritable key = reader.getCurrentKey();
				String userid = key.getuserid();
				if(userid == null) {
					Tuple tuple = tupleFactory.newTuple(1);
					tuple.set(0, null);
					return tuple;
				}
				
				Tuple tuple = tupleFactory.newTuple(2);
				
				List<Tuple> tuples = new ArrayList<Tuple>();
				TagsWritable value = reader.getCurrentValue();
				List<Long> tagList = value.gettags_long();
				for(java.util.Iterator<Long> iter = tagList.iterator();iter.hasNext();) {
					long tagall = iter.next();
					long tag = tagall&tag_mask;

					Tuple tagTuple = tupleFactory.newTuple(1);
					tagTuple.set(0, String.valueOf(tag));
					tuples.add(tagTuple);
				}
				
				DataBag tags = bagFactory.newDefaultBag(tuples);
				
				tuple.set(0, userid);
				tuple.set(1, tags);
				return tuple;
				
			}
		}  catch (Exception e) {
			throw new ExecException(e);
		}
			
	}
	

}

