package com.sohu.adrd.data.hive.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.pig.parser.QueryParser.add_expr_return;
import org.apache.commons.lang.math.Fraction;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import com.sohu.ADRD.AudienceTargeting.type.basic.TagsWritable;
import com.sohu.ADRD.AudienceTargeting.type.basic.UserWritable;
import com.sohu.adrd.data.common.AdrdDataHive;
import com.sohu.adrd.data.common.AdrdDataUtil;
import com.sohu.adrd.data.common.FormatResult;
import com.sohu.adrd.data.common.LogSchema;
import com.sohu.adrd.data.common.Util;

/**
 * 
 * @author Suhanchen hanchensu@sohu-inc.com
 * @schema {user:string, tags:list<string>}
 *
 */
public class UserTagRecordReader extends BaseSequenceRecordReader<UserWritable, TagsWritable>{

	public UserTagRecordReader(Configuration conf, FileSplit split)
			throws IOException {
		super(conf, split);
	}

	@Override
	public void transform(UserWritable key, TagsWritable trueValue,
			BytesWritable value) {
		String userid = key.getuserid();
		
		long tag_mask = 0xffffffffffff0000l;
		List<String> tags = new ArrayList<String>();
		List<Long> tagList = trueValue.gettags_long();
		for(java.util.Iterator<Long> iter = tagList.iterator();iter.hasNext();) {
			long tagall = iter.next();
			long tag = tagall&tag_mask;
			tags.add(String.valueOf(tag));
		}
		
		String recordStr = AdrdDataHive.toHiveStr(userid) + AdrdDataHive.FIELD_DELIMITER + AdrdDataHive.toHiveStr(tags,AdrdDataHive.LIST_DELIMITER);
		
		value.set(new BytesWritable(recordStr.getBytes()));
	}
}
