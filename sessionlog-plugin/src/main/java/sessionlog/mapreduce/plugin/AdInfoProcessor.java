package sessionlog.mapreduce.plugin;

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

import sessionlog.mapreduce.Processor;
import sessionlog.mapreduce.ProcessorEntry;
import sessionlog.mapreduce.ReuseMemoryBuffer;
import sessionlog.op.AdInfoOperation;
import sessionlog.op.OperationType;
import sessionlog.op.PvOperation;

public class AdInfoProcessor implements Processor {

	private TProtocol protocol;
	private TMemoryInputTransport inputTransport;

	private AdInfoOperation info = new AdInfoOperation();
	private String pre;

	public AdInfoProcessor() {
		inputTransport = new TMemoryInputTransport();
		protocol = new TBinaryProtocol(inputTransport);
	}

	public List<OperationType> acceptTypes() {
		return Arrays.asList(OperationType.AD_CLICK, OperationType.AD_DISPLAY,
				OperationType.NEWS_CLICK, OperationType.NEWS_DISPLAY,
				OperationType.HB_CLICK, OperationType.HB_DISPLAY, OperationType.ARRIVE);
	}

	public Iterator<ProcessorEntry> process(Iterator<ProcessorEntry> it) {
		List<ProcessorEntry> pelist = new ArrayList<ProcessorEntry>();

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
					pre = info.toString() + entry.getTimestamp(); // logtime and
																	// log
																	// content
				} else {
					String str = info.toString() + entry.getTimestamp();
					if (str.equals(pre)) { // duplicate in flume
						lastAddOne(pelist); // last entry's repeat in pelist add
											// one
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
		AdInfoOperation lastinfo = new AdInfoOperation();
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