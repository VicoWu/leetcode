import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuchang at 1/2/18
 * Question 78
 */

public class Subsets {
    /**
     * 这是我自己的实现
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets(int[] nums) {

        List<List<Integer>> result  = new LinkedList<>();
        recursive(nums,0,result,new LinkedList());
        return result;
    }


    /**
     * 计算nums[lo,nums.length]的所有子集
     * @param nums
     * @param lo
     * @param result
     * @param current
     */
    public void recursive(int[] nums,int lo,List<List<Integer>> result,List<Integer> current){

        if(lo > nums.length)
            return;
        if(lo==nums.length)
            result.add(new LinkedList<>(current));//必须拷贝出来，而不能直接result.add(current)
        else{
            recursive(nums,lo+1,result,current); //不添加当前的nums[lo]，对后面的元素进行递归
            current.add(nums[lo]); //将nums[lo]添加进来，对后面的元素进行递归
            recursive(nums,lo+1,result,current);
            current.remove(current.size()-1); //栈退出即递归回溯的时候将这层栈添加进来的元素删除
        }
    }

    public static void main(String[] args) {

        int[] nums = {1,2,3};
        new Subsets().subsets(nums);
    }
}
