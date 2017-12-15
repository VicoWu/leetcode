import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

/**
 * Question 719
 * https://leetcode.com/articles/find-k-th-smallest-pair-distance/
 *
 *
 * 我直接参照[连接](https://leetcode.com/articles/find-k-th-smallest-pair-distance/)中的内容进行讲解；
 我直接参照[连接](https://leetcode.com/articles/find-k-th-smallest-pair-distance/)中的内容进行讲解；

 ## 方法1 ， 多个有序数组的排序
 我们先对输入的数据进行从小到达的排序。
 对于一个有序数组的每个点对之间的距离虽然有大有小，但是，对于某一个元素nums[i]，以它为左侧点的距离线段nums[j]-nums[i]，
 却随着j的增大而变大。因此，我们初始化一个堆`heap = [(i, i+1) for all i]`，即这个堆初始化保存了nums中的每一个元素和它的后面的元素，
 这个堆根据元素之间的距离排序。每次从堆顶取出最小元素(i,x)，我们都把(i,x+1)放入堆中，相当于多个有序数组的全局排序
 **时间复杂度**：对数组进行排序，O(nlogn) ,然后n个元素形成的距离有n(n-1)/2个，如果k就等于n(n-1)/2，即要寻找距离最大的Pair Distance，
 * 那么循环就需要进行O(n^2)次，每弹出一个堆元素都需要进行堆排序，复杂度O(logn)，因此，基于堆寻找全局第k小的Pair Distance的最坏和平均时间复杂度是O(n^2logn)，因此全体时间复杂度是O(n+n^2)logn即n^2logn
 **空间复杂度**：主要用来存放堆，即O(n)


 ## 方法2，二分查找并基于prefix定位结果

 暴力情况下，我们可以对`k=n(n-1)/2`个距离进行排序，显然，排序的时间复杂度是klogk，那么，我们能不能加速这个过程呢？

 还是先对nums进行排序，不排序的话只能使用暴力查找方式；

 题目需要我们求第k小的距离对，那么，我们反过来想，我们尽管不知道第k小的距离对是哪个，但是，对于某一个距离对，我们能否知道它是第几小的呢？
 比如，我们要求第k=5小的距离对，然后对于距离L，如果我们看到它是第2小，显然，第5小的距离肯定大于L，而如果我们发现距离L是第7小，显然，第k小的距离肯定小于L，
 同时，我们可以在O(n)的时间复杂度内获取到最小距离(最小距离肯定是存在于某个元素和它相邻元素形成的点对中)，以及立刻获取最大距离`nums[nums.length - 1] - nums[0]`，有了最小最大距离范围，我们就可以通过二分查找快速逼近真是结果。

 **问题1**:对于某一个距离，如何知道这个距离排第几
 先不说二分查找，我们来看，对于某一个距离mi(这个mi可能是nums中的某两个点的距离，也有可能只是位于最大距离和最小距离之间、但是不是nums中任何两点的距离的值)，如何计算
 nums[i]中小于等于mi的距离数？暴力方式，我们需要遍历nums中每个点极端得到<=mi的数量；当然，我们可以提前准备一个prefix数组，
 每个prefix[i]，记录了nums中小于等于i的元素的数量。显然，prefix的长度与nums中的最大元素值有关：
 比如对于`nums = {1,2,4,4,6}`，会有`prefix={0,1,2,2,4,4,5,5}`
 那么，对于nums中的某个元素i,以`nums[i]`为左侧节点的距离对中距离小于等于mi的点的数量就是`prefix[nums[i]+mi]-prefix[nums[i]] + multiplicity[i]`，其中，
 `multiplicity[i]`代表nums中与`nums[i]`距离相等的点的个数。还是以`nums = {1,2,4,4,6}`为例，`multiplicity={0,0,0,1,0}`，假如`mi=4`，
 我们需要计算以`nums[1]=1`为左侧节点且距离小于等于3的点的数量，通过`prefix[1+3]=4`，`prefix[1]=1`,并且`multiplicity[3]=1`（4重复了1次），
 我们知道以1开头、距离小于等于4的点的数量为4-1+1=4，即`(1,1),(1,2),(1,4),(1,4)`
 因此，对于一个mi，我们对nums中所有元素进行一次伤处运算并将结果相加，就得到了nums中距离小于等于mi的点的数量；

 **问题2: ** 如何通过二分法快速定位到第k小的距离
 任何时候，lo和lh代表上边界和下边界，对于`(lo+lh)/2`即中间位置的值mi，我们看看小于距离mi的数量n有`n>k`，说明第k大的距离位于`(lo+1,lh)`；
 如果小于距离mi的数量n有`n<k`,说明第k大的距离值肯定位于`(lo,lh-1)`，关键问题，如果刚好有`n==k`，我们应该停止吗？显然不是，
 因为这个mi不一定是nums[i]的点对形成的距离中的一个，因此，我们需要将边界调整为`(lo,lh)`，即包括中间节点的左侧段进行二分查找。这样，当`lo==lh`的时候，lo肯定是`nums[i]`中的数值，所以，我们看代码中：
 ```
 if (count >= k)  //这里，即使是等于，也需要往左侧收缩，而不是停止收缩
 hi = mi;
 else
 lo = mi + 1;
 ```

 时间复杂度：
 构造prefix数组，这个时间复杂度根nums[i]中最大元素W相关，复杂度是O(W)，而基于范围(0,W)（即最小距离和最大距离之间的范围）进行二分查找，
 时间复杂度为O(WlogN)，同时，最开始的时候，对nums进行排序，复杂对是O(NlogN)，因此整体时间复杂度是O(W+NlogW+NlogN)
 空间复杂度：O(N+W), 分别用来存放prefix数据和multiplicity数据


 ## 方法3 通过二分查找和滑动窗口计算
 对nums进行排序以及通过二分查找的过程没有变，唯一改变的是对于mi，计算小于等于mi的距离的数量值的算法，我们通过滑动窗口法进行，
 滑动窗口的计算代码如下，不做详细讲解：
 ```
 for (int i = 0,j=1; i < nums.length && j<nums.length; i++) {
 while ((j<nums.length) && (nums[j] - nums[i] <= middle))
 j++;
 j--;
 count += (j-i);
 //System.out.println(count);
 }
 ```

 时间复杂度：对于某一个mi，通过滑动窗口确认小于等于mi的距离数量时，由于每一步都至少会移动一次j或者i，因此时间复杂度是O(N)；
 因此这个二分查找的总体复杂度是`O(NlogW)`，W是nums中的最大值，加上初始化的排序操作，总体时间复杂度是`O(NlogW+NlogN)`
 空间复杂度：`O(1)`







 )
 */
public class FindKthSmallestPairDistance {

    /**
     * prefix[v] 代表在nums中小于等于v的数字的数量
     * multiplicity[j]代表在nums中与nums[j]相等但是位置在j之前的点的数量
     * possible(guess) guess代表距离，当且仅当小于等于这个距离的点的数量不小于k的时候，possible(guess)返回true，否则返回false
     * @param nums
     * @param k
     * @return
     */
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums); //先对nums进行排序
        int WIDTH = 2 * nums[nums.length - 1]; //WIDTH的大小是nums中最大的数的两倍，即，如果最大的数字是200，则WIDTH=400

        //multiplicity[i] = number of nums[j] == nums[i] (j < i)
        int[] multiplicity = new int[nums.length];
        for (int i = 1; i < nums.length; ++i) {
            if (nums[i] == nums[i-1]) {
                multiplicity[i] = 1 + multiplicity[i - 1];//multiplicity[i]代表在nums中与nums[i]相等但是位置在i之前的点的数量
            }
        }

        //prefix[v] = number of values <= v
        int[] prefix = new int[WIDTH];
        int left = 0;
        for (int i = 0; i < WIDTH; ++i) {
            while (left < nums.length && nums[left] == i)
                left++;
            prefix[i] = left;
        }

        int lo = 0; //距离最小值是0
        int hi = nums[nums.length - 1] - nums[0]; //距离最大值是nums中的最大值减去nums中的最小值
        while (lo < hi) {
            int mi = (lo + hi) / 2;
            int count = 0; //count值保存了距离l<=mi的点对的数量
            for (int i = 0; i < nums.length; ++i) { //计算距离<=mi的点对的数量
                count += prefix[nums[i] + mi] - prefix[nums[i]] + multiplicity[i]; //最关键部分，计算与nums[i]的距离在mi以内的点对的数量，multiplicity是为了正确计算重复的点的数量
            }
            System.out.println("["+lo+","+mi+","+hi+"]，count："+count);

            //count = number of pairs with distance <= mi
            if (count >= k)  //这里，即使是等于，也需要往左侧收缩，而不是停止收缩
                hi = mi;
            else
                lo = mi + 1;
        }
        System.out.println("final result is "+lo);
        return lo;
}


    /**
     * 通过滑动窗口的方式
     * @param nums
     * @param k
     * @return
     */
    public int smallestDistancePairBySlidingWindow(int[] nums, int k) {
        Arrays.sort(nums);

        int minimum = 0;
        int maximum = nums[nums.length - 1];
        while (minimum < maximum) {
            int middle = (minimum + maximum) / 2;

            //现在，我们开始通过滑动窗口的方式，确定距离<=middle的点对的数量
            int count = 0;
            for (int i = 0,j=1; i < nums.length && j<nums.length; i++) {
                while ((j<nums.length) && (nums[j] - nums[i] <= middle))
                    j++;
                j--;
                count += (j-i);
                //System.out.println(count);
            }

            if (count >= k) maximum = middle;
            else minimum = middle + 1;
        }
        return minimum;
    }


    int bsearch(int array[], int low, int high, int target)
    {
        if (low > high) return -1;

        int mid = (low + high)/2;
        if (array[mid]>= target)
            return    bsearch(array, low, mid, target);
        if (array[mid]< target)
            return    bsearch(array, mid+1, high, target);

        //if (midValue == target)
        return mid;
    }



    int bsearchWithoutRecursion(int array[], int low, int high, int target)
    {
        while(low < high)
        {
            int mid = (low + high)/2;
            if (array[mid] >= target)
                high = mid ;
            else
                low = mid + 1;

        }
         return low;
    }


    int bsearchWithoutRecursion2(int array[], int low, int high, int target)
    {
        while(low < high)
        {
            int mid = (low + high)/2;
            int num = 0;
            for(int i=0;i<array.length;i++)
                if(array[i] <= mid) num++;
            System.out.println("["+low+","+mid+","+high+"]，num："+num );

            if (num >= target)
                high = mid ;
            else
                low = mid+1 ;
        }
        return low;
    }


    public static void main(String[] args) {

        int[] nums1 = {1,2,4,4,6};
        //System.out.println(new FindKthSmallestPairDistance().bsearchWithoutRecursion2(nums1,1,15,3));
        new FindKthSmallestPairDistance().smallestDistancePair(nums1,1);
    }
}
