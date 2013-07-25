package com.sohu.ad.data.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;

import com.sohu.ad.data.thrift.operation.CountinfoOperation;


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
	
	public static String getOpType(String reqType, String adType, String suv) {
		
		String CLICK_FLAG = "click";
		String DISPLAY_FLAG = "view";
		String NEWS_TYPE = "2";
		String TEST_SUV = "123456";
		String REACH_FLAG = "reach";
		String ARRIVE_FLAG = "arrive";
		String ERR_FLAG = "err";
		
		
		if (DISPLAY_FLAG.equals(reqType) && TEST_SUV.equals(suv)) {
			
			return "hbdisplay";
			
		} else if (CLICK_FLAG.equals(reqType) && TEST_SUV.equals(suv)) {
			
			return "hbclick";
			
		} else if (DISPLAY_FLAG.equals(reqType) && NEWS_TYPE.equals(adType)) {
			
			return "newsdisplay";
			
		} else if (CLICK_FLAG.equals(reqType) && NEWS_TYPE.equals(adType)) {
			
			return "newsclick";
			
		} else if (DISPLAY_FLAG.equals(reqType) && !NEWS_TYPE.equals(adType)) {
			
			return "addisplay";
			
		} else if (CLICK_FLAG.equals(reqType) && !NEWS_TYPE.equals(adType)) {
			
			return "adclick";
			
		} else if (reqType.equals(REACH_FLAG)) {
			
			return "reach";
			
		}  else if (reqType.equals(ARRIVE_FLAG)) {
			
			return "arrive";
			
		} else if (reqType.equals(ERR_FLAG)) {
			
			return "err";
			
		} else {
			
			return "unknownLogType";
		}
	}
	
	public static String getOpType(CountinfoOperation countinfo) {
		
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
	
	
//	public static long getStatusCode(FormatResult formatResult) {
//		return new CountinfoMaker().makeCountinfo(formatResult).getAdId();
//	}

}
