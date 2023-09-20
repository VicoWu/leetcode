package leetcode.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    private List<List<Integer>> result;
    private List<Integer> curResult;

    public Solution(){
        this.result = new ArrayList<>();
        this.curResult = new ArrayList<>();
    }

    public List<List<Integer>> combine(int[] input, int target){
        Arrays.sort(input);
        this.combineOnlyForPositive(input, 0, curResult, result, target);
        return result;
    }

    public void combineOnlyForPositive(int[] input, int start, List<Integer> curRes, List<List<Integer>> result , int target){
        if(target == 0){ // Found the solution
            result.add(new ArrayList<Integer>(curRes)); // copy it to the result
            return;
        }
        if(start >= input.length || target < input[start] ){ // already sorted, if target < input[start], abort ahead of time
            return;
        }
        int multiple = 0;
        for(multiple = 0;;multiple++){ //  当前的数字可以出现0次或者多次
            int remaining = target - input[start] * multiple;
            this.combineOnlyForPositive(input, start+1, curRes, result, remaining);

            if(remaining < 0){ // 退出
                break;
            }
            curRes.add(input[start]);
        }
        for(int i=0;i<multiple;i++){
            curRes.remove(curRes.size() - 1);
        }
    }

    public static void main(String[] args){
        Solution s = new Solution();
        int[] input = {2,3,7};
        int[] input2 = {2,3,7};
        List<List<Integer>> re = s.combine(input, 7);
        System.out.println(re);
    }
}

