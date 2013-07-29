package com.sohu.adrd.data.sessionlog.util;

import com.sohu.adrd.data.common.AdrdDataUtil;


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
	
	public boolean less(ProcessorEntry pe) {
		if(this.getTimestamp() < pe.getTimestamp())
			return true;
		if(this.getTimestamp() == pe.getTimestamp() && AdrdDataUtil.compareTo(this.getData(), this.getOffset(), this.getLength(), pe.getData(), pe.getOffset(), pe.getLength()) < 0 )
			return true;
		return false;
		
	}
	
	public boolean greater(ProcessorEntry pe) {
		if(this.getTimestamp() > pe.getTimestamp())
			return true;
		if(this.getTimestamp() == pe.getTimestamp() && AdrdDataUtil.compareTo(this.getData(), this.getOffset(), this.getLength(), pe.getData(), pe.getOffset(), pe.getLength()) > 0 )
			return true;
		return false;
		
	}
	
	public boolean equals(ProcessorEntry pe) {
		if(pe.getTimestamp() == this.getTimestamp() && AdrdDataUtil.compareTo(this.getData(), this.getOffset(), this.getLength(), pe.getData(), pe.getOffset(), pe.getLength()) == 0 )
			return true;
		return false;
		
	}
	
	
}
