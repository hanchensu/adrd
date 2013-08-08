package com.sohu.adrd.data.pig.loader;

import com.sohu.adrd.data.common.AdrdDataUtil;
import com.sohu.adrd.data.common.FormatResult;
import com.sohu.adrd.data.common.LogSchema;
import com.sohu.adrd.data.pig.udf.log.LogLoader;

public class CountinfoLoader extends LogLoader {
	
	public CountinfoLoader(String projection) {
		this.projection = projection;
	}
	

	public FormatResult format(String str) {
		SCHEMA = LogSchema.COUNTINFO_SCHEMA;
		return AdrdDataUtil.format(str, SCHEMA);
	}

}
