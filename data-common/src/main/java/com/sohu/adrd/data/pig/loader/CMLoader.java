package com.sohu.adrd.data.pig.loader;

import com.sohu.adrd.data.common.AdrdDataUtil;
import com.sohu.adrd.data.common.FormatResult;
import com.sohu.adrd.data.common.LogSchema;

public class CMLoader extends LogLoader {
	
	public CMLoader(String projection) {
		this.projection = projection;
	}
	
	public FormatResult format(String str) {
		SCHEMA = LogSchema.CM_SCHEMA;
		return AdrdDataUtil.format(str, SCHEMA);
	}

}

