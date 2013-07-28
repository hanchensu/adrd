package com.sohu.adrd.data.sessionlog.consume.util;


import java.io.Closeable;
import java.io.IOException;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TMemoryInputTransport;

public class ThriftProtocol implements Closeable {
	
	private TBinaryProtocol protocol = null;
	private TMemoryInputTransport transport = null;
	
	public ThriftProtocol() {
		transport = new TMemoryInputTransport();
		protocol = new TBinaryProtocol(transport);
	}

	public TBinaryProtocol getProtocol() {
		return protocol;
	}

	public void setProtocol(TBinaryProtocol protocol) {
		this.protocol = protocol;
	}

	public TMemoryInputTransport getTransport() {
		return transport;
	}

	public void setTransport(TMemoryInputTransport transport) {
		this.transport = transport;
	}

	public void close() throws IOException {
		this.transport.close();
	}

}
