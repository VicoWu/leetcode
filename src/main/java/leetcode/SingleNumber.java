package leetcode;

/**
 * Created by wuchang at 1/9/18
 * Question 136
 */

public class SingleNumber {


    public int singleNumber(int[] nums) {
        int result = 0;
        for (int i = 0; i<nums.length; i++)
        {
            result ^=nums[i];
        }
        return result;
    }


    public static void main(String[] args) {

    }
}
