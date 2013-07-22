package com.sohu.ad.data.sessionlog.util;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.BytesWritable;



public class Util {
	
	public static boolean isBlank(String str) {
		if (str == null || str.length() == 0 || str.trim().length() == 0)
			return true;
		else
			return false;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}
	
	public static String getMD5(String s) {
		ThreadLocal<MessageDigest> Md5s = new ThreadLocal<MessageDigest>();
		try {
			MessageDigest md5 = Md5s.get();
			if (md5 == null) {
				md5 = MessageDigest.getInstance("MD5");
				Md5s.set(md5);
			}
			byte[] byteArray = s.getBytes("ISO-8859-1");
			byte[] md5Bytes = md5.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16) hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		} catch (Exception e) {
			return null;
		}
	}
}
