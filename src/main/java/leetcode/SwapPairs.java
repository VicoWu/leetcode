package leetcode;

/**
 * Question 24
 */
public class SwapPairs {
  public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
    public ListNode swapPairs(ListNode head) {
        ListNode pivot = new ListNode();
        pivot.next = head;
        ListNode cur = head;
        ListNode pre = pivot;
        while(cur != null && cur.next != null){
            ListNode next = cur.next;


            cur.next = next.next;
            pre.next = next;
            next.next = cur;

            pre=cur;
            cur = cur.next;
        }
        return pivot.next;
    }
}
