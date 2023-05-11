package leetcode;

/**
 * Created by wuchang at 12/26/17
 * Question 138
 *
 * # 我自己的解法
 # 暴力解法
 比较通用的做法，我们先通过next指针构建这个链表的拷贝，对于random指针，为了知道原链表的random指针指向的原链表的节点和拷贝链表的节点的对应关系
 我们就使用一个map来保存元链表和拷贝链表中每个节点的一一对应关系。但是这样显然需要O(N)的空间复杂度。

 # 链表归并再拆分

 我自己实现的原链表和拷贝链表先拼接再拆分的方法，这是在O(1)空间复杂度和O(n)时间复杂度范围内解答这一题比较通用的做法，具体做法是：
 1.  为原链表的每一个node创建拷贝节点，挂载到原节点后面
 2.  修改链表中的拷贝节点的random指针，指向对应的拷贝节点
 3.  将归并链表中的原链表和拷贝链表拆分处理

 以上3步的每一步都需要遍历一次链表。




 */

public class CopyListWithRandomPointer {
    public RandomListNode copyRandomList(RandomListNode head) {
        RandomListNode current = head;
        while(current !=null){ //为原链表的每一个node创建拷贝节点，挂载到原节点后面行程一个统一的链表
            RandomListNode copy = new RandomListNode(current.label);
            copy.random = current.random;
            RandomListNode tm = current.next;
            current.next = copy;
            copy.next = tm;
            current = tm;
        }

        current = head;

        //开始修改拷贝链表的random指针，指向对应的拷贝节点。由于每一个拷贝节点都挂载在源节点的后面，因此，拷贝节点的random指针应该就是它的原节点的random指向的节点的next节点
        while(current!=null){

            RandomListNode copy = current.next;
            if(current.random!=null)
                copy.random = current.random.next;
            current = copy.next;
        }

        RandomListNode copyHead =new  RandomListNode(-1); //创建一个copyHead头结点，是为了便于在遍历的时候统一处理，不需要进行空指针判断
        RandomListNode cursor = copyHead;
        current = head;
        //从统一链表中剥离还原出原链表和拷贝链表
        while(current!=null){
            RandomListNode copy = current.next;
            current.next = copy.next;
            current = current.next;
            cursor.next = copy;
            cursor = copy;
        }
        return copyHead.next;
    }

    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList2(Node head) {
        if(head == null)
            return null;
        Node cur = head;
        Node tail = new Node(-1);
        tail.next = head;
        while(cur != null){
            tail.next = cur;

            Node copy = new Node(cur.val);
            Node nextHead = cur.next;

            cur.next = copy;
            copy.next = null;
            tail = copy;
            cur = nextHead;
        }

        cur = head;
        while(cur != null){
            if(cur.random != null){
                cur.next.random = cur.random.next;
            }
            cur = cur.next.next;
        }

        Node pivotOfSource = new Node(-1);
        Node curOfSource = pivotOfSource;
        Node pivotOfCopy = new Node(-1);
        Node curOfCopy = pivotOfCopy;
        cur = head;
        while(cur!= null){
            Node nextBoth = cur.next.next;
            curOfSource.next = cur;
            curOfCopy.next = cur.next;
            curOfSource = curOfSource.next;
            curOfCopy = curOfCopy.next;
            curOfSource.next = null;
            curOfCopy.next = null;
            cur = nextBoth;
        }
        return pivotOfCopy.next;
    }

    public static void main(String[] args) {
        Node a = new Node(-1);
//        Node b = new Node(13);
//        Node c = new Node(11);
//        Node d = new Node(10);
//        Node e = new Node(1);
//        a.next = b;
//        b.next = c;
//        c.next = d;
//        d.next = e;
//        b.random = a;
//        c.random = e;
//        d.random = c;
//        e.random = a;

        new CopyListWithRandomPointer().copyRandomList2(a);
    }
   static class RandomListNode {
         int label;
          RandomListNode next, random;
        RandomListNode(int x) { this.label = x; }
    }



}
