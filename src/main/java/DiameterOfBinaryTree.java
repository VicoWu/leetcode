/**
 * Created by wuchang at 12/27/17
 * Question 543
 *
 * # 递归计算
 [这里](https://www.geeksforgeeks.org/diameter-of-a-binary-tree/) 讲解了一个二叉树的直径的解法
 [这里](https://www.geeksforgeeks.org/diameter-n-ary-tree/) 讲解了一个多叉树的直径的解法
 [这里](https://www.geeksforgeeks.org/longest-path-undirected-tree/) 讲解了一个`undirected tree`求解一个最长路径的解法
 我在代码里面对这种方法进行了实现。
 必须区分二叉树的直径(diameter)和高度(height):直径是二叉树中任意两个叶子节点之间的最大距离，而高度则是要求以root节点作为重点的最长路径
 因此，一个二叉树的直径，或者等于左子树的直径，或者等于右子树的直径，或者等于左子树的高度+有子树的高度

 我在代码里面的算法不是优化的算法，因为每次求一个子树的直径的时候都需要调用height()方法计算height，属于重复计算。可以进行优化，在通过递归调用diameter的过程中直接获取高度，然后随着diameter的栈退出，
 直接高度递增，这样不需要不断重复计算高度。同样，[这里](https://www.geeksforgeeks.org/diameter-of-a-binary-tree/)讲解了这种优化方式。
 */

public class DiameterOfBinaryTree {

  public static class TreeNode {
    int val;
     TreeNode left;
      TreeNode right;
    TreeNode(int x) { val = x; }
 }



    public int diameterOfBinaryTree(TreeNode root) {

      return diameter(root);

    }

    public int height(TreeNode root){

      if(root==null)
          return 0;
      if(root.left==null && root.right==null)
          return 1;
      return Math.max(height(root.left)+1,height(root.right)+1);
    }

    /**
     * 计算以某个节点作为root的子树的直径
     * @param root
     * @return
     */
    public int diameter(TreeNode root){
        if(root==null || root.left==null && root.right==null)
            return 0;
        return  Math.max(Math.max(diameter(root.left), //这棵树的直径，可能在左子树上，因此等于左子树的直径
                diameter(root.right)), //这棵树的直径，可能在右子树上，因此等于右子树的直径
                height(root.left)+height(root.right)); //这棵树的直径，可能经过根节点，因此等于左子树的高度+
    }

    public static void main(String[] args) {
        TreeNode t1 = new TreeNode(1);
//        TreeNode t2 = new TreeNode(2);
//        TreeNode t3 = new TreeNode(3);
//        TreeNode t4 = new TreeNode(4);
//        TreeNode t5 = new TreeNode(5);
//        t1.left = t2;
//        t1.right = t3;
//        t2.left = t4;
//        t2.right = t5;
        System.out.println(new DiameterOfBinaryTree().diameterOfBinaryTree(t1));
    }
}
