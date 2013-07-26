package sessionlog.mapreduce.plugin;

import java.util.List;

import sessionlog.config.PreprocessFiles;
import sessionlog.mapreduce.Preprocessor;
import sessionlog.mapreduce.plugin.adinfo.AdInfoDivider;
import sessionlog.op.OperationType;
import sessionlog.util.Util;

public class AdinfoMonitorkeyPreprocessor implements Preprocessor{

	@Override
	public List<String> preprocess(List<String> strs, PreprocessFiles preProcessData) {
		String mkey = strs.get(AdInfoDivider.indexOf("MONITORKEY"));
		String adtype = strs.get(AdInfoDivider.indexOf("ADTYPE"));
		int statusCode = Integer.parseInt(strs.get(strs.size()-1));
		List<String> monitorkeys = preProcessData.monitorkeys;
		OperationType opType = AdInfoDivider.getOperationType(strs);
		if(monitorkeys != null && (opType == OperationType.AD_CLICK || opType == OperationType.AD_DISPLAY) && "1".equals(adtype)) {
			if(Util.isNotBlank(mkey) && !"NULL".equalsIgnoreCase(adtype) && !monitorkeys.contains(mkey)) {
				statusCode = statusCode | 1;
			}
		}
		strs.set(strs.size() - 1, Integer.toString(statusCode));
		return strs;
	}
}
