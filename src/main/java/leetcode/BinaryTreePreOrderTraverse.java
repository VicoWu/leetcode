package leetcode;

import java.util.*;

/**
 * Question 144
 */

public class BinaryTreePreOrderTraverse {


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public List<Integer> preorderTraversal(TreeNode root) {

        List<Integer> result = new LinkedList<Integer>();

        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode current = root;
        while(current != null){
            result.add(current.val);
            stack.push(current);
            current = current.left;
        }

        while(!stack.isEmpty()){

          TreeNode poped =   stack.pop();
          TreeNode right = poped.right;
          while(right!= null){
              result.add(right.val);
              stack.push(right);
              right = right.left;
          }
        }
        return result;

    }


    public static void main(String[] args){
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


        new BinaryTreePreOrderTraverse().preorderTraversal(root);
    }
}
