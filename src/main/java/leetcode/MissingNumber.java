package leetcode;

/**
 * Created by wuchang at 2/4/18
 * Question 268
 */

public class MissingNumber {

    public int missingNumber(int[] nums) {
        int xor = 0, i = 0;
        for (i = 0; i < nums.length; i++) {
            xor = xor ^ i ^ nums[i];
        }

        return xor ^ i;
    }
}
