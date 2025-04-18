package weightedGraph;

public class Main 
{
    public static void main(String[] args) 
    {
        UndirectedGraph g = new UndirectedGraph(7);

        g.addEdge(0, 2, 3);
        g.addEdge(1, 2, 1);
        g.addEdge(4, 3, 2);
        g.addEdge(3, 0, 1);
        g.addEdge(5, 6, 4);

        g.printGraph();
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
    }
}
