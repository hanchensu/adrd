package sessionlog.mapreduce.pig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.zebra.types.TypesUtils;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.BagFactory;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;
import org.apache.thrift.TException;
import org.apache.thrift.meta_data.FieldMetaData;
import org.apache.thrift.meta_data.FieldValueMetaData;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TType;
import org.apache.thrift.transport.TMemoryInputTransport;

import sessionlog.op.AdInfoOperation;
import sessionlog.op.AdInfoOperation._Fields;
import sessionlog.util.Util;

public class AdInfoUDF extends EvalFunc<DataBag> {

	private TProtocol protocol = null;
	private TMemoryInputTransport inputTransport = null;
	private static BagFactory bagFactory = BagFactory.getInstance();
	
	public AdInfoUDF() {
		inputTransport = new TMemoryInputTransport();
		protocol = new TBinaryProtocol(inputTransport);
	}
	
	public DataBag exec(Tuple input) throws IOException {
		if (input == null || input.size() == 0) return null;
		DataByteArray infoData = (DataByteArray)input.get(0);
		byte buffer[] = infoData.get();
		int length = infoData.size();
		inputTransport.reset(buffer, 0, length);
		List<Tuple> tuples = new ArrayList<Tuple>();
		if (infoData != null && (length = infoData.size()) > 0) {
			buffer = infoData.get();
			inputTransport.reset(buffer, 0, length);
			AdInfoOperation info = new AdInfoOperation();
	        while (inputTransport.getBufferPosition() < length - 1) {
	        	info.clear();
	            try {
	            	info.read(protocol);
				} catch (TException e) {
					throw new IOException("deserialize data error", e);
				}
	            Tuple tuple = TypesUtils.createTuple(AdInfoOperation.metaDataMap.size());
	            Iterator<Map.Entry<_Fields, FieldMetaData>> it = AdInfoOperation.metaDataMap.entrySet().iterator();
	            int index = 0;
	    		while (it.hasNext()) {
	    			Map.Entry<_Fields, FieldMetaData> entry = it.next();
	    			_Fields field = entry.getKey();
	    			FieldValueMetaData meta = entry.getValue().valueMetaData;
	    			Object value = info.getFieldValue(field);
	    			switch (meta.type) {
	    		    case TType.BOOL :
	    		    	tuple.set(index, Integer.valueOf((Boolean)value ? 1 : 0));
	    		    	break;
	    		    case TType.BYTE :
	    		    	tuple.set(index, Integer.valueOf((Byte)value));
	    		    	break;
	    		    case TType.I16 :
	    		    	tuple.set(index, Integer.valueOf((Short)value));
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

}
