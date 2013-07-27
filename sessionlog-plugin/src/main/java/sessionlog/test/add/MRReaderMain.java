package sessionlog.test.add;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.zebra.mapreduce.TableInputFormat;

import sessionlog.mapreduce.AddOutputFormat;
import sessionlog.mapreduce.AddPartitioner;

public class MRReaderMain {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		File libFile = new File("/Volumes/mandela/workspace/hadoop-0.20.2/");
		String libFiles[] = libFile.list();
		for (String name : libFiles) {
			String path = "";
			if (name.endsWith(".jar")) {
				path = "file://" + "/Volumes/mandela/workspace/hadoop-0.20.2/" + name;
				addTmpJar(path, conf);
			}
		}
		conf.set("mapred.job.name", "mrreader");
		conf.set("mapred.mapper.new-api", "true");
		conf.set("mapred.reducer.new-api", "true");
		Path input = new Path("hdfs://localhost:9000/sessionlog/");
		Path[] inputpaths = readFolder(input, conf);
		String output = "hdfs://localhost:9000/sessionlog/";
		conf.set("mapred.output.dir", output);
		conf.set("mapred.job.tracker", "localhost:9001");
		conf.set("fs.default.name", "hdfs://localhost:9000");
		//conf.set("hadoop.job.ugi", "root,root");
		//conf.set("mapred.tasktracker.map.tasks.maximum", "4");
		conf.set("sessionlong.add.schema", "keyword:bytes");
		conf.set("sessionlong.add.storage", "[keyword] as keyword");
		conf.set("mapreduce.lib.table.input.projection", "user,search");
		Job job = new Job(conf);
		job.setInputFormatClass(TableInputFormat.class);
		TableInputFormat.setInputPaths(job, inputpaths);
		job.setOutputFormatClass(AddOutputFormat.class);
		job.setMapperClass(MRReaderMapper.class);
		job.setPartitionerClass(AddPartitioner.class);
		job.setReducerClass(MRReaderReducer.class);
		job.setNumReduceTasks(4);
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(Text.class);
		job.submit();
		job.waitForCompletion(true);
	}

	public static void addTmpJar(String jarPath, Configuration conf) throws IOException {
		System.setProperty("path.separator", ":");
		FileSystem fs = FileSystem.getLocal(conf);
		String newJarPath = new Path(jarPath).makeQualified(fs).toString();
		String tmpjars = conf.get("tmpjars");
		if (tmpjars == null || tmpjars.length() == 0) {
			conf.set("tmpjars", newJarPath);
		} else {
			conf.set("tmpjars", tmpjars + "," + newJarPath);
		}
	}

	public static Path[] readFolder(Path path, Configuration conf) throws IOException {
		List<Path> pathList = new ArrayList<Path>();
		FileSystem fs = FileSystem.get(path.toUri(), conf);
		FileStatus[] fileStatusArray = fs.listStatus(path);
		for (FileStatus fileStatus : fileStatusArray) {
			if (!fs.isFile(fileStatus.getPath())) {
				if (fileStatus.getPath().getName().contains("SUB")) {
					pathList.add(new Path(fileStatus.getPath().toString()));
				}
			}
		}
		Path[] paths = new Path[pathList.size()];
		int i = 0;
		for (Path p : pathList) {
			paths[i] = p;
			i++;
		}
		return paths;
	}
	
}