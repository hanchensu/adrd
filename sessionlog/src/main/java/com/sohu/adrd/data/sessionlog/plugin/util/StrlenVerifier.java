package com.sohu.adrd.data.sessionlog.plugin.util;


public class StrlenVerifier implements Verifier{
	private int minLen;
	private int maxLen;
	
	public StrlenVerifier(int min,int max) {
		this.minLen = min;
		this.maxLen = max;
	}
	
	public StrlenVerifier(int max) {
		this.minLen = 0;
		this.maxLen = max;
	}
	
	@Override
	public boolean isValid(Object... objects) {
		String value = (String) objects[0];
		if (value.length() >= minLen && value.length() <= maxLen) {
			return true;
		}
		return false;
	}

}
