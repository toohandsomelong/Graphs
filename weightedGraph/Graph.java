package weightedGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public abstract class Graph
{
    protected int vertices = 0;
    protected Double[][] adjMatrix = new Double[0][0];

    public Graph(int v)
    {
        vertices = v;
        adjMatrix = new Double[v][v];
        for (int i = 0; i < adjMatrix.length; i++)
        {
            for (int j = 0; j < adjMatrix[i].length; j++)
            {
                adjMatrix[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    public abstract void addEdge(int x, int y, double w);

    public abstract void removeEdge(int x, int y);

    public abstract void printEdges();

    public abstract double weight();

    protected boolean isValidVertex(int vertex)
    {
        if (vertex > adjMatrix.length - 1 || vertex < 0)
            return false;

        return true;
    }

    public void addVertex(int v)
    {
        Double[][] newAdjMatrix = new Double[vertices + v][vertices + v];

        for (int i = 0; i < vertices; i++)
            for (int j = 0; j < vertices; j++)
                newAdjMatrix[i][j] = adjMatrix[i][j];

        vertices += v;
        adjMatrix = newAdjMatrix;
    }

    public void removeVertex(int v)
    {
        if (!isValidVertex(v))
        {
            System.out.println(v + "is not valid");
            return;
        }

        Double[][] newAdjMatrix = new Double[vertices - 1][vertices - 1];

        //is this the fastest?
        for (int i = 0; i < vertices; i++)
        {
            if (i == v)
                continue;

            for (int j = 0; j < vertices; j++)
            {
                if (j == v)
                    continue;

                int i_temp = i;
                int j_temp = j;

                if (i >= v)
                    i_temp = i - 1;

                if (j >= v)
                    j_temp = j - 1;

                newAdjMatrix[i_temp][j_temp] = adjMatrix[i][j];
            }
        }

        vertices -= 1;
        adjMatrix = newAdjMatrix;

    }

    public void printGraph()
    {
        for (Double[] i : adjMatrix)
        {
            for (Double j : i)
                if (Double.isInfinite(j))
                {
                    System.out.printf("%7s", j > 0 ? "Inf" : "-Inf"); //space 7
                } else {
                    System.out.printf("%7.1f", j); //space 7, decimal 1
                }

            System.out.println();
        }
    }

    public boolean pathConnected(int start, int end)
    {
        Set<Integer> list = new HashSet<Integer>();
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[adjMatrix.length];

        // Mark start vertex as visited and add queue it
        visited[start] = true;
        queue.add(start);

        while (!queue.isEmpty())
        {
            //poll is get first and remove the first one
            int current = queue.poll();
            list.add(current);

            for (int i = 0; i < adjMatrix[current].length; i++)
            {
                if (adjMatrix[current][i] == Double.POSITIVE_INFINITY)
                    continue;

                if (visited[i])
                    continue;

                visited[i] = true;
                queue.add(i);
            }

            if (visited[end] == true)
            {
                list.add(end);
                System.out.println(start + " connected " + end);
                return true;
            }
        }

        return false;
    }

    public UndirectedGraph minimumSpanningTree_Kruskal()
    {
        //get list edge to PriorityQueue
        //aka sorted queue
        PriorityQueue<Edge> listEdges = new PriorityQueue<Edge>(getListEdges());

        int m = 0; //total edge added
        UndirectedGraph temp = new UndirectedGraph(adjMatrix.length);

        while (m < adjMatrix.length - 1)
        {
            Edge e = listEdges.poll();

            if (e == null)
            {
                m++;
                continue;
            }

            if (temp.pathConnected(e.src, e.dest))
                continue;

            temp.addEdge(e.src, e.dest, e.w);
            m++;
        }

        return temp;
    }

    //dijkstra  

    protected List<Edge> getListEdges()
    {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < adjMatrix.length; i++)
        {
            for (int j = i; j < adjMatrix.length; j++)
            {
                if (adjMatrix[i][j] != Double.POSITIVE_INFINITY)
                    edges.add(new Edge(i, j, adjMatrix[i][j]));
            }
        }

        return edges;
    }
}


class Edge implements Comparable<Edge>
{
    int src, dest;
    double w;

    public Edge(int src, int dest, double w)
    {
        this.src = src;
        this.dest = dest;
        this.w = w;
    }

    @Override
    public int compareTo(Edge o)
    {
        if (this.w > o.w)
            return 1;
        else if (this.w < o.w)
            return -1;
        else
            return 0;
    }
}
