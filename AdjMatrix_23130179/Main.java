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

        // DirectedGraph g = new DirectedGraph(4);
        // g.addEdge(0, 1);
        // g.addEdge(1, 2);
        // g.addEdge(2, 3);
        // g.addEdge(3, 0);
        // g.addEdge(0, 2);
        // g.addEdge(2, 0);

        // System.out.println(g.findEulerCircuit());

        // // Example 1: Semi-Eulerian graph (Eulerian path exists but no Eulerian circuit)
        // // Graph: 0-1, 1-2
        // // Degrees: vertex 0: 1, vertex 1: 2, vertex 2: 1 (exactly 2 odd-degree vertices)
        // UndirectedGraph graph1 = new UndirectedGraph(3);
        // graph1.addEdge(0, 1);
        // graph1.addEdge(1, 2);
        // System.out.println("Graph1 is semi-Eulerian: " + graph1.isSemiEulerianGraph());  // Expected: true
        // System.out.println("Graph1 is Eulerian: " + graph1.isEulerianGraph()); // Expected: false

        // // Example 2: Eulerian graph (has an Eulerian circuit)
        // // Graph: 0-1, 1-2, 2-0
        // // Degrees: vertex 0: 2, vertex 1: 2, vertex 2: 2 (all even)
        // UndirectedGraph graph2 = new UndirectedGraph(3);
        // graph2.addEdge(0, 1);
        // graph2.addEdge(1, 2);
        // graph2.addEdge(2, 0);
        // System.out.println("Graph2 is semi-Eulerian: " + graph2.isSemiEulerianGraph());  // Expected: false
        // System.out.println("Graph2 is Eulerian: " + graph2.isEulerianGraph());           // Expected: true
        // System.out.println(graph2.findEulerCircuit());

        Tree tree = new Tree(7); //this
        tree.addEdge(0, 1);
        tree.addEdge(0, 2);
        tree.addEdge(1, 3);
        tree.addEdge(2, 4);
        tree.addEdge(4, 5);
        tree.addEdge(4, 6); //if delete 1 vertex please dont forget edit vertices count
        
        // Compute eccentricity for each node
        // for (int i = 0; i < 5; i++) {
        //     int ecc = tree.Eccentricity(i);
        //     System.out.println("Eccentricity of node " + i + ": " + ecc);
        // }

        System.out.println(tree.center());
    
    
    
    }
}
