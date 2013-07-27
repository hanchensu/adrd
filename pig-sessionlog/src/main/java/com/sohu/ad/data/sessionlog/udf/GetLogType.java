package com.sohu.ad.data.sessionlog.udf;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;


import com.sohu.adrd.data.common.AdrdDataUtil;


public class GetLogType extends EvalFunc<String> {
	
	public String exec(Tuple input) throws IOException {
		if (input == null || input.size() == 0) {
            return null;
        }
		try{
			String rawLog = (String) input.get(0);
			
			String regex = "\"([\\p{Print}]*?)\"[\\p{Blank}]*:[\\p{Blank}]*((\"[\\p{Print}]*?\")|([\\p{Print}]*?))[\\p{Blank}]*[,\\}]";
			Pattern pattern = Pattern.compile(regex);
			Matcher match = pattern.matcher(rawLog);
			
			String reqType = null;
			String adType = null;
			
			while (match.find()) {
				String key = match.group(1);
				String quoted = match.group(3);
				String value = quoted != null ? quoted.substring(1, quoted.length()-1):match.group(4);
				
				key = key.trim();
				value = value.trim();
				
				if("REQTYPE".equals(key)) {
					reqType = value;
				}
				
				if("ADTYPE".equals(key)) {
					adType = value;
				}
				if(reqType != null && adType != null) {
					break;
				}
			}
			return AdrdDataUtil.getOpType(reqType,adType,null); //TODO: add suv
			
		} catch (Exception e) {
			return null;
		}
		
		
	}		

}
