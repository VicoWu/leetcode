/**
 * Created by wuchang at 1/5/18
 * Question 59
 * 这一题与[Spiral Matrix](https://leetcode.com/problems/spiral-matrix/description/)的解法基本相同，
 * 都是通过设置四个rowBegin、rowEnd、colBegin、colEnd来限制范围，先往右、再往下、再往左、再往上进行遍历；
 */

public class SpiralMatrixII {
    public int[][] generateMatrix(int n) {

        int[][] result = new int[n][n];
        int rowBegin = 0 ,colBegin = 0;
        int rowEnd =n-1, colEnd = n-1;
        int value = 1;
        while(rowBegin <= rowEnd && colBegin <= colEnd){
            for(int i=colBegin;i<=colEnd;i++){
                result[rowBegin][i] = value++;
            }
            rowBegin++;
            for(int i= rowBegin;i<=rowEnd;i++){
                result[i][colEnd] = value++;
            }
            colEnd --;
            for(int i = colEnd;i>=colBegin;i--)
                result[rowEnd][i] = value++;
            rowEnd--;
            for(int i = rowEnd;i>=rowBegin;i--)
                result[i][colBegin] = value++;
            colBegin++;
        }
        return result;
    }


    public static void main(String[] args) {
        new SpiralMatrixII().generateMatrix(3);
    }
}
