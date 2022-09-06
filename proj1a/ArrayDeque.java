public class ArrayDeque<T> {
    private T[] item;
    private int size;
    private double useRate = 0.0;

    public ArrayDeque() {
        item = (T[]) new Object[8];
        size = 0;
    }

//    public ArrayDeque(ArrayDeque other) {
//        this.item = (T[]) new Object[other.size];
//        System.arraycopy(other.item, 0, this.item, 0, other.size - 1);
//        this.size = other.size;
//    }

    private void computeUseRate() {
        this.useRate = ((double) this.size) / this.item.length;
    }
    private void resize(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        System.arraycopy(this.item, 0, newArray, 0, this.size);
        this.item = newArray;
    }

    private void shrink() {
        computeUseRate();
        if (this.useRate < 0.25) {
            resize(this.item.length / 2);
        }
    }

    public void addFirst(T itemAdded) {
        if (this.size == this.item.length) {
            resize(this.size * 2);
        }
        for (int index = this.size - 1; index >= 0; index -= 1) {
            this.item[index + 1] = this.item[index];
        }
        this.item[0] = itemAdded;
        this.size += 1;
    }

    public void addLast(T itemAdded) {
        if (this.size == this.item.length) {
            resize(this.size * 2);
        }
        this.item[this.size] = itemAdded;
        this.size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return this.size;
    }

    public void printDeque() {
        for (int i = 0; i < size; i += 1) {
            System.out.print(this.item[i]);
            System.out.print(" ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T returnItem = this.item[0];
        for (int i = 0; i < size - 1; i += 1) {
            this.item[i] = this.item[i + 1];
        }
        this.item[size - 1] = null;
        this.size -= 1;
        shrink();
        return returnItem;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T returnItem = this.item[size - 1];
        this.item[size - 1] = null;
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
