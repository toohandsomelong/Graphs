package AdjList_23130179;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class UndirectedGraph extends Graph
{
    @Override
    public void addEdge(String k, String v) 
    {
        if (!isValidVertex(k) || !isValidVertex(v))
            return;

        adjList.get(k).add(v);
        
        // System.out.println("added successfully"); //debug
        if (k.equals(v))
        {
            selfLoops.add(v);
            return;
        }

        adjList.get(v).add(k);
    }

    @Override
    public void removeEdge(String k, String v)
    {
        if (!isValidVertex(k) || !isValidVertex(v))
            return;

        adjList.get(k).remove(v);
        
        // System.out.println("removed successfully"); //debug
        if (k.equals(v))
        {
            selfLoops.remove(k);
            return;
        }

        adjList.get(v).remove(k);
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
    public int degreeOf(String vertex)
    {
        if (!isValidVertex(vertex))
            return -1;

        int result = 0;

        for (String value : adjList.get(vertex)) {
            if (value == vertex) //arc
                result++;

            result++;
        }

        return result;
    }

    @Override
    public boolean isBipartite() 
    {
        HashMap<String, Integer> color = new HashMap<>();

        for (String vertex : adjList.keySet()) {
            if (color.containsKey(vertex))
                continue;
            if (!BFS_Color(vertex, color))
                return false;
        }
        return true;
    }
    
    private boolean BFS_Color(String start, HashMap<String, Integer> color) 
    {
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        color.put(start, 0); // assign initial color 0 to the starting vertex

        while (!queue.isEmpty()) 
        {
            String current = queue.poll();
            int currentColor = color.get(current);
            Set<String> neighbors = adjList.get(current);

            if (neighbors != null) 
            {
                for (String neighbor : neighbors) 
                {
                    // If neighbor hasn't been colored, assign it the opposite color.
                    if (!color.containsKey(neighbor)) 
                    {
                        color.put(neighbor, 1 - currentColor);
                        queue.add(neighbor);
                    } 
                    // If the neighbor already has the same color, the graph is not bipartite.
                    else if (color.get(neighbor) == currentColor) 
                        return false;
                }
            }
        }
        return true;
    }

    
    public int countConnectedComponent()
    {
        //always have 1
        int result = 1;
        List<String> list = new ArrayList<String>();

        Map<String, Boolean> visited = newVisited(adjList.keySet());

        String firstKey = adjList.keySet().iterator().next();
        BFS(firstKey, list, visited);

        for (String key : visited.keySet()) 
        {
            if(visited.get(key))
                continue;

            result++;
            result += countConnectedComponent(key, visited, list);
        }

        return result;
    }
    
    private int countConnectedComponent(String start, Map<String, Boolean> visited, List<String> list)
    {
        int result = 0;
        BFS(start, list, visited);

        for (String key : visited.keySet()) 
        {
            if(visited.get(key))
                continue;

            result++;
            result += countConnectedComponent(key, visited, list);
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
        if (adjList.keySet().size() <= 1 && degreeOf(adjList.keySet().iterator().next()) == 0)
            return false;
        //must connected
        if (!isConnected())
            return false;

        for (String vertex : adjList.keySet()) 
        {
            //every vertex must have an even degree
            if(degreeOf(vertex) % 2 != 0)
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

        for (String vertex : adjList.keySet()) 
        {
            if(degreeOf(vertex) % 2 != 0)
                oddDegCount++;

            if (oddDegCount > 2)
                return false;
        }
        
        return !(oddDegCount == 0);
    }
}
