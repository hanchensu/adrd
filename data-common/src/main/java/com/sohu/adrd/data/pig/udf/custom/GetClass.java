package com.sohu.adrd.data.pig.udf.custom;


import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

import com.sohu.adrd.data.common.Util;

public class GetClass extends EvalFunc<String> {
	@Override
	public String exec(Tuple input) throws IOException {
		return input.get(0).getClass().toString();
	}
}
