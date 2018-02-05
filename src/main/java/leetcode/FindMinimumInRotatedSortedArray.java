package leetcode;

/**
 * Created by wuchang at 1/9/18
 * Question 153
 [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/description/)
 [Search in Rotated Sorted Array II](https://leetcode.com/problems/search-in-rotated-sorted-array-ii/)
 [Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/description/)
 [Find Minimum in Rotated Sorted Array II](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/description/)
这一题其实是[Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/description/)这一题的第二种解法的第一步，即找到pivot元素。
 我们通过二分查找：
 - 如果这个nums[mid]元素大于nums[high]元素，说明pivot肯定在nums[mid]的右侧肯定不是mid元素本身，因此，我们设置`low = mid+1;`
 - 如果nums[mid] < nums[hi]，则说明pivot元素可能是mid元素本身，也可能在mid的左侧，因此,我们设置`hi = mid;`
 - 如果nums[mid] = nums[hi]，则说明pivot元素是mid元素本身，为了代码简洁，我们和上一步合并，也是设置`hi = mid;`

 当循环结束，low指向了最小的元素，即pivot元素。

 */

public class FindMinimumInRotatedSortedArray {
    public int findMin(int[] nums) {

        int low = 0;int hi = nums.length -1;
        while(low<hi){
            int mid = (low+hi)/2;
            if(nums[mid] > nums[hi]){ //nums[mid] > nums[hi]，说明pivot元素肯定在右侧，并且肯定不是mid
                low = mid+1;
            }
            else {//nums[mid] <= nums[hi]，说明pivot元素肯定在左侧，并且，有可能是mid元素本身
                hi = mid;
            }

        }
        return low;
    }



    public static void main(String[] args) {

        int[] nums = {6,0,1};
        new FindMinimumInRotatedSortedArray().findMin(nums);
    }
}
