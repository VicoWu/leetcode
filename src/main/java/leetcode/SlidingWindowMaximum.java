package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Question 239
 ## 我之前想的方法：
 对于每一个window，保留这个window的最大值、左侧值和右侧值，这样，这个窗口在向右移动的过程中，只需要根据上一个窗口的最大值、左侧值和右侧值以及新窗口最新的右侧值(因为窗口向右移动)来获得新窗口的最大值、左侧值和右侧值，但是，这个无法获得，比如：1 [3  -1  -3] -4  3  6  7 此时窗口的最大值是最左侧的值，窗口向右移动一步，新的右边界为-4，比上一个窗口的最大值3要小，因此此时新窗口的最大值在内部而不是边界。

 ## 基于堆栈
 在代码maxSlidingWindow2()中的算法是基于堆栈的：
 * 这个算法的思想是，由于窗口是向右移动，假如由于窗口移动而新加入进来的元素大于它左侧的元素，那么它左侧的元素就可以抛弃，因为以后的窗口都是在该窗口的右侧，此时
 * 这个左侧较小的元素可以抛弃
 * 可是，如果右侧的元素小于左侧的元素，这个右侧较小元素不可以抛弃，因为这个右侧较小的元素在窗口右移的过程中可能就成为最大元素。
 * dequeue的队首始终保存的是当前window的最大元素

 ## 基于窗口切分：
 代码看[这里](https://discuss.leetcode.com/topic/26480/o-n-solution-in-java-with-two-simple-pass-in-the-array)：
 这个方法的基本思路是：现将原始数据基于k进行切分：
 2, 1, 3, 4 | 6, 3, 8, 9 | 10, 12, 56
 最后的位置可能不足k个。想象着一个宽度为k的窗口逐渐移动，移动过程中，这个窗口或者不被切分，比如，窗口为[2,1,3,4]或者窗口为[6,3,8,9],或者窗口被切分，比如，窗口[3,4,6,3]，被切分为[3,4]和[6,3]，被切分的窗口，只需要获得两个子窗口[3,4]和[6,3]各自的最大值，就可以获得当前窗口[3,4,6,3]的最大值。[3,4]的最大值可以从左到右遍历原始数据，获取每一个切分的最大值序列：`2, 2, 3, 4 | 6, 6, 8, 9 | 10, 12, 56`，命名为A,[6,3]的最大值可以从右向左遍历，获得每一个切分的最大值序列：`4,4,4,4|9,9,9,9|56,56,56`，命名为B，这样，[3,4]的最大值就是B[2]=4（2是数字3所在的位置），[6,3]的最大值就是A[5]=6（5是数字3所在的位置），从而，[3,4,6,3]的最大值是6.


 相似问题
 [239. Sliding Window Maximum](https://leetcode.com/problems/sliding-window-maximum/description/)
 [480. Sliding Window Median](https://leetcode.com/problems/sliding-window-median/description/)
 [346	Moving Average from Data Stream](http://www.cnblogs.com/grandyang/p/5450001.html)，这个从流中求均值最简单


 力扣： https://leetcode-cn.com/problems/sliding-window-maximum/solution/dong-hua-yan-shi-dan-diao-dui-lie-239hua-hc5u/

 */
public class SlidingWindowMaximum {



    /**
     * 这个算法的思想是，由于窗口是向右移动，因此，如果右侧的元素大于左侧的元素，这个元素肯定不会影响以后窗口的最大值的结果，因为以后的窗口都是在该窗口的右侧，此时
     * 这个左侧较小的元素可以抛弃
     * 可是，如果右侧的元素小于左侧的元素，这个右侧较小元素不可以抛弃，因为这个右侧较小的元素在窗口右移的过程中可能就成为最大元素。
     * dequeue的队首始终保存的是当前window的最大元素
     * @param a
     * @param k
     * @return
     */
    public int[] maxSlidingWindow2(int[] nums, int k) {
        if (nums == null || k <= 0) {
            return new int[0];
        }
        int n = nums.length;
        int[] r = new int[n-k+1];
        int ri = 0;
        // 注意，队列中存放的是序号，不是元素的值
        Deque<Integer> q = new ArrayDeque<Integer>();
        for (int i = 0; i < nums.length; i++) {
            // remove numbers out of range k
            while (!q.isEmpty() && q.peek() < i - k + 1) { //从队列头中去掉序号小于i-k+1即落于窗口之外的数
                q.poll();
            }
            // remove smaller numbers in k range as they are useless
            while (!q.isEmpty() && nums[q.peekLast()] < nums[i]) { //如果上一次进入栈的元素小于当前元素，则把上一次进入栈的元素出栈，这样，队列的队首元素肯定是当前窗口的最大元素
                q.pollLast();
            }
            // q contains index... r contains content
            q.offer(i); //当前元素进入栈
            if (i >= k - 1) { //队首的元素肯定是当前窗口的最大元素
                r[ri++] = nums[q.peek()];
            }
        }
        return r;
    }


    public static int[] slidingWindowMax(final int[] in, final int w) {
        final int[] max_left = new int[in.length];
        final int[] max_right = new int[in.length];

        max_left[0] = in[0];
        max_right[in.length - 1] = in[in.length - 1];

        for (int i = 1; i < in.length; i++) {
            max_left[i] = (i % w == 0) ? in[i] : Math.max(max_left[i - 1], in[i]);

            final int j = in.length - i - 1;
            max_right[j] = ((j+1) % w == 0) ? in[j] : Math.max(max_right[j + 1], in[j]);
        }

        final int[] sliding_max = new int[in.length - w + 1];
        for (int i = 0, j = 0; i + w <= in.length; i++) {
            sliding_max[j++] = Math.max(max_right[i], max_left[i + w - 1]);
        }

        return sliding_max;
    }


    public static void main(String[] args) {
        int[] input = {2,1,3,4,6,3,8,9,10,12,56}; int k=3;
        new SlidingWindowMaximum().maxSlidingWindow2(input ,3);
        SlidingWindowMaximum.slidingWindowMax(input,4);
    }

}
