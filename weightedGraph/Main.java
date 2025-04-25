package weightedGraph;

import java.util.List;

public class Main 
{



    public static void main(String[] args) 
    {
        UndirectedGraph g = new UndirectedGraph(7);

        g.addEdge(0, 2, 3);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 3, 2);
        g.addEdge(0, 3, 7); //check if it actually shortest
        g.addEdge(5, 6, 4);

        // g.printGraph();
        // System.out.println(g.pathConnected(0, 3));

        //test 
        // PriorityQueue<Integer> q = new PriorityQueue<>();
        // q.add(3);
        // q.add(1);
        // q.add(2);

        // Integer i = q.poll();
        // System.out.println(i);
        // i = q.poll();
        // System.out.println(i);

        System.out.println(g.minimumSpanningTree_Prim(0));

        int start = 1;
        List<List<Integer>> shortestPaths_Dijkstra = g.shortestPaths_Dijkstra(1);

        int temp = 0;
        for (List<Integer> l : shortestPaths_Dijkstra)
        {
            System.out.println("Path from " + temp + " to " + start + " : " + l);

            temp++;
        }
        System.out.println();
    }
}
