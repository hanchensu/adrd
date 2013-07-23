package com.sohu.ad.data.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AdinfoFormator {
	
	public static FormatResult format(String str,String[] schema) {
		List<String> result = new ArrayList<String>();
		
		if (Util.isBlank(str)) // filter illegal
			return new FormatResult(null, "BlankLine");

		result.clear();
		for (int i = 0; i < schema.length; i++)
			result.add(null);

		try {
			String logtime = str.substring(0, 23);
			result.set(0, logtime);
		} catch (Exception e1) {
			result.set(0, null);
		}

		try {
			String mark = str.substring(24, 29);
			result.set(1, mark);
		} catch (Exception e2) {
			result.set(0, null);
		}

		return Util.jsonFormat(schema, result, str);
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(
				"D:/worktmp/countinfo.txt")));
		String str;
		
		
		while ((str = br.readLine()) != null) {
			System.out.println(str);
			
			FormatResult fr = AdinfoFormator.format(str,LogSchema.COUNTINFO_SCHEMA);
			System.out.println(fr.errorcode);
			int index = 0;
			for(String attr:fr.strs) {
				System.out.println((index++)+":"+attr);
			}
		}
		br.close();
	}

}


