package AdjList_23130179;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Graph 
{
    protected int vertices = 0;
    protected HashMap<String, Set<String>> adjList = new HashMap<String, Set<String>>();
    protected Set<String> selfLoops = new HashSet<String>();

    protected boolean isValidVertex(String vertex)
    {
        if (!adjList.containsKey(vertex))
        {
            System.out.println("Vertex: " + vertex + "not existing");
            return false;
        }
        // else
        //     System.out.println("Vertex is valid");
        
        return true;
    }
    
    public abstract void addEdge(String k, String v);

    public abstract void removeEdge(String k, String v);

    public abstract String getGraphType();

    public abstract int degreeOf(String vertex);

    public abstract boolean isBipartite();

    public void addVertex(String v)
    {
        adjList.put(v, new HashSet<String>());
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
        if (selfLoops.isEmpty()) 
        {
            System.out.println("This graph doesn't have ring");
            return false;
        }

        System.out.println("Ring list: " + selfLoops);

        return true;
    }
    
    public Set<String> getRingList()
    {
        if (selfLoops.isEmpty()) 
        {
            System.out.println("This graph doesn't have ring");
            return null;
        }

        return selfLoops;
    }
    
    public boolean isHavingSelfLoop()
    {
        for (Set<String> values : adjList.values())
            if (hasDuplicates(values))
                return true;
        
        return false;
    }

    private boolean hasDuplicates(Set<String> list) 
    {
        Set<String> set = new HashSet<>();
        for (String str : list)
        {
            if (!set.add(str))
                return true;
            // System.out.println(list);
            // System.out.println(str);
        }

        return false;
    }

    public int numEdges() 
    {
        int result = 0;

        for (String vertex : adjList.keySet()) {
            result += degreeOf(vertex);
        }

        result /= 2;

        return result;
    }
    
    public boolean isCompleteGraph() 
    {
        if (isHavingSelfLoop())
            return false;
        
        Set<String> allVertices = new HashSet<>(adjList.keySet());
        int totalVertices = allVertices.size();

        for (Map.Entry<String, Set<String>> entry : adjList.entrySet())
        {
            String vertex = entry.getKey();
            //list to set for O(1) check contains which is faster than list contains O(n)
            Set<String> values = new HashSet<String>(entry.getValue());

            if(values.size() != totalVertices - 1)
                return false;

            for (String v : allVertices)
            {
                if (!v.equals(vertex) && !values.contains(v)) 
                    return false;
            }
        }

        return true;
    }
}
