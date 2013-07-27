package com.sohu.adrd.data.pig.udf.custom;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

import com.sohu.adrd.data.common.KeyFormator;
import com.sohu.adrd.data.common.MonitorKeyParser;

public class MonitorkeyGetAdertiser extends EvalFunc<String> {
	public String exec(Tuple input) throws IOException {
		if (input == null || input.size() < 1) {
            return null;
        }
		String monitorkey = (String)input.get(0);
		try {
			MonitorKeyParser mkp = new MonitorKeyParser();
			mkp.set_monitor_key(monitorkey);
			mkp.parseMonitorKey();
			long advertiser = mkp.get_advertiser_id();
			return Long.toString(advertiser);
			
		} catch (Exception e) {
			return "MonitorkeyErr";
		}
	}
	
	public static void main(String args[]) {
		String monitorkey = "377905260+375448740+375364522+375001156+374817366+373643004";
		MonitorKeyParser mkp = new MonitorKeyParser();
		mkp.set_monitor_key(monitorkey);
		mkp.parseMonitorKey();
		long advertiser = mkp.get_advertiser_id();
		System.out.println(Long.toString(advertiser));
	}

	
}
