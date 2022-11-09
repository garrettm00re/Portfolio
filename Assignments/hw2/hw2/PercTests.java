package hw2;

import org.junit.Test;
import org.junit.Assert;

import static org.junit.Assert.*;

public class PercTests {
    @Test
    public void testCorners() {
        Percolation perc = new Percolation(5);
        perc.open(0, 0);
        perc.open(0, 4);
        perc.open(4, 0);
        perc.open(4, 4);
    }
    @Test
    public void straightLine() {
        Percolation perc = new Percolation(8);
        for (int i = 0; i < 8; i++) {
            perc.open(i, 0);
            assertEquals(true, perc.isOpen(i, 0));
        }
        assertEquals(true, perc.percolates());
    }
    @Test
    public void Immmmmmpercolating() {
        int n = 15;
        while (n > 2) {
            Percolation perc = new Percolation(n);
            int rest = n - (n/2);
            int start = n - rest;
            if (start < rest) {
                int del = start;
                start = rest;
                rest = del;
            } // mayb delete this component
            for (int i = 0; i < start; i++) {
                perc.open(i, i);
                perc.open(i, i + 1);
            }
            for (int i = start; i < (start + rest - 1); i++) {
                perc.open(i + 1, i);
                perc.open(i + 1, i + 1);
            }
            assertFalse(perc.percolates());
            perc.open(start, start);
            assertTrue(perc.percolates());
            n--;
        }
    }
    @Test
    public void percStatsTest() {
        PercolationStats ps = new PercolationStats(100, 1000, new PercolationFactory());
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceHigh());
        System.out.println(ps.confidenceLow());
    }
    @Test
    public void pBoundTest() {
        new PercolationStats(0, 0, new PercolationFactory());
        new PercolationStats(-1, -1, new PercolationFactory());
        new PercolationStats(0, 0, new PercolationFactory());
        new PercolationStats(10, -1, new PercolationFactory());
    }
}