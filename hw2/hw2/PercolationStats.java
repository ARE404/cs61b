package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int T;
    private double[] fractions;
    private int N;
    private Percolation p;

    public PercolationStats(int n, int t, PercolationFactory pf) {
        if (t <= 0 || n <= 0) {
            throw new IllegalArgumentException("n and t should be positive");
        }
        T = t;
        N = n;
        this.fractions = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int row = StdRandom.uniform(n);
                int col = StdRandom.uniform(n);
                p.open(row, col);
            }
            fractions[i] = (double)p.numberOfOpenSites() / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(fractions);
    }

    public double stddev() {
        return StdStats.stddev(fractions);
    }

    private double confidenceHelper() {
        return 1.96 * stddev() / Math.sqrt(T);
    }
    public double confidenceLow() {
        return mean() - confidenceHelper();
    }

    public double confidenceHigh() {
        return mean() + confidenceHelper();
    }
}
