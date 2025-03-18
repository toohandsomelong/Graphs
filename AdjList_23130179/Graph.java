package AdjList_23130179;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
            System.out.println("This graph doesn't have self loop");
            return false;
        }

        System.out.println("self loop list: " + selfLoops);

        return true;
    }
    
    public Set<String> getSelfLoops()
    {
        if (selfLoops.isEmpty()) 
        {
            System.out.println("This graph doesn't have self loop");
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

        for (Map.Entry<String, Set<String>> entry : adjList.entrySet()) {
            String vertex = entry.getKey();
            //list to set for O(1) check contains which is faster than list contains O(n)
            Set<String> values = new HashSet<String>(entry.getValue());

            if (values.size() != totalVertices - 1)
                return false;

            for (String v : allVertices) {
                if (!v.equals(vertex) && !values.contains(v))
                    return false;
            }
        }

        return true;
    }
    
    protected Map<String, Boolean> newVisited(Set<String> keySet)
    {
        Map<String, Boolean> newVisited = new HashMap<>();
        for (String key : keySet) 
        {
            newVisited.put(key, false);
        }
        
        return newVisited;
    }

    protected void BFS(String start, List<String> list, Map<String, Boolean> visited) 
    {
        Queue<String> queue = new LinkedList<>();

        // Mark start vertex as visited and add queue it
        visited.put(start, true);
        queue.add(start);

        while (!queue.isEmpty()) 
        {
            //poll is get first and remove the first one
            String current = queue.poll();
            list.add(current);

            List<String> currList = new ArrayList<>(adjList.get(current));

            for (int i = 0; i < currList.size(); i++) 
            {
                String curr = currList.get(i);
                if (visited.get(curr))
                    continue;

                visited.put(curr, true);
                queue.add(curr);
            }
        }
    }

    private List<String> BFS(String start, Map<String, Boolean> visited)
    {
        List<String> list = new ArrayList<String>();

        BFS(start, list, visited);

        return list;
    }

    public List<String> BFS(String start)
    {
        List<String> list = new ArrayList<String>();

        Map<String, Boolean> visited = newVisited(adjList.keySet());

        BFS(start, list, visited);

        return list;
    }
    
    public boolean isConnected()
    {
        Map<String, Boolean> visited = newVisited(adjList.keySet());

        String firstKey = adjList.keySet().iterator().next();
        BFS(firstKey, visited);

        return !visited.containsValue(false);
    }
    
    public boolean pathConnected(String start, String end)
    {
        Set<String> list = new HashSet<String>();
        Queue<String> queue = new LinkedList<>();
        Map<String, Boolean> visited = newVisited(adjList.keySet());

        // Mark start vertex as visited and add queue it
        visited.put(start, true);
        queue.add(start);

        while (!queue.isEmpty()) 
        {
            //poll is get first and remove the first one
            String current = queue.poll();
            list.add(current);

            List<String> currList = new ArrayList<>(adjList.get(current));

            for (int i = 0; i < currList.size(); i++) 
            {
                String curr = currList.get(i);
                if (visited.get(curr))
                    continue;

                visited.put(curr, true);
                queue.add(curr);
            }

            if (visited.get(end) == true) 
            {
                list.add(end);
                System.out.println(list);
                return true;
            }
        }

        return false;
    }
    
    public List<String> BFS_UnconnectedGraph(String start)
    {
        List<String> list = new ArrayList<String>();

        Map<String, Boolean> visited = newVisited(adjList.keySet());
        BFS(start, list, visited);

        for (String key : visited.keySet()) 
        {
            if(visited.get(key))
                continue;

            BFS_UnconnectedGraph(key, visited, list);
        }

        return list;
    }

    private List<String> BFS_UnconnectedGraph(String start, Map<String, Boolean> visited, List<String> list)
    {
        BFS(start, list, visited);

        for (String key : visited.keySet()) 
        {
            if(visited.get(key))
                continue;

            BFS_UnconnectedGraph(key, visited, list);
        }

        return list;
    }

    public List<String> getConnectedComponent(String v)
    {
        return BFS(v);
    }
}
