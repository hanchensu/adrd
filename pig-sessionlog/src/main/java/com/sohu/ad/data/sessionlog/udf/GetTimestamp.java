package com.sohu.ad.data.sessionlog.udf;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

import com.sohu.ad.data.common.AdrdDataUtil;

public class GetTimestamp extends EvalFunc<Long> {
	
	public Long exec(Tuple input) throws IOException {
		if (input == null || input.size() == 0) {
            return -1L;
        }
		try {
			String rawLog = (String) input.get(0);
			
			String regex = "\"([\\p{Print}]*?)\"[\\p{Blank}]*:[\\p{Blank}]*((\"[\\p{Print}]*?\")|([\\p{Print}]*?))[\\p{Blank}]*[,\\}]";
			Pattern pattern = Pattern.compile(regex);
			Matcher match = pattern.matcher(rawLog);
			
			
			
			while (match.find()) {
				String key = match.group(1);
				String quoted = match.group(3);
				String value = quoted != null ? quoted.substring(1, quoted.length()-1):match.group(4);
				
				long time;
				
				if("TIME".equals(key)) {
					time = Long.parseLong(value);
					return time;
				}
				
				
			}
		} catch (Exception e) {
			return -1L;
		}
		return -1L;
		
	}		
}
