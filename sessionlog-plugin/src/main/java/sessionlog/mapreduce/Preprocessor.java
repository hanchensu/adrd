package sessionlog.mapreduce;

import java.util.List;

import org.apache.hadoop.mapreduce.Mapper.Context;

import sessionlog.config.PreprocessFiles;

public interface Preprocessor {
	
	public List<String> preprocess(List<String> strs, PreprocessFiles preProcessData);

}
