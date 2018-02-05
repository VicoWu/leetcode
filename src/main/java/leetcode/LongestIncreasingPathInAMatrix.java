package leetcode;

/**
 * Created by wuchang at 12/18/17
 * Question 329
 * 我自己实现的基于DP的计算方式
 * # 我自己实现的动态规划方法

 `longestIncreasingPath（）`这是我自己实现的通过**动态规划**的方式的实现.

 元素matrix[i][j]的最长路径是由它上、下、左、右四个位置的最长路径以及它和它上、下、左、右四个位置的大小关系决定的

 需要注意以下几点：

 1. 保存中间结果，避免重复计算：动态规划方式最好能够保存中间结果，避免重复计算。这一题的重复计算率非常高，因此如果保存中间结果可以大大提高效率。

 2. 最小值：矩阵中的任何一个数字，它的最短递增路径至少是1。 动态规划过程中，如果遇到越界的位置，这些位置设置为0

 3. 避免陷入无限递归：我刚开始写的代码是这样的：

 ```
 if(x>0 )  max  =  ( (depth =  (findByDP(cache,matrix,x-1,y) )) >=  max) ? depth+1: max ;//上

 ```

 抛出栈溢出异常，这是因为形成了环形依赖，比如：计算matrix\[1\]\[1\] 需要依赖matrix\[0\]\[1\],然后matrix\[0\][1\]又依赖matrix\[1\]\[1\]，即形成环形依赖。因此必须加上限定条件：

 ```
 if(x>0 && matrix[x][y] < matrix[x-1][y])  max  =  ( (depth =  (findByDP(cache,matrix,x-1,y) )) >=  max) ? depth+1: max ;//上
 ```

 即，如果周围的某个元素不大于自己，就没必要在那个元素上进行递归调用了

 # 深度优先遍历方法

 这是别人的深度优先遍历方法 https://discuss.leetcode.com/topic/34835/15ms-concise-java-solution，这里不再赘述。

 ```
 public static final int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

 public int longestIncreasingPath(int[][] matrix) {
 if(matrix.length == 0) return 0;
 int m = matrix.length, n = matrix[0].length;
 int[][] cache = new int[m][n];
 int max = 1;
 for(int i = 0; i < m; i++) {
 for(int j = 0; j < n; j++) {
 int len = dfs(matrix, i, j, m, n, cache);
 max = Math.max(max, len);
 }
 }
 return max;
 }

 public int dfs(int[][] matrix, int i, int j, int m, int n, int[][] cache) {
 if(cache[i][j] != 0) return cache[i][j];
 int max = 1;
 for(int[] dir: dirs) {
 int x = i + dir[0], y = j + dir[1];
 if(x < 0 || x >= m || y < 0 || y >= n || matrix[x][y] <= matrix[i][j]) continue;
 int len = 1 + dfs(matrix, x, y, m, n, cache);
 max = Math.max(max, len);
 }
 cache[i][j] = max;
 return max;
 }
 ```


 */

public class LongestIncreasingPathInAMatrix {

    /**
     * 这是我自己实现的通过**动态规划**的方式的实现
     * 元素matrix[i][j]的最长路径是由它上、下、左、右四个位置的最长路径以及它和它上、下、左、右四个位置的大小关系决定的
     * @param matrix
     * @return
     */
    public int longestIncreasingPath(int[][] matrix) {
        if(matrix.length==0) return 0;
        int m=matrix.length;int n=matrix[0].length;
        Integer[][] cache = new Integer[m][n];
        int result = 0;int tmp;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if((tmp = findByDP(cache,matrix,i,j)) > result)
                    result = tmp;

            }
        }
        return result;
    }

    private Integer findByDP(Integer[][] cache,int[][] matrix ,int x,int y){
        if(cache[x][y]!=null)
            return cache[x][y]; //重复计算的概率很大，因此重复利用计算结果，避免重复计算
        if(x<0 || x > cache.length || y<0 || y>cache[0].length-1)
            return 0;
        int max = 1; int depth; //任意一个节点，最长路径为1，因为，至少包含它自己
        //通过动态规划的方式，分别获取四个方向的最长递增路径 注意，以上方为例，只有当 matrix[x][y] < matrix[x-1][y]我们才需要遍历，
        // 如果不这样可能会栈溢出，比如，计算matrix[1][1] 需要依赖matrix[0][1],然后matrix[0][1]又依赖matrix[1][1]，即形成环形依赖
        if(x>0 && matrix[x][y] < matrix[x-1][y])  max  =  ( (depth =  (findByDP(cache,matrix,x-1,y) )) >=  max) ? depth+1: max ;//上
        if(x < matrix.length-1 && matrix[x][y] < matrix[x+1][y])  max  =  ( (depth =  (findByDP(cache,matrix,x+1,y) )) >=  max) ? depth+1: max ; //下
        if(y< matrix[0].length-1 && matrix[x][y] < matrix[x][y+1])  max  =  ( (depth =  (findByDP(cache,matrix,x,y+1) )) >=  max) ? depth+1: max ;  //左
        if(y > 0 && matrix[x][y] < matrix[x][y-1])  max  =  ( (depth =  (findByDP(cache,matrix,x,y-1) )) >=  max) ? depth+1: max ; //右
        cache[x][y] = max;
        return max;
    }


    public static void main(String[] args) {

        int[][] nums = {{9,9,4},{6,6,8},{2,1,1}};
        System.out.println(new LongestIncreasingPathInAMatrix().longestIncreasingPath(nums));
    }
}
