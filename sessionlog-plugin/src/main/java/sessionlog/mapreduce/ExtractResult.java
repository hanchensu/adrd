package sessionlog.mapreduce;

import java.util.List;

public class ExtractResult {
	public List<ExtractorEntry> res;
	public String errorcode;
	public ExtractResult(List<ExtractorEntry> res, String errorcode) {
		this.res = res;
		this.errorcode = errorcode;
	}

}
