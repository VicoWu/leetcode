package leetcode;

/**
 * Question 35
 */
public class SearchInsert {
    public int searchInsert(int[] nums, int target) {
        int left = 0, right = nums.length-1;
        int mid = (left + right)/2;
        while(left <= right){
            if(target > nums[mid]){
                left = mid + 1;
            }else if(target < nums[mid]){
                right = mid - 1;
            }else{
                return mid;
            }
            mid = (left + right)/2;
        }
        return left;
    }
}
