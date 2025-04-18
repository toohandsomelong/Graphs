package AdjMatrix_23130179;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

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
    public void addEdge(int x, int y) 
    {
        if (!isValidVertex(x) || !isValidVertex(y))
            return;

        adjMatrix[x][y] += 1;

        if (x == y)
            return;

        adjMatrix[y][x] += 1;
    }

    @Override
    public void removeEdge(int x, int y) 
    {
        if (!isValidVertex(x) || !isValidVertex(y))
            return;

        adjMatrix[x][y] = 0;

        if (x == y)
            return;

        adjMatrix[y][x] = 0;
    }

    @Override
    public String getGraphType() 
    {
        if (isHavingArc())
            return "Pseudographs";

        if (isHavingSelfLoop())
            return "Multigraph";

        return "Simple graph";
    }

    @Override
    public int degreeOf(int vertex) 
    {
        if (!isValidVertex(vertex))
            return -1;
        
        int result = 0;

        for (int i : adjMatrix[vertex]) 
        {
            result += i;
        }

        return result;
    }

    @Override
    public boolean isBipartite() 
    {
        //-1 is blank, 0 and 1 are the two colors.
        int[] color = new int[vertices];
        for (int i = 0; i < vertices; i++) 
            color[i] = -1;

        for (int i = 0; i < vertices; i++) 
        {
            if (color[i] != -1) //is colored -> skip
                continue;
            
            if (!BFS_Color(i, color)) 
                return false;
        }
        return true;
    }

    private boolean BFS_Color(int start, int[] color) 
    {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        color[start] = 0; // Start by coloring the first vertex with 0.

        while (!queue.isEmpty()) 
        {
            int u = queue.poll();

            for (int v = 0; v < vertices; v++) 
            {
                // There is no edge between u and v.
                if (adjMatrix[u][v] != 1) 
                    continue;

                // If v has not been colored, assign it the opposite color.
                if (color[v] == -1) 
                {
                    color[v] = 1 - color[u];
                    queue.offer(v);
                }
                
                // If v has the same color as u, the graph is not bipartite.
                else if (color[v] == color[u])
                    return false;
            }
        }
        return true;
    }
    
    public int countConnectedComponent()
    {
        //always have 1
        int result = 1;
        List<Integer> list = new ArrayList<Integer>();

        boolean[] visited = new boolean[adjMatrix.length];

        BFS(0, list, visited);

        for (int i = 0; i < visited.length; i++) 
        {
            if (visited[i])
                continue;

            result++;
            result += countConnectedComponent(i, visited, list);
        }

        return result;
    }
    
    private int countConnectedComponent(int start, boolean[] visited, List<Integer> list)
    {
        int result = 0;
        BFS(start, list, visited);

        for (int i = 0; i < visited.length; i++) {
            if (visited[i])
                continue;

            result++;
            result += countConnectedComponent(i, visited, list);
        }

        return result;
    }
    
    /**
     * To check Eulerian Graph (Euler Circuit):
     * <ul>
     * <li>MUST connected.
     * <li>vertex MUST have an EVEN degree.
     * <ul>
     */
    @Override
    public boolean isEulerianGraph()
    {
        if (adjMatrix.length <= 1 && degreeOf(0) == 0)
            return false;
            
        //must connected
        if (!isConnected())
            return false;

        for (int i = 0; i < adjMatrix.length; i++) 
        {
            //every vertex must have an even degree
            if(degreeOf(i) % 2 != 0)
                return false;
        }
        return true;
    }

    /**
     * To check Semi-Eulerian Graph (Euler Path):
     * <ul>
     * <li>MUST strongly connected.
     * <li>EXACTLY two vertices have an odd degree (and these become the start and end of the path).
     * <ul>
     */
    @Override
    public boolean isSemiEulerianGraph()
    {
        //must connected
        if (!isConnected())
            return false;

        int oddDegCount = 0;
        for (int i = 0; i < adjMatrix.length; i++) {
            if (degreeOf(i) % 2 != 0)
                oddDegCount++;

            if (oddDegCount > 2)
                return false;
        }

        return (oddDegCount != 0);
    }

    @Override
    public List<Integer> findEulerCircuit() 
    {
        if(!isEulerianGraph())
            return null;

        //copy adj matrix
        int[][] tempMatrix = new int[vertices][];
        for (int i = 0; i < vertices; i++)
            tempMatrix[i] = Arrays.copyOf(adjMatrix[i], vertices);
        
        Stack<Integer> stack = new Stack<>();
        List<Integer> subcircuit = new ArrayList<>();
        int start = 0;
        
        stack.push(start); //start = 0

        while (!stack.isEmpty()) {
            int currentVertex = stack.peek();
            int u; //next vertex to visit
            
            //find next available vertex
            for (u = 0; u < vertices; u++) 
                if (tempMatrix[currentVertex][u] > 0) break;
            
            if (u < vertices) 
            {
                stack.push(u);
                tempMatrix[currentVertex][u]--;
                tempMatrix[u][currentVertex]--;
            } 
            else 
                subcircuit.add(stack.pop());
        }
        
        Collections.reverse(subcircuit);
        return subcircuit;
    }

    @Override
    public boolean isTree()
    {
        if(!isConnected())
            return false;

        if(isEulerianGraph())
            return false;

        if(numEdges() != (vertices - 1))
            return false;

        return true;
    }
    
}
