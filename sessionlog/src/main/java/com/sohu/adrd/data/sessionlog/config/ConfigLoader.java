package com.sohu.adrd.data.sessionlog.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.commons.digester3.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class ConfigLoader {
	
	private static final Log LOG = LogFactory.getLog(ConfigLoader.class);
	public static String SESSIONLOG_CONFIG_NAME = "sessionlog.config";
	public static SessionLogConfig loadConfig(Configuration conf) throws IOException {
		
		SessionLogConfig config = null;
		URI localFiles[] = DistributedCache.getCacheFiles(conf);
		if (localFiles == null || localFiles.length == 0) throw new IOException("sessionlog.config excepted");
		IOException exception = null;
		for (URI uri : localFiles) {
			LOG.warn("config path:" + uri.toString());
			
			if (uri != null && uri.getFragment().contains(SESSIONLOG_CONFIG_NAME)) {
				Path path = new Path(uri.getRawPath());
				FileSystem fs = path.getFileSystem(conf);
				if (fs.exists(path)) {
					try {
						InputStream input = fs.open(path);
						Digester digester = new Digester();
						config = new SessionLogConfig();
						digester.push(config);
						digester.addObjectCreate("config/paths/path", PathConfig.class);
						digester.addSetProperties("config/paths/path");
						digester.addSetNext("config/paths/path", "addPathConfig");
						digester.addObjectCreate("config/zebra", ZebraConfig.class);
						digester.addSetProperties("config/zebra");
						digester.addSetNext("config/zebra", "setZebraConfig");
						digester.addObjectCreate("config/zebra/sort", SortConfig.class);
						digester.addSetProperties("config/zebra/sort");
						digester.addSetNext("config/zebra/sort", "setSortConfig");
						digester.addObjectCreate("config/ops/op", OpConfig.class);
						digester.addSetProperties("config/ops/op");
						digester.addSetNext("config/ops/op", "addOpConfig");
						digester.addSetProperties("config/preprocessors/preprocessor");
						digester.addSetNext("config/preprocessors/preprocessor", "addPreprocessConfig");
						config = (SessionLogConfig) digester.parse(input);
						break;
					} catch (Exception e) {
						exception = new IOException("sessionlog.config parse error");
						break;
					}
				} else {
					exception = new IOException("sessionlog.config excepted");
					break;
				}
			}
		}
		if (exception != null) throw exception;
		return config;
	}

}
