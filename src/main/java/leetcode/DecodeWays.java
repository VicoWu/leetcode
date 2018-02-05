package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuchang at 1/2/18
 * Question 91
 * # 我的递归方法
 我的递归方法是属于从上到下的计算方法。
 ```
 对于输入字符串s，它对应的解码方式有多少种，是这样递归形成的：
 - 如果s[0]是一个合法的字符，比如'a'，那么递归计算s.subString(1)对应的编码方案，假如数量为a
 - 如果s.subString[0,2]是一个合法的字符，比如'z'，那么，递归计算s.subString(2)对应的编码方案，假如数量为b
 那么，s的编码方案数量就是a+b
 我们这样递归进行。
 同时，由于这种递归肯定会出现大量的重复计算，因此我们使用cache保存中间结果
 ```
 # 别人的动态规划方法
 别人的动态规划方式属于从下向上的方法，因此不存在重复计算的问题

 */

public class DecodeWays {

    /**
     * 这是我的递归动态规划方法
     * @param s
     * @return
     */
    public int numDecodings(String s) {
        Map<String,Integer> cache  = new HashMap<String,Integer>();
        if(s==null||s.length()==0)
            return 0;
        return decode(s,0,cache);
    }

    public int decode(String s, int i, Map<String, Integer> cache) {

        if (i>=s.length()) //这里i>=s.length代表已经解析结束了
            return 1;
        if (i == s.length()-1)
            return s.charAt(i) != '0' ? 1 : 0;
        if (cache.containsKey(s.substring(i))){ //判断缓存是否已经存在
            return cache.get(s.substring(i));
        }
        else {
            int a = 0;
            int b = 0;
            if (s.charAt(i) != '0') //判断第一位是不是有效的编码
                a = decode(s, i + 1 ,cache);
            if (s.charAt(i) == '1' || s.charAt(i) == '2' && s.charAt(i + 1) <= '6') //判断前两位是不是合起来是某个字符的有效编码
                b = decode(s, i + 2, cache);
            cache.put(s.substring(i), a + b);
            return a + b;
        }
    }


    /**
     * 这是别人的动态规划算法
     * @param s
     * @return
     */
    public int numDecodingsByDP(String s) {
        int n = s.length();
        if (n == 0) return 0;

        int[] memo = new int[n+1];//使用长度为n+1的数组保存中间结果，从下而上计算。momo[0]代表了s是结果，memo[1]存放了s.subString(1) 的结果,memo[s.length-1]存放了s.subString(s.length()-1) 的结果
        memo[n]  = 1; //memo代表s最后的空字符串的编码
        memo[n-1] = s.charAt(n-1) != '0' ? 1 : 0; //初始化最后一个字符对应的编码数量，如果最后一个字符是'0'则编码数量是0，否则编码数量是1

        for (int i = n - 2; i >= 0; i--)
            if (s.charAt(i) == '0') continue;  //如果对应的位置字符是0，则没有有效的编码
            else memo[i] = (Integer.parseInt(s.substring(i,i+2))<=26)
                    ? memo[i+1]+memo[i+2]
                    : memo[i+1];

        return memo[0];
    }


    public static void main(String[] args) {
        System.out.println(new DecodeWays().numDecodings("9371597631128776948387197132267188677349946742344217846154932859125134924241649584251978418763151253")) ;
    }


}
