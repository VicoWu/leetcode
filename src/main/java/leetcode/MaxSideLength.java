package leetcode;

public class MaxSideLength {
    public int maxSideLength(int[][] mat, int threshold) {
        if(mat.length == 0)
            return 0;
        if(mat.length == 1 || mat[0].length == 1){
            for(int i = 0;i<mat.length;i++){
                for(int j = 0;j<mat.length;j++){
                    if(mat[i][j] >= threshold){
                        return 1;
                    }
                }
            }
            return 0;
        }

        int[][] preArea = new int[mat.length][mat[0].length];
        int sum = 0;
        for(int col = 0;col < mat[0].length;col++){
            preArea[0][col] = sum + mat[0][col];
            sum+=mat[0][col];
        }
        sum = 0;
        for(int row = 0;row < mat.length;row++){
            preArea[row][0] = sum + mat[row][0];
            sum+=mat[row][0];
        }

        for(int row = 1;row<mat.length;row++){
            for(int col=1;col<mat[0].length;col++){
                preArea[row][col] = preArea[row-1][col] + preArea[row][col-1] - preArea[row-1][col-1] + mat[row][col];
            }
        }

        int l = 1; int r = Math.min(mat.length, mat[0].length);
        int maxLength = 0;
        while(l <= r){
            int mid = (l + r)/2;
            boolean found = false;
            // 计算大小为mid的矩形是否满足条件
            for(int row = 0;row<=mat.length-mid;row++){
                for(int col = 0;col<=mat[0].length-mid;col++){
                    if(getArea(row, col, mid, preArea) <= threshold){
                        found = true;
                        maxLength = mid;
                        l = mid+1;
                        break;
                    }
                }
                if(found){
                    break;
                }else{
                    r = mid - 1;
                }
            }
        }
        return maxLength;


    }

    private int getArea(int row, int col, int width, int[][] preArea){
        if(row == 0 && col == 0){
            return preArea[row + width - 1][col + width - 1];
        }else if(row == 0){
            return preArea[row + width - 1][col + width - 1] - preArea[row + width - 1][col-1];
        }else if(col == 0){
            return preArea[row + width - 1][col + width - 1] - preArea[row - 1][col + width - 1];
        }else{
            return preArea[row + width - 1][col + width - 1]
                    - preArea[row-1][col + width - 1]
                    - preArea[row + width - 1][col-1]
                    + preArea[row-1][col-1];
        }

    }

    public static void main(String[] args) {
        int array[][] = new int[][] {{1,1,3,2,4,3,2},{1,1,3,2,4,3,2},{1,1,3,2,4,3,2}};
        System.out.println(new MaxSideLength().maxSideLength(array, 4));
    }
}
