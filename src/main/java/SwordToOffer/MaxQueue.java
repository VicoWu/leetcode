package SwordToOffer;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 剑指 Offer 59 - II. 队列的最大值
 */
public class MaxQueue {
    Queue<Integer> q;
    Deque<Integer> d;
    public MaxQueue() {
        q = new LinkedList<Integer>();
        d = new LinkedList<Integer>();
    }

    public int max_value() {
        if(q.isEmpty()) return -1;
        return d.peekFirst();
    }

    public void push_back(int value) {
        while(!d.isEmpty() && d.peekLast() < value) {
            d.pollLast();
        }
        q.offer(value);
        d.offerLast(value);
    }

    public int pop_front() {
        if(q.isEmpty()) return -1;
        int res = q.poll();
        if(res == d.peekFirst()) d.pollFirst();
        return res;
    }
}
