package leetcode;

/**
 * Created by wuchang at 3/1/18
 * Question 572. Subtree of Another Tree
 这一题需要与newcoder上的剑指offer题目[树的子结构](https://www.nowcoder.com/practice/6e196c44c7004d15b1610b9afca8bd88?tpId=13&tqId=11170&tPage=1&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)相区别。树的子树和子结构不同，
 如果T是S的子结构，只要求T是S的一部分就行了，而T如果是S的子树，则不仅仅需要是子结构，还要求T中包含S的所有子树节点。
 我直接复制了[这里](https://leetcode.com/problems/subtree-of-another-tree/discuss/102724/Java-Solution-tree-traversal)的写法。
 */

public class SubtreeOfAnotherTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
    }


    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (s == null) return false;
        if (isSame(s, t)) return true;
        return isSubtree(s.left, t) || isSubtree(s.right, t);
    }

    private boolean isSame(TreeNode s, TreeNode t) {
        if (s == null && t == null) return true;
        if (s == null || t == null) return false; //由于要求是子树而不是子结构，所有如果一个为空另外一个不为空，直接返回false

        if (s.val != t.val) return false;

        return isSame(s.left, t.left) && isSame(s.right, t.right);
    }

}
