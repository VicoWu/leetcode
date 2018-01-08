/**
 * Created by wuchang at 1/8/18
 * Question 129
 *
 * # 我的解法
 设置一个全局的变量sum作为最终的求和的结果，然后遍历这棵树的时候，我们用根节点携带从根节点到当前节点的路径，
 一旦我们发现这个节点是一个叶子节点，就开始计算结果。比如，我们经过2->3->1 到达节点0，并且0是叶子节点，那么当前的结果就是`231*10 + 0 = 2310`。这样，当我们遍历完所有的节点，就得到了和。

 # 别人的解法
 对于以root作为根节点的树，我们求得左子树的和以及右子树的和，作为当前树的和。比如对于树：
 ```
 1
 /  \
 2   3
 /   \
 4    5

 ```
 对于节点1，左子树的和是124+125=249，右子树的和是13，因此整个树的和就是249+13=262




 */

public class SumRootToLeafNumbers {

    public int sum = 0;
 public static class TreeNode {
    int val;
      TreeNode left;
      TreeNode right;
     TreeNode(int x) { val = x; }
  }


    /**
     * 这是我自己的解法
     * @param root
     * @return
     */
    public int sumNumbers(TreeNode root) {

        sumNumbers(root,new StringBuilder());
        return sum;
    }


    public void sumNumbers(TreeNode root,StringBuilder sb ) {

        if(root==null)
            return ;
        sb.append(root.val);
        if(root.left==null && root.right == null) //如果遇到
        {
            sum+=Integer.valueOf(sb.toString());
        }
        sumNumbers(root.left,sb);
        sumNumbers(root.right,sb);
        sb.delete(sb.length()-1,sb.length());
    }

    /**
     * 这是别人的解法
     * @param root
     * @return
     */
    public int sumNumbers1(TreeNode root) {
        return sum(root, 0);
    }

    public int sum(TreeNode n, int s){
        if (n == null) return 0;
        if (n.right == null && n.left == null) return s*10 + n.val;
        return sum(n.left, s*10 + n.val) + sum(n.right, s*10 + n.val);
    }



    public static void main(String[] args) {

//        TreeNode t0 = new TreeNode(0);
//        TreeNode t1 = new TreeNode(1);
        //t1.right = t0;
        //t0.right = t1;
//        TreeNode t2 = new TreeNode(2);
//        TreeNode t3 = new TreeNode(3);
//        t1.left = t2;
//        t1.right = t3;
//        TreeNode t4 = new TreeNode(4);
//        TreeNode t9 = new TreeNode(9);
//        TreeNode t0 = new TreeNode(0);
//        TreeNode t1 = new TreeNode(1);
//        t4.left = t9;
//        t4.right = t0;
//        t9.right = t1;
        TreeNode t1 = new TreeNode(1);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(3);
        TreeNode t4 = new TreeNode(4);
        TreeNode t5 = new TreeNode(5);
        TreeNode t6 = new TreeNode(6);
        t1.left = t2;
        t1.right = t3;
        t2.right = t4;
        t4.left  = t5;
        t4.right= t6;
        System.out.println(new SumRootToLeafNumbers().sumNumbers(t1));
    }
}
