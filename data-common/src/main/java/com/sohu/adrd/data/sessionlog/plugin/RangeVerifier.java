package com.sohu.adrd.data.sessionlog.plugin;

public class RangeVerifier implements Verifier{
	
	Object min;
	Object max;
	
	RangeVerifier(Object min, Object max){
		this.min = min;
		this.max = max;
	}

	
	public boolean isValid(Object... objects) {
		
		Object value = objects[0].getClass();
		
		if(value.getClass() == java.lang.Integer.class) {
			
			if((Integer)min <= (Integer)value && (Integer)value <= (Integer)max) {
				return true;
			}
			return false;
		}
		
		
		if(value.getClass() == java.lang.Long.class) {
			
			if((Long)min <= (Long)value && (Long)value <= (Long)max) {
				return true;
			}
			return false;
		}
		
		
		if(value.getClass() == java.lang.Double.class) {
			
			if((Double)min <= (Double)value && (Double)value <= (Double)max) {
				return true;
			}
			return false;
		}
		
		return false;
			
	}
	
}
