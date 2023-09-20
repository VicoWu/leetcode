package leetcode;

import java.util.*;

/**
 * Question 30
 参考 https://leetcode.cn/problems/find-all-anagrams-in-a-string/description/
 */
public class FindSubstring {


public List<Integer> findSubstring(String s, String[] words) {
    List<Integer> res = new ArrayList();
    Map<String,Integer> statI = new HashMap<String,Integer>();
    int diffI = 0;
    for(String word: words){
        if(!statI.containsKey(word) ){
            diffI++;
            statI.put(word, 1);
        }else{
            statI.put(word, statI.get(word) + 1);
        }
    }
    int m = words.length; int n = words[0].length();
    for(int i = 0;i<n;i++){ // n种切分规则
        Map<String,Integer> stat = new HashMap(statI);
        int diff = diffI; // 不同的单词的数量，注意，这里不是单词出现的总次数
        for(int j = 0;j<m && (i + m * n) <= s.length();j++){ // 先计算好第一轮窗口，保证窗口右边界没有越界
            String word = s.substring(i + j * n, i + j * n + n);
            if(stat.containsKey(word)){
                stat.put(word, stat.get(word) - 1);
            }
        }
        for(String word: stat.keySet()){ // 看看是否每个word都已经全部清零了
            if(stat.get(word) == 0){ //统计下有多少个单词的数量是不一致的
                diff--;
            }
        }
        if(diff == 0){ //全部清零了
            res.add(i);
        }
        int left = i; int right = i + m * n - 1; // 左闭右闭,包含了m*n个元素
        for(;right + n < s.length();){ // right < s.length() - 1 保证可以向右移动一个n
            String rightWord = s.substring(right+1, right + 1 + n); // 注意substring()是左闭右开
            if(stat.containsKey(rightWord)){
                stat.put(rightWord, stat.get(rightWord) - 1);
                if(stat.get(rightWord) == 0){
                    diff--;
                }else if(stat.get(rightWord) == -1){
                    diff++;
                }
            }
            String leftWord = s.substring(left, left + n); // 注意substring()是左闭右开
            if(stat.containsKey(leftWord)){
                stat.put(leftWord, stat.get(leftWord) + 1);
                if(stat.get(leftWord) == 0){
                    diff--;
                }else if(stat.get(leftWord) == 1){
                    diff++;
                }
            }
            if(diff == 0){ //全部清零了,记录结果
                res.add(left + n);
            }
            left += n;
            right += n;
        }
    }
    return res;
}

    public static void main(String[] args) {
        String[] p = {"fooo","barr","wing","ding","wing"};
        new FindSubstring().findSubstring("lingmindraboofooowingdingbarrwingmonkeypoundcake", p);
    }
}
