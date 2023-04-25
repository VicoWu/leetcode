package leetcode;

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
        ListNode newHead = new ReverseNodesInKGroup().reverseKGroup2(n1,2);

        System.out.println(newHead);
    }

   public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

    public ListNode reverseKGroup2(ListNode head, int k) {
        ListNode pivot = new ListNode();
        pivot.next = head;
        //始终维护上一个group的尾部节点，这样当当前group反转结束以后，link到上一个group的尾部
        ListNode preGroupTail = pivot;
        ListNode currentGroupHead = pivot.next;
        while(currentGroupHead != null){
            ListNode currentGroupTail = currentGroupHead;
            int i = 1;
            for(;i<k && currentGroupTail!=null;i++){
                currentGroupTail = currentGroupTail.next;
            }
            // 已经不足k个节点，那么剩下的链表不需要反转，挂到后面，直接退出，结束
            if(currentGroupTail == null){
                preGroupTail.next = currentGroupHead;
                break;
            }
            // 在反转以前，暂时保存下一个group的头节点
            ListNode nextGroupHead = currentGroupTail.next;//
            // 当前正在处理的group的头节点
           // ListNode currentGroupHead = preGroupTail.next;
            // 反转当前的kgroup，并返回反转以后的头节点
            reverse2(currentGroupHead, currentGroupTail);
            //  将反转以后的group挂载到前一个group的tail
            preGroupTail.next = currentGroupTail;
            //  更新tail为反转以后的列表的tail
            preGroupTail = currentGroupHead;
            // 更新下一个group的head
            currentGroupHead = nextGroupHead;
        }
        return pivot.next;
    }

    /**
     * 这段代码退出以后，head变成了尾节点，tail变成了头节点
     * @param head
     * @param tail
     */
    private void reverse2(ListNode head, ListNode tail){
        ListNode[] result = new ListNode[2];
        if(head == tail) { // 只有一个节点
            head.next = null;
            return;
        }
        ListNode pivot = new ListNode();
        pivot.next = head;
        ListNode cur = head.next;
        // 反转以后的链表的尾节点next 为空
        head.next = null;
         // 设置为空，好作为退出判断条件
        tail.next = null;
        while(cur != null){
            ListNode newCur = cur.next;
            cur.next = pivot.next;
            pivot.next = cur;
            cur = newCur;
        }
        return;

    }

}


