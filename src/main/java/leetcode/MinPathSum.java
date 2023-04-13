package leetcode;

/**
 * Question 64
 */
public class MinPathSum {
    /**
     * 我的思路是通过一个中间矩阵来存放递归过程的中间结果，避免重复计算。
     * 注意：如果这一题没有说"只能向下或者向右"，而是可以上下左右自由移动，那么下面的算法是会出现栈溢出的。
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] cache = new int[m][n];
        cache[m-1][n-1]=grid[m-1][n-1];
        return getMinPath(grid, cache, m,n, 0, 0);
    }
    public int getMinPath(int[][] grid, int[][] cache, int m, int n, int i, int j){
        if(cache[i][j]!=0 || (i == m-1 && j == n-1)){
            return cache[i][j];
        }
        int shortest = Integer.MAX_VALUE;
        if(i+1 < m){
            shortest=Math.min(Integer.MAX_VALUE, getMinPath(grid, cache, m, n, i+1, j) + grid[i][j]);
        }
        if(j+1 < n){
            shortest=Math.min(shortest, getMinPath(grid, cache,m,n,i, j+1) + grid[i][j]);
        }
        cache[i][j]=shortest;
        return shortest;
    }


    /**
     *     作者：Krahets
     *     链接：https://leetcode.cn/problems/minimum-path-sum/solutions/25943/zui-xiao-lu-jing-he-dong-tai-gui-hua-gui-fan-liu-c/
     *     来源：力扣（LeetCode）
     *     著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     *     由近及远，逐渐计算grid[i][j]到grid[0][0]的距离，直到最后我们得到grid[m-1][n-1]
     *     当然，我们也可以逐渐计算grid[i][j]到grid[m-1][n-1]的距离，直到最后我们得到了grid[0][0]到grid[m-1][n-1]的距离， refer  minPathSum3
     * @param grid
     * @return
     */
    public int minPathSum2(int[][] grid) {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(i == 0 && j == 0) continue;
                else if(i == 0)  grid[i][j] = grid[i][j - 1] + grid[i][j];
                else if(j == 0)  grid[i][j] = grid[i - 1][j] + grid[i][j];
                else grid[i][j] = Math.min(grid[i - 1][j], grid[i][j - 1]) + grid[i][j];
            }
        }
        return grid[grid.length - 1][grid[0].length - 1];
    }

    /**
     *
     * @param grid
     * @return
     */
    public int minPathSum3(int[][] grid) {
        for(int i = grid.length-1; i>=0; i--){
            for(int j = grid[0].length-1; j>=0; j--){
                if(i == grid.length -1 && j == grid[0].length-1){
                    continue;
                }
                if(i == grid.length - 1){
                    grid[i][j] = grid[i][j+1] + grid[i][j];
                }
                else if(j == grid[0].length - 1){
                    grid[i][j] = grid[i+1][j] + grid[i][j];
                }
                else{
                    grid[i][j] = grid[i][j] + Math.min(grid[i+1][j], grid[i][j+1]);
                }
            }

        }
        return grid[0][0];
    }

}
