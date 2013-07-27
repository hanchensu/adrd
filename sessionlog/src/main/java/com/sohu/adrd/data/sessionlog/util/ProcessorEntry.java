package com.sohu.adrd.data.sessionlog.util;

import java.util.Arrays;

public class ProcessorEntry {
	
	private String tag = "";
	
	private long timestamp = 0;
	private byte data[] = null;
	private int offset = 0, length = 0;
	
	public static String DELETE_TAG = "DELETE";
	public static String NORMAL_TAG = "NORMAL";
	
	public ProcessorEntry(long time, byte data[], int offset, int length) {
		if (time < 0 || data == null || data.length < (offset + length)) {
			throw new RuntimeException("Conrrupt mapper load data");
		}
		this.timestamp = time;
		this.data = data;
		this.offset = offset; this.length = length;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimeStamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public byte[] getData() {
		return data;
	}

	public int getOffset() {
		return offset;
	}

	public int getLength() {
		return length;
	}
	
	public void setData(byte[] data,int length) {
		for(int i = 0; i < length; i++) {
			this.data[i+9] = data[i];
		}
	}
	
}
