package AdjList_23130179;

public class Main 
{
    public static void main(String[] args) 
    {
        DirectedGraph g1 = new DirectedGraph();

        g1.addVertex("a");
        g1.addVertex("b");
        g1.addVertex("c");
        g1.addVertex("d");

        g1.addEdge("a", "b");
        g1.addEdge("b", "d");
        g1.addEdge("c", "d");
        g1.addEdge("d", "d");
        g1.addEdge("d", "c");

        g1.printGraph();

        System.out.println(g1.inDegreeOf("d"));
        System.out.println(g1.outDegreeOf("d"));
    }
}
