package com.sohu.adrd.data.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.pig.parser.QueryParser.null_check_cond_return;

public class AdrdDataHive {
	public static final String NULL_STR = "_NULL_";
	public static final String FIELD_DELIMITER = "\001";
	public static final String LIST_DELIMITER = "\002";
	public static final String MAP_DELIMITER = "\003";
	
	/**
	 * 
	 * @PotentialProblem value == null OR Util.isBlank(value)?
	 * 
	 */
	public static String toHiveStr(String value) {
		if (Util.isBlank(value))
			return NULL_STR;
		return value;

	}

	public static String toHiveStr(List values,String delimeter) {
		if(values == null || values.isEmpty()) 
			return NULL_STR;
		StringBuilder hiveStr=new StringBuilder();
		for(Object value: values) {
			hiveStr.append(toHiveStr(value == null? null:String.valueOf(value))+delimeter);
		}
		return hiveStr.toString().substring(0,hiveStr.length() - delimeter.length());
		
	}

	public static void main(String[] args) {
		List<String> test = new ArrayList<String>();
		test.add("abc");
		test.add(null);
//		test.add("");
//		String abc = "asd";
		System.out.println(toHiveStr(null,FIELD_DELIMITER));
	}
}
