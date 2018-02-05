package leetcode;

import java.util.*;

/**
 * Question 145
 */
public class BinaryTreePostOrderTraverse {


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        LinkedList<Integer> result = new LinkedList<Integer>();
        TreeNode current = root;
        while (current != null) {
            stack.push(current);
            current = current.left;
        }
        TreeNode lastNode = null;
        while (!stack.isEmpty()) {
            TreeNode stackTop = stack.lastElement();
            //如果这个节点没有右子树，那么肯定应该读取它了，如果它有右子树，则需要判断这个节点的右子树是不是已经读取过了，如果右子树读取过了，
            //就可以读取这个节点了，如果没有读取过，则需要读取右子树
            if (stackTop.right == null || stackTop.right == lastNode) {
                lastNode = stack.pop();
                result.add(lastNode.val); //如果这个节点没有右子树，那么就可以读取了
            } else  //它的右子树还没有读取，则应该读取其右子树
            {
                TreeNode right = stackTop.right;
                while (right != null) { //右子树的最左侧分支进栈
                    stack.push(right);
                    right = right.left;
                }
            }

        }
        return result;
    }



    public List<Integer> postorderTraversal2(TreeNode root) {
        LinkedList<Integer> result = new LinkedList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode p = root;
        while (!stack.isEmpty() || p != null) {
            if (p != null) {
                stack.push(p);
                result.addFirst(p.val);  // Reverse the process of preorder
                p = p.right;             // Reverse the process of preorder
            } else {
                TreeNode node = stack.pop();
                p = node.left;           // Reverse the process of preorder
            }
        }
        return result;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4);
        TreeNode n5 = new TreeNode(5);
        TreeNode n6 = new TreeNode(6);
        root.left = n2;
        root.right = n5;
        n2.left = n3;
        n2.right = n4;
        n4.left = n6;


        new BinaryTreePostOrderTraverse().postorderTraversal(root);
    }
}
