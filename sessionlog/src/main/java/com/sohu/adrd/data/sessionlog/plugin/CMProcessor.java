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
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryInputTransport;

import com.sohu.adrd.data.common.AdrdDataUtil;
import com.sohu.adrd.data.common.Util;
import com.sohu.adrd.data.sessionlog.thrift.operation.CMOperation;
import com.sohu.adrd.data.sessionlog.thrift.operation.OperationType;
import com.sohu.adrd.data.sessionlog.util.Extractor;
import com.sohu.adrd.data.sessionlog.util.ExtractorEntry;
import com.sohu.adrd.data.sessionlog.util.Processor;
import com.sohu.adrd.data.sessionlog.util.ProcessorEntry;
import com.sohu.adrd.data.sessionlog.util.ReuseMemoryBuffer;


public class CMProcessor implements Processor {
	
	
	private TProtocol protocol;
	private TMemoryInputTransport inputTransport;

	private String pre;

	public CMProcessor() {
		inputTransport = new TMemoryInputTransport();
		protocol = new TBinaryProtocol(inputTransport);
	}

	public List<OperationType> acceptTypes() {
		return Arrays.asList(OperationType.CM);
	}

	public Iterator<ProcessorEntry> process(Iterator<ProcessorEntry> it) {
		List<ProcessorEntry> pelist = new ArrayList<ProcessorEntry>();
		CMOperation info = new CMOperation();

		while (it.hasNext()) {
			ProcessorEntry entry = it.next();
			inputTransport.reset(entry.getData(), entry.getOffset(),
					entry.getLength());
			try {

				info.clear();
				info.read(protocol);

				if (pelist.size() == 0) {
					entry.setTag("NORMAL");
					pelist.add(entry);
					pre = info.toString() + entry.getTimestamp(); // logtime and log content
				} else {
					String str = info.toString() + entry.getTimestamp();
					if (str.equals(pre)) { // duplicate in flume
						lastAddOne(pelist); // last entry's repeat in pelist add one
					} else {
						entry.setTag("NORMAL");
						pelist.add(entry);
						pre = info.toString() + entry.getTimestamp();
					}

				}

			} catch (TException e) {
				entry.setTag("DELETE");
			}
		}
		return pelist.iterator();
	}

	private void lastAddOne(List<ProcessorEntry> pelist) throws TException {
		TMemoryInputTransport readTransport = new TMemoryInputTransport();
		TProtocol readProtocol = new TBinaryProtocol(readTransport);
		CMOperation lastinfo = new CMOperation();
		ProcessorEntry lastEntry = pelist.get(pelist.size() - 1);
		readTransport.reset(lastEntry.getData(), lastEntry.getOffset(),
				lastEntry.getLength());
		lastinfo.read(readProtocol);
		lastinfo.repeat++;

		ReuseMemoryBuffer writeTransport = new ReuseMemoryBuffer(2048);
		TProtocol writeProtocol = new TBinaryProtocol(writeTransport);
		lastinfo.write(writeProtocol);
		pelist.get(pelist.size() - 1).setData(writeTransport.getArray(),
				writeTransport.length());
	}
	
}
