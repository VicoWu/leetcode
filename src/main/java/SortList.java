/**
 *
 * Question 148
 *
 * 由于需要一个nlogn复杂度的，因此，基于数组序号的二分插入排序(链表无法使用随机序号)、堆排序(需要使用O(n)的空间复杂度)无法完成，只能使用归并排序。

 基于两个已经排序完毕的有序数组进行归并为一个有序数组，时间复杂度是O(n)，空间复杂度是O(1).

 注意，在完成一次归并以后的队列应该是一个合法的队列，因此，这个队列的队尾的tail.next应该为ull,否则，有可能最后的确成功排序，
 但是队列的队尾tail.next却指向队列中的某个元素，造成队列遍历死循环。
 */
public class SortList {

     public static class ListNode {
          int val;
          ListNode next;
          ListNode(int x) { val = x; }
      }


    public ListNode sortList(ListNode head) {
         ListNode current = head;int length=0;
         ListNode tail=head;
         if(head == null) return head;
         while(current !=null) {
             tail = current;
             current = current.next;
             length++;
         }

         return merge(head,tail,length);

    }


    private ListNode merge(ListNode start,ListNode end,int length){
         end.next = null;
         if(length<=2){ //长度小于等于二
             if(start.val > end.val){ //交换
                 end.next = start;
                 start.next = null;
                 return end;
             }
                 return start;
         }
         else{
             int k=0;ListNode cursor = start;
             while(k++ < (length/2)){
                 cursor=cursor.next;
             }
             ListNode cursorNext  = cursor.next;
             ListNode header1 = merge(start,cursor,k);
             ListNode header2 = merge(cursorNext,end,length-k);
             return mergeSortedList(header1,k,header2,length-k);

         }
    }

    private ListNode mergeSortedList(ListNode start1,int k1,ListNode start2,int k2){
        ListNode header=new ListNode(-1);
        ListNode current = header;
        ListNode cursor1 = start1;
        ListNode cursor2 = start2;
        while(k1 > 0 || k2 > 0){
            while(k1>0 && (k2==0 || cursor1.val<=cursor2.val) ){
                current.next=cursor1;
                System.out.println("add "+cursor1.val);
                cursor1 = cursor1.next;
                k1--;
                current = current.next;

            }
            while(k2>0 && (k1 ==0|| cursor1.val>cursor2.val) ){

                current.next = cursor2;
                System.out.println("add "+cursor2.val);
                cursor2 = cursor2.next;
                k2--;
                current = current.next;
            }
        }
        current.next = null;
        return header.next;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        ListNode l2 = new ListNode(1);
        ListNode l3 = new ListNode(1);
        ListNode l4 = new ListNode(1);
        ListNode l5 = new ListNode(1);
        ListNode l6 = new ListNode(1);
        l1.next=l2;l2.next=l3;l3.next=l4;l4.next=l5;l5.next=l6;

        ListNode rs = new SortList().sortList(l1);
        while(rs!=null){
            System.out.print(rs.val+" ");

            rs = rs.next;
        }
    }
}
