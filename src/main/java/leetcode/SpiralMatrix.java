package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuchang at 1/4/18
 * Question 54
 * 直接采用了别人的解法
 我们围绕一个矩形进行环绕的时候，先向右走，然后向下走，然后向左走，然后向上走。
 因此，使用rowBegin、rowEnd、colBegin、colEnd的边界，在边界的控制范围内先向右走，然后向下走，然后向左走，然后向上走，其中，向右走了以后需要向下走，此时rowBegin加1，向下走了以后向左走，此时colEnd减1，向左走了以后向上走，rowEnd减1，向上走完了以后，colBegin加1，此时一圈走完，rowBegin从0变成1，colBegin从0变成1，rowEnd从m变成m-1，colEnd变成n-1，开始下一圈。
 [Spiral Matrix II](https://leetcode.com/problems/spiral-matrix-ii/description/)与这一题基本相同。
 */

public class SpiralMatrix {

    /**
     * 这是别人的解法
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder1(int[][] matrix) {

        List<Integer> res = new ArrayList<Integer>();

        if (matrix.length == 0) {
            return res;
        }

        int rowBegin = 0;
        int rowEnd = matrix.length-1;
        int colBegin = 0;
        int colEnd = matrix[0].length - 1;

        while (rowBegin <= rowEnd && colBegin <= colEnd) {
            // Traverse Right
            for (int j = colBegin; j <= colEnd; j ++) {
                res.add(matrix[rowBegin][j]);
            }
            rowBegin++;

            // Traverse Down
            for (int j = rowBegin; j <= rowEnd; j ++) {
                res.add(matrix[j][colEnd]);
            }
            colEnd--;

            if (rowBegin <= rowEnd) {  //这个判断是为了防止那种以后一行或者只有一列是数据的情况，避免出现重复数据
                // Traverse Left
                for (int j = colEnd; j >= colBegin; j --) {
                    res.add(matrix[rowEnd][j]);
                }
            }
            rowEnd--;

            if (colBegin <= colEnd) {  //这个判断是为了防止那种以后一行或者只有一列是数据的情况，避免出现重复数据
                // Traver Up
                for (int j = rowEnd; j >= rowBegin; j --) {
                    res.add(matrix[j][colBegin]);
                }
            }
            colBegin ++;
        }

        return res;
    }

    public static void main(String[] args) {

        int n = 5;
        int[][]  matrix = new int[n][n];
        int k = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                matrix[i][j]=k++;
            }
        }
        int[][] matrix2 = {{2,3}};
        new SpiralMatrix().spiralOrder1(matrix2);
    }

}
