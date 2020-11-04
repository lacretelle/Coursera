import edu.princeton.cs.algs4.WeightedQuickUnionUF;
// test de monte carlo
// 1- Initialisation tout blocked
// 2- puis remplir les sites ouverts aleatoirement
// 3- full open site are connected to the top
// 4- ajout de sites ouverts -> check if connected to the top with its neighbours
// 5- test if percolates 

public class Percolation {
    private int n = 0;
    private boolean[][] grid;
    private int openSites = 0;
    private WeightedQuickUnionUF uf;
    private int top = 0;
    private int end;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.grid = new boolean[n][n];
        int i = 0;
        int j = 0;
        while (i < n) {
            while (j < n) {
                this.grid[i][j] = false;
                j++;
            }
            i++;
        }
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.end = n * n + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if ((row < 1 && row > n) || (col < 1 && col > n)) {
            throw new IllegalArgumentException();
        }
        if (!this.grid[row - 1][col - 1])
        {
            this.grid[row - 1][col - 1]= true;
            this.openSites += 1;
        }

        if (row == 1)
            uf.union(translateToUF(row, col), top);
        if (row == n)
            uf.union(translateToUF(row, col), end);
        // top 
        if (row > 1 && isOpen(row - 1, col))
            uf.union(translateToUF(row, col), translateToUF(row -1, col));
        // bottom
        if (row < n && isOpen(row + 1, col))
            uf.union(translateToUF(row, col), translateToUF(row +1, col));
        // left
        if (col > 1 && isOpen(row, col - 1))
            uf.union(translateToUF(row, col), translateToUF(row, col - 1));
        // right
        if (col < n && isOpen(row, col + 1))
            uf.union(translateToUF(row, col), translateToUF(row, col + 1));
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((row < 1 && row > n) || (col < 1 && col > n)) {
            throw new IllegalArgumentException();
        }
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((row < 1 && row > n) || (col < 1 && col > n)) {
            throw new IllegalArgumentException();
        }
        if (uf.find(translateToUF(row, col)) == uf.find(this.top))
            return true;
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (uf.find(this.top) == uf.find(this.end))
            return true;
        return false;
    }

    // engine to transform grid to object uf (map)
    private int translateToUF(int row, int col)
    {
        return n * (row - 1) + col;
    }
    // test client (optional)
    public static void main(String[] args) {
    // a mian for test client
    }
}
