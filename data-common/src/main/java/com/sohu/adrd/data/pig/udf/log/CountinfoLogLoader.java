package com.sohu.adrd.data.pig.udf.log;

import java.util.ArrayList;
import java.util.List;


import com.sohu.adrd.data.common.FormatResult;
import com.sohu.adrd.data.common.LogSchema;
import com.sohu.adrd.data.common.Util;

public class CountinfoLogLoader extends LogLoader {
	
	public CountinfoLogLoader(String projection) {
		this.projection = projection;
	}

	public static final String[] SCHEMA = LogSchema.COUNTINFO_SCHEMA;

	public FormatResult format(String str) {
		
		List<String> result = new ArrayList<String>();
		
		if (Util.isBlank(str)) // filter illegal
			return new FormatResult(null, "BlankLine");

		result.clear();
		for (int i = 0; i < SCHEMA.length; i++)
			result.add(null);

		try {
			String logtime = str.substring(0, 23);
			result.set(0, logtime);
		} catch (Exception e) {
			return new FormatResult(null, "LogTimeErr");
		}

		try {
			String mark = str.substring(24, 29);
			result.set(1, mark);
		} catch (Exception e) {
			return new FormatResult(null, "MarkErr");
		}

		return Util.jsonFormat(SCHEMA, result, str);

	}

	@Override
	public String[] getSchema() {
		return SCHEMA;
	}
}
