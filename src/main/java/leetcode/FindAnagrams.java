package leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Question 438
 * 我的解法其实跟https://leetcode.cn/problems/find-all-anagrams-in-a-string/solutions/1123971/zhao-dao-zi-fu-chuan-zhong-suo-you-zi-mu-xzin/的优化解法的思路一模一样
 * 用一个diff维护当前窗口中尚且不同的字符数量，当diff==0，说明窗口内所有字符相同。由于窗口每次向右多一个字符，向左小一个字符，因此我们完全可以在每次窗口右移一位的时候维护diff的值。
 * 延伸： https://leetcode.cn/problems/substring-with-concatenation-of-all-words/description/
 */
public class FindAnagrams {
    public List<Integer> findAnagrams(String s, String p) {
        Integer[] stat = new Integer[26]; // 记录p中每一个字符尚且空缺的次数，因此，stat[i]为正数n，表示当前窗口中字符i还缺n次，stat[i]为负数-n，表示当前窗口中字符i多出现了n次
        HashSet<Character> cont = new HashSet();
        Arrays.fill(stat, 0);
        List<Integer> res = new LinkedList();
        for(int i = 0;i<p.length();i++){
            stat[p.charAt(i) - 'a'] = stat[p.charAt(i) - 'a'] + 1;
            cont.add(p.charAt(i));
        }
        // 计算完p中所有字母的出现次数
        int left = 0; int right = 0;
        int diff = 0; // 当前窗口中数量不同的字母数量
        while(right < s.length() && right < p.length()){
            char rightC = s.charAt(right);
            if(cont.contains(rightC)){
                stat[rightC - 'a'] = stat[rightC - 'a'] - 1;
            }
            right++;
        }
        right = 0;
        while(right < stat.length){
            diff = (stat[right] != 0 ? (diff + 1) : diff);
            right++;
        }

        if(diff == 0){ //窗口内的所有字母的数量相同
            res.add(left);
        }
        for(;right + 1 <s.length();){
            char rightC = s.charAt(right + 1); // 右侧向右增加一个字母
            if(cont.contains(rightC)){ // 这是p中的字符
                stat[rightC - 'a'] = stat[rightC - 'a'] - 1;  // 空缺次数减少1
                if(stat[rightC - 'a'] == 0) // 从不同(1)到相同(0)，本来缺了一个，但是补上了
                    diff--;
                else if(stat[rightC - 'a'] == -1) // 从相同(0)到不同(-1)，即本来次数一样，现在多了一个
                    diff++;
            }
            char leftC = s.charAt(left); // 左侧向右减少一个字母
            if(cont.contains(leftC)){ // 这是p中的字符
                stat[leftC - 'a'] = stat[leftC - 'a'] + 1;
                if(stat[leftC - 'a'] == 0) // 从不同(-1)到相同(0)，本来多了一个，现在减少一个，想等了
                    diff--;
                else if(stat[leftC - 'a'] == 1)  // 从相同(0)到不同(1)，即本来次数一样，现在少了一个
                    diff++;
            }
            // 判断  所有p中的单词的出现次数都一样
            if(diff == 0){
                res.add(left);
            }
            left++;
            right++; //左边界也要右移
        }
        return res;
    }

    public static void main(String[] args) {
        new FindAnagrams().findAnagrams("cbaebabacd","abc");
    }
}
