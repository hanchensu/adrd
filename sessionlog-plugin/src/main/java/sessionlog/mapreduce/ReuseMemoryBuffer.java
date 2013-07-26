package sessionlog.mapreduce;

import java.io.UnsupportedEncodingException;

import org.apache.thrift.TByteArrayOutputStream;
import org.apache.thrift.transport.TTransport;

public class ReuseMemoryBuffer extends TTransport {

	public ReuseMemoryBuffer(int size) {
		arr_ = new TByteArrayOutputStream(size);
	}

	public boolean isOpen() {
		return true;
	}

	public void open() {
	}

	public void close() {
	}

	public int read(byte[] buf, int off, int len) {
		byte[] src = arr_.get();
		int amtToRead = (len > arr_.len() - pos_ ? arr_.len() - pos_ : len);
		if (amtToRead > 0) {
			System.arraycopy(src, pos_, buf, off, amtToRead);
			pos_ += amtToRead;
		}
		return amtToRead;
	}

	public void write(byte[] buf, int off, int len) {
		arr_.write(buf, off, len);
	}

	public String toString(String enc) throws UnsupportedEncodingException {
		return arr_.toString(enc);
	}

	public String inspect() {
		String buf = "";
		byte[] bytes = arr_.toByteArray();
		for (int i = 0; i < bytes.length; i++) {
			buf += (pos_ == i ? "==>" : "") + Integer.toHexString(bytes[i] & 0xff) + " ";
		}
		return buf;
	}

	private TByteArrayOutputStream arr_;
	private int pos_;

	public int length() {
		return arr_.size();
	}

	public byte[] getArray() {
		return arr_.get();
	}
	
	public void reuse() {
		arr_.reset();
	}

}
