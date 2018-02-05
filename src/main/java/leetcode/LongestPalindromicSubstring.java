package leetcode;

public class LongestPalindromicSubstring {

    public String longestPalindrome(String s) {
        int start=0;int end = 0;

        int length = s.length();
        boolean[][] result = new boolean[length][length];

        boolean current = false;
        //计算base case，即初始化result[i][i]和result[i][j]
        for(int i=0;i<length;i++){
            result[i][i] = true;
            if(i+1 <= length-1) {
                current =  result[i][i + 1] = (s.charAt(i) == s.charAt(i + 1)) ? true : false;
                if(current) {
                    start = i;
                    end = i + 1;
                }
            }

        }


        //开始通过DP进行递归计算
        for(int slice=2;slice < length;slice++){
            for(int i=0;i<length-slice;i++){
                current = result[i][i+slice] = result[i+1][i+slice-1] && (s.charAt(i) == s.charAt(i+slice));
                if( current && (slice > (end - start))){ //找到一个回文
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
