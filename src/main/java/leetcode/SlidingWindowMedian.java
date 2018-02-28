package leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by wuchang at 2/27/18
 * Question 480
 这个问题我没有直接实现，拷贝的代码来自[讨论区](https://leetcode.com/problems/sliding-window-median/discuss/96348/Java-solution-using-two-PriorityQueues)
 主要思想是：
 在流数据不断进来的时候，始终维护一个大顶堆和一个小顶堆，大顶堆是左侧较小的一半数据，小顶堆是右侧较大的一半数据。由于中位数分为两种情况，当窗口大小为奇数，中位数为中间的那个数字，当窗口大小为偶数，中位数为中间两个数字的均值。因此，我们规定，当为偶数的时候，自然两个堆的size必须相等，当为奇数的时候，只能右侧小顶堆比左侧大顶堆的大小多1 。显然，如果窗口为偶数，则中位数为两个堆顶元素的均值，如果为奇数，则中位数是小顶堆的堆顶元素。
 相似问题
 [239. Sliding Window Maximum](https://leetcode.com/problems/sliding-window-maximum/description/)
 [480. Sliding Window Median](https://leetcode.com/problems/sliding-window-median/description/)
 [346	Moving Average from Data Stream](http://www.cnblogs.com/grandyang/p/5450001.html)，这个从流中求均值最简单


 */

public class SlidingWindowMedian {
    PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();//小顶堆，维护右侧所有较大的元素
    PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>( //大顶堆，维护左侧所有较小的元素
            new Comparator<Integer>() {
                public int compare(Integer i1, Integer i2) {
                    return i2.compareTo(i1);
                }
            }
    );

    public double[] medianSlidingWindow(int[] nums, int k) {
        int n = nums.length - k + 1;
        if (n <= 0) return new double[0];
        double[] result = new double[n];

        for (int i = 0; i <= nums.length; i++) {
            if (i >= k) { //从第k个数开始，计算中位数
                result[i - k] = getMedian();
                remove(nums[i - k]); //把窗口的第一个数字删除
            }
            if (i < nums.length) {
                add(nums[i]);//添加这个数字
            }
        }

        return result;
    }

    /**
     * 插入一个元素，插入过程中需要始终让小顶堆的元素与大顶堆的元素相同(当元素个数是偶数)，或者小顶堆的元素个数比大顶堆的元素个数多1(当元素个数是奇数)
     * @param num
     */
    private void add(int num) {
        if (num < getMedian()) { //如果插入的数字小于中位数，那么就把这个数字插入到存放较小元素的大顶堆中
            maxHeap.add(num);
        }
        else {//如果插入的数字大于中位数，那么就把这个数字插入到存放较大元素的小顶堆中
            minHeap.add(num);
        }
        if (maxHeap.size() > minHeap.size()) { //需要始终让小顶堆的元素不小于大顶堆的元素
            minHeap.add(maxHeap.poll());
        }
        if (minHeap.size() - maxHeap.size() > 1) { //如果小顶堆的元素比大顶堆的元素的数量大于1，则应该从小顶堆中拿出元素放入大顶堆中
            maxHeap.add(minHeap.poll());
        }
    }

    private void remove(int num) {
        if (num < getMedian()) {
            maxHeap.remove(num);
        }
        else {
            minHeap.remove(num);
        }

        //跟add()方法相同个，时刻保持小顶堆的元素与大顶堆的元素相同(当元素个数是偶数)，或者小顶堆的元素个数比大顶堆的元素个数多1(当元素个数是奇数)
        if (maxHeap.size() > minHeap.size()) {
            minHeap.add(maxHeap.poll());
        }
        if (minHeap.size() - maxHeap.size() > 1) {
            maxHeap.add(minHeap.poll());
        }
    }

    private double getMedian() {
        if (maxHeap.isEmpty() && minHeap.isEmpty()) return 0;

        if (maxHeap.size() == minHeap.size()) { //如果两个堆数量相同，说明窗口的大小是偶数，因此中位数是两个堆顶元素的均值
            return ((double)maxHeap.peek() + (double)minHeap.peek()) / 2.0;
        }
        else {//两个堆的大小不同，minHeap的堆顶元素就是中位数
            return (double)minHeap.peek();
        }
    }

}
