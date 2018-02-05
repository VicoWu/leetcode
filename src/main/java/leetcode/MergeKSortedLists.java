package leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Question 23
 */
public class MergeKSortedLists {

   static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static class NodeComparator implements Comparator<ListNode>{

        public int compare(ListNode o1, ListNode o2) {
            return o1.val - o2.val;
        }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        ListNode head = null;ListNode tail = null;
        PriorityQueue<ListNode> queue = new PriorityQueue<ListNode>(new NodeComparator());

        for(int i=0;i<lists.length;i++)
            if(lists[i]!=null) queue.add(lists[i]);
        while(!queue.isEmpty()){

            ListNode node =  queue.poll(); //取出一个最小元素
            if(node.next!= null) queue.add(node.next);
            if(head == null )
            {
                head = node;
                tail = head;
            }
            else{
                tail.next = node;
                tail = tail.next;
            }

        }
        return head;
    }

    public static void main(String[] args) {
//        ListNode a1 = new ListNode(1);
//        ListNode a2 = new ListNode(5);
//        ListNode a3 = new ListNode(7);
//        ListNode a4 = new ListNode(9);
//        ListNode a5 = new ListNode(20);

        //a1.next = a2;a2.next =a3;a3.next=a4;a4.next=a5;

        ListNode b1 = new ListNode(4);
//        ListNode b2 = new ListNode(6);
//        ListNode b3 = new ListNode(8);
//        ListNode b4 = new ListNode(11);
//        ListNode b5 = new ListNode(12);

        //b1.next = b2;b2.next =b3;b3.next=b4;

        ListNode[] param = new ListNode[2];
        param[0]=null;
        param[1]=b1;
        new MergeKSortedLists().mergeKLists(param);

    }
}
