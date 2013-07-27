package com.sohu.adrd.data.sessionlog.mapreduce;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.pig.parser.AliasMasker.query_return;

import com.sohu.adrd.data.sessionlog.plugin.adinfo.AdInfoDivider;
import com.sohu.adrd.data.sessionlog.util.ExtractorEntry;
import com.sohu.adrd.data.sessionlog.util.Formator;

import sessionlog.mapreduce.FormatResult;
import sessionlog.op.AdInfoOperation;
import sessionlog.util.Util;

public class AdinfoFormator implements Formator {

	private List<String> result = new ArrayList<String>();

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

	public static final String[] SCHEMA = { "ADID", "ADPID", "ADPOS", "ADP_X",
			"ADP_Y", "ADTYPE", "BROWSER", "CLICK_X", "CLICK_Y", "CONTENT_URL",
			"EXT", "FREQ", "GETURL", "IMPRESSIONID", "LATENCY", "MONITORKEY",
			"OS", "REFFER", "REGION", "REQTYPE", "RESOLUTION", "SUPPORT_FLASH",
			"SUV", "TIME", "TURN", "USERAGENT", "USERIP", "YYID", "e", "c",
			"PAGEID", "ADVERTISERID", "JS_VERSION", "BidPrice", "BidPrice2",
			"BidType", "BidType2", "CTR", "CTR2", "eCPM", "eCPM2",
			"ADGROUPID_MK", "ADVERTISERID_MK", "AdScore", "CAMPAIGNID_MK",
			"ED_CONTENT","ED_STATUS", "LINEID_MK", "MATERIAL_MK"};

	public static final int TOTAL_COLUMN = SCHEMA.length + 1; // -2 for logtime,
																// -1 for status
																// code

	public boolean posValid(int index, String field) {
		if (SCHEMA[index].equals(field))
			return true;
		return false;

	}

	private String getLogtime(String str) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss,SSS");
		Date date;
		date = format.parse(str);
		return Long.toString(date.getTime());
	}

	public FormatResult format(String str) {
		if (Util.isBlank(str)) // filter illegal
			return new FormatResult(null, "err1");

		result.clear();
		for (int i = 0; i < TOTAL_COLUMN; i++)
			result.add(null);

		try {
			String logtime = getLogtime(str.substring(0, 23));
			result.set(result.size() - 1, logtime);
		} catch (ParseException e) {
			return new FormatResult(null, "err2");
		}

		// String regex =
		// "\"([^\"]+)\"[\\p{Blank}]*:[\\p{Blank}]*\"?([^\"]+)\"?[\\p{Blank}]*[,\\}]";
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

			// if(!posValid(index, key)){
			// return null;
			// }

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

			int index = AdInfoDivider.indexOf(key);
			if (index == -1)
				continue; // 0.2.7
				// return new FormatResult(null, "err4"); 0.2.6

			if (Util.isNotBlank(value)) {
				result.set(index, value);
			}

		}

		return new FormatResult(result, "0");
	}

	public static void newAttr(String str) {
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

			int index = AdInfoDivider.indexOf(key);
			if (index == -1)
				System.out.println(key);
		}

	}

	public static void strAttr(String str) {
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

			int index = AdInfoDivider.indexOf(key);
			if (index == -1)
				System.out.print("\"" + key + "\",");
		}

	}

	@Override
	public String getMark() {
		return "Adinfo_";
	}

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File(
				"D:/worktmp/countinfo.txt")));
		String str;
		
//		while ((str = br.readLine()) != null) {
//			newAttr(str);
//			System.out.println("-----------------");
//		}
//		br.close();
//
//
//
//		while ((str = br.readLine()) != null) {
//			strAttr(str);
//			System.out.println("-----------------");
//		}
//		br.close();
		
		while ((str = br.readLine()) != null) {
			System.out.println(str);
			FormatResult fr = new AdinfoFormator().format(str);
			fr.strs.add("0");
			System.out.println(new AdinfoExtractor().extract(fr.strs).errorcode);
//			System.out.println(fr.strs.size());
			System.out.println(fr.errorcode);
//			System.out.println(AdinfoFormator.TOTAL_COLUMN+1);
//			System.out.println("--------"+fr.strs.size()+"---------");
			for(String att:fr.strs) {
				System.out.println(att);
			}
		}
		br.close();
	}

}
