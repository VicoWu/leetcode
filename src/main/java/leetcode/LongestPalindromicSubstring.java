package leetcode;

/**
 * Question 5
 *
 * 这一题如果使用动态规划，动态规划的基本规则是，如果s[i,j]是一个回文字符串，那么s[i-1,j+1] 是回文串，当且仅当s[i-1] == s[j+1]
 * 这一题如果使用中心扩展方法，反而比较简单理解
 */
public class LongestPalindromicSubstring {


    public String longestPalindrome(String s) {
        int start=0;int end = 0;

        int length = s.length();
        //result[i][j]的值是一个boolean， 代表s[i, j]的这个字符串是不是回文串
        // 通过动态规划的方式，不断填充result
        boolean[][] result = new boolean[length][length];

        boolean current = false;
        //计算base case，即初始化result[i][i]和result[i][j]
        for(int i=0;i<length;i++){
            result[i][i] = true; // 所有长度为1的字符串都肯定是回文字符串
            if(i+1 <= length-1) {
                //对于长度为2 的字符串，判断它是不是回文字符串
                current =  result[i][i + 1] = (s.charAt(i) == s.charAt(i + 1)) ? true : false;
                if(current) { // 尽管长度为2，但是还是进行记录
                    start = i;
                    end = i + 1;
                }
            }

        }


        // 通过dp , 不断填充二维数组result 的剩下的值
        //开始通过DP进行递归计算
        for(int slice=2;slice < length;slice++){
            for(int i=0;i<length-slice;i++){
                current = result[i][i+slice] = result[i+1][i+slice-1] && (s.charAt(i) == s.charAt(i+slice));
                if( current && (slice > (end - start))){ //找到一个回文， 并且这个回文的长度更长
                    start = i;
                    end = i+slice;
                }

            }
        }

        return s.substring(start,end+1);
    }


    public String longestPalindrome1(String s) {
        long startTime = System.currentTimeMillis();

        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        long endTime = System.currentTimeMillis();

        System.out.println("time consuming1 "+(endTime-startTime)+" for input "+s);
        return s.substring(start, end + 1);
    }

    private int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }



    public static void main(String[] args){

        String param = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabcaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        System.out.println(new LongestPalindromicSubstring().longestPalindrome(param));
        System.out.println(new LongestPalindromicSubstring().longestPalindrome1(param));
        System.out.println(new LongestPalindromicSubstring().longestPalindrome(param));
        System.out.println(new LongestPalindromicSubstring().longestPalindrome1(param));
        System.out.println(new LongestPalindromicSubstring().longestPalindrome(param));
        System.out.println(new LongestPalindromicSubstring().longestPalindrome1(param));
        System.out.println(new LongestPalindromicSubstring().longestPalindrome(param));
        System.out.println(new LongestPalindromicSubstring().longestPalindrome1(param));
        System.out.println(new LongestPalindromicSubstring().longestPalindrome(param));
        System.out.println(new LongestPalindromicSubstring().longestPalindrome1(param));
        System.out.println(new LongestPalindromicSubstring().longestPalindrome(param));
        System.out.println(new LongestPalindromicSubstring().longestPalindrome1(param));
        System.out.println(new LongestPalindromicSubstring().longestPalindrome(param));
        System.out.println(new LongestPalindromicSubstring().longestPalindrome1(param));
        System.out.println(new LongestPalindromicSubstring().longestPalindrome(param));
        System.out.println(new LongestPalindromicSubstring().longestPalindrome1(param));
    }
}
