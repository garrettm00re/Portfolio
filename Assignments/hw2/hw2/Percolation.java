package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int openSites;
    private int dimension;
    private WeightedQuickUnionUF wqu1;
    private WeightedQuickUnionUF wqu2;

    private boolean percolates;
    private int vTop;
    private int vBot;
    public Percolation(int N) {
        if (N < 0) {
            throw new java.lang.IllegalArgumentException();
        }
        grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }
        openSites = 0;
        dimension = N;
        wqu1 = new WeightedQuickUnionUF(N * N + 1);
        wqu2 = new WeightedQuickUnionUF(N * N + 2);
        percolates = false;
        vTop = dimension * dimension;
        vBot = dimension * dimension + 1;
    }

    public void open(int row, int col) {
        thrower(row, col);
        if (!grid[row][col]) {
            grid[row][col] = true;
            openSites++;
            createPath(row, col);
        }
    }

    public boolean isOpen(int row, int col) {
        thrower(row, col);
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        thrower(row, col);
        boolean b = wqu1.connected(vTop, wquI(row, col));
        if (b && row == dimension - 1) {
            percolates = true;
        }
        return b;
    }

    private void createPath(int r, int c) {
        if (r == 0) {
            wqu1.union(vTop, wquI(r, c));
            wqu2.union(vTop, wquI(r, c));
        }
        if (c + 1 < dimension && grid[r][c + 1]) {
            wqu1.union(wquI(r, c), wquI(r, c + 1));
            wqu2.union(wquI(r, c), wquI(r, c + 1));
        }
        if (c - 1 >= 0 && grid[r][c - 1]) {
            wqu1.union(wquI(r, c), wquI(r, c - 1));
            wqu2.union(wquI(r, c), wquI(r, c - 1));
        }
        if (r + 1 < dimension && grid[r + 1][c]) {
            wqu1.union(wquI(r, c), wquI(r + 1, c));
            wqu2.union(wquI(r, c), wquI(r + 1, c));
        }
        if (r - 1 >= 0 && grid[r - 1][c]) {
            wqu1.union(wquI(r, c), wquI(r - 1, c));
            wqu2.union(wquI(r, c), wquI(r - 1, c));
        }
        if (r == dimension - 1) {
            wqu2.union(wquI(r, c), vBot);
        }
    }
    private int wquI(int r, int c) {
        int index = (dimension * r) + c;
        return index;
    }
    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        /**if (!percolates) {
            for (int i = 0; i < dimension; i++) {
                if (grid[dimension - 1][i] && wqu.connected(wquI(dimension - 1, i), vTop)) {
                    percolates = true;
                    break;
                }
            }
        }
        return percolates; */
        if (!percolates) {
            percolates = wqu2.connected(vTop, vBot);
        }
        return percolates;
    }
    private void thrower(int r, int c) {
        if (r >= dimension || c >= dimension) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }
}
