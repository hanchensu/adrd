package com.sohu.adrd.data.sessionlog.util;

import java.util.Iterator;
import java.util.List;

import com.sohu.adrd.data.sessionlog.thrift.operation.OperationType;


public interface Processor {
	
	public List<OperationType> acceptTypes();
	
	public Iterator<ProcessorEntry> process(Iterator<ProcessorEntry> it);
	
}
