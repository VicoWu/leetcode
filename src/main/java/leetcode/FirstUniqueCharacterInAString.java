package leetcode;

/**
 * Created by wuchang at 2/28/18
 * Question  387
 解法比较简单，参考[这里](https://leetcode.com/problems/first-unique-character-in-a-string/discuss/86348/Java-7-lines-solution-29ms)
 区别于newcoder中的剑指offer的题目.[字符流中第一个不重复的字符](https://www.nowcoder.com/practice/00de97733b8e4f97a3fb5c680ee10720?tpId=13&tqId=11207&tPage=3&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)
 */

public class FirstUniqueCharacterInAString {
    public int firstUniqChar(String s) {
        int freq [] = new int[26];
        for(int i = 0; i < s.length(); i ++)
            freq [s.charAt(i) - 'a'] ++;
        for(int i = 0; i < s.length(); i ++)
            if(freq [s.charAt(i) - 'a'] == 1)
                return i;
        return -1;
    }
}
