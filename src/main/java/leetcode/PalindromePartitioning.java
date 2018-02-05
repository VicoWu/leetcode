package leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuchang at 1/3/18
 * Question 131
 *
 * 这一题同样是递归和回溯方法的典型应用。 [这里](https://leetcode.com/problems/permutations/discuss/18239)介绍了各种排列问题通过回溯方法进行解答的方案；
 其中，每一个字符本身都肯定是回文的，因此，任何一个字符串肯定都可以拆分成一个字符一个字符组成的回文组合，比如"aab" -> ["a","a","b"]

 对于输入"aab"，我们需要判断a,aa ,aab是不是回文串，如果是，则再判断剩余的部分是不是回文串。如果不是 ，后面的剩余串就无需再继续判断了。
 比如"aab"，由于a是回文，因此我们再判断剩余的'ab'是不是，'ab'可以形成[a,b]的回文拆分，因为"aab"就可以形成["a","a","b"]的拆分。由于"aa"是回文的，我们需要判断剩余的"b"是不是回文的，"b"可以形成[b]的回文，因此可以形成["aa","b"]的回文。最后，对于"aab"就形成了
 `[["aa","b"],["a","a","b"]]`的最终结果。

 */

public class PalindromePartitioning {
    public List<List<String>> partition(String s) {

        List<List<String>> result  = new LinkedList<>();
        partition(s,0,result,new LinkedList<>());
        return result;
    }


    public void partition(String s,int lo,List<List<String>>  result,List<String> current){
        if(lo == s.length())
            result.add(new LinkedList<>(current)); //发现回文
        for(int i=lo;i<s.length();i++){
            if(isPalindrome(s,lo,i)){ //前半部分是否是回文串，如果是，才需要在后半部分进行递归，如果不是，则后半部分也没必要考虑了
                current.add(s.substring(lo,i+1));
                partition(s,i+1,result,current);//后半部分进行递归
                current.remove(current.size() -1);//准备退栈回溯，删除刚刚添加进来的元素
            }
        }
    }

    public boolean isPalindrome(String s, int lo,int ri){
        for(int i=lo;i<=(lo+ri)/2;i++){
            if(s.charAt(i) != s.charAt((lo+ri-i)))
                return false;
        }
        return true;
    }
    public static void main(String[] args) {

        new PalindromePartitioning().partition("aab");
    }

}
