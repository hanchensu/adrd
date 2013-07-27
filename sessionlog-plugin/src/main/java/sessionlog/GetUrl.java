package sessionlog;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.zebra.io.BasicTable;
import org.apache.hadoop.zebra.io.TableScanner;
import org.apache.hadoop.zebra.types.TypesUtils;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryInputTransport;

import sessionlog.op.AdInfoOperation;


public class GetUrl implements Tool {
	
	private Configuration config_;
	private String[] argv_;
	private String timeStr_;
	private ExecutorService service;
	private static final Log LOG = LogFactory.getLog(GetUrl.class);

	public static void main(String[] args) throws Exception {
		int returnStatus = 0;
		GetUrl job = new GetUrl();
		returnStatus = ToolRunner.run(job, args);
		if (returnStatus != 0) {
			System.err.println("Streaming Job Failed!");
			System.exit(returnStatus);
		}
	}

	public Configuration getConf() {
		return config_;
	}

	public void setConf(Configuration conf) {
		this.config_ = conf;
	}

	public int run(String[] args) throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
			for (String arg : args) sb.append(arg).append(" ");
			LOG.warn("submit sessionlog job : " + sb.toString());
			this.argv_ = args;
			if (this.argv_ == null || this.argv_.length != 1) {
				throw new IllegalArgumentException("time not setted");
			} else timeStr_ = this.argv_[0];
			return getUrl();
		} catch (IllegalArgumentException ex) {
			LOG.warn("submit sessionlog job error for : " + ex);
			return 1;
		}
	}

	private int getUrl() {
		String path = "/user/aalog/adlog/" + timeStr_;
		service = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 32; i++) {
			service.execute(new ReaderThread(path, i));
		}
		service.shutdown();
		return 0;
	}
	
	public class ReaderThread implements Runnable {
		
		private String path = null;
		private String outputPath = null;
		private FileOutputStream output = null;
		
		
		public ReaderThread(String str, int index) {
			if (index < 10) path = str + "/SUB-000" + index;
			else path = str + "/SUB-00" + index;
			outputPath = "/opt/develop/urls/url-" + index + ".txt";
		}

		public void run() {
			BasicTable.Reader reader = null;
			try {
				output = new FileOutputStream(outputPath);
				reader = new BasicTable.Reader(new Path(path), config_);
				reader.setProjection("user,adclick,addisplay");
				TableScanner scanner = reader.getScanner(null, true);
				Tuple row = TypesUtils.createTuple(3);
				TProtocol protocol = null;
				TMemoryInputTransport inputTransport = new TMemoryInputTransport();
				protocol = new TBinaryProtocol(inputTransport);
				AdInfoOperation click = new AdInfoOperation();
				AdInfoOperation display = new AdInfoOperation();
				while (!scanner.atEnd()) {
					int clickNumber = 0;
					int displayNumber = 0;
					String clickStr = "";
					scanner.getValue(row);
					scanner.advance();
					String uerId = (String)row.get(0);
					DataByteArray clickData = (DataByteArray)row.get(1);
					byte buffer[] = null;
					int length = 0;
					if (clickData != null && (length = clickData.size()) > 0) {
						buffer = clickData.get();
						inputTransport.reset(buffer, 0, length);
				        while (inputTransport.getBufferPosition() < length - 1) {
				        	click.clear();
				        	click.read(protocol);
				        	clickStr += click.toString();
				        	clickNumber++;
				        }
					}
					DataByteArray displayData = (DataByteArray)row.get(2);
					if (displayData != null && (length = displayData.size()) > 0) {
						buffer = displayData.get();
						inputTransport.reset(buffer, 0, length);
				        while (inputTransport.getBufferPosition() < length - 1) {
				        	display.clear();
				        	display.read(protocol);
				        	displayNumber++;
				        }
					}
					if (displayNumber == 0 && clickNumber > 0) {
						String data = uerId + ":" + clickNumber + "-" + displayNumber + ":" + clickStr + "\n";
						output.write(data.getBytes());
						output.flush();
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
					}
				}
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
					}
				}
			}
		}
		
	}

}
