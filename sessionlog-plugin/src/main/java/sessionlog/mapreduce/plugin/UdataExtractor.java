package sessionlog.mapreduce.plugin;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryInputTransport;

import sessionlog.mapreduce.ExtractResult;
import sessionlog.mapreduce.Extractor;
import sessionlog.mapreduce.ExtractorEntry;
import sessionlog.mapreduce.ReuseMemoryBuffer;
import sessionlog.op.OperationType;
import sessionlog.op.SearchOperation;
import sessionlog.util.Util;

public class UdataExtractor implements Extractor {
	
	private TProtocol protocol;
	private ReuseMemoryBuffer transport;
	private List<ExtractorEntry> entryList;
	private int offset;
	private Random random;
	private SearchOperation search = new SearchOperation();
	
	public static void main(String args[]) throws Exception {
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(new FileInputStream("/opt/key/output/decrudata.20121101.1100")));
		String str = br.readLine();
		int number = 0;
		String userKey = null;
		ByteArrayOutputStream buffer = null;
		DataOutputStream output = null;
		UdataExtractor extractor = new UdataExtractor();
		UdataFormator formator = new UdataFormator();
		while (str != null) {
		    //System.out.println(str);
			List<String> strs = formator.format(str).strs;
			if (strs != null) {
				List<ExtractorEntry> entries = extractor.extract(strs).res;
				if (entries != null) {
					for (ExtractorEntry entry : entries) {
						if (entry == null || !entry.check()) continue;
						if (output == null) {
							buffer = new ByteArrayOutputStream(512);
							output = new DataOutputStream(buffer);
						}
						userKey = entry.getUserKey();
						output.write(entry.getOperation().getOperateId());
						output.writeLong(entry.getTimestamp());
						output.write(entry.getData(), entry.getOffset(), entry.getLength());
						TProtocol protocol = null;
						TMemoryInputTransport inputTransport = new TMemoryInputTransport();
						protocol = new TBinaryProtocol(inputTransport);
						inputTransport.reset(buffer.toByteArray(), 9, buffer.size() - 9);
						if (entry.getOperation().getOperateId() == 2) {
							SearchOperation pv = new SearchOperation();
							pv.read(protocol);
							System.out.println(userKey + " : " + pv.toString());
						} else {
							System.out.println("error...");
						}
						if (inputTransport.getBufferPosition() != buffer.size()) {
							System.out.println("error");
						}
						buffer.reset();
					}
				}
			}
			str = br.readLine();
			number++;
		}
	}
	
	public UdataExtractor() {
		transport = new ReuseMemoryBuffer(1024);
		protocol = new TBinaryProtocol(transport);
		entryList = new ArrayList<ExtractorEntry>();
		offset = 0;
		random = new Random();
	}
	
	public ExtractResult extract(List<String> strs) {
		transport.reuse();
		entryList.clear();
		offset = 0;
		if (strs == null || strs.size() == 0) return new ExtractResult(null, "err1");
		for (String line : strs) {
			if (Util.isBlank(line)) continue;
			int index = line.indexOf(":");
			if (index > 0 && index < (line.length() - 1)) {
				String userid = line.substring(0, index);
				String rest = line.substring(index + 1);
				String parts[] = rest.split(",");
				if (parts != null && parts.length > 0) {
					for (String part : parts) {
						if (Util.isBlank(part)) continue;
						String subPart[] = part.split("\t");
						if (subPart != null && subPart.length == 3) {
							if (Util.isBlank(subPart[0]) || Util.isBlank(subPart[1])
							|| Util.isBlank(subPart[2])) continue;
							Date date = null;
							int number = 0;
							try {
								number = Integer.parseInt(subPart[0]);
								date = Util.udataFormat().parse(subPart[1]);
							} catch (ParseException e) {
								continue;
							} catch (NumberFormatException ne) {
								continue;
							}
                            if (number <= 0) number = 1;
                            for (int i = 0; i < number; i++) {
								long time = date.getTime();
								time = time / 1000;
								time += 28800 + random.nextInt(57600);
								ExtractorEntry entry = new ExtractorEntry();
								entry.setUserKey(userid);
								entry.setTimestamp(time);
								entry.setOperation(OperationType.SEARCH);
								search.clear();
								search.setYyid(userid);
								search.setTimestamp(time);
								search.setKeywords(subPart[2]);
								entry = writeSearchFields(entry, search);
								entryList.add(entry);
                            }
						}
					}
				}
			}
		}
		return new ExtractResult(entryList, "0");
	}
	
	public ExtractorEntry writeSearchFields(ExtractorEntry entry, SearchOperation search) {
		try {
			search.write(protocol);
		} catch (TException e) {
		}
		entry.setValue(transport.getArray(), offset, transport.length() - offset);
		offset = transport.length();
		return entry;
	}

	@Override
	public String getTypeId(List<String> strs) {
		return "Uata";
	}
	
}
