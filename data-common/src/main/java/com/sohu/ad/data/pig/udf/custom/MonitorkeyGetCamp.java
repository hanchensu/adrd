package com.sohu.ad.data.pig.udf.custom;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

import com.sohu.ad.data.common.KeyFormator;
import com.sohu.ad.data.common.MonitorKeyParser;

public class MonitorkeyGetCamp extends EvalFunc<String> {
	public String exec(Tuple input) throws IOException {
		if (input == null || input.size() < 1) {
            return null;
        }
		String monitorkey = (String)input.get(0);
		try {
			MonitorKeyParser mkp = new MonitorKeyParser();
			mkp.set_monitor_key(monitorkey);
			mkp.parseMonitorKey();
			long campaign = mkp.get_campaign_id();
			return Long.toString(campaign);
			
		} catch (Exception e) {
			return "MonitorkeyErr";
		}
	}
	
	public static void main(String args[]) {
		String monitorkey = "20q1d000r0000000q2R000q79";
		MonitorKeyParser mkp = new MonitorKeyParser();
		mkp.set_monitor_key(monitorkey);
		mkp.parseMonitorKey();
		long campaign = mkp.get_campaign_id();
		System.out.println(Long.toString(campaign));
	}

	
}
