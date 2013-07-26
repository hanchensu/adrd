package sessionlog.test;

import java.io.File;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;

import sessionlog.mapreduce.SessionLogInputFormat;
import sessionlog.mapreduce.SessionLogOutputFormat;
import sessionlog.mapreduce.SessionLogMapper;
import sessionlog.mapreduce.SessionLogReducer;

public class JobGo {
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		/*
		conf.set("mapred.job.tracker", "master:9001");
		conf.set("fs.default.name", "hdfs://master:9000");
		JobClient jobClient = new JobClient(new JobConf(conf));
		jobClient.getJob("job_201208071521_0004").killJob();
		*/
        conf.set("tmpfiles", "file:///Users/mac/conf/sessionlog.config");
        final StringBuffer sb = new StringBuffer();
        File libFile = new File("/Volumes/mandela/workspace/hadoop-0.20.2/");
        String libFiles[] = libFile.list();
        boolean first = true;
        for (String name : libFiles) {
        	if (name.endsWith(".jar")) {
        		if (first) first = false;
        		else sb.append(",");
				sb.append("file://").append("/Volumes/mandela/workspace/hadoop-0.20.2/").append(name);
			}
        }
        conf.set("tmpjars", sb.toString());
        conf.set("mapred.job.name", "sessionlog");
        conf.set("mapred.mapper.new-api", "true");
        conf.set("mapred.reducer.new-api", "true");
        String inputPath = "hdfs://master:9000/pvdata/pv-2012/2012-07-24/1207240000.gz";
        inputPath = inputPath + ",hdfs://master:9000/pvdata/pv-2012/2012-07-24/1207240005.gz";
        inputPath = inputPath + ",hdfs://master:9000/addata/ad-2012/2012-08-29";
        conf.set("mapred.input.dir", inputPath);
        //conf.set("mapred.input.dir", "hdfs://localhost:9000/pvlog/");
        String output = "hdfs://master:9000/test/sessionlog";
        //String output = "file:///opt/zebra";
        //conf.set("mapred.input.dir", "file:///Users/mac/Desktemp/sohu.log");
        conf.set("mapred.output.dir", output);
        //conf.set(FileInputFormat.PATHFILTER_CLASS, "mapred.FileFilter");
        conf.set("mapred.job.tracker", "master:9001");
        conf.set("fs.default.name", "hdfs://master:9000");
        conf.set("mapred.tasktracker.map.tasks.maximum", "12");
        Path outputPath = new Path(output);
        FileSystem fs = FileSystem.get(outputPath.toUri(), conf);
        if (fs.exists(outputPath)) fs.delete(outputPath, true);
        Job job = new Job(conf);
        job.setInputFormatClass(SessionLogInputFormat.class);
        job.setOutputFormatClass(SessionLogOutputFormat.class);
        job.setMapperClass(SessionLogMapper.class);
        job.setReducerClass(SessionLogReducer.class);
        job.setPartitionerClass(HashPartitioner.class);
        job.setNumReduceTasks(4);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(BytesWritable.class);
        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(Text.class);
        job.submit();
        job.waitForCompletion(true);
	}

}