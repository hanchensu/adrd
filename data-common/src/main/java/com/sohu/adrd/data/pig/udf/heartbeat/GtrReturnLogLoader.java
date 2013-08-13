package com.sohu.adrd.data.pig.udf.heartbeat;

import java.util.ArrayList;
import java.util.List;

import org.apache.pig.newplan.logical.relational.LogicalSchema;

import com.sohu.adrd.data.common.FormatResult;
import com.sohu.adrd.data.common.LogSchema;
import com.sohu.adrd.data.common.Util;
import com.sohu.adrd.data.pig.loader.LogLoader;

public class GtrReturnLogLoader extends LogLoader {
	public GtrReturnLogLoader(String projection) {
		this.projection = projection;

	}

	public static final String[] SCHEMA = LogSchema.GTR_RETURN_SCHEMA;

	@Override
	protected FormatResult format(String str) {

		List<String> result = new ArrayList<String>();

		if (Util.isBlank(str)) // filter illegal
			return new FormatResult(null, "BlankLine");

		result.clear();
		for (int i = 0; i < SCHEMA.length; i++)
			result.add(null);

		try {
			String logtime = str.substring(0, 13);
			result.set(0, logtime);
		} catch (Exception e) {
			return new FormatResult(null, "LogTimeErr");
		}

		return Util.jsonFormat(SCHEMA, result, str);
	}
}
