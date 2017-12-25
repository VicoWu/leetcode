/**
 * Created by wuchang at 12/22/17
 * Question 55
 *
 * 这题的解法和思路仿照了[Jump Game II](https://leetcode.com/problems/jump-game-ii/description/)的贪心方法。
 使用maxNextPosition记录每一个位置所能到达的最远的位置，比如：

 ```
 A    2  3  1  1   4
 mn:  0  4  4  4   4

 A    3  2   1   0   4
 mn:  0  3   3   3   0

 mn:maxNextPosition
 ```
 我们遍历数组:
 - 如果发现maxNextPosition已经小于i了，比如我们遍历到i=5，但是maxNextPosition=4，
 说明当前的i=5是不可以到达的，因此直接返回false；问：i=5不可以到达，有没有可能i=6却可以到达？答案是不可能。
 因为A[i]的数值代表的是最远距离，比如A[5]=3,则 从A[5]可以直接到达A[6],A[7],A[8]三个都行，
 而maxNextPosition是根据最远的位置即a[5]获得的，因此，如果i=5到不了，i>5的位置肯定都到不了了，不存在跳跃。
 - 如果我们发现maxNextPosition已经>= nums.length-1，即已经可以到达最后一个节点了，则直接返回true；



 */

public class JumpGame {
    public boolean canJump(int[] nums) {
        int maxNextPosition = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if( i>maxNextPosition)
                return false;
            maxNextPosition = Math.max(maxNextPosition, i + nums[i]);
            if (maxNextPosition >= nums.length - 1)
                return true;
        }
        return nums.length <=1;
    }

    public static void main(String[] args) {

        int[] a = {3,2,1,0,4};
        int[] b = {2,3,1,1,4};
        int[] c = {1,0,1,0};
        System.out.println(new JumpGame().canJump(a));;
        System.out.println(new JumpGame().canJump(b));
        System.out.println(new JumpGame().canJump(c));
    }
}
