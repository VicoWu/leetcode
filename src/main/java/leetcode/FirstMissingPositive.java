package leetcode;

/**
 * Created by wuchang at 12/16/17
 * Question 41
 */

public class FirstMissingPositive {
    int firstMissingPositive(int A[])
    {
        int n = A.length;
        for(int i = 0; i < n; ++ i){
            while(A[i] > 0
                    && A[i] <= n
                    && A[A[i] - 1] != A[i]){
                int tmp = A[i];
                A[i] = A[tmp-1];
                A[tmp-1] = tmp;

            }
        }


        for(int i = 0; i < n; ++ i)
            if(A[i] != i + 1)
                return i + 1;

        return n + 1;
    }

    public int firstMissingPositive3(int[] nums) {
        int N = nums.length;
        for(int i = 0;i<nums.length;i++){
            if(nums[i] > N || nums[i] <= 0 || nums[i] == i + 1){
                continue;
            }
            int curValue = nums[i]; // 当前待处理的元素位置
            int targetIndex = nums[i] - 1; // 该元素放置的目标位置
            int targetTemp = nums[targetIndex];
            nums[i] = -1;
            while(true){
                targetTemp = nums[targetIndex];
                nums[targetIndex] = curValue; // 放到正确的位置去
                if(targetTemp > N || targetTemp <= 0 || targetTemp == targetIndex + 1  || targetTemp - 1 == i) // 不在1 ~ N范围内，可以退出来了
                {
                    break;
                }
                else { // 需要继续交换
                    curValue = targetTemp;
                    targetIndex = targetTemp - 1; // 不断调整位置
                }
            }
            nums[i] = targetTemp;
        }

        for(int i = 1;i<=nums.length;i++){
            if(nums[i-1] != i){ // 找到第一个缺失的正整数
                return i;
            }
        }
        return nums.length + 1;
    }


//    public static void main(String[] args) {
//        int[] result = {-1,2,4};
//       System.out.println(new FirstMissingPositive().firstMissingPositive(result)) ;
//      //  System.out.println(new FirstMissingPositive().firstMissingPositive1(result)) ;
//    }

//    int firstMissingPositive1(int A[]){
//
//        int min=Integer.MAX_VALUE ;int max = -1;
//        int sum = 0;
//        for(int i=0;i<A.length;i++){
//            if(A[i] > 0)
//            {
//                if(A[i] < min) min = A[i];
//                if(A[i] > max) max = A[i];
//                sum+=A[i];
//            }
//        }
//        if(min != 1) return 1;
//
//        int expected = expectedSum(max);
//        if(expected == sum) return max+1;
//        else return expected - sum;
//
//    }
//
//    private int expectedSum(int end){
//        return (1+end)* (end) /2;
//    }

    public static void main(String[] args) {
        int[] nums = {2, 1};
        new FirstMissingPositive().firstMissingPositive3(nums);
    }
    private void printArray(int[] input)
    {
        String s = "";
        for(int i=0;i<input.length;i++){
            s=s+ input[i] +" ";
        }
        System.out.println(s);
    }
}
