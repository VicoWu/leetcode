/**
 * Question 10
 */

import java.util.*;
import java.util.regex.*;

public class RegularExpressionMatching {
    public boolean isMatch(String s, String p) {
//        if(p.isEmpty()) return s.isEmpty();
//
//        boolean headMatched = !s.isEmpty() && ((s.charAt(0) == p.charAt(0))||p.charAt(0) == '.');
//
//        if(!headMatched ) return false;
//        if(p.charAt(1) == '*'){
//            return isMatch(s,p.substring(2)) || (headMatched && isMatch(s.substring(1),p);
//        }
//        else{
//            return headMatched&&isMatch(s.substring(1),p.substring(1));
//        }
        return true;
    }


    public boolean bottomToUp(String s, String p){
        boolean[][] result = new boolean[s.length()+1][p.length()+1];
        result[s.length()][p.length()] = true;
        for(int i=0;i<s.length();i++){
            result[i][p.length()] = false;
        }

        boolean matchEmpty = p.isEmpty();

        if(p.length() == 1)
            matchEmpty = false;
        for(int i=0;i<=p.length()-1;i++){
            if(p.charAt(i)!= '*' && p.charAt(i+1)!= '*')
            {
                matchEmpty = false;
            }
        }

        for(int j=0;j<p.length();j++){
            result[s.length()][j] = matchEmpty;
        }


        for(int i=s.length()-1;i>=0;i--){
            for(int j=p.length()-1;j>=0;j--){

                boolean headMatched = s.charAt(i) == p.charAt(j) || p.charAt(j) == '.';
                if(j< p.length()-1 && p.charAt(j+1) == '*'){
                    result[i][j] = result[i][j+2] || headMatched && result[i+1][j];
                }
                else
                {
                    result[i][j] = headMatched && result[i+1][j+1];
                }
            }
        }

        return result[0][0];
    }



    private Boolean[][] result =null;
    public boolean upToBottomDP(String s, String p){

        result = new Boolean[s.length()+1][p.length()+1];

        result[s.length()][p.length()] = true;
        return dp(0,0,s,p);
    }

    public Boolean dp(int i, int j, String s, String p) {
        if (result[i][j] != null)
            return result[i][j];

        if (j == p.length()) {
            result[i][j] = i == s.length() ? true : false;
            return result[i][j];
        }

        boolean matchedHead = (i<s.length())&& (s.charAt(i) == p.charAt(j)  || p.charAt(j) == '.');
        if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
            result[i][j] = matchedHead && dp(i + 1, j, s, p) || dp(i, j + 2, s, p);
        } else
            result[i][j] = matchedHead && dp(i + 1, j + 1, s, p);
        return result[i][j];

    }


    /**
     * Dynamic Programming
     *
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch3(String s, String p) {

        //处理p="a*"类似的情况
        if((p.charAt(0)!= '.' && p.charAt(0)!= '*') && p.charAt(1) == '*'){
            if(s.charAt(0) == p.charAt(0))
                return isMatch(s.substring(1),p);
            else
                return isMatch(s,p.substring(2));
        }

        //处理p=".adb"类似的情况
        if(p.startsWith(".")){
            if(s.length() >= 1)
                return isMatch(s.substring(1),p.substring(1));
            else
                return false;
        }


        if(p.startsWith("."))
        if(p==null||p.equals("")){
            if(s!=null && !s.equals(""))
                return false;
        }


        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) != '*' && p.charAt(i) != '.' && (i==p.length()-1 || p.charAt(i+1) != '*')) { //从pattern中找到了一个确定字符串,并且不是a*的形式
                for (int j = 0; j < s.length(); j++) {
                    if (s.charAt(j) == p.charAt(i)) { //找到固定字符串匹配

                        boolean matched = isMatch(s.substring(0, j), p.substring(0, i)) //前半部分是否匹配
                                && isMatch(s.substring(j+1, s.length()), p.substring(i+1, p.length())); //后半部分是否匹配
                        if (matched)
                            return true; //如果已经确定匹配，则不需要再继续了
                        else continue; //如果s中的确定串和pattern中的一致，但是没有匹配成功，则需要继续在s中检查是或否还有更多的该确定串
                    }
                }
                return false;//对于pattern中的确定字符串，在s中没有找到匹配，直接返回false
            }
        }
        //一个确定字符串都没有找到，剩下一些不确定字符串的情况
        //剩下几种特殊情况，需要匹配 ".*"  ".."  "**"  "*." "" 的五种情况




        int dotCount = 0;
        for(int i=0;i<p.length();i++){
            if(p.charAt(i) == '.')
                dotCount++;
        }

        if(dotCount == 0) //全是*，没有.
            return true;
        else if(dotCount <= s.length()){ //既有* ，也有.

            return true;
        }

        else return false;

    }

    public boolean isMatch2(String text, String pattern) {
        if (pattern.isEmpty()) return text.isEmpty();
        boolean first_match = (!text.isEmpty() &&
                (pattern.charAt(0) == text.charAt(0) || pattern.charAt(0) == '.'));

        if (pattern.length() >= 2 && pattern.charAt(1) == '*'){
            return (isMatch(text, pattern.substring(2)) ||
                    (first_match && isMatch(text.substring(1), pattern)));
        } else {
            return first_match && isMatch(text.substring(1), pattern.substring(1));
        }
    }

    enum Result {
        TRUE, FALSE
    }

    Result[][] memo;

    public boolean isMatch4(String text, String pattern) {
        memo = new Result[text.length() + 1][pattern.length() + 1];
        return dp4(0, 0, text, pattern);
    }

    public boolean dp4(int i, int j, String text, String pattern) {
        if (memo[i][j] != null) {
            return memo[i][j] == Result.TRUE;
        }
        boolean ans;
        if (j == pattern.length()){
            ans = i == text.length();
        } else{
            boolean first_match = (i < text.length() &&
                    (pattern.charAt(j) == text.charAt(i) ||
                            pattern.charAt(j) == '.'));

            if (j + 1 < pattern.length() && pattern.charAt(j+1) == '*'){
                ans = (dp(i, j+2, text, pattern) ||
                        first_match && dp4(i+1, j, text, pattern));
            } else {
                ans = first_match && dp4(i+1, j+1, text, pattern);
            }
        }
        memo[i][j] = ans ? Result.TRUE : Result.FALSE;
        return ans;
    }


    public static void main(String[] args) {

        String a = "12";
        //System.out.println(a.charAt(2));
        //System.out.println(a.substring(3));
        //Pattern p = Pattern.compile("c*a*b");
        //System.out.println(p.matcher("aab").matches());
       //System.out.println(new RegularExpressionMatching().isMatch("aaa","a.a"));
        System.out.println(new RegularExpressionMatching().upToBottomDP("a","a.*"));
    }
}
