package com.sohu.adrd.data.sessionlog.util;

import java.util.List;

public interface Extractor {
	
    public ExtractorEntry extract(List<String> strs);
    
}
