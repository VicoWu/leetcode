package leetcode;

import java.util.HashMap;

/**
 * Question 146
 *
 * 由于get()操作需要在O(1)的时间内完成，因此必须使用hash进行，因为不使用hash对有序元素进行查找时间复杂度最低是 O(logn)，无序元素则是 o(n)。
 同时，在进行get或者put的时候，被get或者put的元素都必须成为一个最新的元素，其他元素都变旧，因此联想到栈。但是
 使用栈进行操作，无法实现中间元素的移动(比如查询某一个中间元素)，因此，联想到双向链表。结合hash和双向链表，
 既可以实现在O(1)的时间范围内进行查询，同时，也能在O(1)的时间范围内将查询到的某个节点进行移动；

 之前我想的是将LRU的数据结构抽象成一个Array，
 使用根据key%capacity进行放置，然后使用两个变量分别存放oldest和latest的索引位置，但是，
 可能存在这样的情况，加入capacity=10，然后后面插入的元素的key虽然不同，但是key%capacity却相同，
 比如，1，11，21等等。这时候这些元素应该如何放置是一个问题。因此改为使用HashMap，这样
 ，任何元素都可以控制在O(1)的时间内找到。但是注意，当数据数量等于capacity的时候，
 我们需要找到最旧的元素将其删除，再把新的元素插入为最新值。





 */
public class LRUCache {

    HashMap<Integer,CacheNode> cache  = new HashMap<Integer,CacheNode>();
    private int capacity;
    private int currentCap;
    private CacheNode latest;
    private CacheNode oldest;
    public LRUCache(int capacity) {
        this.oldest = this.latest = null;
        this.capacity = capacity;
        currentCap = 0;
    }

    public int get(int key) {

        CacheNode cacheNode = cache.get(key);
        if(cacheNode == null) //这个位置没有值，或者有值，但是key不匹配
            return -1;
        else{//找到
            System.out.println("get "+key);
            this.updateLatest(cacheNode);
            return cacheNode.getValue();
        }
    }

    private void printCache(){
        CacheNode cursor = oldest;
        StringBuilder sb = new StringBuilder();
        while(cursor!=latest)
        {
            sb.append(cursor.key+" ");
            cursor = cursor.next;
        }
        sb.append(latest.key);
        System.out.println("cache is "+sb.toString());
    }

    private void updateLatest(CacheNode cacheNode){
        if(cacheNode.previous == null) //这刚好是最旧的一个节点
        {
            oldest =cacheNode.next;
            oldest.previous = null;

            latest.next = cacheNode;
            cacheNode.previous=latest;
            cacheNode.next = null;
            latest = cacheNode;
        }
        else{
            if(currentCap<=2){ //当前只有两个或者只有一个节点，指针顺序不需要改变，只需要修改latest和earliest
                latest= cacheNode;
                oldest = cacheNode.next;
            }
            else{
                if(cacheNode == oldest )oldest = cacheNode.next; //可能需要更新oldest

                if(cacheNode == latest) return;
                cacheNode.previous.next = cacheNode.next;
                cacheNode.next.previous = cacheNode.previous;


                latest.next = cacheNode; //更新latest和cacheNode之间的指针
                cacheNode.previous = latest;



                oldest.previous = cacheNode;
                cacheNode.next = oldest;

                latest = cacheNode;

            }
        }
    }


    public void put(int key, int value) {

        System.out.println("put ["+key+","+value+"]");
        CacheNode cacheNode = cache.get(key);
        if(cacheNode == null ){ //insert
            //we should consider if the cache is full
            if(currentCap==capacity){ //the cache is full,replace the oldest
                cache.put(key,oldest);
                cache.remove(oldest.getKey());
                oldest.setKV(key,value);
                this.updateLatest(oldest);
            }else{ //the cache still has remaining location,insert a new node
                CacheNode node = new CacheNode(key,value,this.latest,this.oldest);
                cache.put(key,node);
                if(this.latest==null) //如果这个节点是第一个节点
                {
                    this.latest =  this.oldest=node;
                }
                this.oldest.previous = this.latest.next=node;
                this.latest = node;
                currentCap++;
            }
        }
        else{ //set ,no matter the key is equals or not
            cacheNode.setKV(key,value);
            this.updateLatest(cacheNode);
        }

        printCache();
    }

    private static class CacheNode {
        CacheNode previous;
        CacheNode next;
        int key;
        int value;

        public CacheNode(int key, int value,CacheNode previous,CacheNode next) {
            this.key = key;
            this.value = value;
            this.previous = previous;
            this.next = next;
        }

        public void setKV(int key,int value){
            this.key=key;
            this.value = value;
        }

        public CacheNode getPrevious() {
            return previous;
        }

        public void setPrevious(CacheNode previous) {
            this.previous = previous;
        }

        public CacheNode getNext() {
            return next;
        }

        public void setNext(CacheNode next) {
            this.next = next;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }


    public static void main(String[] args) {
        LRUCache cache = new LRUCache( 10 /* capacity */ );


        cache.put(10,13);
        cache.put(3, 17);
        cache.put(6,11);
        cache.put(10, 5);
        cache.put(9,10);
        cache.get(13);
        cache.put(2, 19);
        cache.get(2);
        cache.get(3);       // returns 1
        cache.put(5, 25);    // evicts key 2
        cache.get(8);


        cache.put(9,22);
        cache.put(5, 5);
        cache.put(1,30);
        cache.get(11);

        cache.put(9, 12);
        cache.get(7);       // returns 3
        cache.get(5);       // returns 4
        cache.get(8);
        cache.get(9);

        cache.put(4,30);

        cache.put(9,3);

        cache.get(9);       // returns -1 (not found)
        cache.get(10);       // returns 3
        cache.get(10);       // returns 4


        cache.put(6,14);
        cache.put(3,1);

        cache.get(3);

        cache.put(10, 11);

        cache.get(8);

        cache.put(2,14);

        cache.get(1);       // returns 4
        cache.get(5);
        cache.get(4);


        cache.put(11,4);
        cache.put(12,24);
        cache.put(5,18);
        cache.get(13);       // returns 4


        cache.put(7, 23);

        cache.get(8);
        cache.get(12);

        cache.put(3,27);
        cache.put(2,12);

        cache.get(5);       // returns 4



        cache.put(2,9);
        cache.put(13,4);
        cache.put(8,18);

        cache.put(1,7);


    }
}
