package com.sohu.ad.data.pig.udf.custom;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

import com.sohu.ad.data.common.KeyFormator;
import com.sohu.ad.data.common.MonitorKeyParser;

public class MonitorkeyFormat extends EvalFunc<String> {
	public String exec(Tuple input) throws IOException {
		if (input == null || input.size() < 2) {
			return null;
		}
		String monitorkey = (String) input.get(0);
		String projection = (String) input.get(1);
		try {
			MonitorKeyParser mkp = new MonitorKeyParser();
			mkp.set_monitor_key(monitorkey);
			mkp.parseMonitorKey();
			long source = mkp.get_source_id();
			long advertiser = mkp.get_advertiser_id();
			long campaign = mkp.get_campaign_id();
			long adgroup = mkp.get_adgroup_id();
			long line = mkp.get_line_id();
			long material = mkp.get_material_id();
			return KeyFormator.format(Arrays.asList("source", "advertiser",
					"campaign", "adgroup", "line", "material"), Arrays.asList(
					source, advertiser, campaign, adgroup, line, material), projection);

		} catch (Exception e) {
			return "MonitorkeyErr";
		}
	}

	public static void main(String args[]) {
		String monitorkey = "20q1d000r0000000q2R000q79";
		String projection = "010000";
		MonitorKeyParser mkp = new MonitorKeyParser();
		mkp.set_monitor_key(monitorkey);
		mkp.parseMonitorKey();
		long source = mkp.get_source_id();
		long advertiser = mkp.get_advertiser_id();
		long campaign = mkp.get_campaign_id();
		long adgroup = mkp.get_adgroup_id();
		long line = mkp.get_line_id();
		long material = mkp.get_material_id();
		System.out.println(mkp.get_advertiser_id());
		System.out.println(KeyFormator.format(Arrays.asList("source",
				"advertiser", "campaign", "adgroup", "line", "material"),
				Arrays.asList(source, advertiser, campaign, adgroup, line, material), projection));

	}

}
