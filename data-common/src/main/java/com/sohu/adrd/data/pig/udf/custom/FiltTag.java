package com.sohu.adrd.data.pig.udf.custom;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.impl.logicalLayer.schema.Schema;

import com.sohu.adrd.data.common.Util;

/**
 * 
 * @author Su Hanchen hanchensu@sohu-inc.com
 * @inputSchema (userid: chararrary, tag: chararray)
 * @outputSchema (userid: chararray, tag: chaarray)
 */

public class FiltTag extends EvalFunc<Tuple> {
	
	String tagListFile;
	HashSet<String> tags = null;
	
	public FiltTag(String file) {
		tagListFile = file;
	}
	
	@Override
	public Tuple exec(Tuple input) throws IOException {
		if(tags==null) {
			tags = new HashSet<String>();
			
			FileReader fr = new FileReader("./tag_list");
			BufferedReader d = new BufferedReader(fr);
			String line;
			while((line = d.readLine()) != null) {
				String tag = line;
				tags.add(tag);
				
			}
			fr.close();
		}
		
		if (input == null || input.size() < 2) {
            return null;
        }
		String userid = (String) input.get(0);
		String tag = (String) input.get(1);
		
		if(Util.isBlank(userid) || Util.isBlank(tag) || !tags.contains(tag)) {
			return null;
		} else {
			return input;
		}
		
	}
	
	
	public Schema outputSchema(Schema input) {
		try {
			List<Schema.FieldSchema> fieldSchemas = new ArrayList<Schema.FieldSchema>();
			
			Schema.FieldSchema fieldSchema = new Schema.FieldSchema("userid",DataType.CHARARRAY);
			fieldSchemas.add(fieldSchema);
		    
		    fieldSchema = new Schema.FieldSchema("tag",DataType.CHARARRAY);
			fieldSchemas.add(fieldSchema);
			
			
			return new Schema(fieldSchemas);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public List<String> getCacheFiles() {
		List<String> list = new ArrayList<String>();
		list.add(tagListFile+"#tag_list");
		return list;
	}
}
