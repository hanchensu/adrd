package com.sohu.ad.data.pig.udf.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.pig.LoadFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigSplit;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;

import com.sohu.ad.data.common.FormatResult;
import com.sohu.ad.data.common.InputPathFilter;
import com.sohu.ad.data.common.Util;

public class PvLogLoader extends LoadFunc {
	
	private RecordReader reader;
	private final TupleFactory tupleFactory = TupleFactory.getInstance();
	
	@Override
	public InputFormat getInputFormat() throws IOException {
		return new TextInputFormat();
	}
	
	

	@Override
	public Tuple getNext() throws IOException {
		try {
			if(!reader.nextKeyValue()) {
				return null;
			}
			Text value = (Text) reader.getCurrentValue();
			String line = value.toString();
			
			FormatResult fs = new PvlogFormator().format(line);
			
			Tuple tuple = tupleFactory.newTuple(3);
			
			tuple.set(0, fs.strs.get(0));
			tuple.set(1, fs.strs.get(1));
			tuple.set(2, fs.strs.get(7));
			return tuple;
			
		} catch (Exception e) {
			return null;
		}
	}
	
	
	

	@Override
	public void prepareToRead(RecordReader reader, PigSplit split)
			throws IOException {
		this.reader = reader;
	}

	@Override
	public void setLocation(String location, Job job) throws IOException {
		FileInputFormat.setInputPathFilter(job, InputPathFilter.class);
		FileInputFormat.setInputPaths(job, location);
		
	}
	
	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File("D:/worktmp/pv.txt")));
		String str;
		while ((str = br.readLine()) != null) {
			System.out.println(str);
			System.out.println(new PvlogFormator().format(str).strs.get(0)+"\t"+new PvlogFormator().format(str).strs.get(1)+"\t"+new PvlogFormator().format(str).strs.get(7));
		}
		br.close();
	}

}

class PvlogFormator  {
    
	private long lastTimeStamp = -1;
	private List<String> result = new ArrayList<String>();
	
	public FormatResult format(String str) {
		if (Util.isBlank(str)) return new FormatResult(null, "err1");
		result.clear();
		String yyid = null;
		String suv = null;
		String ip = null;
		String useragent = null;
		String region = null;
		String timeStr = null;
		String refer = null;
		String url = null;
		int num = 0;
		int pos = 0;
		int spos = 0;
		String substr = "";
		while (true) {
			pos = str.indexOf(" ", pos);
			if (pos == -1) {
				substr = str.substring(spos, spos + 1);
				num++;
				break;
			} else {
				if (pos == 0) substr = "-";
				else substr = str.substring(spos, pos);
				spos = pos + 1;
				num++;
			}
			if (spos == str.length()) break;
			char ch = str.charAt(spos);
			if (ch == '[') {
				pos = str.indexOf("]", spos + 1) + 1;
			} else if (ch == '"') pos = str.indexOf('"', spos + 1) + 1;
			else pos = pos + 1;
			switch (num) {
			case 1:
				suv = substr;
				break;
			case 2:
				timeStr = substr.substring(1, substr.length() - 1);
				break;
			case 3:
				ip = substr;
				break;
			case 4:
				refer = substr.substring(1, substr.length() - 1);
				break;
			case 5:
				try {
					url = substr.substring(1, substr.length() - 1);
				} catch(StringIndexOutOfBoundsException e) {
					url="";
				}
				break;
			case 7:
				region = substr;
				break;
			case 9:
				yyid = substr;
				break;
			case 10:
				try { 
					useragent = substr.substring(1, substr.length() - 1);
				} catch(StringIndexOutOfBoundsException e) {
					useragent="";
				}
				break;
			default:
				;
			}
		}
		if ("-".equals(yyid)) {
			yyid = null;
		}
		result.add(yyid);
		if ("-".equals(suv)) suv = null;
		result.add(suv);
		if ("-".equals(ip)) ip = null;
		result.add(ip);
		if ("-".equals(useragent) || "".equals(useragent)) useragent = null;
		result.add(useragent);
		if ("-".equals(region)) region = null;
		result.add(region);
		if ("-".equals(timeStr) || "".equals(timeStr)) timeStr = null;
		if (timeStr == null || timeStr.length() == 0) result.add(null);
		else {
			SimpleDateFormat sdf = Util.usFormat();
			Date date = null;
			try {
				date = sdf.parse(timeStr);
				long lTime = date.getTime() / 1000;
				lastTimeStamp = lTime;
				result.add(String.valueOf(lTime));
			} catch (ParseException e) {
				long lTime = lastTimeStamp;
				result.add(String.valueOf(lTime));
			}
		}
		if ("".equals(refer) || "-".equals(refer)) result.add(null);
		else {
			int start = refer.indexOf("r?=");
			if (start != -1) {
				int end = refer.indexOf(" ", start);
				refer = refer.substring(start + 3, end);
				if (!refer.equals("") && !refer.equals("-") && refer != null) result.add(refer);
				else result.add(null);
			} else result.add(null);
		}
		if (!"".equals(url) && !"-".equals(url)) result.add(url);
		else {
			result.add(null);
		}
		return new FormatResult(result, "0"); 
	}

}