package AdjList_23130179;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class DirectedGraph extends Graph
{
    @Override
    public void addEdge(String k, String v) 
    {
        if (!isValidVertex(k) || !isValidVertex(v))
            return;

        adjList.get(k).add(v);
        
        // System.out.println("added successfully"); //debug
        if (k.equals(v))
            selfLoops.add(v);
    }

    @Override
    public void removeEdge(String k, String v)
    {
        if (!isValidVertex(k) || !isValidVertex(v))
            return;

        adjList.get(k).remove(v);
        
        // System.out.println("removed successfully"); //debug
        if (k.equals(v))
            selfLoops.remove(k);
    }

    @Override
    public String getGraphType()
    {
        if (isHavingArc() || isHavingSelfLoop())
            return "Directed Multigraph";

        return "Simple Directed Graph";
    }
    
    public int inDegreeOf(String vertex)
    {
        int result = 0;
        
        for (Set<String> list : adjList.values()) 
        {
            if (!list.contains(vertex))
                continue;
            
            result++;
        }

        return result;
    }
    
    public int outDegreeOf(String vertex)
    {
        return adjList.get(vertex).size();
    }

    
    @Override
    public int degreeOf(String vertex)
    {
        if (!isValidVertex(vertex))
            return -1;

        return inDegreeOf(vertex) + outDegreeOf(vertex);
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

        while (!queue.isEmpty()) {
            String current = queue.poll();
            int currentColor = color.get(current);
            Set<String> neighbors = getUndirectedNeighbors(current);

            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    // If neighbor hasn't been colored, assign it the opposite color.
                    if (!color.containsKey(neighbor)) {
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
    
    private Set<String> getUndirectedNeighbors(String vertex) 
    {
        Set<String> neighbors = new HashSet<>();
        // Add out vertex
        if (adjList.containsKey(vertex)) 
            neighbors.addAll(adjList.get(vertex));

        // Add in vertex
        for (Map.Entry<String, Set<String>> entry : adjList.entrySet()) 
        {
            String other = entry.getKey();

            if (entry.getValue().contains(vertex))
                neighbors.add(other);
        }
        return neighbors;
    }

}
