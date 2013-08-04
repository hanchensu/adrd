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
import com.sohu.adrd.data.sessionlog.plugin.util.CMMaker;
import com.sohu.adrd.data.sessionlog.thrift.operation.CMOperation;
import com.sohu.adrd.data.sessionlog.thrift.operation.OperationType;
import com.sohu.adrd.data.sessionlog.util.Extractor;
import com.sohu.adrd.data.sessionlog.util.ExtractorEntry;
import com.sohu.adrd.data.sessionlog.util.ReuseMemoryBuffer;


public class CMExtractor implements Extractor {
	
	
	private TProtocol protocol;
	private int offset = 0;	
	private ReuseMemoryBuffer transport;
	private List<ExtractorEntry> entryList;
	
	public CMExtractor() {
		transport = new ReuseMemoryBuffer(2048);
		protocol = new TBinaryProtocol(transport);
		entryList = new ArrayList<ExtractorEntry>();
	}
	
	public List<ExtractorEntry> extract(List<String> strs) {
		transport.reuse();
		entryList.clear();
		
		CMOperation operation = CMMaker.makeCM(strs);
		
		String userkey = AdrdDataUtil.makeUserId(operation.yyid, operation.suv, null, null);
		
		
		ExtractorEntry entry = new ExtractorEntry();
		entry.setUserKey(userkey);
		entry.setTimestamp(operation.getTimestamp());
		
		OperationType opType = OperationType.CM;
		
		entry.setOperation(opType);
		entry = writeFields(entry, operation);
		
		entryList.add(entry);
		offset = 0;
		return entryList;
	}
	
	public ExtractorEntry writeFields(ExtractorEntry entry, CMOperation operation) {
		try {
			operation.write(protocol);
		} catch (TException e) {
		}
		entry.setValue(transport.getArray(), offset, transport.length() - offset);
		offset = transport.length();
		return entry;
	}
	
	
	public static void main(String args[]) throws IOException {
		
		
	}
	
}
