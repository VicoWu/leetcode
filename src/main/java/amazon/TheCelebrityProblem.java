package amazon;

import java.util.Stack;

/**
 * Created by wuchang at 2/4/18
 * 这道题在亚马逊的面试中出现过
 * 链接在[这里](https://www.geeksforgeeks.org/the-celebrity-problem/)
 * The Celebrity Problem
 * 3.7
 * In a party of N people, only one person is known to everyone. Such a person may be present in the party, if yes, (s)he doesn’t know anyone in the party. We can only ask questions like “does A know B? “. Find the stranger (celebrity) in minimum number of questions.
 * <p>
 * We can describe the problem input as an array of numbers/characters representing persons in the party. We also have a hypothetical function HaveAcquaintance(A, B) which returns true if A knows B, false otherwise. How can we solve the problem.
 */

public class TheCelebrityProblem {


    // Person with 2 is celebrity
    public static int MATRIX[][] = {{0, 0, 1, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 0},
            {0, 0, 1, 0}};

    // Returns true if a knows b, false otherwise
    public static boolean knows(int a, int b) {
        boolean res = (MATRIX[a][b] == 1) ? true : false;
        return res;
    }


    /**
     * 这是基于栈的计算方法
     *
     * @param n
     * @return
     */
    // Returns -1 if celebrity is not present.
    // If present, returns id (value from 0 to n-1).
    static int findCelebrity(int n) {
        Stack<Integer> st = new Stack<>();
        int c;

        // Step 1 :Push everybody onto stack
        for (int i = 0; i < n; i++) {
            st.push(i);
        }

        while (st.size() > 1) {
            // Step 2 :Pop off top two persons from the
            // stack, discard one person based on return
            // status of knows(A, B).
            int a = st.pop();
            int b = st.pop();

            // Step 3 : Push the remained person onto stack.
            if (knows(a, b)) {
                st.push(b);
            } else
                st.push(a);
        }

        c = st.pop();

        // Step 5 : Check if the last person is
        // celebrity or not
        for (int i = 0; i < n; i++) {
            // If any person doesn't know 'c' or 'a'
            // doesn't know any person, return -1
            if (i != c && (knows(c, i) || !knows(i, c)))
                return -1;
        }
        return c;
    }

    // Driver program to test above methods
    public static void main(String[] args) {
        int n = 4;
        int result = findCelebrity(n);
        if (result == -1) {
            System.out.println("No Celebrity");
        } else
            System.out.println("Celebrity ID " + result);
    }

    /**
     * 通过两个指针的方式进行计算
     * Method 4 (Using two Pointers)
     * The idea is to use two pointers, one from start and one from the end.
     * Assume the start person is A, and the end person is B. If A knows B, then A must not be the celebrity.
     * Else, B must not be the celebrity. We will find a celebrity candidate at the end of the loop.
     * Go through each person again and check whether this is the celebrity.
     *
     * @param n
     * @return
     */
    public static int findCelebrityByTwoPointer(int n) {
        // Initialize two pointers as two corners
        int a = 0;
        int b = n - 1;

        // Keep moving while the two pointers
        // don't become same.
        while (a < b) {
            if (knows(a, b))
                a++;
            else
                b--;
        }

        // Check if a is actually a celebrity or not
        for (int i = 0; i < n; i++) {
            // If any person doesn't know 'a' or 'a'
            // doesn't know any person, return -1
            if (i != a && (knows(a, i) || !knows(i, a)))
                return -1;
        }
        return a;
    }


}
