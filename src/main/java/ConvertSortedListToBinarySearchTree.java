/**
 * Created by wuchang at 1/6/18
 * Question 109
代码中列举了两种算法和思想：
 # 一次顺序遍历完成构造
 这个算法根据我们对二叉排序树进行中序遍历能够得到一个顺序递增集合的思想，认为这个输入的 链表就是我们待构造的BST的中序遍历结果，用这个结果来还原出我们的二叉排序树。
 当然，根据中序遍历结果还原二叉排序树，这个二叉排序树并不一定是平衡的，因此，` inorderHelper(int start, int end)`使用不断地二分方式来限制范围，使得整个二叉树
 构造出来以后是平衡的。这种方法只需要一遍顺序遍历输入链表就可以把整个二叉排序树构造出来。而下面的sortedListToBST2()这种比较容易想到的通用做法为了找到链表的中间元素就必须顺序遍历链表来找到这个中间元素，因此链表可能会被遍历多次。

 # 通用方法：对链表不断进行二分
 这是一种比较容易理解的方法，在list中找到中间节点作为root节点，然后分别对左子树和右子树进行相同的算法，逐渐构建二叉平衡搜索树。
 但是这种算法有一个缺点，就是时间复杂度比较高，这个复杂度主要是来自于通过遍历的方式找到链表的中间节点带来的复杂度。
 */

public class ConvertSortedListToBinarySearchTree {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    private ListNode node;

    /**
     * 这个算法根据我们对二叉排序树进行中序遍历能够得到一个顺序递增集合的思想，认为这个输入的 链表就是我们待构造的BST的中序遍历结果，用这个结果来还原出我们的二叉排序树。
     * 当然，根据中序遍历结果还原二叉排序树，这个二叉排序树并不一定是平衡的，因此，` inorderHelper(int start, int end)`使用不断地二分方式来限制范围，使得整个二叉树
     * 构造出来以后是平衡的。
     * 这种方法只需要一遍顺序遍历输入链表就可以把整个二叉排序树构造出来。而下面的sortedListToBST2()这种比较容易想到的通用做法为了找到链表的中间元素
     * 就必须顺序遍历链表来找到这个中间元素，因此链表可能会被遍历多次。
     * @param head
     * @return
     */
    public TreeNode sortedListToBST(ListNode head) {
        if(head == null){
            return null;
        }

        int size = 0;
        ListNode runner = head;
        node = head;

        while(runner != null){
            runner = runner.next;
            size ++;
        }

        long end = System.currentTimeMillis();

        return inorderHelper(0, size - 1);
    }

    public TreeNode inorderHelper(int start, int end){
        if(start > end){
            return null;
        }

        int mid = start + (end - start) / 2;
        TreeNode left = inorderHelper(start, mid - 1); //在左子树进行中序遍历

        TreeNode treenode = new TreeNode(node.val);  //访问当前节点
        treenode.left = left;
        node = node.next; //移动到链表的下一节点

        TreeNode right = inorderHelper(mid + 1, end);//在右子树进行中序遍历
        treenode.right = right;

        return treenode;
    }


    /**
     * 这是一种比较容易理解的方法，在list中找到中间节点作为root节点，然后分别对左子树和右子树进行相同的算法，逐渐构建二叉平衡搜索树。
     * 但是这种算法有一个缺点，就是时间复杂度比较高，这个复杂度主要是来自于通过遍历的方式找到链表的中间节点带来的复杂度。
     * @param head
     * @return
     */
    public TreeNode sortedListToBST2(ListNode head) {
        if(head==null) return null;
        return toBST(head,null);
    }

    public TreeNode toBST(ListNode head, ListNode tail){
        ListNode slow = head;
        ListNode fast = head;
        if(head==tail) return null;

        while(fast!=tail&&fast.next!=tail){
            fast = fast.next.next;
            slow = slow.next;
        }
        TreeNode thead = new TreeNode(slow.val);
        thead.left = toBST(head,slow);
        thead.right = toBST(slow.next,tail);
        return thead;
    }


    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode tail = node1;
        for(int i=2;i<1000000;i++){
            ListNode node = new ListNode(i);
            tail.next = node;
            tail  = node;
        }
        long start = System.currentTimeMillis();
        TreeNode result = new ConvertSortedListToBinarySearchTree().sortedListToBST2(node1);
        long end = System.currentTimeMillis();
        result = new ConvertSortedListToBinarySearchTree().sortedListToBST(node1);
        long end2  = System.currentTimeMillis();
        System.out.println((end-start) + " "+(end2-end));
        System.out.println();
    }
}
