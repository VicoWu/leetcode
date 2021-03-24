package leetcode;

/**
 * 633. Sum of Square Numbers\
 * 两个指针，一个从0开始，一个从可能的最大值开始,然后往中间移动
 * @author chang.wu
 * @date 11/25/20
 *
 *
 * [Permutations](https://leetcode.com/problems/permutations/description/)
 *  [Permutations II](https://leetcode.com/problems/permutations-ii/description/)
 *  [Next permutation](https://leetcode.com/problems/next-permutation/description/)
 *  [Permutation Sequence](https://leetcode.com/problems/permutation-sequence/description/)
 *  [Subsets](https://leetcode.com/problems/subsets/description/)
 *  [Subsets II](https://leetcode.com/problems/subsets-ii/description/)
 *  [Combination Sum](https://leetcode.com/problems/combination-sum/description/)
 *  [Combination Sum II](https://leetcode.com/problems/combination-sum-ii/description/)
 *  [Combination Sum III](https://leetcode.com/problems/combination-sum-iii/description/)
 *  [Combination Sum IV](https://leetcode.com/problems/combination-sum-iv/description/)
 *  [Sum of Square Numbers](https://leetcode.com/problems/sum-of-square-numbers/)
 *
 */
public class SumOfSquareNumbers
{
    public boolean judgeSquareSum(int c) {
        if(c <= 2)
            return true;
        int left = 0;
        double right = Math.ceil(Math.sqrt(c - 1));
        while(left <= right){
            Double PowSum = Math.pow(left, 2) + Math.pow(right, 2);
            if (PowSum > c){
                right--;
            }
            else if(PowSum < c){
                left++;
            }
            else
                return true;
        }
        return false;
    }

    public static void main(String[] args)
    {
        System.out.println(new SumOfSquareNumbers().judgeSquareSum(4));
    }
}
