package leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuchang at 12/19/17
 *
 * # 我的实现

 我的实现完全是仿照https://discuss.leetcode.com/topic/33246/java-15ms-easiest-solution-100-00
 进行的实现，没有区别

 将words中所有的单词构建出一个前缀树。构建前缀树的目的，是为了避免那些有共同前缀的单词的重复查找，
 有了前缀树，它们共同的前缀只需要在矩阵中查找一次。由于这里的单词同样是小写的a-z，只有有限的26个不同的字符，
 因此这里的前缀树，使用的是Implement Trie的讨论中优化的前缀树，即树的节点中不需要保存代表的字符，而是用父亲节点
 指针是否为空来判断它的位置；

 由于题目中规定board中的字符在某一个单词中最多使用一次，不可重复使用，我们可以选择使用一个
 缓存来记录哪些节点已经被使用。但是这里使用了一个巧妙的方式，如果一个字符被使用，则用一个特殊字符替换做标记，
 当栈返回，再把原字符还原；

 避免重复：加入矩阵board有某个单词oath的两种不同的形式，那么如果不去重，则会输出两个oath。
 我们固然可以使用一个Set来保存结果起到去重的目的，但是本解法的去重方式也很巧妙，当我们找到了某个word的合法存在，
 就把前缀树中这个单词置为空，这样，即使board中有这个单词的另外一个实现，我们下次遍历也找不到这个单词了。

 构建出前缀树以后，我们的工作变成：

 对于矩阵board中的每一个字符，从前缀树的root节点开始判断是否在矩阵中存在这个单词。
 加入现在从board[0][0] = 'o' 开始，我们发现root.children['z' - 'o']!= null，说明，以'o'开头的单词存在
 ，然后，我们接着以同样的方式对这个前缀树往下遍历，知道找到字符匹配并且TrieNode.word不为空，
 说明这个单词完整地找到了。


 */

public class WordSearchII {
    private class TrieNode{
        TrieNode[] children;
        String word;

        private TrieNode(){
            children = new TrieNode[26];
        }
    }
    public List<String> findWords(char[][] board, String[] words) {
        List<String> result = new LinkedList<>();
        TrieNode root = buildPreTree(words);
        for(int i=0;i<board.length;i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board,i,j,result,root);
            }
        }
        return result;
    }

    private void dfs(char[][] board,int i,int j,List<String> result,TrieNode current){

        if(board[i][j] == '#')
            return;
        char bak =  board[i][j];
        if(current.children['z' - bak ] == null)
            return;
        TrieNode child = current.children['z' - bak];

        if(child.word !=  null) {
            result.add(child.word);
            child.word = null; //这个单词找到，则置为null,这样避免board中还有这个单词的相同实现导致我们加入两个相同的单词到result中
        }
        board[i][j] = '#';
        if(i>0) dfs(board,i-1,j,result,child);
        if(i < board.length-1) dfs(board,i+1,j,result,child);
        if(j>0) dfs(board,i,j-1,result,child);
        if(j < board[0].length-1) dfs(board,i,j+1,result,child);
        board[i][j] = bak;
    }

    private TrieNode buildPreTree( String[] words){
        TrieNode root = new TrieNode();

        for(String word:words){
            TrieNode current = root;
            char[] chars = word.toCharArray();
            for(char c:chars){
                if(current.children['z' - c] == null)
                    current.children['z' - c] = new TrieNode();
                current = current.children['z' - c];
            }
            current.word = word;
        }
        return root;
    }

    public static void main(String[] args) {

        //char[][] board = {{'o','a','a','n'},{'e','t','a','e'},{'i','h','k','r'},{'i','f','l','v'}};
        //String[] words = {"oath","pea","eat","rain"};
        char[][] board = {{'a'},{'a'}};
        String[] words = {"a"};
        new WordSearchII().findWords(board,words);
    }

}