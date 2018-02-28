package Q7_08_Othello;

public class Game {
	private Player[] players;
	private static Game instance;
	private Board board;
	private final int ROWS = 10;
	private final int COLUMNS = 10;

	/**
	 * 一个游戏基本上包含棋盘、选手、棋子
	 */
	private Game() {
		board = new Board(ROWS, COLUMNS);
		players = new Player[2];
		players[0] = new Player(Color.Black);
		players[1] = new Player(Color.White);
		Automator.getInstance().initialize(players); // used for testing
	}
	
	public static Game getInstance() { //单例
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}
	
	public Board getBoard() {
		return board;
	}
}
