import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuchang at 1/2/18
 * Question 46
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
 * 对于输入的nums，对于index=0，我们将nums[0]分别与nums中的每一个元素交换(包括自己)，
 * 这样就在index=0的位置形成了nums.length种情况，然后，我们再处理nums[1],将nums[1]与[1,nums.length-1]
 * 的所有元素进行一次交换，以此类推，我们将nums[i]与[i,nums.length-1]的所有元素交换。当进行到最后的时候，就是我们得到结果的时候。
 *
 *
 */

public class Permutations {
    /**
     * 将每一个位置的元素与它后面的元素进行交换，进而获得所有的排列组合
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {

        List<List<Integer>> result  = new LinkedList<>();
        recursive(nums,0,result,new LinkedList<Integer>());
        return result;
    }

    public void recursive(int[] nums,int lo,List<List<Integer>> result,List<Integer> current){

        if(lo == nums.length){ //递归到了底层，可以打印结果了
            result.add(new LinkedList(current));
            return;
        }

        for(int i=lo;i<nums.length;i++){ //将lo处的元 素分别于[lo,nums.length-1]范围内的所有元素交换
            swap(nums,lo,i);//进行交换
            current.add(nums[lo]);
            recursive(nums,lo+1,result,current);//对[lo+1,nums.length-1]进行计算
            current.remove(current.size()-1); //将结果还原
            swap(nums,lo,i); //将交换还原回来，然后准备进行下一次交换
        }
    }

    private void swap(int[] nums,int i,int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3};
        new Permutations().permute(nums);
    }
}
