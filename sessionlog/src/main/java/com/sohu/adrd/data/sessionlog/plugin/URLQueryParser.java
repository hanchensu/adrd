package com.sohu.adrd.data.sessionlog.plugin;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLQueryParser {
	
	private static String RegStr = "(.+?(google.com.+?q=)|(sogou.+?query=)|(yahoo.com.+?q=)|(yahoo.+?[\\?|&]p=)|(openfind.+?query=)|(google.+?q=)|(lycos.+?query=)|(onseek.+?keyword=)|(search.tom.+?word=)|"
	+ "(search.qq.com.+?word=)|(zhongsou.com.+?word=)|(search.msn.com.+?q=)|(yisou.com.+?p=)|(sina.+?word=)|(sina.+?query=)|(sina.+?_searchkey=)|(sohu.+?word=)|"
	+ "(sohu.+?key_word=)|(sohu.+?query=|163.+?q=)|(baidu.+?wd=)|(baidu.+?kw=)|(baidu.+?word=)|(3721.com.+?p=)|(Alltheweb.+?q=)|(soso.+?w=)|(115.+?q=)|(youdao.+?q=)|"
	+ "(bing.+?q=)|(114.+?kw=)|(p.zhongsou.com.+?w=)|(www.qihoo.com.+?kw=))([^?&]*)";
	private static Pattern Patt = Pattern.compile(RegStr);

	public static String getQuery(String url){
		String[] result = new String[2];
		Matcher Mat = Patt.matcher(url.trim());
		if (Mat.find()) {
			result[0] = Mat.group(Mat.groupCount()).toString();
			result[1] = getDomain(url);
		} else {
			return null;
		}
		if (result[0] != null && !result[0].equals("")) {
			result[0] = getEncode(result[0]);
			return result[0];
		}
		result[0] = "";
		return result[0];
	}

	private static String getDomain(String url) {
		String domain = "";
		int spos = url.indexOf("//");
		int epos = url.indexOf('/', spos + 2);
		domain = url.substring(spos + 2, epos);
		return domain;
	}
	
	private static String getEncode(String src) {
		String result;
		if (src.contains("query") && src.contains("|")) {
			src = src.substring(0, src.indexOf("|"));
		}
		try {
			result = URLDecoder.decode(src, "utf-8");
			if (!DecoderError(result))
				return fix(result);
		} catch (UnsupportedEncodingException e) {
			try {
				result = URLDecoder.decode(src, "utf-16");
				if (!DecoderError(result))
					return fix(result);
			} catch (UnsupportedEncodingException e1) {
				try {
					result = URLDecoder.decode(src, "gb2312");
					if (!DecoderError(result))
						return fix(result);
				} catch (UnsupportedEncodingException e2) {
					return "";
				}	
			}
		}
		return "";
	}
	
	private static String fix(String src) {
		if(src == null || src.length() == 0) {
			return "";
		}

		String regStr = "[\\p{Print}]";

		StringBuilder sb = new StringBuilder();
		
		for (int j = 0; j < src.length(); j++) {
			char ch = src.charAt(j);
			if (isChinese(ch)) {
				sb.append(ch);
				continue;
			}
			if (ch >= 0x00 && ch <= 0x7F && src.substring(j, j+1).matches(regStr)) {
				sb.append(ch);
			} else {
				sb.append("\t");
			}
		}
		return sb.toString().trim().replaceAll("\\t+", " ");
	}
	

	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
		|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
		|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
		|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
		|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
		|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	private static boolean hasChinese(String str) {
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (isChinese(ch)) return true;
		}
		return false;
	}

	private static boolean DecoderError(String str) {
		for (int j = 0; j < str.length(); j++) {
			char ch = str.charAt(j);
			if (isChinese(ch)) continue;
			if (ch >= 0x00 && ch <= 0x7F) continue;
			return true;
		}
		return false;
	}
	
	
	public static void main(String args[]) throws UnsupportedEncodingException {

		
		String testUrl = "https://www.google.com.hk/#newwindow=1&safe=strict&q=apache+pig+zebra&oq=apache+pig+zebra&gs_l=serp.3...2945.7088.0.7355.16.12.0.0.0.0.0.0..0.0...0.0.0..1c.1.15.serp.oSv-QFEY2So&fp=1&biw=1600&bih=775&bav=on.2,or.&cad=b";
		String query = URLQueryParser.getQuery(testUrl);
		System.out.println(query);
		
		
	}
	
	
}