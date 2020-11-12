package leetcode;

/**
 * Created by wuchang at 2/4/18
 * Question 268
 *
 * 位操作
 * 还有位操作的是 371. Sum of Two Integers
 * 这里有一个总结： https://leetcode.com/problems/sum-of-two-integers/discuss/84278/A-summary%3A-how-to-use-bit-manipulation-to-solve-problems-easily-and-efficiently
 *
 */

public class MissingNumber {

    public int missingNumber(int[] nums) {
        int xor = 0, i = 0;
        for (i = 0; i < nums.length; i++) {
            xor = xor ^ i ^ nums[i];
        }

        return xor ^ i;
    }

    public int missinNumber(int[] nums){
        int xor1 = 0; int xor2 = 0;
        for(int i=0;i < nums.length; i++){
            xor1 = xor1 ^ nums[i];
        }
        for(int i =0; i<=nums.length;i++){
            xor2 = xor2 ^ i;
        }
        return xor1 ^ xor2;
    }
}
