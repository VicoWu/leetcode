package leetcode;

/**
 * 剑指 Offer 47. 礼物的最大价值
 */
public class MaxValue {
    /**
     * 典型的动态规划，grid[0][0]到grid[i][j]的最大距离，是由grid[0][0]到grid[i-1][j]和grid[0][0]到grid[i][j-1]决定的。
     * @param grid
     * @return
     */
    public int maxValue(int[][] grid) {
        int[] cache = new int[grid[0].length];
        cache[0] = grid[0][0];
        for(int i = 0;i<grid.length;i++){
            int[] current = new int[grid[0].length];
            for(int j = 0;j<grid[0].length;j++){
                int max = grid[i][j];
                if(i > 0){
                    max = Math.max(max, grid[i][j] + cache[j]);
                }
                if(j > 0){
                    max = Math.max(max, grid[i][j] + current[j-1]);
                }
                current[j] = max;
            }
            cache = current;
        }
        return cache[grid[0].length - 1];
    }
}
