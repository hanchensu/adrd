package com.sohu.adrd.data.sessionlog.config;

public class OpConfig {
	
	private String operation = null;
	private String processor = null;
	
	public String getOperation() {
		return operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public String getProcessor() {
		return processor;
	}
	
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{operation=").append(operation).append(", processor=");
		sb.append(processor).append("}");
		return sb.toString();
	}

}
