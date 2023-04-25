package leetcode;

/**
 * Question 1314
 * Similar Question: https://leetcode.cn/problems/maximum-side-length-of-a-square-with-sum-less-than-or-equal-to-threshold/description/
 */
public class MatrixBlockSum {
    public int[][] matrixBlockSum(int[][] mat, int k) {
        int[][] result = new int[mat.length][mat[0].length];
        // 开始计算面积前缀和数组
        int[][] preArea = new int[mat.length][mat[0].length];
        int sum = 0;
        // 作为corner case，先计算第一行的所有数据的前缀和
        for(int col = 0;col < mat[0].length;col++){
            preArea[0][col] = sum + mat[0][col];
            sum+=mat[0][col];
        }
        sum = 0;
        // 作为corner case，先计算第一列的所有数据的前缀和
        for(int row = 0;row < mat.length;row++){
            preArea[row][0] = sum + mat[row][0];
            sum+=mat[row][0];
        }
        // 计算其他前缀和
        for(int row = 1;row<mat.length;row++){
            for(int col=1;col<mat[0].length;col++){
                preArea[row][col] = preArea[row-1][col] + preArea[row][col-1] - preArea[row-1][col-1] + mat[row][col];
            }
        }

        for(int i = 0;i<mat.length;i++){
            for(int j = 0;j<mat[0].length;j++){
                result[i][j] = getArea(
                        Math.max(i - k,0),
                        Math.max(j - k, 0),
                        Math.min(i+k, mat.length - 1),
                        Math.min(j+k, mat[0].length - 1),
                        preArea);
            }
        }
        return result;
    }

    /**
     计算以[row1, col1]为左上侧节点、以[row2, col2]为右下侧节点的矩阵的节点之和
     */
    private int getArea(int row1, int col1, int row2, int col2, int[][] preArea){
        if(row1 == 0 && col1 == 0){
            return preArea[row2][col2];
        }else if(row1 == 0){
            return preArea[row2][col2] - preArea[row2][col1-1];
        }else if(col1 == 0){
            return preArea[row2][col2] - preArea[row1 - 1][col2];
        }else{
            return preArea[row2][col2]
                    - preArea[row2][col1-1]
                    - preArea[row1-1][col2]
                    + preArea[row1-1][col1-1];
        }

    }
}
