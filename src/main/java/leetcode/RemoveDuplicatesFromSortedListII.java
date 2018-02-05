package leetcode;

/**
 * Created by wuchang at 1/8/18
 * Question 82
 注意，一旦某个节点由重复，就需要全部删除，而不是只保留一个。
 因此，对于任何一个节点，我们都判断前置节点和后置节点是不是与自己相同。只要前置节点或者后置节点中有一个与当前节点的 值相同，就应该删除。
 使用preValue保存上一个节点的值。
 */

public class RemoveDuplicatesFromSortedListII {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        Integer preValue = null; //上一个值
        ListNode pre = dummy;
        while(head!=null){


            if((preValue!=null && head.val == preValue) || (head.next!=null && head.next.val == head.val) ){ //发现重复，需要删除一个节点
                preValue = head.val;//head即将被删除，保存它的值到preValue
                head = head.next;//head指向下一个节点
                pre.next = head;//删除节点，让pre.next直接指向下一个节点
            }
            else{ //不需要删除节点
                preValue = head.val;//保存当前节点的值
                pre = head;
                head = head.next;
            }

        }
        return dummy.next;
    }

    public static void main(String[] args) {

        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(3);
        head.next.next.next.next = new ListNode(4);
        head.next.next.next.next.next = new ListNode(4);
        head.next.next.next.next.next.next = new ListNode(5);
        new RemoveDuplicatesFromSortedListII().deleteDuplicates(head);
    }
}
