package AdjMatrix_23130179;

public class Main 
{
    public static void main(String[] args) 
    {
        // UndirectedGraph g = new UndirectedGraph(5);

        // g.addEdge(0, 1);
        // g.addEdge(0, 2);
        // g.addEdge(1, 3);
        // g.addEdge(2, 3);

        DirectedGraph g = new DirectedGraph(4);

        g.addEdge(0, 1);
        g.addEdge(0, 3);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);

        g.printGraph();
        System.out.println(g.getConnectedComponent(0));
    }
}
