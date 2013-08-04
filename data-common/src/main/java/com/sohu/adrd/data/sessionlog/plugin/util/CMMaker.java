package com.sohu.adrd.data.sessionlog.plugin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.pig.parser.AliasMasker.cmd_return;
import org.apache.thrift.protocol.TType;
import org.json.JSONArray;
import org.json.JSONException;


import com.sohu.adrd.data.common.AdrdDataUtil;
import com.sohu.adrd.data.common.FormatResult;
import com.sohu.adrd.data.common.LogSchema;
import com.sohu.adrd.data.common.Util;
import com.sohu.adrd.data.sessionlog.thrift.operation.CMOperation;
import com.sohu.adrd.data.sessionlog.thrift.operation.CountinfoOperation;
import com.sohu.adrd.data.sessionlog.thrift.operation.CMOperation._Fields;
import com.sohu.adrd.data.sessionlog.thrift.operation.OperationType;


public class CMMaker {
	
	private static String[] thriftName = { "logTime", "logLevel", "extData","exUID","mID","suv","tags","ver","yyid" };

	

	public static int indexOf(String key) {
		return Arrays.asList(LogSchema.CM_SCHEMA).indexOf(key);
	}

	public static CMOperation makeCM(String log) {
		FormatResult fr = AdrdDataUtil.format(log, LogSchema.CM_SCHEMA);
		return makeCM(fr);
	}
	
	public static CMOperation makeCM(FormatResult formatResult) {
		return makeCM(formatResult.strs);
	}

	public static CMOperation makeCM(List<String> strs) {
		
		CMOperation operation = new CMOperation();

		if (strs != null) {
			for (int i = 0; i < LogSchema.CM_SCHEMA.length; i++) {

				_Fields field = CMOperation._Fields
						.findByName(thriftName[i]);

				byte type = CMOperation.metaDataMap.get(field).valueMetaData.type;
				String valueStr = strs.get(i);
				
				if (type == TType.STRING && Util.isNotBlank(valueStr)) {
					
					operation.setFieldValue(field, valueStr);
					
				} else {
					try {
						
						JSONArray tagsJsonArray = new JSONArray(strs.get(indexOf("TAGS")));
						operation.setTags(new ArrayList<String>());
						for(int j = 0; j < tagsJsonArray.length(); j++) {
							String tag = tagsJsonArray.getString(j);
							operation.tags.add(tag);
							
						}
					} catch (JSONException e) {
						
					}
					
				}
			}
		}
		
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss,SSS");
		Date date;
		try {
			date = format.parse(operation.getLogTime());
			operation.setTimestamp(date.getTime() / 1000L);
		} catch (Exception e) {
			operation.setTimestamp(1);
		}
		
		return operation;
	}
	

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File("D:/worktmp/cm.txt")));
		String str;
		while ((str = br.readLine()) != null) {
			System.out.println(str);
			CMOperation cm = CMMaker.makeCM(str);
			System.out.println(cm.getTimestamp());
			for(String tag : cm.tags) {
				System.out.println(tag);
			}
			
		}
		
		
		br.close();
	}

}
