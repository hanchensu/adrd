package sessionlog.mapreduce.plugin.adinfo;

import java.util.List;

import com.sohu.adrd.data.sessionlog.mapreduce.AdinfoFormator;


import sessionlog.op.OperationType;

public class AdInfoDivider {

	public static int indexOf(String field) {
		for (int i = 0; i < AdinfoFormator.SCHEMA.length; i++) {
			if (AdinfoFormator.SCHEMA[i].equals(field)) {
				return i;
			}
		}
		return -1;
	}

	public static final String CLICK_FLAG = "click";
	public static final String DISPLAY_FLAG = "view";
	public static final String NEWS_TYPE = "2";
	public static final String TEST_SUV = "123456";
	public static final String ARRIVE_FLAG = "reach"; //0.2.4

	public static OperationType getOperationType(List<String> strs) {

		String reqType = strs.get(indexOf("REQTYPE"));
		String adType = strs.get(indexOf("ADTYPE"));
		String suv = strs.get(indexOf("SUV"));
		
		if (reqType.equals(DISPLAY_FLAG) && TEST_SUV.equals(suv)) {
			
			return OperationType.HB_DISPLAY;
			
		} else if (reqType.equals(CLICK_FLAG) && TEST_SUV.equals(suv)) {
			
			return OperationType.HB_CLICK;
			
		} else if (reqType.equals(DISPLAY_FLAG) && NEWS_TYPE.equals(adType)) {
			
			return OperationType.NEWS_DISPLAY;
			
		} else if (reqType.equals(CLICK_FLAG) && NEWS_TYPE.equals(adType)) {
			
			return OperationType.NEWS_CLICK;
			
		} else if (reqType.equals(DISPLAY_FLAG) && adTypeValid(adType)) {
			
			return OperationType.AD_DISPLAY;
			
		} else if (reqType.equals(CLICK_FLAG) && adTypeValid(adType)) {
			
			return OperationType.AD_CLICK;
			
		} else if (reqType.equals(ARRIVE_FLAG)) {
			
			return OperationType.ARRIVE;
			
		} else {
			return null;
		}
	}

	private static boolean adTypeValid(String adType) {
//		if (!"2".equals(adType) || "3".equals(adType) || "4".equals(adType)
//				|| "null".equalsIgnoreCase(adType) || "0".equals(adType)) {
//			return true;
//		}
		if (!"2".equals(adType)) {
			return true;
		}
		return false;
	}
}
