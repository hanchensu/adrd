package com.sohu.adrd.data.sessionlog.plugin;

import org.apache.hadoop.io.BytesWritable;

import com.sohu.adrd.data.sessionlog.util.IdCreater;


public class ASCIdCreater implements IdCreater {
	
	private long id = 0;
	private byte longBuffer[] = new byte[8];
	private BytesWritable key = new BytesWritable(longBuffer);
	
	public BytesWritable nextId(String userid) {
		transfer(id++, longBuffer);
		return key;
	}
	
	public static void transfer(long v, byte writeBuffer[]) {
		writeBuffer[0] = (byte)(v >>> 56);
        writeBuffer[1] = (byte)(v >>> 48);
        writeBuffer[2] = (byte)(v >>> 40);
        writeBuffer[3] = (byte)(v >>> 32);
        writeBuffer[4] = (byte)(v >>> 24);
        writeBuffer[5] = (byte)(v >>> 16);
        writeBuffer[6] = (byte)(v >>>  8);
        writeBuffer[7] = (byte)(v >>>  0);
	}
	
	public static void main(String args[]) {
		ASCIdCreater idCreater = new ASCIdCreater();
		for(int i = 0; i < 5; i++) {
			
			BytesWritable key = idCreater.nextId("bsd");
			System.out.println(idCreater.id+"__"+key);
//			System.out.println(key);
		}
	}

}
