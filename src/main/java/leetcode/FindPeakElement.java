package leetcode;

/**
 * Created by wuchang at 1/11/18
 * Question 162
 问题：假如有一个数组nums，中间的某个元素为nums[mid]，两边的元素分别为nums[low]和nums[hi]，即形成了一个**拱形结构**，并且有 nums[low]<nums[mid]>nums[hi]，即nums[mid]是最大的。问题：这个数组是不是一定存在一个peek元素？是~
 我们用反证法，假如在这种情况下，这个peek元素不存在，那么，[low,mid]以及[mid,hi]都是递增区间或者凹陷区间，比如(1,2,3,4)这种递增区间以及[4,3,2,3,4]这种凹陷区间，可以，如果[low,mid]以及[mid,hi]是递增区间或者凹陷区间，那么nums[mid]本身就成为了peek元素，与假设发生矛盾。

 注意，题目中写明 ：`num[-1] = num[n] = -∞.`，也就是说，假如nums={1,2,3,4}或者nums={4,3,2,1}，那么4就是一个peek元素。

 有了上述的证明，我们就可以通过判断nums[lo]、nums[mid]、nums[hi] 三个元素之间的关系，获取二分的规则：
 ```
 If num[i-1] < num[i] > num[i+1], then num[i] is peak
 If num[i-1] < num[i] < num[i+1], then num[i+1…n-1] must contains a peak
 If num[i-1] > num[i] > num[i+1], then num[0…i-1] must contains a peak
 If num[i-1] > num[i] < num[i+1], then both sides have peak
 ```

 这里是[解法1](https://leetcode.com/problems/find-peak-element/discuss/50236)

 这里是[解法2](https://leetcode.com/problems/find-peak-element/discuss/50232)，解法2原理相同，但是代码比较简单：
 比较nums[mid]和nums[mid+1]的大小，如果num[mid1] < num[mid2]，则num[mid1] 、num[mid2]以及num[n]形成了我们上面所证明的**拱形结构**，所以在[mid2,n]之间肯定能找到peek元素，否则，由于题目已经说明num中相邻的元素不相同，因此必有num[mid1] > num[mid2]，此时，nums[0],num[mid1],num[mid2]形成了**拱形结构**，所以在[0,mid1]之间肯定存在peek元素。这样形成了二分查找规则。


 */

public class FindPeakElement {



    public int findPeakElement(int[] num)
    {
        int low = 0;
        int high = num.length-1;

        while(low < high)
        {
            int mid1 = (low+high)/2;
            int mid2 = mid1+1;
            if(num[mid1] < num[mid2])
                low = mid2;
            else
                high = mid1;
        }
        return low;
    }


    public static void main(String[] args) {

    int[] nums = {10,12,15,9,20,30,35,40,45,50};
    new FindPeakElement().findPeakElement(nums);
    }
}
