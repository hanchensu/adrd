package com.sohu.adrd.data.sessionlog.plugin;

import java.util.List;
import java.util.Map;

import com.sohu.adrd.data.sessionlog.plugin.adinfo.AdInfoDivider;


import sessionlog.config.PreprocessFiles;
import sessionlog.mapreduce.Preprocessor;
import sessionlog.op.OperationType;

public class AdinfoFixMonitorkeyPreprocessor implements Preprocessor{

	@Override
	public List<String> preprocess(List<String> strs, PreprocessFiles preProcessData) {
		String mkey = strs.get(AdInfoDivider.indexOf("MONITORKEY"));
		String adid = strs.get(AdInfoDivider.indexOf("ADID"));
		
		Map<String,String> adid2mkey = preProcessData.adid2mkey;
		OperationType opType = AdInfoDivider.getOperationType(strs);
		if(opType == OperationType.AD_CLICK || opType == OperationType.AD_DISPLAY) {
			if(adid2mkey != null && adid2mkey.get(adid) != null && adid2mkey.get(adid).length() == 25) {
				mkey = adid2mkey.get(adid);
			} else if (adid.length() == 25) {
				mkey = adid;
			} else if (adid2mkey != null && adid2mkey.get(mkey) != null && adid2mkey.get(mkey).length() == 25) {
				mkey = adid2mkey.get(mkey);
			}
			strs.set(AdInfoDivider.indexOf("MONITORKEY"), mkey);
		}
		return strs;
	}
}
