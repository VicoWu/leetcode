package leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuchang at 1/5/18
 * Question 18
 * 几个跟sum相关的问题：
 [Two sum](https://leetcode.com/problems/two-sum/description/)
 [3Sum](https://leetcode.com/problems/3sum/)
 [3Sum Closest](https://leetcode.com/problems/3sum-closest/)
 [4Sum](https://leetcode.com/problems/4sum/submissions/1)
 [4sum II](https://leetcode.com/problems/4sum-ii/discuss/93920)

 * 这个FourSum就是1+3Sum，即在nums中选定一个数字，然后在这个数字后面的元素中进行3sum操作。由于3sum的时间复杂度是O(n^2)，因此这个算法的时间复杂度是O(n^3)
 */

public class FourSum {
    public List<List<Integer>> fourSum(int[] nums, int target) {

        List<List<Integer>> result = new LinkedList<>();
        Arrays.sort(nums);
        for(int i=0;i<nums.length-3;i++){
            if(i!=0 && nums[i] ==nums[i-1]) //发现重复元素，略过
                continue;
            List<List<Integer>> c = threeSum(nums, nums[i], i+1, target - nums[i]);
            result.addAll(c);
        }
        return result;
    }


    /**
     * 计算3sum，headValue是4个元素中已经选定的第一个元素，需要选定剩余3个数字。target是剩余的和。from是起始坐标，即，3sum在[from,nums.length-1]范围内进行查找
     * @param num
     * @param headValue
     * @param from
     * @param target
     * @return
     */
    public List<List<Integer>> threeSum(int[] num, int headValue, int from, int target) {
        List<List<Integer>> res = new LinkedList<List<Integer>>();
        for (int i = from; i < num.length-2; i++) {
            if (i == from || (i > 0 && num[i] != num[i-1])) { //去除重复的i，即，如果nums[i]==nums[i-1]，则当前的i滤过，因为上一个i已经获取了所有的结果
                int lo = i+1, hi = num.length-1, sum = target - num[i];//注意lo=i+1，也起到了去除重复的作用，比如[-2,-1,3]，然后我们又遇到了[-1]，此时我们就不可以再考虑-2了
                while (lo < hi) {
                    if (num[lo] + num[hi] == sum) {
                        res.add(Arrays.asList(headValue,num[i], num[lo], num[hi]));
                        while (lo < hi && num[lo] == num[lo+1]) lo++;//去除重复
                        while (lo < hi && num[hi] == num[hi-1]) hi--; //去除重复
                        lo++; hi--;
                    } else if (num[lo] + num[hi] < sum) lo++;
                    else hi--;
                }
            }
        }
        return res;
    }


    public static void main(String[] args) {

        int[] S = {1,0,-1,0,-2,2};
        new FourSum().fourSum(S,0);
    }
}
