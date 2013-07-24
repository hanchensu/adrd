package com.sohu.ad.data.common;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.apache.pig.LoadPushDown.RequiredField;

import com.sohu.ad.data.thrift.operation.CountinfoOperation;

public class CountinfoMaker {
	private static Object[] defaults = new Object[LogSchema.COUNTINFO_SCHEMA.length];
	private static boolean[] required = new boolean[LogSchema.COUNTINFO_SCHEMA.length];
	static {
		
		required[Arrays.asList(LogSchema.COUNTINFO_SCHEMA).indexOf("")] = false;
		
	}

	public static CountinfoOperation makeCountinfo(FormatResult formatResult) {
		return new CountinfoOperation();
	}

	public static void main(String[] args) {
		for (String attr : LogSchema.COUNTINFO_SCHEMA) {
			System.out.print("false,");
		}
	}

}
