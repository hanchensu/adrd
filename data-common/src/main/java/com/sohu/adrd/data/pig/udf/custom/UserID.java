package com.sohu.adrd.data.pig.udf.custom;

import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

import com.sohu.adrd.data.common.Util;

public class UserID extends EvalFunc<String> {

	@Override
	public String exec(Tuple input) throws IOException {
		if (input == null || input.size() < 4) {
            return null;
        }
		String str = (String) input.get(0);
		if (!"_blank".equals(str) && Util.isNotBlank(str)) return str;
		str = (String) input.get(1);
		if (!"_blank".equals(str) && Util.isNotBlank(str)) return str;
		str = (String) input.get(2);
		String agent = (String) input.get(3);
		if (!"_blank".equals(str) && Util.isNotBlank(str)) {
			String s = Util.isNotBlank(agent) ? str + agent : str;
			s = Util.getMD5(s);
			if (s == null) return null;
			else return s;
		}
		return null;
	}
	

}
