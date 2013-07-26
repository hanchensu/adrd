package sessionlog.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.digester3.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.pig.parser.AliasMasker.keyvalue_return;
import org.apache.pig.parser.QueryParser.null_check_cond_return;

import sessionlog.config.OpConfig;
import sessionlog.config.PathConfig;
import sessionlog.config.PreprocessConfig;
import sessionlog.config.PreprocessFiles;
import sessionlog.config.SessionLogConfig;
import sessionlog.config.SortConfig;
import sessionlog.config.ZebraConfig;

public class Util {
	
	public static String SESSIONLOG_CONFIG = "new-sessionlog.config";
	private static final Log LOG = LogFactory.getLog(Util.class);
	private static ThreadLocal<SimpleDateFormat> Usformaters = new ThreadLocal<SimpleDateFormat>();
	private static ThreadLocal<SimpleDateFormat> Logformaters = new ThreadLocal<SimpleDateFormat>();
	private static ThreadLocal<SimpleDateFormat> Udataformaters = new ThreadLocal<SimpleDateFormat>();
	private static ThreadLocal<MessageDigest> Md5s = new ThreadLocal<MessageDigest>();
	
	public static boolean isBlank(String str) {
		if (str == null || str.length() == 0 || str.trim().length() == 0) return true;
		else return false;
	}
	
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}
	
	public static SessionLogConfig loadConfig(Configuration conf) throws IOException {
		SessionLogConfig config = null;
		URI localFiles[] = DistributedCache.getCacheFiles(conf);
		if (localFiles == null || localFiles.length == 0) throw new IOException("sessionlog.config excepted");
		IOException exception = null;
		for (URI uri : localFiles) {
			LOG.warn("config path:" + uri.toString());
			if (uri != null && uri.getFragment().equals(SESSIONLOG_CONFIG)) {
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
						digester.addObjectCreate("config/preprocessors/preprocessor", PreprocessConfig.class);
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
	
	public static long readLog(byte data[], int off) {
		if (off + 8 > data.length) throw new RuntimeException("");
		return (((long)data[off + 0] << 56) + ((long)(data[off + 1] & 255) << 48) +
		((long)(data[off + 2] & 255) << 40) + ((long)(data[off + 3] & 255) << 32) +
        ((long)(data[off + 4] & 255) << 24) + ((data[off + 5] & 255) << 16) +
        ((data[off + 6] & 255) <<  8) + ((data[off + 7] & 255) <<  0));
	}

	public static SimpleDateFormat usFormat() {
		SimpleDateFormat format = Usformaters.get();
		if (format == null) {
			format = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.US);
			Usformaters.set(format);
		}
		return format;
	}
	
	public static SimpleDateFormat logFormat() {
		SimpleDateFormat format = Logformaters.get();
		if (format == null) {
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Logformaters.set(format);
		}
		return format;
	}
	
	public static SimpleDateFormat udataFormat() {
		SimpleDateFormat format = Udataformaters.get();
		if (format == null) {
			format = new SimpleDateFormat("yyyyMMdd");
			Udataformaters.set(format);
		}
		return format;
	}
	
	public static String getMD5(String s) {
		try {
			MessageDigest md5 = Md5s.get();
			if (md5 == null) {
				md5 = MessageDigest.getInstance("MD5");
				Md5s.set(md5);
			}
			byte[] byteArray = s.getBytes("ISO-8859-1");
			byte[] md5Bytes = md5.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16) hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public static void loadPreprocessFiles(PreprocessFiles preProcessFiles, String path,Configuration conf) throws IOException {
		
		if(path.contains("countinfo")) {
			URI localFiles[] = DistributedCache.getCacheFiles(conf);
			if (localFiles == null || localFiles.length == 0) throw new IOException("moniterkeyList excepted");
			
			for (URI uri : localFiles) {
				
				if (uri != null && "monitorkeyList".equals(uri.getFragment())) {
					
					BufferedReader br = new BufferedReader(new FileReader(new File("monitorkeyList")));
					preProcessFiles.monitorkeys = new ArrayList<String>();
					String str;
					while ((str = br.readLine()) != null) {
						preProcessFiles.monitorkeys.add(str);
					}
					br.close();
				}
				
				if (uri != null && "adid2mkey".equals(uri.getFragment())) {
					
					BufferedReader br = new BufferedReader(new FileReader(new File("adid2mkey")));
					preProcessFiles.adid2mkey = new HashMap<String,String>();
					String str;
					while ((str = br.readLine()) != null) {
						String[] kv = str.split("\\p{Blank}+");
						if(kv.length == 2) {
							String key = kv[0];
							String value = kv[1];
							preProcessFiles.adid2mkey.put(key, value);
						}
						
					}
					br.close();
					break;
				}
			}
			
		}
		
	}
	
}
