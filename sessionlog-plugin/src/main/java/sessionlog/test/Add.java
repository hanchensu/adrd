package sessionlog.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.zebra.io.BasicTable;
import org.apache.hadoop.zebra.io.TableInserter;
import org.apache.hadoop.zebra.io.TableScanner;
import org.apache.hadoop.zebra.types.TypesUtils;
import org.apache.pig.data.Tuple;

import sessionlog.zebra.add.AddBasicTable;

public class Add {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Path path = new Path("/opt/zebra");
		FileSystem fs = path.getFileSystem(conf);
		if (fs.exists(path)) fs.delete(path, true);
		byte longBuffer[] = new byte[8];
		BytesWritable key = new BytesWritable(longBuffer);
		//create table with two cg
		String schema = "user:string,pv:string";
		String storageHint = "[user];[pv]";
		BasicTable.Writer writer = new BasicTable.Writer(path, schema, storageHint, "user", "jclass:" + LongWritable.Comparator.class.getName(), conf);
		TableInserter partInserter = writer.getInserter("part-0", false);
		Tuple rowTuple = TypesUtils.createTuple(2);
		for (int i = 0; i < 900; i++) {
			if (i > 0 && (i % 100 == 0)) {
				int ind = i / 100;
				partInserter.close();
				partInserter = writer.getInserter("part-" + ind, false);
			}
			String user = "user:" + i;
			String pv = "pv:" + i;
			rowTuple.set(0, user);
			rowTuple.set(1, pv);
			transfer((long)i, longBuffer);
			partInserter.insert(key, rowTuple);
		}
		partInserter.close();
		writer.finish(); writer.close();
		partInserter = null;
		writer = null;
		//iterate table
		BasicTable.Reader reader = new BasicTable.Reader(path, conf);
		reader.setProjection("user,pv");
		TableScanner scanner = reader.getScanner(null, true);
		Tuple row = TypesUtils.createTuple(2);
		long v = 0;
		try {
			while (!scanner.atEnd()) {
				scanner.getValue(row);
				System.out.println(row.get(0) + " " + row.get(1));
				v = 100 + v;
				transfer(v, longBuffer);
				scanner.seekTo(key);
			}
		} finally {
			scanner.close();
		}
		reader.close();
		scanner = null;
		reader = null;
		//add cg
		AddBasicTable.Writer addwriter = new AddBasicTable.Writer(path, "num:int,search:string", "[num];[search]", conf);
		partInserter = addwriter.getInserter("part-0", false);
		for (int i = 0; i < 900; i++) {
			if (i > 0 && (i % 100 == 0)) {
				int ind = i / 100;
				partInserter.close();
				partInserter = addwriter.getInserter("part-" + ind, false);
			}
			String search = "search" + i;
			rowTuple.set(0, i);
			rowTuple.set(1, search);
			transfer((long)i, longBuffer);
			partInserter.insert(key, rowTuple);
		}
		partInserter.close();
		addwriter.finish(); addwriter.close();
		partInserter = null;
		addwriter = null;
		//iterate table
		reader = new BasicTable.Reader(path, conf);
		reader.setProjection("user,pv,num,search");
		scanner = reader.getScanner(null, true);
		row = TypesUtils.createTuple(4);
		v = 0;
		try {
			while (!scanner.atEnd()) {
				scanner.getValue(row);
				System.out.println(row.get(0) + " " + row.get(1) + " " 
				+ row.get(2) + " " + row.get(3));
				v = 100 + v;
				transfer(v, longBuffer);
				scanner.seekTo(key);
			}
		} finally {
			scanner.close();
		}
		reader.close();
		scanner = null;
		reader = null;
	}
	
	private static void transfer(long v, byte writeBuffer[]) {
		writeBuffer[0] = (byte)(v >>> 56);
        writeBuffer[1] = (byte)(v >>> 48);
        writeBuffer[2] = (byte)(v >>> 40);
        writeBuffer[3] = (byte)(v >>> 32);
        writeBuffer[4] = (byte)(v >>> 24);
        writeBuffer[5] = (byte)(v >>> 16);
        writeBuffer[6] = (byte)(v >>>  8);
        writeBuffer[7] = (byte)(v >>>  0);
	}

}
