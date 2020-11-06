package leetcode;

import java.util.Arrays;

/**
 * Created by wuchang at 1/3/18
 * Question 377
 *

 [Permutations](https://leetcode.com/problems/permutations/description/)
 [Permutations II](https://leetcode.com/problems/permutations-ii/description/)
 [Next permutation](https://leetcode.com/problems/next-permutation/description/)
 [Permutation Sequence](https://leetcode.com/problems/permutation-sequence/description/)
 [Subsets](https://leetcode.com/problems/subsets/description/)
 [Subsets II](https://leetcode.com/problems/subsets-ii/description/)
 [Combination Sum](https://leetcode.com/problems/combination-sum/description/)
 [Combination Sum II](https://leetcode.com/problems/combination-sum-ii/description/)
 [Combination Sum III](https://leetcode.com/problems/combination-sum-iii/description/)
 [Combination Sum IV](https://leetcode.com/problems/combination-sum-iv/description/)

 [这里](https://leetcode.com/problems/permutations/discuss/18239)介绍了各种排列问题通过回溯方法进行解答的方案；


 * [这里](https://leetcode.com/problems/combination-sum-iv/discuss/85036)非常详细地讲解了这一题的解答思路
 注意这一题与[Combination Sum](https://leetcode.com/problems/combination-sum/description/)的相同和不同：
 相同：
 首先，输入数组中的数字都是不重复的正整数
 都是要求和
 一个数字都可以被重复使用多次

 不同：
 这一题中，要求所有答案的排列组合，比如[1,2,1]，[1,1,2]和[2,1,1]是三个不同的结果，而在[Combination Sum](https://leetcode.com/problems/combination-sum/description/)中，这三个是相同的。

 # 从上而下的递归方法
 假如nums=[1,2,3]，target=4，对于nums中的任何一个数字，比如1，有target-1=3，因此，我们需要从nums递归检索和为3的组合。因为题目中[1,2,1]，[1,1,2]和[2,1,1]是不同的组合，
 并且允许一个数字使用多次，因此，对剩余的和4-1=3，我们仍然在nums中查找，不会排除任何数字。

 # 从下向上的方法
 使用数组result记录[1,2,...target-1]的组合数量。那么，对于一个target，如何根据前面已经计算得到的结果获取target对应的记录数呢？
 ```
 比如，nums=[1,3,7]，target=10，已经知道了result[1],result[2],result[3]...result[9]，怎么求result[10]呢？
 10=1+9 或者 3+7 或者7+3
 因此，当1固定，后面的排列数量就是result[10-1]=result[9]
 因此，当3固定，后面的排列数量就是result[10-3]=result[7]
 因此，当7固定，后面的排列数量就是result[10-7]=result[3]
 最终， result[10] = result[9]+result[7]+result[3]
 ```



 */

public class CombinationSumIV {

    public int combinationSum4(int[] nums, int target) {
        // cache[i] store the possible solution that the sum is i
        int[] cache = new int[target+1];
        Arrays.fill(cache,-1);
        return combine(nums,target,cache);
    }


    // combine方法输入原始数组（这个数组从来不变）， 返回solution的数量。我们使用cache，是因为由于不要求数字不重复，因此，我们在遍历过程中会遇到很多的重复工作，因此使用一个cache可以很好的避免重复的搜索
    public int combine(int[] nums,int target,int[] cache){

        // Find the possible solution
        if(target == 0)
            return 1;
        if(cache[target]!=-1)
            return cache[target];
        int result = 0;
        for(int i=0;i<nums.length;i++){
            if(target >= nums[i] )
                // The possible solution start with target = target-nums[i], start from i
            result+= combine(nums,target-nums[i],cache);
        }
        cache[target] = result;
        return result;
    }

    public int combinationSum4_1(int[] nums, int target) {
        int[] result = new int[target + 1];
        result[0] = 1; // 为什么result[0] = 1? 假如target = 1， 并且nums = {1}, 这时候我们求result[1], 由于 1 = 1 + 0因此，result[1] = result[0]，显然是1
        for (int i = 1; i < result.length; i++) { // 从1 开始，一直算到target
            // 计算result[i],计算result[i]的时候，我们确定result[i-1]已经计算好了
            for (int j = 0; j < nums.length; j++) { //对于nums[]中的每一个数字，固定下这个数字nums[j], 那么包含nums[j]的排列组合数量就是result[i - nums[j]]
                if (i >= nums[j])
                    result[i] += result[i - nums[j]];
            }
        }
        return result[target];
    }





    public static void main(String[] args) {

        int[] nums = {1,2,3};
        System.out.println(new CombinationSumIV().combinationSum4_1(nums,4));
        System.out.println(new CombinationSumIV().combinationSum4(nums,4));

    }
}
