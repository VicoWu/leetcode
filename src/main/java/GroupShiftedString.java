/**
 * Created by wuchang at 1/3/18
 * Question 249
 * 这一题在leetcode上是付费的，在[geeksforgeeks上](https://www.geeksforgeeks.org/group-shifted-string/)上可以看到



 Given an array of strings (all lowercase letters), the task is to group them in such a way that
 all strings in a group are shifted versions of each other. Two string S and T are called shifted if,

 ```
 S.length = T.length
 and
 S[i] = T[i] + K for
 1 <= i <= S.length  for a constant integer K

 ```

 For example strings {acd, dfg, wyz, yab, mop} are shifted versions of each other.

 ```
 Input  : str[] = {"acd", "dfg", "wyz", "yab", "mop",
 "bdfh", "a", "x", "moqs"};

 Output : a x
 acd dfg wyz yab mop
 bdfh moqs
 All shifted strings are grouped together.
 ```


从Shifted String的定义来看，一个group中的Shifted String，相邻字符之间的间距是相等的，比如abc ,def, ghi

 */

public class GroupShiftedString {

    private static final int ALPHA = 26;
    String getDiffString(String s)
    {
        char[] str = s.toCharArray();
        char[] groupId = new char[str.length-1];
        for (int i = 1; i < str.length; i++)
        {
            int dif = str[i] - str[i-1];
            if (dif < 0) //如果dif<0，即后一个字符比前一个字符要小，则用26取
                dif += ALPHA;
            // Representing the difference as char
            groupId[i-1] = (char)('a' + dif);
        }

        // This string will be 1 less length than str
        return new String(groupId);
    }


    public static void main(String[] args) {
        //new GroupShiftedString().getDiffString("abc");
        //new GroupShiftedString().getDiffString("def");
        new GroupShiftedString().getDiffString("cba");
    }

}
