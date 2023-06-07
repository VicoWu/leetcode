package leetcode;

public class RotateArray {
    public void rotate(int[] nums, int k) {
        int range = gcd(nums.length, k);
        int l = nums.length;
        for(int i = 0;i<range;i++){
            int pre = i;
            int cur = i + k;
            int src = nums[pre];
            while((cur % l) != (i )){
                int bak = nums[cur % l];
                nums[cur % l] = src;
                pre = cur;
                cur = cur + k;
                src = bak;
            }
            src = nums[i];
        }
    }
    public int gcd(int a, int b){
        if(b == 0){
            return a;
        }
        return gcd(b, a%b);
    }

    public static void main(String[] args) {
        int[] input = {1,2,3,4,5,6,7};
        new RotateArray().rotate(input, 3);
    }
}
