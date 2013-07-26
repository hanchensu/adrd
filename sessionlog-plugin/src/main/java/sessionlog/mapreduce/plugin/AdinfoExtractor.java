package sessionlog.mapreduce.plugin;

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

import sessionlog.mapreduce.ExtractResult;
import sessionlog.mapreduce.Extractor;
import sessionlog.mapreduce.ExtractorEntry;
import sessionlog.mapreduce.ReuseMemoryBuffer;
import sessionlog.mapreduce.plugin.adinfo.AdInfoDivider;
import sessionlog.op.AdInfoOperation;
import sessionlog.op.OperationType;
import sessionlog.op.PvOperation;
import sessionlog.util.Util;

public class AdinfoExtractor implements Extractor {
	
	
	private TProtocol protocol;
	private int offset;	
	private ReuseMemoryBuffer transport;
	private List<ExtractorEntry> entryList;
	
	public AdinfoExtractor() {
		transport = new ReuseMemoryBuffer(2048);
		protocol = new TBinaryProtocol(transport);
		entryList = new ArrayList<ExtractorEntry>();
	}
	
	public ExtractResult extract(List<String> strs) {
		transport.reuse();
		entryList.clear();
		if (strs == null || strs.size() != AdinfoFormator.TOTAL_COLUMN + 1) return new ExtractResult(null, "err1");
		
		String userkey = chooseUserId(strs);
		
		
		if (userkey == null) {
			System.out.println("case2");
			return new ExtractResult(null, "err2");
		}
		
		long timestamp = Long.parseLong(strs.get(strs.size()-2));
		
		ExtractorEntry entry = new ExtractorEntry();
		entry.setUserKey(userkey);
		entry.setTimestamp(timestamp);
		
		OperationType opType = AdInfoDivider.getOperationType(strs);
		if(opType == null) {
			return new ExtractResult(null, "err3");
		}
		
		AdInfoOperation operation = new AdInfoOperation();
		setValues(operation, strs);
		entry = writeFields(entry, operation);
		entry.setOperation(opType);
		
		entryList.add(entry);
		
		offset = 0;
		return new ExtractResult(entryList, "0");
	}
	
	
	
	private void setValues(AdInfoOperation adinfo, List<String> strs) {
		adinfo.clear();
		String str = strs.get(AdInfoDivider.indexOf("ADID"));
		if (Util.isNotBlank(str)) adinfo.setAdId(str);
		
		str = strs.get(AdInfoDivider.indexOf("ADPID"));
		if (Util.isNotBlank(str)) adinfo.setAdpId(str);
		
		str = strs.get(AdInfoDivider.indexOf("ADPOS"));
		if (Util.isNotBlank(str)) adinfo.setAdPos(str);
		
		str = strs.get(AdInfoDivider.indexOf("ADP_X"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setAdpX(Integer.parseInt(str));
			} catch (NumberFormatException e) {
				adinfo.setAdpX(-123);
			}
		} else {
			adinfo.setAdpX(-234);
		}
		
		str = strs.get(AdInfoDivider.indexOf("ADP_Y"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setAdpY(Integer.parseInt(str));
			} catch (NumberFormatException e) {
				adinfo.setAdpY(-123);
			}
		} else {
			adinfo.setAdpY(-234);
		}
		
		str = strs.get(AdInfoDivider.indexOf("ADTYPE"));
		if (Util.isNotBlank(str)) adinfo.setAdType(str);
		
		str = strs.get(AdInfoDivider.indexOf("BROWSER"));
		if (Util.isNotBlank(str)) adinfo.setBrowser(str);
		
		str = strs.get(AdInfoDivider.indexOf("CLICK_X"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setClickX(Integer.parseInt(str));
			} catch (NumberFormatException e) {
				adinfo.setClickX(-123);
			}
		} else {
			adinfo.setClickX(-234);
		}
		
		str = strs.get(AdInfoDivider.indexOf("CLICK_Y"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setClickY(Integer.parseInt(str));
			} catch (NumberFormatException e) {
				adinfo.setClickY(-123);
			}
		} else {
			adinfo.setClickY(-234);
		}
		
		str = strs.get(AdInfoDivider.indexOf("CONTENT_URL"));
		if (Util.isNotBlank(str)) adinfo.setContentUrl(str);
		
		str = strs.get(AdInfoDivider.indexOf("EXT"));
		if (Util.isNotBlank(str)) adinfo.setExt(str);
		
		str = strs.get(AdInfoDivider.indexOf("FREQ"));
		if (Util.isNotBlank(str)) adinfo.setFreq(str);
		
		str = strs.get(AdInfoDivider.indexOf("GETURL"));
		if (Util.isNotBlank(str)) adinfo.setGetUrl(str);
		
		str = strs.get(AdInfoDivider.indexOf("IMPRESSIONID"));
		if (Util.isNotBlank(str)) adinfo.setImpressionId(str);
		
		str = strs.get(AdInfoDivider.indexOf("LATENCY"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setLatency(Long.parseLong(str));
			} catch (NumberFormatException e) {
				adinfo.setLatency(-123);
			}
		} else {
			adinfo.setLatency(-234);
		}
		
		str = strs.get(AdInfoDivider.indexOf("MONITORKEY"));
		if (Util.isNotBlank(str)) adinfo.setMonitorKey(str);
		
		str = strs.get(AdInfoDivider.indexOf("OS"));
		if (Util.isNotBlank(str)) adinfo.setOs(str);
		
		str = strs.get(AdInfoDivider.indexOf("REFFER"));
		if (Util.isNotBlank(str)) adinfo.setRefer(str);
		
		str = strs.get(AdInfoDivider.indexOf("REGION"));
		if (Util.isNotBlank(str)) adinfo.setRegion(str);
		
		str = strs.get(AdInfoDivider.indexOf("REQTYPE"));
		if (Util.isNotBlank(str)) adinfo.setReqType(str);
		
		str = strs.get(AdInfoDivider.indexOf("RESOLUTION"));
		if (Util.isNotBlank(str)) adinfo.setResolution(str);
		
		str = strs.get(AdInfoDivider.indexOf("SUPPORT_FLASH"));
		if (Util.isNotBlank(str)) adinfo.setSupportFlash(str);
		
		str = strs.get(AdInfoDivider.indexOf("SUV"));
		if (Util.isNotBlank(str)) adinfo.setSuv(str);
		
		str = strs.get(AdInfoDivider.indexOf("TIME"));
		if (Util.isNotBlank(str)) {
			try {
				if (Util.isNotBlank(str)) adinfo.setTimestamp(Long.parseLong(str));
			} catch (NumberFormatException e) {
				adinfo.setTimestamp(-123);		
			}
		} else {
			adinfo.setTimestamp(-234);
			
		}
		
		str = strs.get(AdInfoDivider.indexOf("TURN"));
		if (Util.isNotBlank(str)) adinfo.setTurn(str);
		
		str = strs.get(AdInfoDivider.indexOf("USERAGENT"));
		if (Util.isNotBlank(str)) adinfo.setUserAgent(str);
		
		str = strs.get(AdInfoDivider.indexOf("USERIP"));
		if (Util.isNotBlank(str)) adinfo.setUserIp(str);
		
		str = strs.get(AdInfoDivider.indexOf("YYID"));
		if (Util.isNotBlank(str)) adinfo.setYyId(str);
		
		str = strs.get(AdInfoDivider.indexOf("e"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setE(Double.parseDouble(str));
			} catch (NumberFormatException e) {
				adinfo.setE(0.0);
			}	
		} else {
			adinfo.setE(0.0);
		}
		
		str = strs.get(AdInfoDivider.indexOf("c"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setC(Double.parseDouble(str));
			} catch (NumberFormatException e) {
				adinfo.setC(0.0);
			} 
		} else {
			adinfo.setC(0.0);
		}
		
		str = strs.get(AdInfoDivider.indexOf("PAGEID"));
		if (Util.isNotBlank(str)) adinfo.setPageId(str);
		
		adinfo.setRepeat(1);
		
		str = strs.get(strs.size()-1);
		try {
			adinfo.setStatusCode(Integer.parseInt(str));
		} catch (NumberFormatException e) {
			adinfo.setStatusCode(0);
		} 
		
		str = strs.get(AdInfoDivider.indexOf("ADVERTISERID"));
		if (Util.isNotBlank(str)) adinfo.setAdvertiserId(str);
		
		str = strs.get(AdInfoDivider.indexOf("JS_VERSION"));
		if (Util.isNotBlank(str)) adinfo.setJsVersion(str);
		
		str = strs.get(AdInfoDivider.indexOf("BidPrice"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setBidPrice(Double.parseDouble(str));
			} catch (NumberFormatException e) {
				adinfo.setBidPrice(0.0);
			}	
		} else {
			adinfo.setBidPrice(0.0);
		}
		
		str = strs.get(AdInfoDivider.indexOf("BidPrice2"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setBidPrice2(Double.parseDouble(str));
			} catch (NumberFormatException e) {
				adinfo.setBidPrice2(0.0);
			}	
		} else {
			adinfo.setBidPrice2(0.0);
		}
		
		str = strs.get(AdInfoDivider.indexOf("CTR"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setCtr(Double.parseDouble(str));
			} catch (NumberFormatException e) {
				adinfo.setCtr(0.0);
			}	
		} else {
			adinfo.setCtr(0.0);
		}
		
		str = strs.get(AdInfoDivider.indexOf("CTR2"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setCtr2(Double.parseDouble(str));
			} catch (NumberFormatException e) {
				adinfo.setCtr2(0.0);
			}	
		} else {
			adinfo.setCtr2(0.0);
		}
		
		str = strs.get(AdInfoDivider.indexOf("eCPM"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setECPM(Double.parseDouble(str));
			} catch (NumberFormatException e) {
				adinfo.setECPM(0.0);
			}	
		} else {
			adinfo.setECPM(0.0);
		}
		
		str = strs.get(AdInfoDivider.indexOf("eCPM2"));
		if (Util.isNotBlank(str)) {
			try {
				adinfo.setECPM2(Double.parseDouble(str));
			} catch (NumberFormatException e) {
				adinfo.setECPM2(0.0);
			}	
		} else {
			adinfo.setECPM2(0.0);
		}
		
		str = strs.get(AdInfoDivider.indexOf("BidType"));
		if (Util.isNotBlank(str)) adinfo.setBidType(str);
		
		str = strs.get(AdInfoDivider.indexOf("BidType2"));
		if (Util.isNotBlank(str)) adinfo.setBidType2(str);	
		
		str = strs.get(AdInfoDivider.indexOf("ADGROUPID_MK"));
		if (Util.isNotBlank(str)) adinfo.setAdgroupMK(str);
		
		str = strs.get(AdInfoDivider.indexOf("ADVERTISERID_MK"));
		if (Util.isNotBlank(str)) adinfo.setAdvertiserIdMK(str);
		
		str = strs.get(AdInfoDivider.indexOf("AdScore"));
		if (Util.isNotBlank(str)) adinfo.setAdScore(str);
		
		str = strs.get(AdInfoDivider.indexOf("CAMPAIGNID_MK"));
		if (Util.isNotBlank(str)) adinfo.setCampaignIdMK(str);
		
		str = strs.get(AdInfoDivider.indexOf("ED_CONTENT"));
		if (Util.isNotBlank(str)) adinfo.setEdContent(str);
		
		str = strs.get(AdInfoDivider.indexOf("ED_STATUS"));
		if (Util.isNotBlank(str)) adinfo.setEdStatus(str);
		
		str = strs.get(AdInfoDivider.indexOf("LINEID_MK"));
		if (Util.isNotBlank(str)) adinfo.setLineIdMK(str);
		
		str = strs.get(AdInfoDivider.indexOf("MATERIAL_MK"));
		if (Util.isNotBlank(str)) adinfo.setMaterialMK(str);
		
	}
	
	public ExtractorEntry writeFields(ExtractorEntry entry, AdInfoOperation info) {
		try {
			info.write(protocol);
		} catch (TException e) {
		}
		entry.setValue(transport.getArray(), offset, transport.length() - offset);
		offset = transport.length();
		return entry;
	}
	

	private String chooseUserId(List<String> strs) {
		String str = strs.get(AdInfoDivider.indexOf("YYID"));
		if (Util.isNotBlank(str)) return str;
		str = strs.get(AdInfoDivider.indexOf("SUV"));
		if (Util.isNotBlank(str)) return str;
		str = strs.get(AdInfoDivider.indexOf("USERIP"));
		String agent = strs.get(AdInfoDivider.indexOf("USERAGENT"));
		if (Util.isNotBlank(str)) {
			String s = Util.isNotBlank(agent) ? str + agent : str;
			s = Util.getMD5(s);
			if (s == null) return null;
			else return s;
		}
		return null;
	}
	
	@Override
	public String getTypeId(List<String> strs) {
		
		StringBuilder sb = new StringBuilder();
		
		String str = strs.get(AdInfoDivider.indexOf("YYID"));  //yyid
		if (Util.isNotBlank(str)) {
			sb.append("1");
		} else {
			sb.append("0");
		}
		
		str = strs.get(AdInfoDivider.indexOf("SUV"));  //suv
		if (Util.isNotBlank(str)) {
			sb.append("1");
		} else {
			sb.append("0");
		}
		
		str = strs.get(AdInfoDivider.indexOf("USERIP"));  //ip
		if (Util.isNotBlank(str)) {
			sb.append("1");
		} else {
			sb.append("0");
		}
		
		str = strs.get(AdInfoDivider.indexOf("USERAGENT")); //ua
		
		if (Util.isNotBlank(str)) {
			sb.append("1");
		} else {
			sb.append("0");
		}
		
		String logType = "ClickOrView";
		
		return logType + "_" + sb.toString();
	}
}
