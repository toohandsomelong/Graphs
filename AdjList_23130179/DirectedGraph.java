package AdjList_23130179;

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
    public boolean isBipartite() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isBipartite'");
    }
}
