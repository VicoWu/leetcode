import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * 这一题我的思路是先确定三个数字的中间数字，然后按照与讨论中相同的方式去确定答案。
 * 但是这样做有一个问题非常不好处理，就是为了去除重复结果，必须处理输入的串中的重复字符
 * ，比如`[-2,-2,4]`，或者`[-4,2,2]`，如果我用`nums[m]!=nums[m+1]（m是三个数字中的中间数字）`来做过滤，
 * 那么`[-2,-2,4]`会被漏掉，而如果我用nums[m-1]!=nums[m]做过滤，那么`[-4,2,2]`会被过滤掉。
 但是，如果按照讨论区中的做法，先确定三个数字中的第一个数字，然后再确定三个数字中的剩下两个数字，
 只需要按照`i > 0 && num[i] != num[i-1]`中进行过滤，就可以了，即，如果输入中出现了相同数字如
 [-2,-2,4]或者`[-4,2,2]`这样的，只需要将第一次出现的-2作为第一个数字就行了，以后再出现-2都不再处理，
 应为第一次处理-2的时候肯定已经覆盖了所有情况。
 * Question 15
 */
public class ThreeSum {

    public List<List<Integer>> threeSum(int[] nums) {

        Arrays.sort(nums); //O(nlogn)
        List<List<Integer>> result = new LinkedList<List<Integer>>();

        for (int m = 1; m < nums.length - 1; m++) { //O(n^2)
            int i = m - 1;
            int j = m + 1;
            while (i >= (nums[m] == nums[m - 1] ? m - 1 : 0) && j < nums.length) {
                if (nums[i] + nums[m] + nums[j] > 0) {
                    i--;
                } else if (nums[i] + nums[m] + nums[j] < 0) {
                    j++;
                } else {
                    List<Integer> tempResult = new LinkedList<Integer>();
                    tempResult.addAll(Arrays.asList(nums[i], nums[m], nums[j]));
                    result.add(tempResult);
                    while (i > 0 && nums[i] == nums[i - 1] && i > 0) i--;
                    while (j < nums.length - 1 && nums[j] == nums[j + 1]) j++;//中心数字选定以后，左侧的数字去除重复
                    System.out.println("adding " + nums[i] + "," + nums[m] + "," + nums[j]);
                    i--;
                    j++;
                }
            }
        }
        return result;
    }




    public List<List<Integer>> threeSum2(int[] num) {
        Arrays.sort(num);
        List<List<Integer>> res = new LinkedList<List<Integer>>();
        for (int i = 0; i < num.length-2; i++) {
            if (i == 0 || (i > 0 && num[i] != num[i-1])) {
                int lo = i+1, hi = num.length-1, sum = 0 - num[i];
                while (lo < hi) {
                    if (num[lo] + num[hi] == sum) {
                        res.add(Arrays.asList(num[i], num[lo], num[hi]));
                        System.out.println("adding "+num[lo]+","+num[i]+","+num[hi]);
                        while (lo < hi && num[lo] == num[lo+1]) lo++;
                        while (lo < hi && num[hi] == num[hi-1]) hi--;
                        lo++; hi--;
                    } else if (num[lo] + num[hi] < sum) lo++;
                    else hi--;
                }
            }
        }
        return res;
    }




    public static void main(String[] args) {

        int[] params = {0,0,0,0};
        new ThreeSum().threeSum(params);
    }
}
