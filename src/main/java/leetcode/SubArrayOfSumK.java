package leetcode;

/**
 * Question 560
 */

import java.util.HashMap;
import java.util.Map;

public class SubArrayOfSumK {
    public int subarraySum(int[] nums, int k) {
        Map<Integer,Integer> counts = new HashMap<Integer,Integer>();
        counts.put(0, 1); // 初始化counts，(0, 1)，代表我们认为没有任何元素的时候，前缀和为0，比如计算(1,2)，前缀和为1的时候
        Integer currentPrefixSum = null;
        Integer result = 0;
        for(int i = 0; i<nums.length;i++){
            if(currentPrefixSum == null){
                currentPrefixSum = nums[i];
            }else{
                currentPrefixSum = currentPrefixSum + nums[i];
            }
            // 在这个字符之前是否有前缀和为 currentPrefixSum - k 的连续数组
            if(counts.containsKey(currentPrefixSum - k)){
                result += counts.get(currentPrefixSum - k);
            }
            // 更新counts中key为currentPrefixSum的记录
            if(counts.containsKey(currentPrefixSum)){
                counts.put(currentPrefixSum, counts.get(currentPrefixSum)+1);
            }else{
                counts.put(currentPrefixSum, 1);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
    }
}
