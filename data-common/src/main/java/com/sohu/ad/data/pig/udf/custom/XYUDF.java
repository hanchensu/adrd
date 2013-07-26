package com.sohu.ad.data.pig.udf.custom;

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
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;

import com.sohu.ad.data.common.KeyFormator;
import com.sohu.ad.data.common.MonitorKeyParser;

public class XYUDF extends EvalFunc<Tuple> {
	public Tuple exec(Tuple input) throws IOException {
		try{
			TupleFactory tupleFactory = TupleFactory.getInstance();
			String mkey = (String) input.get(0);
			int clickX = (Integer) input.get(1);
			int clickY = (Integer) input.get(2);
			int adpX = (Integer) input.get(3);
			int adpY = (Integer) input.get(4);
			long time = (Long) input.get(5);
			int x,y;
			String status="";
			if(clickX*clickY>122500){
			    x= clickX-adpX;
			    y= clickY-adpY;
			}else{
			    x = clickX;
			    y = clickY;
			}

			if(x<=0 || y<=0 || x*y > 122500){
			   status = "invalid";
			} else {
			   status = "valid";
			}
			
			Tuple res = tupleFactory.newTuple(5);
			res.set(0, mkey);
			res.set(1, x);
			res.set(2, y);
			res.set(3, status);
			res.set(4, time);
			return res;
			
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public Schema outputSchema(Schema input) {
		try {
			List<Schema.FieldSchema> fieldSchemas = new ArrayList<Schema.FieldSchema>();
			
			Schema.FieldSchema fieldSchema = new Schema.FieldSchema("mkey",DataType.CHARARRAY);
			fieldSchemas.add(fieldSchema);
		    
		    fieldSchema = new Schema.FieldSchema("x",DataType.INTEGER);
			fieldSchemas.add(fieldSchema);
			
			fieldSchema = new Schema.FieldSchema("y",DataType.INTEGER);
			fieldSchemas.add(fieldSchema);
			
			fieldSchema = new Schema.FieldSchema("status",DataType.CHARARRAY);
			fieldSchemas.add(fieldSchema);
			
			fieldSchema = new Schema.FieldSchema("time",DataType.LONG);
			fieldSchemas.add(fieldSchema);
			
//			Schema schema = new Schema(fieldSchemas);
//			
//			return new Schema(new Schema.FieldSchema("tuple",schema,DataType.TUPLE));
			return new Schema(fieldSchemas);
		} catch (Exception e) {
			return null;
		}
	}
}
