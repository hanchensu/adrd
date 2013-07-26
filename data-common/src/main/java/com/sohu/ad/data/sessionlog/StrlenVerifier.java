package com.sohu.ad.data.sessionlog;

public class StrlenVerifier implements Verifier{
	private int length;
	
	public StrlenVerifier(int length) {
		this.length = length;
	}
	
	@Override
	public boolean isValid(Object... objects) {
		String value = (String) objects[0];
		if (value.length() <= length) {
			return true;
		}
		return false;
	}

}
