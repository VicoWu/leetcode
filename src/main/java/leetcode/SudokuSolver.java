package leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

    private HashSet<Character>[] rowChoice; // 按照行规则当前可以填入的字符
    private HashSet<Character>[] columnChoice;// 按照列规则当前可以填入的字符
    private HashSet<Character>[] boxChoice ; // 按照方框规则当前可以填入的字符
    public SudokuSolver(){
        rowChoice = new HashSet[9];
        columnChoice = new HashSet[9];
        boxChoice = new HashSet[9];
    }
    public void solveSudoku3(char[][] board) {
        HashSet<Character> init = new HashSet();
        for(int i = 1;i<10;i++){
            init.add((char)(i + '0'));
        }

        for(int i = 1;i<10;i++){
            rowChoice[i-1] = new HashSet(init);
            columnChoice[i-1] = new HashSet(init);
            boxChoice[i-1] = new HashSet(init);
        }

        // 根据当前棋盘已经填入的数组，确定按照行规则、列规则、方框规则可用的数字
        for(int i = 0;i<board.length;i++){
            for(int j = 0;j<board[i].length;j++){
                rowChoice[i].remove(board[i][j]); // 这个字符在行规则中不能再填写
                columnChoice[j].remove(board[i][j]); // 这个字符在列规则中不能再填写
                boxChoice[3 * (i / 3) + j/3].remove(board[i][j]);  // 这个字符在方框规则中不能再填写
            }
        }
        findFrom(board, 0, 0);
    }

    /**
     从位置[i,j]开始按照从左往右、从上到下的顺序查找
     */
    private boolean findFrom(char[][] board, int i, int j){
        if(i==9){ // 出结果了
            return true;
        }
        if(i == 0 && j == 0){
            System.out.println();
        }
        if(board[i][j] != '.'){
            boolean result = findFrom(board, j == 8 ? i + 1 : i, j == 8 ? j = 0:j+1);
            if(result){
                if(i == 0 && j == 0){
                    System.out.println();
                }
                return true;
            }
            if(i == 0 && j == 0){
                System.out.println();
            }
            return false;
        } else{ // it is blank
            List<Character> choices = choice(rowChoice[i], columnChoice[j], boxChoice[3 * (i / 3) + j/3]);
            if(i == 0 && j == 0){
                System.out.println();
            }
            for(Character choice: choices){
                attempt(board, choice, i, j);
                if(i == 0 && j == 7){
                    System.out.println();
                }
                boolean result = findFrom(board, j == 8 ? i + 1 : i, j == 8 ? j = 0:j+1);
                if(result){
                    if(i == 0 && j == 0){
                        System.out.println();
                    }
                    return true;
                }
                if(i == 0 && j == 0){
                    System.out.println();
                }
                wipe(board, i, j);
            }
            return false;
        }
    }

    /**
     在位置[i,j]能够填入的数字
     */
    private List<Character> choice(HashSet<Character> rowChoice, HashSet<Character> columnChoice, HashSet<Character> boxChoice){
        List<Character> choice = new ArrayList();
        for(Character c: rowChoice){
            if(columnChoice.contains(c) && boxChoice.contains(c)){
                choice.add(c);
            }
        }
        return choice;
    }

    private void attempt(char[][] board, char c, int i , int j){
        board[i][j] = c;
        rowChoice[i].remove(c); // 这个字符在行规则中不能再填写
        columnChoice[j].remove(c); // 这个字符在列规则中不能再填写
        boxChoice[3 * (i / 3) + j/3].remove(c);  // 这个字符在方框规则中不能再填写
    }

    /**
     这个数字不行，擦掉
     */
    private void wipe(char[][] board, int i , int j){
        char c = board[i][j];
        board[i][j] = '.';
        rowChoice[i].add(c); // 这个字符在行规则中不能再填写
        columnChoice[j].add(c); // 这个字符在列规则中不能再填写
        boxChoice[3 * (i / 3) + j/3].add(c);  // 这个字符在方框规则中不能再填写
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
        new SudokuSolver().solveSudoku3(board);
        System.out.println("");
    }


    private void testIsValid(){

    }
}
