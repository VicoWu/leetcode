package leetcode;

import java.util.Stack;

/**
 * Question 98
 */
public class ValidateBinarySearchTree {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    public boolean isValidBST(TreeNode root) {

        Stack<TreeNode> stack =new Stack<TreeNode>();
        Integer lastValue = null;
        while(root!=null || !stack.isEmpty()){
            while(root!=null){
                stack.push(root);
                root = root.left;
            }
            TreeNode poped = stack.pop();
            System.out.println(poped.val);
            if(lastValue != null && poped.val <= lastValue)
                return false;
            lastValue = poped.val;
            if(poped.right!=null)
            {
                TreeNode rightNode = poped.right;
                while(rightNode!=null) {
                    stack.push(rightNode);
                    rightNode = rightNode.left;
                }
            }

        }
        return true;
    }


    public static void main(String[] args) {
        TreeNode node5 =new TreeNode(5);
        TreeNode node10 =new TreeNode(10);
        TreeNode node6 =new TreeNode(6);
        TreeNode node15=new TreeNode(15);
        TreeNode node20 =new TreeNode(20);
        node10.left = node5;
        node10.right = node15;
        node15.left = node6;
        node15.right = node20;

       // TreeNode test=new TreeNode(-2147483648);

        System.out.println(new ValidateBinarySearchTree().isValidBST(node10));
    }
}
