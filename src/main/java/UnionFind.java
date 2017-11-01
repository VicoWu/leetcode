


import java.io.File;
import java.util.Scanner;

/**
 * 联通分量查找,这种查找算法可以用来在有向图或者无向图中确认是否有环
 *
 * http://www.cnblogs.com/SeaSky0606/p/4752941.html
 * http://www.geeksforgeeks.org/?p=26350 //这是讲Union find 算法的具体实现的
 * http://www.geeksforgeeks.org/union-find-algorithm-set-2-union-by-rank/ 这是讲在union find算法中如何通过Path Compression将find的最坏情况下的时间复杂度从O(n)降低到O(logn)
 *
 * ，其中一个变量count表示实时的连通分量数，另一个变量可以用来存储具体每一个点所属的连通分量。因为不需要存储复杂的信息。
 * 这里我们选常用的数组 id[N] 存储即可。然后，我们需要2个函数find(int x)和union(int p,int q)。前者返回点“x”所属于的连通分量，
 * 后者将p,q两点进行连接。注意，所谓的连接，其实可以简单的将p的连通分量值赋予q或者将q的连通分量值赋予p，即：

 id[p]=q 或者id[q]=p。



 */

// Java Program for union-find algorithm to detect cycle in a graph
import java.util.*;
import java.lang.*;
import java.io.*;

class Graph
{
    int V, E;    // V-> no. of vertices & E->no.of edges
    Edge edge[]; // /collection of all edges

    class Edge
    {
        int src, dest;
    };

    // Creates a graph with V vertices and E edges
    Graph(int v,int e)
    {
        V = v;
        E = e;
        edge = new Edge[E];
        for (int i=0; i<e; ++i)
            edge[i] = new Edge();
    }

    // A utility function to find the subset of an element i
    int find(int parent[], int i)
    {
        if (parent[i] == -1)
            return i;
        return find(parent, parent[i]); //继续递归寻找
    }


    // A utility function to find set of an element i
// (uses path compression technique)
    int findByCompressedPath(int parent[], int i)
    {
        // find root and make root as parent of i (path compression)
        if (parent[i] != i)
            parent[i] = findByCompressedPath(parent, parent[i]);

        return i;
    }


    // A utility function to do union of two subsets
    void Union(int parent[], int x, int y)
    {
        int xset = find(parent, x);
        int yset = find(parent, y);
        parent[xset] = yset;
    }


    // The main function to check whether a given graph
    // contains cycle or not
    int isCycle( Graph graph)
    {
        // Allocate memory for creating V subsets
        int parent[] = new int[graph.V];

        // Initialize all subsets as single element sets
        for (int i=0; i<graph.V; ++i)
            parent[i]=-1;

        // Iterate through all edges of graph, find subset of both
        // vertices of every edge, if both subsets are same, then
        // there is cycle in graph.
        for (int i = 0; i < graph.E; ++i)
        {
            int x = graph.find(parent, graph.edge[i].src); //找到这条边的左节点的联通分量
            int y = graph.find(parent, graph.edge[i].dest); //找到这条边的右节点的联通分量

            if (x == y)//如果是
                return 1;

            graph.Union(parent, x, y);
        }
        return 0;
    }





    // Driver Method
    public static void main (String[] args)
    {
        /* Let us create following graph
         0
        |  \
        |    \
        1-----2 */
        int V = 3, E = 3;
        Graph graph = new Graph(V, E);

        // add edge 0-1
        graph.edge[0].src = 0;
        graph.edge[0].dest = 1;

        // add edge 1-2
        graph.edge[1].src = 1;
        graph.edge[1].dest = 2;

        // add edge 0-2
        graph.edge[2].src = 0;
        graph.edge[2].dest = 2;

        if (graph.isCycle(graph)==1)
            System.out.println( "graph contains cycle" );
        else
            System.out.println( "graph doesn't contain cycle" );
    }
}
