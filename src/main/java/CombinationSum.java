import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuchang at 1/3/18
 * Question 39
 这一题同样是递归和回溯的典型应用

 对于candidates，我们首先在`[0,candidates.length-1]`的范围内进行搜索，因此取出`candidates[0]`，剩余`target-candidates[0]`，由于题目中允许candidates中的任何一个数字被任意多次使用，因此，我们还是在`[0,candidates.length-1]`的范围内来给`target-candidates[0]`进行递归搜索。如果什么时候`target==0`，说明找到候选，保存结果。
 当完成了target在`[0,candidates.length-1]`范围内的搜索，我们再在`[1,candidates.length-1]`范围内进行搜索，以此类推，知道完成`[candidates.length-1，candidates.length-1]`范围的搜索。

 为了降低时间复杂度，我们将candidates进行递增排序，这样，假如我们发现当前`target<candidates[lo]`，就可以终止搜索了，因为lo以及lo后面的元素都是比`candidates[lo]`大的元素。
 */

public class CombinationSum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {

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

        if(target  < candidates[lo]) //由于我们已经对candidates进行了排序，因此如果此时target已经小于最左侧数字(不一定是第一个)，肯定没有候选的了，直接退出
            return;

        for(int i=lo;i<candidates.length;i++){ //i代表了起始的搜索位置
            current.add(candidates[i]);
            combine(candidates,target-candidates[i],i,result,current);//在[i,candidates.length-1]的范围内搜索target-candidates[i],由于是允许重复，因此，是[i,candidates.length-1]，而不是[i+1,candidates.length-1]
            current.remove(current.size()-1); //栈准备退出和回溯，因此删除刚才加入进来的元素
            //在[i,candidates.length-1]的范围内搜索完成，开始在[i+1,candidates.length-1]的范围内搜索target-candidates[i]
        }

    }
    public static void main(String[] args) {

        int[] nums = {2,3,6,7};
        new CombinationSum().combinationSum(nums,7);
    }

}
