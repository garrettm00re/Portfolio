package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private final Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null); //slightly different than staff solution
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    private class Node {
        private Node prev;
        private T item;
        private Node next;

        Node(Node backwards, T current, Node forwards) {
            prev = backwards;
            item = current;
            next = forwards;
        }
    }
    private class LLDIterator<T> implements Iterator<T> {
        private int iterated;
        private Node storage;

        LLDIterator() {
            iterated = 0;
            storage = sentinel;
        }
        public boolean hasNext() {
            return iterated < size;
        }
        public T next() {
            storage = storage.next;
            iterated++;
            return (T) storage.item;
        }
    }
    public Iterator<T> iterator() {
        return new LLDIterator<>();
    }
    @Override
    public void addFirst(T thing) {
        Node newNode;
        if (size > 0) {
            newNode = new Node(sentinel, thing, sentinel.next);
            sentinel.next = newNode;
            sentinel.next.next.prev = newNode; //sentinel.next.prev before setting prev
        } else {
            newNode = new Node(sentinel, thing, sentinel);
            sentinel.next = newNode;
            sentinel.prev = newNode; //is this else case even necessary
        }
        size++;
    }
    @Override
    public void addLast(T thang) {
        Node newNode;
        if (size > 0) {
            newNode = new Node(sentinel.prev, thang, sentinel);
            sentinel.prev = newNode;
            sentinel.prev.prev.next = newNode;
        } else {
            newNode = new Node(sentinel, thang, sentinel);
            sentinel.next = newNode;
            sentinel.prev = newNode;
        }
        size++;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node sendtoPrint = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(sendtoPrint.item + " ");
            sendtoPrint = sendtoPrint.next;
        }
        System.out.println();
    }
    @Override
    public T removeFirst() {
        T deleted = null;
        if (size >= 1) {
            deleted = sentinel.next.item;
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
            size--;
        }
        return deleted;
    }
    @Override
    public T removeLast() {
        T deleted = null;
        if (size >= 1) {
            deleted = sentinel.prev.item;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            size--;
        }
        return deleted;
    }
    @Override
    public T get(int index) {
        int q = setIndex(index);
        Node iterated = sentinel;
        if (q < index) {
            while (q >= 0) {
                iterated = iterated.prev;
                q--;
            }
        } else {
            while (q >= 0) {
                iterated = iterated.next;
                q--;
            }
        }
        return iterated.item;
    }
    private int setIndex(int i) {
        float determinant = 0;
        if (size > 0) {
            determinant = (i + 1) / size;
        }
        if (determinant > 0.5) {
            i = size - 1 - i;
        }
        return i;
    }
    public T getRecursive(int i) {
        String d = "up";
        int index = setIndex(i);
        if (index < i) {
            d = "down";
        }
        return helper(sentinel, index, d);
    }

    private T helper(Node n, int i, String d) {
        if (d.equals("up")) {
            n = n.next;
        } else {
            n = n.prev;
        }
        i--;
        if (i < 0) {
            return n.item;
        }
        return helper(n, i, d);
    }
    @Override
    public boolean equals(Object O) { //implements? -->this will not work
        boolean b = true;
        Node store = sentinel;
        if (O instanceof Deque<?>) {
            Deque compare = (Deque) O;
            if (compare.size() == this.size()) {
                for (int i = 0; i < size; i++) {
                    store = store.next;
                    if (!b) {
                        return false;
                    }
                    b = store.item.equals(compare.get(i));
                }
                return b;
            }
        }
        return false;
    }
}
