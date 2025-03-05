package AdjList_23130179;

import java.util.List;

public class DirectedGraph extends Graph
{
    @Override
    public void addEdge(String k, String v) 
    {
        if (!isValidKey(k) || !isValidKey(v))
            return;

        adjList.get(k).add(v);
        
        // System.out.println("added successfully"); //debug
        if (k.equals(v))
            arcs.add(v);
    }

    @Override
    public void removeEdge(String k, String v)
    {
        if (!isValidKey(k) || !isValidKey(v))
            return;

        adjList.get(k).remove(adjList.get(k).indexOf(v));
        
        // System.out.println("removed successfully"); //debug
        if (k.equals(v))
            arcs.remove(arcs.indexOf(k));
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
        
        for (List<String> list : adjList.values()) 
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
        return inDegreeOf(vertex) + outDegreeOf(vertex);
    }
}
