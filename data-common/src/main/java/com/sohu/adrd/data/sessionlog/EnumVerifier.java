package com.sohu.adrd.data.sessionlog;

import java.util.HashSet;
import java.util.Set;

public class EnumVerifier implements Verifier {
	Set valids = new HashSet();
	EnumVerifier(Object... objects) {
		for(Object object:objects) {
			valids.add(object);
		}
	}
	
	
	@Override
	public boolean isValid(Object... objects) {
		Object value = objects[0];
		return valids.contains(value);
	}

}
