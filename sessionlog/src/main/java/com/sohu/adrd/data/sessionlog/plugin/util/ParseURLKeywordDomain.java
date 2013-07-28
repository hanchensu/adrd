package com.sohu.adrd.data.sessionlog.plugin.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseURLKeywordDomain {
	
	private static String RegStr = "(.+?(google.com.+?q=)|(sogou.+?query=)|(yahoo.com.+?q=)|(yahoo.+?[\\?|&]p=)|(openfind.+?query=)|(google.+?q=)|(lycos.+?query=)|(onseek.+?keyword=)|(search.tom.+?word=)|"
	+ "(search.qq.com.+?word=)|(zhongsou.com.+?word=)|(search.msn.com.+?q=)|(yisou.com.+?p=)|(sina.+?word=)|(sina.+?query=)|(sina.+?_searchkey=)|(sohu.+?word=)|"
	+ "(sohu.+?key_word=)|(sohu.+?query=|163.+?q=)|(baidu.+?wd=)|(baidu.+?kw=)|(baidu.+?word=)|(3721.com.+?p=)|(Alltheweb.+?q=)|(soso.+?w=)|(115.+?q=)|(youdao.+?q=)|"
	+ "(bing.+?q=)|(114.+?kw=)|(p.zhongsou.com.+?w=)|(www.qihoo.com.+?kw=))([^?&]*)";
	private static Pattern Patt = Pattern.compile(RegStr);

	public static String[] getKeywordAndDomain(String url){
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
			return result;
		}
		result[0] = "";
		return result;
	}

	public static String getDomain(String url) {
		String domain = "";
		int spos = url.indexOf("//");
		int epos = url.indexOf('/', spos + 2);
		domain = url.substring(spos + 2, epos);
		return domain;
	}
	
	public static String getEncode(String src) {
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
	

	public static boolean isChinese(char c) {
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

	public static boolean hasChinese(String str) {
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (isChinese(ch)) return true;
		}
		return false;
	}

	public static boolean DecoderError(String str) {
		for (int j = 0; j < str.length(); j++) {
			char ch = str.charAt(j);
			if (isChinese(ch)) continue;
			if (ch >= 0x00 && ch <= 0x7F) continue;
			return true;
		}
		return false;
	}
	
	
	public static void main(String args[]) throws UnsupportedEncodingException {
		
		String[] test = getKeywordAndDomain("http://zhaoxuan.sohu.com/report/index.html?key_word=%E7%94%B1%E6%9C%AC%E6%B5%8B");
		System.out.println(test == null);
		System.out.println(test[0]);
		System.out.println(test[1]);
		
//		String abc = "2005%E5%B9%B4%E6%98%A5%E8%8A%82%E6%99%9A%E4%BC%9A%E4%BA%89%E5%A5%87%E6%96%97%E8%89%B3%E2%80%94%E6%B5%81%E8%A1%8C%E9%A3%8E";
//		String encodeStr = "gb2312";
//		String result = "";
//		
//		result = getEncode(abc);
//		System.out.println(result);
		
//		result = URLDecoder.decode(abc, encodeStr);
//		System.out.println(encodeStr+": "+result);
//		
//		encodeStr = "utf-8";
//		result = URLDecoder.decode(abc, encodeStr);
//		System.out.println(encodeStr+": "+result);
//		for(int i = 0; i < result.length(); i++) {
//			System.out.println(result.charAt(i) +"__" +(long)result.charAt(i));
//		}
//		
//		encodeStr = "utf-16";
//		result = URLDecoder.decode(abc, encodeStr);
//		System.out.println(encodeStr+": "+result);
		
//		String regStr = "\\t+";
//		Pattern pattern = Pattern.compile(regStr);
//		System.out.println(fix("abc		asdasd	¸ïÃüÉíÌå"));
		
		
	}
	
	
}