/**
 * Created by wuchang at 12/31/17
 * Question 215
 *
 [Kth Largest Element in an Array](https://leetcode.com/problems/kth-largest-element-in-an-array/description/)
 [Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/description/)
 [Sort Characters By Frequency](https://leetcode.com/problems/sort-characters-by-frequency/description/)
 [Split Array into Consecutive Subsequences](https://leetcode.com/problems/split-array-into-consecutive-subsequences/description/)
 这一题设计到堆排序和归并排序，因此，我对各种排序算法的实现、原理和是否是稳定的排序算法进行了分析，分析放在这里：DifferentSortAlgorithms.MD
 [这里](https://leetcode.com/problems/kth-largest-element-in-an-array/discuss/60294)详细介绍了各种解决方案；

 # 使用堆排序进行解决方案
 我们使用一个小顶堆，这个堆的元素大小最大只有k个。遍历nums中的元素，先将元素插入到堆，然后我们判断堆的大小是否已经大于k，如果是，则从堆中弹出元素丢弃。由于是小顶堆，并且丢弃前堆中已经有了k+1个元素，因此，这个被丢弃的元素肯定小于第k大的元素。当我们遍历完所有的元素，堆顶存放的就是第k大的元素。
 可以在：DifferentSortAlgorithms.MD中看到堆排序的算法讲解；

 # 介于快排算法的解决方案
 快排的分支方法，平均时间复杂度是O(n)，但是最坏情况下，比如partition()方法每次都返回上一个位置的左侧一个位置，
 这时候时间复杂度退化为O(n^2)；
 注意，这一题中由于是需要求第k大的元素，因此，我们的partition是将大于或等于nums[pivot]的元素挪动到pivot的左侧，
 将小于nums[pivot]的元素挪动到pivot的右侧，与我们的快排算法正好相反，但是原理相同
 在《算法导论》的9.3节中提到了Blum-Floyd-Pratt-Rivest-Tarjan 算法，可以把这个选择问题在最坏情况下的时间复杂度降低到O(n).
 可以在DifferentSortAlgorithms.MD中看到快速排序算法的讲解；
 */

public class KthLargestElementInAnArray {
    /**
     * 使用快排的分治方法
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {

        int start = 0; int end = nums.length-1;
        while(start < end){
            int index  = partition(nums,start,end); //index代表了pivot元素的位置，由于k代表第k大的元素，因此，k=1，对应index=0,k=2 对应index=1，以此类推
            if(index == k-1)
                return nums[index];
            else if(index > k-1 )
                end = index-1;
            else
                start = index+1;
        }
        return nums[start];

    }

    /**
     * 由于我们需要求的是第k大的元素而不是第k小的元素，因此，我们的partition算法
     * @param nums
     * @param left
     * @param right
     * @return
     */
    private int partition(int[] nums,int left,int right){

        //以left对应的元素作为pivot，两个坐标i和j不断向中间靠拢，交换左大右小的元素
        int i= left;int j = right;
        while(i<j){

            //以left对应的元素作为pivot，不断将左侧大于nums[left]的以及右侧小于nums[left]的元素进行狡猾
            while(i<=right && nums[i] >= nums[left]) i++;
            while(j>=left && nums[j] < nums[left]) j--;
            if(j>i){
                exchange(nums,i,j);
            }
        }
        //交换结束以后，j指向的就是我们pivot元素应该存在的位置
        exchange(nums,j,left);
        return j; //结束了，A[j]左侧的元素就是全部大于或者等于A[j]的元素，右侧就是全部小于A[j]的元素
    }

    private void exchange(int[] nums , int i,int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j]=tmp;
    }

    public static void main(String[] args) {
        int[] nums = {3,2,1,5,6,4};
        int[] nums1 = {2,1};
        new KthLargestElementInAnArray().findKthLargest(nums1,1);
    }

}
