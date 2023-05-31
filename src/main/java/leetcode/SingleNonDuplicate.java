package leetcode;

public class SingleNonDuplicate {
    public int singleNonDuplicate(int[] nums) {
        if(nums.length == 1){
            return nums[0];
        }
        if(nums[0] != nums[1]){
            return nums[0];
        }
        if(nums[nums.length - 2] != nums[nums.length - 1]){
            return nums[nums.length - 1];
        }
        // 从第三个数到倒数第三个数之间的范围寻找答案
        int start = 2; int end = nums.length - 2;

        while(start <= end){
            int mid = (start + end)/2;
            // 判断nums[mid]是否就是答案
            if(nums[mid]!=nums[mid+1] && nums[mid] != nums[mid-1]){
                return nums[mid];
            }
            // 保证mid的下标是两个相同数字的第一个数字
            if(nums[mid] == nums[mid-1]){
                mid = mid + 1;
            }
            //偶数，说明答案不在左边
            if((mid - start) % 2 == 0){
                start = mid;
            }else{
                // 奇数，说明答案在左边
                end = mid - 1;
            }
        }
        return nums[start];
    }
}
