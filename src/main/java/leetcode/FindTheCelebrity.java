package leetcode;

import java.util.Stack;

/**
 * Created by wuchang at 2/8/18
 * question 277
[这里](https://discuss.leetcode.com/topic/23534/java-solution-two-pass)的解法跟下面通过栈
的方式求解相似
[这里](http://www.cnblogs.com/grandyang/p/5310649.html)对这一题有比较详细的解释

 无论什么解法，其实方法都是大体相似的，即，如果这个集合里面有名人，那么，我们通过一轮O(n)的遍历，找到的一定就是名人。但是，有可能集合里面没有名人，那么我们找到的这个人就不是名人。
 因此，需要第二轮O（n）的遍历来确认它是不是名人。
 */

public class FindTheCelebrity {
    static int MATRIX[][] = { { 0, 0, 1, 0 },
            { 0, 0, 1, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 1, 0 } };

    // Returns true if a knows b, false otherwise
    static boolean knows(int a, int b)
    {
        boolean res = (MATRIX[a][b] == 1) ? true : false;
        return res;
    }

    /**
     * 通过栈的方式求解,参考[这里](https://www.geeksforgeeks.org/the-celebrity-problem/)
     * @param n
     * @return
     */
    // Returns -1 if celebrity is not present.
    // If present, returns id (value from 0 to n-1).
    static int findCelebrity(int n)
    {
        Stack<Integer> st = new Stack<>();
        int c;

        // Step 1 :Push everybody onto stack
        for (int i = 0; i < n; i++)
        {
            st.push(i);
        }

        while (st.size() > 1)
        {
            // Step 2 :Pop off top two persons from the
            // stack, discard one person based on return
            // status of knows(A, B).
            int a = st.pop();
            int b = st.pop();

            // Step 3 : Push the remained person onto stack.
            if (knows(a, b))
            {
                st.push(b);//如果a认识b，那么a肯定不是名人，b可能是名人
            }

            else
                st.push(a);//如果a不认识b，那么b肯定不是名人，a可能是名人
        }

        c = st.pop();

        //确定了celebrity候选人，如果的确存在一个celebrity，那么它肯定就是celebrity,
        // 当然有可能不存在这样的celebrity，因此需要二次遍历来确认
        // Step 5 : Check if the last person is
        // celebrity or not
        for (int i = 0; i < n; i++)
        {
            // If any person doesn't know 'c' or 'a'
            // doesn't know any person, return -1
            if (i != c && (knows(c, i) || !knows(i, c)))
                return -1;
        }
        return c;
    }


    /**
     * 设定候选人res为0，原理是先遍历一遍，对于遍历到的人i，若候选人res认识i，则将候选人res设为i，
     * 完成一遍遍历后，我们来检测候选人res是否真正是名人，我们如果判断不是名人，则返回-1，如果并没有冲突，返回res，参见代码如下：
     * 这是[这里](http://www.cnblogs.com/grandyang/p/5310649.html)的解法
     * @param n
     * @return
     */
    public int findCelebrity1(int n) {
        int res = 0;
        for (int i = 0; i < n; ++i) {
            if (knows(res, i)) res = i;
        }
        for (int i = 0; i < n; ++i) {
            if (res != i && (knows(res, i) || !knows(i, res))) return -1;
        }
        return res;
    }




    // Driver program to test above methods
    public static void main(String[] args)
    {
        int n = 4;
        int result = findCelebrity(n);
        if (result == -1)
        {
            System.out.println("No Celebrity");
        }
        else
            System.out.println("Celebrity ID " + result);
    }

}
