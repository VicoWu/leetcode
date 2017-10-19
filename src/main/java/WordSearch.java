/**
 * Question 79
 *
 * 很明显的深度优先遍历算法
 最需要注意的是这句话：`The same letter cell may not be used more than once.`，也就是说，同一个位置[i,j]，不可以被使用两次。
 因此，我们在遍历的过程中，遍历到了某个位置[i,j]，就把这个位置置为\*，代表这个位置我们已经遍历过。但是，是不是这样就可以了？不是的。
 比如，这种输入就会出现问题：{{'c','a','a'},{'a','a','a'},{'b','c','d'}} ， "aab"，这是因为我们在上一轮遍历的时候由于遍历过a，
 把它置为\*，导致下一轮也无法使用a了，而且， "aab"中有两个a。因此，我们将某个位置标记为\*,只可以影响到本轮的下层递归调用，本层递归完成以后，必须还原。


 */
public class WordSearch {
    public boolean exist(char[][] board, String word) {

        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (exist(board, i, j, 0, word)) return true;
                }

            }
        return false;
    }

    public boolean exist(char[][] board, int i, int j, int startIndex, String word) {

        if (i < 0 || j < 0 || i > board.length - 1 || j > board[0].length - 1) return false;
        if (board[i][j] == '*') return false;
        if (word.charAt(startIndex) != board[i][j]) return false;
        if (startIndex+1 == word.length()) return true;
        char bak = board[i][j];
        board[i][j]='*';
        boolean result  =  exist(board, i, j + 1, startIndex + 1, word)
                || exist(board, i, j - 1, startIndex + 1, word)
                || exist(board, i + 1, j, startIndex + 1, word)
                || exist(board, i - 1, j, startIndex + 1, word);

        board[i][j] = bak;
        return result;
    }

    public static void main(String[] args) {

        char[][] input = {{'c','a','a'},{'a','a','a'},{'b','c','d'}};
        System.out.println(new WordSearch().exist(input,"aab"));
    }
}
