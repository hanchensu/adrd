package com.sohu.ad.data.pig.udf.log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pig.FilterFunc;
import org.apache.pig.FuncSpec;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;

public class IllegalLogFilter extends FilterFunc {
//	String errtype;
//	public IllegalLogFilter(String errtype) {
//		this.errtype = errtype;
//	}
	
	@Override
	public Boolean exec(Tuple tuple) throws ExecException {
		if(tuple == null | tuple.size() == 0) {
			return false;
		}
		try {
			Object object = tuple.get(0);
			if(object == null) {
				return false;
			}
			String firstField = (String) object;	
//			String strvalue = firstField.toString();
//			if("all".equalsIgnoreCase(errtype)) {
//				if(firstField.contains("err")) {
//					return true;
//				}
//			} else if(firstField.contains("err")&firstField.contains(errtype)) {
//				return true;
//			}
			if(firstField.contains("err"))
					return true;
			return false;
		} catch (Exception e) {
			throw new ExecException();
		}
		
	}
	
	@Override
	public List<FuncSpec> getArgToFuncMapping() throws FrontendException {
		List<FuncSpec> funcSpecs = new ArrayList<FuncSpec>();
		funcSpecs.add(new FuncSpec(this.getClass().getName(),new Schema(new Schema.FieldSchema(null, DataType.CHARARRAY))));
		return funcSpecs;
	}

}
