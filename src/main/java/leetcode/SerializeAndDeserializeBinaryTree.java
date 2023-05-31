package leetcode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Question 297
 *
 * ## 序列化方法
 通过Dequeue，对树使用分层遍历，先把根节点入队列，并把根节点的元素存入序列化串中。然后，循环对队列进行弹出操作，每弹出一个节点，就在序列化串中append其左节点和有节点
 （注意append的时候数据之间需要有分隔符，同时，左节点和有节点有可能是空的，也需要标记），对不为空的子节点进行入队列操作。

 ## 反序列化方法
 先根据分割规则将序列化的串进行分割，分割得到的元素有的是正常的元素，有的是空元素代表空节点。被分割的数组的第一个元素肯定不是空并且是根节点，
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

    // Decodes your encoded data to tree.
    public TreeNode deserialize1(String data) {
        if(data==null)
            return null;
        Deque<String> sQueue = new LinkedBlockingDeque<String>();
        Deque<TreeNode> treeDueue = new LinkedBlockingDeque<TreeNode>();
        String[] values =  data.split(SPLITTER);
        TreeNode root = new TreeNode(Integer.valueOf(values[0])); //第一个元素是根元素
        treeDueue.addLast(root);
        ArrayList q = new ArrayList();



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

    /**
     * 我这里的方法是按照前序遍历的方法，通过递归的方式，构造序列化的字符串
     * 在进行反序列化的时候，同样
     * @param root
     * @return
     */

    public String serialize(TreeNode root) {
        StringBuffer sb = new StringBuffer();
        construct(root, sb);
        return sb.toString();
    }

    private void construct(TreeNode node, StringBuffer sb)
    {
        if (node == null) {
            sb.append("NULL,");
            return;
        }
        else {
            sb.append(String.valueOf(node.val) + ",");
        }
        construct(node.left, sb);
        construct(node.right, sb);
    }



    // Encodes a tree to a single string.

    // Decodes your encoded data to tree.
    int index = 0;

    public TreeNode deserialize(String data)
    {
        String[] dataArray = data.split(",");
        TreeNode root = deConstruct(dataArray, index);
        return root;
    }

    public TreeNode deConstruct(String[] dataArray, int index){
        if("NULL".equals(dataArray[index]))
            return null;
        TreeNode currentRoot = new TreeNode(Integer.valueOf(dataArray[index]));
        index++;
        TreeNode left = deConstruct(dataArray, index);
        currentRoot.left = left;
        index++;
        TreeNode right = deConstruct(dataArray, index);
        currentRoot.right = right;
        return currentRoot;
    }

    int currentEnd = 0;
    // Encodes a tree to a single string.
    public String serialize2(TreeNode root) {
        if(root == null){
            return "N";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(root.val);
        sb.append("|");
        sb.append(serialize2(root.left));
        sb.append("|");
        sb.append(serialize2(root.right));
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize2(String data) {
        return deserialize2(data, 0);
    }

    public TreeNode deserialize2(String data, int startIndex){
        if(data.charAt(startIndex) == 'N'){
            currentEnd = startIndex;
            return null;
        }
        TreeNode t = new TreeNode(getValue(data, startIndex));
        TreeNode left = deserialize2(data, currentEnd + 2);
        TreeNode right = deserialize2(data, currentEnd + 2);
        t.left = left;
        t.right = right;
        return t;
    }

    public int getValue(String data, int startIndex){
        int nextSplit = data.indexOf("|", startIndex);
        currentEnd = Math.min(nextSplit, data.length()) - 1;
        return Integer.valueOf(data.substring(startIndex, currentEnd + 1));
    }

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(-1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(-3);
        //TreeNode node4 = new TreeNode(4);
        node1.left=node2;
        node2.right = node3;
        //node3.right = node4;


        String serilized = new SerializeAndDeserializeBinaryTree().serialize2(node1);
        TreeNode root = new SerializeAndDeserializeBinaryTree().deserialize2(serilized);
        System.out.println(root.val);
    }
}
