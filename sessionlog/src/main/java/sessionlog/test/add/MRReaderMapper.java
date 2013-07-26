package sessionlog.test.add;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.pig.data.Tuple;

import sessionlog.mapreduce.AddMapper;
import sessionlog.mapreduce.AddReducer;
import sessionlog.op.SearchOperation;

public class MRReaderMapper extends AddMapper<LongWritable, Text> {
	
	public static final String CG_USER = "user";
	public static final String CG_PV = "pv";
	public static final String CG_SEARCH = "search";
	List<SearchOperation> searchList = new ArrayList<SearchOperation>();
	private static final Log LOG = LogFactory.getLog(MRReaderMapper.class);

	protected void map(BytesWritable key, Tuple value, Context context) throws IOException, InterruptedException {
		StringBuffer sb = new StringBuffer();
		decode(key, value);
		searchList = (List<SearchOperation>) list.get(CG_SEARCH);
		if (searchList != null && searchList.size() != 0) {
			for (SearchOperation search : searchList) {
				sb.append("[");
				sb.append(search.getKeywords());
				sb.append("]");
			}
			context.write(new LongWritable(id), new Text(sb.toString()));
		}
	}
	
}
