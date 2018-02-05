package leetcode;

/**
 * Created by wuchang at 1/8/18
 * Question 238
 用`res[i]`先保存`nums[0]*nums[1]...*nums[i-1]`的结果，即res[i]保存了`nums[i]`左侧的数的乘积（不包括`nums[i]`本身）。比如
 ```
 nums:    1  2  3  2  1  0  2  3
 res:     1  1  2  6  12 12 0  0
 ```
 这时候res中存放的还不是最终结果，我们这时候从右往左再来一轮，同时，遍历到每一个`nums[i]`的时候，我们使用一个变量right记录`nums[i]`右侧的数字的乘积，
 因此，我们从右往左遍历res的时候，对于每一个`nums[i]`，我们其实就知道了`nums[i]`左侧(目前保存在`res[i]`中)和右侧(保存在变量right中)的乘积，自然，二者相乘就可以了。
 */

public class ProductOfArrayExceptSelf {
    public int[] productExceptSelf(int[] nums) {

        int[] res = new int[nums.length];
        res[0]=1;
        for(int i=1;i<nums.length;i++){ //第一轮遍历，每一个res[i]都保存了nums[i]左侧的乘积(不包括nums[i])
            res[i] = res[i-1] * nums[i-1];
        }

        int right = 1; //保存右侧的乘积
        for(int i=nums.length-1;i>=0;i--){
            res[i] = res[i] * right;//res[i]此时存放了num[i]左侧的乘积，right存放了右侧的乘积，因此res[i] * right就是nums[i]对应的结果
            right = right*nums[i];
        }
        return res;
    }

    public static void main(String[] args) {
        int[] input = {1,2,3,2,1,0,2,3};
        new ProductOfArrayExceptSelf().productExceptSelf(input);

    }
}
