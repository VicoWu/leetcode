package leetcode;

import java.util.Stack;

/**
 * Created by wuchang at 2/8/18
 * question 277
 * [这里](https://discuss.leetcode.com/topic/23534/java-solution-two-pass)的解法跟下面通过栈
 * 的方式求解相似
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
                st.push(a);//如果b认识a，那么b肯定不是名人，a可能是名人
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
