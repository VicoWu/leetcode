package leetcode;

/**
 * Created by wuchang at 12/21/17
 * Question 214
 * # 递归方法
 如果是回文串，那么，我们从左侧往右侧遍历和从右侧往左侧遍历应该得到一样的遍历结果。
 因此我们使用两个游标，i从右往左遍历，j从左往右遍历，如果发现A[j] == A[i]，j就向右移动一个，代表j左侧的已经在i的遍历过程中找到：
 ```
 比如:adcba
 a  d  c  b  a
 j  -  -  -  i  A[j] == A[i]  因此j++，i--
 -  j  -  i  - 不匹配，i--
 -  j  i  -  -  不匹配 i--
 - j(i) -  -  -         不匹配 i--
 i  -  j  -  -

 ```
 由于j=2，字符串被分成ad和cba， cba肯定是在最短回文串的外面的，因此，最终结果肯定以`reverse(cba)=abc`开头，于是，我们递归调用，`abc+shortestPalindrome(ad)+abc`，得到最终结果
 `abcaddaabc`
 # 使用KMP算法
 关于KMP算法的最详细的解释在[这里](http://blog.csdn.net/v_july_v/article/details/7041827)和[这里](https://www.cnblogs.com/yjiyjige/p/3263858.html)
 KMP算法主要用在通过O(m+n)的时间复杂度确定一个字符串是不是另外一个字符串的子串，看leetcode的[Implement strStr()](https://leetcode.com/problems/implement-strstr/)
 我自己实现了KMP算法。
 这一题的KMP算法的基本思想是：
 加入输入abacdfg，我们通过处理变成abacdfg#gfdcaba ，显然，前面的回文串abc经过逆序以后变成







 */

public class ShortestPalindrome {

    /**
     * 这是使用KMP的解法
     * @param s
     * @return
     */
    public String shortestPalindrome1(String s) {
        String temp = s + "#" + new StringBuilder(s).reverse().toString();
        int[] table = getKMPTable(temp);
        return new StringBuilder(s.substring(table[table.length - 1])).reverse().toString() + s;
    }


    public String shortestPalindrome(String s) {
        int j = 0;
        for (int i = s.length() - 1; i >= 0; i--) {

            if (s.charAt(i) == s.charAt(j)) {
                System.out.println("j:"+j+",i:"+i);
                j += 1; }

        }
        if (j == s.length()) { return s; }
        String suffix = s.substring(j);
        return new StringBuffer(suffix).reverse().toString() + shortestPalindrome(s.substring(0, j)) + suffix;
    }

    public static void main(String[] args) {
        System.out.println(new ShortestPalindrome().shortestPalindrome("abaab"));
       // System.out.println(new ShortestPalindrome().shortestPalindrome1("abab"));
        //System.out.println(new ShortestPalindrome().getKMPTable("aabaaac"));
//        System.out.println(new ShortestPalindrome().myKMP("","aabaaac"));
//        System.out.println(new ShortestPalindrome().myKMP("aaaaa","bba"));d
    }


    public int[] getKMPTable(String s){
        //get lookup table
        int[] table = new int[s.length()];

        //pointer that points to matched char in prefix part

        int index = 0;
        //skip index 0, we will not match a string with itself
        for(int i = 1; i < s.length(); i++){
            if(s.charAt(index) == s.charAt(i)){
                //we can extend match in prefix and postfix
                table[i] = table[i-1] + 1;
                index ++;
            }else{
                //match failed, we try to match a shorter substring

                //by assigning index to table[i-1], we will shorten the match string length, and jump to the
                //prefix part that we used to match postfix ended at i - 1
                index = table[i-1];

                while(index > 0 && s.charAt(index) != s.charAt(i)){
                    //we will try to shorten the match string length until we revert to the beginning of match (index 1)
                    index = table[index-1];
                }

                //when we are here may either found a match char or we reach the boundary and still no luck
                //so we need check char match
                if(s.charAt(index) == s.charAt(i)){
                    //if match, then extend one char
                    index ++ ;
                }

                table[i] = index;
            }

        }

        return table;
    }




}
