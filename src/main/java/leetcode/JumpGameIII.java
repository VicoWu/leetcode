package leetcode;

/**
 * Question 1306
 * 我使用的是dfs算法
 * 由于我们无法保证是否有环（比如， {3,0,2,1,2}），因此必须有一个中间数组来记录已经访问过的记录，避免环的存在导致递归无法退出
 */
public class JumpGameIII {
    public boolean canReach(int[] arr, int start) {
        boolean[] visited = new boolean[arr.length];
        return search(arr, visited, start);
    }

    private boolean search(int[] arr, boolean[] visited, int start) {
        if(arr[start] == 0){
            return true;
        }
        if(visited[start]){
            return false;
        }
        visited[start] = true;
        if(start + arr[start] < arr.length){
            boolean result = search(arr, visited, start + arr[start]);
            if(result){
                return true;
            }
        }
        if(start - arr[start] >= 0){
            boolean result = search(arr, visited, start - arr[start]);
            if(result){
                return true;
            }
        }
        return false;
    }
}
