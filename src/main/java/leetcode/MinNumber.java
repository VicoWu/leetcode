package leetcode;

/**
 * 本质上是排序，定义好了比较规则，那么剩下的就是排序算法选择，可以用o(n2)来暴力排序，可以用o(nlogn)来进行归并或者选择排序
 */
public class MinNumber {
    public String minNumber(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<nums.length;i++){
            sb.append(Integer.valueOf(nums[i]).toString());
        }
        return sb.toString();
    }

    public void quickSort(int[] nums, int i , int j){
        if(i == j){
            return;
        }
        if(i + 1 == j){
            if(compare(nums,i,j) > 0){
                int tm = nums[i];
                nums[i] = nums[j];
                nums[j] = tm;
            }
            return;
        }
        int k = (i+j)/2;
        quickSort(nums, i, k);
        quickSort(nums, k+1, j);
        int cur1 = i;
        int cur2 = k+1;
        int cur = 0;
        int[] cache = new int[j - i + 1];
        while(cur1 <= k && cur2 <= j){
            if(compare(nums, cur1, cur2) < 0)
            {
                cache[cur++] = nums[cur1++];
            }else{
                cache[cur++] = nums[cur2++];
            }
        }
        for(;cur1<=k;){
            cache[cur++] = nums[cur1++];
        }
        for(;cur2<=j;){
            cache[cur++] = nums[cur2++];
        }
        for(int m = 0;m<=j-i;m++){
            nums[m+i] = cache[m];
        }
    }

    private int compare(int[] nums, int i, int j){
        String a = Integer.valueOf(nums[i]).toString();
        String b = Integer.valueOf(nums[j]).toString();
        return (a + b).compareTo(b+a);
    }

    public static void main(String[] args) {
        int[] input = {3,30,34,5,9};
        new MinNumber().minNumber(input);
    }
}
