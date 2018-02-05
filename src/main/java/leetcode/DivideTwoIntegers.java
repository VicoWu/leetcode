package leetcode;

/**
 * Created by wuchang at 12/21/17
 * Question 29
 *
 * 大致思路很简单，比如28/5

 ```
 5*1 = 5  , 5<28
 5*2 = 10 ，10<28
 10*2=20 , 20 < 28
 20*2 - 40 40> 28，超出范围
 此时结果为4，还剩下一个28-20=8没有算，然后，对8开始计算
 5*1 = 5 , 5<8
 5*2 = 10, 10> 8
 此时有效的相除结果为1 ， 还剩下8-5 = 3

 由于3<5 退出，结束

 即，我们将28/5 拆分为 5^4 + 5^1 +3 来进行计算。
 ```

 最困难的是溢出控制：

 为了统一处理正数和负数，我们首先将dividend和divisor的正负关系记录下来，然后取绝对值再进行计算：

 ```
 int sign = ((dividend < 0) ^ (divisor < 0))?-1:1;
 dividend = Math.abs(dividend);divisor = Math.abs(divisor);
 ```

 最终结果会根据符号来进行正数和负数的转换：

 ```
 return sign==1?result:-result;
 ```



 但是这样带来一个问题：由于整数的范围是-2147483648到2147483647,-2147483648取了绝对值的数字超过了整形的范围，因此，必须单独处理，处理逻辑是：

 如果发现dividend=-2147483648:

 - 如果divisor=1，返回-2147483648

 - 如果divisor=-1，这时候发生了整形溢出，按照题目要求返回Integer.MAX_VALUE

 - 如果divisor是其它值，结果怎么计算呢？我们根据divisor是奇数（divisor&1==1）还是偶数(divisor&1==0)来判断，
 如果是奇数，-2147483648/divisor 肯定等于(-2147483648+1)/divisor。问题来了，如果-2147483648可以被某个奇数整除，
 那么(-2147483648+1)/divisor  != -2147483648/divisor ，比如，-10/5 != (-10+1)/5，但是事实是-2147483648是2^k，
 因此它不可能被某个奇数整除，因此对任何奇数的divisor必定有(-2147483648+1)/divisor  != -2147483648/divisor。
 那么如果divisor是偶数怎么处理？那就二者都减小一倍处理：

 ```
 ((divisor & 1) == 1)
 ?(divide(dividend+1,divisor))
 :divide(dividend>>1,divisor>>1);
 ```

 ​



 如果divisor=-2147483648，后面取绝对值也会溢出，因此需要特殊处理。由于任何整数除以Integer.MIN_VALUE结果为0或者1，
 当dividend==Integer.MIN_VALUE时结果是1，其它结果是0。
 */

public class DivideTwoIntegers {
    public int divide(int dividend, int divisor) {

        if(divisor==0)
            return Integer.MAX_VALUE;

        if(dividend == Integer.MIN_VALUE){
            if(divisor == -1) return Integer.MAX_VALUE;
            else if(divisor == 1) return dividend;
            else {
                return ((divisor & 1) == 1)
                        ?(divide(dividend+1,divisor)):divide(dividend>>1,divisor>>1);
            }
        }
        if(divisor == Integer.MIN_VALUE) //当dividend == Integer.MIN_VALUE结果是1，其它情况结果是0.由于dividend == Integer.MIN_VALUE的情况上面已经处理，因此这里直接返回0
            return 0;
        int result=0;
        int sign = ((dividend < 0) ^ (divisor < 0))?-1:1;
        dividend = Math.abs(dividend);divisor = Math.abs(divisor);
        int dvs = divisor;

        while(dividend >= dvs){
            int times = 1;
            while(dividend > dvs){

                //这里必须判断 dvs<<1 > 0 ,防止dvs在加倍的过程中由于溢出变成了0，造成死循环，比如，输入是2147482647, 1
                if( dvs<<1 > 0 && dividend  >= dvs<<1){
                    times = times<<1;
                    dvs = dvs<<1;
                }
                else
                    break;
            }
            result +=times;
            dividend = dividend - dvs;
            dvs = divisor;
        }

         return sign==1?result:-result;
    }

    public static void main(String[] args) {
//        System.out.println(-15/3);
//        System.out.println(-14/3);
//        System.out.println(1&1);
//        System.out.println(2&1);
//        System.out.println(3&1);
//        System.out.println(4&1);
//        System.out.println(Integer.MAX_VALUE+" "+Integer.MIN_VALUE);
        System.out.println(new DivideTwoIntegers().divide(-2147483648,2));

//        for(int i=3;i<2147483648L;i+=2){
//
//            Long result =  2147483648L % i;
//            if(result < 3)
//                System.out.println(result);
//        }

    }
}
