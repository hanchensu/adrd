package com.sohu.adrd.data.sessionlog.plugin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.thrift.protocol.TType;

import com.sohu.adrd.data.common.AdrdDataUtil;
import com.sohu.adrd.data.common.FormatResult;
import com.sohu.adrd.data.common.LogSchema;
import com.sohu.adrd.data.common.Util;
import com.sohu.adrd.data.sessionlog.thrift.operation.CountinfoOperation;
import com.sohu.adrd.data.sessionlog.thrift.operation.CountinfoOperation._Fields;

public class CountinfoMaker {
	private static Object[] defaults = new Object[LogSchema.COUNTINFO_SCHEMA.length];
	private static boolean[] required = new boolean[LogSchema.COUNTINFO_SCHEMA.length];
	private static String[] thriftName = { "logTime", "logLevel", "adId",
			"adpId", "adPos", "adpX", "adpY", "adType", "browser", "clickX",
			"clickY", "contentUrl", "ext", "freq", "getUrl", "impressionId",
			"latency", "monitorKey", "os", "refer", "region", "reqType",
			"resolution", "supportFlash", "suv", "timestamp", "turn", "userAgent", "userIp",
			"yyId",  "e", "c", "pageId", "advertiserId",
			"jsVersion", "bidPrice", "bidPrice2", "bidType", "bidType2", "ctr",
			"ctr2", "eCPM", "eCPM2", "adgroupMK", "advertiserIdMK", "adScore",
			"campaignIdMK", "edContent", "edStatus", "lineIdMK", "materialMK" };

	private static Verifier[] verifiers = new Verifier[LogSchema.COUNTINFO_SCHEMA.length];
	static {

		defaults[indexOf("ADPOS")] = 0;
		defaults[indexOf("ADP_X")] = -1;
		defaults[indexOf("ADP_Y")] = -1;
		defaults[indexOf("BROWSER")] = "0";
		defaults[indexOf("CLICK_X")] = -1;
		defaults[indexOf("CLICK_Y")] = -1;
		defaults[indexOf("FREQ")] = 1;
		defaults[indexOf("LATENCY")] = 0L;
		defaults[indexOf("OS")] = "0";
		defaults[indexOf("REGION")] = "965011170131968";
		defaults[indexOf("TURN")] = "0";
		defaults[indexOf("SUPPORT_FLASH")] = "1";
		defaults[indexOf("e")] = 0.0;
		defaults[indexOf("c")] = 0.0;
		defaults[indexOf("CTR")] = 0.0;
		defaults[indexOf("CTR2")] = 0.0;
		defaults[indexOf("eCPM")] = 0.0;
		defaults[indexOf("eCPM2")] = 0.0;

//		required[indexOf("ADTYPE")] = true;
		required[indexOf("TIME")] = true;
		required[indexOf("USERIP")] = true;
		
		for(int i = 0; i < verifiers.length; i++) {
			
			verifiers[i] = new Verifier() {
				
				public boolean isValid(Object... objects) {
					return true;
				}
			};
		}

		verifiers[indexOf("ADID")] = new StrlenVerifier(256);
		verifiers[indexOf("ADPID")] = verifiers[indexOf("YYID")] = new StrlenVerifier(32);
		verifiers[indexOf("ADTYPE")] = new EnumVerifier("0","1","2","3","4","5","6","null");
		verifiers[indexOf("ADPOS")] = new RangeVerifier(0, 20);
		verifiers[indexOf("ADP_X")] = new RangeVerifier(0, 2000);
		verifiers[indexOf("ADP_Y")] = new RangeVerifier(0, 65535);
		verifiers[indexOf("BROWSER")] = new Verifier() {
			public boolean isValid(Object... objects) {
				String value = (String) objects[0];
				if (value.length() <= 64 && !value.contains(" ")) {
					return true;
				}
				return false;
			}
		};
		verifiers[indexOf("CLICK_X")] = verifiers[indexOf("CLICK_Y")] = new RangeVerifier(0, 2000);
		verifiers[indexOf("CONTENT_URL")] = new StrlenVerifier(2048);
		verifiers[indexOf("FREQ")] = new RangeVerifier(-1, 256);
		verifiers[indexOf("GETURL")] = new StrlenVerifier(1024);
		verifiers[indexOf("IMPRESSIONID")] = new StrlenVerifier(34);
		verifiers[indexOf("LATENCY")] = new RangeVerifier(0L, 100000L);
		verifiers[indexOf("OS")] = new Verifier() {
			public boolean isValid(Object... objects) {
				
				String value = (String) objects[0];
				if (value.length() <= 128 && !value.contains(":")
						&& !value.contains("\t") && !value.contains(" ")) {
					return true;
				}
				return false;
			}
		};
		verifiers[indexOf("REFFER")] = new StrlenVerifier(128);
		verifiers[indexOf("REQTYPE")] = new EnumVerifier("view","click","err","arrive","reach");
		verifiers[indexOf("SUPPORT_FLASH")] = new EnumVerifier("0","1");
		verifiers[indexOf("SUV")] = new StrlenVerifier(64);
		verifiers[indexOf("USERAGENT")] = new StrlenVerifier(1024);
		verifiers[indexOf("JS_VERSION")] = new StrlenVerifier(8);
//		verifiers[indexOf("BidType")] = verifiers[indexOf("BidType2")] = new EnumVerifier(1,2);

	}

	public static int indexOf(String key) {
		return Arrays.asList(LogSchema.COUNTINFO_SCHEMA).indexOf(key);
	}

	public static CountinfoOperation makeCountinfo(String log) {
		FormatResult fr = AdrdDataUtil.format(log, LogSchema.COUNTINFO_SCHEMA);
		return makeCountinfo(fr);
	}
	
	public static CountinfoOperation makeCountinfo(FormatResult formatResult) {
		return makeCountinfo(formatResult.strs);
	}

	public static CountinfoOperation makeCountinfo(List<String> strs) {

		CountinfoOperation countinfo = new CountinfoOperation();
		
		countinfo.setRepeat(1);

		if (strs != null) {
			for (int i = 0; i < LogSchema.COUNTINFO_SCHEMA.length; i++) {

				_Fields field = CountinfoOperation._Fields
						.findByName(thriftName[i]);

				byte type = CountinfoOperation.metaDataMap.get(field).valueMetaData.type;
				String valueStr = strs.get(i);
				if (Util.isBlank(valueStr)) {
					if (defaults[i] != null) {
						countinfo.setFieldValue(field, defaults[i]);
					} else if (required[i]) {
						setError(countinfo,i);
					}
				} else if (type == TType.STRING) {
					countinfo.setFieldValue(field, valueStr);
					if(!verifiers[i].isValid(valueStr)) {
						setError(countinfo,i);
					} 
				} else if (type == TType.I32) {
					try {
						int value = Integer.parseInt(valueStr);
						countinfo.setFieldValue(field, value);
						if(!verifiers[i].isValid(value)) {
							setError(countinfo,i);
						} 
					} catch (NumberFormatException e) {
						setError(countinfo,i);
					}
				} else if (type == TType.I64) {
					try {
						long value = Long.parseLong(valueStr);
						countinfo.setFieldValue(field, value);
						if(!verifiers[i].isValid(value)) {
							setError(countinfo,i);
						} 
					} catch (NumberFormatException e) {
						setError(countinfo,i);
					}
				} else if (type == TType.DOUBLE) {
					try {
						double value = Double.parseDouble(valueStr);
						countinfo.setFieldValue(field, value);
						if(!verifiers[i].isValid(value)) {
							setError(countinfo,i);
						}
					} catch (NumberFormatException e) {
						setError(countinfo,i);
					}
				}

			}
		}
		crossFieldVerify(countinfo);
		return countinfo;

	}
	
	public static void crossFieldVerify(CountinfoOperation countinfo) {
		if("2".equals(countinfo.getAdType()) && !countinfo.isSetAdId()) {
			setError(countinfo, 63);
		}
	}
	
	
	public static void setError(CountinfoOperation countinfo, int index) {
		long status = countinfo.getStatusCode();
		countinfo.setStatusCode(status | (1L << index));
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File("D:/worktmp/countinfo.txt")));
		String str;
		while ((str = br.readLine()) != null) {
			System.out.println(str);
		
			System.out.println(AdrdDataUtil.format(str, LogSchema.COUNTINFO_SCHEMA).errorcode);
			CountinfoOperation countinfo = CountinfoMaker.makeCountinfo(str);
			System.out.println(AdrdDataUtil.prettyErr(countinfo.statusCode, LogSchema.COUNTINFO_SCHEMA));

			
		}
		
		
		br.close();
	}

}
