package leetcode;

/**
 * @author chang.wu
 * @date 11/11/20
 *
 * 206. Reverse Linked List
 */
public class ReverseLinkedList
{

    public ListNode reverseList(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode reversedHead = head;
        ListNode cur = head.next;
        reversedHead.next = null;
        while(cur != null){
            ListNode pNext = cur.next;
            cur.next = reversedHead;
            reversedHead = cur;
            cur = pNext;

        }
        return reversedHead;
    }

    ListNode finalHead = null;
    public ListNode reverseListByRecursive(ListNode head){
        if(head == null || head.next == null)
            return head;
        reverseListInner(head);
        head.next = null;
        return finalHead;
    }

    /**
     reverse the list start from head, return the tail of the reversed list
     */
    public ListNode reverseListInner(ListNode head){
        if(head.next == null){
            finalHead = head;
            return head;
        }
        reverseListInner(head.next).next = head;
        return head;
    }

    public static void main(String[] args)
    {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        ListNode ret = new ReverseLinkedList().reverseListByRecursive(n1);

        System.out.println(ret);
        //new ReverseLinkedList().reverseListByRecursive(n1)
    }

}
