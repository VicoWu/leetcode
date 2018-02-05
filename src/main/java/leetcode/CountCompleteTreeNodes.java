package leetcode;

import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;

/**
 * Created by wuchang at 1/12/18
 * Question 222
 */


public class CountCompleteTreeNodes {

    public static class TreeNode {
        ConcurrentHashMap chm;
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int countNodes(TreeNode root) {

        return 1;
    }
}
