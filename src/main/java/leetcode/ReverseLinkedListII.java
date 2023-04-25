package leetcode;

/**
 * Created by wuchang at 1/4/18
 * Question 92
 *
 1 2 3 4 5 6 m=2 n=4

 mockHead：创建一个虚拟节点，指向链表的表头，这是为了便于在翻转的时候控制，避免空指针等问题；
 cur：指向待翻转链表的前一个位置，这里，需要翻转2 3 4，因此cur指向1；
 rc:指向当前正在进行翻转的链表，比如，当前准备翻转2，则rc指向2，2翻转完毕指向3，则rc指向3；
 reverseTail:已经翻转完成的链表的尾部，这里已经翻转的链表的尾部实际上就是第一个被翻转的节点，这里是节点2。比如，先翻转2， 变成 12 3456 , 然后翻转3 ， 132 456 ， 然后翻转4  1432 56，reverseTail时钟指向2；

 翻转完毕以后，让reverseTail指向下一个没翻转的节点，即，1432 56，让2的next指向5，从而实现链表的连接

 */

public class ReverseLinkedListII {
   public static class ListNode {
       int val;
       ListNode next;

       ListNode() {
       }

       ListNode(int val) {
           this.val = val;
       }

       ListNode(int val, ListNode next) {
           this.val = val;
           this.next = next;

       }
   }

    public ListNode reverseBetween(ListNode head, int m, int n) {

        ListNode mockHead = new ListNode(-1);
        mockHead.next = head; //一个指向头结点的虚拟节点
        ListNode cur = mockHead;
        int dis = n-m;
        for(;m>1;m--)
            cur = cur.next; //遍历完毕，cur指向第m个节点的前一个节点

        /*
        reverseTail指向被翻转链表的最后一个节点
        比如12345 ，m=2,n=4，由于翻转以后节点2总是在末尾，我们让reverseTail指向2，当所有翻转结束，让reverseTail指向5,从而把链表连接起来
         */
        ListNode reverseTail = cur.next;
        ListNode rc = cur.next;//rc指向当前被翻转的节点
        cur.next = null;
        for(;dis>=0&& rc!=null;dis--){
             ListNode next = rc.next;//先保存下一个节点
             rc.next = cur.next; //将当前待翻转的节点rc的next节点指向当前已经翻转的链表，从而实现待翻转节点插入到以翻转链表的前面
             cur.next = rc;//cur指向当前被翻转链表的头结点
             rc = next;//rc指向下一个待翻转的节点
        }
        reverseTail.next = rc;//将被翻转链表的尾部指向下一个节点，比如，12345，m=2,n=4，翻转以后变成1432 5，我们让2指向5，从而实现整个链表的最终连接
        return mockHead.next;
    }

    public ListNode reverseBetween2(ListNode head, int left, int right) {
        if(left == right)
            return head;
        ListNode pivot = new ListNode();
        pivot.next = head;
        for(int i = 0;i<left-1; i++){
            pivot = pivot.next;
        }
        ListNode pre = pivot;
        ListNode tail = pivot;
        for(int i = left; i<=right; i++){
            tail = tail.next;
        }
        ListNode toReverse = pivot.next.next;
        pivot.next.next = tail.next;

        while(toReverse != null){
            ListNode newToReverse = toReverse.next;

            ListNode oldHead = pivot.next;
            pivot.next = toReverse;
            toReverse.next = oldHead;

            if(toReverse == tail)
                break;
            toReverse = newToReverse;
        }
        pre.next = pivot.next;
        return pre.next;
    }

    public static void main(String[] args) {
        ListNode a1 = new ListNode(3);
        ListNode a2 = new ListNode(5);
//        ListNode a3 = new ListNode(3);
//        ListNode a4 = new ListNode(4);
        a1.next = a2;
//        a2.next = a3;
//        a3.next = a4;
        new ReverseLinkedListII().reverseBetween2(a1,1,2);
    }
}
