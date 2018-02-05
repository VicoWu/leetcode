package leetcode;

/**
 * Created by wuchang at 12/25/17
 * Question 130
 *
 * 根据题目描述，两种O：一种O属于包围节点，即这个O和它的所有邻居形成的联通分量中没有处在边界中的节点，另外一种O属于非包围节点，即这个O和邻居所行程的联通分量中存在至少一个O处于矩阵的边界上。
 # BFS
 代码中是我的BFS实现
 BFS是对边界上的所有O进行遍历，遍历到的O全部修改为N，当BFS结束，这些修改为N的节点就是全部的非包围O节点，剩余的X就是全部的包围型O节点
 将剩余的全部的O全部修改为X
 将全部的N全部修改为O


 # Union-Find
 `Union-Find`需要有数组保存每一个元素的parent值，find()操作通过不断递归读取parent，parent的parent，来判断两个元素是不是同一个union。union的时候，分别通过find找到两个元素的root parent ,然后用一个元素的替换掉另外一个元素的，从而实现两个元素的归并。
 这里的元素是二维数组，因此使用`unionSet = new int[height * width];`来记录每一个元素的parent，其中，unionSet中的i对应了board二维数组中的`board[i / width][i%width]`。同时，使用`hasEdgeO = new boolean[unionSet.length];`记录某个元素是不是在矩阵的边界上然后。这样，我们遍历所有的元素，将元素与它相邻的元素进行union。然后，我们对所有的元素进行find操作，只要find返回的root元素是非包围的的元素都是非包围的，因为它和它的root在同一个联通分量里面，反之，则是包围型的，需要被替换为X。
 这里，在对两个元素进行union的过程中，只要有一个节点是一个非包围型的节点，那么，他们的root节点就必须标记为非包围型，这样，才会让这个联通分量里面所有的节点的`hasEdgeO[findSet(i)]`都返回非包围型


 */

public class SurroundedRegions {
    /**
     * 这是我的BFS实现
     * @param board
     */
    public void solve(char[][] board) {

        if(board.length == 0) return;
        int h = board.length;int w = board[0].length;
        for(int i=0;i<board.length;i++){ //对于左右边界上的O，进行遍历，将遍历到的O全部临时修改为N,这些N就是非包围型O
                if(board[i][0] == 'O') filter(board,board.length,board[0].length,i,0);
                if(board[i][board[0].length-1] == 'O') filter(board,board.length,board[0].length,i,board[0].length-1);
        }
        for(int j=0;j<board[0].length;j++){ //对上下边界上的O，进行遍历，将遍历到的O全部临时修改为N，这些N也是非包围型O
            if(board[0][j] == 'O') filter(board,board.length,board[0].length,0,j);
            if(board[board.length-1][j] == 'O') filter(board,board.length,board[0].length,board.length-1,j);
        }


        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (board[i][j] == 'O') board[i][j] = 'X'; //这些O节点全部都是包围型，因此全部修改为X
                else if (board[i][j] == 'N') board[i][j] = 'O'; //临时修改为N的节点属于非包围型，因此全部改回为O
            }
        }


    }


    public void filter(char[][] board, int h, int w, int i, int j) {
        board[i][j] = 'N';
        if (i < h-1 && board[i + 1][j] == 'O') filter(board, h, w, i + 1, j);
        if (i > 0 && board[i - 1][j] == 'O') filter(board, h, w, i - 1, j);
        if (j < w-1 && board[i][j + 1] == 'O') filter(board, h, w, i, j + 1);
        if (j > 0 && board[i][j - 1] == 'O') filter(board, h, w, i, j - 1);
    }



    int[] unionSet; // union find set
    boolean[] hasEdgeO; // whether an union has an 'O' which is on the edge of the matrix

    /**
     * 这个union-find实现来自https://discuss.leetcode.com/topic/1944/solve-it-using-union-find/3
     *
     * @param board
     */
    public void solveByUnionFind(char[][] board) {
        if(board.length == 0 || board[0].length == 0) return;

        // init, every char itself is an union
        int height = board.length, width = board[0].length;
        unionSet = new int[height * width];//记录每一个节点的parent值
        hasEdgeO = new boolean[unionSet.length];//标记一个节点是包围型节点还是非包围型节点
        for(int i = 0;i<unionSet.length; i++) unionSet[i] = i; //初始化union-set，由于是二维，unionSet[i] = i，代表初始化状态下，每一个节点各自是一个集合
        for(int i = 0;i<hasEdgeO.length; i++){
            int x = i / width, y = i % width;
            hasEdgeO[i] = (board[x][y] == 'O' && (x==0 || x==height-1 || y==0 || y==width-1));//所有边界节点上的O进行标记
        }

        // iterate the matrix, for each char, union it + its upper char + its right char if they equals to each other
        for(int i = 0;i<unionSet.length; i++){ //由于x=i/width,y=i%width ，这个遍历实际上是一行一行，每行从左到右遍历的
            int x = i / width, y = i % width, up = x - 1, right = y + 1;
            if(up >= 0 && board[x][y] == board[up][y]) union(i,i-width); //i-width代表上方节点
            if(right < width && board[x][y] == board[x][right]) union(i,i+1);//i+1代表右侧节点
        }

        // for each char in the matrix, if it is an 'O' and its union doesn't has an 'edge O', the whole union should be setted as 'X'
        for(int i = 0;i<unionSet.length; i++){
            int x = i / width, y = i % width;
            if(board[x][y] == 'O' && !hasEdgeO[findSet(i)]) //对于每一个节点，如果这个节点是一个O,并且它的root没有被标记为边界O，则这个节点肯定是非边界O，应该被改为X
                board[x][y] = 'X';
        }
    }

    private void union(int x,int y){
        int rootX = findSet(x);
        int rootY = findSet(y);
        // if there is an union has an 'edge O',the union after merge should be marked too
        //union的过程中，只要有一个节点是一个非包围型的节点，那么，他们的root节点就必须标记Wie非包围型，这样，才会让这个联通分量里面所有的节点的hasEdgeO[findSet(i)]都返回非包围型
        boolean hasEdgeO = this.hasEdgeO[rootX] || this.hasEdgeO[rootY];

        unionSet[rootX] = rootY;
        this.hasEdgeO[rootY] = hasEdgeO;
    }

    private int findSet(int x){
        if(unionSet[x] == x) return x;
        unionSet[x] = findSet(unionSet[x]);
        return unionSet[x];
    }



    public static void main(String[] args) {

        char[][] board = {{'X','X','X','X'},{'X','O','O','X'},{'X','X','O','X'},{'X','O','X','X'}};
        new SurroundedRegions().solve(board);
    }
}
