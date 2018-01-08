/**
 * Created by wuchang at 1/8/18
 * Question 152
 *
 算法思路(动态规划)：
 我们从左到右遍历nums，每遍历到一个数字，我们就可以知道以这个数字为最右侧数字的区间(这个区间只是以这个数字为最右侧，但是至于左侧数字是那个不确定)的最大值和最小值，这是因为对于当前数字`nums[i]`，如果我们知道了以`nums[i-1]`作为最右侧数字作为最右侧区间形成的最大值和最小值，那么，以`nums[i]`作为最右侧区间形成的最大值和最小值就知道了：
 如果`nums[i]>=0`，那么：
 ```
 imax = Math.max(nums[i],imax * nums[i]);
 imin = Math.min(nums[i],imin * nums[i]);
 ```
 如果`nums[i]<0`，那么
 ```
 int imaxTem = imax;
 imax = Math.max(nums[i],imin * nums[i]);
 imin = Math.min(nums[i],imaxTem * nums[i]);
 ```
 假如`nums[2]`形成的最大最小值是`[2,4]`，当前`nums[3]=2`，那么最大值`4*2`,最小值`2*2`；如果`nums[2]`形成的最大最小值是`[-2，-1]`，当前`nums[3]=2`，那么最大值是`nums[3]`自己，最小值`-2*2=-4`

 假如`nums[2]`形成的最大最小值是`[2,4]`，当前`nums[3]=-2`，那么最大值是自己-2,最小值`-2*4 = -8`；如果`nums[2]`形成的最大最小值是`[-2，-1]`，当前`nums[3]=-2`，那么最大值是`-2*-2=4`，最小值是自己-1;
 */

public class MaximumProductSubarray {

    /**
     * 这是我自己的实现
     * 我自己实现也是依据https://leetcode.com/problems/maximum-product-subarray/discuss/48230的实现
     *
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {

        int n = nums.length;
        int r = nums[0];
        for(int i =1,imax=r,imin=r;i<n;i++){
            if(nums[i] >=  0){//当前值是一个正数

                imax = Math.max(nums[i],imax * nums[i]); //这个最大值或者是自己，或者是imax * nums[i]
                imin = Math.min(nums[i],imin * nums[i]);//这个最小值或者是自己，或者是imin * nums[i]
            }
            else{
                int imaxTem = imax;
                imax = Math.max(nums[i],imin * nums[i]); //这个最大值或者是自己，或者是imin * nums[i]
                imin = Math.min(nums[i],imaxTem * nums[i]);//这个最小值或者是自己，或者是imaxTem * nums[i]
            }
            r = Math.max(r,imax);
        }
        return r;
    }

    /**
     * 这是https://leetcode.com/problems/maximum-product-subarray/discuss/48230的实现
     * @param A
     * @return
     */
    int maxProduct2(int A[]) {
        int n = A.length;
        // store the result that is the max we have found so far
        int r = A[0];

        // imax/imin stores the max/min product of
        // subarray that ends with the current number A[i]
        for (int i = 1, imax = r, imin = r; i < n; i++) {
            // multiplied by a negative makes big number smaller, small number bigger
            // so we redefine the extremums by swapping them
            if (A[i] < 0)
            {
                //交换
                int tmp = imax;
                imax = imin;
                imin =tmp;
            }

            // max/min product for the current number is either the current number itself
            // or the max/min by the previous number times the current one
            imax = Math.max(A[i], imax * A[i]);
            imin = Math.min(A[i], imin * A[i]);

            // the newly computed max value is a candidate for our global result
            r = Math.max(r, imax);
        }
        return r;
    }


    public static void main(String[] args) {

        int[] input = {2,3,-2,1,-2,3,-1,2};
        System.out.println(new MaximumProductSubarray().maxProduct(input));
    }
}
