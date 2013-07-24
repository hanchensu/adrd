package com.sohu.ad.data.common;

import java.util.List;

import com.sohu.ad.data.thrift.operation.CountinfoOperation;

public class CountinfoMaker {
	
	public static CountinfoOperation makeCountinfo(FormatResult formatResult) {
		return new CountinfoOperation();
	}

}
