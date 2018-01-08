/**
 * Created by wuchang at 1/8/18
 * Question 143


 # 基本思路：
 将队列一分为二，将后半部分进行翻转，然后将翻转以后的元素逐渐插空到前面链表中。
 比如1 -> 2 -> 3 -> 4 -> 5
 将链表分为1 -> 2 -> 3和4 -> 5两部分
 然后将后面的4->5 进行翻转，反转为5->4
 然后将5-4插入到1 -> 2 -> 3中，变成1->5->2->4->3

 如何将链表一分为二：
 我的思路是先遍历整个链表，获取链表的长度，然后再次遍历链表到长度的一半从而获取链表的中间节点。
 但是其实可以一步进行：
 ```
 //Find the middle of the list
 ListNode p1=head;
 ListNode p2=head;
 while(p2.next!=null&&p2.next.next!=null){
 p1=p1.next;
 p2=p2.next.next;
 }

 ```



 */

public class ReorderList {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 这是我自己的解答
     * @param head
     */
    public void reorderList(ListNode head) {


        if(head == null )
            return;
        int size = 0;
        ListNode current = head;
        while(current!=null){ //探测队列的长度
            size++;
            current= current.next;
        }

        current = head;
        size = (size-1)/2;//将队列一分为二
        while(size-- >0){
            current = current.next;
        }
        ListNode reversed = reverse(current.next);
        current.next = null;
        ListNode c1 = head;//未翻转链表的头结点
        ListNode c2 = reversed; //已经翻转的链表的头结点
        while(c1!=null && c2!=null){ //将翻转队列的每一个元素依次插入到未翻转元素里面
                ListNode bak1 = c1.next;
                ListNode bak2 = c2.next;
                c2.next = c1.next;
                c1.next = c2;
                c1=bak1;
                c2=bak2;
        }


    }

    public ListNode reverse(ListNode head) {
        ListNode dummy = new ListNode(-1);
        while (head != null) {
            ListNode cur = head.next;
            ListNode removed = head;
            removed.next = dummy.next;
            dummy.next = removed;
            head = cur;
        }
        return dummy.next;
    }


    /**
     * 这是别人的解答https://leetcode.com/problems/reorder-list/discuss/44992/
     * ，思路跟我的解答一致
     * @param head
     */
    public void reorderList1(ListNode head) {
        if(head==null||head.next==null) return;

        //Find the middle of the list
        ListNode p1=head;
        ListNode p2=head;
        while(p2.next!=null&&p2.next.next!=null){
            p1=p1.next;
            p2=p2.next.next;
        }

        //Reverse the half after middle  1->2->3->4->5->6 to 1->2->3->6->5->4
        ListNode preMiddle=p1;
        ListNode preCurrent=p1.next;
        while(preCurrent.next!=null){
            ListNode current=preCurrent.next;
            preCurrent.next=current.next;
            current.next=preMiddle.next;
            preMiddle.next=current;
        }

        //Start reorder one by one  1->2->3->6->5->4 to 1->6->2->5->3->4
        p1=head;
        p2=preMiddle.next;
        while(p1!=preMiddle){
            preMiddle.next=p2.next;
            p2.next=p1.next;
            p1.next=p2;
            p1=p2.next;
            p2=preMiddle.next;
        }
    }
    public static void main(String[] args) {

        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        //n1.next = n2;n2.next = n3;n3.next = n4;n4.next = n5;
        new ReorderList().reorderList(n1);
    }
}
