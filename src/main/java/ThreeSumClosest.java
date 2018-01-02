import java.util.Arrays;

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

    public static void main(String[] args) {

        int[] nums = {-1,2,1,-4};
        int[] nums2 = {0,1,2};
        System.out.println(new ThreeSumClosest().threeSumClosest(nums2,3));
    }
}
