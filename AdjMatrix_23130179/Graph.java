package AdjMatrix_23130179;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public abstract class Graph 
{
    protected int vertices = 0;
    protected int[][] adjMatrix = new int[0][0];

    public Graph(int v)
    {
        vertices = v;
        adjMatrix = new int[v][v];
    }

    protected boolean isValidVertex(int vertex)
    {
        if(vertex > adjMatrix.length - 1 || vertex < 0)
            return false;
        
        return true;
    }

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
        if(!isValidVertex(v))
        {
            System.out.println(v + "is not valid");
            return;
        }

        int[][] newAdjMatrix = new int[vertices - 1][vertices - 1];

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
        for (int[] i : adjMatrix) 
        {
            for (int j : i)
                System.out.print(j + " ");
            
            System.out.println();
        }
    }

    public int numEdges() 
    {
        int result = 0;

        for (int i = 0; i < adjMatrix.length; i++)
        {
            result += degreeOf(i);
        }

        result /= 2;

        return result;
    }
    
    public boolean isCompleteGraph() 
    {
        if (isHavingSelfLoop())
            return false;

        for (int i = 0; i < adjMatrix.length; i++)
        {
            //every deg must = (adj length - 1)
            if(degreeOf(i) != adjMatrix.length - 1)
                return false;
            
            for (int j = 0; j < adjMatrix.length; j++) 
            {
                //already checked self loop so skip
                if(i == j)
                    continue;
                
                //must = 1
                if(adjMatrix[i][j] != 1)
                    return false;
            }
        }

        return true;
    }
        
    protected boolean isHavingSelfLoop() 
    {
        for (int i = 0; i < adjMatrix.length; i++) 
        {
            if (adjMatrix[i][i] != 0)
                return true;
        }

        return false;
    }
    
    public Set<Integer> getSelfLoops()
    {
        Set<Integer> list = new HashSet<Integer>();

        for (int i = 0; i < adjMatrix.length; i++) {
            if (adjMatrix[i][i] != 0)
                list.add(i);
        }

        return list;
    }
    
    public boolean isHavingArc()
    {
        if (!isHavingSelfLoop()) 
        {
            System.out.println("This graph doesn't have self loop");
            return false;
        }

        System.out.println("self loop list: " + getSelfLoops());

        return true;
    }

    protected void BFS(int start, List<Integer> list, boolean[] visited) 
    {
        Queue<Integer> queue = new LinkedList<>();

        // Mark start vertex as visited and add queue it
        visited[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            //poll is get first and remove the first one
            int current = queue.poll();
            list.add(current);

            for (int i = 0; i < adjMatrix[current].length; i++) 
            {
                //0 is not connect so skip
                if (adjMatrix[current][i] == 0)
                    continue;

                if (visited[i])
                    continue;

                visited[i] = true;
                queue.add(i);
            }
        }
    }

    private List<Integer> BFS(int start, boolean[] visited)
    {
        List<Integer> list = new ArrayList<Integer>();

        BFS(start, list, visited);

        return list;
    }

    public List<Integer> BFS(int start)
    {
        List<Integer> list = new ArrayList<Integer>();

        boolean[] visited = new boolean[adjMatrix.length];

        BFS(start, list, visited);

        return list;
    }
    
    public boolean isConnected()
    {
        boolean[] visited = new boolean[adjMatrix.length];

        BFS(0, visited);

        for (boolean b : visited) 
        {
            if (!b)
                return false;
        }

        return true;
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
                System.out.println(list);
                return true;
            }
        }

        return false;
    }
    
    public List<Integer> BFS_UnconnectedGraph(int start)
    {
        List<Integer> list = new ArrayList<Integer>();

        boolean[] visited = new boolean[adjMatrix.length];

        BFS(start, list, visited);

        for (int i = 0; i < visited.length; i++) 
        {
            if(visited[i])
                continue;
            
            BFS_UnconnectedGraph(i, visited, list);
        }

        return list;
    }

    private List<Integer> BFS_UnconnectedGraph(int start, boolean[] visited, List<Integer> list)
    {
        BFS(start, list, visited);

        for (int i = 0; i < visited.length; i++) {
            if (visited[i])
                continue;

            BFS_UnconnectedGraph(i, visited, list);
        }

        return list;
    }

    public List<Integer> getConnectedComponent(int v)
    {
        return BFS(v);
    }

    public abstract void addEdge(int x, int y);

    public abstract void removeEdge(int x, int y);

    public abstract String getGraphType();

    public abstract int degreeOf(int vertex);

    public abstract boolean isBipartite();

    public abstract boolean isEulerianGraph();

    public abstract boolean isSemiEulerianGraph();

    public abstract List<Integer> findEulerCircuit();

    public abstract boolean isTree();
}
