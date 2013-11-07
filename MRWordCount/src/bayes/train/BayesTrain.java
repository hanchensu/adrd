package bayes.train;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.parse.HiveParser.stringLiteralSequence_return;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class BayesTrain {

	public static class TrainMapper extends
			Mapper<Object, Text, Text, Text> {


		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] splits = line.split("\\p{Blank}+");
			String gender = splits[0];
			gender = "0".equals(gender) ? "M":"F";
			for(String seg: splits) {
				String[] kv = seg.split(":");
				if(kv.length != 2) continue;
				context.write(new Text(kv[0]), new Text(gender+":"+kv[1]));
			}
			
		}
	}

	public static class TrainReducer extends
			Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterable<Text> values,
				Context context) throws IOException, InterruptedException {
			long maleSum = 0;
			long femaleSum = 0;
			for (Text val : values) {
				String gender = val.toString().split(":")[0];
				if("M".equals(gender)) {
					maleSum++;
				} else {
					femaleSum++;
				}
			}
			context.write(new Text(key), new Text("M:"+maleSum+",F:"+femaleSum));
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();

		Job job = new Job(conf, "Bayes Train");
		
		job.setJarByClass(BayesTrain.class);
		job.setMapperClass(TrainMapper.class);
		job.setReducerClass(TrainReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setNumReduceTasks(Integer.parseInt(args[2]));
		
		FileInputFormat.addInputPaths(job, args[0]);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.out.println(job.waitForCompletion(true) ? 0 : 1);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
