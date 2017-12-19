/**
 * Created by wuchang at 12/19/17
 * 这个算法比较简单，因此直接使用别人的算法，自己没有写代码。
 https://discuss.leetcode.com/topic/19221/ac-java-solution-simple-using-single-array/3
 这篇帖子的一楼的算法是基础的实现，没问题。二楼对一楼的算法进行了简化，总之，这样表示这棵树：
 1. 父亲节点使用一个长度为26的指针数组指向子节点，如果对应位置指针为null，说明元素不存在，如果不为空，说明元素存在：
 2. 每个节点不需要存放对应的具体字符了，因为每个节点用长度为26的数组分别代表a-z，因此，从父亲节点到孩子节点，
 通过位置就可以判断这个孩子节点中存放的字符，数组中第一个位置指向的孩子节点如果不为空肯定是字符a，第二个位置不为空肯定是字符b，以此类推。
 3. 每个TrieNode通过isWord标志符号标记这个前缀是否是一个完整的单词了。
 显然，例如，只要"ab"是一个完整的字符(有可能同时是其它单词的前缀)，isWord标记是true，当"ab"仅仅是其它单词的前缀，则isWord标记是false

 */

public class ImplementTrie {
    class TrieNode {
        public boolean isWord;
        public TrieNode[] children = new TrieNode[26];
        public TrieNode() {}
    }


    public class Trie {
        private TrieNode root;
        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            TrieNode ws = root;
            for(int i = 0; i < word.length(); i++){
                char c = word.charAt(i);
                if(ws.children[c - 'a'] == null){
                    ws.children[c - 'a'] = new TrieNode();
                }
                ws = ws.children[c - 'a'];
            }
            ws.isWord = true;
        }

        /**
         * 由于TrieNode中并没有存放当前的字符，实际上是用父亲节点指向这个节点的游标在游标数组children中的位置决定。
         * @param word
         * @return
         */
        public boolean search(String word) {
            TrieNode ws = root;
            for(int i = 0; i < word.length(); i++){
                char c = word.charAt(i);
                if(ws.children[c - 'a'] == null) return false;
                ws = ws.children[c - 'a'];
            }
            return ws.isWord;
        }

        public boolean startsWith(String prefix) {
            TrieNode ws = root;
            for(int i = 0; i < prefix.length(); i++){
                char c = prefix.charAt(i);
                if(ws.children[c - 'a'] == null) return false;
                ws = ws.children[c - 'a'];
            }
            return true;
        }
    }

    public static void main(String[] args) {
        ImplementTrie im = new ImplementTrie();
        Trie trie = im.new Trie();
        trie.insert("de");
        trie.insert("def");
        trie.insert("defg");
    }
}
