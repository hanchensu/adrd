package com.sohu.adrd.data.pig.loader;

import java.io.IOException;
import java.util.List;

import org.apache.commons.net.nntp.NewGroupsOrNewsQuery;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.pig.LoadFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigSplit;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;

import com.sohu.adrd.data.common.AdrdDataUtil;
import com.sohu.adrd.data.common.FormatResult;
import com.sohu.adrd.data.common.LogSchema;
import com.sohu.adrd.data.common.Util;
import com.sohu.adrd.data.mapreduce.InputPathFilter;
import com.sohu.adrd.data.pig.udf.log.LogLoader;

public class TestLoader extends LoadFunc {
	
	private RecordReader reader;
	private final TupleFactory tupleFactory = TupleFactory.getInstance();

	@Override
	public InputFormat getInputFormat() throws IOException {
		return new TextInputFormat();
	}
	
	
	@Override
	public Tuple getNext() throws IOException {
		try {
			if (!reader.nextKeyValue()) {
				return null;
			}
			Text value = (Text) reader.getCurrentValue();
			String line = value.toString();
			
			Tuple tuple = tupleFactory.newTuple(1);
			tuple.set(0, new DataByteArray(line));
			return tuple;

		} catch (InterruptedException e) {
			throw new ExecException(e);
		}
	}
	
	@Override
	public void prepareToRead(RecordReader reader, PigSplit split)
			throws IOException {
		this.reader = reader;
	}

	@Override
	public void setLocation(String location, Job job) throws IOException {
		FileInputFormat.setInputPathFilter(job, InputPathFilter.class);
		FileInputFormat.setInputPaths(job, location);
	}
}
