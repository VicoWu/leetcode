package SwordToOffer;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LevelOrder {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public int[] levelOrder(TreeNode root) {
        List<TreeNode> fifo = new LinkedList();
        List<Integer> list = new ArrayList();
        fifo.add(root);
        while(!fifo.isEmpty()){
            TreeNode head = fifo.remove(0);
            list.add(head.val);
            if(head.left != null)
                fifo.add(head.left);
            if(head.right != null)
                fifo.add(head.right);
        }
        int[]  res = new int[list.size()];
        for(int i = 0;i<list.size();i++){
            res[i] = list.get(i);
        }
        return res;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        new LevelOrder().levelOrder(root);
    }
}
