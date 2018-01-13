/**
 * Created by wuchang at 1/12/18
 * Question 200
 *
 *三种方法 ，1. DFS 2. BFS  3. UnionFind  比较简单。我使用的是DFS
 */

public class NumberOfIslands {

    public int numIslands(char[][] grid) {

        if(grid.length==0)
            return 0;
        int count = 0;
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                if(grid[i][j] == '1'){
                    search(grid,i,j);
                    count++;
                }

            }
        }


        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length && grid[i][j]=='*';j++){
                grid[i][j]='1';
            }
        }
        return count;
    }

    public void search(char[][] grid,int i,int j){
        grid[i][j]='*';
        if(i>0 && grid[i-1][j]=='1') search(grid,i-1,j);
        if(i<grid.length-1 && grid[i+1][j]=='1') search(grid,i+1,j);
        if(j>0 && grid[i][j-1]=='1') search(grid,i,j-1);
        if(j<grid[0].length-1 && grid[i][j+1]=='1') search(grid,i,j+1);
    }


    public static void main(String[] args) {
        char[][] nums = {
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}};
        System.out.println(new NumberOfIslands().numIslands(nums));
    }

}
