package leetcode;

/**
 * Created by wuchang at 3/7/18
 * 这是8皇后问题
 */

public class EightQueen {
    private static final int N = 8;
    private int[] board = new int[N];
    void init()
    {
        for(int i=0;i<N;i++)
            board[i] = -1;
    }

    boolean can_put(int k)
    {
        for(int i=1;i<k;i++)
            if( (Math.abs(k-i) == Math.abs(board[k]-board[i]))  //判断是否在同一斜线上
                    || board[k] == board[i]) //判断是否在同一列上
                return false;
        return true;
    }

    void queen(int row)

    {
            for (int col=0;col<N;col++) { //试探第row行的每一个列

                if (can_put(row)){
                    board[row] = col; //放置皇后
                    if(row == N-1){ //已经是最后一行了
                        display(board);
                    }
                    else{
                        queen(row + 1);  //继续探测下一行
                    }
                }
            }
    }


    /**
     * 非递归方法解决8皇后问题
     * 一行一行的尝试放置皇后。显然，由于我们一行一行放置，这就满足了每个皇后都不在同一行，因此，我们对每一行放置皇后的时候，只需要判断当前的放置是否与其它皇后在同一列或者同一斜线
     */
    public void eight_queen() {
        int k = 0;
        while (k >= 0) { //从k=0开始
            board[k]++;
            while ((board[k] < N) && !can_put(k)) //在第k行中找到一个可以放置皇后的列
                board[k] += 1; //对于第k行，找一个能放皇后的地方
            if (board[k] < N) { //当前行成功放置了一个皇后
                if (k == N-1) //如果当前行已经是最后一行了，那么这就是一个成功的全局结果
                    System.out.println(display(board));
                else {//当前行还不是最后一行，因此，开始对下一行进行皇后位置的确认
                    k++;
                    board[k] = 0;
                }
            } else //没有在当前第k行没有找到任何一列可以放置皇后，那么，开始“回溯”到上一行
                k-- ;
        }
    }

    public String display(int[] board){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<board.length;i++){
            sb.append(board[i]+" ");
        }
        return sb.toString();
    }
}
