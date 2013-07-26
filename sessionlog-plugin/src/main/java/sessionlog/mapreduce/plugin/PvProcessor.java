package sessionlog.mapreduce.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryInputTransport;

import sessionlog.mapreduce.Processor;
import sessionlog.mapreduce.ProcessorEntry;
import sessionlog.op.OperationType;
import sessionlog.op.PvOperation;

public class PvProcessor implements Processor {

	private TProtocol protocol;
	private TMemoryInputTransport inputTransport;
	private Map<String, Long> urlindex = new HashMap<String, Long>();
	private PvOperation pv = new PvOperation();

	public PvProcessor() {
		inputTransport = new TMemoryInputTransport();
		protocol = new TBinaryProtocol(inputTransport);
	}

	public List<OperationType> acceptTypes() {
		return Arrays.asList(OperationType.PV);
	}

	public Iterator<ProcessorEntry> process(Iterator<ProcessorEntry> it) {
		urlindex.clear();
		List<ProcessorEntry> pelist = new ArrayList<ProcessorEntry>();
		long threshold = 60;

		while (it.hasNext()) {
			ProcessorEntry entry = it.next();
			inputTransport.reset(entry.getData(), entry.getOffset(), entry
					.getLength());
			try {
				pv.clear();
				pv.read(protocol);
				String url = pv.getUrl();
				long time = pv.getTimestamp();
				byte pvFlag = pv.getFlag();
				if (urlindex.get(url) != null) {
					long pretime = urlindex.get(url);
					long interval = time - pretime;
					if (interval <= threshold && (pvFlag == 0)) {
						entry.setTag("DELETE");
					} else {
						entry.setTag("NORMAL");
						urlindex.put(url, time);
					}
				} else {
					entry.setTag("NORMAL");
					urlindex.put(url, time);
				}

			} catch (TException e) {
				entry.setTag("DELETE");
			}
			pelist.add(entry);
		}
		return pelist.iterator();
	}

}

