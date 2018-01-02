/**
 * Created by wuchang at 12/29/17
 * Question 81
 *
 * 这一题必须参照[Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/description/ ) 进行解答。
 由于[Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/description/ ) 中Rotated Sorted Array 中没有重复数字，因此，我们可以很明确地根据left , mid，right之间的大小关系，知道这个target是在左侧区间还是右侧区间，可是，一旦有重复数字，这个判断就失灵了：
 比如：
 A=[2，3，4，2，2，2，2] A[left] == A[mid] ==  A[right] = 2
 B=[2，2，2，2，3，4，2] B[left] == B[mid] ==  B[right] = 2
 两组Rotated Sorted Array 中，我们要求的target = 3 ， 可是A中，target在左侧，B中，target在右侧。这种情况下，我们只能左右侧分别都进行查找，这时候，时间复杂度退化成O(n)


 */

public class SearchInRotatedSortedArrayII {

    public boolean search(int[] nums, int target) {

        return search(nums,0,nums.length-1,target) != -1;
    }

    public int search(int[] nums,int left,int right,int target){
        if(left> right)
            return -1;
        int mid = (left+right)/2;
        if(target == nums[mid])
            return mid;

        if(nums[left] <= nums[mid]){ //左侧有序，即pivot不在左侧
            if(nums[left] == nums[mid]&& nums[left]  == nums[right])
                return search(nums,left+1,right-1,target);
            if(target >= nums[left] && target < nums[mid])
                return normalBinarySearch(nums,left,mid-1,target); //确认有序，进行标准的二分查找
            else
                return search(nums,mid+1,right,target);
        }
        else{ //右侧有序，即privot不在右侧
            if(target > nums[mid] && target <= nums[right])
                return normalBinarySearch(nums,mid+1,right,target); //确认有序，进行标准的二分查找
            else
                return search(nums,left,mid-1,target);
        }
    }

    /**
     * 确定了一个区间是正常的二分区间，我们就可以对这个区间进行二分查找了
     * @param nums
     * @param left
     * @param right
     * @param target
     * @return
     */
    public int normalBinarySearch(int[] nums,int left,int right,int target){
        while (left <= right) {
            int mid = (left + right) / 2;
            if (target < nums[mid])
                right = mid - 1;
            else if (target > nums[mid])
                left = mid + 1;
            else return mid;
        }
        return -1;
    }


    public static void main(String[] args) {
        int[] nums = {7,0,1,2,3};
        int[] nums2={1};
        int[] nums3= {2,3,4,2,2,2,2};
        int[] nums4= {2,2,2,2,2,3,4};
        int[] nums5= {2,2,2,2,2,2,2};
        System.out.println(new SearchInRotatedSortedArrayII().search(nums,0));
        System.out.println(new SearchInRotatedSortedArrayII().search(nums,2));
        System.out.println(new SearchInRotatedSortedArrayII().search(nums,5));
        System.out.println(new SearchInRotatedSortedArrayII().search(nums2,1));
        System.out.println(new SearchInRotatedSortedArrayII().search(nums2,5));

        System.out.println(new SearchInRotatedSortedArrayII().search(nums3,2));
        System.out.println(new SearchInRotatedSortedArrayII().search(nums3,3));
        System.out.println(new SearchInRotatedSortedArrayII().search(nums3,4));
        System.out.println(new SearchInRotatedSortedArrayII().search(nums3,1));

        System.out.println(new SearchInRotatedSortedArrayII().search(nums4,2));
        System.out.println(new SearchInRotatedSortedArrayII().search(nums4,3));
        System.out.println(new SearchInRotatedSortedArrayII().search(nums4,4));
        System.out.println(new SearchInRotatedSortedArrayII().search(nums4,1));


        System.out.println(new SearchInRotatedSortedArrayII().search(nums5,2));
        System.out.println(new SearchInRotatedSortedArrayII().search(nums5,1));



    }

}
