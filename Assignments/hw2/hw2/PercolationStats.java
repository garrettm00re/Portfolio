package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private PercolationFactory p;
    private int gDim;
    private int expNum;
    private double[] trials;
    private double mean;
    private double stddev;
    private double cLow;
    private double cHigh;

    public PercolationStats(int N, int T, PercolationFactory pf) {  // perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        p = pf;
        gDim = N;
        expNum = T;
        trials = new double[T];
        randomTests(expNum);
        mean = mean();
        stddev = stddev();
        cLow = confidenceLow();
        cHigh = confidenceHigh();
    }

    public double mean() {                                           // sample mean of percolation threshold
        /**double mean = 0.0;
        for (int i = 0; i < expNum; i++) {
            mean += trials[i];
        }
        return mean / expNum;
         */
        return StdStats.mean(trials);
    }
    public double stddev() {                                         // sample standard deviation of percolation threshold
        /**double std = 0.0;
        for (int i = 0; i < expNum; i++) {
            std += Math.pow(2.0, trials[i] - mean);
        }
        std /= (expNum - 1);
        std = Math.pow(1/2, stddev);
        return stddev; */
        return StdStats.stddev(trials);
    }
    public double confidenceLow() {                                 // low endpoint of 95% confidence interval
        double x = ((1.96 * stddev) / Math.sqrt(expNum));
        return mean - x;
    }
    public double confidenceHigh() { // high endpoint of 95% confidence interval
        double x = ((1.96 * stddev) / Math.sqrt(expNum));
        return mean + x;
    }
    private void randomTests(int x) {
        while (x > 0) {
            Percolation pk = p.make(gDim);
            int xT = 0;
            int gridArea = gDim * gDim;
            while (!pk.percolates()) {
                int randR = StdRandom.uniform(0, gDim);
                int randC = StdRandom.uniform(0, gDim);
                pk.open(randR, randC);
            }
            xT = pk.numberOfOpenSites();
            trials[expNum - x] = (double) xT / gridArea; //make sure this is a float (it isnt till u delete this comment >:( )
            x--;
        }
    }
}
