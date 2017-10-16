import java.util.*;

/**
 * Question 25
 */


class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}


public class ReverseNodesInKGroup {


    public ListNode reverseKGroup(ListNode head, int k) {

        if (k <= 1 || head == null ||head.next == null  )
            return head;

        ListNode monitor = new ListNode(-1);
        ListNode currentGroupHeader = head;
        ListNode currentGroupTail = head;
        for (int i = 1; i < k && currentGroupTail!= null; i++) {
            currentGroupTail = currentGroupTail.next;
        }
        if(currentGroupTail==null)
            return currentGroupHeader;

        ListNode   nextGroupHeader = null;
        ListNode previousGroupTail = monitor;
        ListNode result = currentGroupTail;

        while (currentGroupTail != null) {

            nextGroupHeader =  currentGroupTail.next;      //掉头之前，先备份下一个group的header
            reverse(currentGroupHeader,currentGroupTail);

            previousGroupTail.next = currentGroupTail; //上一个group的尾部节点指向当前group的头节点
            previousGroupTail = currentGroupHeader;
            currentGroupHeader = currentGroupTail = nextGroupHeader;
            for (int i = 1; i < k && currentGroupTail!= null; i++) { //让currentGroupHeader和currentGroupTail分别指向下一个group的首尾
                currentGroupTail = currentGroupTail.next;
            }

        }

        previousGroupTail.next =  currentGroupHeader;

        return result;
    }

    private void reverse(ListNode currentGroupHeader,ListNode currentGroupTail){

        ListNode current = currentGroupHeader.next;
        ListNode previous = currentGroupHeader;
        while (current != currentGroupTail) {
            ListNode nextNodeBak = current.next;
            current.next = previous;
            previous = current;
            current = nextNodeBak;
        }

        current.next = previous;

        currentGroupTail = currentGroupHeader;
        currentGroupHeader  = current;

    }

    public static void main(String[] args){
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        n1.next = n2;
        n2.next = n3;
        n3.next=n4;
        n4.next=n5;
        new ReverseNodesInKGroup().reverseKGroup(n1,2);

    }

}


