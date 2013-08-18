package com.sohu.adrd.data.common;

import java.util.List;

public class FormatResult {
	public List<String> strs;
	public String errorcode;

	public FormatResult(List<String> strs, String errorcode) {
		this.strs = strs;
		this.errorcode = errorcode;
	}

}