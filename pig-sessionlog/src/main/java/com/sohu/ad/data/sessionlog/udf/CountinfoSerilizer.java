package com.sohu.ad.data.sessionlog.udf;

import java.io.IOException;
import java.util.List;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;

import com.sohu.ad.data.sessionlog.operation.AdInfoOperation;


public class CountinfoSerilizer extends EvalFunc<Tuple> {
	
	private static TupleFactory tupleFactory = TupleFactory.getInstance();
	
	public Tuple exec(Tuple input) throws IOException {
		if (input == null || input.size() < 2) {
            return null;
        }
		String rawLog = (String) input.get(0);
		int dupliNum = (Integer) input.get(1); 
		try {
			List<String> formatStr = CountinfoSerilizerUtil.format(rawLog);
			
			String userId = CountinfoSerilizerUtil.makeUserId(formatStr);
			String logType = CountinfoSerilizerUtil.getOperationType(formatStr);
			
			AdInfoOperation adinfo = new AdInfoOperation();
			CountinfoSerilizerUtil.setAdInfoValues(adinfo, formatStr);
			adinfo.setRepeat(dupliNum);
			
			long timestamp = adinfo.getTimestamp();
			
			DataByteArray serilized = new DataByteArray(CountinfoSerilizerUtil.serilize(adinfo));
			
			Tuple tuple = tupleFactory.newTuple(4);
			tuple.set(0, userId);
			tuple.set(1, logType);
			tuple.set(2, timestamp);
			tuple.set(4, serilized);
			
			return tuple;
			
		} catch (Exception e) {
			
			return null;
			
		}
	}
	
}