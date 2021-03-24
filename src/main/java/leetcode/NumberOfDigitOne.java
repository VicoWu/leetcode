package leetcode;

/**
 * Created by wuchang at 3/1/18
 * Question 233
 *
 * 这道题从最低位开始，逐个向高位计算每一位中1出现的 次数
 * current代表当前位的数值，n代表当前位的前面的数值，比如，7431,最低位current = 1, n = 743
 * 根据current是不是等于1，次数是不一样的
 * 比如，对于7413:
 * current是倒数第二位， current=1，  n = 74， base  = 10
 * ，显然，高两位可以从0一直到73，总共74次，但是，当到了74的时候，base就不是10了，比如， 7419就超出了 7413的范围，因此，只能是n * base + (remain + 1);
 * current是倒数第三位，current=4, n = 7, base = 100, 因此倒数第三位1出现的 次数是8 * 100 = 800
 *
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
