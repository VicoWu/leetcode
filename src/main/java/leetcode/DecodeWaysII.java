package leetcode;

/**
 * Created by wuchang at 1/2/18
 * Question 639
 * 这道题是[Decode Ways](https://leetcode.com/problems/decode-ways/description/)的升级版
 主要区别是对于\*的处理,分以下几种情况：
 - 如果是连续两个\*，即\*\*，那么有`result[i] = 9 * result[i+1] + 15 * result[i+23]` ，第一个\*独立成为字符，有9中可能，两个\*可以形成15个单一的合法字符，包括11-19,21-26；
 - 如果是一个\*，后面是一个数字，那么`result[i] = 9 * result[i+1] + s.charAt(i+1) <='6'?2:1) * result[i+2]`，即要看第二个字符是<=6还是大于6，如果小于等于6，有两种可能，比如14,24, 如果大于6，只有一种，比如17，18
 - 如果当前是一个正常数字，但是数字后面是一个\*，那么`result[i] = result[i+1] +(s.charAt(i)=='1'  ?9 :(s.charAt(i)=='2'?6:0) )  * result[i+2]`
 - 如果都是正常数字，则 `result[i] = result[i + 1]+(Integer.valueOf(s.substring(i, i + 2)) <= 26 ? result[i + 2] : 0)	`

 */

public class DecodeWaysII {
    /**
     * 这是我通过动态规划的方法的实现
     * @param s
     * @return
     */
    public int numDecodings(String s) {

        if(s==null || s.length()==0)
            return 0;
        long[] result = new long[s.length()+1];
        result[s.length()] = 1L;
        result[s.length()-1] = s.charAt(s.length()-1)=='*'
                ?9
                :(s.charAt(s.length()-1)=='0'?0:1);
        for(int i=s.length()-2;i>=0;i--){
            if(s.charAt(i)=='0') //如果这个字符是0，则合法解码数是0
                continue;
            if(s.charAt(i) == '*'){
                result[i] += 9 * result[i+1];//第一个字符独立成为字符时候的合法解码数
                if(s.charAt(i+1) == '*')  //如果第二个字符是*好
                    result[i] += 15 * result[i+2]; //两个*可以形成15个合法字符，包括11-19,21-26
                else
                    result[i] += (s.charAt(i+1) <='6'?2:1) * result[i+2]; //如果第一个字符是*，第二个字符不是*，那么要看第二个字符是<=6还是大于6，如果小于等于6，有两种可能，比如14,24, 如果大于6，只有一种，比如17，18
            }
            else{ //s.charAt[i]是正常1-9的数字
               if(s.charAt(i+1) == '*'){ //
                   result[i]+=result[i+1];
                   result[i]+=(s.charAt(i)=='1' //如果当前字符是1，则1*有9中可能
                           ?9
                           :(s.charAt(i)=='2'?6:0) )  * result[i+2]; //如果当前字符是2，则2*有6中可能，如果大于2，比如3*，这两位肯定无法映射到某一个字符
               }
               else //s.charAt[i]和s.charAt[]i+1都不是'*'
               {
                   result[i] += result[i + 1];
                   result[i] += (Integer.valueOf(s.substring(i, i + 2)) <= 26 ? result[i + 2] : 0);
               }

            }
            result[i]  = (long) (result[i] % (Math.pow(10,9)+7));//按照题目要求取余数
        }
        return (int)(result[0] % (Math.pow(10,9)+7)) ;
    }



    public static void main(String[] args) {
//        int a = 10 * 10 * 10 * 10 * 10 * 10 * 10 * 10 * 10;


        System.out.println(1000000005L * 3 * 5 %1000000007L);
        System.out.println(1000000005L * 3 % 1000000007 * 5 %1000000007L);
//         System.out.println(new DecodeWaysII().numDecodings("********************"));
//        System.out.println(new DecodeWaysII().numDecodings2("********************"));

    }
}
