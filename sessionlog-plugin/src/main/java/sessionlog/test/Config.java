package sessionlog.test;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;

import com.sohu.adrd.data.sessionlog.config.SessionLogConfig;

import sessionlog.op.PvOperation;
import sessionlog.op.SearchOperation;
import sessionlog.util.Util;

public class Config {
	
	public static void main(String args[]) throws IOException {
		Configuration conf = new Configuration();
		Path path = new Path("file:///Users/mac/conf/sessionlog.config");
		DistributedCache.addCacheFile(path.toUri(), conf);
		SessionLogConfig config = Util.loadConfig(conf);
		System.out.println(config);
	}

}
