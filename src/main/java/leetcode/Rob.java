package leetcode;

import java.util.HashMap;
import java.util.Map;

public class Rob {

    public int rob(int[] nums) {
        Map<Integer, Integer> cache = new HashMap<>();
        cache.put(nums.length - 1, nums[nums.length - 1]);
        // 当包含第一个元素，那么剩下的可以考虑的元素是[2, nums.length - 2]
        int keepFirst = nums[0] + rob(nums, 2, nums.length - 2, cache);
        // 必须清空缓存，因为最后一个元素这里不能包含
        cache.clear();
        // 当不包含第一个元素
        int noFirst = rob(nums, 1, nums.length - 1, cache);
        return Math.max(keepFirst,noFirst);
    }

    public int rob(int[] nums, int start, int end, Map<Integer, Integer> cache){

        if(start > end){
            return 0;
        }
        if(cache.containsKey(start)){
            return cache.get(start);
        }
        // 只有这一个元素，那么就可以偷盗这个
        if(start == end){
            return nums[start];
        }

        int res = Math.max(nums[start] + rob(nums, start + 2, end, cache),
                rob(nums, start + 1, end, cache));
        cache.put(start, res);
        return res;
    }

    public int rob2(int[] nums) {
        int[] cache = new int[nums.length+1];
        if(nums.length == 1){
            return nums[0];
        }
        // 偷第一个房间，那么最后一个房间不能偷，因为最后一个房间和第一个房间相邻
        cache[nums.length] = 0;
        cache[nums.length - 1] = 0; // 最后一个房间不能偷
        int maxValue = cache[0];
        for(int i = nums.length - 2; i>=2; i--){
            cache[i] = Math.max(nums[i] + cache[i + 2], cache[i+1]);
        }
        maxValue = Math.max(maxValue, cache[2] + nums[0]);

        // 不偷第一个房间, 那么最后一个房间是可以偷的
        cache[nums.length - 1] = nums[nums.length - 1];
        for(int i = nums.length - 2; i>=1; i--){
            cache[i] = Math.max(nums[i] + cache[i + 2], cache[i+1]);
        }
        return Math.max(maxValue, cache[1]);
    }

    public static void main(String[] args) {
        int[] input = {6,6,4,8,4,3,3,10};
        System.out.println(new Rob().rob(input));
    }
}
