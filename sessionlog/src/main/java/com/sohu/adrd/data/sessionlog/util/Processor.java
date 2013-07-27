package com.sohu.adrd.data.sessionlog.util;

import java.util.Iterator;
import java.util.List;

import sessionlog.mapreduce.ProcessorEntry;
import sessionlog.op.OperationType;

public interface Processor {
	
	public List<OperationType> acceptTypes();
	
	public Iterator<ProcessorEntry> process(Iterator<ProcessorEntry> it);
	
	/*
	TProtocol protocol = null;
	TMemoryInputTransport inputTransport = new TMemoryInputTransport();
	protocol = new TBinaryProtocol(inputTransport);
	-------------
	process() {
	    while (it.hasNext()) {
	        ProcessorEntry entry = it.next();
	        inputTransport.reset(entry.getData(), entry.getOffset(), entry.getLength());
	        PvOperation pv = new PvOperation();
	        pv.read(protocol);
	        ...
	    }   
	}
	*/
	
}
