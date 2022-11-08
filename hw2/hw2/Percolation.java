package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import javax.xml.transform.sax.SAXTransformerFactory;
import java.awt.desktop.AboutEvent;
import java.util.AbstractList;

public class Percolation {
    private final int length;
    private WeightedQuickUnionUF u;
    private WeightedQuickUnionUF uWithOutBW;
    private boolean[] state;
    private final int virtualTop;
    private final int virtualBottom;
    private int numberOfOpenSites;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be positive.");
        }
        this.length = N;
        this.u = new WeightedQuickUnionUF(N * N + 2);
        this.uWithOutBW = new WeightedQuickUnionUF(N * N + 2);
        this.state = new boolean[N * N];
        this.numberOfOpenSites = 0;
        this.virtualTop = N * N;
        this.virtualBottom = N * N + 1;
    }

    private int xyTo1D(int x, int y) {
        if (x > length - 1 || y > length - 1 || x < 0 || y < 0) {
            throw new IndexOutOfBoundsException("illegal position");
        }
        return x * length + y;
    }

    // num++
    public void open(int row, int col) {
        int id = xyTo1D(row, col);
        if (state[id]) {
            return;
        }
        state[id] = true;
        numberOfOpenSites++;
        unionAround(row, col);
    }

    private void unionAround(int row, int col) {
        int id = xyTo1D(row, col);

        int above = id - length;
        int below = id + length;
        if (row == 0) {
            u.union(id, virtualTop);
            uWithOutBW.union(id, virtualTop);
        } else if (state[above]) {
            u.union(id, above);
            uWithOutBW.union(id, above);
        }
        if (row == length - 1) {
            u.union(id, virtualBottom);
        }else if (state[below]) {
            u.union(id, below);
            uWithOutBW.union(id, below);
        }

        int left = id - 1;
        int right = id + 1;
        if (col != 0 && state[left]) {
            u.union(id, left);
            uWithOutBW.union(id, left);
        }
        if (col != length - 1 && state[right]) {
            u.union(id, right);
            uWithOutBW.union(id, right);
        }
    }

    public boolean isOpen(int row, int col) {
        return state[xyTo1D(row, col)];
    }

    public boolean isFull(int row, int col) {
        return uWithOutBW.connected(virtualTop, xyTo1D(row, col));
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return u.connected(virtualTop, virtualBottom);
    }

    public static void main(String[] args) {

    }
}
