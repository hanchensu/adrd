package com.sohu.adrd.data.sessionlog.util;

import java.util.List;

import sessionlog.mapreduce.ExtractResult;

public interface Extractor {
	
    public ExtractResult extract(List<String> strs);
    
    public String getTypeId(List<String> strs);
    
    /*
    TProtocol protocol = null;
    ReuseMemoryBuffer transport = new ReuseMemoryBuffer(1024);
	protocol = new TBinaryProtocol(transport);
	List<ExtractorEntry> entryList = null;
	-------------
	extract() {
	    transport.reuse();
	    int offset = 0;
	    for (Operation operation : operations) {
	        operation.write(protocol);
	        extractorEntry.data = transport.getArray();
	        extractorEntry.offset = offset;
	        extractorEntry.length = transport.length() - offset;
	        offset = transport.length();
	        entryList.add(extractorEntry);
	    }
	    return entryList;
	}
    */
    
}
