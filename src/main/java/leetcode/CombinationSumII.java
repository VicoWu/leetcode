package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuchang at 1/3/18
 * Question 40
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



 这一题与[Combination Sum](https://leetcode.com/problems/combination-sum/description/)是解法一样，但是区别有两点：
 1. 这一题中C里面的每一个数字都只能用一次
 2. 这一题中C里面可能有重复数字

 因此，我们在解答这一题的时候，只需要加上去除重复的数字的逻辑：
 ```
 for(int i=lo;i<candidates.length;i++){ //i代表了起始的搜索位置
 if(i>lo && candidates[i] == candidates[i-1])
 continue;
 ```
 这样，对于题目中的C,排序以后有：1，1，2，5，6，7，10,target=8,我们第一个1和后面的2，5相加为8，当我们到达第二个1的时候，就直接跳过去不考虑了，从而避免输出两个[1,2,5],但是，由于C中本来就有两个1，因此[1,1,6]是合法的，因为每个1各使用一次，请将两种情况区分开。

 同时，我们还需要在[Combination Sum](https://leetcode.com/problems/combination-sum/description/)的基础上进行修改，保证C中的每个数字不会被重复使用：
 ```
 combine(candidates,target-candidates[i],i+1,result,current);//由于不允许一个数字用两次，因此我们在[i+1,candidates.length-1]的范围内搜索target-candidates[i],而不是[i,candidates.length-1]
 ```
 */

public class CombinationSumII {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {

        Arrays.sort(candidates);
        List<List<Integer>> result  = new LinkedList<>();
        combine(candidates,target,0,result,new LinkedList<>());
        return result;
    }


    public void combine(int[] candidates,int target,int lo,List<List<Integer>> result,List<Integer> current){

        if(target == 0){
            result.add(new LinkedList<>(current)); //发现候选
            return;
        }

        if(lo >= candidates.length)
            return ;

        if(target  < candidates[lo]) //由于我们已经对candidates进行了排序，因此如果此时target已经小于最左侧数字(不一定是第一个)，肯定没有候选的了，直接退出
            return;

        for(int i=lo;i<candidates.length;i++){ //i代表了起始的搜索位置
           // 对于题目中的C,排序以后有：1，1，2，5，6，7，10,target=8,我们第一个1和后面的2，5相加为8，当我们到达第二个1的时候，就直接跳过去不考虑了，从而避免输出两个[1,2,5]
            if(i>lo && candidates[i] == candidates[i-1])
                continue;
            current.add(candidates[i]);
            combine(candidates,target-candidates[i],i+1,result,current);//由于不允许一个数字用两次，因此我们在[i+1,candidates.length-1]的范围内搜索target-candidates[i],而不是[i,candidates.length-1]
            current.remove(current.size()-1); //栈准备退出和回溯，因此删除刚才加入进来的元素

            //在[i,candidates.length-1]的范围内搜索完成，开始在[i+1,candidates.length-1]的范围内搜索target-candidates[i]
        }
    }


    // ##########################

    public List<List<Integer>> combinationSum2_2(int[] cand, int target) {
        Arrays.sort(cand);
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        List<Integer> path = new ArrayList<Integer>();
        dfs_com(cand, 0, target, path, res);
        return res;

    }
    void dfs_com(int[] cand, int cur, int target, List<Integer> path, List<List<Integer>> res) {
        if (target == 0) {
            res.add(new ArrayList(path));
            return ;
        }
        if (target < 0) return;
        for (int i = cur; i < cand.length; i++){
            if (i > cur && cand[i] == cand[i-1]) continue;
            path.add(path.size(), cand[i]);
            dfs_com(cand, i+1, target - cand[i], path, res);
            path.remove(path.size()-1);
        }
    }


    public static void main(String[] args) {

        int[] nums = {10, 1,  7, 6, 1, };
        new CombinationSumII().combinationSum2_2(nums,8);
    }



}
