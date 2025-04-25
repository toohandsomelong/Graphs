package weightedGraph;

public class DirectedGraph extends Graph 
{
    public DirectedGraph(DirectedGraph graph)
    {
        super(graph.vertices);
        adjMatrix = graph.adjMatrix.clone();
    }
    
    public DirectedGraph(int vertices) {
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
    }

    @Override
    public void removeEdge(int x, int y) 
    {
        if (!isValidVertex(x) || !isValidVertex(y))
        {
            System.out.println("Not valid vertex: " + x +", " + y);
            return;
        }

        adjMatrix[x][y] = Double.POSITIVE_INFINITY;
    }

    public int inDegreeOf(int v) 
    {
        int result = 0;

        for (int i = 0; i < vertices; i++)
            result += adjMatrix[v][i];

        return result;
    }

    public int outDegreeOf(int v) 
    {
        int result = 0;

        for (int i = 0; i < vertices; i++)
            result += adjMatrix[i][v];

        return result;
    }
    
    @Override
    public void printEdges()
    {
        for (int i = 0; i < adjMatrix.length; i++)
        {
            for (int j = 0; j < adjMatrix.length; j++)
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
            for (int j = 0; j < adjMatrix.length; j++)
            {
                if (adjMatrix[i][j] == Double.POSITIVE_INFINITY)
                    continue;

                sum += adjMatrix[i][j];
            }
        }
        return sum;
    }
}
