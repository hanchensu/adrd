package com.sohu.adrd.data.sessionlog.util;

import org.apache.hadoop.io.BytesWritable;

public interface IdCreater {
	
	public BytesWritable nextId(String userid);

}
