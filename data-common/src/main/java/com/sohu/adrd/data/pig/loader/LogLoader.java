package com.sohu.adrd.data.pig.loader;

import java.io.IOException;
import java.util.List;

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
import org.apache.pig.impl.logicalLayer.schema.Schema;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sohu.adrd.data.common.FormatResult;
import com.sohu.adrd.data.common.Util;
import com.sohu.adrd.data.mapreduce.InputPathFilter;

public abstract class LogLoader extends LoadFunc {

	protected String projection;
	protected String[] SCHEMA;
	protected final Log log = LogFactory.getLog(getClass());

	protected abstract FormatResult format(String log);

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
			if (Util.isNotBlank(line) && line.startsWith("(")
					&& line.endsWith(")")) {
				line = line.substring(1, line.length() - 1);
			}

			FormatResult fs = format(line);

			List<String> columns = fs.strs;

			if (columns == null) {
				Tuple tuple = tupleFactory.newTuple(1);
				tuple.set(0, null);
				return tuple;
			}
			
			int columnNum = projection.split(",").length;
			Tuple tuple = tupleFactory.newTuple(columnNum);
			int index = 0;
			for (String column : projection.split(",")) {
				if ("rawLog".equals(column.trim())) {
					tuple.set(index, new DataByteArray(line));
				} else {
					String fieldValue = columns.get(Util.indexOf(column.trim(), SCHEMA));
					
					if (Util.isBlank(fieldValue)) {
						tuple.set(index, null);
					} else {
						tuple.set(index, new DataByteArray(fieldValue));
					}
				}
				index++;
			}
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

	public static void main(String[] args) {
	}

}
