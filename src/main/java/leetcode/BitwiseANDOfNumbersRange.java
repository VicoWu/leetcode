package leetcode;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wuchang at 1/12/18
 * Question 201
 *
#  我的解法
 对于[m,n]，如果  2^i  =< m,n < 2^(i+1)，那么，m和n之间的任何数在2^i这个位置肯定都是1，举例子

 ```
 [5,7] :
 0101  5
 0110  6
 0111  7
 由于    4和7都在[2^2,2^3)（左闭右开），那么4和7在2^2位置都是1，结果是4

 [4,8]
 0101  5
 0110  6
 0111  7
 1000  8
 [4,8]，和上一个例子相比，7变成了8，即在2^2的位置从1变成了0，最后的结果是0

 [14,16]
 0000 1100  14
 0000 1101  15
 0000 1110  16
 显然由于   14,16都在[2^3,2^4)(左闭右开)的范围内，因此显然在2^3的位置是1，同时，我们可以看到在2^2的位置也是1，怎么计算？由于在2^3的位置的数字已经处理了，因此我们只需要关心2^2和以后的几位，即我们只需要比较100 ~ 110，即[14 - 2^3,16-2^3]的范围的结果：

 for(int i=29;i>=0;i--){
 if(min >= t<<i && min<t<<(i+1) && max >= t<<i && max<t<<(i+1)){
 res = res |  t<<i;
 min -=  t<<i;
 max -=  t<<i;
 }
 }
 ```

 关于溢出：
 最大的整数是0111 1111 11111111 11111111 11111111
 即2^31 == -2147483648。
 如果输入的数字中有2147483647，加入不考虑到溢出，我们就需要判断  2147483647是否在[2^30,2^31)之间，而2^31发生溢出，因此会造成判断失误。
 解决异常当然可以使用long，我在代码中把[2^30,2^31)单独进行判断，判断范围是不是在[2^30,2^31-1] (左闭右闭)，2^31-1 = Integer.MAX_VALUE:
 ```
 if(min >= t<<30 && min<=Integer.MAX_VALUE && max >= t<<30 && max<=Integer.MAX_VALUE) {
 res = res | t << 30;
 min -= t << 30;
 max -= t << 30;

 }
 ```

 #  别人的解法
 输入数字m和n右对齐，然后我们从低位逐渐往高位判断，如果以当前位作为最低位的数字不同，则肯定出现过0和1，因此and以后的结果肯定是0，如果以当前位作为最低位的数字相同，则这个范围内的数字and以后的结果的高位肯定就是这些数字，低位肯定都是0：

 ```
 [14,16]
 0000 1100  14
 0000 1101  15
 0000 1110  16
 第一位，由于1100 和1110不同，因此结果是0
 第二位，由于110 和 111 不同，因此结果是0
 从第三位开始结果就相同了
 因此，最终结果就是目前的高位11右移两位得到，即1100
 ```

 */


public class BitwiseANDOfNumbersRange {
    public int rangeBitwiseAnd(int m, int n) {


        ConcurrentHashMap k;
        int min = Math.min(m,n);int max = Math.max(m,n);


        int res = 0;
        int t = 1;

        if(min >= t<<30 && min<=Integer.MAX_VALUE && max >= t<<30 && max<=Integer.MAX_VALUE) { //为了防止溢出进行特殊处理，看看输入是不是在[2^30,3^13-1]之间，左闭右闭
                 res = res | t << 30;
                min -= t << 30;
                max -= t << 30;

        }
        for(int i=29;i>=0;i--){ //判断范围是否在[2^i,2^(i+1))，左闭右开
            if(min >= t<<i && min<t<<(i+1) && max >= t<<i && max<t<<(i+1)){
               res = res |  t<<i;
               min -=  t<<i;
               max -=  t<<i;
           }
        }
        return res;
    }


    public int rangeBitwiseAnd1(int m, int n) {
        if(m == 0){
            return 0;
        }
        int moveFactor = 1;
        while(m != n){
            m >>= 1;
            n >>= 1;
            moveFactor <<= 1;
        }
        return m * moveFactor;
    }


    public static void main(String[] args) {

        System.out.println(new BitwiseANDOfNumbersRange().rangeBitwiseAnd(2147483645,2147483647));
        System.out.println(new BitwiseANDOfNumbersRange().rangeBitwiseAnd(2147483645,2147483646));
        System.out.println(new BitwiseANDOfNumbersRange().rangeBitwiseAnd1(2147483645,2147483646));
        System.out.println(new BitwiseANDOfNumbersRange().rangeBitwiseAnd(1,16));
    }
}
