package leetcode;

/**
 * @author chang.wu
 * @date 11/9/20
 */




public class RemoveDuplicatesfromSortedList
{
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val;this.next = next; }
    }

    public ListNode deleteDuplicates(ListNode head) {
        ListNode cur = head;
        ListNode pre = head;
        while(cur != null){
            cur = cur.next;
            while(cur != null && cur.val == pre.val)
            {
                cur = cur.next;
            }
            pre.next = cur;
            pre = cur;

        }
        return head;
    }

    public static void main(String[] args)
    {
        ListNode head = new ListNode(1);
    }
}
