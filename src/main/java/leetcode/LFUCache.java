package leetcode;

import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * Created by wuchang at 2/27/18
 * Question 460
 */

public class LFUCache {
    HashMap<Integer, Integer> vals;
    HashMap<Integer, Integer> counts;

    //key 代表数量，value的 LinkedHashSet<Integer>代表访问次数为这个数量的所有的key，为什么要使用LinkedHashSet?首先，这里面的元素是不可以重复的，其次，又需要是线性的，这样头部元素就代表最久没有访问的元素
    HashMap<Integer, LinkedHashSet<Integer>> lists;
    int cap;
    int min = -1; //当前最小的访问次数，这样我们在进行evict的时候可以从lists中知道哪些key的访问次数最少，然后从中淘汰一个
    public LFUCache(int capacity) {
        cap = capacity;
        vals = new HashMap<>();
        counts = new HashMap<>();
        lists = new HashMap<>();
        lists.put(1, new LinkedHashSet<>());
    }

    public int get(int key) {
        if(!vals.containsKey(key))
            return -1;
        int count = counts.get(key); //获取这个元素之前的访问数量
        counts.put(key, count+1);  //访问数量+1
        lists.get(count).remove(key); //从旧的访问数量的map中删除
        if(count==min && lists.get(count).size()==0)
            min++; //当前最小的访问数量+1
        if(!lists.containsKey(count+1))
            lists.put(count+1, new LinkedHashSet<>());
        lists.get(count+1).add(key);
        return vals.get(key);
    }

    public void put(int key, int value) {
        if(cap<=0)
            return;
        if(vals.containsKey(key)) {//如果这个key存在，说明这是一次update，因此需要更新vals，然后更新counts即这个key的访问数量，然后更行lists
            vals.put(key, value);
            get(key);
            return;
        }
        if(vals.size() >= cap) { //如果容量满，则需要删除访问次数最小的值，如果这个访问次数最小的值有多个，则删除最远的一个
            int evit = lists.get(min).iterator().next(); //取出当前最小的访问数量
            lists.get(min).remove(evit);
            vals.remove(evit);
        }
        vals.put(key, value);
        counts.put(key, 1);
        min = 1;//由于是插入了一个新的元素，因此缓存的最小访问数量为1
        lists.get(1).add(key);
    }

    public static void main(String[] args) {
//        LinkedHashSet<Integer> set = new LinkedHashSet<Integer>();
//        set.add(1);
//        set.add(2);
//        set.add(2);
//        set.add(1);
//        System.out.println(set.iterator().next());
        LFUCache cache = new LFUCache( 2 /* capacity */ );

        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(2);
        cache.get(1);
        cache.put(3,3);
        cache.get(1);       // returns 1
        cache.put(3, 3);    // evicts key 2
        cache.get(2);       // returns -1 (not found)
        cache.get(3);       // returns 3.
        cache.put(4, 4);    // evicts key 1.
        cache.get(1);       // returns -1 (not found)
        cache.get(3);       // returns 3
        cache.get(4);       // returns 4


    }
}
