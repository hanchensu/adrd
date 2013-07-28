package com.sohu.adrd.data.sessionlog.config;

import java.util.ArrayList;

import com.sohu.adrd.data.common.Util;


public class SessionLogConfig {
	
	private ArrayList<PathConfig> pathConfigs = new ArrayList<PathConfig>();
	private ZebraConfig zebraConfig = null;
	private ArrayList<OpConfig> opConfigs = new ArrayList<OpConfig>();
	

	public void setOpConfigs(ArrayList<OpConfig> opConfigs) {
		this.opConfigs = opConfigs;
	}

	public ArrayList<OpConfig> getOpConfigs() {
		return opConfigs;
	}

	public void addPathConfig(PathConfig pathConfig) {
		pathConfigs.add(pathConfig);
	}
	
	public ArrayList<PathConfig> getPathConfigs() {
		return pathConfigs;
	}
	
	public void setPathConfigs(ArrayList<PathConfig> pathConfigs) {
		this.pathConfigs = pathConfigs;
	}
	
	public ZebraConfig getZebraConfig() {
		return zebraConfig;
	}
	
	public void setZebraConfig(ZebraConfig zebraConfig) {
		this.zebraConfig = zebraConfig;
	}

	public boolean check() {
		for (PathConfig pathConfig : pathConfigs) {
			if (pathConfig == null || Util.isBlank(pathConfig.getLocation()) ||
			Util.isBlank(pathConfig.getExtractor()) || Util.isBlank(pathConfig.getFormator())) {
				return false;
			}
		}
		if (zebraConfig == null || Util.isBlank(zebraConfig.getSchema()) || 
		Util.isBlank(zebraConfig.getStorage())) {
			return false;
		}
		for (OpConfig opConfig : opConfigs) {
			if (opConfig == null || Util.isBlank(opConfig.getOperation()) || Util.isBlank(opConfig.getProcessor())) {
				return false;
			}
		}
		return true;
	}
	
	public void addOpConfig(OpConfig opConfig) {
		opConfigs.add(opConfig);
	}
	
	
}