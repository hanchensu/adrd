package com.sohu.adrd.data.sessionlog.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.pig.newplan.logical.relational.LogicalSchema;
import org.apache.pig.parser.AliasMasker.query_return;

import com.sohu.adrd.data.common.AdrdDataUtil;
import com.sohu.adrd.data.common.FormatResult;
import com.sohu.adrd.data.common.LogSchema;
import com.sohu.adrd.data.sessionlog.util.Formator;


public class CountinfoFormator implements Formator {

	private static String dfltEncName = null;
	static {
		dfltEncName = (String) AccessController
				.doPrivileged(new PrivilegedAction<String>() {
					public String run() {
						String value = System.getProperty("file.encoding");
						if (value == null)
							value = "UTF-8";
						return value;
					}
				});
	}


	public FormatResult format(String str) {
		FormatResult fr = AdrdDataUtil.format(str, LogSchema.COUNTINFO_SCHEMA);
		
		if(fr.strs == null) {
			return fr;
		}
		
		int referIndex = Arrays.asList(LogSchema.COUNTINFO_SCHEMA).indexOf("REFFER");
		int agentIndex =  Arrays.asList(LogSchema.COUNTINFO_SCHEMA).indexOf("USERAGENT");
		
		String agent = fr.strs.get(agentIndex);
		fr.strs.set(agentIndex, agent.replaceAll("\\\"", "\""));
		
		String refer = fr.strs.get(referIndex);
		try {
			refer = URLDecoder.decode(refer, dfltEncName);
			fr.strs.set(referIndex, refer);
		} catch (Exception e) {
			
		}
		return fr;
	}	
}
