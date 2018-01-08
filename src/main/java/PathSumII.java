import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuchang at 1/8/18
 * Question 113
# 我的解法，深度优先遍历
 从根节点开始往下遍历，并通过remaining记录当前剩余的和。然后分别在左子树和右子树进行递归。
 当`(remaining == root.val && root.left==null && root.right == null)`满足，则说明这个节点是一个叶子节点并且从根节点到当前这个叶子节点的和满足sum，因此保存结果并返回。
 注意，这一题必须要求是`root-to-leaf paths` ,因此，当我们发现remaining==0的时候，必须判断当前节点是叶子节点才说明找到了一个合法的`root-to-leaf paths`.
 */

public class PathSumII {

  public static  class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
 }


    public List<List<Integer>> pathSum(TreeNode root, int sum) {

        List<List<Integer>> res = new LinkedList<>();
        pathSum(root,sum,new LinkedList<>(),res);
      return res;
    }

    public void pathSum(TreeNode root,int remaining,List<Integer> path,List<List<Integer>> res){
      if(root==null)
          return ;
      path.add(root.val);
      if(remaining == root.val && root.left==null && root.right == null)
      {
          res.add(new LinkedList<>(path));
          path.remove(path.size()-1);
          return;
      }
      pathSum(root.left,remaining - root.val,path,res);
      pathSum(root.right,remaining - root.val,path,res);
      path.remove(path.size()-1);
    }

    public static void main(String[] args) {
        TreeNode t5 = new TreeNode(5);
        TreeNode t4 = new TreeNode(4);
        TreeNode t8 = new TreeNode(8);
        TreeNode t11 = new TreeNode(11);
        TreeNode t13 = new TreeNode(13);
        TreeNode t40 = new TreeNode(4);
        TreeNode t7 = new TreeNode(7);
        TreeNode t2 = new TreeNode(2);
        TreeNode t50 = new TreeNode(5);
        TreeNode t1 = new TreeNode(1);
        t5.left = t4;
        t5.right = t8;
        t4.left = t11;
        t8.left = t13;
        t8.right = t40;
        t11.left = t7;
        t11.right = t2;
        t40.left = t50;
        t40.right = t1;
//        TreeNode t1 = new TreeNode(1);
//        TreeNode t2 = new TreeNode(-2);
//        TreeNode t3 = new TreeNode(1);
//        TreeNode t4 = new TreeNode(-1);

//        TreeNode t2 = new TreeNode(-2);
//        TreeNode t3 = new TreeNode(-3);
//        t2.right = t3;
         new PathSumII().pathSum(t5,22);
    }


}
