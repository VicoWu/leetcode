package leetcode;

import java.util.HashSet;

/**
 * Created by wuchang at 12/20/17
 * Question 36
 */

public class ValidSudoku {
    /**
     * 这个解法的巧妙之处就是只需要一轮遍历，就可以判断是不是合法的数独。尤其需要注意的是判断9个3*3的小方格
     *
     * @param board
     * @return
     */
    public boolean isValidSudoku(char[][] board) {
        for(int i = 0; i<9; i++){
            HashSet<Character> rows = new HashSet<Character>();
            HashSet<Character> columns = new HashSet<Character>();
            HashSet<Character> cube = new HashSet<Character>();
            for (int j = 0; j < 9;j++){
                if(board[i][j]!='.' && !rows.add(board[i][j]))
                    return false;//判断是否满足每一个行的数字不同
                if(board[j][i]!='.' && !columns.add(board[j][i]))
                    return false; //判断是否满足每一列的数字不同

                //判断是否满足每一个sub-boxes数字唯一
                //当i=0,横纵坐标的起始位置是[0,0],i=1,横纵坐标的起始位置是[0,3],i=2:[0,6]，i=3:[3,0] ,i=4:[3,3],i=5:[3,6],i=7:[6,0], i=8:[6,3],i=9:[6,6]
                //即，i从0到9刚好确定了9个3*3小方格的左上角的方格的坐标，然后通过j从0到9确定这个3*3的方格中每个小格子的坐标，从而判断是不是合法的数独
                int RowIndex = 3*(i/3);
                int ColIndex = 3*(i%3);
                if(board[RowIndex + j/3][ColIndex + j%3]!='.' && !cube.add(board[RowIndex + j/3][ColIndex + j%3]))
                    return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        for(int i = 0; i<9; i++) {
            for (int j = 0; j < 9;j++){
                int RowIndex = 3*(i/3);
                int ColIndex = 3*(i%3);
                System.out.println(RowIndex+" "+ColIndex);
            }
        }
    }
}
