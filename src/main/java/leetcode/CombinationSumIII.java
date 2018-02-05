package leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuchang at 1/3/18
 * Question 216
 *
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
 * 对于[1..9]，我们先从1开始，看看有没有以1开头的合理组合，所有以1开头的组合都找完以后，再找以2开头的，再找以3开头的组合。。。
 假如说我们现在要找k个数字组成的组合，范围是[1...9]，假如现在1定下来，我们找以1开头的组合，因此我们需要在[2...9]范围内查找由k-1个数字并且和为n-1的组合，对于[2...n]中的每一个数字t，我们需要再在[t+1,9]的范围内查找由k-2个数字并且和为n-1-t组成的组合，依次类推。
 在不断递归的过程中，我们都会通过当前current的大小来判断current.size()是否已经等于k以及剩余和是否为0，如果是，则保存这个结果，返回。
 */

public class CombinationSumIII {

    public List<List<Integer>> combinationSum3(int k, int n) {

        List<List<Integer>> result  = new LinkedList<>();

        combine(n,k,1,result,new LinkedList<>());
        return result;
    }

    /**
     *
     * @param n 代表当前剩余的和
     * @param k
     * @param lo
     * @param result
     * @param current
     */
    public void combine(int n,int k,int lo,List<List<Integer>> result,List<Integer> current){

        if(n == 0 && current.size() == k){ //如果当前已经剩余的和为0，并且current中的数据正好是k，则是满足要求的结果
            result.add(new LinkedList<>(current)); //发现候选
            return;
        }

        if(n  < lo)
            return;

        for(int i=lo;i<10;i++){ //i代表了起始的搜索位置，我们在[lo,9]的范围内进行搜索
            // 对于题目中的C,排序以后有：1，1，2，5，6，7，10,target=8,我们第一个1和后面的2，5相加为8，当我们到达第二个1的时候，就直接跳过去不考虑了，从而避免输出两个[1,2,5]

            current.add(i);
            combine(n-i,k,i+1,result,current);//看看能否找到和为n-i 并且以i+1开头的 数字组合
            current.remove(current.size()-1); //栈准备退出和回溯，因此删除刚才加入进来的元素

            //以lo开头并且和为n的组合搜索完成，我们开始以lo+1开头并且和为n进行搜索
        }
    }



    public static void main(String[] args) {

        new CombinationSumIII().combinationSum3(3,9);


    }
}
