package weightedGraph;

import java.util.ArrayList;
import java.util.List;

public class UndirectedGraph extends Graph
{
    public UndirectedGraph(UndirectedGraph graph)
    {
        super(graph.vertices);
        adjMatrix = graph.adjMatrix.clone();
    }

    public UndirectedGraph(int vertices) 
    {
        super(vertices);
    }
    
    @Override
    public void addEdge(int x, int y, double w) 
    {
        if (!isValidVertex(x) || !isValidVertex(y))
        {
            System.out.println("Not valid vertex: " + x +", " + y);
            return;
        }

        // if (pathConnected(x, y))
        //     return;
            
        adjMatrix[x][y] = w;
        adjMatrix[y][x] = w;
    }

    @Override
    public void removeEdge(int x, int y) 
    {
        if (!isValidVertex(x) || !isValidVertex(y))
            return;

        adjMatrix[x][y] = Double.POSITIVE_INFINITY;
        adjMatrix[y][x] = Double.POSITIVE_INFINITY;
    }

    @Override
    public void printEdges()
    {
        for (int i = 0; i < adjMatrix.length; i++)
        {
            for (int j = i; j < adjMatrix.length; j++)
            {
                if (adjMatrix[i][j] != Double.POSITIVE_INFINITY)
                    System.out.println(i + " " + j + ": " + adjMatrix[i][j]);
            }
        }
    }

    @Override
    public double weight()
    {
        double sum = 0;
        for (int i = 0; i < adjMatrix.length; i++)
        {
            for (int j = i; j < adjMatrix.length; j++)
            {
                if (adjMatrix[i][j] == Double.POSITIVE_INFINITY)
                    continue;

                sum += adjMatrix[i][j];
            }
        }
        return sum;
    }

    public List<Integer> minimumSpanningTree_Prim(int start)
    {
        //from start set weight that connected to start - update neighbors
        //then set start = lowest weight and set visisted - find lowest weight
        //keep doing that until all is visisted - set visisted

        int length = adjMatrix.length;
        int[] prevs = new int[length];

        Double[] weights = new Double[length];
        boolean[] visisted = new boolean[length];

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < length; i++)
        {
            weights[i] = Double.POSITIVE_INFINITY;
            prevs[i] = -1;
        }

        weights[start] = 0.;
        int minV = start;

        while (minV >= 0)
        {
            //set visisted
            visisted[minV] = true;
            list.add(minV);

            //set find next vertices
            for (int j = 0; j < adjMatrix.length; j++)
            {
                if (visisted[j])
                    continue;
                if (adjMatrix[minV][j] == Double.POSITIVE_INFINITY)
                    continue;

                weights[j] = adjMatrix[minV][j];
                prevs[j] = minV;
            }
            minV = getMinWeight(length, weights, visisted);
        }
        return list;
    }
    
    
}
