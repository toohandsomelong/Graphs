package AdjList_23130179;

public class UndirectedGraph extends Graph
{
    @Override
    public void addEdge(String k, String v) 
    {
        if (!isValidKey(k) || !isValidKey(v))
            return;

        adjList.get(k).add(v);
        
        // System.out.println("added successfully"); //debug
        if (k.equals(v))
        {
            arcs.add(v);
            return;
        }

        adjList.get(v).add(k);
    }

    @Override
    public void removeEdge(String k, String v)
    {
        if (!isValidKey(k) || !isValidKey(v))
            return;

        adjList.get(k).remove(adjList.get(k).indexOf(v));
        
        // System.out.println("removed successfully"); //debug
        if (k.equals(v))
        {
            arcs.remove(arcs.indexOf(k));
            return;
        }

        adjList.get(v).remove(adjList.get(v).indexOf(v));
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
        int result = 0;
        
        for (String value : adjList.get(vertex)) 
        {
            if (value == vertex) //arc
                result++;

            result++;
        }

        return result;
    }
}
