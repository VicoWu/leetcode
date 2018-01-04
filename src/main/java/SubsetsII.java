import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuchang at 1/2/18
 * Question 90
 * [这里](https://leetcode.com/problems/permutations/discuss/18239)介绍了各种排列问题通过回溯方法进行解答的方案；
 * 我们首先对输入数组nums进行排序，排序的目的是，如果有重复元素，那么重复元素是相邻的。
 举个例子，假如输入集合为[2,2]，那么这两个2带来的不同的子集是:`[],[2],[2,2]`，加入有3个2，带来的不同的子集是`[]，[2]，[2,2]，[2,2,2]`，由此可以知道，某个元素一共有n个重复项，只能形成n+1个不同的子集。
 因此，对于已经排序的nums，我们进行递归的时候，每个元素都需要检查是否有重复元素，加入某个元素在nums中有k个，
 因此，我们需要分别在这个元素出现了0次、1次、2次...、k次的情况做递归计算。

 */

public class SubsetsII {

    public List<List<Integer>> subsetsWithDup(int[] nums) {

        Arrays.sort(nums); //由于可能存在重复元素，因此我们先对nums进行排序，这样如果有重复元素，重复的元素都是相邻的
        List<List<Integer>> result  = new LinkedList<>();

        recursive(nums,0,result,new LinkedList<>());
        return result;
    }

    /**
     * 计算nums[lo,nums.length]的所有子集
     * @param nums
     * @param lo
     * @param result
     * @param current
     */
    public void recursive(int[] nums, int lo, List<List<Integer>> result, List<Integer> current) {

        if (lo > nums.length)
            return;
        if (lo == nums.length)
            result.add(new LinkedList<>(current));//必须拷贝出来，而不能直接result.add(current)
        else {

            int nextDif = lo + 1;
            for (; nextDif < nums.length && nums[nextDif] == nums[lo]; nextDif++)
                ;//为了避免有重复元素，我们通过nextDif指向下一个不同的元素，比如1,2,2,2,3，lo=1,nums[lo]=2,那么nextDif=4,即nums[nextDif] = nums[4]=3

            //这个元素出现的次数为nextDif-lo次
            recursive(nums, nextDif, result, current); //不添加当前的nums[lo]，对后面的元素进行递归
            for (int i = lo; i < nextDif; i++) { //分别计算这个重复的元素nums[lo]出现了1次、2次、...、nextDif-lo的情况进行计算
                current.add(nums[i]);
                recursive(nums, nextDif, result, current); //不添加当前的nums[lo]，对后面的元素进行递归
            }
            for (int i=lo; i<nextDif; i++) //准备回溯，将刚才添加的这个元素全部删除
                current.remove(current.size() - 1); //栈退出即递归回溯的时候将这层栈添加进来的元素删除
        }
    }



    public static void main(String[] args) {

        int[] nums = {1,2,2};
        new SubsetsII().subsetsWithDup(nums);
    }

}
