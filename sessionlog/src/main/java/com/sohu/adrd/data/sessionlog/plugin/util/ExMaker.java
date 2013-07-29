package com.sohu.adrd.data.sessionlog.plugin.util;

import java.io.IOException;
import java.util.Arrays;
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
		
		
		verifiers[indexOf("ADID")] = new StrlenVerifier(256);
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
		verifiers[indexOf("REQTYPE")] = new EnumVerifier("view","click","err","arrive","reach");

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
					if(verifiers[i].isValid(valueStr)) {
						ex.setFieldValue(field, valueStr);
					} else {
						setError(ex,i);
					}
				} else if (type == TType.I32) {
					try {
						int value = Integer.parseInt(valueStr);
						if(verifiers[i].isValid(value)) {
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
						if(verifiers[i].isValid(value)) {
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
						if(verifiers[i].isValid(value)) {
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
		return ex;

	}
	
	public static void setError(ExOperation ex, int index) {
		long status = ex.getStatusCode();
		ex.setStatusCode(status | (1L << index));
	}

	public static void main(String[] args) throws IOException {
		
	}

}
