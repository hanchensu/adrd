package com.sohu.adrd.data.sessionlog.util;

import java.util.Arrays;
import java.util.Iterator;

import com.sohu.adrd.data.sessionlog.thrift.operation.OperationType;

public class PriorityQueue {

	private int size = 0;
	private int cgIndex = 0;
	private OperationType operationType = null;
	private ProcessorEntry[] queue = new ProcessorEntry[1024];

	public PriorityQueue(int cgIndex, OperationType operationType) {
		this.cgIndex = cgIndex;
		this.operationType = operationType;
	}

	public byte[] getBytes() {
		return null;
	}

	public int size() {
		return size;
	}

	public void add(ProcessorEntry entry) {
		if (size + 1 == queue.length)
			queue = Arrays.copyOf(queue, 2 * queue.length);
		queue[++size] = entry;
		fixUp(size);
	}

	public ProcessorEntry getMin() {
		return queue[1];
	}

	public void removeMin() {
		queue[1] = queue[size];
		queue[size--] = null;
		fixDown(1);
	}

	public void quickRemove(int i) {
		assert i <= size;
		queue[i] = queue[size];
		queue[size--] = null;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		for (int i = 1; i <= size; i++)
			queue[i] = null;
		size = 0;
	}
	
	private void fixUp(int k) {
        while (k > 1) {
            int j = k >> 1;
            if (queue[j].getTimestamp() <= queue[k].getTimestamp()) break;
            ProcessorEntry tmp = queue[j];  queue[j] = queue[k]; queue[k] = tmp;
            k = j;
        }
    }

    private void fixDown(int k) {
        int j;
        while ((j = k << 1) <= size && j > 0) {
            if (j < size && queue[j].getTimestamp() > queue[j+1].getTimestamp()) j++;
            if (queue[k].getTimestamp() <= queue[j].getTimestamp()) break;
            ProcessorEntry tmp = queue[j];  queue[j] = queue[k]; queue[k] = tmp;
            k = j;
        }
    }

//	private void fixUp(int k) {
//		while (k > 1) {
//			int j = k >> 1;
//			if ((queue[j].getTimestamp() < queue[k].getTimestamp() || (queue[j]
//					.getTimestamp() == queue[k].getTimestamp() && compareTo(
//					queue[j], queue[k]) < 0))
//					|| (queue[j].getTimestamp() == queue[k].getTimestamp() && compareTo(
//							queue[j], queue[k]) == 0))
//				break;
//			ProcessorEntry tmp = queue[j];
//			queue[j] = queue[k];
//			queue[k] = tmp;
//			k = j;
//		}
//	}
//
//	private void fixDown(int k) {
//		int j;
//		while ((j = k << 1) <= size && j > 0) {
//			if (j < size
//					&& queue[j].getTimestamp() > queue[j + 1].getTimestamp()
//					|| (queue[j].getTimestamp() == queue[j + 1].getTimestamp() && compareTo(
//							queue[j], queue[j + 1]) > 0))
//				j++;
//			if (queue[k].getTimestamp() <= queue[j].getTimestamp())
//				break;
//			ProcessorEntry tmp = queue[j];
//			queue[j] = queue[k];
//			queue[k] = tmp;
//			k = j;
//		}
//	}

	public void heapify() {
		for (int i = size / 2; i >= 1; i--)
			fixDown(i);
	}

	public int compareTo(ProcessorEntry pe1, ProcessorEntry pe2) {
		return compareTo(pe1.getData(), pe1.getOffset(), pe1.getLength(),
				pe2.getData(), pe2.getOffset(), pe2.getLength());
	}

	public int compareTo(byte[] buffer1, int offset1, int length1,
			byte[] buffer2, int offset2, int length2) {
		// Short circuit equal case
		if (buffer1 == buffer2 && offset1 == offset2 && length1 == length2) {
			return 0;
		}

		int end1 = offset1 + length1;
		int end2 = offset2 + length2;
		for (int i = offset1, j = offset2; i < end1 && j < end2; i++, j++) {
			int a = (buffer1[i] & 0xff);
			int b = (buffer2[j] & 0xff);
			if (a != b) {
				return a - b;
			}
		}
		return length1 - length2;
	}

	public Iterator<ProcessorEntry> iterator() {
		return new Iterator<ProcessorEntry>() {

			public boolean hasNext() {
				if (PriorityQueue.this.isEmpty())
					return false;
				return true;
			}

			public ProcessorEntry next() {
				ProcessorEntry entry = PriorityQueue.this.getMin();
				PriorityQueue.this.removeMin();
				return entry;
			}

			public void remove() {
				throw new RuntimeException("Not supported operation");
			}
		};
	}

	public int getCgIndex() {
		return cgIndex;
	}

	public void setCgIndex(int cgIndex) {
		this.cgIndex = cgIndex;
	}

	public OperationType getOperationType() {
		return operationType;
	}

}
