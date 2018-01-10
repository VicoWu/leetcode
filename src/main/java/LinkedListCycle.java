/**
 * Created by wuchang at 1/9/18
 * Question 141
 *
 [Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/description/)
 [Linked List Cycle II](https://leetcode.com/problems/linked-list-cycle-ii/description/)
 两题可以类比一下。

 本题中，两个指针同时向前移动，一个指针每次移动一步，一个指针每次移动两步，如果最终相遇，说明有环，如果遇到空指针，说明没有环。
 */

public class LinkedListCycle {
      private static class ListNode {
      int val;
      ListNode next;
      ListNode(int x) {
          val = x;
          next = null;
      }
  }

    /**
     * 我的解法，两个指针，一个指针每次移动一步，一个指针每次移动两步，如果两个指针能够相遇，说明有环，如果遇到空指针，说明没有环(由于每个节点只有一个指针，因此，如果遇到空指针，必定不存在环)
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {

          ListNode fast = head;
          ListNode slow = head;
          while(fast!=null && slow!=null){
              slow = slow.next;
              if(fast.next==null)
                  return false;
              fast = fast.next.next;
              if(slow == fast)
                  return true;
          }
          return false;
    }

    public static void main(String[] args) {
        ListNode  l1 = new ListNode(1);
        ListNode  l2 = new ListNode(1);
        ListNode  l3 = new ListNode(1);
        ListNode  l4 = new ListNode(1);
        ListNode  l5 = new ListNode(1);
        ListNode  l6 = new ListNode(1);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;
        l5.next = l6;
        //l6.next = l3;
        System.out.println(new LinkedListCycle().hasCycle(l1));
    }
}
