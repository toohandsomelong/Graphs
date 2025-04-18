package AdjMatrix_23130179;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Tree extends UndirectedGraph
{

	public Tree(int vertices)
	{
		super(vertices);
	}

    @Override
    public void addEdge(int x, int y) 
    {
        if (!isValidVertex(x) || !isValidVertex(y))
            return;

        if (pathConnected(x, y))
        {
            System.out.println(x + " and " + y + " having connected path");
            return;
        }

        adjMatrix[x][y] += 1;

        if (x == y)
            return;

        adjMatrix[y][x] += 1;
    }

    public int Eccentricity(int v)
    {
        boolean[] visited = new boolean[adjMatrix.length];
        Queue<Integer> queue = new LinkedList<>();
        int n = adjMatrix.length;
        int[] levels = new int[n];

        // Mark start vertex as visited and add queue it
        visited[v] = true;
        queue.add(v);
        levels[v] = 0;

        while (!queue.isEmpty())
        {
            //poll is get first and remove the first one
            int current = queue.poll();

            for (int i = 0; i < n; i++)
            {
                //0 is not connect so skip
                if (adjMatrix[current][i] == 0)
                    continue;

                if (visited[i])
                    continue;

                visited[i] = true;
                queue.add(i);
                levels[i] = levels[current] + 1;
            }
        }
        
        int max = 0;
        for (int d : levels)
        {
            max = Math.max(max, d);
        }

        return max;
    }

    public List<Integer> center()
    {
        int n = adjMatrix.length;
        int remain = n;
        int[] degree = new int[n];

        List<Integer> centers = new ArrayList<>();
        List<Integer> leaves = new ArrayList<>();

        UndirectedGraph tempAdj = new UndirectedGraph(this);

        for (int i = 0; i < adjMatrix.length; i++)
        {
            int deg = degreeOf(i);

            degree[i] = deg;

            if (deg == 1)
                leaves.add(i);
        }

        while (remain > 2)
        {
            List<Integer> newLeaves = new ArrayList<>();

            for (int leaf : leaves)
            {
                degree[leaf] = 0;
                remain--;

                for (int i = 0; i < n; i++)
                {
                    if (tempAdj.adjMatrix[leaf][i] != 1)
                        continue;

                    //remove everything related to leaf if it is connected 
                    tempAdj.adjMatrix[leaf][i] = 0;
                    tempAdj.adjMatrix[i][leaf] = 0;
                    degree[i]--;

                    if (degree[i] == 1)
                        newLeaves.add(i);
                }
            }
            leaves = newLeaves;
        }

        for (int i = 0; i < n; i++)
            if (degree[i] > 0)
                centers.add(i);

        return centers;
    }
    
    public int treeRadius()
    {
        int minEcc = -1;
        int[] arr = new int[vertices];

        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = Eccentricity(i);
        }

        for (int n : arr)
        {
            minEcc = Math.min(minEcc, n);
        }

        return minEcc;
    }
    //is complete tree
}
