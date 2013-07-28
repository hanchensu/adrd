package com.sohu.adrd.data.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;

import com.sohu.adrd.data.sessionlog.thrift.operation.CountinfoOperation;
import com.sohu.adrd.data.sessionlog.thrift.operation.OperationType;


public class AdrdDataUtil {
	
	public static String makeUserId(String yyid, String suv, String ip, String agent) {
		
		if (Util.isNotBlank(yyid)) return yyid;
		
		if (Util.isNotBlank(suv)) return suv;
		
		if (Util.isNotBlank(ip)) {
			String s = Util.isNotBlank(agent) ? ip + agent : ip;
			s = Util.getMD5(s);
			if (s == null) return null;
			else return s;
		}
		return null;
	}
	
	public static String makeUserId(CountinfoOperation countinfo) {
		return makeUserId(countinfo.yyId, countinfo.suv, countinfo.userIp, countinfo.userAgent);
	}
	
	public static FormatResult format(String str,String[] schema) {
		List<String> result = new ArrayList<String>();
		
		if (Util.isBlank(str)) // filter illegal
			return new FormatResult(null, "BlankLine");

		result.clear();
		for (int i = 0; i < schema.length; i++)
			result.add(null);

		try {
			String logtime = str.substring(0, 23);
			result.set(0, logtime);
		} catch (Exception e1) {
			result.set(0, null);
		}

		try {
			String mark = str.substring(24, 29);
			result.set(1, mark);
		} catch (Exception e2) {
			result.set(0, null);
		}

		return Util.jsonFormat(schema, result, str);
	}
	
	public static OperationType getOpType(String reqType, String adType, String suv) {
		
		String CLICK_FLAG = "click";
		String DISPLAY_FLAG = "view";
		String NEWS_TYPE = "2";
		String TEST_SUV = "123456";
		String REACH_FLAG = "reach";
		String ARRIVE_FLAG = "arrive";
		String ERR_FLAG = "err";
		
		
		if (DISPLAY_FLAG.equals(reqType) && TEST_SUV.equals(suv)) {
			
			return OperationType.HB_DISPLAY;
			
		} else if (CLICK_FLAG.equals(reqType) && TEST_SUV.equals(suv)) {
			
			return OperationType.HB_CLICK;
			
		} else if (DISPLAY_FLAG.equals(reqType) && NEWS_TYPE.equals(adType)) {
			
			return OperationType.NEWS_DISPLAY;
			
		} else if (CLICK_FLAG.equals(reqType) && NEWS_TYPE.equals(adType)) {
			
			return OperationType.NEWS_CLICK;
			
		} else if (DISPLAY_FLAG.equals(reqType) && !NEWS_TYPE.equals(adType)) {
			
			return OperationType.AD_DISPLAY;
			
		} else if (CLICK_FLAG.equals(reqType) && !NEWS_TYPE.equals(adType)) {
			
			return OperationType.AD_CLICK;
			
		} else if (reqType.equals(REACH_FLAG)) {
			
			return OperationType.REACH;
			
		}  else if (reqType.equals(ARRIVE_FLAG)) {
			
			return OperationType.ARRIVE;
			
		} else if (reqType.equals(ERR_FLAG)) {
			
			return OperationType.ERR;
			
		} else {
			
			return null;
		}
	}
	
	public static OperationType getOpType(CountinfoOperation countinfo) {
		
		return getOpType(countinfo.reqType, countinfo.adType, countinfo.suv);
	}
	
	public static byte[] serilize(CountinfoOperation adinfo) throws IOException {
		TSerializer serializer = new TSerializer(new TBinaryProtocol.Factory());
		try {
			byte[] byteArray = serializer.serialize(adinfo);
			return byteArray;
		} catch (TException e) {
			throw new IOException("Serilizer: Serilize error");
		}
	
	}
	

}
