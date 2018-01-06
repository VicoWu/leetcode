/**
 * Created by wuchang at 1/2/18
 * Question 31
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


 * 这一题完全参考[这里](https://leetcode.com/problems/next-permutation/discuss/13865)的答案就行；
 * 在当前序列中，从尾端往前寻找两个相邻元素，前一个记为first，后一个记为second，并且满足first 小于 second。
 * 然后再从尾端寻找另一个元素number，如果满足first 小于number，即将第first个元素与number元素对调，并将second元素之后（包括second）的所有元素颠倒排序，即求出下一个序列

 example:
 6，3，4，9，8，7，1
 此时 first ＝ 4，second = 9
 从尾巴到前找到第一个大于first的数字，就是7
 交换4和7，即上面的swap函数，此时序列变成6，3，7，9，8，4，1
 再将second＝9以及以后的序列重新排序，让其从小到大排序，使得整体最小，即reverse一下（因为此时肯定是递减序列）
 得到最终的结果：6，3，7，1，4，8，9

 */

public class NextPermutation {
    public void nextPermutation(int[] nums) {

        int i = nums.length-2;
        for(;i>=0 && nums[i]>=nums[i+1];i--) ;//从后往前找到第一个i，使得nums[i] < nums[i+1],比如，对于输入6，3，4，9，8，7，1，这个i是nums[2]=4
        if(i>=0){ //找到了一个
            int j = nums.length-1;
            for(;j>i && nums[j]<=nums[i];j--);//从后往前找到第一个j，使得nums[j]是第一个大于nums[i]的数字，比如6，3，4，9，8，7，1，i=2，j=5,这样交换才能让变换以后的数字尽可能小
            swap(nums,i,j);
            reverse(nums,i+1,nums.length-1);//将剩余的部分进行翻转，比如6，3，4，9，8，7，1，我们交换nums[2]和nums[5]，变成6 3 7 9 8 4 1,然后，我们对nums[3]到nums[6] 进行翻转,变成6 3 7 1 4 8 9
        }
        else //没有找到，说明输入的数据是一个递减数据，按照题目要求全局翻转
            reverse(nums,0,nums.length-1);
    }

    /**
     * 交换两个数
     * @param nums
     * @param i
     * @param j
     */
    private void swap(int[] nums,int i,int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    /**
     * 翻转指定范围的数
     * @param nums
     * @param lo
     * @param ri
     */
    private void reverse(int[] nums,int lo,int ri){
        for(int i=lo;i<=(lo+ri)/2;i++){
            int tmp = nums[i];
            nums[i] = nums[ri+lo-i];
            nums[ri+lo-i] = tmp;
        }
    }
    public static void main(String[] args) {

        int[] nums = {3,2,1};
        new NextPermutation().nextPermutation(nums);
    }

}
