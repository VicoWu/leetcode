package leetcode;

import java.util.Stack;

/**
 * @author chang.wu
 * @date 11/12/20
 */




public class SymmetricTree
{

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode(int x) { val = x; }
     * }
     */
    class Solution {
        public boolean isSymmetric(TreeNode root) {
            return root==null || isSymmetricHelp(root.left, root.right);
        }

        private boolean isSymmetricHelp(TreeNode left, TreeNode right){
            if(left==null || right==null)
                return left==right;
            if(left.val!=right.val)
                return false;
            return isSymmetricHelp(left.left, right.right) && isSymmetricHelp(left.right, right.left);
        }
    }


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // 我的思路是通过中序遍历的方式，如果在整棵树的根节点左侧，就进行入栈操作，如果在根节点右侧，就
    // 出栈并且比较出栈元素和当前元素，如果相同，则对称，从而递归比较整棵树的右子树的所有值
    // 但是这种方法的判断结果，是 这棵树是symmetric树 的必要不充分条件，也就是说，如果是symmetric树
    // 那么这个方法肯定返回true，但是，这个方法返回true，不一定就是symmectic树
    //比如
    /**
     *               1
     *             /   \
     *           2      2
     *          /      /
     *         2      2
     */
    Stack<TreeNode> st = new Stack<>();
    TreeNode empty = new TreeNode(-1);
    boolean rooted = false;
    public boolean isSymmetric(TreeNode root){
        if(root == null)
            return false;
        return traverseInMidOrder(root, root);
    }

    private boolean traverseInMidOrder(TreeNode cur, TreeNode globalRoot){
        boolean leftRes = false;
        boolean rightRes = false;
        // Process left sub tree
        if(cur == null){
            if(rooted){
                if(st.isEmpty() || st.pop() != empty)
                    return false;
                else
                    return true;
            }
            else{
                st.push(empty);
                return true;
            }
        }
       leftRes = traverseInMidOrder(cur.left, globalRoot);

        if(cur == globalRoot) // 当前位于根节点
            rooted = true; //整棵树的根节点，不用入也不用出，不影响对树的判断
        else if(rooted == true){ //已经位于根节点的右侧了
            if(st.isEmpty() || !isSameTree(st.pop(), cur)) // 看看当前元素是否是栈顶元素,不是，则立刻false
                return false;
        }
        else {// 还位于根节点的左侧
            st.push(cur);
        }

        // Process right sub tree
        rightRes = traverseInMidOrder(cur.right, globalRoot);
        return leftRes & rightRes;
    }

    public boolean isSameTree(TreeNode a, TreeNode b){
        if(a == b && a == empty)
            return true;
        else if(a != empty && b!= empty & a.val == b.val)
            return true;
        return false;
    }


    public static void main(String[] args)
    {
        SymmetricTree  st = new SymmetricTree();

        TreeNode t1 = new TreeNode(1);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(2);
        TreeNode t4 = new TreeNode(2);
        TreeNode t5 = new TreeNode(2);
        t1.left = t2;
        t1.right =t3;
        t2.left = t4;
        t3.left = t5;


//        tr.left = trLeft;
//        tr.right = trRight;
        System.out.println(st.isSymmetric(t1));
    }

}

