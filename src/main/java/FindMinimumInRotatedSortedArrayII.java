/**
 *
 [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/description/)
 [Search in Rotated Sorted Array II](https://leetcode.com/problems/search-in-rotated-sorted-array-ii/)
 [Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/description/)
 [Find Minimum in Rotated Sorted Array II](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/description/)

 这几个问题都是跟Rotated Sorted Array相关的问题，都是进行查找。
 对于本题，如果出现重复，那么A[mid]和A[low]以及A[hi]的关系变得复杂。
 如果没有重复元素，那么每一步都可以二分：
 ```
 int mid = (low+hi)/2;
 if(nums[mid] > nums[hi]){ //nums[mid] > nums[hi]，说明pivot元素肯定在右侧，并且肯定不是mid
 low = mid+1;
 }
 else {//nums[mid] <= nums[hi]，说明pivot元素肯定在左侧，并且，有可能是mid元素本身
 hi = mid;
 }
 ```

 如果出现重复元素，那么，时间复杂度变成O(n)，因为存在无法判断pivot元素在左侧还是右侧的情况。
 比如：
 ```
 5 5 5 5 0 1 5
 0 1 2 0 0 0 0
 ```
 这两种情况都有nums[mid]==nums[low]==nums[hi]，但是pivot元素却一个在右侧一个在左侧，因此，如果遇到这种情况，我们无法再进行二分，而只能把nums[hi]排除，即时间复杂度退化到O(n)


 * Created by wuchang at 1/9/18
 * Question 154
 */

public class FindMinimumInRotatedSortedArrayII {
    public int findMin(int[] nums) {
        int low = 0;int hi = nums.length -1;
        while(low<hi){
            int mid = (low+hi)/2;
            if(nums[mid] > nums[hi]){ //如果中间元素A[mid]>nums[hi]，那么pivot毫无疑问在右侧，并且肯定不是中间元素A[mid]本身
                low = mid+1;
            }
            //如果中间元素素A[mid] < nums[low]，或者，nums[mid] < nums[hi]，那么pivot毫无疑问在左侧，但是有可能pivot刚好是A[low]本身
            //其实这里nums[mid] < nums[low] 的条件可以去掉，因为对于任意一个旋转数组必有nums[low]<=nums[hi]，所以如果nums[mid] < nums[low]成立，那么nums[mid] < nums[hi]肯定成立
            else if(nums[mid] < nums[low] || nums[mid] < nums[hi]){
                hi = mid;
            }
            else if(nums[mid]==nums[hi])  //如果nums[mid]==nums[hi]，由于mid=(low+hi)/2，而且由于low<hi(while循环的条件)，所以必有mid!=high,至少我们可以把nums[hi]排除，这时候的复杂度变成O(n)
                hi--;
        }
        return nums[low];
    }

    public static void main(String[] args) {

        int[] nums = {5,5,5,5,0,1,5};
        int[] nums2 = {0,1,2,0,0,0,0};
        int[] nums3 = {1,3};
        System.out.println(new FindMinimumInRotatedSortedArrayII().findMin(nums));
        System.out.println(new FindMinimumInRotatedSortedArrayII().findMin(nums2));
        System.out.println(new FindMinimumInRotatedSortedArrayII().findMin(nums3));

    }
}
