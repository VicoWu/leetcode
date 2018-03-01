package leetcode;

/**
 * Created by wuchang at 3/1/18
 * Question 233
 */

public class NumberOfDigitOne {
    public int countDigitOne(int n) {
        if (n <= 0)
            return 0;
        int count = 0;    //统计1出现的次数
        long current;    //当前位
        long base = 1; //当前位的基
        long remain = 0;   //当前位为1时，后面位剩余的数（即不完整的部分）中1出现的次数
        while (n > 0) {
            current = n % 10;
            n = n / 10;

            if (current > 1)
                count += (n + 1) * base;
            else if (current == 1)
                count += n * base + (remain + 1);
            else
                count += n * base;

            //下一位要用到的基和剩余不完整部分值
            remain += current * base;
            base *= 10;
        }
        return count;
    }


    public static void main(String[] args) {
        new NumberOfDigitOne().countDigitOne(13);
    }


}
