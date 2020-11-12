package leetcode;

/**
 * @author chang.wu
 * @date 11/7/20
 * 371. Sum of Two Integers
 * Question 371
 *
 * https://leetcode.com/problems/sum-of-two-integers/discuss/84278/A-summary%3A-how-to-use-bit-manipulation-to-solve-problems-easily-and-efficiently
 * https://leetcode.com/problems/sum-of-two-integers/discuss/132479/Simple-explanation-on-how-to-arrive-at-the-solution
 */
public class SumOf2Integers
{

    int getSum(int a, int b) {
        return b==0? a:getSum(a^b, (a&b)<<1); //be careful about the terminating condition;
    }

    /**
     *  a & b 得到的是每一位的进位值，左移一位是因为我们需要向高位进位
     *  a ^ b 得到的是每一个当前相加得到的当前位的值(1 + 0 = 0 + 1 = 1, 0 + 0 = 1 + 1 = 0)，尽管1+1 = （10）， 但是这时候carry(进位)是单独计算了，本位值还是0
     *  为什么终止条件是b == 0呢？当我们的carry变成0的时候，b就是0了
     *
     *  可以具体研究 case 1: 0011 + 0101
     *  case 2: 00001111 + 00001111
     * @param a
     * @param b
     * @return
     */
    public int getSum2(int a, int b) {
        int c;
        while(b !=0 ) {
            c = (a&b); // 两个bit的and代表了进位的结果
            a = a ^ b; // 两个bit的异或代表两个bit位相加的结果
            b = (c)<<1; //向上一位进位
        }
        return a;

    }




    public static void main(String[] args)
    {
        System.out.println(new SumOf2Integers().getSum(1, 2));
        System.out.println(new SumOf2Integers().getSum(5000, 500000000));
        System.out.println(new SumOf2Integers().getSum2(-1, 2));
        System.out.println(new SumOf2Integers().getSum2(1, -2));
    }
}
