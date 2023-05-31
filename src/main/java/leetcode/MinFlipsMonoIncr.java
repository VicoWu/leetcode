package leetcode;

/**
 * 剑指 Offer II 092. 翻转字符
 * Leetcode Question 926
 */
public class MinFlipsMonoIncr {
    public int minFlipsMonoIncr(String s) {
        int[] leftOne = new int[s.length()];
        int oneCount = 0;
        for(int i = 0;i<s.length();i++){
            if(s.charAt(i) == '1'){
               ++oneCount;
            }
            leftOne[i] = oneCount;
        }

        int result = s.length();
        int zeroCount = 0;
        // 从右向左，统计右侧的0的个数，因此，左侧的1的个数和右侧0的个数就是需要反转的次数
        // 其中， j=s.length()-1，表示全部反转成0的情况
        for(int j = s.length()-1; j>=0 ; j--){
            result = Math.min(zeroCount+leftOne[j], result);
            if(s.charAt(j) == '0')
                zeroCount++;
        }
        // 我们漏掉了全部反转成0的情况，全部变成1，需要反转的次数是zeroCount
        return Math.min(result, zeroCount);
    }

    public static void main(String[] args) {
        System.out.println(new MinFlipsMonoIncr().minFlipsMonoIncr("00110"));
    }
}
