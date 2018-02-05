package leetcode;

/**
 * Created by wuchang at 12/28/17
 *
 * Question 34
 * 自己的解法，先通过二分查找找到某个target，再在两侧分别向左右二分查找

 先通过普通的二分查找，试图找到任何一个target数字，即：

 当nums[middle] < target，我们就在[middle+1,right]范围内查找；

 当nums[middle] > target，我们就在[left,middle-1]查找，

 如果没有找到，则返回{-1,-1}

 而如果找到了一个target，那么我们就开始确定范围：

 查找最左侧的target：如果此时middle=0，或者， nums[middle-1] < target ，说明左侧的值不是target，因此当前值就是最左侧值。但是如果我们发现左侧值也是target，则此时我们将范围设置为[left,middle-1]，在左侧继续进行二分查找。为了标记当前是要找到最左侧值，我们使用标记为direction="left"

 查找最右侧的target：如果此时middle=nums.length-1，或者， nums[middle+1] > target ，说明右侧的值不是target，因此当前值就是最右侧值。但是如果我们发现右侧值也是target，则此时我们将范围设置为[middle+1,right]，在右侧继续进行二分查找。为了标记当前是要找到最右侧值，我们使用标记为direction="right"



 两轮二分查找法

 这里有对这个方法的详细解释

 这里也是通过二分查找法进行查找，并且，使用单纯的二分查找，并且，保证单纯的二分查找算法最终找到的时候，这个值就是最左侧值，或者就是最右侧值。

 我们平常使用二分查找是这样的：如果target>nums[middle]，则将范围缩减为[middle+1,right]，如果target<nums[middle]，则将范围缩减为[left，middle-1]，在这里，为了能够在找到target以后，能够继续向左逼近进而找到最左侧的target，或者能够继续向右逼近进而找到最右侧的target，我们这样修改二分查找逻辑：

 - 第一轮二分查找，找到最左侧的target：

 mid = (high+low)/2

 如果target > nums[mid] ， 则范围修改为[mid+1,high]

 如果target <= nums[mid] ， 则范围修改为[low,mid]，这里体现出了不同，右侧范围依然保留mid，由于我们要找最左侧的target，因此，即使当前target=nums[mid]，由于我们还不清楚这个target是不是最左侧的，因此我们保留target的位置，在左侧继续查找

 当递归退出，low值就指向了最左侧的target



 - 第二轮二分查找，找到最右侧的target：

 mid = (high+low)/2 +1 ，注意，这里+1是由于我们计算机的除法运算总是偏向左侧(比如，(5+0)/2 == 2 而不是3)，因此加1，让它偏向右侧((5+0)/2 + 1 == 3)

 如果target < nums[mid] ， 则范围修改为[low,mid-1]

 如果target >= nums[mid] ， 则范围修改为[mid,high]，这里体现出了不同，右侧范围依然保留mid，由于我们要找最右侧的target，因此，即使当前target=nums[mid]，由于我们还不清楚这个target是不是最右侧的，因此我们保留target的位置，在右侧继续查找

 当递归退出，high值就指向了最右侧的target

 */

public class SearchForARange {
    /**
     * 这是我自己的解法
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        return search(nums,0,nums.length-1,target,null);
    }

    /**
     * 这是我自己的解法
     * 使用标记位direction代表当我们发现了target以后，是向左查找还是向右查找
     * 我们通过二分查找法，先试图找到一个对应的target，找到target以后，我们判断左侧是否还是target，如果不是，则这个target已经是最左侧的target了，而如果是，则需要在左边继续进行二分查找，右侧同理
     *
     * @param nums
     * @param left
     * @param right
     * @param target
     * @param direction
     * @return
     */
    private int[] search(int[] nums,int left, int right,int target,String direction){
        if(nums.length==0 || left > right  ) //没有找到这个值，则返回{-1,-1}
        {
            int[] result = {-1,-1};
            return result;
        }
        int middle = (left+right)/2;

        if(nums[middle] < target)
            return search(nums,middle+1,right,target,null);
        else if(nums[middle] > target)
            return search(nums,left,middle-1,target,null);
        else{
            //我们找到了target，此时，需要向两侧分别查找
            int[] result = new int[2];
            if(direction==null || "left".equals(direction))
            {
                if(middle== 0 || nums[middle-1] < target) //如果middle已经为0，或者，middle左侧的值不是target，那么当前的target就是最小位置的target
                    result[0] = middle;
                else
                    result[0] = search(nums,left,middle-1,target,"left")[0]; //否则，在左侧继续查找
            }
            if(direction==null || "right".equals(direction)){
                if(middle == nums.length-1 || nums[middle+1] > target) //如果middle==nums.length-1，即右侧没有值了，或者，nums[middle+1] > target即右侧的值大于target，说明这个target就是最右侧的target
                    result[1] = middle;
                else //这个值不是最右侧的target，还需要在右侧进行递归查找
                    result[1] = search(nums,middle+1,right,target,"right")[1];
            }

            return result;
        }
    }


    /**
     * 这是[这里](https://leetcode.com/problems/search-for-a-range/discuss/14699/)的解法
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange2(int[] nums, int target) {
        int hi = nums.length - 1;
        int low = 0;
        int[] rs = new int[2];
        // base case
        if (nums == null || nums.length == 0)
            return new int[]{-1, -1};

        //第一轮二分查找，找到最左侧的target
        while (low < hi) {
            int mid = (hi+low)/2;
            System.out.println("left : "+low+","+mid+","+hi);
            if (target > nums[mid]) {
                low = mid + 1;
            } else {
                hi = mid;
            }
        }
        if (target == nums[low]) {
            rs[0] = low;
        } else {
            rs[0] = -1;
        }

        //第二轮二分查找，找到最右侧的target
        hi = nums.length - 1;
        while (low < hi) {
            int mid = (hi+low)/2 + 1;
            System.out.println("right : "+low+","+mid+","+hi);

            if (target < nums[mid]) {
                hi = mid - 1;
            } else {
                low = mid;
            }
        }
        if (target == nums[hi]) {
            rs[1] = hi;
        } else {
            rs[1] = -1;
        }
        return rs;
    }


    public static void main(String[] args) {
        int[] nums = {2,2};
        int[] nums2 = {5, 7, 7, 8, 8, 10};
        int[] result = new SearchForARange().searchRange2(nums2,8);
        System.out.println(result[0]+","+result[1]);
    }

}
