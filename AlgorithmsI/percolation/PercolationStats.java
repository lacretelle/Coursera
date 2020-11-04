import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private int trials;
    private double[] m;
    private static double confidenceK = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("With the value of n, an error occurred");
        }
        // this.n = n;
        this.trials = trials;
        this.m = new double[trials];
        // test percolation
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                }
            }
            double fraction = (double) perc.numberOfOpenSites() / (n * n);
            this.m[i] = fraction;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.m);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.m);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - ((this.confidenceK * this.stddev()) / Math.sqrt(this.trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + ((this.confidenceK * this.stddev()) / Math.sqrt(this.trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        String name = args[0];
        int n;
        int trials;
        if (!name.equals("PercolationStats")) {
            StdOut.println("This test not exist, write PercolationStats.");
        }
        n = Integer.parseInt(args[1]);
        trials = Integer.parseInt(args[2]);

        PercolationStats pStats = new PercolationStats(n, trials);

        String confidence = "[" + pStats.confidenceLo() + ", "
                + pStats.confidenceHi() + "]";
        StdOut.println("mean                    = " + pStats.mean());
        StdOut.println("stddev                  = " + pStats.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }

}
