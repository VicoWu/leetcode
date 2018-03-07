package leetcode;

/**
 * Created by wuchang at 1/9/18
 * Question 142
 *
 [Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/description/)
 [Linked List Cycle II](https://leetcode.com/problems/linked-list-cycle-ii/description/)
 两题可以类比一下。

 [Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/description/)这里判断一个链表是否有环。[这里](http://blog.csdn.net/sinat_35261315/article/details/79205157)有比较详细的解释。
 假设从表头到环的入口的长度是L，环大小是R，相遇点距离环入口的长度是S，显然，相遇的时候，slow走了`L+S`, fast走了  `L+S+nR` , 因此， `L+S+nR = 2(L+S)` , 即 `L+S = nR;`
 我们同样用两个指针，一个指针slow每次移动一步，一个指针fast每次移动两步。如果slow经过step步两个指针相遇，这时候fast肯定比slow多走的步数肯定就是环的大小的整数倍，比如：
 ```
 0 - 1 -  2 - 3 -  4
 |     |
 6 - 5
 ```
 这里L=2， R= 4
 A每次移动一步，B每次移动2步：
 ```
 Slow  Fast
 0        0
 1        2
 2        4
 3        6
 4        4
 ```
 显然，他们在4处相遇，即S=2 ， 且n=1
 这个环的大小是4，肯定，slow经过4步，fast经过8步，二者相遇。
 有了这个环的大小，我们就可以确定环的起始位置3了：
 我们让slow和fast回归到链表头，然后让fast先走4步，然后slow和fast一起走，这样，fast和slow肯定会在3这里相遇。

 另外一种情况：
 ```
 0 -1 -2 -3 -4 -5 -6 -7 -8 -9 -10 -11
 |    |
 13 -12
 ```
 ```
 Slow Fast
 0       0
 1       2
 2       4
 3       6
 4       8
 5       10
 6       12
 7       10
 8       12
 9       10
 12       12
 11       10
 12       12
 ```
 显然，L=10，R=4 ,相遇点距离环的入口的距离为2即S=2 , 并且相遇前fast绕环走了3圈，即n=3
 即 `L+S = 12 = nR = 3*4`
 此时，我们让一个指针A从链表表头开始走，一个指针B从相遇点开始走，两个步速相同，由于L=nR - S, 显然，当指针A走到环的入口，即走了L=nR - S步，指针B由于也走了nR-S 步，此时指针B肯定也在环的入口，即相遇了，且相遇位置正好就是环的入口。







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
