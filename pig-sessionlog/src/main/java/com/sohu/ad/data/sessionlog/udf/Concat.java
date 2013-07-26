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


public class Concat extends EvalFunc<DataByteArray> {
		
	public DataByteArray exec(Tuple input) throws IOException {
		if (input == null || input.size() == 0) {
            return null;
        }
		
		try {
			DataBag bag = (DataBag)input.get(0);
			Iterator<Tuple> it = bag.iterator();
			
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			while(it.hasNext()) {
				Tuple t = it.next();
				byte[] serilized = ((DataByteArray) t.get(0)).get();
				buffer.write(serilized);
			}
			
			return new DataByteArray(buffer.toByteArray());
			
		} catch (Exception e) {
			
			return null;
			
		}
	}
	
}
