import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

/**
 * Question 719
 * https://leetcode.com/articles/find-k-th-smallest-pair-distance/
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
            if (count >= k) hi = mi;
            else lo = mi + 1;
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

        int[] nums1 = {1,3,1};
        System.out.println(new FindKthSmallestPairDistance().bsearchWithoutRecursion2(nums1,1,15,3));
        new FindKthSmallestPairDistance().smallestDistancePairBySlidingWindow(nums1,1);
    }
}
