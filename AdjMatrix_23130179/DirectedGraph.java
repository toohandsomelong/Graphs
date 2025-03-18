package AdjMatrix_23130179;

import java.util.LinkedList;
import java.util.Queue;

public class DirectedGraph extends Graph {
    public DirectedGraph(int vertices) {
        super(vertices);
    }

    @Override
    public void addEdge(int x, int y) {
        adjMatrix[x][y] += 1;
    }

    @Override
    public void removeEdge(int x, int y) {
        if (!isValidVertex(x) && !isValidVertex(y))
            return;

        adjMatrix[x][y] = 0;
    }

    public int inDegreeOf(int v) {
        int result = 0;

        for (int i = 0; i < vertices; i++) {
            result += adjMatrix[v][i];
        }

        return result;
    }

    public int outDegreeOf(int v) {
        int result = 0;

        for (int i = 0; i < vertices; i++) {
            result += adjMatrix[i][v];
        }

        return result;
    }

    @Override
    public String getGraphType() {
        if (isHavingArc() || isHavingSelfLoop())
            return "Directed Multigraph";

        return "Simple Directed Graph";
    }

    @Override
    public int degreeOf(int vertex) {
        return inDegreeOf(vertex) + outDegreeOf(vertex);
    }

    @Override
    public boolean isBipartite() {
        int[] color = new int[vertices];
        // -1 is blank.
        for (int i = 0; i < vertices; i++)
            color[i] = -1;

        for (int i = 0; i < vertices; i++) {
            if (color[i] != -1) //is colored -> skip
                continue;

            if (!BFS_Color(i, color))
                return false;
        }
        return true;
    }

    private boolean BFS_Color(int start, int[] color) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        color[start] = 0; // start with color 0

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < vertices; v++) {
                // Check if there is an edge in either direction (ignoring edge direction)
                if (!(adjMatrix[u][v] == 1 || adjMatrix[v][u] == 1))
                    continue;
                if (color[v] == -1) {
                    color[v] = 1 - color[u];
                    queue.offer(v);
                } else if (color[v] == color[u])
                    return false;
            }
        }
        return true;
    }
}
