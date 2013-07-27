package com.sohu.adrd.data.pig.udf.custom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.BagFactory;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.schema.Schema;




public class RmDupli extends EvalFunc<Tuple> {
	
	private static TupleFactory tupleFactory = TupleFactory.getInstance();
	private static BagFactory bagFactory = BagFactory.getInstance();
	
	public Tuple exec(Tuple input) throws IOException {
		if (input == null || input.size() < 2) {
			return null;
		}
		try {
			
			DataBag bag = (DataBag) input.get(0);
			int interval = (Integer) input.get(1);
			
			List<Tuple> tupleList = new ArrayList<Tuple>();
			
			
			Iterator<Tuple> it = bag.iterator();
			
			while(it.hasNext()) {
				Tuple t = it.next();
				if(t != null && t.size() >= 4) {
					tupleList.add(t);
				}
			}
			
			Long nodupli = 0L;
			Long dupli = 0L;
			for(int i = 0; i < tupleList.size(); i++) {
				
				Tuple t = tupleList.get(i);
				Long time;
				try {
					
					time = (Long) t.get(3);
					int sum = 0;
					Set<String> keySet = new HashSet<String>();
					for(int j = i; j < tupleList.size(); j++) {
						Tuple t2 = tupleList.get(j);
						String mkey2;
						Long time2;
						try {
							mkey2 = (String) t2.get(1);
							time2 = (Long) t2.get(3);
							if(time2 - time <= interval) {
								sum++;
								keySet.add(mkey2);
							} else {
								break;
							}
						} catch (Exception e) {
							
						}
						
					}
					dupli+=sum;
					nodupli+=keySet.size();
					
					
				} catch (Exception e) {
					
				}
				
			}
			Tuple res = tupleFactory.newTuple(2);
			res.set(0, nodupli);
			res.set(1, dupli);
			
			return res;

		} catch (Exception e) {
			return null;
		}
	}
	
	public Schema outputSchema(Schema input) {
		try {
			List<Schema.FieldSchema> fieldSchemas = new ArrayList<Schema.FieldSchema>();
			
			Schema.FieldSchema fieldSchema = new Schema.FieldSchema("nodupli",DataType.LONG);
			fieldSchemas.add(fieldSchema);
		    fieldSchema = new Schema.FieldSchema("dupli",DataType.LONG);
		    fieldSchemas.add(fieldSchema);
		    
			return new Schema(fieldSchemas);
		} catch (Exception e) {
			return null;
		}
	}

}
