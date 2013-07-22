package com.sohu.ad.data.pig.udf.custom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pig.EvalFunc;
import org.apache.pig.FuncSpec;
import org.apache.pig.builtin.Base;
import org.apache.pig.builtin.DoubleBase;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;

public class TestLog10 extends EvalFunc<Double>{
	Double compute(Double input){
		return input+11;
	}
	
	public Double exec(Tuple input) throws IOException {
        if (input == null || input.size() == 0)
            return null;

        try {
            Double val = (Double)input.get(0);
            return (val == null ? null : compute(val));
        } catch (Exception e){
            throw new IOException("Caught exception processing input of " + this.getClass().getName(), e);
        }
	}
	
	

    /* (non-Javadoc)
     * @see org.apache.pig.EvalFunc#getArgToFuncMapping()
     */
    @Override
    public List<FuncSpec> getArgToFuncMapping() throws FrontendException {
        List<FuncSpec> funcList = new ArrayList<FuncSpec>();
//        funcList.add(new FuncSpec(this.getClass().getName(), new Schema(new Schema.FieldSchema(null, DataType.DOUBLE))));
        funcList.add(new FuncSpec(TestIntLog10.class.getName(), new Schema(new Schema.FieldSchema(null, DataType.INTEGER))));

        return funcList;
    }
}
