package leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by wuchang at 1/6/18
 * Question 103
 * 这一题与
 */

public class BinaryTreeZigzagLevelOrderTraversal {


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {

        List<List<Integer>> res = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();

        if (root == null)
            return res;
        queue.add(root);
        int ln = 1;
        int nextLn = 0;//ln记录了本层节点的数量 //nextLn保存了下一层节点的数量
        LinkedList<Integer> thisLevel = new LinkedList<>();
        boolean l2r = true; //标记为，记录是从左到右还是从右到左。从题目来看，根节点是从左到右
        while (!queue.isEmpty()) {

            TreeNode node = queue.poll();

            if (node.left != null) {
                nextLn++; //下一层节点数量加1
                queue.add(node.left);
            }
            if (node.right != null) {
                nextLn++;//下一层节点数量加1
                queue.add(node.right);
            }

            if(l2r) //如果是从左到右，则添加到尾部
                thisLevel.add(node.val);
            else
                thisLevel.addFirst(node.val); //如果是从右到左，则添加到头部

            if (ln-- == 1) {
                res.add(thisLevel); //保存这一层的结果
                thisLevel = new LinkedList<>(); //thisLevel清空，准备开始下一层的遍历
                ln = nextLn;//
                nextLn = 0;  //nextLn清零
                l2r = !l2r;
            }
        }
        return res;
    }



    public static void main(String[] args) {

       TreeNode t3= new  TreeNode(3);
        TreeNode t9= new  TreeNode(9);
      TreeNode t20= new  TreeNode(20);
        TreeNode t15= new TreeNode(15);
     TreeNode t17= new TreeNode(7);
        t3.left = t9;
        t3.right = t20;
        t20.left = t15;
        t20.right = t17;
        new BinaryTreeZigzagLevelOrderTraversal().zigzagLevelOrder(t3);
    }

}
