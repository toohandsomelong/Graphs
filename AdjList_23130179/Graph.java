package AdjList_23130179;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Graph 
{
    protected int vertices = 0;
    protected HashMap<String, List<String>> adjList = new HashMap<String, List<String>>();
    protected List<String> arcs = new ArrayList<String>();

    protected boolean isValidKey(String k)
    {
        if (!adjList.containsKey(k))
            System.out.println("Vertex: " + k + "not existing");
        // else
        //     System.out.println("Vertex is valid");
        
        return adjList.containsKey(k);
    }
    
    public abstract void addEdge(String k, String v);

    public abstract void removeEdge(String k, String v);

    public abstract String getGraphType();

    public abstract int degreeOf(String vertex);

    public void addVertex(String v)
    {
        adjList.put(v, new ArrayList<String>());
    }

    public void removeVertex(String v)
    {
        adjList.remove(v);
    }

    public void printGraph() 
    {
        adjList.forEach((k, v) -> System.out.println(k + "->" + v));
    }
    
    public boolean isHavingArc()
    {
        if (arcs.isEmpty()) 
        {
            System.out.println("This graph doesn't have ring");
            return false;
        }

        System.out.println("Ring list: " + arcs);

        return true;
    }
    
    public List<String> getRingList()
    {
        if (arcs.isEmpty()) 
        {
            System.out.println("This graph doesn't have ring");
            return null;
        }

        return arcs;
    }
    
    public boolean isHavingSelfLoop()
    {
        for (List<String> value : adjList.values())
            if (hasDuplicates(value))
                return false;
        
        return true;
    }

    private boolean hasDuplicates(List<String> list) 
    {
        Set<String> set = new HashSet<>();
        for (String str : list)
            if (!set.add(str)) 
                return true;

        return false;
    }
}
