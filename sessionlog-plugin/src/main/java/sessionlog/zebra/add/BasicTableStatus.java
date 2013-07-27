package sessionlog.zebra.add;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.zebra.tfile.Utils;

public final class BasicTableStatus implements Writable {
	
	long size;
	long rows;
	BytesWritable beginKey;
	BytesWritable endKey;

	public BytesWritable getBeginKey() {
		return beginKey;
	}

	public BytesWritable getEndKey() {
		return endKey;
	}

	public long getSize() {
		return size;
	}

	public long getRows() {
		return rows;
	}

	public void readFields(DataInput in) throws IOException {
		size = Utils.readVLong(in);
		rows = Utils.readVLong(in);
		if (rows == 0) {
			beginKey = null;
			endKey = null;
		} else {
			if (beginKey == null) {
				beginKey = new BytesWritable();
			}
			beginKey.readFields(in);
			if (endKey == null) {
				endKey = new BytesWritable();
			}
			endKey.readFields(in);
		}
	}

	public void write(DataOutput out) throws IOException {
		Utils.writeVLong(out, size);
		Utils.writeVLong(out, rows);
		if (rows > 0) {
			beginKey.write(out);
			endKey.write(out);
		}
	}
}
