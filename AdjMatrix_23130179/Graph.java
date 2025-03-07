package AdjMatrix_23130179;

public abstract class Graph 
{
    protected int vertices = 0;
    protected int[][] adjMatrix = new int[0][0];

    public Graph(int v)
    {
        vertices = v;
        adjMatrix = new int[v][v];
    }

    public abstract void addEdge(int x, int y);

    public abstract void removeEdge(int x, int y);

    public void addVertex(int v)
    {
        int[][] newAdjMatrix = new int[vertices + v][vertices + v];

        for (int i = 0; i < vertices; i++)
            for (int j = 0; j < vertices; j++)
                newAdjMatrix[i][j] = adjMatrix[i][j];

        vertices += v;
        adjMatrix = newAdjMatrix;
    }

    public void removeVertex(int v)
    {
        int[][] newAdjMatrix = new int[vertices - 1][vertices - 1];

        for (int i = 0; i < vertices; i++)
            for (int j = 0; j < vertices; j++)
            {
                if(i == v || j == v) //not done yet
                    return;
                
                newAdjMatrix[i][j] = adjMatrix[i][j];
            }

        vertices -= 1;
        adjMatrix = newAdjMatrix;

    }

    public void printGraph() 
    {
        for (int[] i : adjMatrix) 
        {
            for (int j : i)
                System.out.print(j + " ");
            
            System.out.println();
        }
    }
}
