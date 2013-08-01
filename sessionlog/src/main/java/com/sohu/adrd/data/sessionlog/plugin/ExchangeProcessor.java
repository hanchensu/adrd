package com.sohu.adrd.data.sessionlog.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryInputTransport;

import com.sohu.adrd.data.sessionlog.thrift.operation.ExOperation;
import com.sohu.adrd.data.sessionlog.thrift.operation.OperationType;
import com.sohu.adrd.data.sessionlog.util.Processor;
import com.sohu.adrd.data.sessionlog.util.ProcessorEntry;
import com.sohu.adrd.data.sessionlog.util.ReuseMemoryBuffer;

public class ExchangeProcessor implements Processor {

	private TProtocol protocol;
	private TMemoryInputTransport inputTransport;

	private String pre;

	public ExchangeProcessor() {
		inputTransport = new TMemoryInputTransport();
		protocol = new TBinaryProtocol(inputTransport);
	}

	public List<OperationType> acceptTypes() {
		return Arrays.asList(OperationType.EXCHANGE);
	}

	public Iterator<ProcessorEntry> process(Iterator<ProcessorEntry> it) {
		List<ProcessorEntry> pelist = new ArrayList<ProcessorEntry>();
		ExOperation info = new ExOperation();

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
		ExOperation lastinfo = new ExOperation();
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
