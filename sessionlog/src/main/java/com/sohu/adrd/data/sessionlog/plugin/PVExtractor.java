package com.sohu.adrd.data.sessionlog.plugin;

import java.util.ArrayList;
import java.util.List;

//import org.apache.hadoop.hbase.filter.Filter.ReturnCode;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

import com.sohu.adrd.data.common.AdrdDataUtil;
import com.sohu.adrd.data.common.Util;
import com.sohu.adrd.data.sessionlog.plugin.util.ParseURLKeywordDomain;
import com.sohu.adrd.data.sessionlog.thrift.operation.OperationType;
import com.sohu.adrd.data.sessionlog.thrift.operation.PVOperation;
import com.sohu.adrd.data.sessionlog.thrift.operation.SearchOperation;
import com.sohu.adrd.data.sessionlog.util.Extractor;
import com.sohu.adrd.data.sessionlog.util.ExtractorEntry;
import com.sohu.adrd.data.sessionlog.util.ReuseMemoryBuffer;


public class PVExtractor implements Extractor {
	
	private TProtocol protocol;
	private ReuseMemoryBuffer transport;
	private List<ExtractorEntry> entryList;
	private int offset;	

	public PVExtractor() {
		transport = new ReuseMemoryBuffer(1024);
		protocol = new TBinaryProtocol(transport);
		entryList = new ArrayList<ExtractorEntry>();
		offset = 0;
	}
	
	public List<ExtractorEntry> extract(List<String> strs) {
		transport.reuse();
		entryList.clear();
		offset = 0;
		String userkey = "";
		long timestamp = Long.parseLong(strs.get(5));
		String[] urlstr = new String[2];
		
		userkey = chooseUserId(strs);
		
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
					PVOperation pv = new PVOperation();
					try {
						pv.setStatusCode(Integer.parseInt(strs.get(strs.size()-1)));
					} catch (NumberFormatException e) {
						pv.setStatusCode(0);
					}
					int tmp = 0;
					for (int j = 1; j < 8; j++) {
						if (j==2) continue;
						if (j != 7)
							pv.setFieldValue(PVOperation._Fields.findByThriftId(j), strs.get(tmp));
						else
							pv.setFieldValue(PVOperation._Fields
							.findByThriftId(j), Long.parseLong(strs.get(tmp)));
						tmp++;
					}
					if(i==1) {pv.setRefUrl(urlstr[0]);pv.setFlag((byte)1);};//for splited refurl,urlstr[0]:refurl and flag:1 add By SunWenbao@2012/11/15
					
					if (urlstr[i].length() <=4 || !urlstr[i].substring(0, 4).equals("http")) break;
					entry.setOperation(OperationType.PV);
					pv.setFieldValue(PVOperation._Fields.findByThriftId(8), urlstr[i]);
					entry = writePVFields(entry, pv);
					entryList.add(entry);
					break;
				default:
					break;
				}
			}
		}
		return entryList;
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

	public ExtractorEntry writePVFields(ExtractorEntry entry, PVOperation pv) {
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
		return AdrdDataUtil.makeUserId(yyid, suv, ip, useragent);
	}

}
