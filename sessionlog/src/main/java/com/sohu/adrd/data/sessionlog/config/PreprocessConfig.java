package com.sohu.adrd.data.sessionlog.config;

public class PreprocessConfig {
	
	private String path = null;
	private String processors = null;

	public String getProcessors() {
		return processors;
	}

	public void setProcessors(String processors) {
		this.processors = processors;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "PreprocessorConfig [path=" + path + ", processors="
				+ processors + "]";
	}

}
