package AdjMatrix_23130179;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

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
    public void addEdge(int x, int y) 
    {
        if (!isValidVertex(x) || !isValidVertex(y))
            return;
            
        adjMatrix[x][y] += 1;
    }

    @Override
    public void removeEdge(int x, int y) 
    {
        if (!isValidVertex(x) || !isValidVertex(y))
            return;

        adjMatrix[x][y] = 0;
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
    public String getGraphType() 
    {
        if (isHavingArc() || isHavingSelfLoop())
            return "Directed Multigraph";

        return "Simple Directed Graph";
    }

    @Override
    public int degreeOf(int vertex) 
    {
        return inDegreeOf(vertex) + outDegreeOf(vertex);
    }

    @Override
    public boolean isBipartite() 
    {
        int[] color = new int[vertices];
        // -1 is blank.
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

    /**
     * To check Eulerian Graph (Euler Circuit):
     * <ul>
     * <li>MUST strongly connected.
     * <li>Every vertex MUST have the same number of inDeg and outDeg.
     * <ul>
     */
    @Override
    public boolean isEulerianGraph() 
    {
        //MUST strongly connected
        if (!isConnected())
            return false;

        for (int i = 0; i < adjMatrix.length; i++) 
        {
            //Every vertex MUST have the same number of inDeg and outDeg.
            if(inDegreeOf(i) != outDegreeOf(i))
                return false;
        }
        return true;
    }

    /**
     * To check Semi-Eulerian Graph (Euler Path):
     * <ul>
     * <li>MUST strongly connected.
     * <li>One vertex (the start) must have out‑degree exactly one more than its in‑degree.
     * <li>One vertex (the end) must have in‑degree exactly one more than its out‑degree.
     * <li>All vertices except two must have equal in‑degree and out‑degree.
     * <ul>
     */
    @Override
    public boolean isSemiEulerianGraph() 
    {
        //MUST strongly connected
        if (!isConnected())
            return false;

        int countStart = 0;
        int countEnd = 0;
        for (int i = 0; i < adjMatrix.length; i++) 
        {
            if (inDegreeOf(i) == (outDegreeOf(i) + 1))
            {
                countStart++;
                continue;
            }

            if (outDegreeOf(i) == (inDegreeOf(i) + 1))
            {
                countEnd++;
                continue;
            }

            if (countStart > 1 || countEnd > 1)
                return false;

            if (inDegreeOf(i) != outDegreeOf(i))
                return false;
        }
        
        return (countStart != 1 || countEnd != 1);
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
        
        // Choose a starting vertex with a non-zero out-degree
        int start = -1;
        for (int i = 0; i < vertices; i++) 
        {
            if (outDegreeOf(i) == 0)
                continue;
            start = i;
            break;
        }

        if (start == -1) return new ArrayList<>();  // no edges

        Stack<Integer> stack = new Stack<>();
        List<Integer> subcircuit = new ArrayList<>();
        stack.push(start);

        while (!stack.isEmpty()) 
        {
            int v = stack.peek(); //current vertex
            int u = -1;
            for (int i = 0; i < vertices; i++) 
            {
                if (tempMatrix[v][i] <= 0)
                    continue;
                u = i;
                break;
            }
            if (u != -1) 
            {
                stack.push(u);
                tempMatrix[v][u]--; // Remove directed edge v -> u
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
