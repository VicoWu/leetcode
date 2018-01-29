package SwordToOffer;

/**
 * Created by wuchang at 1/23/18
 * 剑指offer第11题
 * 二进制中1的个数
 * https://www.nowcoder.com/practice/8ee967e43c2c4ec193b040ea7fbb10b8?tpId=13&tqId=11164&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking&tPage=1
 *
 * 对应的相应讲解在[这里](https://www.cnblogs.com/edisonchou/p/4752086.html)
 */

public class NumberOfDigitOne {
    public int NumberOf1(int n) {
        int count = 0;

        while (n > 0)
        {
            if ((n & 1) == 1)
            {
                count++;
            }

            n = n >> 1;
        }

        return count;
    }

    public static int NumberOf1Solution2(int n)
    {
        int count = 0;
        int flag = 1;
        while (flag >= 1 || flag==Integer.MIN_VALUE)  //整数是32位，因此，而在最高位上是Integer.MIN_VALUE，因此，flag需要不断遍历,1,10,100,1000,.... ,  80000000
        {
            if ((n & flag)  == flag)
            {
                count++;
            }

            flag = flag << 1;
        }

        return count;
    }



    public static void main(String[] args) {
        System.out.println(new NumberOfDigitOne().NumberOf1(3));
        System.out.println(new NumberOfDigitOne().NumberOf1Solution2(Integer.MIN_VALUE));
        System.out.println(new NumberOfDigitOne().NumberOf1Solution2(0));
    }
}
