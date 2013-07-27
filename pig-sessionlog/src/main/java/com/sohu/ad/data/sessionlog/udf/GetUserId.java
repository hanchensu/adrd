package com.sohu.ad.data.sessionlog.udf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.schema.Schema;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;

import com.sohu.adrd.data.common.AdrdDataUtil;
import com.sohu.adrd.data.common.FormatResult;
import com.sohu.adrd.data.common.LogSchema;
import com.sohu.adrd.data.common.Util;
import com.sohu.adrd.data.sessionlog.plugin.CountinfoMaker;
import com.sohu.adrd.data.sessionlog.thrift.operation.CountinfoOperation;


public class GetUserId extends EvalFunc<String> {
	
	
	public String exec(Tuple input) throws IOException {
		if (input == null || input.size() == 0) {
            return null;
        }
		try {
			String rawLog = (String) input.get(0);
			
			String regex = "\"([\\p{Print}]*?)\"[\\p{Blank}]*:[\\p{Blank}]*((\"[\\p{Print}]*?\")|([\\p{Print}]*?))[\\p{Blank}]*[,\\}]";
			Pattern pattern = Pattern.compile(regex);
			Matcher match = pattern.matcher(rawLog);
			
			String yyid = null;
			String suv = null;
			String ip = null;
			String agent = null;
			
			while (match.find()) {
				String key = match.group(1);
				String quoted = match.group(3);
				String value = quoted != null ? quoted.substring(1, quoted.length()-1):match.group(4);
				
				key = key.trim();
				value = value.trim();
				
				if("YYID".equals(key)) {
					yyid = value;
				}
				
				if("SUV".equals(key)) {
					suv = value;
				}
				
				if("USERIP".equals(key)) {
					suv = value;
				}
				
				if("USERAGENT".equals(key)) {
					suv = value;
				}
				
			}
			return AdrdDataUtil.makeUserId(yyid, suv, ip, agent); 
		} catch (Exception e) {
			return null;
		}
		
	}		

}