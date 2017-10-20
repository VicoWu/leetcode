/**
 * Question 124
 * 动态规划
 主要思路是：某个节点的最长路径信息，如何根据其子树的最长路径和的信息推断出来？
 显然，如果我们有节点root的左子树和右子树的最长路径和的信息，怎么都无法得到root的最长路径信息，因为题目规定路径必须是一条，
 不可以出现分叉，因此我们不知道root加入进来以后整个树的最长路径信息会怎么变化。因此，计算每一个子树的信息的时候，少了一条信息，需要以下两条信息才能完成递归：
 1. 这个子树的最长路径(可能包含也可能不包含子树的根节点)，
 2. 以这个子树的根节点作为最后节点的最长路径信息。
 当某个树的左右子树有了这两个信息，那么这棵树也可以计算出来这两个信息，进而逐步往上，直到计算出根节点的这两个信息。
 */
public class BinaryTreeMaximumPathSum {

    public static class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
         TreeNode(int x) { val = x; }
     }


    public int maxPathSum(TreeNode root) {

        return getMax(root)[1];
    }

    /**
     * getMax返回一个array
     *
     * @param currentRoot
     * @return
     * result[0] :路径的末端包含currentRoot并且最长的路径的路径和，简称末端根节点最大路径值
     * result[1]: 这颗树的最长路径和的值，这个最长路径可能包含currentRoot，也可能不包含currentRoot，简称最大路径值
     */

    public int[] getMax(TreeNode currentRoot){

           int[] result =new int[2];

           if(currentRoot==null)
               result[0] = result[1] = 0;
           else if(currentRoot.left == null && currentRoot.right == null)
               result[0]=result[1] = currentRoot.val;
           else{
               int[] left = getMax(currentRoot.left); //左子树的计算信息
               int[] right = getMax(currentRoot.right);  //右子树的计算信息
               result[0] =  //计算末端根节点最大路径值
                       (left[0] > right[0]) ?  //如果左子树大于右子树
                       (left[0] > 0 //左子树大于右子树，并且左子树还大于0
                               ? left[0] + currentRoot.val //左子树大于右子树，并且左子树还大于0,则当前树的末端根节点最大路径值等于左子树的末端根节点最大值+根节点值
                               : currentRoot.val) //左子树大于右子树，并且左子树小于0，说明左右子树都小于0，那根节点自身就是当前树的末端根节点最大路径值
                       : (right[0] > 0  //如果右子树大于左子树，并且右子树大于0
                               ? (right[0] + currentRoot.val) //右子树大于左子树，并且右子树还大于0,则当前树的末端根节点最大路径值等于右子树的末端根节点最大值+根节点值
                               : currentRoot.val); ///右子树大于左子树，并且右子树小于0，说明左右子树都小于0，那根节点自身就是当前树的末端根节点最大路径值

               int maxIncludingRoot = Math.max(result[0],left[0]+right[0]+currentRoot.val);  //包含跟节点的最大路径和的值，

               int maxexcludingRoot = //不包含根节点的最大路径和的值
                       currentRoot.left==null
                       ?right[1] //如果没有左子树，那么这个值就是右子树的最大路径和
                       :(currentRoot.right==null
                               ?(left[1]) //如果没有右子树，那么这个值就是左子树的最大路径和
                               :(Math.max(left[1],right[1])));  //如果既有左子树又有右子树，那么就是左子树或者右子树中的较大值
               result[1] = Math.max(maxIncludingRoot,maxexcludingRoot) ;
           }
           return result;
    }

    private int getMax(int... args){
        int result=Integer.MIN_VALUE;
        for(int a:args){
           result=  Math.max(a,result);
        }
        return result;
    }


    public static void main(String[] args) {

        TreeNode node1  = new TreeNode(-2);
        TreeNode node2  = new TreeNode(-1);
//        TreeNode node3  = new TreeNode(-2);
//        TreeNode node4  = new TreeNode(-1);
        //TreeNode node5  = new TreeNode(6);
//        TreeNode node6  = new TreeNode(-7);
//        TreeNode node7  = new TreeNode(9);
        node1.left = node2;
//        node1.right=node3;
//        node2.left = node4;
//        node2.right = node5;
//        node3.left = node6;
//        node3.right = node7;


        System.out.println(new BinaryTreeMaximumPathSum().maxPathSum(node1));
    }
}
