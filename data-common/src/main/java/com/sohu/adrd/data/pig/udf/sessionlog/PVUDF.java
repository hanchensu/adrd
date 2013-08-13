package com.sohu.adrd.data.pig.udf.sessionlog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.BagFactory;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;
import org.apache.thrift.TException;
import org.apache.thrift.meta_data.FieldMetaData;
import org.apache.thrift.meta_data.FieldValueMetaData;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TType;
import org.apache.thrift.transport.TMemoryInputTransport;

import com.sohu.adrd.data.common.Util;
import com.sohu.adrd.data.sessionlog.thrift.operation.PVOperation;
import com.sohu.adrd.data.sessionlog.thrift.operation.PVOperation._Fields;


public class PVUDF extends EvalFunc<DataBag> {
	
	private TProtocol protocol = null;
	private TMemoryInputTransport inputTransport = null;
	private static BagFactory bagFactory = BagFactory.getInstance();
	private static TupleFactory tupleFactory = TupleFactory.getInstance();
	
	public PVUDF() {
		inputTransport = new TMemoryInputTransport();
		protocol = new TBinaryProtocol(inputTransport);
	}
	
	public DataBag exec(Tuple input) throws IOException {
		if (input == null || input.size() == 0) return null;
		DataByteArray pvData = (DataByteArray)input.get(0);
		byte buffer[] = pvData.get();
		int length = pvData.size();
		inputTransport.reset(buffer, 0, length);
		List<Tuple> tuples = new ArrayList<Tuple>();
		if (pvData != null && (length = pvData.size()) > 0) {
			buffer = pvData.get();
			inputTransport.reset(buffer, 0, length);
			PVOperation op = new PVOperation();
	        while (inputTransport.getBufferPosition() < length - 1) {
	        	op.clear();
	            try {
	            	op.read(protocol);
				} catch (TException e) {
					throw new IOException("deserialize data error", e);
				}
	            Tuple tuple = tupleFactory.newTuple(PVOperation.metaDataMap.size());
	            Iterator<Map.Entry<_Fields, FieldMetaData>> it = PVOperation.metaDataMap.entrySet().iterator();
	            int index = 0;
	            while (it.hasNext()) {
	    			Map.Entry<_Fields, FieldMetaData> entry = it.next();
	    			_Fields field = entry.getKey();
	    			FieldValueMetaData meta = entry.getValue().valueMetaData;
	    			Object value = op.getFieldValue(field);
	    			switch (meta.type) {
	    		    case TType.I32 :
	    		    	tuple.set(index, Integer.valueOf((Integer)value));
	    		    	break;
	    		    case TType.I64 :
	    		    	tuple.set(index, Long.valueOf((Long)value));
	    		    	break;
	    		    case TType.STRING :
	    		    	if (Util.isBlank((String)value)) tuple.set(index, "");
	    		    	else tuple.set(index, value);
	    		    	break;
	    		    default :
	    		    	tuple.set(index, value);
	    			}
	    			index++;
	    		}
	    		tuples.add(tuple);
	        }
		}
		return bagFactory.newDefaultBag(tuples);
	}
	
	public Schema outputSchema(Schema input) {
		try {
			List<Schema.FieldSchema> fieldSchemas = new ArrayList<Schema.FieldSchema>();
			Schema.FieldSchema fieldSchema = null;
			Iterator<Map.Entry<_Fields, FieldMetaData>> it = PVOperation.metaDataMap.entrySet().iterator();
			while (it.hasNext()) {
    			Map.Entry<_Fields, FieldMetaData> entry = it.next();
    			_Fields field = entry.getKey();
    			FieldValueMetaData meta = entry.getValue().valueMetaData;
    			switch (meta.type) {
    		    case TType.I32 :
    		    	fieldSchema = new Schema.FieldSchema(field.getFieldName(),DataType.INTEGER);
    		    	break;
    		    case TType.I64 :
    		    	fieldSchema = new Schema.FieldSchema(field.getFieldName(),DataType.LONG);
    		    	break;
    		    case TType.STRING :
    		    	fieldSchema = new Schema.FieldSchema(field.getFieldName(),DataType.CHARARRAY);
    		    	break;
    		    default :
    		    	fieldSchema = new Schema.FieldSchema(field.getFieldName(),DataType.BYTEARRAY);
    			}
    		}
			fieldSchemas.add(fieldSchema);
			Schema scehma = new Schema(fieldSchemas);
			return new Schema(new Schema.FieldSchema(input.getField(0).alias+"Bag",scehma,DataType.BAG));
		} catch (FrontendException e) {
			return null;
		}
	}

}
