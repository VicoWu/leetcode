package leetcode;

public class AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode cur1 = l1;
        ListNode cur2 = l2;
        int shortLength = 0;
        while(cur1 != null && cur2 != null){
            shortLength++;
            cur1 = cur1.next ;
            cur2 = cur2.next;
        }
        int sub = 0;
        ListNode longList = null;
        ListNode shortList = null;
        if(cur1 == null){
            longList = l2;
            shortList = l1;
            while(cur2 != null){ //
                cur2 = cur2.next;
                sub++;
            }
        }else{
            longList = l1;
            shortList = l2;
            while(cur1 != null){
                cur1 = cur1.next;
                sub++;
            }
        }
        String a="";
        if(a.charAt(0) == a.charAt(1))
            System.out.println(1);
        ListNode pivot = new ListNode(0);
        int add = addTwoNumbers(longList, shortList, pivot, 1, sub);
        if(add != 0){
            ListNode first = new ListNode(add);
            first.next = pivot.next;
            pivot.next = first;
        }
        return pivot.next;
    }

    public int addTwoNumbers(ListNode longList, ListNode shortList, ListNode pivot, int depth, int sub){
        if(depth <= sub){ // 当前还处在较长链表的较长部分
            int add = addTwoNumbers(longList.next, shortList, pivot, depth+1, sub);
            int cur = (add + longList.val)%10;
            ListNode curNode = new ListNode(cur);
            curNode.next = pivot.next;
            pivot.next = curNode;
            return (add + longList.val)/10;
        }else{ // 进入了等长部分
            if(longList == null && shortList == null){
                return 0;
            }
            int add = addTwoNumbers(longList.next, shortList.next, pivot, depth+1, sub);
            int cur = (add + longList.val + shortList.val)%10;
            ListNode curNode = new ListNode(cur);
            curNode.next = pivot.next;
            pivot.next = curNode;
            return (add + shortList.val + longList.val)/10; // 返回向高位的进位
        }
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(7);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(4);
        ListNode l4 = new ListNode(3);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;

        ListNode l5 = new ListNode(5);
        ListNode l6 = new ListNode(6);
        ListNode l7 = new ListNode(4);
        l5.next = l6;
        l6.next = l7;
        new AddTwoNumbers().addTwoNumbers(l1, l5);

    }
}
