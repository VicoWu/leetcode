/**
 * Created by wuchang at 12/20/17
 *
 * Question 37
 * 这一题的解法也比较通用，就是通过递归和回溯，不断尝试，找到一个合法的解。
 对于任何一个没有填入数字的方格，我们从0-9依次尝试填入数字，当然，填入的数字必须是合法的，即：
 1. 对应行没有重复数字
 2. 对应列没有重复数字
 3. 对应sub-box没有重复数字
 注意，合法并不代表就是正确的解，一个正确的解的每个位置都是合法的。

 对于位置`(i,j)`填入一个数字以后，对这个字谜调用`solve()`进行相同的递归运算。当在位置`(i,j)`调用`solve()`最终返回false，
 说明没有找到解，因此尝试下一个数字，依次递归。一旦`solve()`返回true，说明找到了一个接，所有的`solve()`的调用栈直接全部返回true退栈。

 注意，如果在位置(i,j)填入所有数字以后，调用`solve()`都返回false，这一层递归准备回溯的时候，这个填入的位置的数字（最后填入了数字9）
 必须还原成未填写状态，即恢复为'.'，因为回溯以后，这个位置会被重新进行`0-9`的遍历尝试。


 */

public class SudokuSolver {

    public void solveSudoku(char[][] board) {

        if(board.length==0)
            return;
        solve(board);
        System.out.println("finished.");
    }


    public boolean solve(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

                if (board[i][j] != '.')
                    continue;
                for (char c = '1'; c <= '9'; c++) {
                    if (isValid(board, i, j, c)) {
                        board[i][j] = c;
                        if (solve(board))
                            return true;
                        else
                            board[i][j] = '.';
                    }
                    else
                        board[i][j] = '.';
                }
                return false;
            }
        }
        return true;
    }


    private boolean isValid(char[][] board,int row,int col,char c){
        int luRow = 3 * (row/3);int luCol = 3 * (col/3);
        for(int i=0;i<board.length;i++){
            if(board[row][i] == c) //判断同一行是否有相同的数字
                return false;
            if(board[i][col] == c) //判断同一列是否有相同的数字
                return false;
            //System.out.println((luRow + i/3) + " "+(luCol + i%3));
            if(board[luRow + i/3][luCol + i%3] == c) //判断所在的sub-box是否有相同的数组
                return false;
        }
        return true;
    }

    public static void main(String[] args) {

        char[][] board = {
                {'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}};
        new SudokuSolver().solveSudoku(board);
    }


    private void testIsValid(){

    }
}
