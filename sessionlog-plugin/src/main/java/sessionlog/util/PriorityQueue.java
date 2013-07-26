package sessionlog.util;

import java.util.Arrays;
import java.util.Iterator;

import sessionlog.mapreduce.ProcessorEntry;
import sessionlog.op.OperationType;

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
	    queue = Arrays.copyOf(queue, 2*queue.length);
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
        return size==0;
    }

    public void clear() {
        for (int i=1; i<=size; i++) queue[i] = null;
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

    public void heapify() {
        for (int i = size/2; i >= 1; i--) fixDown(i);
    }
    
    public Iterator<ProcessorEntry> iterator() {
    	return new Iterator<ProcessorEntry>() {

			public boolean hasNext() {
				if (PriorityQueue.this.isEmpty()) return false;
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
