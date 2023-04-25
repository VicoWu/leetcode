package leetcode;

/**
 * Created by wuchang at 12/26/17
 * Question 50
 * #  我的递归解法
 [这篇帖子](https://discuss.leetcode.com/topic/21837/5-different-choices-when-talk-with-interviewers/20)总结了五种好的计算方法，我使用的是第二种方法`double myPow`
 刚开始以为这里考察的是精读处理或者乘法转加法。但是其实可以直接用乘法，考察的是算法的效率。
 暴力的方式的计算：
 ```
 double result=1.0d;
 for(int i=0;i<n;i++){
 result*=x;
 }
 ```
 这样，对于输入n，我们需要进行n-1次乘法运算。但是我们完全可以通过二分法进行计算；
 比如2^10 = 2^5 * 2^5 ,我们只需要计算一次2^5，然后对于2^5， 等于2 *  2^2 ， 对于 2^2 , 为2*2
 以此类推。

 其它几种算法的思想都是基于这个。
 这里详细讲解一下最后一种方法，位运算方法

 # 位运算方法
 链接在[这里](https://discuss.leetcode.com/topic/3636/my-answer-using-bit-operation-c-implementation)
 我们要求的是x^n， 假如n=10 = 00001010x，即10=8+2,那么如果我们预先计算好了2^8和2^2，
 则就直接拿来用了。即，任何一个整数n，都可以表示成`k0*2^0 + k1*2^1 + ... +k31*2^31`，其中ki为0或者1，代表对应的二进制位是0还是1。

 因此，我们事先把2^1，2^2 ， 2^3， .... 2^31计算好放到一个数组tbl里面
 然后假如n=10，对n的每一个二进制位进行判断是否为1：
 ```
 (n & (0x1 << i) ) != 0
 ```
 如果为1 ， 则结果乘上去。
 比如，n=10=00001010x， 则会和0x1<<1 以及0x1<<3进行与运算的时候不为0，因此，2.0^10 = 2.0^8 * 2.0^2 = tbl[7] * tbl[1] = 1024。











 `

 */

public class Pow {
    /**
     *
     * 这是我使用的方法。大致思想是：
     * 比如myPow(2.0,10)，则我们就计算2.0^5 * 2.0^5,而且不需要重复计算，
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        if (n == 1)
            return x;
        if (n == 0)
            return 1;
        if (n < 0) {
            n = -n;
            if (n > 0)
                //这里的处理是为了防止溢出，比如x=Integer.MIN_VALUE,由于0x80000000取负数以后由于溢出还是本身，这样，我们myPow(2,-2147483648)就变成了myPow(0.5,-1073741824) * myPow(0.5,-1073741824)
                //结果趋于无穷大，实际上结果是0，因此，加一个判断，通过n>0判断是否发生溢出，没有溢出才将x=1/x，这样，myPow(2,-2147483648) 就会成为myPow(2,-1073741824) * myPow(2,-1073741824) ,结果才正确
                x = 1 / x;
        }
        double result = myPow(x, n / 2);  //保存结果，不需要计算两次
        return n % 2 == 0 ? result * result : x * result * result;
    }

    public double pow(double x, int n) {
        if(n == 0)
            return 1;
        if(n<0){
            n = -n;
            x = 1/x;
        }
        return (n%2 == 0) ? pow(x*x, n/2) : x*pow(x*x, n/2);
    }


    /**
     * 迭代方式
     * @param x
     * @param n
     * @return
     */
    double myPowByInterative(double x, int n) {
        if(n==0) return 1;
        if(n<0) {
            n = -n;
            x = 1/x;
        }
        double ans = 1;
        while(n>0){
            if((n & 1) !=0)
                ans *= x;
            x *= x;
            n >>= 1;
        }
        return ans;
    }


    /**
     * 位运算方式
     * https://discuss.leetcode.com/topic/3636/my-answer-using-bit-operation-c-implementation
     * @param x
     * @param n
     * @return
     */
    double powByBIT(double x, int n) {
        if(n<0){
            x = 1.0/x;
            n = -n;
        }

        double[] tbl = new double[32];
        double result = 1;
        tbl[0] = x;
        for(int i=1;i<32;i++){
            tbl[i] = tbl[i-1]*tbl[i-1];
        } //我们事先把2^1 2^2 2^3 .... 2^31计算好
        for(int i=0;i<32;i++){
            if( (n & (0x1 << i) ) != 0) //进行位匹配，假如n=10=00001010x， 则会和1<<1 以及1<<3进行匹配，因此，2.0^10 = 2.0^8 * 2.0^2 = tbl[7] * tbl[1] = 1024
                result *= tbl[i];
        }
        return result;
    }


    public double myPow3(double x, int n) {
        if(n == 0)
            return 1;
        if(n == 1)
            return x;
        if(n == Integer.MIN_VALUE){
            return (1/x) * compute(x,-Integer.MAX_VALUE);
        }
        return compute(x, n);
    }


    private double compute(double x, int n){
        long k = n <  0 ? -n: n;
        long t = 2;
        double res = ((1 & k) == 1 ? x : 1.0d);
        double curValue = x;
        while(t <= k){
            long v =  k & t;
            curValue = curValue * curValue;
            if(v == t){
                res = res * curValue;
            }
            t = t<<1;//左移动一位
        }
        if(n < 0)
            return 1/res;
        return res;
    }


    public static void main(String[] args) {

        System.out.println(new Pow().myPow3(2.0, -2147483648));
        //System.out.println(Integer.MIN_VALUE/2);
        //new Pow().myPow(34.00515,-3);
    }
}
