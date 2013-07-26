package sessionlog.mapreduce.plugin;

import java.util.ArrayList;
import java.util.List;

import sessionlog.mapreduce.FormatResult;
import sessionlog.mapreduce.Formator;
import sessionlog.util.Util;

public class UdataFormator implements Formator {

	private List<String> result = new ArrayList<String>();
	
	public FormatResult format(String str) {
		if (Util.isBlank(str)) return new FormatResult(null,"err1");
		result.clear();
		result.add(str);
		return new FormatResult(result,"0");
	}

	@Override
	public String getMark() {
	
		return "udata_";
	}

}
