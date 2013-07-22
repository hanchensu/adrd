package com.sohu.ad.data.common;

import java.util.List;

public class KeyFormator {
	public static String format(List<String> names, List values, String projection) {
		if(values == null || projection == null || values.size() == 0 || projection.length() == 0 || values.size()!=projection.length()) {
			return null;
		}
		if(names == null || names.size() == 0 || names.size()!=projection.length()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<");
		for(int i = 0; i < names.size(); i++) {
			sb.append(names.get(i)+":");
			if(projection.charAt(i)=='1') {
				sb.append(values.get(i));
			} else {
				sb.append("-");
			}
			sb.append(",");
		}
		return sb.substring(0, sb.length()-1) + ">";
	}
}
