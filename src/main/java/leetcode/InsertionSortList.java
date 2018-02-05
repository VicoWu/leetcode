package leetcode;

/**
 * Created by wuchang at 1/9/18
 * Question 147
 */

public class InsertionSortList {
    public ListNode insertionSortList(ListNode head) {
        ListNode dummy = new ListNode(-1);
        ListNode cursor = head;
        while (cursor != null) {
            ListNode pre = dummy;
            ListNode current = cursor;
            cursor = cursor.next;
            while(pre.next!=null && pre.next.val < current.val)
                pre=pre.next;
            ListNode t = pre.next;
            pre.next = current;
            current.next = t;
        }
        return dummy.next;
    }

    public static void main(String[] args) {

        ListNode n4 = new ListNode(4);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n5 = new ListNode(5);
        n4.next =n2;n2.next = n3;n3.next= n5;
        new InsertionSortList().insertionSortList(n4);
    }

}
