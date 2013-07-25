package com.sohu.ad.data.sessionlog.udf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.schema.Schema;


public class Concat extends EvalFunc<Tuple> {
	
	private static TupleFactory tupleFactory = TupleFactory.getInstance();
	
	public Tuple exec(Tuple input) throws IOException {
		if (input == null || input.size() < 2) {
            return null;
        }
		
		try {
			Tuple userAndType = (Tuple) input.get(0);
			String userId = (String) userAndType.get(0);
			String logType = (String) userAndType.get(1);
			DataBag bag = (DataBag)input.get(1);
			Iterator<Tuple> it = bag.iterator();
			
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			while(it.hasNext()) {
				Tuple t = it.next();
				byte[] serilized = ((DataByteArray) t.get(3)).get();
				buffer.write(serilized);
			}
			
			Tuple tuple = tupleFactory.newTuple(3);
			tuple.set(0, userId);
			tuple.set(1, logType);
			tuple.set(2, new DataByteArray(buffer.toByteArray()));
			
			return tuple;
			
		} catch (Exception e) {
			
			return null;
			
		}
	}
	
	public Schema outputSchema(Schema input) {
		try {
			List<Schema.FieldSchema> fieldSchemas = new ArrayList<Schema.FieldSchema>();
			
			Schema.FieldSchema fieldSchema = new Schema.FieldSchema("user",DataType.CHARARRAY);
			fieldSchemas.add(fieldSchema);
		    
			fieldSchema = new Schema.FieldSchema("type",DataType.CHARARRAY);
		    fieldSchemas.add(fieldSchema);
		    
		    
		    fieldSchema = new Schema.FieldSchema("serilizeOps",DataType.BYTEARRAY);
		    fieldSchemas.add(fieldSchema);
		    
			return new Schema(fieldSchemas);
		} catch (Exception e) {
			return null;
		}
	}
	
}
