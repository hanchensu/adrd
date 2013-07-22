package com.sohu.ad.data.common;

import java.util.ArrayList;
import java.util.List;

public class AdinfoFormator {
	
	public static FormatResult format(String str,String[] schema) {
		List<String> result = new ArrayList<String>();
		
		if (Util.isBlank(str)) // filter illegal
			return new FormatResult(null, "BlankLine");

		result.clear();
		for (int i = 0; i < schema.length; i++)
			result.add(null);

		try {
			String logtime = str.substring(0, 23);
			result.set(0, logtime);
		} catch (Exception e1) {
			result.set(0, null);
		}

		try {
			String mark = str.substring(24, 29);
			result.set(1, mark);
		} catch (Exception e2) {
			result.set(0, null);
		}

		return Util.jsonFormat(schema, result, str);
	}

}


