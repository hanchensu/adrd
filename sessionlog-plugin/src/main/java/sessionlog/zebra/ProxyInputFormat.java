package sessionlog.zebra;

import java.io.IOException;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.InputFormat;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.zebra.mapred.TableInputFormat;
import org.apache.pig.data.Tuple;

@Deprecated
public class ProxyInputFormat implements InputFormat<Text, Text> {
	
	private TableInputFormat format = null;
	
	public ProxyInputFormat() {
		format = new TableInputFormat();
	}

	public InputSplit[] getSplits(JobConf job, int numSplits) throws IOException {
		return format.getSplits(job, numSplits);
	}

	public RecordReader<Text, Text> getRecordReader(InputSplit split, JobConf job, Reporter reporter) 
	throws IOException {
		String strProj = job.get(TableInputFormat.INPUT_PROJ);
		RecordReader<BytesWritable, Tuple> reader = format.getRecordReader(split, job, reporter);
		return new ProxyRecordReader(reader, strProj);
	}
	
}
