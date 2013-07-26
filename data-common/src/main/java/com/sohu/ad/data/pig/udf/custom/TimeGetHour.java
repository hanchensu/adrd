package com.sohu.ad.data.pig.udf.custom;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

import com.sohu.ad.data.common.KeyFormator;
import com.sohu.ad.data.common.MonitorKeyParser;

public class TimeGetHour extends EvalFunc<String> {
	public String exec(Tuple input) throws IOException {
		if (input == null || input.size() < 1) {
            return null;
        }
		String timestamp = (String)input.get(0);
		try {
			long time = Long.parseLong(timestamp);
			
			Date date = new Date(time*1000);
			return Integer.toString(date.getHours());
			
		} catch (Exception e) {
			return "MonitorkeyErr";
		}
	}

	
}
