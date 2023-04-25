package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wuchang at 1/2/18
 * Question 16
 * 思路：固定第一个元素，然后在右侧找第二个和第三个元素。
 注意距离的定义，必须取绝对值。
 如果当前的sum和target之间的距离小于0，说明我们应该适当增加sum，因此，需要lo++
 如果当前的sum和target之间的距离大于0，说明我们应该适当减小sum，因此，需要ri--
 每一步，我们都将当前距离和目前的最小距离比较，如果我们发现距离变小了，则记录下来这个sum值放到result里面
 遍历完成，返回result结果

 这个算法的时间复杂度是O(n^2)
 */

public class ThreeSumClosest {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int minDistance = Integer.MAX_VALUE;int result = nums[0]+nums[1]+nums[2];
        for(int i=0;i<nums.length-2;i++){ //固定第一个元素，在右侧找第二个和第三个元素
            int lo = i+1;int ri = nums.length-1;
//            int sum = nums[i] + nums[lo] + nums[ri];
//            int distance = sum - target;
//            if( Math.abs(distance)<minDistance){
//                minDistance = Math.abs(distance);
//                result = sum;
//            }
            while(lo < ri){
                int sum =  nums[i] + nums[lo] + nums[ri];
                int distance = nums[i] + nums[lo] + nums[ri] - target;
                if( Math.abs(distance)<minDistance){
                    minDistance = Math.abs(distance);
                    result = sum;
                }
                if(distance<0)
                    lo++;
                else if(distance>0)
                    ri--;
                else
                    return sum;
            }
        }
        return result;
    }

    public int threeSumClosest2(int[] nums, int target) {
        Arrays.sort(nums);
        Integer result = nums[0] + nums[1] + nums[2];
        Integer currentClosestDist = Math.abs(result - target);
        for(int mid = 1; mid<nums.length-1; mid++){
            for(int left = 0, right = nums.length-1;  left < mid && right > mid; ){
                int sum = nums[mid] + nums[left] + nums[right];
                int distance = sum - target;
                if(distance > 0){
                    if(distance < currentClosestDist){
                        result = sum;
                        currentClosestDist = distance;
                    }
                    right--;
                }else if(distance < 0){
                    if(-distance < currentClosestDist){
                        result = sum;
                        currentClosestDist = -distance;
                    }
                    left++;
                }else{
                    return result;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {

        int[] nums = {-1,2,1,-4};
        int[] nums2 = {4,0,5,-5,3,3,0,-4,-5};
        System.out.println(new ThreeSumClosest().threeSumClosest2(nums2,-2));
    }
}
