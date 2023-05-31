package leetcode;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LargestValue {
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
    public List<Integer> largestValues(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        Queue<TreeNode> next = null;
        List<Integer> res = new LinkedList();
        if(root == null)
            return new LinkedList();
        q.add(root);
        while(!q.isEmpty()){
            next =  new LinkedList<TreeNode>();
            int max = Integer.MIN_VALUE;
            int size = q.size();
            for(int i = 0;i<size;i++){
                TreeNode node = q.poll();
                max = Math.max(max, node.val);
                if(node.left != null){
                    next.offer(node.left);
                }
                if(node.right != null){
                    next.offer(node.right);
                }
            }
            res.add(max);
            q.addAll(next);
        }

        return res;
    }

    public static void main(String[] args) {
      TreeNode n1 = new TreeNode(1);
      TreeNode n2 = new TreeNode(2);
      TreeNode n3 = new TreeNode(3);
      n1.left = n2;
      n1.right = n3;
      new LargestValue().largestValues(n1);
    }
}
