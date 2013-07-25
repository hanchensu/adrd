package com.sohu.ad.data.sessionlog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.thrift.protocol.TType;

import com.sohu.ad.data.common.AdrdDataUtil;
import com.sohu.ad.data.common.FormatResult;
import com.sohu.ad.data.common.LogSchema;
import com.sohu.ad.data.common.Util;
import com.sohu.ad.data.thrift.operation.CountinfoOperation;
import com.sohu.ad.data.thrift.operation.CountinfoOperation._Fields;

public class CountinfoMaker {
	private static Object[] defaults = new Object[LogSchema.COUNTINFO_SCHEMA.length];
	private static boolean[] required = new boolean[LogSchema.COUNTINFO_SCHEMA.length];
	private static String[] thriftName = { "logTime", "logLevel", "adId",
			"adpId", "adPos", "adpX", "adpY", "adType", "browser", "clickX",
			"clickY", "contentUrl", "ext", "freq", "getUrl", "impressionId",
			"latency", "monitorKey", "os", "refer", "region", "reqType",
			"resolution", "suv", "timestamp", "turn", "userAgent", "userIp",
			"yyId", "supportFlash", "e", "c", "pageId", "advertiserId",
			"jsVersion", "bidPrice", "bidType", "bidPrice2", "bidType2", "ctr",
			"ctr2", "eCPM", "eCPM2", "adgroupMK", "advertiserIdMK", "adScore",
			"campaignIdMK", "edContent", "edStatus", "lineIdMK", "materialMK" };

	private static Verifier[] verifiers = {};
	static {
		defaults[indexOf("ADPOS")] = 0;
		defaults[indexOf("ADP_X")] = -1;
		defaults[indexOf("ADP_Y")] = -1;
		defaults[indexOf("BROWSER")] = "0";
		defaults[indexOf("CLICK_X")] = -1;
		defaults[indexOf("CLICK_Y")] = -1;
		defaults[indexOf("FREQ")] = 1;
		defaults[indexOf("LATENCY")] = 0;
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

		required[indexOf("ADTYPE")] = true;
		required[indexOf("TIME")] = true;
		required[indexOf("USERIP")] = true;

	}

	public static int indexOf(String key) {
		return Arrays.asList(LogSchema.COUNTINFO_SCHEMA).indexOf(key);
	}

	public static CountinfoOperation makeCountinfo(String log) {
		FormatResult fr = AdrdDataUtil.format(log, LogSchema.COUNTINFO_SCHEMA);
		return makeCountinfo(fr);
	}

	public static CountinfoOperation makeCountinfo(FormatResult formatResult) {

		CountinfoOperation countinfo = new CountinfoOperation();

		List<String> strs = formatResult.strs;
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
						countinfo.setStatusCode(123);
					}
				} else if (type == TType.STRING) {
					countinfo.setFieldValue(field, valueStr);
				} else if (type == TType.I32) {
					try {
						int value = Integer.parseInt(valueStr);
						countinfo.setFieldValue(field, value);
					} catch (NumberFormatException e) {
						countinfo.setStatusCode(123);
					}
				} else if (type == TType.I64) {
					try {
						long value = Long.parseLong(valueStr);
						countinfo.setFieldValue(field, value);
					} catch (NumberFormatException e) {
						countinfo.setStatusCode(123);
					}
				} else if (type == TType.DOUBLE) {
					try {
						double value = Double.parseDouble(valueStr);
						countinfo.setFieldValue(field, value);
					} catch (NumberFormatException e) {
						countinfo.setStatusCode(123);
					}
				}

			}
		}
		return countinfo;

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(
				"D:/worktmp/countinfo.txt")));
		String str;
		while ((str = br.readLine()) != null) {
			System.out.println(str);
			System.out.println(makeCountinfo(str).latency);
		}
		br.close();
//		for (int i = 0; i < LogSchema.COUNTINFO_SCHEMA.length; i++) {
//			System.out.println(LogSchema.COUNTINFO_SCHEMA[i] + ":"
//					+ thriftName[i]);
//		}
//		int i = 2;
//		System.out.println(CountinfoOperation._Fields.findByName(thriftName[i])
//				+ " " + LogSchema.COUNTINFO_SCHEMA[i]);

	}

}
