package leetcode;

/**
 * Created by wuchang at 1/9/18
 * Question 142
 *
 [Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/description/)
 [Linked List Cycle II](https://leetcode.com/problems/linked-list-cycle-ii/description/)
 两题可以类比一下。

 [Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/description/)这里判断一个链表是否有环。
 我们同样用两个指针，一个指针slow每次移动一步，一个指针fast每次移动两步。如果slow经过step步两个指针相遇，这时候fast肯定比slow多走的步数肯定就是环的大小，比如：
 ```
 1 -  2 - 3 -  4
 |     |
 6 - 5
 ```
 这个环的大小是4，肯定，slow经过4步，fast经过8步，二者相遇。
 有了这个环的大小，我们就可以确定环的起始位置3了：
 我们让slow和fast回归到链表头，然后让fast先走4步，然后slow和fast一起走，这样，fast和slow肯定会在3这里相遇。

 */

public class LinkedListCycleII {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }


    public ListNode detectCycle(ListNode head) {
         ListNode fast = head;
        ListNode slow = head;
        int step=0;
        while(fast!=null && slow!=null){
            step++;
            slow = slow.next;
            if(fast.next==null)
                return null;
            fast = fast.next.next;
            if(slow == fast)
            {
                slow = fast = head;
                while(step-->0)
                    fast = fast.next; //让fast先走step步
                while(slow!=fast){  //只要slow和fast还没有相遇，二者就一起往前走
                    slow=slow.next;
                    fast=fast.next;
                }
                return slow;  //最终，slow和fast肯定会在环的起始位置相遇
            }

        }
        return null;

    }

    public static void main(String[] args) {
        ListNode  l1 = new ListNode(1);
        ListNode  l2 = new ListNode(2);
        ListNode  l3 = new ListNode(3);
        ListNode  l4 = new ListNode(4);
        ListNode  l5 = new ListNode(5);
        ListNode  l6 = new ListNode(6);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;
        l5.next = l6;
        l6.next = l1;
        new LinkedListCycleII().detectCycle(l1);
    }

}
