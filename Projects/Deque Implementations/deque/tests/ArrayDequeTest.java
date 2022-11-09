package deque.tests;

import deque.ArrayDeque;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class ArrayDequeTest {
    @Test
    public void nothing() {
        ArrayDeque<Integer> k = new ArrayDeque<>();
        assertTrue(k.size() == 0);
    }

    @Test
    public void addnScale() {
        ArrayDeque<Integer> k = new ArrayDeque<>();
        int n = 500;
        while (n > 0) {
            if (StdRandom.uniform(0, 2) == 1) {
                int q = k.size();
                k.addFirst(StdRandom.uniform(1, 50));
                assertTrue(q + 1 == k.size());
            } else {
                int q = k.size();
                k.addLast(StdRandom.uniform(50, 100));
                assertTrue(q + 1 == k.size());
            }
            n--;
        }
        k.printDeque();
    }

    @Test
    public void oddtoabillion() {
        ArrayDeque<Integer> ann = new ArrayDeque<>();
        int annsN = 100;
        int i = 0;
        while (annsN > 0) {
            ann.addFirst(i);
            i += 2;
            annsN--;
        }
        ann.printDeque();
    }

    @Test
    public void fillUpemptyFillupAgain() {
        int n, q, m;
        n = q = m = 100;
        ArrayDeque<Integer> K = new ArrayDeque();
        while (q > 0) {
            n = m = 10000;
            while (n > 0) {
                int t = StdRandom.uniform(0, 100);
                int decision = StdRandom.uniform(0, 2);
                int index;
                if (decision == 1) {
                    K.addFirst(t);
                    index = 0;
                } else {
                    K.addLast(t);
                    index = K.size() - 1;
                }
                n--;
            }
            while (m > 0) {
                int decision = StdRandom.uniform(0, 2);
                if (decision == 1) {
                    assertEquals(K.get(0), K.removeFirst());
                } else {
                    assertEquals(K.get(K.size() - 1), K.removeLast());
                }
                m--;
            }
            q--;
        }
    }

    @Test
    public void testEquals() {
        ArrayDeque<Integer> l1 = new ArrayDeque<>();
        ArrayDeque<Integer> e1 = new ArrayDeque<>();
        ArrayDeque<Integer> ne1 = new ArrayDeque<>();
        ArrayDeque<Object> l2 = new ArrayDeque<>();
        ArrayDeque<Object> e2 = new ArrayDeque<>();
        ArrayDeque<Object> ne2 = new ArrayDeque<>();
        int n, x, decision;
        n = 300;
        while (n > 0) {
            x = StdRandom.uniform(0, 500);
            decision = StdRandom.uniform(0, 4);
            if (decision == 1) {
                l1.addFirst(x);
                e1.addFirst(x);
                ne1.addFirst(x);
                l2.addFirst(new int[]{x});
                e2.addFirst(new int[]{x});
                ne2.addFirst(new int[]{x});
            } else if (decision == 2) {
                l1.addLast(x);
                e1.addLast(x);
                ne1.addLast(x);
                l2.addLast(new int[]{x});
                e2.addLast(new int[]{x});
                ne2.addLast(new int[]{x});
            } else if (decision == 3) {
                l1.removeFirst();
                e1.removeFirst();
                ne1.removeFirst();
                l2.removeFirst();
                e2.removeFirst();
                ne2.removeFirst();
              }
            else if (n % 25 == 0) {
                l1.addLast(0);
                e1.addLast(0);
                ne1.addLast(999);
                l2.addLast(new int[]{x});
                e2.addLast(new int[]{x});
                ne2.addLast(new int[]{x, x-1, x-2, x-3});
            }
            n--;
        }
        assertTrue(l1.equals(e1));
        assertFalse((l1.equals(ne1)));
        assertTrue(l2.equals(e2));
        assertFalse((l2.equals(ne2)));
    }
    @Test
    public void testIndices() {
        int n = 500;
        ArrayDeque L = new ArrayDeque<Integer>();
        while (n > 0) {
            L.addLast(n % 100);
            assertEquals(L.get(500-n), n % 100) ;
            n--;
        }
    }
    @Test
    public void testIterator() {
        ArrayDeque<Integer> it = new ArrayDeque<>();
        int n = 500;
        int cap = 500;
        while (n > 0) {
            int x = StdRandom.uniform(0, 2);
            if (x == 1) {
                it.addFirst(StdRandom.uniform(2, 100));
            }
            else {
                it.addLast(StdRandom.uniform(2, 100));
            }
            n--;
        }
        int i = 0;
        Iterator<Integer> list = (Iterator<Integer>) it.iterator();
        for (Iterator<Integer> iter = list; list.hasNext(); ) {
            int d = list.next();
            int x = it.get(i);
            assertEquals(d, x);
            i++;
        }
    }
    @Test
    public void randomizedTest() {
        int i = 1000000000;
        ArrayDeque<Integer> L = new ArrayDeque<>();
        Object lastLast = null;
        Object lastFirst = null;
        int d = 0;
        int q;
        int random = StdRandom.uniform(0, 100);
        while (d > 0) {
            int operationNumber = StdRandom.uniform(0, 2);
            if (operationNumber == 0) {
                L.addLast(random);
                q = L.get(L.size() - 1);
                assertEquals(q , random);
                lastLast = random;
            } else if (operationNumber == 1) {
                L.addFirst(random);
                q = L.get(0);
                assertEquals(q , random);
                lastFirst = random;
            }
            random = StdRandom.uniform(0, 100);
            d--;
        }
        while (i > 0) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                L.addLast(random);
                q = L.get(L.size() - 1);
                assertEquals(q , random);
                lastLast = random;
            } else if (operationNumber == 1) {
                L.addFirst(random);
                q = L.get(0);
                assertEquals(q , random);
                lastFirst = random;
            } else if (operationNumber == 2) {
                q = L.removeLast();
                assertEquals(q, lastLast);
                lastLast = L.get(L.size() - 1);
            } else if (operationNumber == 3) {
                q = L.removeFirst();
                assertEquals(lastFirst, q);
                lastFirst = L.get(0);
            }
            random = StdRandom.uniform(0, 100);
            i--;
        }
    }
    @Test
    public void failingADTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(0);
        assertEquals(0, (int) ad.get(0));
        ad.addFirst(1);
        assertEquals(1, (int) ad.get(0));
        assertEquals(0, (int) ad.get(1));
        assertEquals(1, (int) ad.removeFirst());
        assertEquals(0, (int) ad.get(0));
        assertEquals(0, (int) ad.removeFirst());
        ad.addFirst(7);
        assertEquals(7, (int) ad.get(0));
        assertEquals(7, (int) ad.removeLast());
        assertEquals(null, (Object) ad.get(0));
        ad.addFirst(10);
        assertEquals(10, (int) ad.get(0));
        ad.addFirst(12);
        assertEquals(12, (int) ad.get(0));
        assertEquals(10, (int) ad.get(1));
        assertEquals(10, (int) ad.removeLast());
    }
}
