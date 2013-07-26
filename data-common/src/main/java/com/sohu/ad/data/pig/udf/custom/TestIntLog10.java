package com.sohu.ad.data.pig.udf.custom;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

public class TestIntLog10 extends EvalFunc<Integer>{
	Integer compute2(Integer input){
		return input-10;
	}
	
	public Integer exec(Tuple input) throws IOException {
        if (input == null || input.size() == 0)
            return null;

        try {
        	Integer val = (Integer)input.get(0);
            return (val == null ? null : compute2(val));
        } catch (Exception e){
            throw new IOException("Caught exception processing input of " + this.getClass().getName(), e);
        }
	}
}