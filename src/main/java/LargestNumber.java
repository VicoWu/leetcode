import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by wuchang at 1/11/18
 * Question 179
 *
 显然，这一题是对字符串进行排序的题目：
 最一般的情况，如果一个整数是45，一个整数是5，显然，通过比较字符串，"5" >  "45"。但是，这种直接根据字符串的比较是不是就可以了？否！因为我们还需要考虑一种特殊情况，一个字符串是另外一个字符串的前缀的问题。
 加入nums={12,121}，如果我们直接比较字符串，"12" < "121"，因此得到的结果是"12112"，但是，实际结果应该是"12" > "121"，从而得到的结果是"12121"，即这种情况下较短的串应该排在前面，而假如输入是nums={12,123}，这时候有应该是较长的字符串排在前面,即"12312" > "12123"，那么这种结果是什么原因导致的呢？
 ```
 abcdefg
 abcd
 ```
 到底哪个应该排在前面呢？我们只需要对两个字符串进行拼接以后比较就行：
 ```
 abcdeftabcd 长+短
 abcdabcdefg 短+长
 ```
 至于原因，主要是跟较长串除去与较短串的相同前缀以后与较短的串之间的大小关系。

 */

public class LargestNumber {
    public String largestNumber(int[] num) {
        if(num == null || num.length == 0)
            return "";

        // Convert int array to String array, so we can sort later on
        Integer[] s_num = new Integer[num.length];
        for(int i = 0; i < num.length; i++)
            s_num[i] = num[i];

        // Comparator to decide which string should come first in concatenation
        Comparator<Integer> comp = new Comparator<Integer>(){
            @Override
            public int compare(Integer i1, Integer i2){
                String s1 = i1.toString();
                String s2 = i2.toString();
                return (s2+s1).compareTo(s1+s2); // reverse order here, so we can do append() later
            }
        };

        Arrays.sort(s_num, comp);
        // An extreme edge case by lc, say you have only a bunch of 0 in your int array
        if(s_num[0] == 0)
            return "0";
//        if(s_num[0].charAt(0) == '0')
//            return "0";

        StringBuilder sb = new StringBuilder();
        for(Integer s: s_num)
            sb.append(s);

        return sb.toString();

    }

}
