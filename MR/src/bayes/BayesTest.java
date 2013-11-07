package bayes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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


public class BayesTest implements Tool {

	protected Configuration _conf = new Configuration();
	
	protected String _input = null;
	protected String _output = null;
	protected String _times = null;
	protected String _numReduce = null;

	@Override
	public Configuration getConf() {
		return _conf;
	}

	@Override
	public void setConf(Configuration conf) {
		_conf = conf;

	}

	public static class TestMapper extends Mapper<Object, Text, Text, Text> {
		private static final int THRESHOLD = 200;
		private static final int CHANGE_THRESHOLD = 10;
		static Map<String, Double> mfCnt = null;

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			Random random = new Random();
			int n = random.nextInt(10);
			if( n != 1) {
				return;
			}
			if (mfCnt == null) {
				mfCnt = new HashMap<String, Double>();
				
				FileSystem fs = FileSystem.get(context.getConfiguration());
				FSDataInputStream fin = fs.open(new Path("./sorted"));
				
				BufferedReader br = new BufferedReader(new InputStreamReader(fin));
				String str;
				while ((str = br.readLine()) != null) {
					String[] splits = str.split("\\p{Blank}+");
					String pageId = splits[0];
					double rate = 1;
					Double mNum = 0.0, fNum = 0.0;
					for (String seg : splits[1].split(",")) {
						String[] kv = seg.split(":");
						if (kv.length != 2)
							continue;
						if ("M".equals(kv[0]))
							mNum = Double.parseDouble(kv[1]);
						else
							fNum = Double.parseDouble(kv[1]);
					}
					rate = mNum / fNum;
					if (mNum > 0 && fNum > 0 && mNum + fNum >= THRESHOLD) {
						mfCnt.put(pageId, rate);
					}
				}
				br.close();
				Double mfRate = mfCnt.get("All");
				for (String pageId : mfCnt.keySet()) {
					if (!"All".equals(pageId)) {
						mfCnt.put(pageId, mfCnt.get(pageId) / mfRate);
					}
				}

			}
			
			String Test = "";
			String line = value.toString();
			String[] splits = line.split("\\p{Blank}+");
			String gender = splits[0];
			gender = "0".equals(gender) ? "M" : "F";
			Double rate = mfCnt.get("All");
			int changed = 0;
			for (String seg : splits) {
				String[] kv = seg.split(":");
				if (kv.length != 2)
					continue;
				if (mfCnt.containsKey(kv[0])) {
					changed++;
					
					rate *= mfCnt.get(kv[0]);
					
//					Test += " <"+kv[0]+","+mfCnt.get(kv[0])+"> "; line+" "+Test
				}
			}

			Double frate = 1.0 / (rate + 1.0);
			if (changed > CHANGE_THRESHOLD) {
				context.write(new Text(frate.toString()+" "+gender), new Text(" "));
			}

		}
	}

	@Override
	public int run(String[] args) throws Exception {

		Job job = new Job(_conf, "Bayes Test");

		job.setJarByClass(BayesTest.class);
		job.setMapperClass(TestMapper.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setNumReduceTasks(0);

		FileInputFormat.addInputPaths(job, "/user/adrd-dev/mayimin/demo/data/sample-year");
		FileOutputFormat.setOutputPath(job, new Path("/user/adrd-dev/shc/bayesTestRes"));
		System.out.println(job.waitForCompletion(true) ? 0 : 1);
		return job.waitForCompletion(true) ? 0 : 1;
	}
	
	public static void main(String[] args) throws Exception {
		int ret = ToolRunner.run(new BayesTest(), args);
		if (ret != 0) {
			System.err.println("Job Failed!");
			System.exit(ret);
		}
	}
}
