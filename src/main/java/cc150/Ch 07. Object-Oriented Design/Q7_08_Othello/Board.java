package Q7_08_Othello;

public class Board {
	private int blackCount = 0;
	private int whiteCount = 0;
	private Piece[][] board;
	
	public Board(int rows, int columns) {
		board = new Piece[rows][columns];
	}
	
	public void initialize() {
		/* initial board has a grid like the following in the center:
		 *     WB
		 *     BW
		 */
		int middleRow = board.length / 2;
		int middleColumn = board[middleRow].length / 2;//初始化棋盘，先放置四个棋子
		board[middleRow][middleColumn] = new Piece(Color.White);
		board[middleRow + 1][middleColumn] = new Piece(Color.Black);
		board[middleRow + 1][middleColumn + 1] = new Piece(Color.White);
		board[middleRow][middleColumn + 1] = new Piece(Color.Black);
		blackCount = 2;
		whiteCount = 2;
	}
	
	public boolean placeColor(int row, int column, Color color) {
		if (board[row][column] != null) {
			return false;
		}
		
		/* attempt to flip each of the four directions */
		int[] results = new int[4];
		results[0] = flipSection(row - 1, column, color, Direction.up);
		results[1] = flipSection(row + 1, column, color, Direction.down);
		results[2] = flipSection(row, column + 1, color, Direction.right);
		results[3] = flipSection(row, column - 1, color, Direction.left);
		
		/* compute how many pieces were flipped */
		int flipped = 0;
		for (int result : results) {
			if (result > 0) {
				flipped += result; //统计上下左右总共翻转的个数
			}
		}
		
		/* if nothing was flipped, then it's an invalid move */
		if (flipped < 0) { //如果上下左右都没有被翻转，说明放到了一个错误的位置
			return false;
		}
		
		/* flip the piece, and update the score */
		board[row][column] = new Piece(color);
		updateScore(color, flipped + 1);
		return true;
	}
	
	private int flipSection(int row, int column, Color color, Direction d) {
		/* Compute the delta for the row and the column. At all times, only the row or the column
		 * will have a delta, since we're only moving in one direction at a time.
		 */
		int r = 0;
		int c = 0;
		switch (d) {
		case up:
			r = -1;//继续向上
			break;
		case down: //继续向下
			r = 1;
			break;
		case left: //继续向左
			c = -1;
			break;
		case right: //继续向右
			c = 1;
			break;
		}

		/**
		 * 越界
		 */
		/* If out of bounds, or nothing to flip, return an error (-1) */
		if (row < 0 || row >= board.length || column < 0 || column >= board[row].length || board[row][column] == null) {
			return -1;
		}
		
		/* Found same color - return nothing flipped */
		if (board[row][column].getColor() == color) { //找到了相同颜色，可以退出了
			return 0;
		}
		
		/* Recursively flip the remainder of the row. If -1 is returned, then we know we hit the boundary
		 * of the row (or a null piece) before we found our own color, so there's nothing to flip. Return
		 * the error code.
		 */
		int flipped = flipSection(row + r, column + c, color, d); //在相同方向上继续向上
		if (flipped < 0) {
			return -1;
		}
		
		/* flip our own color */
		board[row][column].flip();
		return flipped + 1;
	}
	
	public int getScoreForColor(Color c) {
		if (c == Color.Black) {
			return blackCount;
		} else {
			return whiteCount;
		}
	}
	
	public void updateScore(Color newColor, int newPieces) {
		/* If we added x pieces of a color, then we actually removed x - 1 pieces of the other
		 * color. The -1 is because one of the new pieces was the just-placed one.
		 */
		if (newColor == Color.Black) {
			whiteCount -= newPieces - 1;
			blackCount += newPieces;
		} else {
			blackCount -= newPieces - 1;			
			whiteCount += newPieces;
		}
	}
	
	public void printBoard() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length; c++) {
				if (board[r][c] == null) {
					System.out.print("_");
				} else if (board[r][c].getColor() == Color.White) {
					System.out.print("W");
				} else {
					System.out.print("B");
				}
			}
			System.out.println();
		}
	}
}
