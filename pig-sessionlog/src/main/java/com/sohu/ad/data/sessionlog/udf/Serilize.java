package com.sohu.ad.data.sessionlog.udf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.schema.Schema;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;

import com.sohu.ad.data.common.AdrdDataUtil;
import com.sohu.ad.data.common.FormatResult;
import com.sohu.ad.data.common.LogSchema;
import com.sohu.ad.data.common.Util;
import com.sohu.ad.data.sessionlog.CountinfoMaker;
import com.sohu.ad.data.thrift.operation.CountinfoOperation;


public class Serilize extends EvalFunc<DataByteArray> {
	
	
	public DataByteArray exec(Tuple input) throws IOException {
		if (input == null || input.size() < 3) {
            return null;
        }
		String rawLog = (String) input.get(0);
		if(Util.isBlank(rawLog)) return null;
		long dupliNum = (Long) input.get(1); 
		String logTag = (String) input.get(2);
		
		try {
			FormatResult fr = AdrdDataUtil.format(rawLog,LogSchema.COUNTINFO_SCHEMA);
			
			if(fr.strs != null) {
				
				if("countinfo".equals(logTag)) {
					CountinfoOperation countinfo = CountinfoMaker.makeCountinfo(fr);
					countinfo.setRepeat((int) dupliNum);
					
//					String userId = AdrdDataUtil.makeUserId(countinfo.yyId,countinfo.suv,countinfo.userIp,countinfo.userAgent);
//					String logType = AdrdDataUtil.getOpType(countinfo);
//					long timestamp = countinfo.getTimestamp();
					DataByteArray serilized = new DataByteArray(AdrdDataUtil.serilize(countinfo));
					
//					Tuple tuple = tupleFactory.newTuple(4);
//					tuple.set(0, userId);
//					tuple.set(1, logType);
//					tuple.set(2, timestamp);
//					tuple.set(3, serilized);
					
					return serilized;
				
				} else {
					return null;
				}
				
			
			} else {
				
				return null;
				//TODO: blankLine or jsonError, output to error file;
			}

		} catch (Exception e) {
			//output serilize error
			return null;			
		}
		
	}
	
//	public Schema outputSchema(Schema input) {
//		try {
//			List<Schema.FieldSchema> fieldSchemas = new ArrayList<Schema.FieldSchema>();
//			
//			Schema.FieldSchema fieldSchema = new Schema.FieldSchema("user",DataType.CHARARRAY);
//			fieldSchemas.add(fieldSchema);
//		    
//			fieldSchema = new Schema.FieldSchema("type",DataType.CHARARRAY);
//		    fieldSchemas.add(fieldSchema);
//		    
//		    fieldSchema = new Schema.FieldSchema("timestamp",DataType.LONG);
//		    fieldSchemas.add(fieldSchema);
//		    
//		    fieldSchema = new Schema.FieldSchema("serilizeOp",DataType.BYTEARRAY);
//		    fieldSchemas.add(fieldSchema);
//		    
//			return new Schema(fieldSchemas);
//		} catch (Exception e) {
//			return null;
//		}
//	}
	
}