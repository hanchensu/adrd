package com.sohu.ad.data.common;

import java.util.List;

import com.sohu.ad.data.thrift.operation.CountinfoOperation;

public class CountinfoMaker {
	private Object[] defaults = {null,null};
	private boolean[] required = {};
	private 
	
	public static CountinfoOperation makeCountinfo(FormatResult formatResult) {
		return new CountinfoOperation();
	}
	
	public static void main(String[] args){
		CountinfoOperation countinfo = new CountinfoOperation();
		countinfo.setC(1);
		System.out.println(countinfo.isSetC());
		System.out.println(countinfo.c);
	}

}
