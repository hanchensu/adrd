package com.sohu.ad.data.sessionlog.udf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;


public class MergeOps extends EvalFunc<Tuple> {
	
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
			
			Tuple tuple = tupleFactory.newTuple(4);
			tuple.set(0, userId);
			tuple.set(1, logType);
			tuple.set(2, new DataByteArray(buffer.toByteArray()));
			
			return tuple;
			
		} catch (Exception e) {
			
			return null;
			
		}
	}
	
}
