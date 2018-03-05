package leetcode;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wuchang at 1/12/18
 * Question 222
 我们知道，对于一个完全二叉树，除最后一层外，每一层上的结点数均达到最大值；在最后一层上只缺少右边的若干结点。计算节点数量的关键，
 在于不断通过二分法确认这个完全二叉树的最后一个节点是在当前节点的左子树还是右子树上。

 我们知道，如果一个树不仅仅是完全二叉树，还是满二叉树，那么，这个树的节点数量可以根据树的高度轻松计算得到：`count = h^2;`
 如果当前节点为n，并且以n为根节点的树的高度为h, 那么，分两种情况：
 - 整个完全二叉树的最后一个节点在节点n的左子树上，那么，我们就知道，n的右子树不仅仅是完全二叉树，还是满二叉树，因此，右子树+当前的节点n的节点数量，就等于h^2；
 - 如果最后一个节点在节点n的右子树上，那么显然，左子树就不仅仅是一个完全二叉树，还是一个满二叉树，那么，左子树的节点数量+当前根节点的数量就可以根据满二叉树的高度计算得到。
 那么，我们应该怎么确认，完全二叉树的最后一个节点是在左子树上还是右子树上呢？我们通过对比左子树和有字数的 高度值。显然，任何情况下，左子树的高度或者与右子树的高度相同，或者等于右子树的高度+1；
 加入以节点n为根节点的 树，高度为h，
 如果左子树的高度与右子树高度相同，那么说明最后一个节点肯定在右子树上，即左子树是一个完全二叉树。这种情况下，左子树和右子树高度都是`h-1`;
 如果左子树的高度等于右子树高度+1，那么说明最后一个节点肯定在左子树上，即右子树是一个完全二叉树。这种情况下，左子树高度是`h-1`，右子树高度是`h-2`;

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

    int height(TreeNode root) {
        return root == null ? -1 : 1 + height(root.left);
    }
    public int countNodes(TreeNode root) {
        int h = height(root);
        return h < 0 ? 0 :
                height(root.right) == h-1 ? (1 << h) + countNodes(root.right)
                        : (1 << h-1) + countNodes(root.left);
    }
}
