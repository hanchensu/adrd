package com.sohu.adrd.data.sessionlog.consume.util;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.hadoop.io.BytesWritable;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;
import org.apache.thrift.TException;

import com.sohu.adrd.data.common.Util;
import com.sohu.adrd.data.sessionlog.thrift.operation.CountinfoOperation;
import com.sohu.adrd.data.sessionlog.thrift.operation.PVOperation;
import com.sohu.adrd.data.sessionlog.thrift.operation.SearchOperation;

public class DataUtil {

	public static final String CG_USER = "user";
	public static final String CG_PV = "pv";
	public static final String CG_SEARCH = "search";
	public static final String CG_ADDISPLAY = "addisplay";
	public static final String CG_ADCLICK = "adclick";
	public static final String CG_NEWSDISPLAY = "newsdisplay";
	public static final String CG_NEWSCLICK = "newsclick";
	public static final String CG_HBDISPLAY = "hbdisplay";
	public static final String CG_HBCLICK = "hbclick";
	public static final String CG_ARRIVE = "arrive";
	public static final String CG_REACH = "reach";
	public static final String CG_ACTION = "action";
	public static final String CG_ERR = "err";

	public static final String OPERATION_SPLIT = "\1";
	public static final String OBJECT_SPLIT = "\2";
	public static final String FIELD_SPLIT = "\3";

	public static void trastoId(long v, BytesWritable data) {
		byte writeBuffer[] = null;
		if (data == null || ((writeBuffer = data.getBytes()).length != 8)) {
			throw new IllegalArgumentException("not id key BytesWritable");
		}
		writeBuffer[0] = (byte) (v >>> 56);
		writeBuffer[1] = (byte) (v >>> 48);
		writeBuffer[2] = (byte) (v >>> 40);
		writeBuffer[3] = (byte) (v >>> 32);
		writeBuffer[4] = (byte) (v >>> 24);
		writeBuffer[5] = (byte) (v >>> 16);
		writeBuffer[6] = (byte) (v >>> 8);
		writeBuffer[7] = (byte) (v >>> 0);
	}

	public static long trasfromId(BytesWritable data) {
		byte readBuffer[] = null;
		if (data == null || ((readBuffer = data.getBytes()).length < 8)) {
			throw new IllegalArgumentException("not id key BytesWritable");
		}
		return (((long) readBuffer[0] << 56)
				+ ((long) (readBuffer[1] & 255) << 48)
				+ ((long) (readBuffer[2] & 255) << 40)
				+ ((long) (readBuffer[3] & 255) << 32)
				+ ((long) (readBuffer[4] & 255) << 24)
				+ ((readBuffer[5] & 255) << 16) + ((readBuffer[6] & 255) << 8) + ((readBuffer[7] & 255) << 0));
	}

	public static void extractorValue(NamedList<Object> list,
			ThriftProtocol protocol, Tuple value, String projection)
			throws ExecException, TException {
		if (protocol == null || Util.isBlank(projection) || value == null
				|| list == null) {
			throw new IllegalArgumentException(
					"illegal projection/protocol/tuple/list");
		}
		if (list.size() > 0)
			list.clear();
		String cgs[] = projection.split(",");
		if (cgs.length != value.size()) {
			throw new IllegalArgumentException(
					"not consistent projection/tuple");
		}
		int index = -1;
		byte buffer[] = null;
		int length = 0;
		DataByteArray data = null;
		for (String cg : cgs) {
			index++;
			if (cg == null)
				continue;
			if (cg.equalsIgnoreCase(CG_USER)) {
				String userid = (String) value.get(index);
				userid = userid == null ? "" : userid;
				list.add(CG_USER, userid);
			} else if (cg.equalsIgnoreCase(CG_PV)) {
				data = (DataByteArray) value.get(index);
				if (data != null && (length = data.size()) > 0) {
					buffer = data.get();
					protocol.getTransport().reset(buffer, 0, length);
					ArrayList<PVOperation> pvList = null;
					while (protocol.getTransport().getBufferPosition() < length - 1) {
						PVOperation pv = new PVOperation();
						pv.read(protocol.getProtocol());
						if (pvList == null)
							pvList = new ArrayList<PVOperation>();
						pvList.add(pv);
					}
					list.add(CG_PV, pvList);
				}
			} else if (cg.equalsIgnoreCase(CG_SEARCH)) {
				data = (DataByteArray) value.get(index);
				if (data != null && (length = data.size()) > 0) {
					buffer = data.get();
					protocol.getTransport().reset(buffer, 0, length);
					ArrayList<SearchOperation> searchList = null;
					while (protocol.getTransport().getBufferPosition() < length - 1) {
						SearchOperation search = new SearchOperation();
						search.read(protocol.getProtocol());
						if (searchList == null)
							searchList = new ArrayList<SearchOperation>();
						searchList.add(search);
					}
					list.add(CG_SEARCH, searchList);
				}
			} else if (cg.equalsIgnoreCase(CG_ADDISPLAY)
					|| cg.equalsIgnoreCase(CG_ADCLICK)
					|| cg.equalsIgnoreCase(CG_NEWSDISPLAY)
					|| cg.equalsIgnoreCase(CG_NEWSCLICK)
					|| cg.equalsIgnoreCase(CG_HBDISPLAY)
					|| cg.equalsIgnoreCase(CG_HBCLICK)
					|| cg.equalsIgnoreCase(CG_ARRIVE)
					|| cg.equalsIgnoreCase(CG_REACH)
					|| cg.equalsIgnoreCase(CG_ACTION)
					|| cg.equalsIgnoreCase(CG_ERR)) {
				data = (DataByteArray) value.get(index);
				if (data != null && (length = data.size()) > 0) {
					buffer = data.get();
					protocol.getTransport().reset(buffer, 0, length);
					ArrayList<CountinfoOperation> infoList = null;
					while (protocol.getTransport().getBufferPosition() < length - 1) {
						CountinfoOperation info = new CountinfoOperation();
						info.read(protocol.getProtocol());
						if (infoList == null)
							infoList = new ArrayList<CountinfoOperation>();
						infoList.add(info);
					}
					list.add(cg, infoList);
				}
			} else {
				throw new UnsupportedOperationException("not supported cg:"
						+ cg);
			}
		}
	}

}
