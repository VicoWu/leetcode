package leetcode;

/**
 * Question 343 整数拆分
 */
public class IntegerBreak {
    public int integerBreak(int n) {
        int[] cache = new int[n+1];
        cache[1] = 1;
        for(int l = 2; l<=n;l++){ // 绳子的长度逐渐增加
            int curMax = Integer.MIN_VALUE;
            for(int i = 1; i<=(l>>1); i++){ //确定一个左侧的位置，剪断
                int rightMax = Math.max(l-i, cache[l-i]);  //右侧可以选择拆分, 或者不拆分
                curMax = Math.max(curMax, i * rightMax);
            }
            cache[l] = curMax;
        }
        return cache[n];
    }

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
    }
}
