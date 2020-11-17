package leetcode;

/**
 * @author chang.wu
 * @date 11/17/20
 * leetcode  https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/
 */
public class BinarySearchTreeToDoublyLinkedList
{
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    TreeNode pre = null;
    public TreeNode convert(TreeNode root){
        if(root == null)
            return null;
        convert(root.left);
        if(pre != null){
            pre.right = root;
            root.left = pre;
        }
        pre = root;
        convert(root.right);
        return root;
    }

    public static void main(String[] args)
    {
        TreeNode n10 = new TreeNode(10);
        TreeNode n6 = new TreeNode(6);
        TreeNode n18 = new TreeNode(18);
        TreeNode n5 = new TreeNode(5);
        TreeNode n8 = new TreeNode(8);
        TreeNode n16 = new TreeNode(16);
        TreeNode n19 = new TreeNode(19);
        n10.left = n6;
        n10.right = n18;
        n6.left = n5;
        n6.right = n8;
        n18.left = n16;
        n18.right = n19;
        TreeNode res = new BinarySearchTreeToDoublyLinkedList().convert(n10);
        TreeNode cur = res;
        while(cur != null){
            System.out.println(cur.val);
            cur = cur.left;
        }
        cur = res;
        while(cur != null){
            System.out.println(cur.val);
            cur = cur.right;
    }

    }
}
