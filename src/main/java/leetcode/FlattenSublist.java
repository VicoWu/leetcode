package leetcode;

public class FlattenSublist {
    class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;

        public Node(int val) {
            this.val = val;
        }
    };

    public Node flatten(Node head) {
        return flattenSubList(head)[0];
    }

    /**
     返回head为头部的链表的首尾节点

     */
    private Node[] flattenSubList(Node head){
        Node[] res = new Node[2];
        res[0] = head;
        Node cur = head;
        while(cur!=null){
            if(cur.child != null){
                Node next = cur.next;
                Node[] sub = flattenSubList(cur.child);
                cur.child = null;
                cur.next = sub[0];
                sub[0].prev = cur;

                sub[1].next = next;
                if(next != null){
                    next.prev = sub[1];
                }
                // 更新cur为child链表的尾节点
                cur = sub[1];
            }
            //保存当前的尾节点
            res[1] = cur;
            //处理下一个节点
            cur = cur.next;
        }
        //返回以head为头的整个链表的头尾节点
        return res;
    }

    public static void main(String[] args) {
        FlattenSublist f1 = new FlattenSublist();
        FlattenSublist f2 = new FlattenSublist();
        FlattenSublist f3 = new FlattenSublist();
        FlattenSublist.Node n1 = f1.new Node(1);
        FlattenSublist.Node n2 = f1.new Node(2);
        FlattenSublist.Node n3 = f1.new Node(3);
        n1.next = n2;
        n2.prev = n1;
        n1.child = n3;
        f1.flatten(n1);
    }
}
