package sessionlog.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.zebra.io.BasicTable;
import org.apache.hadoop.zebra.io.TableScanner;
import org.apache.hadoop.zebra.types.TypesUtils;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryInputTransport;

import sessionlog.op.AdInfoOperation;
import sessionlog.op.PvOperation;
import sessionlog.op.SearchOperation;
import sessionlog.util.Util;

public class Reader {
	
	public static void main(String[] args) throws Exception {
		boolean flag = true;
		//if (flag) return;
	    Configuration conf = new Configuration();
	    //conf.set("fs.default.name", "hdfs://192.168.132.197:9000");
	    Path path = new Path("file:///Users/mac/SUB-0031");
	    BasicTable.Reader reader = new BasicTable.Reader(path, conf);
		TableScanner scanner = reader.getScanner(null, true);
		Tuple row = TypesUtils.createTuple(3);
		TProtocol protocol = null;
		TMemoryInputTransport inputTransport = new TMemoryInputTransport();
		protocol = new TBinaryProtocol(inputTransport);
		
		//UserWritable writeable = new UserWritable();
		int index = 0;
		String astr = "001906f2b384ff57eb7b6c0b8537139d";
		try {
			while (!scanner.atEnd()) {
				flag = false;
				StringBuffer sb = new StringBuffer();
				scanner.getValue(row);
				scanner.advance();
				String uerId = (String)row.get(0);
				if (uerId.equals(astr)) flag = false;
				//writeable.id = 123;
				//writeable.Username = uerId;
				
				//DataByteArray kwData = (DataByteArray)row.get(3);
				//String keyword = new String(kwData.get(), 0, kwData.size(), "UTF-8");
				sb.append(uerId).append(":");
				DataByteArray pvData = (DataByteArray)row.get(1);
				DataByteArray searchData = (DataByteArray)row.get(2);
				//DataByteArray adDisplayData = (DataByteArray)row.get(4);
				//DataByteArray adClickData = (DataByteArray)row.get(3);
				byte buffer[] = null;
				int length = 0;
				//List<Serializable> pvs = new ArrayList<Serializable>();
				if (pvData != null && (length = pvData.size()) > 0) {
					sb.append("[");
					buffer = pvData.get();
					inputTransport.reset(buffer, 0, length);
					AdInfoOperation pv = new AdInfoOperation();
			        while (inputTransport.getBufferPosition() < length - 1) {
			        	pv.clear();
			            pv.read(protocol);
			            if (pv.getYyId() != null) {
			            	if (!pv.getYyId().equals(uerId)) {
			            	    flag = false;
			            	}
			            } else if (pv.getSuv() != null) {
			            	if (!pv.getSuv().equals(uerId)) {
			            	    flag = false;
			            	}
			            } else {
			            	String ip = pv.getUserIp();
			            	String agent = pv.getUserAgent();
			            	String str = "";
			            	if (ip != null) str = ip;
			            	if (agent != null) str += agent;
			            	if (!Util.getMD5(str).equals(uerId)) {
			            		flag = false;
			            	}
			            }
			            sb.append(pv);
			            //pvs.add(pv);
			        }
			        sb.append("]");
				}
				//writeable.OperationHash.put("pv", pvs);
				//List<Serializable> searchs = new ArrayList<Serializable>();
                if (searchData != null && (length = searchData.size()) > 0) {
                	sb.append("[");
					buffer = searchData.get();
					inputTransport.reset(buffer, 0, length);
					AdInfoOperation search = new AdInfoOperation();
			        while (inputTransport.getBufferPosition() < length - 1) {
			        	search.clear();
			        	search.read(protocol);
			        	if (search.getYyId() != null) {
			            	if (!search.getYyId().equals(uerId)) {
			            	    flag = false;
			            	}
			            } else if (search.getSuv() != null) {
			            	if (!search.getSuv().equals(uerId)) {
			            	    flag = false;
			            	}
			            } else {
			            	String ip = search.getUserIp();
			            	String agent = search.getUserAgent();
			            	String str = "";
			            	if (ip != null) str = ip;
			            	if (agent != null) str += agent;
			            	if (!Util.getMD5(str).equals(uerId)) {
			            		flag = false;
			            	}
			            }
			        	//System.out.println(uerId + " : " + search.getKeywords());
			            sb.append(search);
			            //searchs.add(search);
			        }
			        sb.append("]");
				}
                //writeable.OperationHash.put("search", searchs);
                //List<Serializable> adDisplays = new ArrayList<Serializable>();
                /*
                if (adDisplayData != null && (length = adDisplayData.size()) > 0) {
                	sb.append("[");
					buffer = adDisplayData.get();
					inputTransport.reset(buffer, 0, length);
					AdDisplayOperation search = new AdDisplayOperation();
			        while (inputTransport.getBufferPosition() < length - 1) {
			        	search.clear();
			        	search.read(protocol);
			            sb.append(search);
			            //adDisplays.add(search);
			        }
			        sb.append("]");
			        //flag = true;
				}
                //writeable.OperationHash.put("adclick", adDisplays);
                if (adClickData != null && (length = adClickData.size()) > 0) {
                	sb.append("[");
					buffer = adClickData.get();
					inputTransport.reset(buffer, 0, length);
					AdClickOperation search = new AdClickOperation();
			        while (inputTransport.getBufferPosition() < length - 1) {
			        	search.clear();
			        	search.read(protocol);
			            sb.append(search);
			        }
			        sb.append("]");
			        //flag = true;
				}
                //if (!tempStr.equals(keyword)) {
                	//throw new RuntimeException(keyword + "," + tempStr);
                //}
                 * */
				if (!flag) System.out.println(index + " " + sb.toString());
				/*
				DataOutputStream output = new DataOutputStream(new FileOutputStream("/Users/mac/Desktop/wdx.txt"));
				writeable.write(output);
				output.flush();
				output.close();
				DataInputStream input = new DataInputStream(new FileInputStream("/Users/mac/Desktop/wdx.txt"));
				UserWritable newwriteable = new UserWritable();
				newwriteable.readFields(input);
				System.out.println(newwriteable);
				break;
				*/
				index++;
			}
		} finally {
			scanner.close();
		}
	}

}
