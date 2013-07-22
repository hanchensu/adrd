package com.sohu.ad.data.pig.udf.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


import org.apache.commons.net.nntp.NewGroupsOrNewsQuery;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.pig.Expression;
import org.apache.pig.LoadCaster;
import org.apache.pig.LoadFunc;
import org.apache.pig.LoadMetadata;
import org.apache.pig.ResourceSchema;
import org.apache.pig.ResourceStatistics;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigSplit;
import org.apache.pig.builtin.Utf8StorageConverter;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.util.Utils;
import org.apache.pig.parser.AliasMasker.output_clause_return;

import com.sohu.ad.data.common.InputPathFilter;
import com.sohu.ad.data.common.Util;
import com.sohu.ad.data.pig.udf.custom.TestConverter;

public  class TestLoader1 extends LoadFunc implements LoadMetadata{
	
	protected String projection;
	
	private RecordReader reader;
	private final TupleFactory tupleFactory = TupleFactory.getInstance();
	
	@Override
	public InputFormat getInputFormat() throws IOException {
		return new TextInputFormat();
	}
	
	@Override
	public Tuple getNext() throws IOException {
		try {
			if(!reader.nextKeyValue()) {
				return null;
			}
			Text value = (Text) reader.getCurrentValue();
			String line = value.toString();
			Tuple tuple = tupleFactory.newTuple(8);		
			
			tuple.set(0, new DataByteArray(line.split(",")[0]));
			tuple.set(1, new DataByteArray(line.split(",")[1]));
			tuple.set(2, new DataByteArray(line.split(",")[2]));
			tuple.set(3, new DataByteArray(line.split(",")[3]));
			
			return tuple;
			
		} catch (InterruptedException e) {
			throw new ExecException(e);
		}
	}
	
	@Override
	public LoadCaster getLoadCaster() throws IOException {
        return new TestConverter();
    }
	
	public static void main(String args[]) throws IOException {
		TupleFactory tupleFactory = TupleFactory.getInstance();
		Tuple tuple = tupleFactory.newTuple(3);
		tuple.set(0, 1);
		tuple.set(1, "abc");
		tuple.set(2, (float)3.5);
		System.out.println(tuple.get(0).getClass().toString());
		System.out.println(tuple.get(1).getClass());
		System.out.println(tuple.get(2).getClass());

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

	@Override
	public ResourceSchema getSchema(String location, Job job)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(location+"/_schema")));
		String str;
		while ((str = br.readLine()) != null) {
			break;
		}
		br.close();
		ResourceSchema s = new ResourceSchema(Utils.getSchemaFromString(str));
				
		return s;
	}

	@Override
	public ResourceStatistics getStatistics(String location, Job job)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getPartitionKeys(String location, Job job)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPartitionFilter(Expression partitionFilter)
			throws IOException {
		// TODO Auto-generated method stub
		
	}

}

