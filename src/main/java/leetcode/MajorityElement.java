package leetcode;

/**
 * Created by wuchang at 1/10/18
 * Question 169
 */

public class MajorityElement {
    public int majorityElement(int[] nums) {
        int majority = nums[0],count = 1;
        for(int i=1;i< nums.length;i++){
            if(majority == nums[i])
             count++;
            else{
                count--;
                if(count==0){
                    count=1;
                    majority = nums[i];
                }
            }
        }
        return majority;
    }

    public static void main(String[] args) {
        int[] nums = {4,4,4,3,4,3,3};
        new MajorityElement().majorityElement(nums);
    }
}
