package leetcode;

/**
 * Question 785
 * 剑指 Offer II 106. 二分图
 */
public class Bipartite {
    /**
     * 如果这个图只有一个子图，那么很简单，就是每个节点有两个状态，从任意节点出发，给这个节点设置一个初始状态，那么根据深度优先遍历，下一个节点就必须是一个相反状态
     * 如果下一个节点已经有状态但是不是预期的相反状态，那么就不是二分图。
     * 如果这个图有两个或者多个子图，那么只需要保证每个子图都是二分图就行。
     * @param graph
     * @return
     */
    public boolean isBipartite(int[][] graph) {
        int[] visit = new int[graph.length];
        for (int i = 0; i < visit.length; i++) {
            visit[i] = -1;
        }
        for (int i = 0; i < graph.length; i++) {
            if (visit[i] == -1 && !isBipartite(graph, visit, 0, i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isBipartite(int[][] graph, int[] visit, int catagory, int i) {
        int expect = (catagory + 1) % 2;
        if (visit[i] != -1) {
            return visit[i] == expect;
        }
        visit[i] = expect;
        for (int j = 0; j < graph[i].length; j++) {
            boolean res = isBipartite(graph, visit, expect, graph[i][j]);
            if (res == false) {
                return res;
            }
        }
        return true;
    }
}
