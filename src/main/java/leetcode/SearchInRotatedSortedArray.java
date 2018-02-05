package leetcode;

/**
 * Created by wuchang at 12/29/17
 * Question 33
 *
 *
 [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/description/)
 [Search in Rotated Sorted Array II](https://leetcode.com/problems/search-in-rotated-sorted-array-ii/)
 [Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/description/)
 [Find Minimum in Rotated Sorted Array II](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/description/)


 直接二分查找

 这是我自己的解法，对于一个区间[left,right]，如果pivot不在这个区间里面，肯定有nums[left]<=nums[right]，否则，如果pivot在这里面，必有nums[left] > nums[right]，比如{4,5,6,0,1,2,3}

 因此，对于区间[left,right]，我们从中间切分mid，pivot必定存在于[left,mid]和[mid,right]两个中的一个，所以，我们这样判断：

 - 如果target == nums[mid]， 我们自然就找到了。
 - 如果nums[left] <= nums[mid]，说明左侧是有序的，既然有序，我们通过判断target >= nums[left] && target < nums[mid]是不是在左侧这个区间，如果是，那么我们就在左侧这个区间进行常规的二分查找，否则，我们就在右侧进行递归调用
 - 如果nums[left] > nums[mid]，说明右侧是有序的，既然有序，我们通过判断target > nums[mid] && target <= nums[right]是不是在右侧这个有序区间，如果是，那么我们就在右侧这个区间进行常规的二分查找，否则，我们就在左侧进行递归调用

 这样，通过不断二分，我们就可以在log(n)时间内获取target是否在nums中。





 先找到pivot的位置，再在正常递增的子区间中进行普通的二分查找

 我们先通过二分查找的方式找到pivot的位置，因为，凡是包含pivot的区间[left,right]，必有nums[left] > nums[right]，这样，我们通过二分查找不断逼近，最终确认pivot的位置，比如，[4,5,6,0,1,2,3]，pivot的位置就是nums[3]=0；找到了pivot，则pivot左侧和右侧都是正常的递增区间，而且，我们很容易就知道target是在pivot的左侧还是右侧还是直接nums[pivot]=target。





 备注：一个性质

 其实，Rotated Sorted Array是对一个有序数组进行滚动得到的。

 比如nums=[0,1,2,3,4,5,6]

 r_nums = [4,5,6,0,1,2,3] 显然， pivot=3,即，r_nums[3] == nums[0]

 其实，对于nums的任意一个以pivot = j作为pivot得到的一个Rotated Sorted Array(名字交r_nums)，必有nums[i] = r_nums[(i+pivot)%n]，比如，pivot=3，则有

 nums[0]=r_nums[3],

 nums[1]=r_nums[4]

 nums[2]=r_nums[5]

 nums[3]=r_nums[6]

 nums[4]=r_nums[0]

 nums[5]=r_nums[1]

 这样，知道了原区间和折叠区间之间的元素位置映射关系，我们就通过这种对应关系，直接通过映射关系进行普通的二分查找；

 class Solution {
 public:
 int search(int A[], int n, int target) {
 int lo=0,hi=n-1;
 // find the index of the smallest value using binary search.
 // Loop will terminate since mid < hi, and lo or hi will shrink by at least 1.
 // Proof by contradiction that mid < hi: if mid==hi, then lo==hi and loop would have been terminated.
 while(lo<hi){
 int mid=(lo+hi)/2;
 if(A[mid]>A[hi]) lo=mid+1;
 else hi=mid;
 }
 // lo==hi is the index of the smallest value and also the number of places rotated.
 int rot=lo;
 lo=0;hi=n-1;
 // The usual binary search and accounting for rotation.
 while(lo<=hi){
 int mid=(lo+hi)/2;
 int realmid=(mid+rot)%n; //通过映射关系，直接进行二分查找
 if(A[realmid]==target)return realmid;
 if(A[realmid]<target)lo=mid+1;
 else hi=mid-1;
 }
 return -1;
 }
 };

 */

public class SearchInRotatedSortedArray {

    public int search2(int[] nums, int target) {
        int minIdx = findMinIdx(nums); //先找到pivot的位置(指进行rotate前第一个数字在rotate之后的位置)，比如{4,5,6,0,1,2,3}，则pivot的位置是index=4,即nums[0]的位置
        //找到pivot以后，pivot左侧和右侧的区间都是正常的递增区间
        if (target == nums[minIdx]) return minIdx;
        int m = nums.length;
        int start = (target <= nums[m - 1]) ? minIdx : 0;
        int end = (target > nums[m - 1]) ? minIdx : m - 1;

        //正常的二分查找
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) return mid;
            else if (target > nums[mid]) start = mid + 1;
            else end = mid - 1;
        }
        return -1;
    }

    /**
     * 确认pivot的位置，比如{4,5,6,0,1,2,3}，则pivot的位置是index=4,即nums[0]的位置
     * @param nums
     * @return
     */
    public int findMinIdx(int[] nums) {
        int start = 0, end = nums.length - 1;
        while (start < end) {
            int mid = start + (end -  start) / 2;
            if (nums[mid] > nums[end]) start = mid + 1;//nums[mid] > nums[end]，说明pivot肯定在[mid+1,end]之间，比如3,4,5,6,0,1,2,start = 0,end = 6,mid = 3
            else end = mid; //否则，在[start,mid]之间查找，比如[4,5,6,0,1,2,3],start = 0,end = 6,mid = 3
        }
        return start;
    }


    /**
     *  这是我自己的解法
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {

        return this.search(nums,0,nums.length-1,target);
    }


    public int search(int[] nums,int left,int right,int target){

        if(left> right)
            return -1;
        int mid = (left+right)/2;
        if(target == nums[mid])
            return mid;
        if(nums[left] <= nums[mid]){ //左侧有序，即pivot不在左侧
            if(target >= nums[left] && target < nums[mid])
                return normalBinarySearch(nums,left,mid-1,target); //确认有序，进行标准的二分查找
            else
                return search(nums,mid+1,right,target);
        }
        else{ //右侧有序，即privot不在右侧
            if(target > nums[mid] && target <= nums[right])
                return normalBinarySearch(nums,mid+1,right,target); //确认有序，进行标准的二分查找
            else
                return search(nums,left,mid-1,target);
        }
    }


    /**
     * 确定了一个区间是正常的二分区间，我们就可以对这个区间进行二分查找了
     * @param nums
     * @param left
     * @param right
     * @param target
     * @return
     */
    public int normalBinarySearch(int[] nums,int left,int right,int target){
        while (left <= right) {
            int mid = (left + right) / 2;
            if (target < nums[mid])
                right = mid - 1;
            else if (target > nums[mid])
                left = mid + 1;
            else return mid;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = {3,1};
        int[] nums2={1};
        System.out.println(new SearchInRotatedSortedArray().search(nums,1));
    }

}
