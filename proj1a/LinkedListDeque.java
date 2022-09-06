public class LinkedListDeque<T> {
    private class DequeNode {
        private T item;
        private DequeNode prev, next;

        public DequeNode(T x) {
            item = x;
            prev = null;
            next = null;
        }

        public DequeNode(T x, DequeNode p, DequeNode n) {
            item = x;
            prev = p;
            next = n;
        }
    }

    private DequeNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new DequeNode(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /** Create a deep copy of other */
    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new DequeNode(null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;

        for (int i = 0; i < other.size; i += 1) {
            this.addLast((T) other.get(i));
        }
    }

    /**
     * Add an item of type T in front of deque.
     *
     * @param item added item
     */
    public void addFirst(T item) {
        DequeNode newNode = new DequeNode(item, sentinel, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size += 1;
    }

    /**
     * Add an item of type T in back of deque.
     *
     * @param item added item
     */
    public void addLast(T item) {
        DequeNode newNode = new DequeNode(item, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size += 1;
    }

    /**
     * Return whether L is empty.
     *
     * @return boolean value
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return size
     */
    public int size() {
        return size;
    }

    /**
     * Print every item in list from first to last, separated by a space,
     * once all printed add a new line
     */
    public void printDeque() {
        DequeNode p = sentinel;
        while (p.next != null && p.next != sentinel) {
            System.out.print(p.next.item);
            System.out.print(" ");
            p = p.next;
        }
        System.out.println();
    }

    /**
     * Remove and return first item, if it doesn't exist, return null
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            DequeNode first = sentinel.next;
            first.next.prev = sentinel;
            sentinel.next = first.next;
            this.size -= 1;
            return first.item;
        }
    }

    /**
     * Remove and return last item, if it doesn't exist, return null
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            DequeNode last = sentinel.prev;
            last.prev.next = sentinel;
            sentinel.prev = last.prev;
            this.size -= 1;
            return last.item;
        }
    }

    /**
     * Gets the item in the given index, if index beyond range, return null
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else {
            DequeNode p = sentinel.next;
            int counter = 0;
            while (counter < index) {
                p = p.next;
                counter += 1;
            }
            return p.item;
        }
    }

    /** get but recursive version */
    public T getRecursive(int index) {
        if (index == 0) {
            return sentinel.next.item;
        }
        this.removeFirst();
        return getRecursive(index - 1);
    }
}
