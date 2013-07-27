package com.sohu.adrd.data.sessionlog.config;

public class PathConfig {
	
	private String location = null;
	private String recordReader = null;
	private String formator = null;
	private String extractor = null;
	
	public String getRecordReader() {
		return recordReader;
	}
	
	public void setRecordReader(String recordReader) {
		this.recordReader = recordReader;
	}
	
	public String getFormator() {
		return formator;
	}
	
	public void setFormater(String formator) {
		this.formator = formator;
	}
	
	public String getExtractor() {
		return extractor;
	}
	
	public void setExtractor(String extractor) {
		this.extractor = extractor;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{path=").append(location).append(", recordReader=").append(recordReader);
		sb.append(", formater=").append(formator).append(", extractor=").append(extractor).append("}");
		return sb.toString();
	}

}
