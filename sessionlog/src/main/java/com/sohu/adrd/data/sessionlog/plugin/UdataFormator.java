package com.sohu.adrd.data.sessionlog.plugin;

import java.util.ArrayList;
import java.util.List;

import com.sohu.adrd.data.common.FormatResult;
import com.sohu.adrd.data.common.Util;
import com.sohu.adrd.data.sessionlog.util.Formator;


public class UdataFormator implements Formator {

	private List<String> result = new ArrayList<String>();
	
	public FormatResult format(String str) {
		if (Util.isBlank(str)) return new FormatResult(null,"Udata_Blankline");
		result.clear();
		result.add(str);
		return new FormatResult(result,"0");
	}


}
