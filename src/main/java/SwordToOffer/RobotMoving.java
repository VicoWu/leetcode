package SwordToOffer;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 剑指 Offer 13. 机器人的运动范围
 * 这一题要求机器人从[0,0]出发，因此可以通过BFS来探索是否能到达某个点
 */
public class RobotMoving {
        public int movingCount(int m, int n, int k) {
            if (k == 0) {
                return 1;
            }
            boolean[][] vis = new boolean[m][n];
            int ans = 1;
            vis[0][0] = true;
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if ((i == 0 && j == 0) || get(i) + get(j) > k) {
                        continue;
                    }
                    // 边界判断
                    if (i - 1 >= 0) {
                        vis[i][j] |= vis[i - 1][j];
                    }
                    if (j - 1 >= 0) {
                        vis[i][j] |= vis[i][j - 1];
                    }
                    ans += vis[i][j] ? 1 : 0;
                }
            }
            return ans;
        }

        private int get(int x) {
            int res = 0;
            while (x != 0) {
                res += x % 10;
                x /= 10;
            }
            return res;
        }

    public int movingCount2(int m, int n, int k) {
        boolean visited[][] = new boolean[m][n];
        visited[0][0] = true;
        int ans = 1;
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.add(new int[]{0,0});
        int[][] vec = {{0,1},{1,0}};
        while(!queue.isEmpty()){
            int[] point = queue.poll();
            for(int i = 0; i<2; i++){
                int x = point[0] + vec[i][0];
                int y = point[1] + vec[i][1];
                if(x < m && y < n & !visited[x][y] && (get2(x) + get2(y)) <= k){
                    ans++;
                    queue.add(new int[]{x,y});
                }
                if(x < m && y < n){
                    visited[x][y] = true;
                }
            }
        }
        return ans;
    }

    private int get2(int x){
        int ans = x % 10;
        if(x >= 10){
            ans += (ans/10)%10;
        }
        return ans;
    }


    public static void main(String[] args) {
        int array[][] = {{0,1,2},{1,2,3},{2,3,4}};
        new RobotMoving().get2(38);
    }
}
