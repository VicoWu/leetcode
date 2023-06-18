package leetcode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 我的这种算法容易超时，超时原因在于我把跟访问次数相关的信息放到了一个统一的链表里面，那么，当有很多的节点的访问次数相同（比如，都访问了一次），那么，每假如一个节点，都需要从链表
 * 尾部往头部一直找到自己的插入位置。
 * 改进方法是，构建一个map，map的key是concurrency，value是对应访问次数的所有item，每次插入，只需要插入到链表头即可
 */
public class LFUCache2 {
    Map<Integer, Item> map = new HashMap();
    Map<Integer, LinkedList<Item>> freqMap= new HashMap();
    private int capacity;
    private int currentNum;
    private int minFreq;

    public LFUCache2(int capacity) {
        this.currentNum = 0;
        this.capacity = capacity;
        minFreq = 0;
    }

    public int get(int key) {
        if(!map.containsKey(key)){
            return -1;
        }
        Item item = map.get(key);

        removeFromCurrentFreqQueue(item);
        if(minFreq == item.freq && freqMap.get(item.freq).size() == 0){
            minFreq++;
        }
        item.accessReadonly();
        addToNewFreqQueue(item);
        return item.value;
    }

    public void put(int key, int value) {
        Item newItem;
        if(!map.containsKey(key)){
            if(this.currentNum == this.capacity){
                evict();
            }
            LinkedList<Item> queue = new LinkedList();
            newItem = new Item(key, value);
            minFreq = 1;
            queue.add(newItem);
            map.put(key, newItem);
            this.currentNum++;
        }else{
            newItem = map.get(key);
            removeFromCurrentFreqQueue(newItem);
            if(minFreq == newItem.freq && freqMap.get(newItem.freq).size() == 0){
                minFreq++;
            }
            newItem.accessWithNewValue(value);
        }
        addToNewFreqQueue(newItem);
    }

    public void removeFromCurrentFreqQueue(Item item){
        LinkedList q = freqMap.get(item.freq);
        q.remove(item);
    }

    public void addToNewFreqQueue(Item item){
        if(!freqMap.containsKey(item.freq)){
            LinkedList<Item> queue = new LinkedList();
            freqMap.put(item.freq, queue);
        }
        LinkedList q = freqMap.get(item.freq);
        q.offerFirst(item);
    }

    public void evict(){
        LinkedList<Item> q = freqMap.get(minFreq);
        Item removed = q.removeLast();
        map.remove(removed.key);
        currentNum--;
    }

    class Item{
        public int key;
        public int freq;
        public int value;
        public Item next;
        public Item pre;
        public Item(int key, int value){
            this.key = key;
            this.value = value;
            this.freq = 1;
            this.next = this.pre = null;
        }

        public void accessWithNewValue(int value){
            freq++;
            this.value = value;
        }

        public void accessReadonly(){
            freq++;
        }
    }

    public static void main(String[] args) {
        LFUCache2 cache = new LFUCache2(2);
        cache.put(3,1);
        cache.put(2,1);
        cache.put(2,2);
        cache.put(4,4);

        cache.get(2);
    }
}
