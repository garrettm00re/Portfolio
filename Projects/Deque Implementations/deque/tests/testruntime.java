package deque;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

public class testruntime {
    private static void printTimingTable(ArrayDeque<Integer> Ns, ArrayDeque<Double> times, ArrayDeque<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeMethod();
    }

    public static void timeMethod() {
        ArrayDeque<Integer> ns = new ArrayDeque();
        ArrayDeque<Double> times = new ArrayDeque();
        ArrayDeque<Integer> opCounts = new ArrayDeque();
        int n = 1000;
        for (int i = 0; i < 11; i++) {
            ArrayDeque<Integer> list = new ArrayDeque();
            int size = 1000;
            int k = 0;
            Stopwatch timer = new Stopwatch();
            while (k < n) {
                list.addFirst(6);
                k++;
            }
            double time = timer.elapsedTime();
            ns.addLast(n);
            times.addLast(time);
            opCounts.addLast(n);
            n = n * 2;
        }
        printTimingTable(ns, times, opCounts);
    }

    @Test
    public void randomizedtimingTest() {
        ArrayDeque<Integer> ns = new ArrayDeque();
        ArrayDeque<Double> times = new ArrayDeque();
        ArrayDeque<Integer> opCounts = new ArrayDeque();
        int i = 6; // number of powers (2) to initial deque size
        int m = 100;
        while (i > 0) {
            ArrayDeque<Integer> L = new ArrayDeque<>();
            int cm = m;
            int n = 100;
            int cn = n;
            int x = 11; //number of powers (2) to num ops
            while (x > 0) {
                while (cm > 0) {
                    L.addFirst(StdRandom.uniform(0, 100));
                    cm--;
                }
                Stopwatch timer = new Stopwatch();
                while (cn > 0) {
                    int operationNumber = StdRandom.uniform(0, 4);
                    if (operationNumber == 0) {
                        L.addLast(StdRandom.uniform(0, 100));
                    } else if (operationNumber == 1) {
                        L.addFirst(StdRandom.uniform(0, 100));
                    } else if (operationNumber == 2) {
                        L.removeLast();
                    } else if (operationNumber == 3) {
                        L.removeFirst();
                    }
                    cn--;
                }
                double time = timer.elapsedTime();
                opCounts.addLast(m);
                ns.addLast(n);
                times.addLast(time);
                n *= 2;
                cn = n;
                x--;
            }
            m *= 2;
            i--;
        }
        printTimingTable(ns, times, opCounts);
    }
@Test
    public void timeAddRemove() {
        ArrayDeque<Integer> ns = new ArrayDeque();
        ArrayDeque<Double> times = new ArrayDeque();
        ArrayDeque<Integer> opCounts = new ArrayDeque();
        int n = 500;
        int m = 10000;
        for (int i = 0; i < 12; i++) {
            ArrayDeque<Integer> list = new ArrayDeque();
            int counter = 0;
            while (counter < n) {
                list.addLast(5);
                counter++;
            }
            counter = 0;
            Stopwatch timer = new Stopwatch();
            while (counter < m) {
                list.removeLast();
                counter++;
            }
            double time = timer.elapsedTime();
            ns.addLast(n);
            times.addLast(time);
            opCounts.addLast(m);
            n = n * 2;
        }
        printTimingTable(ns, times, opCounts);
    }
}
