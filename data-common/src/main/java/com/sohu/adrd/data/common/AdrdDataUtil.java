package com.sohu.adrd.data.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;

import com.sohu.adrd.data.sessionlog.thrift.operation.CountinfoOperation;
import com.sohu.adrd.data.sessionlog.thrift.operation.OperationType;


/**
 * @author Su Hanchen hanchensu@sohu-inc.com
 * 
 */

public class AdrdDataUtil {
	/** 
	 * Make user id from yyid, suv, ip and agent
	 * 
	 * @param yyid yyid
	 * @param suv suv
	 * @param ip user ip
	 * @param agent	user agent
	 * @return unique user id, null if all four values are blank
	 */
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
	
	/**
	 * Make user id from {@link CountinfoOperation}
	 * 
	 * @param countinfo {@link CountinfoOperation} serialized by thrift from log
	 * @return unique user id, null if yyid, suv, ip, agent are all blank
	 */
	public static String makeUserId(CountinfoOperation countinfo) {
		return makeUserId(countinfo.yyId, countinfo.suv, countinfo.userIp, countinfo.userAgent);
	}
	
	/**
	 * Turn a jason log from the adserver into a <tt>List<String></tt>
	 * 
	 * @author Su Hanchen hanchensu@sohu-inc.com
	 * @param str Log to format
	 * @param schema a string array that contains the attribute names of the schema
	 * @return {@link FormatResult} with <tt>strs == null</tt> if error happened and the <tt>errcode</tt> (default: Normal)
	 */
	public static FormatResult format(String str,String[] schema) {
		List<String> result = new ArrayList<String>();
		
		if (Util.isBlank(str)) //filter Blankline
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
			String logLevel = str.substring(23,str.indexOf("{"));	
			result.set(1, logLevel.trim());
		} catch (Exception e2) {
			result.set(1, null);
		}

		return Util.jsonFormat(schema, result, str);
	}
	
	public static int compareTo(byte[] buffer1, int offset1, int length1,
			byte[] buffer2, int offset2, int length2) {
		// Short circuit equal case
		if (buffer1 == buffer2 && offset1 == offset2 && length1 == length2) {
			return 0;
		}

		int end1 = offset1 + length1;
		int end2 = offset2 + length2;
		for (int i = offset1, j = offset2; i < end1 && j < end2; i++, j++) {
			int a = (buffer1[i] & 0xff);
			int b = (buffer2[j] & 0xff);
			if (a != b) {
				return a - b;
			}
		}
		return length1 - length2;
	}
	
	public static OperationType getOpType(String reqType, String adType, String suv) {
		
		String CLICK_FLAG = "click";
		String DISPLAY_FLAG = "view";
		String NEWS_TYPE = "2";
		String TEST_SUV = "123456";
		String REACH_FLAG = "reach";
		String ARRIVE_FLAG = "arrive";
		String ACTION_FLAG = "action";
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
			
		} else if (REACH_FLAG.equals(reqType)) {
			
			return OperationType.REACH;
			
		}  else if (ARRIVE_FLAG.equals(reqType)) {
			
			return OperationType.ARRIVE;
			
		} else if (ACTION_FLAG.equals(reqType)) {
			
			return OperationType.ACTION;
			
		} else if (ERR_FLAG.equals(reqType)) {
			
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
	
	public static void main(String[] args) {
		
	}
	

}
