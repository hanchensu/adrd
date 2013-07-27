package com.sohu.adrd.data.pig.udf.custom;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.pig.EvalFunc;
import org.apache.pig.builtin.TupleSize;
import org.apache.pig.data.BagFactory;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;

import com.sohu.adrd.data.common.KeyFormator;
import com.sohu.adrd.data.common.MonitorKeyParser;

public class YingFuUDF extends EvalFunc<DataBag> {
	public DataBag exec(Tuple input) throws IOException {
		BagFactory bagFactory = BagFactory.getInstance();
		List<Tuple> tuplesToGetIntervalDays = new ArrayList<Tuple>();
		List<Tuple> tuplesToMakeBag = new ArrayList<Tuple>();
		try{
			
			DataBag bag = (DataBag)input.get(0);
			Iterator<Tuple> it = bag.iterator();
			
			while(it.hasNext()) {
				Tuple t = it.next();
				if(t != null && t.size() >= 3) {
					String type = (String) t.get(1);
					if("arrive".equals(type)) {
						tuplesToGetIntervalDays.add(t);
					} else if("click".equals(type)) {
						for(Tuple tuple:tuplesToGetIntervalDays) {
							long time1 = (Long) tuple.get(2);
							long time2 = (Long) t.get(2);
							tuple.set(2, getIntervalDays(time1,time2));
							SimpleDateFormat format =   new SimpleDateFormat("yyyy-MM-dd");
							String day = format.format(time1*1000);
							tuple.append(day);
							tuplesToMakeBag.add(tuple);
						}
						tuplesToGetIntervalDays.clear();
					}
				}
			}
			return bagFactory.newDefaultBag(tuplesToMakeBag);
			
		} catch (Exception e) {
			e.printStackTrace();
			return bagFactory.newDefaultBag(tuplesToMakeBag);
		}
		
	}
	
	public long getIntervalDays(long t1, long t2) throws Exception {
		SimpleDateFormat format =   new SimpleDateFormat("yyyy-MM-dd");
		String d1 = format.format(t1*1000L);
	    String d2 = format.format(t2*1000L);
	    Date date1 = format.parse(d1);
	    Date date2 = format.parse(d2);
	    return (date1.getTime() - date2.getTime()) / (24*60*60*1000);
	}

	
	public Schema outputSchema(Schema input) {
		try {
			List<Schema.FieldSchema> fieldSchemas = new ArrayList<Schema.FieldSchema>();
			
			Schema.FieldSchema fieldSchema = new Schema.FieldSchema("ext",DataType.CHARARRAY);
			fieldSchemas.add(fieldSchema);
		    
			fieldSchema = new Schema.FieldSchema("reqtype",DataType.CHARARRAY);
		    fieldSchemas.add(fieldSchema);
			
		    fieldSchema = new Schema.FieldSchema("intervalDays",DataType.LONG);
			fieldSchemas.add(fieldSchema);
			
			fieldSchema = new Schema.FieldSchema("day",DataType.CHARARRAY);
			fieldSchemas.add(fieldSchema);
			
			Schema scehma = new Schema(fieldSchemas);
			
			return new Schema(new Schema.FieldSchema("tuples",scehma,DataType.BAG));
		} catch (FrontendException e) {
			return null;
		}
	}
	

	
}
