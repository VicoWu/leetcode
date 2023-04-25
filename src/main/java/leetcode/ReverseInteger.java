package leetcode;

/**
 * Question 7
 */
public class ReverseInteger {
    public int reverse(int x) {

        int result = 0;
        while(true){
            int remain = x % 10;
            // 提前预判是否是正数溢出，正整数的最后一位是7
            if(result > Integer.MAX_VALUE/10 || result == Integer.MAX_VALUE/10 && remain > 7){
                return 0;
            }
            // 提前预判是否是负数溢出，负数的最后一位数字是-8
            if(result < Integer.MIN_VALUE/10 || result == Integer.MIN_VALUE/10 && remain < -8){
                return 0;
            }
            result = result * 10 + remain;
            x = x / 10;
            if(x == 0) // 已经计算完毕最后一位了，可以退出了
                break;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new ReverseInteger().reverse(-2147483412));;
    }
}
