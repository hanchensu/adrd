package com.sohu.adrd.data.sessionlog.config;

public class ZebraConfig {
	
	private String schema = null;
	private String storage = null;
	private int part = 1000000;
	private SortConfig sortConfig = null;
	
	public String getSchema() {
		return schema;
	}
	
	public void setSchema(String schema) {
		this.schema = schema;
	}
	
	public String getStorage() {
		return storage;
	}
	
	public void setStorage(String storage) {
		this.storage = storage;
	}
	
	public int getPart() {
		return part;
	}
	
	public void setPart(int part) {
		this.part = part;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{schema=").append(schema).append(", storage=");
		sb.append(storage).append(", part=").append(part).append("}");
		return sb.toString();
	}
	
	public SortConfig getSortConfig() {
		return sortConfig;
	}

	public void setSortConfig(SortConfig sortConfig) {
		this.sortConfig = sortConfig;
	}

}
