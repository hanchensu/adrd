package com.sohu.ad.data.sessionlog.udf;

import java.io.IOException;
import java.util.List;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;

import com.sohu.ad.data.common.AdrdUtil;
import com.sohu.ad.data.common.CountinfoMaker;
import com.sohu.ad.data.common.FormatResult;
import com.sohu.ad.data.common.LogSchema;
import com.sohu.ad.data.common.Util;
import com.sohu.ad.data.thrift.operation.CountinfoOperation;


public class Serilize extends EvalFunc<Tuple> {
	
	private static TupleFactory tupleFactory = TupleFactory.getInstance();
	
	public Tuple exec(Tuple input) throws IOException {
		if (input == null || input.size() < 2) {
            return null;
        }
		String rawLog = (String) input.get(0);
		if(Util.isBlank(rawLog)) return null;
		int dupliNum = (Integer) input.get(1); 
		
		try {
			FormatResult fr = AdrdUtil.format(rawLog,LogSchema.COUNTINFO_SCHEMA);
			
			if(fr.strs != null) {
				
				CountinfoOperation countinfo = CountinfoMaker.makeCountinfo(fr);
				countinfo.setRepeat(dupliNum);
				
				String userId = AdrdUtil.makeUserId(countinfo.yyId,countinfo.suv,countinfo.userIp,countinfo.userAgent);
				String logType = AdrdUtil.getOpType(countinfo);
				long timestamp = countinfo.getTimestamp();
				DataByteArray serilized = new DataByteArray(AdrdUtil.serilize(countinfo));
				
				Tuple tuple = tupleFactory.newTuple(4);
				
				tuple.set(0, userId);
				tuple.set(1, logType);
				tuple.set(2, timestamp);
				tuple.set(3, serilized);
				
				return tuple;
			
			} else {
				
				return null;
				//TODO: blankLine or jsonError, output to error file;
			}

		} catch (Exception e) {
			//output serilize error
			return null;			
		}
		
	}
	
}