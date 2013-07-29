package com.sohu.adrd.data.sessionlog.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
import com.sohu.adrd.data.common.Util;
import com.sohu.adrd.data.sessionlog.util.Formator;


public class ExchangeFormator implements Formator {

	public FormatResult format(String str) {
		FormatResult fr = AdrdDataUtil.format(str, LogSchema.EX_SCHEMA);
		
		return fr;
	}
	
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(new File("D:/worktmp/countinfo.txt")));
		String str;
		while ((str = br.readLine()) != null) {
			for(String abc : new ExchangeFormator().format(str).strs) {
				System.out.println(abc);
			}
				
		}
		br.close();
	}
}
