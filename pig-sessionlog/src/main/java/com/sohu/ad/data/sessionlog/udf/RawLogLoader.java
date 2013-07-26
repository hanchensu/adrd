package com.sohu.ad.data.sessionlog.udf;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.pig.LoadFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigSplit;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;

import com.sohu.ad.data.common.Util;
import com.sohu.ad.data.sessionlog.io.InputPathFilter;
import com.sohu.ad.data.sessionlog.io.SessionLogInputFormat;

public abstract class RawLogLoader extends LoadFunc {

	protected String projection;

	private RecordReader reader;
	private final TupleFactory tupleFactory = TupleFactory.getInstance();

	@Override
	public InputFormat getInputFormat() throws IOException {
		return new SessionLogInputFormat();
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

			if (Util.isNotBlank(line)) {
				tuple.set(0, new DataByteArray(line));
				return tuple;
			}
			return null;
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
