package com.sohu.adrd.data.sessionlog.plugin;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryInputTransport;

import com.sohu.adrd.data.common.AdrdDataUtil;
import com.sohu.adrd.data.common.Util;
import com.sohu.adrd.data.sessionlog.plugin.util.CountinfoMaker;
import com.sohu.adrd.data.sessionlog.plugin.util.ExMaker;
import com.sohu.adrd.data.sessionlog.thrift.operation.ExOperation;
import com.sohu.adrd.data.sessionlog.thrift.operation.OperationType;
import com.sohu.adrd.data.sessionlog.util.Extractor;
import com.sohu.adrd.data.sessionlog.util.ExtractorEntry;
import com.sohu.adrd.data.sessionlog.util.ReuseMemoryBuffer;


public class ExchangeExtractor implements Extractor {
	
	
	private TProtocol protocol;
	private int offset = 0;	
	private ReuseMemoryBuffer transport;
	private List<ExtractorEntry> entryList;
	
	public ExchangeExtractor() {
		transport = new ReuseMemoryBuffer(2048);
		protocol = new TBinaryProtocol(transport);
		entryList = new ArrayList<ExtractorEntry>();
	}
	
	public List<ExtractorEntry> extract(List<String> strs) {
		transport.reuse();
		entryList.clear();
		
		ExOperation ex = ExMaker.makeEx(strs);
		
		String userkey = Util.isNotBlank(ex.getSuv()) ? ex.suv : "NulL";
		
		String timestr = ex.getLogTime();
		
		long timestamp = 1L;
		
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss,SSS");
		Date date;
		try {
			date = format.parse(timestr);
			timestamp = date.getTime() / 1000L;
		} catch (ParseException e) {
			
		}
		
		ExtractorEntry entry = new ExtractorEntry();
		entry.setUserKey(userkey);
		entry.setTimestamp(timestamp);
		
		OperationType opType = OperationType.EXCHANGE;
		
		entry.setOperation(opType);
		entry = writeFields(entry, ex);
		
		entryList.add(entry);
		offset = 0;
		return entryList;
	}
	
	public ExtractorEntry writeFields(ExtractorEntry entry, ExOperation info) {
		try {
			info.write(protocol);
		} catch (TException e) {
		}
		entry.setValue(transport.getArray(), offset, transport.length() - offset);
		offset = transport.length();
		return entry;
	}
	
	
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(new File("D:/worktmp/countinfo.txt")));
		String str;
		while ((str = br.readLine()) != null) {
			List<ExtractorEntry> entryList = new ExchangeExtractor().extract(new CountinfoFormator().format(str).strs);
			System.out.println(CountinfoMaker.makeCountinfo(new CountinfoFormator().format(str)));
			for(ExtractorEntry entry : entryList) {
				System.out.println(entry.getOffset()+"\t"+entry.getLength());
				
				ByteArrayOutputStream buffer = null;
				DataOutputStream output = null;
				
				if (output == null) {
					buffer = new ByteArrayOutputStream(512);
					output = new DataOutputStream(buffer);
				}
				
				output.write(entry.getOperation().getOperateId());
				output.writeLong(entry.getTimestamp());
				output.write(entry.getData(), entry.getOffset(), entry.getLength());
				
				
				System.out.println(entry.getUserKey()+"\t"+entry.getTimestamp()+"\t"+buffer.toByteArray().length);
			}
				
		}
		br.close();
	}
	
}