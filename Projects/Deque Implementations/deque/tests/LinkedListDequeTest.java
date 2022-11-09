package deque.tests;

import deque.LinkedListDeque;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;


/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

        System.out.println("Printing out deque: ");
        lld1.printDeque();

    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double> lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }
    }

    /**
     * my tests begin here
     */
    @Test
    public void typecheck() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        LinkedListDeque<String> lld2 = new LinkedListDeque<>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<>();
        int n = 100;
        int i = 0;
        String s = "h";
        boolean b = true;
        while (n > 0) {
            lld1.addFirst(i);
            lld2.addFirst(s);
            lld3.addFirst(b);
            assertTrue(lld1.getRecursive(0) == i);
            assertTrue(lld2.getRecursive(0) == s);
            assertTrue(lld3.getRecursive(0) == b);
            i++;
            s = s + "h";
            b = b == false;
            n--;
        }
        lld1.printDeque();
        lld2.printDeque();
        lld3.printDeque();
    }

    @Test
    public void addThenRemove() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        int n = 5000;
        int q = 0;
        int r = 0;
        while (n > 0) {
            q = StdRandom.uniform(10, 50000);
            r = q;
            while (q > 0) {
                lld1.addFirst(n);
                lld1.addLast(n);
                q--;
            }
            while (r > 0) {
                lld1.removeFirst();
                lld1.removeLast();
                r--;
            }
            n--;
        }
    }
    @Test
    public void afalrlrf() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        int n = 50000000;
        int i = 0;
        while (n > 0) {
            lld1.addFirst(i);
            i++;
            lld1.addLast(i);
            i++;
            lld1.addLast(i);
            int x = lld1.removeFirst();
            assertTrue(x == i - 2);
            int y = lld1.removeLast();
            assertTrue(y == i);
            n--;
        }
    }

    @Test
    public void equalsOrnah() {
        LinkedListDeque<Integer> e1, n1 = new LinkedListDeque<>();
        LinkedListDeque<String> e2, n2 = new LinkedListDeque<>();
        LinkedListDeque<Boolean> e3, n3 = new LinkedListDeque<>();
        LinkedListDeque<Integer> l1 = new LinkedListDeque<>();
        LinkedListDeque<String> l2 = new LinkedListDeque<>();
        LinkedListDeque<Boolean> l3 = new LinkedListDeque<>();
        int n = 20;
        int i = 0;
        String s = "a";
        boolean b = false;
        while (n > 0) {
            l1.addFirst(i);
            l2.addFirst(s);
            l3.addFirst(b);
            e1 = l1;
            e2 = l2;
            e3 = l3;
            assertTrue(l1.equals(e1));
            assertTrue(l2.equals(e2));
            assertTrue(l3.equals(e3));
            n1.addFirst(i);
            n2.addFirst(s);
            n3.addFirst(b);
            i++;
            int y = StdRandom.uniform(0, 4);
            if (y == 0) {
                s = "b";
            } else if (y == 1) {
                s = "c";
            } else if (y == 2) {
                s = "d";
            } else {
                s = "a";
            }
            b = b == true;
            n1.addLast(0);
            n2.addLast("Fuck");
            n3.addLast(false);
            n--;
        }

        assertFalse(l1.equals(n1));
        assertFalse(l2.equals(n2));
        assertFalse(l3.equals(n3));
    }
    @Test
    public void testIterator() {
        LinkedListDeque<Integer> it = new LinkedListDeque<>();
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
        Iterator<Integer> list = it.iterator();
        for (Iterator<Integer> iter = list; iter.hasNext(); ) {
            int d = iter.next();
            int x = it.get(i);
            assertEquals(d, x);
            i++;
        }
    }
    @Test
    public void randomizedTest() {
        int i = 1000000;
        LinkedListDeque<Integer> L = new LinkedListDeque<>();
        Object lastLast = null;
        Object lastFirst = null;
        int d = 0;
        Object q;
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
            random = StdRandom.uniform(0, 100);
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
                lastLast = L.getRecursive(L.size() - 1);
            } else if (operationNumber == 3) {
                q = L.removeFirst();
                assertEquals(lastFirst, q);
                lastFirst = L.getRecursive(0);
            }
            i--;
        }
    }
    @Test
    public void failingLLDTest() {
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        lld.addLast(1);
        lld.addFirst(2);
        lld.addFirst(3);
        assertEquals(1, (int) lld.removeLast());
        assertEquals(2, (int) lld.removeLast());
        assertEquals(3, (int) lld.removeFirst());
    }
}