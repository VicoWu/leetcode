package leetcode;

/**
 * Created by wuchang at 1/6/18
 * Question 105
 [Construct Binary Tree from Preorder and Inorder Traversal](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/)
 [Construct Binary Tree from Inorder and Postorder Traversal](https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/description/)的代码原理相同。
 */

public class ConstructBinaryTreeFromPreorderAndInorderTraversal {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {

        return buildTree(preorder,inorder,0,preorder.length-1,0,inorder.length-1);
    }


    public TreeNode buildTree(int[] preorder, int[] inorder, int preLo, int preRi, int inLo, int inRi) {
        if(preLo>preRi || inLo>inRi)
            return null;
        TreeNode root = new TreeNode(preorder[preLo]);
        int i = inLo;
        for (; i <= inRi && inorder[i] != preorder[preLo]; i++) ;
        root.left = buildTree(preorder, inorder, preLo + 1, preLo + i - inLo, inLo, i - 1);
        root.right = buildTree(preorder, inorder, preLo + i - inLo+1, preRi, i + 1, inRi);
        return root;
    }
    public static void main(String[] args) {

        int[] preOrder = {1 ,2};
        int[] inorder = {1,2};
         new ConstructBinaryTreeFromPreorderAndInorderTraversal().buildTree(preOrder,inorder);
    }
}
