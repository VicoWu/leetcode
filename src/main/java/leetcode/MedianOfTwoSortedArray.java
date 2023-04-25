package leetcode;

public class MedianOfTwoSortedArray {

    public double findMedianSortedArrays3(int[] nums1, int[] nums2) {
        int k = (nums1.length + nums2.length)/2;
        if( (nums1.length + nums2.length) % 2 == 0){ // 元素个数的和为偶数，因此中位数是中间两个元素的平均值

            return (findKthItem(nums1, nums2, 0,  0,  k)
                    + findKthItem(nums1, nums2, 0,   0,  k+1)
            )/2;
        }
        else
        {
            // 元素个数为奇数，因此中位数为中间的那个元素， 第k个元素
            return findKthItem(nums1, nums2, 0, 0, k+1);
        }
    }

    private double findKthItem(int[] nums1, int[] nums2, int left1, int left2, int k){
        int mid = k/2;
        if(left1 == nums1.length)
            return nums2[left2+k-1];
        else if(left2 == nums2.length)
            return nums1[left1+k-1];
        if(k == 1){
            return Math.min(nums1[left1], nums2[left2]);
        }
        int last1 = Math.min(left1 + mid, nums1.length) - 1;
        int last2 = Math.min(left2 + mid, nums2.length) - 1;

        if(nums1[last1] < nums2[last2]){
            k = k - (last1 - left1 + 1);
            return findKthItem(nums1, nums2, last1 + 1,left2,  k);
        }else{
            k = k - (last2 - left2 + 1);
            return findKthItem(nums1, nums2, left1,  last2 + 1,  k);
        }
    }
    public static void main(String[] args){
        int[] a = {};
        int[] b = {2};
        System.out.println("hello world");
        //System.out.println(new Solution().findMedianSortedArrays(a,b));
        System.out.println(new MedianOfTwoSortedArray().findMedianSortedArrays3(a,b));
    }
}

class Solution {



    public double findMedianSortedArrays2(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        if (m > n) { // to ensure m<=n
            int[] temp = A; A = B; B = temp;
            int tmp = m; m = n; n = tmp;
        }
        int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2; //halfLen=1;
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i < iMax && B[j-1] > A[i]){
                iMin = iMin + 1; // i is too small
            }
            else if (i > iMin && A[i-1] > B[j]) {
                iMax = iMax - 1; // i is too big
            }
            else { // i is perfect
                int maxLeft = 0;
                if (i == 0) { maxLeft = B[j-1]; }
                else if (j == 0) { maxLeft = A[i-1]; }
                else { maxLeft = Math.max(A[i-1], B[j-1]); }
                if ( (m + n) % 2 == 1 ) { return maxLeft; }

                int minRight = 0;
                if (i == m) { minRight = B[j]; }
                else if (j == n) { minRight = A[i]; }
                else { minRight = Math.min(B[j], A[i]); }

                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }


    private String printArray(int[] params){
        if(params == null)
            return "";
        else
        {
            StringBuffer sb = new StringBuffer();
            for(Object o:params){
                sb.append(o.toString()+" ");
            }
            return sb.toString();
        }
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        try {

            int[] shorter = null;
            int[] longer = null;
            if(nums1.length == 0 && nums2.length == 0)
                throw new Exception("wrong input");
            if(nums1.length==0){
                return (nums2.length%2 ==1 ? nums2[nums2.length/2] : ((double)(nums2[nums2.length/2-1] + nums2[nums2.length/2])/2));
            }
            if(nums2.length==0){
                return (nums1.length%2 ==1 ? nums1[nums1.length/2] : ((double)(nums1[nums1.length/2-1] + nums1[nums1.length/2])/2));
            }
            if (nums1.length < nums2.length) {
                shorter = nums1;
                longer = nums2;
            } else {
                shorter = nums2;
                longer = nums1;
            }


            int iMin = 0, iMax = shorter.length;


            int i = (iMin + iMax) / 2;
            int j = (shorter.length + longer.length) / 2 - i;


            while (iMin  <= iMax) { //i >0 && i<m

                int maxLeft=0,minRight = 0;

                if(i< iMax && shorter[i] < longer[j-1]){  // i is too small
                    iMin = i+1;
                    i = (iMin + iMax) / 2;
                    j = (shorter.length + longer.length) / 2 - i;

                }
                else if(i>iMin && longer[j] < shorter[i-1]){ //i is too big
                    iMax = i-1;
                    i = (iMin + iMax) / 2;
                    j = (shorter.length + longer.length) / 2 - i;
                }
                else{

                    if(i==0) maxLeft = longer[j-1];
                    else if (j==0) maxLeft = shorter[i-1];
                    else maxLeft = Math.max(longer[j-1],shorter[i-1]);


                    if(i==shorter.length) minRight = longer[j];
                    else if(j == longer.length) minRight = shorter[i];
                    else minRight = Math.min(shorter[i],longer[j]);

                    if((shorter.length + longer.length) % 2 ==1 ) return minRight;
                    return ((double)(maxLeft+minRight))/2;
                }
            }
            return 0.0;
        }catch(Exception e){
            System.out.println("input "+this.printArray(nums1)+"    ---     "+this.printArray(nums2));
            return 0.0;
        }
    }
}
