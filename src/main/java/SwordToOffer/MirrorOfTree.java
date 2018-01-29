package SwordToOffer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * Created by wuchang at 1/26/18
 * 这是剑指offer的二叉树的镜像。
 * 我们只需要从上到下的每一个节点的左指针和右指针反转就行。
 */

public class MirrorOfTree {

    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }

    }

    void MirrorIteratively(TreeNode pRoot) {
        if (pRoot == null)
            return;

        Stack stack;
        Deque<TreeNode> stackTreeNode = new ArrayDeque<TreeNode>();

        stackTreeNode.push(pRoot);

        while (stackTreeNode.size() > 0) {
            TreeNode pNode = stackTreeNode.pop();
            TreeNode pTemp = pNode.left;
            pNode.left = pNode.right;
            pNode.right = pTemp;

            if (pNode.left != null)
                stackTreeNode.push(pNode.left);

            if (pNode.right != null)
                stackTreeNode.push(pNode.right);
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode p2 = new TreeNode(1);
        TreeNode p3 = new TreeNode(1);
        root.right = p2;
        p2.right = p3;
        new MirrorOfTree().MirrorIteratively(root);
    }

}
