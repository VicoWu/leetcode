/**
 * Created by wuchang at 1/10/18
 * Question 260
 由于存在两个不同的数字，因此他们之间肯定至少有一位数字是不同的，即，如果我们把整个集合所有数字进行xor操作，这个xor的结果肯定不为0，即，xor以后的二进制表示方法中，
 至少有1个1，比如，xor以后的结果是00110100，我们随机选出一个1，假如，我们就选最后一个1，对于每一个nums[i]，我们根据nums[i]对应的这一位是0还是1，将整个nums分成了两组，
 显然，这两部不同的数字肯定处在不同的组里面，并且，其它的任何出现了两次的数字，
 都分在了同一个组里面。因此，两个组，都是有一个数字出现1次，剩余数字都出现两次，这样，问题转化成了[Single Number](https://leetcode.com/problems/single-number/)问题。
 */

public class SingleNumberIII {

    public int[] singleNumber(int[] nums) {

        int xor = 0;
        for(int n:nums){
            xor = xor ^ n;
        }
        int i=0;
        while( ((xor >> (i)) & 1) != 1)
            i++;

        xor = xor & (1<<i);
        int[] res = {0,0};
        for(int n:nums){

            if((n & xor) == 0){

                res[0] = res[0] ^ n;
            }
            else{

                res[1] = res[1] ^ n;
            }

        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1,1,2,2,3,5};
        new SingleNumberIII().singleNumber(nums);
    }
}
