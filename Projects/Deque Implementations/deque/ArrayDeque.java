package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] list;
    private int size;
    private int iFront;
    private int liFront;
    private int iBack;
    private int liBack;
    public ArrayDeque() {
        final int initArray = 8;
        list = (T[]) new Object[initArray];
        iFront = 0;
        iBack = 0;
        liFront = 0; //disgusting
        liBack = 0; //disgusting
        size = 0;
    }
    private class ArrayDequeIterator<T> implements Iterator<T> {
        private int iterated;

        ArrayDequeIterator() {
            iterated = -1;
        }
        @Override
        public boolean hasNext() {
            return iterated < size - 1;
        }
        @Override
        public T next() {
            iterated++;
            return (T) get(iterated);
        }
    }
    public Iterator<T> iterator() {
        return new ArrayDequeIterator<>();
    }
    private void reset() {
        iFront = 0;
        liBack = 0;
        liFront = 0;
        iBack = 0;
    }
    private void resize(boolean b) {
        int capacity = size;
        if (b) {
            capacity *= 2;
        } else {
            capacity = Math.toIntExact(Math.round((list.length / 2)));
        }
        int x = liFront;
        int index = 0;
        int niFront = capacity - size - 2;
        T[] newList = (T[]) new Object[capacity];
        while (true) {
            newList[niFront + index] = list[x]; //should have just used get method
            if (x == liBack) {
                break;
            }
            if (x == list.length - 1) {
                x = 0;
                index++;
                continue;
            }
            index++;
            x++;
        }
        liFront = niFront; //resetting manually, disgusting
        iFront = liFront - 1;
        list = newList;
        liBack = list.length - 2 - 1;
        iBack = list.length - 2;
    }
    @Override
    public void addFirst(T thang) {
        if (size == list.length) {
            resize(true); // lol
        }
        list[iFront] = thang;
        liFront = iFront;
        if (size == 0) { //all of this could have been simplified by modular approach (wrap index)
            iFront = list.length - 1;
            liBack = 0;
            iBack = 1;
        } else if (iFront == 0) {
            iFront = list.length - 1;
        } else {
            iFront--;
        }
        size++;
    }
    @Override
    public void addLast(T thang) {
        if (size == list.length) {
            resize(true);
        }
        liBack = iBack;
        list[iBack] = thang;
        if (iBack == list.length - 1) {
            iBack = 0;
        } else if (size == 0) {
            iBack = 1;
            liFront = 0;
            iFront = list.length - 1;
        } else {
            iBack++;
        }
        size++;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void printDeque() {
        int x = liBack;
        while (true) {
            if (x == 0) {
                System.out.print(list[x] + " ");
                x = list.length - 1;
            }
            if (x == liFront) {
                System.out.print(list[x] + " ");
                break;
            }
            System.out.print(list[x] + " ");
            x--;
        }
        System.out.println();
    }
    @Override
    public T removeFirst() {
        checkResize();
        if (size > 0) {
            T deleted = list[liFront];
            iFront = liFront;
            list[liFront] = null;
            if (size == 1) {
                reset();
            } else if (liFront == list.length - 1) {
                liFront = 0;
            } else {
                liFront++;
            }
            size--;
            return deleted;
        }
        return null;
    }
    @Override
    public T removeLast() {
        checkResize();
        if (size > 0) {
            T deleted = list[liBack];
            list[liBack] = null;
            iBack = liBack;
            if (size == 1) {
                reset();
            } else if (liBack == 0) {
                liBack = list.length - 1;
            } else {
                liBack--;
            }
            size--;
            return deleted;
        }
        return null;
    }
    private void checkResize() {
        float ratio = (float) size / (list.length + 1);
        final float magicNumberHaha = (float) 0.45;
        final int initArraySize = 8;
        if (ratio < magicNumberHaha && size > initArraySize) {
            resize(false);
        }
    }
    @Override
    public T get(int index) {
        if (size == 0) {
            return null;
        }
        int finalIndex = index;
        if (liFront + index >= list.length) {
            finalIndex += list.length - liFront;
        } else {
            finalIndex += liFront;
        }
        return list[finalIndex];
    }
    @Override
    public boolean equals(Object O) {
        boolean b = true;
        if (O instanceof Deque<?>) {
            Deque compare = (Deque) O;
            if (compare.size() == this.size()) {
                for (int i = 0; i < size(); i++) {
                    if (!b) {
                        return false;
                    }
                    b = this.get(i).equals(compare.get(i));
                }
                return b;
            }
        }
        return false;
    }
}
