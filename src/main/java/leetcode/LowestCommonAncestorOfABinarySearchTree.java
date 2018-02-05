package leetcode;

/**
 * Created by wuchang at 12/28/17
 */

public class LowestCommonAncestorOfABinarySearchTree {


  public static class TreeNode {
      int val;
     TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }


    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
      if(root == p || root == q)
          return root;

      if((root.val - p.val > 0) ^ (root.val - q.val > 0))
          return root;

      return root.val - p.val > 0?lowestCommonAncestor(root.left,p,q):lowestCommonAncestor(root.right,p,q);
    }

    public static void main(String[] args) {
        TreeNode t6 = new TreeNode(6);
        TreeNode t2 = new TreeNode(2);
        TreeNode t8= new TreeNode(8);
        TreeNode t0 = new TreeNode(0);
        TreeNode t4 = new TreeNode(4);
        TreeNode t7 = new TreeNode(7);
        TreeNode t9 = new TreeNode(9);
        TreeNode t3 = new TreeNode(3);
        TreeNode t5 = new TreeNode(5);
        t6.left = t2;
        t6.right = t8;
        t2.left = t0;
        t2.right = t4;
        t8.left = t7;
        t8.right = t9;
        t4.left = t3;
        t4.right = t5;
       System.out.println(new LowestCommonAncestorOfABinarySearchTree().lowestCommonAncestor(t6,t2,t5).val) ;

    }
}
