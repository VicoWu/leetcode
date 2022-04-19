package leetcode;

/**
 * https://leetcode-cn.com/problems/path-sum/solution/lu-jing-zong-he-by-leetcode-solution/
 */
public class PathSum {
    public static  class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

        public boolean hasPathSum(TreeNode root, int sum) {
            if (root == null) {
                return false;
            }
            if (root.left == null && root.right == null) {
                return sum == root.val;
            }
            return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
        }
}
