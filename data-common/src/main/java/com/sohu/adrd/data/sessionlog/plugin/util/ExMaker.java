package com.sohu.adrd.data.sessionlog.plugin.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.thrift.protocol.TType;


import com.sohu.adrd.data.common.AdrdDataUtil;
import com.sohu.adrd.data.common.FormatResult;
import com.sohu.adrd.data.common.LogSchema;
import com.sohu.adrd.data.common.Util;
import com.sohu.adrd.data.sessionlog.thrift.operation.ExOperation;
import com.sohu.adrd.data.sessionlog.thrift.operation.ExOperation._Fields;;

public class ExMaker {
	private static Object[] defaults = new Object[LogSchema.EX_SCHEMA.length];
	private static boolean[] required = new boolean[LogSchema.EX_SCHEMA.length];
	private static String[] thriftName = { "logTime", "logLevel", "dspID",
			"bidID", "impID", "monitorKey", "suv", "adID", "adpID", "adSize",
			"adUrl", "bidFloor", "bidPrice", "secondPrice", "status", "latency",
			"logType"};

	private static Verifier[] verifiers = new Verifier[LogSchema.EX_SCHEMA.length];
	static {

		defaults[indexOf("DSPID")] = 0;
		defaults[indexOf("BIDFLOOR")] = 0;
		defaults[indexOf("LATENCY")] = -1L;
		defaults[indexOf("STATUS")] = "SUCCESS";

		required[indexOf("DSPID")] = true;
		required[indexOf("BIDID")] = true;
		required[indexOf("BIDFLOOR")] = true;
		required[indexOf("STATUS")] = true;
		required[indexOf("LATENCY")] = true;
		required[indexOf("LOGTYPE")] = true;
		
		for(int i = 0; i < verifiers.length; i++) {
			verifiers[i] = new Verifier() {
				public boolean isValid(Object... objects) {
					return true;
				}
			};
		}
		
		verifiers[indexOf("DSPID")] = new RangeVerifier(0, Integer.MAX_VALUE);
		verifiers[indexOf("BIDID")] = new StrlenVerifier(1, 63);
		verifiers[indexOf("IMPID")] = new StrlenVerifier(1, 255);
		verifiers[indexOf("MONITORKEY")] = new StrlenVerifier(25, 25);
		
		verifiers[indexOf("SUV")] = new StrlenVerifier(16, 16);
		verifiers[indexOf("ADID")] = new StrlenVerifier(1, 63);
		verifiers[indexOf("ADPID")] = new StrlenVerifier(1, 63);
		verifiers[indexOf("ADSIZE")] = new StrlenVerifier(0, 99999999);
		
	
		verifiers[indexOf("BIDFLOOR")] = new RangeVerifier(0, Integer.MAX_VALUE);
		verifiers[indexOf("BIDPRICE")] = new RangeVerifier(1, Integer.MAX_VALUE);
		verifiers[indexOf("SECONDPRICE")] = new RangeVerifier(1, Integer.MAX_VALUE);
		verifiers[indexOf("STATUS")] = new EnumVerifier("SUCCESS","NOTEXIST","NOTAUDIT","SIZEERROR","TIMEOUT","LOWPRICE","OTHER");
		
		verifiers[indexOf("LATENCY")] = new RangeVerifier(0L, Long.MAX_VALUE);
		verifiers[indexOf("LOGTYPE")] = new EnumVerifier("WIN","BID","GIVEUP","SEND");
		
		

	}

	public static int indexOf(String key) {
		return Arrays.asList(LogSchema.EX_SCHEMA).indexOf(key);
	}

	public static ExOperation makeEx(String log) {
		FormatResult fr = AdrdDataUtil.format(log, LogSchema.EX_SCHEMA);
		return makeEx(fr);
	}
	
	public static ExOperation makeEx(FormatResult formatResult) {
		return makeEx(formatResult.strs);
	}

	public static ExOperation makeEx(List<String> strs) {

		ExOperation ex = new ExOperation();
		
		ex.setRepeat(1);
		
		if (strs != null) {
			for (int i = 0; i < LogSchema.EX_SCHEMA.length; i++) {

				_Fields field = ExOperation._Fields
						.findByName(thriftName[i]);

				byte type = ExOperation.metaDataMap.get(field).valueMetaData.type;
				String valueStr = strs.get(i);
				if (Util.isBlank(valueStr)) {
					if (defaults[i] != null) {
						ex.setFieldValue(field, defaults[i]);
					} else if (required[i]) {
						setError(ex,i);
					}
				} else if (type == TType.STRING) {
					if(!verifiers[i].isValid(valueStr)) {
						ex.setFieldValue(field, valueStr);
					} else {
						setError(ex,i);
					}
				} else if (type == TType.I32) {
					try {
						int value = Integer.parseInt(valueStr);
						if(!verifiers[i].isValid(value)) {
							ex.setFieldValue(field, value);
						} else {
							setError(ex,i);
						}
					} catch (NumberFormatException e) {
						setError(ex,i);
					}
				} else if (type == TType.I64) {
					try {
						long value = Long.parseLong(valueStr);
						if(!verifiers[i].isValid(value)) {
							ex.setFieldValue(field, value);
						} else {
							setError(ex,i);
						}
					} catch (NumberFormatException e) {
						setError(ex,i);
					}
				} else if (type == TType.DOUBLE) {
					try {
						double value = Double.parseDouble(valueStr);
						if(!verifiers[i].isValid(value)) {
							ex.setFieldValue(field, value);
						} else {
							setError(ex,i);
						}
					} catch (NumberFormatException e) {
						setError(ex,i);
					}
				}

			}
		}
		crossFieldVerify(ex);
		return ex;
	}
	
	public static void crossFieldVerify(ExOperation ex) {
		if(!"TIMEOUT".equals(ex.getStatus()) || !"GIVEUP".equals(ex.getLogType())){
			if(!ex.isSetSuv()) {
				setError(ex, 63);
			}
		}
		if("WIN".equals(ex.getLogType()) && ex.getDspID()!=0) {
			if(!ex.isSetAdID()) {
				setError(ex, 62);
			}
		}
		if("WIN".equals(ex.getLogType())) {
			if(!ex.isSetAdpID()) {
				setError(ex, 61);
			}
		}
		if("BID".equals(ex.getLogType()) || "WIN".equals(ex.getLogType())) {
			if(!ex.isSetBidPrice()) {
				setError(ex, 60);
			}
		}
		if("WIN".equals(ex.getLogType())) {
			if(!ex.isSetSecondPrice()) {
				setError(ex, 59);
			}
		}
		
		//set timestamp
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss,SSS");
		Date date;
		try {
			date = format.parse(ex.getLogTime());
			ex.setTimestamp(date.getTime() / 1000L);
		} catch (Exception e) {
			ex.setTimestamp(1);
			setError(ex, 58);
		}
		
	}
	
	public static void setError(ExOperation ex, int index) {
		long status = ex.getStatusCode();
		ex.setStatusCode(status | (1L << index));
	}

	public static void main(String[] args) throws IOException {
		
	}

}
