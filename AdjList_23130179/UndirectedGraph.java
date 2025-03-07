package AdjList_23130179;

import java.util.HashMap;
import java.util.LinkedList;
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
        // Map to store color assignments for each vertex (0 or 1)
        Map<String, Integer> colors = new HashMap<>();

        // Check every vertex (this also handles disconnected graphs)
        for (String vertex : adjList.keySet()) 
        {
            if (!colors.containsKey(vertex)) 
            {
                if (!bfsCheck(vertex, colors))
                    return false;
            }
        }
        return true;
    }

    private boolean bfsCheck(String start, Map<String, Integer> colors) 
    {
        Queue<String> queue = new LinkedList<>();
        colors.put(start, 0);  // Assign an initial color (0) to the starting vertex.
        queue.offer(start);

        while (!queue.isEmpty()) 
        {
            // Dequeue a vertex v from the queue
            String current = queue.poll();
            int currentColor = colors.get(current);
            int neighborColor = 1 - currentColor;  // The opposite color

            // Process all neighbors of the current vertex.
            Set<String> neighbors = adjList.get(current);
            if (neighbors != null) 
            {
                for (String neighbor : neighbors) 
                {
                    if (!colors.containsKey(neighbor)) 
                    {
                        // Color the neighbor with the opposite color and enqueue it.
                        colors.put(neighbor, neighborColor);
                        queue.offer(neighbor);
                    } 
                    else if (colors.get(neighbor) == currentColor) 
                    {
                        // A neighbor has the same color as the current vertex, so the graph is not bipartite.
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
