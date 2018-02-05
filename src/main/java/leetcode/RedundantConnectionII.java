package leetcode;

/**
 * Question 685
 *
 *
 1) Check whether there is a node having two parents.
 If so, store them as candidates A and B, and set the second edge invalid.  找到存在两个父亲的节点(如果有的话)，将他们极为候选
 2) Perform normal union find.
 If the tree is now valid
   simply return candidate B //如果B成为候选以后，这是一个合法的rooted tree，因此，直接删除B就可以了
 else if candidates not existing
   we find a circle, return current edge;  //如果没有任何一个节点有两个父亲，那么，肯定是存在一个环，因此找到这个环，删除当前边
 else
   remove candidate A instead of B. //如果删除B以后这不是一个rooted tree，并且也找不到一个环，那么就删除A
 */
public class RedundantConnectionII {
    public int[] findRedundantDirectedConnection(int[][] edges) {
        int[] can1 = {-1, -1};
        int[] can2 = {-1, -1};
        int[] parent = new int[edges.length + 1];
        for (int i = 0; i < edges.length; i++) {
            if (parent[edges[i][1]] == 0) {
                parent[edges[i][1]] = edges[i][0];
            } else {
                //can1是发现的第一个边，can2是第二个，can1这条边在edges中肯定在can2前面
                can2 = new int[] {edges[i][0], edges[i][1]};//当前发现的边
                can1 = new int[] {parent[edges[i][1]], edges[i][1]};//edges数组中前面已经出现过的边
                edges[i][1] = 0; //把这条边标记为invalid，实际上将can2标记为invalid
            }
        }
        //情况1：如果没有找到具有两个parent的点，那么肯定格式有环存在的，比如，{{1,2},{2,3},{3,1},{2,4}}，可以移除{1,2},{2,3},{3,1}其中的任意一个，但是我们移除{3,1}
        //如果找到了具有两个parent的点，则有可能有环有可能没有环
        //情况2：如果没有环，其实可以返回任意一个冗余边，但是题目要求是靠后的一个，比如,{{1,2},{1,3},{2,3}}，则可以移除{1,3}或者{2,3}，我们选择移除{2,3}
        //情况3：如果有环，则一定是有一个环内的点，这个点的两个入边一个是环内，一个是环外，这时候只需要移除环内的这个冗余边就行，比如，{{1,2},{2,3},{3,1},{4,2}}，需要移除{1,2}
        for (int i = 0; i < edges.length; i++) {
            parent[i] = i; //初始化标记，初始化Union find的联通分量id
        }
        for (int i = 0; i < edges.length; i++) { //一条边一条边地运行union find方法
            if (edges[i][1] == 0) {
                continue; //这个边是invalid
            }
            int child = edges[i][1], father = edges[i][0]; //取出这条边的孩子和父亲
            if (root(parent, father) == child) { //发现了环
                if (can1[0] == -1) { //如果没有发现具有两个parent的边，如情况1
                    return edges[i]; //把这个边返回，其实，这种情况把环内的任意边返回就行
                }
                return can1;//如果发现了环，注意，这个发现了环的操作是在将edges[i][0]即can2标记为invalid的情况下，这说明remove掉can2是错误的，必须将can1 remove掉
            }
            parent[child] = father; //记录这个边
        }
        return can2;//没有发现环，如情况2，说明can1和can2都是可以的，因此按照题目要求，返回can2
    }

    /**
     * Union Find算法的 Path Compression方法，用来加速Union Find算法查找根节点的速度的
     * http://www.geeksforgeeks.org/union-find-algorithm-set-2-union-by-rank/ 这是讲在union find算法中如何通过Path Compression将find的最坏情况下的时间复杂度从O(n)降低到O(logn)

     * @param parent
     * @param i
     * @return
     */
    int root(int[] parent, int i) {
        while (i != parent[i]) { //只要这个点的parent有记录，就往上追溯，一直追溯到一个新的节点
            parent[i] = parent[parent[i]]; //往上追溯其parent
            i = parent[i];
        }
        return i;
    }


    public static void main(String[] args) {
        int[][] edges = {{1,2},{1,3},{2,3}}; //具有两个parent
        int[][] edges2 = {{1,2},{2,3,},{3,1}};
        int[][] edges3 = {{2,3},{2,4},{3,1},{1,2}};
        new RedundantConnectionII().findRedundantDirectedConnection(edges3);
    }
}
