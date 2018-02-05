package leetcode;

/**
 * Created by wuchang at 1/6/18
 * Question 106

 [Construct Binary Tree from Preorder and Inorder Traversal](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/)
 [Construct Binary Tree from Inorder and Postorder Traversal](https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/description/)的代码原理相同。
 */

public class ConstructBinaryTreeFromInorderAndPostorderTraversal {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
    }


    public TreeNode buildTree(int[] inorder, int[] postorder) {

        return buildTree(inorder,postorder,0,inorder.length-1,0,postorder.length-1);
    }

    public TreeNode buildTree(int[] inorder,int[] postorder, int inLo, int inRi,int poLo,int poRi) {
        if(poLo>poRi || inLo>inRi)
            return null;
        TreeNode root = new TreeNode(postorder[poRi]);
        int i = inRi;
        for (; i >=inLo && inorder[i] != postorder[poRi]; i--) ;
        root.left = buildTree(inorder, postorder, inLo, i - 1,poLo , poLo+i-inLo-1);
        root.right = buildTree(inorder, postorder, i + 1, inRi, poLo + i - inLo, poRi-1);
        return root;
    }

    public static void main(String[] args) {

        int[] inorder  = {4,2,5,1,6,3,7};
        int[] postorder = {4,5,2,6,7,3,1};

        int[] inorder1  = {4,2};
        int[] postorder1 = {2,4};
        new ConstructBinaryTreeFromInorderAndPostorderTraversal().buildTree(inorder1,postorder1);
    }

}
