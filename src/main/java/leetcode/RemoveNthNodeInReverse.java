package leetcode;

/**
 * Question 19
 */
public class RemoveNthNodeInReverse {


      public static class ListNode {
          int val;
          ListNode next;
          ListNode(int x) { val = x; }
      }



    public ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode monitor = new ListNode(-1);
        monitor.next = head;

        if(head == null || n<=0)
            return head;

        ListNode cursor = head;
        int i= 1;
        while(i<n && cursor!= null){
            i++;
            cursor = cursor.next;
        }

        if(i<n) return head;
        ListNode previous = monitor;
        ListNode toRemoved = head;
        while(cursor.next != null){ //逐个遍历，直到cursor指向最后一个元素

            toRemoved = toRemoved.next;
            previous = previous.next;
            cursor = cursor.next;
        }
        previous.next = toRemoved.next;
        return monitor.next;
    }

    public static void main(String[] args){

        ListNode head = new ListNode(1);
        head.next = null;
        ListNode cursor = head;

//        for(int i = 2;i<=5;i++){
//            ListNode newNode = new ListNode(i);
//            cursor.next = newNode;
//            cursor = newNode;
//            cursor.next = null;
//
//        }

        ListNode n = new RemoveNthNodeInReverse().removeNthFromEnd(head,1);

    }
}
