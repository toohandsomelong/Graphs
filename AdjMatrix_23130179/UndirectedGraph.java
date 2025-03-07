package AdjMatrix_23130179;

public class UndirectedGraph extends Graph
{

    public UndirectedGraph(int vertices) 
    {
        super(vertices);
    }
    
    @Override
    public void addEdge(int x, int y) 
    {
        adjMatrix[x][y] += 1;

        if (x == y)
            return;

        adjMatrix[y][x] += 1;
    }

    @Override
    public void removeEdge(int x, int y) 
    {
        adjMatrix[x][y] = 0;

        if (x == y)
            return;

        adjMatrix[y][x] = 0;
    }

    public int degree(int v)
    {
        int result = 0;

        for (int i : adjMatrix[v]) 
        {
            result += i;
        }

        result *= 2;
        return result;
    }
}
