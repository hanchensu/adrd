package sessionlog.test;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.hadoop.io.Writable;

public class UserWritable implements Writable {

	public long id;
	public String Username;
	public HashMap<String, List<Serializable>> OperationHash = new HashMap<String, List<Serializable>>();
	
	public void readFields(DataInput in) throws IOException {
		DataInputTransform input = new DataInputTransform(in);
		ObjectInputStream objInput = null;
        this.id = in.readLong();
        this.Username = in.readUTF();
        int mapSize = in.readInt();
        for (int i = 0; i < mapSize; i++) {
        	String key = in.readUTF();
        	int listSize = in.readInt();
        	List<Serializable> list = new ArrayList<Serializable>();
			if (listSize > 0) {
				objInput = new ObjectInputStream(input);
				for (int j = 0; j < listSize; j++) {
					try {
						list.add((Serializable) objInput.readObject());
					} catch (ClassNotFoundException e) {
						throw new RuntimeException("read data error", e);
					}
				}
        	}
        	OperationHash.put(key, list);
        }
        input.close();
	}

	public void write(DataOutput out) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ObjectOutputStream objOutput = null;
		out.writeLong(this.id);
		out.writeUTF(Username);
		output.reset();
		out.writeInt(OperationHash.size());
		Iterator<Entry<String, List<Serializable>>> it = OperationHash.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String,  List<Serializable>> entry = it.next();
			String key = entry.getKey();
			List<Serializable> value = entry.getValue();
			out.writeUTF(key);
			out.writeInt(value.size());
			if (value.size() > 0) {
				objOutput = new ObjectOutputStream(output);
				for (Serializable serial : value) {
					objOutput.writeObject(serial);
				}
			}
		}
		out.write(output.toByteArray());
		output.close();
	}
	
	public class DataInputTransform extends InputStream {
		
		private DataInput in = null;
		
		public DataInputTransform(DataInput in) {
			this.in = in;
		}

		public int read() throws IOException {
			return in.readByte();
		}
		
		public void close() throws IOException {
			this.in = null;
		}
		
	}
	
}