package com.sohu.adrd.data.sessionlog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.sohu.adrd.data.common.Util;
import com.sohu.adrd.data.mapreduce.InputPathFilter;
import com.sohu.adrd.data.sessionlog.mapreduce.ConfigurableTextInputFormat;
import com.sohu.adrd.data.sessionlog.mapreduce.SessionLogMapper;
import com.sohu.adrd.data.sessionlog.mapreduce.SessionLogReducer;



public class SessionLogJob implements Tool {
	
	private CommandLineParser parser = new BasicParser();
	private Options allOptions;
	private Configuration config_;
	private String[] argv_;
	private String output_;
	private String input_;
	private String time_;
	private String configFile_;
	private String lib_;
	private String numReduceTasks_;
	private String jobName_;
	private String attachments_;
	
	private int numReduce = 1;
	private FileSystem client = null;
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	private static final Log LOG = LogFactory.getLog(SessionLogJob.class);
	
	public static void main(String[] args) throws Exception {
		
		int returnStatus = 0;
		SessionLogJob job = new SessionLogJob();
		returnStatus = ToolRunner.run(job, args);
		if (returnStatus != 0) {
			System.err.println("Sessionlog Job Failed!");
			System.exit(returnStatus);
		}
	}
	
	public SessionLogJob() {
		setupOptions();
		this.config_ = new Configuration();
	}

	private void setupOptions() {
		Option time = createOption("time", "DFS input time for the Map step", "yyyy/MM/dd", Integer.MAX_VALUE, false);
		Option input = createOption("input", "DFS input file(s) for the Map step", "path", Integer.MAX_VALUE, true);
		Option output = createOption("output", "DFS output directory for the Reduce step", "path", 1, true);
		Option configFile = createOption("configFile", "sessionlog config file path", "file", Integer.MAX_VALUE, true);
		Option libjars = createOption("lib", "sessionlog mapreduce lib jars", "file", Integer.MAX_VALUE, true);
		Option numReduceTasks = createOption("numReduceTasks", "reducer num", "spec", 1, true);
		Option attachments = createOption("attachments", "sessionlog attachments, comma seperated", "file", Integer.MAX_VALUE, false);
		Option jobName = createOption("jobName", "Optional.", "spec", 1, false);
		
		allOptions = new Options().addOption(time).addOption(input).addOption(output)
		.addOption(configFile).addOption(libjars).addOption(numReduceTasks).addOption(attachments).addOption(jobName);
	}
	
	public void exitUsage() {
		System.out.println("Usage: $HADOOP_HOME/bin/hadoop jar $SESSIIONLOG_HOME/sessionlog-$VERSION.jar [options]");
		System.out.println("Options:");
		System.out.println("  -time <yyyy/MM/dd> DFS input time for the Map step");
		System.out.println("  -input <path> DFS input file(s) for the Map step");
		System.out.println("  -output <path> DFS output directory for the Reduce step");
		System.out.println("  -lib <path> jars session log job needs");
		System.out.println("  -configFile <path> session log job config file");
		System.out.println("  -numReduceTasks <int> session log job reduce number");
		System.out.println("  -jobName <string> session log MR job name");
		System.out.println("  -attachments <files> session log attachments (mkeylist file e.g.)");
	}
	
	public void fail(String message) {
		System.err.println(message);
		throw new IllegalArgumentException(message);
	}
	
	@SuppressWarnings("static-access")
	private Option createOption(String name, String desc, String argName, int max, boolean required) {
		return OptionBuilder.withArgName(argName).hasArgs(max).withDescription(desc).isRequired(required).create(name);
		
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
			parseArgv();
			postProcessArgs();
			setJobConf();
			return submitAndMonitorJob();
		} catch (IllegalArgumentException ex) {
			LOG.warn("submit sessionlog job error for : " + ex);
			return 1;
		}
	}
	
	private void parseArgv() {
		CommandLine cmdLine = null;
		try {
			cmdLine = parser.parse(allOptions, argv_);
		} catch (Exception oe) {
			exitUsage();
			fail("parser cmd error:" + oe.toString());
		}
		if (cmdLine != null) {
			time_ = (String) cmdLine.getOptionValue("time");
			input_ = (String) cmdLine.getOptionValue("input");
			output_ = (String) cmdLine.getOptionValue("output");
			configFile_ = (String) cmdLine.getOptionValue("configFile");
			lib_ = (String) cmdLine.getOptionValue("lib");
			numReduceTasks_ = (String) cmdLine.getOptionValue("numReduceTasks");
			jobName_ = (String) cmdLine.getOptionValue("jobName");
			attachments_ = (String) cmdLine.getOptionValue("attachments");
		} else {
			exitUsage();
			fail("parser cmd error, is null");
		}
	}
	
	@SuppressWarnings("null")
	private void postProcessArgs() throws IOException {
		String message = null;
		String timeStr = null;
		//set time
		if (Util.isBlank(time_)) {
			long time = new Date().getTime();
			time = time - 24 * 60 * 60 * 1000;
			timeStr = format.format(new Date(time));
		} else timeStr = time_;
		
		//set input path
		if (Util.isBlank(input_)) message = "input path not configed"; 
		else {
			String parts[] = input_.split(",");
			if (parts == null || parts.length == 0) message = "input path not configed error";
			else {
				if (client == null) client = FileSystem.get(config_);
				StringBuffer sb = new StringBuffer();
				boolean flag = false;
				for (String part : parts) {
					if (Util.isBlank(part)) {
						message = "input path not configed error"; break;
					}
					if (part.endsWith("/")) part = part.substring(0, part.length() - 1);
					FileStatus fstatus = null;
					try {
					    fstatus = client.getFileStatus(new Path(part));
					} catch (Exception e) {
					}
					if (fstatus == null) message = "path:" + part + " not found";
					else if (fstatus.isDir()) {
						if (part.startsWith("/user/aa-testlog/udata/data") || part.startsWith("/user/flume/adserver/adinfo") || part.startsWith("/user/aalog/shc")) {
							String subpart = part + "/" + timeStr;
							FileStatus subfstatus = null;
							try {
								subfstatus = client.getFileStatus(new Path(subpart));
							} catch (Exception e) {
							}
							if (subfstatus == null) continue;
							if (!flag) {
								flag = true;
								sb.append(subpart);
							} else sb.append(",").append(subpart);
						} else {
							FileStatus subs[] = client.listStatus(new Path(part));
							if (subs == null) {
								message = "input path:" + part + " not configed error";
								break;
							}
							for (FileStatus sub : subs) {
								String subpart = part + "/" + sub.getPath().getName() + "/" + timeStr;
								FileStatus subfstatus = null;
								try {
									subfstatus = client.getFileStatus(new Path(subpart));
								} catch (Exception e) {
								}
								if (subfstatus == null) continue;
								if (!flag) {
									flag = true;
									sb.append(subpart);
								} else sb.append(",").append(subpart);
							}
						}
					} else {
						if (!flag) {
							flag = true;
							sb.append(part);
						} else sb.append(",").append(part);
					}
				}
				input_ = sb.toString();
			}
		}
		
		//set output path
		if (Util.isBlank(output_)) message = "output path not configed";
		else {
			String parts[] = output_.split(",");
			if (parts.length > 1) message = "output path can't be mutil";
			if (client == null) client = FileSystem.get(config_);
			if (output_.endsWith("/")) output_ = output_ + timeStr;
			else output_ = output_ + "/" + timeStr;
			FileStatus fstatus = null;
			try {
				fstatus = client.getFileStatus(new Path(output_));
			} catch (Exception e) {
			}
			if (fstatus != null) message = "output path:" + output_ + " exists";
		}
		
		//set config file
	    if (Util.isBlank(configFile_)) message = "config file not configed";
	    else {
	    	String files = "";
	    	for(String conFile : configFile_.split(",")) {
		    	File file = new File(conFile);
		    	if (file.isDirectory() || !file.exists()) message = "config file must be sessionlog.config";
		    	else files += "file://" + conFile + ",";
	    	}
	    	configFile_ = files.substring(0,files.length()-1);
	    }
	    
	    //set lib jars
	    if (Util.isBlank(lib_)) message = "lib jars dir not configed";
	    else {
	    	File file = new File(lib_);
	    	if (file.isFile() || !file.exists()) message = "lib jars dir error";
	    }
		if (Util.isNotBlank(numReduceTasks_)) {
			try {
				numReduce = Integer.parseInt(numReduceTasks_);
			} catch (NumberFormatException e) {
				message = "reduce task number not int";
			}
		}
		if (client != null) client.close();
		if (message != null) {
			exitUsage();
			fail(message);
		}
	}
	
	private void setJobConf() throws IOException {
		config_.set("tmpfiles", Util.isNotBlank(attachments_)? configFile_+","+attachments_ : configFile_);
		
		//set tmpjars
		final StringBuffer sb = new StringBuffer();
		if (!lib_.endsWith("/")) lib_ = lib_ + "/";
        File libFile = new File(lib_);
        String libFiles[] = libFile.list();
        boolean first = true;
        for (String name : libFiles) {
        	if (name.endsWith(".jar")) {
        		if (first) first = false;
        		else sb.append(",");
				sb.append("file://").append(lib_).append(name);
			}
        }
        config_.set("tmpjars", sb.toString());
        config_.set("mapred.job.name", Util.isNotBlank(jobName_) ? jobName_ : "new-sessionlog");
        config_.set("mapred.mapper.new-api", "true");
        config_.set("mapred.reducer.new-api", "true");
        config_.set("mapred.input.dir", input_);
        config_.set("mapred.output.dir", output_);
        config_.set("mapred.child.java.opts", "-Xmx1024m");
        Path outputPath = new Path(output_);
        FileSystem fs = FileSystem.get(outputPath.toUri(), config_);
        if (fs.exists(outputPath)) {
        	exitUsage();
			fail("output path exits, check");
        }
	}
	
	private int submitAndMonitorJob() throws IOException {
		
		Job job = new Job(config_);
		FileInputFormat.setInputPathFilter(job, InputPathFilter.class);
        job.setInputFormatClass(ConfigurableTextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setMapperClass(SessionLogMapper.class);
        job.setReducerClass(SessionLogReducer.class);
        job.setPartitionerClass(HashPartitioner.class);
        job.setNumReduceTasks(numReduce);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(BytesWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
//        FileOutputFormat.setCompressOutput(job, true);
//        FileOutputFormat.setOutputCompressorClass(job, BZip2Codec.class);
        try {
			job.submit();
			boolean success = job.waitForCompletion(true);
			return success ? 0 : 1;
		} catch (Exception e) {
			LOG.warn("submit sessionlog job error for : " + e);
			return 1;
		}
	}

}
