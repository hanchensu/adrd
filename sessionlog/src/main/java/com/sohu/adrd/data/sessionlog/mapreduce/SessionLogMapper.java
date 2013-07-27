package com.sohu.adrd.data.sessionlog.mapreduce;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.util.ReflectionUtils;

import com.sohu.adrd.data.common.FormatResult;
import com.sohu.adrd.data.common.Util;
import com.sohu.adrd.data.sessionlog.config.PathConfig;
import com.sohu.adrd.data.sessionlog.config.PreprocessConfig;
import com.sohu.adrd.data.sessionlog.config.PreprocessFiles;
import com.sohu.adrd.data.sessionlog.config.SessionLogConfig;
import com.sohu.adrd.data.sessionlog.util.Extractor;
import com.sohu.adrd.data.sessionlog.util.Formator;


public class SessionLogMapper extends Mapper<LongWritable, Text, Text, BytesWritable> {

	private SessionLogConfig config = null;
	private Formator formator = null;
	private Extractor extractor = null;
	
	
	private ByteArrayOutputStream buffer = null;
	private DataOutputStream output = null;
	private static final Log LOG = LogFactory.getLog(SessionLogMapper.class);
	
	@SuppressWarnings("unchecked")
	protected void setup(Context context) throws IOException, InterruptedException {
		Configuration conf = context.getConfiguration();
		config = Util.loadConfig(conf);
		if (config == null || !config.check()) throw new IOException("sessionlog.config logic error");
		String splitPath = ((FileSplit)context.getInputSplit()).getPath().getParent().toString();
		LOG.warn("mapper split path : " + splitPath);
		for (PathConfig pathConfig : config.getPathConfigs()) {
			if (splitPath.startsWith(pathConfig.getLocation())) {
				String format = pathConfig.getFormator();
				String extract = pathConfig.getExtractor();
				@SuppressWarnings("rawtypes")
				Class formatorClazz = null, extractorClazz = null;
				try {
					formatorClazz = Class.forName(format);
					extractorClazz = Class.forName(extract);
				} catch (ClassNotFoundException e) {
					throw new IOException("Class for formator/extractor" + " not found, " +
					"check distributedCache clas path configed");
				}
				formator = (Formator)ReflectionUtils.newInstance(formatorClazz, null);
				extractor = (Extractor)ReflectionUtils.newInstance(extractorClazz, null);
			}
		}
		if (formator == null || extractor == null) {
			throw new RuntimeException(splitPath + " format or extractor not configed in sessionlog.config");
		}
		
		
		for (PreprocessConfig preprocessConfig : config.getPreprocessConfigs()) {
			if (splitPath.startsWith(preprocessConfig.getPath())) {
				String processors = preprocessConfig.getProcessors();
				@SuppressWarnings("rawtypes")
				Class preprocessClazz = null;
				for(String classname:processors.split(",")) {
					classname = classname.trim();
					try {
						preprocessClazz = Class.forName(classname);
					} catch (ClassNotFoundException e) {
						throw new IOException("Class for preprocessors" + " not found, " +
								"check distributedCache class path configed");
					}
					preprocessors.add((Preprocessor)ReflectionUtils.newInstance(preprocessClazz, null));
				}
				Util.loadPreprocessFiles(preprocessFiles,preprocessConfig.getPath(),conf);
			}
		}
		
		
	}
	
	protected void map(LongWritable key, Text value, Context context) throws IOException, 
	InterruptedException {
		String strValue = value.toString();
		if (Util.isBlank(strValue)) {
			return;
		}
		
		FormatResult formatRes = null;
		
		
		String userKey = null;
		try {
			
			formatRes = formator.format(strValue);
			
			if (formatRes.strs == null) {
				String DEL_MARK = "DelByMapper_";
				String errorCode = formatRes.errorcode;
				if(!errorCode.contains("Pv_")) {
					context.write(new Text(DEL_MARK + formatRes.errorcode+"_"+ strValue), new BytesWritable());
				}
				return;
			}
			
			
			
			extractRes = extractor.extract(preprocessRes);
			
			if (extractRes.res == null || extractRes.res.size() == 0) {
				if(!DEL_MARK.contains("Pv_")) {
					context.write(new Text(DEL_MARK + "Extract_" + extractRes.errorcode+"_"+ strValue), new BytesWritable());
				}
				return;
			}
			for (ExtractorEntry entry : extractRes.res) {
				if (entry == null || !entry.check()) {
					if(!DEL_MARK.contains("Pv_")) {
						context.write(new Text(DEL_MARK + "EntryCheck_" + strValue), new BytesWritable());
					}
					continue;
				}
				if (output == null) {
					buffer = new ByteArrayOutputStream(512);
					output = new DataOutputStream(buffer);
				}
				if (userKey == null) userKey = entry.getUserKey();
				output.write(entry.getOperation().getOperateId());
				output.writeLong(entry.getTimestamp());
				output.write(entry.getData(), entry.getOffset(), entry.getLength());
//				String typeKey = extractor.getTypeId(formatRes.strs);
//				context.write(new Text("AcByMapper_" + typeKey +"_" +strValue), new BytesWritable());
				context.write(new Text(userKey), new BytesWritable(buffer.toByteArray()));
				
				buffer.reset();
			}
		} catch (Exception e) {
			context.write(new Text("DelByMapper_"+e.getClass()+"_"+strValue), new BytesWritable());
		}
	}
	
	protected void cleanup(Context context) throws IOException, InterruptedException {
		try {
		    if (output != null) output.close();
		} catch (IOException e) {
		}		
	}

}