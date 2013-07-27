package com.sohu.adrd.data.pig.udf.custom;

import java.io.IOException;
import java.util.Arrays;

import org.apache.pig.EvalFunc;
import org.apache.pig.PigWarning;
import org.apache.pig.data.Tuple;

import com.sohu.adrd.data.common.KeyFormator;

public class RegionFormat extends EvalFunc<String> {

	@Override
	public String exec(Tuple input) throws IOException {
		if (input == null || input.size() < 2) {
            return null;
        }
		String region = (String)input.get(0);
		String projection = (String) input.get(1);
		try {
			
			long lregion = Long.valueOf(region);
			long mask = 0x0000ffffffff0000l;
			lregion = (lregion & mask) >> 16;
			region = String.valueOf(lregion);
			
			String country, province, city;
			if(region.length() != 10) {
				country = province = city = "unknown-" + region;
			}
			else {
				country = region.substring(0, 4);
				province = region.substring(4, 6);
				city = region.substring(6,8);
			}
			return KeyFormator.format(Arrays.asList("country","province","city"), Arrays.asList(country,province,city), projection);
			
		} catch (Exception e) {
			return "RegionErr";
		}
	}
	
	

}
