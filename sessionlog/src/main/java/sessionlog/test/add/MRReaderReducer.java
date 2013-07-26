package sessionlog.test.add;

import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.LongWritable;
import sessionlog.mapreduce.AddReducer;

public class MRReaderReducer<Text> extends AddReducer<Text> {
	
	private static final Log LOG = LogFactory.getLog(MRReaderReducer.class);

	protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		if (writer == null) init(key, context.getConfiguration());
		long localId = key.get();
		localId = localId & (ID_MARK);
		String keyword = new String();
		Iterator<Text> kwIt = (Iterator<Text>) values.iterator();
		while(kwIt.hasNext()){
			keyword += kwIt.next().toString();
		}
		byte data[] = keyword.getBytes("utf-8");
		int offset = 0;
		int length = data.length;
		inserter.add(data, offset, length);
		inserter.tupleSet(0);
		inserter.insert(localId);
	}
	
}
