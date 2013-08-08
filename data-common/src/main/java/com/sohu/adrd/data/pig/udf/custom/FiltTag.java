package com.sohu.adrd.data.pig.udf.custom;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

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
	
	public List<String> getCacheFiles() {
		List<String> list = new ArrayList<String>();
		list.add(tagListFile+"#tag_list");
		return list;
	}
}
