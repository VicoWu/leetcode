package leetcode;

import java.util.Stack;

/**
 * Question 230
 */
public class KthSmallestElementInBST {


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int kthSmallest(TreeNode root, int k) throws Exception {

        Stack<TreeNode> stack =new Stack<TreeNode>();
        while(root!=null || !stack.isEmpty()){
            while(root!=null){
                stack.push(root);
                root = root.left;
            }
            TreeNode poped = stack.pop();
            if(--k == 0)
                return poped.val;
            if(poped.right!=null)
            {
                TreeNode rightNode = poped.right;
                while(rightNode!=null) {
                    stack.push(rightNode);
                    rightNode = rightNode.left;
                }
            }

        }

        return Integer.MAX_VALUE;//返回一个错误值
    }



    public static void main(String[] args) throws Exception {
        TreeNode node5 =new TreeNode(5);
        TreeNode node10 =new TreeNode(10);
        TreeNode node6 =new TreeNode(6);
        TreeNode node15=new TreeNode(15);
        TreeNode node20 =new TreeNode(20);
        node10.left = node5;
        node5.right = node6;
        node10.right = node20;
        node20.left = node15;
        System.out.println(new KthSmallestElementInBST().kthSmallest(node10,2));
    }
}

