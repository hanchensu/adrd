package com.sohu.adrd.data.hive.io;

import java.io.IOException;

import javax.xml.crypto.dsig.Transform;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.util.ReflectionUtils;

public abstract class BaseSequenceRecordReader<K, V> implements RecordReader<K, BytesWritable> {

	private SequenceFile.Reader in;
	private long start;
	private long end;
	private boolean more = true;
	protected Configuration conf;

	public BaseSequenceRecordReader(Configuration conf, FileSplit split)
			throws IOException {
		Path path = split.getPath();
		FileSystem fs = path.getFileSystem(conf);
		this.in = new SequenceFile.Reader(fs, path, conf);
		this.end = split.getStart() + split.getLength();
		this.conf = conf;

		if (split.getStart() > in.getPosition())
			in.sync(split.getStart()); // sync to start

		this.start = in.getPosition();
		more = start < end;
	}

	/**
	 * The class of key that must be passed to {@link #next(Object, Object)}..
	 */
	public Class getKeyClass() {
		return in.getKeyClass();
	}

	/**
	 * The class of value that must be passed to {@link #next(Object, Object)}..
	 */
	public Class getValueClass() {
		return in.getValueClass();
	}

	@SuppressWarnings("unchecked")
	public K createKey() {
		return (K) ReflectionUtils.newInstance(getKeyClass(), conf);
	}

	@SuppressWarnings("unchecked")
	public BytesWritable createValue() {
		return ReflectionUtils.newInstance(BytesWritable.class, conf);
	}

	public synchronized boolean next(K key, BytesWritable value) throws IOException {
		if (!more)
			return false;
		long pos = in.getPosition();
		V trueValue = (V) ReflectionUtils.newInstance(in.getValueClass(), conf); 
		boolean remaining = in.next((Writable) key, (Writable) trueValue);
		if (remaining) {
			transform(key,trueValue,value);
		}
		if (pos >= end && in.syncSeen()) {
			more = false;
		} else {
			more = remaining;
		}
		return more;
	}

	public abstract void transform(K key, V trueValue, BytesWritable value);

//	protected synchronized boolean next(K key) throws IOException {
//		if (!more)
//			return false;
//		long pos = in.getPosition();
//		boolean remaining = (in.next(key) != null);
//		if (pos >= end && in.syncSeen()) {
//			more = false;
//		} else {
//			more = remaining;
//		}
//		return more;
//	}

	

	/**
	 * Return the progress within the input split
	 * 
	 * @return 0.0 to 1.0 of the input byte range
	 */
	public float getProgress() throws IOException {
		if (end == start) {
			return 0.0f;
		} else {
			return Math.min(1.0f, (in.getPosition() - start)
					/ (float) (end - start));
		}
	}

	public synchronized long getPos() throws IOException {
		return in.getPosition();
	}

	protected synchronized void seek(long pos) throws IOException {
		in.seek(pos);
	}

	public synchronized void close() throws IOException {
		in.close();
	}

}
