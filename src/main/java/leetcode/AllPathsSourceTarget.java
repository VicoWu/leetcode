package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 剑指 Offer II 110. 所有路径
 * Question 797
 * 这一题需要跟329. 矩阵中的最长递增路径 区别开
 * 在329中，我们可以对图进行拓扑排序，经过拓扑排序，整个图排序以后的层数就是我们要的结果
 * 但是该题，求的是从某个原点到终点的所有路径，就是深度优先搜索或者广度优先搜索。如果我们也采用拓扑排序，那么首先要找到起点所在的拓扑层，然后在排序过程中保存路径。但是，跟起点在同一个
 * 拓扑层的可能不止一个节点，后续的拓扑层的起始点我们也不确定是否来自起点0，所以，拓扑排序不适合这一题。
 *
 */
public class AllPathsSourceTarget {
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<Integer> cur = new ArrayList<Integer>();
        List<List<Integer>>  res = new ArrayList();
        allPathsSourceTarget(graph, 0, cur, res);
        return res;
    }

    public void allPathsSourceTarget(int[][] graph, int start, List<Integer> cur, List<List<Integer>>  res){
        cur.add(start);
        if(start == graph.length - 1){
            res.add(new ArrayList(cur));
            cur.remove(cur.size() - 1);
            return;
        }
        for(int i = 0;i<graph[start].length;i++){
            allPathsSourceTarget(graph, graph[start][i], cur, res);
        }
        cur.remove(cur.size() - 1);
        return;
    }
}
