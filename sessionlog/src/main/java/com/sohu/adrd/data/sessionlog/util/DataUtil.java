package sessionlog.util;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.hadoop.io.BytesWritable;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;
import org.apache.thrift.TException;

import sessionlog.op.AdInfoOperation;
import sessionlog.op.PvOperation;
import sessionlog.op.SearchOperation;
import sessionlog.util.Util;

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
					ArrayList<PvOperation> pvList = null;
					while (protocol.getTransport().getBufferPosition() < length - 1) {
						PvOperation pv = new PvOperation();
						pv.read(protocol.getProtocol());
						if (pvList == null)
							pvList = new ArrayList<PvOperation>();
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
					|| cg.equalsIgnoreCase(CG_ARRIVE)) {
				data = (DataByteArray) value.get(index);
				if (data != null && (length = data.size()) > 0) {
					buffer = data.get();
					protocol.getTransport().reset(buffer, 0, length);
					ArrayList<AdInfoOperation> infoList = null;
					while (protocol.getTransport().getBufferPosition() < length - 1) {
						AdInfoOperation info = new AdInfoOperation();
						info.read(protocol.getProtocol());
						if (infoList == null)
							infoList = new ArrayList<AdInfoOperation>();
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

	public static void extractorStr(ThriftProtocol protocol, Tuple value,
			String projection, Tuple extracter) throws ExecException,
			TException {
		if (protocol == null || Util.isBlank(projection) || value == null
				|| extracter == null) {
			throw new IllegalArgumentException(
					"illegal projection/protocol/tuple");
		}
		extracter.set(0, "");
		extracter.set(1, "");
		String cgs[] = projection.split(",");
		if (cgs.length != value.size()) {
			throw new IllegalArgumentException(
					"not consistent projection/tuple");
		}
		StringBuffer sb = new StringBuffer();
		int index = -1, valueIndex = 0;
		byte buffer[] = null;
		int length = 0;
		DataByteArray data = null;
		for (String cg : cgs) {
			index++;
			if (!CG_USER.equals(cg)) {
				if (valueIndex > 0)
					sb.append(OPERATION_SPLIT);
				else
					valueIndex = 1;
			}
			if (cg == null)
				continue;
			if (cg.equalsIgnoreCase(CG_USER)) {
				String userid = (String) value.get(index);
				if (userid != null)
					extracter.set(0, userid);
			} else if (cg.equalsIgnoreCase(CG_PV)) {
				data = (DataByteArray) value.get(index);
				if (data != null && (length = data.size()) > 0) {
					buffer = data.get();
					protocol.getTransport().reset(buffer, 0, length);
					PvOperation pv = new PvOperation();
					boolean first = true;
					while (protocol.getTransport().getBufferPosition() < length - 1) {
						pv.clear();
						pv.read(protocol.getProtocol());
						if (first)
							first = false;
						else
							sb.append(OBJECT_SPLIT);
						append(pv, sb);
					}
				}
			} else if (cg.equalsIgnoreCase(CG_SEARCH)) {
				data = (DataByteArray) value.get(index);
				if (data != null && (length = data.size()) > 0) {
					buffer = data.get();
					protocol.getTransport().reset(buffer, 0, length);
					SearchOperation search = new SearchOperation();
					boolean first = true;
					while (protocol.getTransport().getBufferPosition() < length - 1) {
						search.clear();
						search.read(protocol.getProtocol());
						if (first)
							first = false;
						else
							sb.append(OBJECT_SPLIT);
						append(search, sb);
					}
				}
			} else if (cg.equalsIgnoreCase(CG_ADDISPLAY)
					|| cg.equalsIgnoreCase(CG_ADCLICK)
					|| cg.equalsIgnoreCase(CG_NEWSDISPLAY)
					|| cg.equalsIgnoreCase(CG_NEWSCLICK)
					|| cg.equalsIgnoreCase(CG_HBDISPLAY)
					|| cg.equalsIgnoreCase(CG_HBCLICK)
					|| cg.equalsIgnoreCase(CG_ARRIVE)) {
				data = (DataByteArray) value.get(index);
				if (data != null && (length = data.size()) > 0) {
					buffer = data.get();
					protocol.getTransport().reset(buffer, 0, length);
					AdInfoOperation info = new AdInfoOperation();
					boolean first = true;
					while (protocol.getTransport().getBufferPosition() < length - 1) {
						info.clear();
						info.read(protocol.getProtocol());
						if (first)
							first = false;
						else
							sb.append(OBJECT_SPLIT);
						append(info, sb);
					}
				}
			} else {
				throw new UnsupportedOperationException("not supported cg:"
						+ cg);
			}
		}
		extracter.set(1, sb.toString());
	}

	public static void append(PvOperation pv, StringBuffer sb) {
		Iterator<sessionlog.op.PvOperation._Fields> it = PvOperation.metaDataMap
				.keySet().iterator();
		boolean first = true;
		while (it.hasNext()) {
			sessionlog.op.PvOperation._Fields field = it.next();
			if (first)
				first = false;
			else
				sb.append(FIELD_SPLIT);
			if (pv.isSet(field))
				sb.append(pv.getFieldValue(field));
			else
				sb.append(" ");
		}
	}

	public static void append(SearchOperation search, StringBuffer sb) {
		Iterator<sessionlog.op.SearchOperation._Fields> it = SearchOperation.metaDataMap
				.keySet().iterator();
		boolean first = true;
		while (it.hasNext()) {
			sessionlog.op.SearchOperation._Fields field = it.next();
			if (first)
				first = false;
			else
				sb.append(FIELD_SPLIT);
			if (search.isSet(field))
				sb.append(search.getFieldValue(field));
			else
				sb.append(" ");
		}
	}

	public static void append(AdInfoOperation display, StringBuffer sb) {
		Iterator<sessionlog.op.AdInfoOperation._Fields> it = AdInfoOperation.metaDataMap
				.keySet().iterator();
		boolean first = true;
		while (it.hasNext()) {
			sessionlog.op.AdInfoOperation._Fields field = it.next();
			if (first)
				first = false;
			else
				sb.append(FIELD_SPLIT);
			if (display.isSet(field))
				sb.append(display.getFieldValue(field));
			else
				sb.append(" ");
		}
	}

}
