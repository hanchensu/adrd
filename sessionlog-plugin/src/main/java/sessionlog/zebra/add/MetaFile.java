package sessionlog.zebra.add;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.zebra.tfile.TFile;
import org.apache.hadoop.zebra.tfile.MetaBlockAlreadyExists;
import org.apache.hadoop.zebra.tfile.MetaBlockDoesNotExist;

class MetaFile {

	private MetaFile() {
	}

	static Writer createWriter(Path path, Configuration conf) throws IOException {
		return new Writer(path, conf);
	}

	static Reader createReader(Path path, Configuration conf) throws IOException {
		return new Reader(path, conf);
	}

	static class Reader implements Closeable {
		private Path path;
		private Configuration conf;
		private FSDataInputStream fsdis = null;
		private TFile.Reader metaFile = null;

		Reader(Path path, Configuration conf) {
			this.path = path;
			this.conf = conf;
		}

		private synchronized void checkFile() throws IOException {
			if (metaFile != null) return;
			FileSystem fs = path.getFileSystem(conf);
			fsdis = fs.open(path);
			metaFile = new TFile.Reader(fsdis, fs.getFileStatus(path).getLen(), conf);
		}

		DataInputStream getMetaBlock(String name) throws MetaBlockDoesNotExist, IOException {
			checkFile();
			return metaFile.getMetaBlock(name);
		}

		public void close() throws IOException {
			try {
				if (metaFile != null) {
					metaFile.close();
					metaFile = null;
				}
				if (fsdis != null) {
					fsdis.close();
					fsdis = null;
				}
			} finally {
				if (metaFile != null) {
					try {
						metaFile.close();
					} catch (Exception e) {
					}
					metaFile = null;
				}

				if (fsdis != null) {
					try {
						fsdis.close();
					} catch (Exception e) {
					}
					fsdis = null;
				}
			}
		}
	}

	static class Writer implements Closeable {
		private Path path;
		private Configuration conf;
		private FSDataOutputStream fsdos = null;
		private TFile.Writer metaFile = null;

		Writer(Path path, Configuration conf) {
			this.path = path;
			this.conf = conf;
		}

		private synchronized void checkFile() throws IOException {
			if (metaFile != null) return;
			FileSystem fs = path.getFileSystem(conf);
			fsdos = fs.create(path, false);
			metaFile = new TFile.Writer(fsdos, ColumnGroup.getMinBlockSize(conf),
			ColumnGroup.getCompression(conf), null, conf);
		}

		void finish() throws IOException {
			close();
		}

		public void close() throws IOException {
			try {
				if (metaFile != null) {
					metaFile.close();
					metaFile = null;
				}
				if (fsdos != null) {
					fsdos.close();
					fsdos = null;
				}
			} finally {
				if (metaFile != null) {
					try {
						metaFile.close();
					} catch (Exception e) {
					}
					metaFile = null;
				}

				if (fsdos != null) {
					try {
						fsdos.close();
					} catch (Exception e) {
					}
					fsdos = null;
				}
			}
		}

		public DataOutputStream createMetaBlock(String name) throws MetaBlockAlreadyExists, IOException {
			checkFile();
			return metaFile.prepareMetaBlock(name);
		}
	}
}
