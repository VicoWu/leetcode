package leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Question 295
 * Created by wuchang at 12/15/17
 *
 * # 通过两个堆时刻维护中间的值
 定义一个大顶堆和一个小顶堆（java里面的PriorityQueue默认是小顶堆，如果是大顶堆定义需要自定义Comparator），
 大顶堆存放排序以后的左侧值，小顶堆存放排序以后的右侧值，因此，中位数肯定是两个堆的堆顶中的一个或者二者的平均值。
 我们规定，如果元素个数是偶数，则大顶堆和小顶堆元素个数相同，中位数是两个堆顶元素的平均值，而如果是基数，则大顶堆元素个数比小顶堆多一个，
 因此中位数是大顶堆的堆顶值。因此插入的时候需要有一些讲究。

 当然，如果也可以只用默认的PriorityQueue维护，通过将元素取负值放到小顶堆，间接起到大顶堆的作用。
 https://discuss.leetcode.com/topic/27521/short-simple-java-c-python-o-log-n-o-1/3


 */

public class FindMedianFromDataStream {



    Comparator<? super Long> smallC = new Comparator<Long>() {
        @Override
        public int compare(Long o1, Long o2) {
            return o1<o2?-1:(o1==o2?0:1);
        }
    };

    Comparator<? super Long> largeC = new Comparator<Long>() {
        @Override
        public int compare(Long o1, Long o2) {
            return o1<o2?1:(o1==o2?0:-1);
        }
    };

    private Queue<Long> small = new PriorityQueue(smallC), large = new PriorityQueue(largeC);

    /**
     * 任何时候保持large的元素个数不小于small的元素个数
     */
    public FindMedianFromDataStream() {
    }

    public void addNum(int num) {

        if(large.size() == small.size()){
            large.add((long)num);
            small.add(large.poll());
            large.add(small.poll());
        }
        else{
            small.add((long)num);
            large.add(small.poll());
            small.add(large.poll());
        }
    }


    public double findMedian() {
        if(large.size() > small.size()) return large.peek();
        return ((double)large.peek()+small.peek())/2;
    }


    public static void main(String[] args) {
        FindMedianFromDataStream f = new FindMedianFromDataStream() ;
        f.addNum(1);
        System.out.println(f.findMedian());
        f.addNum(2);
        System.out.println(f.findMedian());
        f.addNum(3);
        System.out.println(f.findMedian());
        f.addNum(4);
        f.addNum(5);
        System.out.println(f.findMedian());
    }
}
