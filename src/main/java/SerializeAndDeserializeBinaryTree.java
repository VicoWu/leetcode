import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Question 297
 *
 * ## 序列化方法
 通过Dequeue，对树使用分层遍历，先把跟节点如队列，并把根节点的元素存入序列化串中。然后，循环对队列进行弹出操作，每弹出一个节点，就在序列化串中append其左节点和有节点
 （注意append的时候数据之间需要有分隔符，同时，左节点和有节点有可能是空的，也需要标记），对不为空的子节点进行入队列操作。

 ## 反序列化方法
 先根据分割规则将序列化的串进行分割，分割得到的元素有的是正常的元素，有的是空元素代表空节点。被分割的数组的第一个元素肯定不是空并且是跟节点，
 先构造这个根节点并把根节点如队列，然后从i=1开始遍历，遍历的同时将队首元素出队列，保证当前i和i+1的两个元素肯定分别就是队列的队首节点的左孩子节点和右孩子节点。
 */
public class SerializeAndDeserializeBinaryTree {

    private static String SPLITTER =",";
    private static String NULL_FLAG = "null";
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if(root==null)
            return null;
        Deque<TreeNode> treeDueue = new LinkedBlockingDeque<TreeNode>();
        TreeNode current = root;
        treeDueue.add(current);
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(root.val)+SPLITTER);
        while (!treeDueue.isEmpty()) {
            TreeNode node = treeDueue.pop();
            if (node.left == null)
                sb.append(NULL_FLAG);
            else {
                treeDueue.addLast(node.left);
                sb.append(String.valueOf(node.left.val));
            }
            sb.append(SPLITTER);
            if (node.right == null)
                sb.append(NULL_FLAG);
            else {
                treeDueue.addLast(node.right);
                sb.append(String.valueOf(node.right.val));
            }
            sb.append(SPLITTER);


        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data==null)
            return null;
        Deque<String> sQueue = new LinkedBlockingDeque<String>();
        Deque<TreeNode> treeDueue = new LinkedBlockingDeque<TreeNode>();
        String[] values =  data.split(SPLITTER);
        TreeNode root = new TreeNode(Integer.valueOf(values[0]));
        treeDueue.addLast(root);


       for(int i=1;i<values.length;){
           String left = values[i];String right = values[i+1]; //取出左节点和右节点
           TreeNode current = treeDueue.pop();
           current.left = NULL_FLAG.equals(left)?null:new TreeNode(Integer.valueOf(left));
           current.right = NULL_FLAG.equals(right)?null:new TreeNode(Integer.valueOf(right));

           if(current.left!=null)
               treeDueue.addLast(current.left);
           if(current.right!=null)
               treeDueue.addLast(current.right);
           i=i+2;
       }

       return root;


    }


    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        node1.left=node2;
        node1.right = node3;
        node3.right = node4;


        String serilized = new SerializeAndDeserializeBinaryTree().serialize(node1);
        TreeNode root = new SerializeAndDeserializeBinaryTree().deserialize(serilized);
        System.out.println(root.val);
    }
}
