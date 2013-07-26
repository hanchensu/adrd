package com.sohu.ad.data.pig.udf.log;

import java.util.ArrayList;
import java.util.List;

import com.sohu.ad.data.common.FormatResult;
import com.sohu.ad.data.common.LogSchema;
import com.sohu.ad.data.common.Util;

public class GtrLogLoader extends LogLoader {
	public GtrLogLoader(String projection) {
		this.projection = projection;
	}
	
	public static final String[] SCHEMA = LogSchema.GTR_SCHEMA;

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
