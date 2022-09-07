public class ArrayDeque<T> {
    private T[] item;
    private int size;
    private double useRate = 0.0;
    /** start index of array */
    private int startindex;
    /** end index of array */
    private int endindex;

    public ArrayDeque() {
        item = (T[]) new Object[8];
        startindex = 0;
        endindex = 0;
        size = 0;
    }

//    public ArrayDeque(ArrayDeque other) {
//        this.item = (T[]) new Object[other.size];
//        System.arraycopy(other.item, 0, this.item, 0, other.size - 1);
//        this.size = other.size;
//    }


    private int prevIndex(int index) {
        if (index > 0) {
            return index - 1;
        } else {
            return index + item.length - 1;
        }
    }

    private int nextIndex(int index) {
        if (index < item.length - 1) {
            return index + 1;
        } else {
            return index - item.length + 1;
        }
    }
    private void resize(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        int index_p = startindex;
        for (int i = 0; index_p != endindex; index_p = nextIndex(index_p)) {
            newArray[i] = this.item[index_p];
        }
        this.item = newArray;
    }

    private void shrink() {
        useRate = ((double) size) / item.length;
        if (this.useRate < 0.25 && this.item.length > 8) {
            resize(this.item.length / 2);
        }
    }

    public void addFirst(T itemAdded) {
        if (this.size == this.item.length) {
            resize(this.size * 2);
        }
        startindex = prevIndex(startindex);
        item[startindex] = itemAdded;
        this.size += 1;
    }

    public void addLast(T itemAdded) {
        if (this.size == this.item.length) {
            resize(this.size * 2);
        }
        endindex = nextIndex(endindex);
        item[endindex] = itemAdded;
        this.size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return this.size;
    }

    public void printDeque() {
        for (int i = startindex; i != endindex; i = nextIndex(i)) {
            System.out.print(this.item[i]);
            System.out.print(" ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T returnItem = this.item[startindex];
        item[startindex] = null;
        startindex = nextIndex(startindex);
        this.size -= 1;
        shrink();
        return returnItem;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T returnItem = this.item[endindex];
        this.item[endindex] = null;
        endindex = prevIndex(endindex);
        this.size -= 1;
        shrink();
        return returnItem;
    }

    public T get(int index) {
        if (isEmpty()) {
            return null;
        }
        return this.item[index];
    }
}
