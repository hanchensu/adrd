package sessionlog.mapreduce.plugin;

import java.util.ArrayList;
import java.util.List;

//import org.apache.hadoop.hbase.filter.Filter.ReturnCode;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

import sessionlog.mapreduce.ExtractResult;
import sessionlog.mapreduce.Extractor;
import sessionlog.mapreduce.ExtractorEntry;
import sessionlog.mapreduce.ReuseMemoryBuffer;
import sessionlog.op.OperationType;
import sessionlog.op.PvOperation;
import sessionlog.op.SearchOperation;
import sessionlog.util.Util;

public class PvlogExtractor implements Extractor {
	
	private TProtocol protocol;
	private ReuseMemoryBuffer transport;
	private List<ExtractorEntry> entryList;
	private int offset;	

	public PvlogExtractor() {
		transport = new ReuseMemoryBuffer(1024);
		protocol = new TBinaryProtocol(transport);
		entryList = new ArrayList<ExtractorEntry>();
		offset = 0;
	}
	
	public ExtractResult extract(List<String> strs) {
		transport.reuse();
		entryList.clear();
		offset = 0;
		String userkey = "";
		long timestamp = Long.parseLong(strs.get(5));
		String[] urlstr = new String[2];
		userkey = chooseUserId(strs);
		if (userkey == null) {
			return new ExtractResult(null, "err1");
		}
		urlstr[0] = strs.get(6);
		urlstr[1] = strs.get(7);
		for (int i = 0; i < 2; i++) {
			if (urlstr[i] != null) {
				ExtractorEntry entry = new ExtractorEntry();
				entry.setUserKey(userkey);
				entry.setTimestamp(timestamp);
				int flag = 1;
				switch (flag) {
				case 1:
					String[] parseresult = new String[2];
					parseresult = ParseURLKeywordDomain.getKeywordAndDomain(urlstr[i]);
					if (parseresult != null && parseresult[0] != "") {
						entry.setOperation(OperationType.SEARCH);
						SearchOperation search = new SearchOperation();
						try {
							search.setStatusCode(Integer.parseInt(strs.get(strs.size()-1)));
						}catch(NumberFormatException e) {
							search.setStatusCode(0);
						}
						int tmp = 0;
						for (int j = 1; j < 8; j++) {
							if (j == 2) continue;
							if (j != 7)
								search.setFieldValue(SearchOperation._Fields.findByThriftId(j), strs.get(tmp));
							else
								search.setFieldValue(SearchOperation._Fields
								.findByThriftId(j), Long.parseLong(strs.get(tmp)));
							tmp++;
						}
						search.setFieldValue(SearchOperation._Fields.findByThriftId(8), parseresult[1]);
						search.setFieldValue(SearchOperation._Fields.findByThriftId(9), parseresult[0]);
						entry = writeSearchFields(entry, search);
						entryList.add(entry);
						break;
					}
					if (parseresult != null){
						break;
					}
				case 2:
					PvOperation pv = new PvOperation();
					try {
						pv.setStatusCode(Integer.parseInt(strs.get(strs.size()-1)));
					} catch (NumberFormatException e) {
						pv.setStatusCode(0);
					}
					int tmp = 0;
					for (int j = 1; j < 8; j++) {
						if (j==2) continue;
						if (j != 7)
							pv.setFieldValue(PvOperation._Fields.findByThriftId(j), strs.get(tmp));
						else
							pv.setFieldValue(PvOperation._Fields
							.findByThriftId(j), Long.parseLong(strs.get(tmp)));
						tmp++;
					}
					if(i==1) {pv.setRefUrl(urlstr[0]);pv.setFlag((byte)1);};//for splited refurl,urlstr[0]:refurl and flag:1 add By SunWenbao@2012/11/15
					
					if (urlstr[i].length() <=4 || !urlstr[i].substring(0, 4).equals("http")) break;
					entry.setOperation(OperationType.PV);
					pv.setFieldValue(PvOperation._Fields.findByThriftId(8), urlstr[i]);
					entry = writePVFields(entry, pv);
					entryList.add(entry);
					break;
				default:
					break;
				}
			}
		}
		return new ExtractResult(entryList, "0");
	}

//	public List<ExtractorEntry> extract(List<String> strs) {
//		transport.reuse();
//		entryList.clear();
//		offset = 0;
//		String userkey = "";
//		long timestamp = Long.parseLong(strs.get(5));
//		String[] urlstr = new String[2];
//		userkey = chooseUserId(strs);
//		if (userkey == null) {
//			return null;
//		}
//		urlstr[0] = strs.get(6);
//		urlstr[1] = strs.get(7);
//		for (int i = 0; i < 2; i++) {
//			if (urlstr[i] != null) {
//				ExtractorEntry entry = new ExtractorEntry();
//				entry.setUserKey(userkey);
//				entry.setTimeStamp(timestamp);
//				
//				//treated as search
//				String[] parseresult = new String[2];
//				parseresult = ParseURLKeywordDomain.getKeywordAndDomain(urlstr[i]);
//				if (parseresult != null && parseresult[0] != "") {
//					entry.setOperation(OperationType.SEARCH);
//					SearchOperation search = new SearchOperation();
//					int tmp = 0;
//					for (int j = 1; j < 8; j++) {
//						if (j == 2) continue;
//						if (j != 7)
//							search.setFieldValue(SearchOperation._Fields.findByThriftId(j), strs.get(tmp));
//						else
//							search.setFieldValue(SearchOperation._Fields.findByThriftId(j), Long.parseLong(strs.get(tmp)));
//						tmp++;
//					}
//					search.setFieldValue(SearchOperation._Fields.findByThriftId(8), parseresult[1]);
//					search.setFieldValue(SearchOperation._Fields.findByThriftId(9), parseresult[0]);
//					entry = writeSearchFields(entry, search);
//					entryList.add(entry);
//					continue;
//				}
//				
//				//treated as pv
//				PvOperation pv = new PvOperation();
//				int tmp = 0;
//				for (int j = 1; j < 8; j++) {
//					if (j==2) continue;
//					if (j != 7)
//						pv.setFieldValue(PvOperation._Fields.findByThriftId(j), strs.get(tmp));
//					else
//						pv.setFieldValue(PvOperation._Fields
//						.findByThriftId(j), Long.parseLong(strs.get(tmp)));
//					tmp++;
//				}
//				if(i==1) {pv.setRefUrl(urlstr[0]);pv.setFlag((byte)1);};//for splited refurl,urlstr[0]:refurl and flag:1 add By SunWenbao@2012/11/15
//				
//				if (urlstr[i].length() <= 4 || !urlstr[i].substring(0, 4).equals("http")) break;
//				entry.setOperation(OperationType.PV);
//				pv.setFieldValue(PvOperation._Fields.findByThriftId(8), urlstr[i]);
//				entry = writePVFields(entry, pv);
//				entryList.add(entry);
//			}
//		}
//		return entryList;
//	}

	public ExtractorEntry writeSearchFields(ExtractorEntry entry, SearchOperation search) {
		try {
			search.write(protocol);
		} catch (TException e) {
		}
		entry.setValue(transport.getArray(), offset, transport.length() - offset);
		offset = transport.length();
		return entry;
	}

	public ExtractorEntry writePVFields(ExtractorEntry entry, PvOperation pv) {
		try {
			pv.write(protocol);
		} catch (TException e) {
		}
		entry.setValue(transport.getArray(), offset, transport.length() - offset);
		offset = transport.length();
		return entry;
	}

	public static String chooseUserId(List<String> result) {
		String yyid = result.get(0);
		String suv = result.get(1);
		String ip = result.get(2);
		String useragent = result.get(3);
		if (Util.isNotBlank(yyid)) return yyid;
		else if (Util.isNotBlank(suv)) return suv;
		else if (Util.isNotBlank(ip)) {
			String s = Util.isNotBlank(useragent) ? ip + useragent : ip;
			s = Util.getMD5(s);
			if (s == null) return null;
			else return s;
		} else return null;
	}

	@Override
	public String getTypeId(List<String> strs) {
		
		StringBuilder sb = new StringBuilder();
		
		String str = strs.get(0);  //yyid
		if (Util.isNotBlank(str)) {
			sb.append("1");
		} else {
			sb.append("0");
		}
		
		str = strs.get(1);  //suv
		if (Util.isNotBlank(str)) {
			sb.append("1");
		} else {
			sb.append("0");
		}
		
		str = strs.get(2);  //ip
		if (Util.isNotBlank(str)) {
			sb.append("1");
		} else {
			sb.append("0");
		}
		
		str = strs.get(3); //ua
		
		if (Util.isNotBlank(str)) {
			sb.append("1");
		} else {
			sb.append("0");
		}
		
		return  "Pv_" + sb.toString();
	}

}
