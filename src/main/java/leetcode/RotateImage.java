package leetcode;

/**
 * Created by wuchang at 1/3/18
 * Question 48
 *
 * # 我的解法
 我的解法是将这个正方形一层一层的旋转：
 比如
 ```
 [
 [ 5, 1, 9,11],
 [ 2, 4, 8,10],
 [13, 3, 6, 7],
 [15,14,12,16]
 ],
 有两层
 我们先对外层进行旋转，然后再旋转内层。
 每个n*n的正方形的层数是int round = (n+1)/2;
 一个正方形的坐标[i,j]，经过旋转以后的坐标是[j,n-i-1]
 由于不允许创建新的数组，我们只能创建一些中间变量：
 比如，对于matrix[0][0] -> matrix[0][3]，我们暂存matrix[0][3]，然后将matrix[0][0] 放入matrix[0][3]，
 然后我们暂存matrix[3][3]，将暂存值放入 matrix[3][3]，然后，我们暂存matrix[3][0],将上一个暂存值存入matrix[3][0]，最后，我们将上一个暂存值存入matrix[0][0]。
 这样，我们通过一圈，完成了matrix[0][0] -> matrix[0][3] -> matrix[3][3]-> matrix[3][0]的旋转。
 然后，我们完成matrix[0][1] -> matrix[1][3] -> matrix[3][2]-> matrix[2][0]的旋转
 当完成了外圈的旋转，我们继续进行内圈的旋转。。。

 ```

 # 别人的解法

 按照[这里](https://leetcode.com/problems/rotate-image/discuss/18872)的解法，先将矩阵上下交换，然后再斜线交换就行了

 ```
 /*
 clockwise rotate 这种做法的好处是，全部都是交换，没有链式的传递的交换，所以非常简单
 first reverse up to down, then swap the symmetry
 1 2 3     7 8 9     7 4 1
  4 5 6  => 4 5 6  => 8 5 2
 7 8 9     1 2 3     9 6 3


```
 */

public class RotateImage {
    public void rotate(int[][] matrix) {

        int n = matrix.length;
        int round = (n+1)/2;
        for(int r=0;r<round;r++){ // 逐圈进行
            for(int s = r;s<n-r-1;s++){
                int i = r;int j=s;
                int times = 0;
                int source = matrix[i][j];
                while(times++ < 4){
                    int bak =  matrix[j][n-i-1];
                   matrix[j][n-i-1] = source;
                   source =  bak ;
                   int ti = i;
                   i = j;
                   j = n-ti-1;
                }
            }
        }
        System.out.println();
    }

    public void rotate5(int[][] matrix) {
        int n = matrix.length;
        int colNum = n;
        int roundNum = (n+1)/2; // 总圈数，比如n=3, roundNum=2, n=4, roundNum=2, n=5，roundNum=3
        for(int round = 0; round < roundNum; round++){
            int row = round;
            // 对于当前的圈，需要处理的总数量，比如，colNum=4，需要以3个数字开始循环
            for(int col = round; col < colNum - 1 + round ; col++){
                // 当前的数字是matrix[row][col]
                //保存第二个值
                int tmp2 = matrix[col][n - 1 - row];
                // 用第一个值matrix[row][col]覆盖第二个值
                matrix[col][n - 1 - row] = matrix[row][col];

                // 保存第三个值
                int tmp3 = matrix[n - 1 - row][n -1 - col];
                // 用第二个值覆盖第三个值matrix[n - 1 - row][n -1 - col]
                matrix[n - 1 - row][n - 1 - col] = tmp2;

                // 保存第四个值
                int tmp4 = matrix[n -1 - col][row];
                // 用第三个值覆盖第四个值matrix[n - 1 - row][n -1 - col]
                matrix[n - 1 - col][row] = tmp3;

                // 保存第一个值(其实没有必要)
                int tmp1 = matrix[row][col];
                // 用第四个值覆盖第一个值matrix[row][col]
                matrix[row][col] = tmp4;
            }
            colNum = colNum - 2; //这一圈的列数，比如，n=4的情况下，最外圈colNum = 4, 第二圈colNum = 2
        }
    }

    public static void main(String[] args) {

        int[][] matrix = {{5,1,9,11},{2,4,8,10},{13,3,6,7},{15,14,12,16}};
        new RotateImage().rotate(matrix);
    }

}