package com.sohu.adrd.data.sessionlog.plugin;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryInputTransport;

import com.sohu.adrd.data.common.AdrdDataUtil;
import com.sohu.adrd.data.sessionlog.plugin.util.CountinfoMaker;
import com.sohu.adrd.data.sessionlog.thrift.operation.CountinfoOperation;
import com.sohu.adrd.data.sessionlog.thrift.operation.OperationType;
import com.sohu.adrd.data.sessionlog.util.Extractor;
import com.sohu.adrd.data.sessionlog.util.ExtractorEntry;
import com.sohu.adrd.data.sessionlog.util.ReuseMemoryBuffer;


public class CountinfoExtractor implements Extractor {
	
	
	private TProtocol protocol;
	private int offset = 0;	
	private ReuseMemoryBuffer transport;
	private List<ExtractorEntry> entryList;
	
	public CountinfoExtractor() {
		transport = new ReuseMemoryBuffer(2048);
		protocol = new TBinaryProtocol(transport);
		entryList = new ArrayList<ExtractorEntry>();
	}
	
	public List<ExtractorEntry> extract(List<String> strs) {
		transport.reuse();
		entryList.clear();
		
		CountinfoOperation countinfo = CountinfoMaker.makeCountinfo(strs);
		
		String userkey = AdrdDataUtil.makeUserId(countinfo);
		
		long timestamp = countinfo.getTimestamp();
		
		ExtractorEntry entry = new ExtractorEntry();
		entry.setUserKey(userkey);
		entry.setTimestamp(timestamp);
		
		OperationType opType = AdrdDataUtil.getOpType(countinfo);
		
		entry.setOperation(opType);
		entry = writeFields(entry, countinfo);
		
		entryList.add(entry);	
		return entryList;
	}
	
	public ExtractorEntry writeFields(ExtractorEntry entry, CountinfoOperation info) {
		try {
			info.write(protocol);
		} catch (TException e) {
		}
		entry.setValue(transport.getArray(), offset, transport.length() - offset);
		offset = transport.length();
		return entry;
	}
	
}
