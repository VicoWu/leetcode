package leetcode;

import java.util.*;

/**
 * Created by wuchang at 12/18/17
 * Question 472
 * # 我的递归动态规划实现
 首先对这些词按照长度从小到大进行排序，因为，如果单词A由单词B+C组成，那么B和C的长度肯定小于A，因此，如果对单词进行了排序，
 在计算每一个单词可能的组合时，只需要它前面的单词加入到字典中

 然后我们遍历单词列表中的每一个单词，对每一个单词使用动态规划的方式进行处理。
 处理的方法与[Word Bread II](https://leetcode.com/problems/word-break-ii/description/)相同：
 对每一个单词，先找到头部是一个字典词的位置，然后对尾部进行DP看是否是一个正确的组合，如果头部是一个字典词，尾部是一个正确的组合，则这个单词是一个合法的组合

 # 别人的非递归方法
 别人的非递归方式关于先对输入的数组进行排序的思想、使用比自己小的单词作为词典词的思想是一致的。不一样的是对每一个单词判断是否是正确地组合词的方式。
 对于一个单词word，自底向上依次判断word.subString(0,1) word.subString(0,2) 。。。是否是合法的组合词，最终得到word.subString(0,word.length)是不是一个合法的组合词。
 比如，对于单词abcdef，我们已经得到a是一个合法组合和，ab是非法组合 abc是合法组合，要判断abcd是合法还是非法组合，只有这样abcd才可能合法：
 如果a是合法的，那么bcd必须是词典中的词
 如果ab是合法的，那么cd必须是词典中的词，
 如果abc是合法的，那么d必须是词典中的词

 */

public class ConcatenatedWords {

    /**
     *
     * 这是别人的非递归方式
     * @param words
     * @return
     */
    public  List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> result = new ArrayList<>();
        Set<String> preWords = new HashSet<>();
        Arrays.sort(words, new Comparator<String>() {
            public int compare (String s1, String s2) {
                return s1.length() - s2.length();
            }
        });

        for (int i = 0; i < words.length; i++) {
            if (canForm(words[i], preWords)) {
                result.add(words[i]);
            }
            preWords.add(words[i]);
        }

        return result;
    }

    private  boolean canForm(String word, Set<String> dict) {
        if (dict.isEmpty()) return false;
        boolean[] dp = new boolean[word.length() + 1];
        dp[0] = true;//dp[j]存放了word.subString(0,j)这个单词是否是一个成功的组合词
        for (int i = 1; i <= word.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (!dp[j]) continue;
                if (dict.contains(word.substring(j, i))) { //如果word.subString(0,j)是一个已经证明的正确的组合，那么只有当word.substring(j, i)在词典中，才可能正确
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[word.length()];
    }


    /**
     * 这是我的通过动态规划的方式的实现
     * @param words
     * @return
     */
    public  List<String> findAllConcatenatedWordsInADictByDP(String[] words) {
        List<String> result = new ArrayList<>();
        Set<String> dict = new HashSet<>();
        Arrays.sort(words, new Comparator<String>() {
            public int compare (String s1, String s2) {
                return s1.length() - s2.length();
            }
        });

        Set<String> cache = new HashSet<String>(); //这里的缓存存放了全局的拼接成功的单词，这样，如果其他单词遇到了它，就不需要再进行计算了
        for (int i = 0; i < words.length; i++) {
            if (findByDP(words[i],dict,cache)) {
                result.add(words[i]);
            }
            dict.add(words[i]);
        }

        return result;
    }


    /**
     * 对于每一个单词进行一次动态规划，dict中存放了排序以后在它前面的单词，即长度比他小的单词，cache中存放了全局遇到的已经成功进行了拼接组合的单词，避免重复计算
     * @param word
     * @param dict
     * @param cache
     * @return
     */
    private boolean findByDP(String word,Set<String> dict,Set<String> cache){
        if(cache.contains(word) || dict.contains(word)) return true;
        for(int i=1;i<word.length();i++){

            String head = word.substring(0,i);
            if(dict.contains(head)  || cache.contains(head)){
                String tail = word.substring(i);
                if(findByDP(tail,dict,cache)){
                    cache.add(tail);
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String[] candidates = {"cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"};
        //new ConcatenatedWords().findAllConcatenatedWordsInADict(candidates);
        new ConcatenatedWords().findAllConcatenatedWordsInADictByDP(candidates);
    }
}

