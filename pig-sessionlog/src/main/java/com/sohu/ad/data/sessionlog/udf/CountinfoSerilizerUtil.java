package com.sohu.ad.data.sessionlog.udf;

import java.net.URLDecoder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;


import com.sohu.ad.data.sessionlog.operation.AdInfoOperation;
import com.sohu.ad.data.sessionlog.util.Util;


public class CountinfoSerilizerUtil {

	private static String dfltEncName = null;
	static {
		dfltEncName = (String) AccessController
				.doPrivileged(new PrivilegedAction<String>() {
					public String run() {
						String value = System.getProperty("file.encoding");
						if (value == null)
							value = "UTF-8";
						return value;
					}
				});
	}

	private static final String[] SCHEMA = { "ADID", "ADPID", "ADPOS", "ADP_X",
			"ADP_Y", "ADTYPE", "BROWSER", "CLICK_X", "CLICK_Y", "CONTENT_URL",
			"EXT", "FREQ", "GETURL", "IMPRESSIONID", "LATENCY", "MONITORKEY",
			"OS", "REFFER", "REGION", "REQTYPE", "RESOLUTION", "SUPPORT_FLASH",
			"SUV", "TIME", "TURN", "USERAGENT", "USERIP", "YYID", "e", "c",
			"PAGEID", "ADVERTISERID", "JS_VERSION", "BidPrice", "BidPrice2",
			"BidType", "BidType2", "CTR", "CTR2", "eCPM", "eCPM2" };

	public static final int TOTAL_COLUMN = SCHEMA.length;

	public static List<String> format(String str) throws Exception {
		List<String> result = new ArrayList<String>();
		
		result.clear();
		for (int i = 0; i < TOTAL_COLUMN; i++)
			result.add(null);

		String regex = "\"([\\p{Print}]*?)\"[\\p{Blank}]*:[\\p{Blank}]*((\"[\\p{Print}]*?\")|([\\p{Print}]*?))[\\p{Blank}]*[,\\}]";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(str);
		while (match.find()) {
			String key = match.group(1);
			String quoted = match.group(3);
			String value = quoted != null ? quoted.substring(1,
					quoted.length() - 1) : match.group(4);
			key = key.trim();
			value = value.trim();

			if (key.equals("USERAGENT")) {
				value = value.replaceAll("\\\"", "\"");
			} else if (key.equals("REFFER")) {
				String ori = value;
				try {
					value = URLDecoder.decode(value, dfltEncName);
				} catch (Exception e) {
					value = ori;
				}

			}

			int index = indexOf(key);
			if (index == -1)
				throw new Exception("Formator: Unkown attribute: "+key);

			if (Util.isNotBlank(value)) {
				result.set(index, value);
			}

		}
		return result;
	}
	
	private static int indexOf(String key) {
		return Arrays.asList(SCHEMA).indexOf(key);
	}
	
	public static void setAdInfoValues(AdInfoOperation adinfo, List<String> strs) {
		
		adinfo.clear();
		String str = strs.get(indexOf("ADID"));
		if (Util.isNotBlank(str)) adinfo.setAdId(str);
		
		str = strs.get(indexOf("ADPID"));
		if (Util.isNotBlank(str)) adinfo.setAdpId(str);
		
		str = strs.get(indexOf("ADPOS"));
		if (Util.isNotBlank(str)) adinfo.setAdPos(str);
		
		str = strs.get(indexOf("ADP_X"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setAdpX(Integer.parseInt(str));
			} catch (NumberFormatException e) {
				adinfo.setAdpX(-123);
			}
		} else {
			adinfo.setAdpX(-234);
		}
		
		str = strs.get(indexOf("ADP_Y"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setAdpY(Integer.parseInt(str));
			} catch (NumberFormatException e) {
				adinfo.setAdpY(-123);
			}
		} else {
			adinfo.setAdpY(-234);
		}
		
		str = strs.get(indexOf("ADTYPE"));
		if (Util.isNotBlank(str)) adinfo.setAdType(str);
		
		str = strs.get(indexOf("BROWSER"));
		if (Util.isNotBlank(str)) adinfo.setBrowser(str);
		
		str = strs.get(indexOf("CLICK_X"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setClickX(Integer.parseInt(str));
			} catch (NumberFormatException e) {
				adinfo.setClickX(-123);
			}
		} else {
			adinfo.setClickX(-234);
		}
		
		str = strs.get(indexOf("CLICK_Y"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setClickY(Integer.parseInt(str));
			} catch (NumberFormatException e) {
				adinfo.setClickY(-123);
			}
		} else {
			adinfo.setClickY(-234);
		}
		
		str = strs.get(indexOf("CONTENT_URL"));
		if (Util.isNotBlank(str)) adinfo.setContentUrl(str);
		
		str = strs.get(indexOf("EXT"));
		if (Util.isNotBlank(str)) adinfo.setExt(str);
		
		str = strs.get(indexOf("FREQ"));
		if (Util.isNotBlank(str)) adinfo.setFreq(str);
		
		str = strs.get(indexOf("GETURL"));
		if (Util.isNotBlank(str)) adinfo.setGetUrl(str);
		
		str = strs.get(indexOf("IMPRESSIONID"));
		if (Util.isNotBlank(str)) adinfo.setImpressionId(str);
		
		str = strs.get(indexOf("LATENCY"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setLatency(Long.parseLong(str));
			} catch (NumberFormatException e) {
				adinfo.setLatency(-123);
			}
		} else {
			adinfo.setLatency(-234);
		}
		
		str = strs.get(indexOf("MONITORKEY"));
		if (Util.isNotBlank(str)) adinfo.setMonitorKey(str);
		
		str = strs.get(indexOf("OS"));
		if (Util.isNotBlank(str)) adinfo.setOs(str);
		
		str = strs.get(indexOf("REFFER"));
		if (Util.isNotBlank(str)) adinfo.setRefer(str);
		
		str = strs.get(indexOf("REGION"));
		if (Util.isNotBlank(str)) adinfo.setRegion(str);
		
		str = strs.get(indexOf("REQTYPE"));
		if (Util.isNotBlank(str)) adinfo.setReqType(str);
		
		str = strs.get(indexOf("RESOLUTION"));
		if (Util.isNotBlank(str)) adinfo.setResolution(str);
		
		str = strs.get(indexOf("SUPPORT_FLASH"));
		if (Util.isNotBlank(str)) adinfo.setSupportFlash(str);
		
		str = strs.get(indexOf("SUV"));
		if (Util.isNotBlank(str)) adinfo.setSuv(str);
		
		str = strs.get(indexOf("TIME"));
		if (Util.isNotBlank(str)) {
			try {
				if (Util.isNotBlank(str)) adinfo.setTimestamp(Long.parseLong(str));
			} catch (NumberFormatException e) {
				adinfo.setTimestamp(-123);		
			}
		} else {
			adinfo.setTimestamp(-234);
			
		}
		
		str = strs.get(indexOf("TURN"));
		if (Util.isNotBlank(str)) adinfo.setTurn(str);
		
		str = strs.get(indexOf("USERAGENT"));
		if (Util.isNotBlank(str)) adinfo.setUserAgent(str);
		
		str = strs.get(indexOf("USERIP"));
		if (Util.isNotBlank(str)) adinfo.setUserIp(str);
		
		str = strs.get(indexOf("YYID"));
		if (Util.isNotBlank(str)) adinfo.setYyId(str);
		
		str = strs.get(indexOf("e"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setE(Double.parseDouble(str));
			} catch (NumberFormatException e) {
				adinfo.setE(-123);
			}	
		} else {
			adinfo.setE(-234);
		}
		
		str = strs.get(indexOf("c"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setC(Double.parseDouble(str));
			} catch (NumberFormatException e) {
				adinfo.setC(-123);
			} 
		} else {
			adinfo.setC(-234);
		}
		
		str = strs.get(indexOf("PAGEID"));
		if (Util.isNotBlank(str)) adinfo.setPageId(str);
		
		adinfo.setRepeat(1);
		
		str = strs.get(strs.size()-1);
		try {
			adinfo.setStatusCode(Integer.parseInt(str));
		} catch (NumberFormatException e) {
			adinfo.setStatusCode(0);
		} 
		
		str = strs.get(indexOf("ADVERTISERID"));
		if (Util.isNotBlank(str)) adinfo.setAdvertiserId(str);
		
		str = strs.get(indexOf("JS_VERSION"));
		if (Util.isNotBlank(str)) adinfo.setJsVersion(str);
		
		str = strs.get(indexOf("BidPrice"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setBidPrice(Double.parseDouble(str));
			} catch (NumberFormatException e) {
				adinfo.setBidPrice(-123);
			}	
		} else {
			adinfo.setBidPrice(-234);
		}
		
		str = strs.get(indexOf("BidPrice2"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setBidPrice2(Double.parseDouble(str));
			} catch (NumberFormatException e) {
				adinfo.setBidPrice2(-123);
			}	
		} else {
			adinfo.setBidPrice2(-234);
		}
		
		str = strs.get(indexOf("CTR"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setCtr(Double.parseDouble(str));
			} catch (NumberFormatException e) {
				adinfo.setCtr(-123);
			}	
		} else {
			adinfo.setCtr(-234);
		}
		
		str = strs.get(indexOf("CTR2"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setCtr2(Double.parseDouble(str));
			} catch (NumberFormatException e) {
				adinfo.setCtr2(-123);
			}	
		} else {
			adinfo.setCtr2(-234);
		}
		
		str = strs.get(indexOf("eCPM"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setECPM(Double.parseDouble(str));
			} catch (NumberFormatException e) {
				adinfo.setECPM(-123);
			}	
		} else {
			adinfo.setECPM(-234);
		}
		
		str = strs.get(indexOf("eCPM2"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setECPM2(Double.parseDouble(str));
			} catch (NumberFormatException e) {
				adinfo.setECPM2(-123);
			}	
		} else {
			adinfo.setECPM2(-234);
		}
		
		str = strs.get(indexOf("BidType"));
		if (Util.isNotBlank(str)) adinfo.setBidType(str);
		
		str = strs.get(indexOf("BidType2"));
		if (Util.isNotBlank(str)) adinfo.setBidType2(str);	
	}
	
	public static String getOperationType(List<String> strs) {
		String CLICK_FLAG = "click";
		String DISPLAY_FLAG = "view";
		String NEWS_TYPE = "2";
		String TEST_SUV = "123456";
		String REACH_FLAG = "reach"; //0.2.4

		String reqType = strs.get(indexOf("REQTYPE"));
		String adType = strs.get(indexOf("ADTYPE"));
		String suv = strs.get(indexOf("SUV"));
		
		if (DISPLAY_FLAG.equals(reqType) && TEST_SUV.equals(suv)) {
			
			return "hbdisplay";
			
		} else if (CLICK_FLAG.equals(reqType) && TEST_SUV.equals(suv)) {
			
			return "hbclick";
			
		} else if (DISPLAY_FLAG.equals(reqType) && NEWS_TYPE.equals(adType)) {
			
			return "newsdisplay";
			
		} else if (CLICK_FLAG.equals(reqType) && NEWS_TYPE.equals(adType)) {
			
			return "newsclick";
			
		} else if (DISPLAY_FLAG.equals(reqType) && adTypeValid(adType)) {
			
			return "addisplay";
			
		} else if (CLICK_FLAG.equals(reqType) && adTypeValid(adType)) {
			
			return "adclick";
			
		} else if (reqType.equals(REACH_FLAG)) {
			
			return "reach";
			
		} else {
			return "unknownLogType";
		}
	}
	
	private static boolean adTypeValid(String adType) {
		if ("1".equals(adType) || "3".equals(adType) || "4".equals(adType)
				|| "null".equalsIgnoreCase(adType) || "0".equals(adType)) {
			return true;
		}
		return false;
	}
	
	public static String makeUserId(List<String> strs) {
		String str = strs.get(indexOf("YYID"));
		if (Util.isNotBlank(str)) return str;
		str = strs.get(indexOf("SUV"));
		if (Util.isNotBlank(str)) return str;
		str = strs.get(indexOf("USERIP"));
		String agent = strs.get(indexOf("USERAGENT"));
		if (Util.isNotBlank(str)) {
			String s = Util.isNotBlank(agent) ? str + agent : str;
			s = Util.getMD5(s);
			if (s == null) return null;
			else return s;
		}
		return null;
	}
	
	public static byte[] serilize(AdInfoOperation adinfo) throws Exception {
		TSerializer serializer = new TSerializer(new TBinaryProtocol.Factory());
		try {
			byte[] byteArray = serializer.serialize(adinfo);
			return byteArray;
		} catch (TException e) {
			throw new Exception("Serilizer: Serilize error");
		}
	
	}

}
