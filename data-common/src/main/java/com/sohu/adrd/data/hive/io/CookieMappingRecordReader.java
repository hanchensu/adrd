package com.sohu.adrd.data.hive.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapreduce.Job;
import org.apache.pig.parser.QueryParser.add_expr_return;
import org.apache.commons.lang.math.Fraction;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.json.JSONArray;
import org.json.JSONException;

import com.sohu.adrd.data.common.AdrdDataHive;
import com.sohu.adrd.data.common.AdrdDataUtil;
import com.sohu.adrd.data.common.FormatResult;
import com.sohu.adrd.data.common.LogSchema;
import com.sohu.adrd.data.common.Util;
import com.sohu.adrd.data.sessionlog.plugin.util.CMMaker;
import com.sohu.adrd.data.sessionlog.thrift.operation.CMOperation;

public class CookieMappingRecordReader extends BaseLineRecordReader{

	public CookieMappingRecordReader(Configuration job, FileSplit split) throws IOException {
		super(job, split);
	}
	
	@Override
	public void transform(Text value) {
		FormatResult res = AdrdDataUtil.format(value.toString(), LogSchema.CM_SCHEMA);
		if(res.strs == null) {
			value.set(res.errorcode);
		} else {
			try {
				int tagsIndex = Util.indexOf("TAGS", LogSchema.CM_SCHEMA);
				JSONArray tagsJsonArray = new JSONArray(res.strs.get(tagsIndex));
				List<String> tags = new ArrayList<String>();
				for(int i = 0; i < tagsJsonArray.length(); i++) {
					String tag = tagsJsonArray.getString(i);
					tags.add(tag);
				}
				res.strs.set(tagsIndex, AdrdDataHive.toHiveStr(tags,AdrdDataHive.LIST_DELIMITER));
			} catch (JSONException e) {
				
			}
			String recordStr = AdrdDataHive.toHiveStr(res.strs, AdrdDataHive.FIELD_DELIMITER);
			value.set(res.errorcode+AdrdDataHive.FIELD_DELIMITER+recordStr);
		}
	}

}
