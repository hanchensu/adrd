package com.sohu.ad.data.pig.udf.custom;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.BagFactory;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;



import com.sohu.ad.data.common.KeyFormator;
import com.sohu.ad.data.common.MonitorKeyParser;

public class ToBag extends EvalFunc<DataBag> {
	
	private static TupleFactory tupleFactory = TupleFactory.getInstance();
	private static BagFactory bagFactory = BagFactory.getInstance();
	
	public DataBag exec(Tuple input) throws IOException {
		if (input == null || input.size() < 4) {
			return null;
		}
		try {
			String url = (String) input.get(0);
			String user = (String) input.get(1);
			long time = (Long) input.get(2);
			int interval = (Integer) input.get(3);
			List<Tuple> tuples = new ArrayList<Tuple>();
			for(int i = 0; i <= interval; i++) {
				Tuple tuple = tupleFactory.newTuple(3);
				tuple.set(0, url);
				tuple.set(1, user);
				tuple.set(2, time+i);
				tuples.add(tuple);
			}
			return bagFactory.newDefaultBag(tuples);

		} catch (Exception e) {
			return null;
		}
	}
	
	public Schema outputSchema(Schema input) {
		try {
			List<Schema.FieldSchema> fieldSchemas = new ArrayList<Schema.FieldSchema>();
			
			Schema.FieldSchema fieldSchema = new Schema.FieldSchema("url",DataType.CHARARRAY);
			fieldSchemas.add(fieldSchema);
		    fieldSchema = new Schema.FieldSchema("user",DataType.CHARARRAY);
		    fieldSchemas.add(fieldSchema);
			fieldSchema = new Schema.FieldSchema("time",DataType.LONG);
			fieldSchemas.add(fieldSchema);
			Schema scehma = new Schema(fieldSchemas);
			
			return new Schema(new Schema.FieldSchema("tuples",scehma,DataType.BAG));
		} catch (FrontendException e) {
			return null;
		}
	}

}
