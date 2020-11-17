package leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by wuchang at 1/6/18
 * Question 102
 *
 这一题比较简单，通过队列实现分层遍历。
 由于需要每层保存结果到一个list，因此最关键的是在分层遍历过程中如何判断当前从queue中取出来的节点是当前层还是已经是下一层了，我们使用计数器来判断。
 当然，我们也可以通过插入一个无效的null来标记这是这一层的最后一个节点了。
 我通过两个变量来保存当前层的剩余节点ln并统计下一层的节点nextLn，然后使用`List<Integer> thisLevel = new LinkedList<>();`来保存这一层的所有节点。
 当我们遍历到某一层的**最后一个节点**的时候，就可以把thisLevel保存下来，然后创建一个空的 thisLevel 了，并且把nextLn赋值给ln，然后nextLn清零，这样开始下一层的遍历。

 *
 *
 */

public class BinaryTreeLevelOrderTraversal {

   public static class TreeNode {
      int val;
    TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
 }


    public List<List<Integer>> levelOrder(TreeNode root) {

       List<List<Integer>> res = new LinkedList<>();
       Queue<TreeNode> queue = new LinkedList<>();

       if(root == null)
           return res;
       queue.add(root);
       int ln = 1;int nextLn = 0;//ln记录了本层节点的数量 //nextLn保存了下一层节点的数量
       List<Integer> thisLevel = new LinkedList<>();
       while(!queue.isEmpty()){

           TreeNode node =  queue.poll();

           if(node.left!=null)
           {
               nextLn++; //下一层节点数量加1
               queue.add(node.left);
           }
           if(node.right!=null) {
               nextLn++;//下一层节点数量加1
               queue.add(node.right);
           }

           thisLevel.add(node.val);
           if(ln-- == 1){
               res.add(thisLevel); //保存这一层的结果
               thisLevel = new LinkedList<>(); //thisLevel清空，准备开始下一层的遍历
               ln = nextLn;//
               nextLn = 0;  //nextLn清零
           }
       }
       return res;
    }


    public List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> globalRes = new LinkedList<>();
        if(root == null)
            return globalRes;
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.add(root);
        TreeNode dummy =  new TreeNode(1);q.add(dummy);
        List<Integer> curLine = new LinkedList<>();
        TreeNode poped;
        while(!q.isEmpty()){
            poped = q.poll();
            if(poped != dummy){
                curLine.add(poped.val);
                if(poped.left != null)
                    q.add(poped.left);
                if(poped.right != null)
                    q.add(poped.right);
            }
            else{ // we met the dummy node
                globalRes.add(curLine);
                if(!q.isEmpty()){
                    q.add(dummy);
                    curLine = new LinkedList();
                }
            }

        }
        return globalRes;
    }




    public static void main(String[] args) {

        TreeNode t3= new TreeNode(3);
        TreeNode t9= new TreeNode(9);
        TreeNode t20= new TreeNode(20);
        TreeNode t15= new TreeNode(15);
        TreeNode t17= new TreeNode(7);
        t3.left = t9;
        t3.right = t20;
        t20.left = t15;
        t20.right = t17;
       new BinaryTreeLevelOrderTraversal().levelOrder2(t3);
    }
}
