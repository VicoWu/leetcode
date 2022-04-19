package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Leetcode 437 https://leetcode.com/problems/path-sum-iii/
 * 这一题与PathSumII 的区别是不再要求从根节点开始
 * <p>
 * https://leetcode-cn.com/problems/path-sum-iii/
 */
public class PathSumIII {
    public static class TreeNode {
        int val;
        PathSumIII.TreeNode left;
        PathSumIII.TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return 0;
        }

        int ret = rootSum(root, targetSum);
        ret += pathSum(root.left, targetSum);
        ret += pathSum(root.right, targetSum);
        return ret;
    }

    public int rootSum(TreeNode root, int targetSum) {
        int ret = 0;

        if (root == null) {
            return 0;
        }
        int val = root.val;
        if (val == targetSum) {
            ret++;
        }

        ret += rootSum(root.left, targetSum - val);
        ret += rootSum(root.right, targetSum - val);
        return ret;
    }


    // The second solution
    class Solution {
        public int pathSum(TreeNode root, int targetSum) {
            HashMap<Long, Integer> prefix = new HashMap<>();
            prefix.put(0L, 1);
            return dfs(root, prefix, 0, targetSum);
        }

        /**
         *
         * @param root
         * @param prefix
         * @param curr 当前节点的前缀和
         * @param targetSum
         * @return
         */
        public int dfs(TreeNode root, Map<Long, Integer> prefix, long curr, int targetSum) {
            if (root == null) {
                return 0;
            }

            int ret = 0;
            curr += root.val; //当前节点的前缀和

            ret = prefix.getOrDefault(curr - targetSum, 0); // 是否以这个点为结尾的段
            prefix.put(curr, prefix.getOrDefault(curr, 0) + 1); //更新前缀和为curr的数量， 增加1
            ret += dfs(root.left, prefix, curr, targetSum); //在左子树中寻找
            ret += dfs(root.right, prefix, curr, targetSum); // 在右子树中寻找
            prefix.put(curr, prefix.getOrDefault(curr, 0) - 1); //由于当前节点和它的子树已经遍历完毕，所以前缀和统计可以去除了。

            return ret;
        }
    }
}
