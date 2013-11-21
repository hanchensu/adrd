package bayes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class PredictTest implements Tool {

	protected Configuration _conf = new Configuration();

	@Override
	public Configuration getConf() {
		return _conf;
	}

	@Override
	public void setConf(Configuration conf) {
		_conf = conf;

	}

	public static void readStrength(Map<String, Double> strengthMap,
			String filename, int uvThreshold) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(filename)));
		int userM = 0, userF = 0;
		String str;
		while ((str = br.readLine()) != null) {
			String[] splits = str.split("\\p{Blank}+");
			if(splits.length < 5) continue;
			int uvF = Integer.parseInt(splits[3]);
			int uvM = Integer.parseInt(splits[4]);
			userM += uvM;
			userF += uvF;
			if (uvF + uvM < uvThreshold || uvF == 0 || uvM == 0)
				continue;
			double rate = ((double) uvF) / uvM;
			strengthMap.put(splits[0], rate);
		}
		br.close();
		
		for(String pageid:strengthMap.keySet()) {
			strengthMap.put(pageid, strengthMap.get(pageid) / (((double) userF) / userM));
		}

	}

	public static class PredictMapper extends Mapper<Object, Text, Text, Text> {

		static Map<String, Double> strengthAll = null;
		static Map<String, Double> strengthM = null;
		static Map<String, Double> strengthF = null;

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {

			final double thresholdF = Double.parseDouble(context.getConfiguration().get("rate.thresholdF", "0.0"));
			final double thresholdM = Double.parseDouble(context.getConfiguration().get("rate.thresholdM", "0.0"));
			final double thresholdTotal = Double.parseDouble(context.getConfiguration().get("rate.thresholdTotal", "0.0"));
			final int topF = Integer.parseInt(context.getConfiguration().get("num.topF", "0"));
			final int topM = Integer.parseInt(context.getConfiguration().get("num.topM", "0"));
			final int topTotal = Integer.parseInt(context.getConfiguration().get("num.topTotal", "0"));
			final int uvThreshold = Integer.parseInt(context.getConfiguration().get("num.uvThreshold", "100"));

			if (strengthAll == null) {
				strengthAll=new HashMap<String, Double>();
				strengthM=new HashMap<String, Double>();
				strengthF=new HashMap<String, Double>();
				readStrength(strengthAll,context.getConfiguration().get("file.pvuv"),uvThreshold);
				for(String pageid:strengthAll.keySet()) {
					double rate = strengthAll.get(pageid);
					if(rate > 1) strengthF.put(pageid, rate); 
					else strengthM.put(pageid, 1.0/rate); 
				}
			}
			
			
			
			
			String line = value.toString();
			String[] splits = line.split("\\p{Blank}+");
			String userid = splits[0];
			String pages="";
			
			
			String regex = "\\(([^,]+),(\\d+),(\\d+)\\)";
			Pattern pattern = Pattern.compile(regex);
			Matcher match = pattern.matcher(splits[1]);
			while (match.find()) {
			    String pageid = match.group(2);
			    pages+=pageid+",";
				
			    
			}
			
			context.write(new Text(userid+"______________________"), new Text(line+"_____________________________"+pages));

			
		}
	}

	@Override
	public int run(String[] args) throws Exception {

		Job job = new Job(_conf, "Predict Test");

		job.setJarByClass(PredictTest.class);
		job.setMapperClass(PredictMapper.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setNumReduceTasks(0);

		System.out.println(_conf.get("bayes.testinput"));
		System.out.println(_conf.get("bayes.testoutput"));

		FileInputFormat.addInputPaths(job, _conf.get("bayes.testinput"));
		FileOutputFormat.setOutputPath(job,
				new Path(_conf.get("bayes.testoutput")));

		// System.out.println(job.waitForCompletion(true) ? 0 : 1);
		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int ret = ToolRunner.run(new PredictTest(), args);
		if (ret != 0) {
			System.err.println("Job Failed!");
			System.exit(ret);
		}
	}
}
