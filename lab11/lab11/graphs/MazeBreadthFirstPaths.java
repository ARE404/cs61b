package lab11.graphs;

import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.Transferable;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private static final int INFINITY = Integer.MAX_VALUE;
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        for (int i = 0; i < m.V(); i++) {
            distTo[i] = INFINITY;
        }
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> q = new ArrayDeque<>();
        q.add(s);
        marked[s] = true;
        announce();
        while (!q.isEmpty()) {
            int v = q.poll();
            if (v == t) {
                return;
            }
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    q.add(w);
                    announce();
                    if (w == t) {
                        return;
                    }
                }
            }
        }
    }


    @Override
    public void solve() {
         bfs();
    }
}

