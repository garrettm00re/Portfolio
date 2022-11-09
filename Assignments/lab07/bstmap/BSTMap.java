package bstmap;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;
    private int size;

    private class Node {
        private K key;
        private V val;
        private Node left;
        private Node right;
        private int nSize; //TA rec, move outside

        public Node(K k, V v, int s) {
            key = k;
            val = v;
            nSize = s; // maybe remove
            left = null;
            right = null;
        }
    }
    public BSTMap() {
        root = new Node(null, null, 0);
        size = 0;
    }
    /* Removes all the mappings from this map. */ /** Should it remove everything underneath/including a given node?*/
    public void printInOrder() {
        printHelper(orderTree(root, root, null, null, size));
    }
    private Node[] orderTree(Node n, Node ln, K lower, K least, int sizeLeft, Node ...array) { // upper necessary?
        if (lower == null && n != null) {
            while (n.left != null) {
                n = n.left;
            }
            array[size - sizeLeft] = n;
            return orderTree(root, root, n.key, null, sizeLeft - 1, array);
        } else if (size > 0) {
            if (n != null && n.key != null && least.compareTo(lower) > 0) { //good base case?
                int compare = n.key.compareTo(lower);
                if (compare > 0) {
                    return orderTree(n.left, root, ln.key, null, sizeLeft - 1, array);
                }
                if (compare < 0) {
                    array[size - sizeLeft] = ln; //case needs adjustment ( need to traverse tree to left AND right)
                    return orderTree(root, root, ln.key, null, sizeLeft - 1, array);
                }
            }
        } else {
            return array;
        }
        return null;
    }
    private void printHelper(Node[] arr) {
        for(int i = 0; i < arr.length; i++) {
            System.out.print(arr[i].key + ", " + arr[i].val);
        }
    }
    @Override
    public void clear() {
        root.left = null;
        root.right = null;
        root.key = null;
        root.val = null;
        size = 0;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K kee) {
        return containsHelper(root, kee);
    }
    private boolean containsHelper(Node n, K kee) {
        if (n == null || kee == null || n.key == null) {
            return false;
        } else {
            int compare = n.key.compareTo(kee);
            if (compare > 0) {
                return containsHelper(n.left, kee);
            } else if (compare < 0) {
                return containsHelper(n.right, kee);
            } else {
                return true;
            }
        }
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(root, key);
    }
    private V getHelper(Node n, K k, int ...arr) {
        if (n == null || n.key == null || k == null) { //need to throw exception if key (not n.key) is null
            return null;
        } else {
            int compare = n.key.compareTo(k);
            if (compare > 0) {
                return getHelper(n.left, k);
            } else if (compare < 0) {
                return getHelper(n.right, k);
            } else {
                return n.val;
            }
        }
    }
    /* Returns the number of key-value mappings in this map. */ /**should this work for any node it's called on */
    @Override
    public int size() {
        return size;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        putHelper(root, key, value);
    }
    private Node putHelper(Node n, K k, V v) {
        if (n == null) {
            size++;
            return new Node(k, v, 1);
        } else if (n.key == null) { //should need two cases, if n.key is null and if n itself is null
            size++;
            n.key = k;
            n.val = v;
        } else {
            int compare = n.key.compareTo(k);
            if (compare > 0) { //compareTo method?
                n.left = putHelper(n.left, k, v);
            } else if (compare < 0) { //compareTo method?
                n.right = putHelper(n.right, k, v); // formerly had return statements here but am using assignment to adjust values
            } else {
                n.val = v;
            }
        }
        return n;
    }

    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. */
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Iterator<K> iterator() {
        return null;
    }

    /**@Override
    public void forEach(Consumer<? super K> action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Spliterator<K> spliterator() {
        throw new UnsupportedOperationException();
    } */
}
