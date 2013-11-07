package safe;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.InputFormat;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.LineRecordReader;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.net.NetworkTopology;

public class SafeTextInputFormat extends FileInputFormat<LongWritable, Text>
		implements InputFormat<LongWritable, Text> {
	
	
	private static final double SPLIT_SLOP = 1.1;   // 10% slop

	private long minSplitSize = 1;
	 
	static final String NUM_INPUT_FILES = "mapreduce.input.num.files";
	
	private static final Log LOG = LogFactory.getLog(SafeTextInputFormat.class);

	@SuppressWarnings("deprecation")
	@Override
	public RecordReader<LongWritable, Text> getRecordReader(InputSplit split,
			JobConf job, Reporter arg2) throws IOException {
		try {
			return new LineRecordReader(job, (FileSplit) split);
		} catch (IOException e) {
			return new SkipLineRecordReader(job, (FileSplit) split);
		}
	}

	protected boolean isSplitable(FileSystem fs, Path filename) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	  public InputSplit[] getSplits(JobConf job, int numSplits)
	    throws IOException {
	    FileStatus[] files = listStatus(job);
	    
	    // Save the number of input files in the job-conf
	    job.setLong(NUM_INPUT_FILES, files.length);
	    long totalSize = 0;                           // compute total size
	    for (FileStatus file: files) {                // check we have valid files
	      if (file.isDir()) {
	        throw new IOException("Not a file: "+ file.getPath());
	      }
	      totalSize += file.getLen();
	    }

	    long goalSize = totalSize / (numSplits == 0 ? 1 : numSplits);
	    long minSize = Math.max(job.getLong("mapred.min.split.size", 1),
	                            minSplitSize);

	    // generate splits
	    ArrayList<FileSplit> splits = new ArrayList<FileSplit>(numSplits);
	    NetworkTopology clusterMap = new NetworkTopology();
	    for (FileStatus file: files) {
	      Path path = file.getPath();
	      FileSystem fs = path.getFileSystem(job);
	      long length = file.getLen();
	      BlockLocation[] blkLocations = fs.getFileBlockLocations(file, 0, length);
	      if ((length != 0) && isSplitable(fs, path)) { 
	        long blockSize = file.getBlockSize();
	        long splitSize = computeSplitSize(goalSize, minSize, blockSize);

	        long bytesRemaining = length;
	        while (((double) bytesRemaining)/splitSize > SPLIT_SLOP) {
	          String[] splitHosts = getSplitHosts(blkLocations, 
	              length-bytesRemaining, splitSize, clusterMap);
	          splits.add(new FileSplit(path, length-bytesRemaining, splitSize, 
	              splitHosts));
	          bytesRemaining -= splitSize;
	        }
	        
	        if (bytesRemaining != 0) {
	          splits.add(new FileSplit(path, length-bytesRemaining, bytesRemaining, 
	                     blkLocations[blkLocations.length-1].getHosts()));
	        }
	      } else if (length != 0) {
	    	  try {
	    		  String[] splitHosts = getSplitHosts(blkLocations,0,length,clusterMap);
	    	        splits.add(new FileSplit(path, 0, length, splitHosts));
	    	  } catch (ArrayIndexOutOfBoundsException e) {
	    		  splits.add(new FileSplit(path, 0, length, blkLocations[0].getHosts()));
	    	  }
	    	  
	      } else { 
	        //Create empty hosts array for zero length files
	        splits.add(new FileSplit(path, 0, length, new String[0]));
	      }
	    }
	    LOG.debug("Total # of splits: " + splits.size());
	    return splits.toArray(new FileSplit[splits.size()]);
	  }

}
