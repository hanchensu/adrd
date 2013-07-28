package com.sohu.adrd.data.sessionlog.util;

import com.sohu.adrd.data.common.Util;
import com.sohu.adrd.data.sessionlog.thrift.operation.OperationType;


public class ExtractorEntry {
	
	private String userKey = null;
	
	private OperationType operation = null;
	
	private long timestamp = -1;
	private byte data[] = null;
	private int offset = 0, length = 0;
	
	public void setValue(byte data[], int offset, int length) {
		if (data == null || data.length < (offset + length)) {
			throw new RuntimeException("Serializable data corrupt");
		}
		this.data = data;
		this.offset = offset; this.length = length;
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

	public String getUserKey() {
		return userKey;
	}
	
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public OperationType getOperation() {
		return operation;
	}

	public void setOperation(OperationType operation) {
		this.operation = operation;
	}
	
	public boolean check() {
		if (Util.isBlank(userKey) || timestamp < 0 
		|| operation == null || data == null || operation == null) return false;
		return true;
	}
	
}
