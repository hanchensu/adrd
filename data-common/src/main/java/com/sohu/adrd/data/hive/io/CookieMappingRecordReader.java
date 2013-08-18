package com.sohu.adrd.data.hive.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.pig.parser.QueryParser.add_expr_return;
import org.apache.commons.lang.math.Fraction;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import com.sohu.adrd.data.common.AdrdDataHive;
import com.sohu.adrd.data.common.AdrdDataUtil;
import com.sohu.adrd.data.common.FormatResult;
import com.sohu.adrd.data.common.LogSchema;

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
			
		}
	}


}
