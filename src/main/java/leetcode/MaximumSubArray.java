package leetcode;

/**
 * leetcode 53
 *
 * 参考剑指offer第42题。 我的做法分为采用dp的方式和非dp的方式
 * dp的方式就是递归，从上而下，要向得到f(n-1), 必须计算f[n-2], 以此类推。
 * 而非递归的思路跟dp其实一致，只不过采用自底向上(先算f[0],在f[1], 。。。。。)
 * @author chang.wu
 * @date 12/2/20
 */
public class MaximumSubArray
{

    int globalMax = Integer.MIN_VALUE;
    public int maxSubArrayByDivideandConqur(int[] nums) {
        divideAndConquer(nums, nums.length - 1);
        return globalMax;
    }



    int divideAndConquer(int[] nums, int current){
        if(current == 0){
            globalMax = nums[0];
            return nums[0];
        }

        Integer currentMaxSum = Math.max(nums[current],
                divideAndConquer(nums, current -1) + nums[current]);
        globalMax = globalMax < currentMaxSum ? currentMaxSum: globalMax;
        return currentMaxSum;
    }


    int maxSubArray2(int[] nums){
        int globalMax = nums[0];
        int curMaxWithEnd = nums[0];
        for(int i =1 ;i< nums.length;i++){
            if(curMaxWithEnd > 0)
            {
                curMaxWithEnd+=nums[i];
            }else{
                curMaxWithEnd = nums[i];
            }
            globalMax = globalMax > curMaxWithEnd?globalMax:curMaxWithEnd;
        }
        return globalMax;

    }


    public int maxSubArray4(int[] nums) {
        return maxSumInSubArray3(nums, 0, nums.length-1);
    }

    private int maxSumInSubArray3(int[] nums, int start, int end){
        if(start == end){
            return nums[start];
        }
        if(start + 1 == end){
            return Math.max(Math.max(nums[start], nums[end]), nums[start] + nums[end]);
        }
        int mid = (start + end)/2;
        int maxCross=nums[mid] + nums[mid+1];

        int currentSum = 0; int maxSum = 0;
        for(int i = mid-1;i>=start;i--){
            currentSum = currentSum + nums[i];
            maxSum = Math.max(currentSum, maxSum);
        }
        maxCross = maxCross + maxSum;
        currentSum = 0;
        maxSum = 0;
        for(int j = mid+2;j<=end;j++){
            currentSum = currentSum + nums[j];
            maxSum = Math.max(currentSum, maxSum);
        }
        maxCross = maxCross + maxSum;
        return Math.max(maxCross, Math.max(maxSumInSubArray3(nums, start, mid), maxSumInSubArray3(nums, mid+1, end)));
    }


    public static void main(String[] args)
    {
        int[] testsData = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(new MaximumSubArray().maxSubArray4(testsData));
        System.out.println(new MaximumSubArray().maxSubArrayByDivideandConqur(testsData));
    }



}
