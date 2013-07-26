package com.sohu.ad.data.pig.udf.custom;


import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;
import com.sohu.ad.data.common.Util;

public class GetClass extends EvalFunc<String> {
	@Override
	public String exec(Tuple input) throws IOException {
		return input.get(0).getClass().toString();
	}
}
