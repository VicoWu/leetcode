package leetcode;

import java.util.*;

/**
 * Question 127
 * Created by wuchang at 12/15/17
 */

public class WordLadder {

    /**
     * 通过广度优先遍历的方法进行搜索的时候，如果endWord和beginWord之间的距离很远，那么整个算法的搜索次数会成指数级的增长
     * 为了优化，假如beginWord在左，endWord在右，因此左右都进行搜索，如果在中间遇到了同一个单词，则说明连接成功
     * 在这里，同时并不是是多线程并行搜索，而是每次从beginSet和endSet中选择较短的一个进行搜索，这样可以保证尽量在中间交汇
     * @param beginWord
     * @param endWord
     * @param wordSet
     * @return
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordSet) {
        Set<String> beginSet = new HashSet<String>(), endSet = new HashSet<String>();

        Set<String> wordList = new HashSet<String>();
        for(String d:wordSet)
            wordList.add(d);

        int len = 1;//len保存了左右两侧搜索的长度之和，当交汇成功，那么beginWord和endWord之间的距离就是len+1
        int strLen = beginWord.length();
        HashSet<String> visited = new HashSet<String>();

        beginSet.add(beginWord);
        endSet.add(endWord);
        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            if (beginSet.size() > endSet.size()) { //交换beginSet 和 endSet ,保持两个集合的平衡
                Set<String> set = beginSet;
                beginSet = endSet;
                endSet = set;
            }

            Set<String> temp = new HashSet<String>();
            for (String word : beginSet) {
                char[] chs = word.toCharArray();

                for (int i = 0; i < chs.length; i++) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        char old = chs[i];
                        chs[i] = c;
                        String target = String.valueOf(chs);

                        if (endSet.contains(target)) { //如果在start和end中遇到了相同的词，那么就可以结束了
                            return len + 1;
                        }

                        if (!visited.contains(target) && wordList.contains(target)) { //如果这个单词在wordList中存在，并且是我们没有访问过的单词
                            temp.add(target);
                            visited.add(target); //添加到已访问的列表中
                        }
                        chs[i] = old; //恢复刚才修改的字符
                    }
                }
            }

            beginSet = temp;
            len++;
        }

        return 0;
    }


    /**
     * 这是leetcode上面的解法
     * 使用先进先出队列，采用宽度优先的分层遍历方法，获取最短路径
     * https://discuss.leetcode.com/topic/17890/another-accepted-java-solution-bfs/8
     * 算法的思想是宽度优先遍历
     * @param b
     * @param e
     * @param dict
     * @return
     */
    public int ladderLength1(String b, String e, List<String> dic) {
        if(b.equals(e)) return 1;

        Set<String> dict = new HashSet<String>();
        for(String d:dic)
            dict.add(d);
        Queue<String> q = new LinkedList<String>();
        q.add(b);
        dict.remove(b);

        int level=2;

        while(!q.isEmpty()) {
            int sz = q.size();

            for(int i=0; i<sz; i++) {
                String tmp = q.poll();

                for(int j=0; j<tmp.length(); j++) {
                    char[] chars = tmp.toCharArray();

                    for(char c='a'; c<='z'; c++) {
                        chars[j] = c;
                        String tmp2 = new String(chars);

                        if(tmp2.equals(e))
                            return level;

                        if(dict.remove(tmp2)) {
                            q.add(tmp2);
                        }
                    }
                }
            }

            level++;
        }

        return 0;
    }


    /**
     * 使用我使用迪杰斯特拉最短路径算法的求解方式
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public int ladderLength2(String beginWord, String endWord, List<String> wordList) {

        Set<String> S = new HashSet<String>();
        Set<String> U = new HashSet<String>();

        Integer[] result= new Integer[wordList.size()];

        String[] wordArray = new String[wordList.size()];
        int endWordIndex = -1;
        for(int i=0;i<result.length;i++){
            String word = wordList.get(i);

            result[i] = getDistance(beginWord,word);
            wordArray[i] = word;
            if(word.equals(endWord))
                endWordIndex = i;
        }

        if(endWordIndex == -1)
            return 0;

        for(int i=0;i<wordArray.length;i++){

            Integer minLength = null;
            int minIndex = -1;
            for(int j=0;j<wordArray.length;j++){

                String word = wordArray[j];
                if(S.contains(word))
                    continue;
                Integer length = result[j];
                if(biggerOrEqual(minLength,length))
                {
                    minLength = length;
                    minIndex = j;
                }
            }

            String v = wordArray[minIndex];
            S.add(v); U.remove(v);
            for(int j=0;j<result.length;j++){
                String word = wordArray[j];
                Integer updatedLength = add(minLength,getDistance(v,word));
                if(biggerOrEqual(result[j] , updatedLength))
                    result[j] =updatedLength;
            }

        }

        return result[endWordIndex] == null ? 0: result[endWordIndex]+1;
    }

    private Integer getDistance(String a,String b){
        int dis = 0;
        for(int i=0;i<a.length();i++){
            if(a.charAt(i)== b.charAt(i))
                continue;
            if(dis++ == 1) return null;
        }
        return dis;
    }

    private boolean biggerOrEqual(Integer a, Integer b) {
        if (a == null)
            return true;
        else if (b == null)
            return false;
        else return a >= b;
    }

    private Integer add(Integer a,Integer b){
        if(a==null||b==null)
            return null;
        return a+b;
    }

    public static void main(String[] args) {
        List<String> wordList = new LinkedList<String>();
        wordList.add("hot");wordList.add("dot");wordList.add("dog");wordList.add("lot");wordList.add("log");wordList.add("cog");
       // wordList.add("a");wordList.add("b");wordList.add("c");
       System.out.println(new WordLadder() .ladderLength("hit","cog",wordList));
    }
}
