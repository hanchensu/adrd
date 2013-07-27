package com.sohu.adrd.data.sessionlog.plugin;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sohu.adrd.data.sessionlog.util.ExtractorEntry;
import com.sohu.adrd.data.sessionlog.util.Formator;

import sessionlog.mapreduce.FormatResult;
import sessionlog.util.Util;

public class PvlogFormator implements Formator {
    
	private long lastTimeStamp = -1;
	private List<String> result = new ArrayList<String>();
	
	public FormatResult format(String str) {
		if (Util.isBlank(str)) return new FormatResult(null, "err1");
		result.clear();
		String yyid = null;
		String suv = null;
		String ip = null;
		String useragent = null;
		String region = null;
		String timeStr = null;
		String refer = null;
		String url = null;
		int num = 0;
		int pos = 0;
		int spos = 0;
		String substr = "";
		while (true) {
			pos = str.indexOf(" ", pos);
			if (pos == -1) {
				substr = str.substring(spos, spos + 1);
				num++;
				break;
			} else {
				if (pos == 0) substr = "-";
				else substr = str.substring(spos, pos);
				spos = pos + 1;
				num++;
			}
			if (spos == str.length()) break;
			char ch = str.charAt(spos);
			if (ch == '[') {
				pos = str.indexOf("]", spos + 1) + 1;
			} else if (ch == '"') pos = str.indexOf('"', spos + 1) + 1;
			else pos = pos + 1;
			switch (num) {
			case 1:
				suv = substr;
				break;
			case 2:
				timeStr = substr.substring(1, substr.length() - 1);
				break;
			case 3:
				ip = substr;
				break;
			case 4:
				refer = substr.substring(1, substr.length() - 1);
				break;
			case 5:
				try {
					url = substr.substring(1, substr.length() - 1);
				} catch(StringIndexOutOfBoundsException e) {
					url="";
				}
				break;
			case 7:
				region = substr;
				break;
			case 9:
				yyid = substr;
				break;
			case 10:
				try { 
					useragent = substr.substring(1, substr.length() - 1);
				} catch(StringIndexOutOfBoundsException e) {
					useragent="";
				}
				break;
			default:
				;
			}
		}
		if ("-".equals(yyid)) {
			yyid = null;
		}
		result.add(yyid);
		if ("-".equals(suv)) suv = null;
		result.add(suv);
		if ("-".equals(ip)) ip = null;
		result.add(ip);
		if ("-".equals(useragent) || "".equals(useragent)) useragent = null;
		result.add(useragent);
		if ("-".equals(region)) region = null;
		result.add(region);
		if ("-".equals(timeStr) || "".equals(timeStr)) timeStr = null;
		if (timeStr == null || timeStr.length() == 0) result.add(null);
		else {
			SimpleDateFormat sdf = Util.usFormat();
			Date date = null;
			try {
				date = sdf.parse(timeStr);
				long lTime = date.getTime() / 1000;
				lastTimeStamp = lTime;
				result.add(String.valueOf(lTime));
			} catch (ParseException e) {
				long lTime = lastTimeStamp;
				result.add(String.valueOf(lTime));
			}
		}
		if ("".equals(refer) || "-".equals(refer)) result.add(null);
		else {
			int start = refer.indexOf("r?=");
			if (start != -1) {
				int end = refer.indexOf(" ", start);
				refer = refer.substring(start + 3, end);
				if (!refer.equals("") && !refer.equals("-") && refer != null) result.add(refer);
				else result.add(null);
			} else result.add(null);
		}
		if (!"".equals(url) && !"-".equals(url)) result.add(url);
		else {
			result.add(null);
		}
		return new FormatResult(result, "0"); 
	}

	@Override
	public String getMark() {
		return "Pv_";
	}
	
	
	
//	public static void main(String args[]) throws Exception {
//
//		PvlogFormator formator = new PvlogFormator();
//		BufferedReader br = null;
//		InputStream input = new FileInputStream("D:/adlog.txt");
//		br = new BufferedReader(new InputStreamReader(input));
//		String str;
//		int count = 0;
//		while ((str = br.readLine()) != null) {
//
//			List<String> res = formator.format(str);
//
//			
//			if (res != null) {
//				System.out.println(str+"\n");
//				System.out.println(res.size());
//				for (int i = 0; i < res.size(); i++) {
//					System.out.println(res.get(i) + " ");
//				}
//				System.out.println();
//				System.out.println();
//				System.out.println();
//				System.out.println();
//				
//				PvlogExtractor ex = new PvlogExtractor();
//				
//				List<ExtractorEntry> entries = null;
//				entries = ex.extract(res);
//				
//				
//				if (entries == null || entries.size() == 0) {
//					if(entries == null) {
//					System.out.println("wtf");
//					}
//					if(entries.size() == 0) {
//						System.out.println("wtf2");
//					}
//				}
//				
//			}
//		}
//		
//	}

}
