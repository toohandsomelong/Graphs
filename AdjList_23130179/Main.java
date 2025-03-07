package AdjList_23130179;

public class Main 
{
    public static void main(String[] args) 
    {
        UndirectedGraph g1 = new UndirectedGraph();

        g1.addVertex("a");
        g1.addVertex("b");
        g1.addVertex("c");

        g1.addEdge("a", "b");
        g1.addEdge("a", "c");
        g1.addEdge("b", "c");

        g1.printGraph();
        System.out.println(g1.isBipartite());
    }
}
