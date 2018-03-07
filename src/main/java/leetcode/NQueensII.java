package leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuchang at 3/7/18
 * Question 52
 */

public class NQueensII {

    /**
     * 这是基于递归的N皇后问题
     * @param n
     * @return
     */
    public int totalNQueens(int n) {
        char[][] board = new char[n][n];
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                board[i][j] = '.';
        //List<List<String>> res = new ArrayList<List<String>>();
        return dfs(board, 0);
     }

    /**
     * 这里的解法是一列一列进行，因此，防止的皇后天然就满足不在同一列的要求，每放置一个皇后，只需要判断是否满足不再同一行以及不在同一斜线的要求。
     * @param board
     * @param colIndex
     * @param res
     */
    private int dfs(char[][] board, int colIndex) {
        if(colIndex == board.length) { //colIndex == board.length代表已经完成了最后一行的皇后的放置，即，已经获得了一个合法的全局棋盘
            //res.add(construct(board)); //保存棋盘状态到结果集中
            return 1;
        }

        int result = 0;
        for(int i = 0; i < board.length; i++) { //对于棋盘的每一行
            if(validate(board, i, colIndex)) {
                board[i][colIndex] = 'Q';
                result += dfs(board, colIndex + 1); //将结果相加
                board[i][colIndex] = '.';
            }
        }
        return result;
    }

    private boolean validate(char[][] board, int x, int y) {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < y; j++) {
                if(board[i][j] == 'Q' && (x + j == y + i || x + y == i + j || x == i))
                    return false;
            }
        }

        return true;
    }

    /**
     * 打印N皇后棋盘的状态
     * @param board
     * @return
     */
    private List<String> construct(char[][] board) {
        List<String> res = new LinkedList<String>();
        for(int i = 0; i < board.length; i++) {  //对于每一行
            String s = new String(board[i]);
            res.add(s);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(new NQueensII().totalNQueens(4));
    }
}
