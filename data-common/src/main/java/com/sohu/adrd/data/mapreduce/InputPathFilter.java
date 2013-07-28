package com.sohu.adrd.data.mapreduce;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

import com.sohu.adrd.data.common.Util;


public class InputPathFilter implements PathFilter {

	public boolean accept(Path path) {
		String pathStr = path.getName();
		if (Util.isNotBlank(pathStr)) {
			if (pathStr.endsWith("tmp") || pathStr.endsWith(".done")
					|| pathStr.endsWith("config.dat")
					|| pathStr.contains("_SUCCESS")
					|| pathStr.contains("_logs") || pathStr.contains("SUB") )
				return false;
		} else
			return false;
		return true;
	}

}