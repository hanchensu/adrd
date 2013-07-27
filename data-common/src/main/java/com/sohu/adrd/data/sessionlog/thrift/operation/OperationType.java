package com.sohu.adrd.data.sessionlog.thrift.operation;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum OperationType {

	PV((byte) 1, "pv"), SEARCH((byte) 2, "search"), AD_CLICK((byte) 3,
			"adclick"), AD_DISPLAY((byte) 4, "addisplay"), NEWS_CLICK((byte) 5,
			"newsclick"), NEWS_DISPLAY((byte) 6, "newsdisplay"), HB_CLICK((byte) 7, "hbclick"), HB_DISPLAY((byte) 8, "hbdisplay"), ARRIVE((byte) 9,"arrive");

	private final byte OperateId;
	private final String OperateName;

	OperationType(byte operateId, String operateName) {
		this.OperateId = operateId;
		this.OperateName = operateName;
	}

	public byte getOperateId() {
		return this.OperateId;
	}

	public String getOperateName() {
		return this.OperateName;
	}

	private static final Map<String, OperationType> byName = new HashMap<String, OperationType>();
	static {
		for (OperationType operate : EnumSet.allOf(OperationType.class)) {
			byName.put(operate.getOperateName(), operate);
		}
	}

	public static OperationType findByOperateId(int OperateId) {
		switch (OperateId) {
		case 1:
			return PV;
		case 2:
			return SEARCH;
		case 3:
			return AD_CLICK;
		case 4:
			return AD_DISPLAY;
		case 5:
			return NEWS_CLICK;
		case 6:
			return NEWS_DISPLAY;
		case 7:
			return HB_CLICK;
		case 8:
			return HB_DISPLAY;
		case 9:
			return ARRIVE;
		default:
			return null;
		}
	}

	public static OperationType findByOperateIdOrThrow(int OperateId) {
		OperationType operates = findByOperateId(OperateId);
		if (operates == null) {
			throw new IllegalArgumentException("OperateId " + OperateId
					+ " doesn't exist!");
		}
		return operates;
	}

	public static OperationType findByOperateName(String name) {
		return byName.get(name);
	}

}
