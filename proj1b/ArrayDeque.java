/**
 * Array implemented Deque.
 * @param <T>
 * @author bobzhang
 */
public class ArrayDeque<T> implements Deque<T> {
    /** */
    private T[] array;
    /** size of array. */
    private int size;
    private double useRate = 0.0;
    /** start index of array. */
    private int prevFirst;
    /** end index of array */
    private int nextLast;


    public ArrayDeque() {
        array = (T[]) new Object[8];
        prevFirst = array.length / 2;
        nextLast = prevFirst + 1;
        size = 0;
    }

    /** Get previous index in a loop view array */
    private int prevIndex(int index) {
        if (index > 0) {
            return index - 1;
        } else {
            return index + array.length - 1;
        }
    }

    /** Get next index in a loop view array */
    private int nextIndex(int index) {
        if (index < array.length - 1) {
            return index + 1;
        } else {
            return index - array.length + 1;
        }
    }

    /** First index */
    private int first() {
        return nextIndex(prevFirst);
    }

    /** Last index */
    private int last() {
        return prevIndex(nextLast);
    }

    /** Resize array, new size is capacity */
    private void resize(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        int indexP = first();
        for (int i = 0; indexP != nextLast; i += 1) {
            newArray[i] = this.array[indexP];
            indexP = nextIndex(indexP);
        }
        prevFirst = capacity - 1;
        nextLast = size;
        this.array = newArray;
    }

    /** If useRate is below 25%, resize array to half size. */
    private void shrink() {
        useRate = ((double) size) / array.length;
        if (this.useRate < 0.25 && this.array.length > 8) {
            resize(this.array.length / 2);
        }
    }

    /** Add to the beginning of array. */
    @Override
    public void addFirst(T arrayAdded) {
        if (this.size >= this.array.length - 1) {
            resize(this.size * 2);
        }
        array[prevFirst] = arrayAdded;
        prevFirst = prevIndex(prevFirst);
        this.size += 1;
    }

    @Override
    public void addLast(T arrayAdded) {
        if (this.size == this.array.length - 1) {
            resize(this.size * 2);
        }
        array[nextLast] = arrayAdded;
        nextLast = nextIndex(nextLast);
        this.size += 1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void printDeque() {
        for (int i = first(); i != last(); i = nextIndex(i)) {
            System.out.print(this.array[i]);
            System.out.print(" ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T returnItem = this.array[first()];
        array[first()] = null;
        prevFirst = first();
        this.size -= 1;
        shrink();
        return returnItem;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T returnItem = this.array[last()];
        this.array[last()] = null;
        nextLast = last();
        this.size -= 1;
        shrink();
        return returnItem;
    }

    @Override
    public T get(int index) {
        if (isEmpty()) {
            return null;
        }
        return array[(first() + index) % array.length];
    }
}
