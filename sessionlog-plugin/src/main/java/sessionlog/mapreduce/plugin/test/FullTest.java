package sessionlog.mapreduce.plugin.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryBuffer;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.apache.thrift.transport.TTransport;

import sessionlog.mapreduce.ExtractorEntry;
import sessionlog.mapreduce.plugin.AdinfoExtractor;
import sessionlog.mapreduce.plugin.AdinfoFormator;
import sessionlog.op.AdInfoOperation;

public class FullTest {
	
	public static void main(String args[]) throws Exception {
		
		TMemoryBuffer trans = new TMemoryBuffer(2048);
		TProtocol protocol = new TBinaryProtocol(trans);
		AdInfoOperation adInfo = new AdInfoOperation();
		adInfo.adId = "12345";
		adInfo.write(protocol);
		System.out.println(trans.length());
		for(int i = 0; i < trans.length(); i++) {
			System.out.print(trans.getArray()[i]);
		}
		
		byte[] empDtl = null;
        TSerializer serializer = new TSerializer();
        empDtl = serializer.serialize(adInfo);
        System.out.println("Serialized thrift object : "+empDtl);
		
        for(int i = 0; i < empDtl.length; i++) {
			System.out.print(empDtl[i]);
		}
		System.out.println();
//		
		AdInfoOperation info = new AdInfoOperation();
		info.adId = "12345";
		
		final TSerializer serializer2 = new TSerializer(new TBinaryProtocol.Factory());
		
		byte[] byteArray = null;
		try {
			byteArray = serializer2.serialize(adInfo);
		} catch (TException e) {
			e.printStackTrace();
		}
		
		final TDeserializer deserializer = new TDeserializer(new TBinaryProtocol.Factory());
		
		final String base64String = DatatypeConverter.printBase64Binary(byteArray);
		String abc = new String(byteArray);
		System.out.println("abc: " + abc);

	}

}
