package AdjMatrix_23130179;

public class DirectedGraph extends Graph
{
    public DirectedGraph(int vertices) 
    {
        super(vertices);
    }
    
    @Override
    public void addEdge(int x, int y) 
    {
        adjMatrix[x][y] += 1;
    }

    public int degreeIn(int v)
    {
        int result = 0;

        for (int i = 0; i < vertices; i++)
        {
            result += adjMatrix[v][i];
        }

        return result;
    }

    public int degreeOut(int v)
    {
        int result = 0;

        for (int i = 0; i < vertices; i++)
        {
            result += adjMatrix[i][v];
        }

        return result;
    }

    @Override
    public void removeEdge(int x, int y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeEdge'");
    }
}
