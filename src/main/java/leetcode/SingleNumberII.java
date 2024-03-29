package leetcode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by wuchang at 1/10/18
 * Question 137
 这里对这一题的解法有详细的讲解http://www.cnblogs.com/grandyang/p/4263927.html
 *
 * # 方法1
 我们可以建立一个32位的数字，来统计每一位上1出现的个数，我们知道如果某一位上为1的话，
 那么如果该整数出现了三次，对3去余为0，我们把每个数的对应位都加起来对3取余，最终剩下来的那个数就是单独的数字。代码如下
 ```
 public int singleNumber2(int[] A){
 int res = 0;
 for(int i=0;i<32;i++){
 int cbr=0;
 for(int num:A){
 cbr+= (num >> i) & 1;
 }
 res = res | (cbr % 3 )<<i;
 }
 return res;
 }
 ```


 # 方法2

 由于位运算的结果与数字出现的顺序没有关系，因此我们完全可以就认为任何相同的三个数字是连续地排列在一起的。

 如果能够找到一种位操作方法，让出现3次的数字被销毁，就像没有出现一样就行了。因此，我们设定ab为一个2位的二进制，由于数字出现了3次，因此刚好由这个二进制数 00 -> 01 ->  10 -> 00 刚好表示一个数字出现1次(01)、出现2次(10)和出现3次(00)。

 ```
 public int singleNumber(int[] A) {
 int b = 0, a = 0;
 for(int i = 0; i < A.length; i++){
 b = (b ^ A[i]) & ~a;//低位
 a = (a ^ A[i]) & ~b;//高位
 }
 return b;
 }
 出现次数    出现1次   出现2次   出现3次
 b          1        0        0
 a          0        1        0
 ab         01       10       00
 ```

 先看低位的b，数字出现1次和出现2次，分别为1和0，因此b=b^num[i]就行，但是第三次的时候b还原为0，因此b=(b ^ A[i]) & ~a ，
 b 初始化为0， 遇到第一个重复数字的1， 由于0^1 = 1, 然后 1 ^ 1 = 0,因此b(1) = 1 , b(2) = 0 的情况完全可以用b ^ A[i]来表达，但是这种case无法表达b(3) = 0， 这时候需要借用a 的值来修正
 注意看代码，b的计算在a的前面，因此这里的a是前面一步的a，比如，计算出现第三次时候的b的公式中的a，是出现第二次2次时候的a的值，为1，刚好，b^num[i] & ~a = 0；
 a 初始化为0， 遇到第2个重复数字的1， 由于0^1 = 1, 然后 1 ^ 1 = 0,因此a(2) = 1 , a(3) = 0 的情况完全可以用a ^ A[i]来表达，但是这种case无法表达a(1) = 0， 这时候需要借用b 的值来修正
 再看高位的a。注意，a的计算是在b 的后面，所以a中用到的b是本轮已经计算好的b，而不是上一轮的b。第一轮，a=(a ^ A[i]) & ~b，由于b=1，因此，a=0，第二轮，由于第一轮的时候a=0，所以a ^ A[i]是A[i]本身，然后& ~b，就是A[i]，第三次出现的时候，由于第二轮的a==A[i]，所以第三轮a^A[i]==0，此时a=0；

 我们这里的a和b是两个整数，但是我们上面讨论的a和b应该看成对应位的操作，因为对于每一个数字的计算都是将所有的位进行了一次性处理。





 */

public class SingleNumberII {

    /**
     * 我们可以建立一个32位的数字，来统计每一位上1出现的个数，我们知道如果某一位上为1的话，
     * 那么如果该整数出现了三次，对3去余为0，我们把每个数的对应位都加起来对3取余，最终剩下来的那个数就是单独的数字。代码如下
     * @param A
     * @return
     */
    public int singleNumber2(int[] A){
        int res = 0;
        for(int i=0;i<32;i++){
            int cbr=0;
            for(int num:A){
                cbr+= (num >> i) & 1;
            }
            res = res | (cbr % 3 )<<i;
        }
        return res;
    }


    /**
     *
     * @param A
     * @return
     */
    public int singleNumber(int[] A) {
        int b = 0, a = 0;
        for(int i = 0; i < A.length; i++){
            b = (b ^ A[i]) & ~a;//低位
            a = (a ^ A[i]) & ~b;//高位
        }
        return b;
    }

    public int singleNumber1(int[] nums) {
        int one = 0, two = 0, three = 0;
        for (int i = 0; i < nums.length; ++i) {
            two |= one & nums[i];
            one ^= nums[i];
            three = one & two;
            System.out.println();
            one &= ~three;
            two &= ~three;
        }
        return one;
    }

    public int singleNumber3(int[] nums) {
        int maxBit1 = 0;
        for(int i = 0; i<nums.length; i++){
            maxBit1 = maxBit1 | nums[i];
        }

        int cur = 1;
        int result = 0;
        while(maxBit1 >= 0){
            int sum = 0;
            for(int i = 0;i<nums.length;i++){
                if((nums[i] & cur) == cur) sum++;
            }
            int bit = sum % 3;
            if(bit == 1){
                result = result | cur;
            }
            cur = cur << 1;
            maxBit1 = maxBit1 >> 1;
        }
        Deque dq = new LinkedList<>();
        return result;

    }

    public int singleNumber4(int[] nums) {
        int cur = 1;
        int result = 0;
        for(int j = 0;j<32; j++){
            int sum = 0;
            for(int i = 0;i<nums.length;i++){
                if((nums[i] & cur) == cur) sum++;
            }
            int bit = sum % 3;
            if(bit == 1){
                result = result | cur;
            }
            cur = cur << 1;

        }
        return result;
    }


    public static void main(String[] args) {
//        int a = -220;
//        for(int i = 0 ; i<10; i++){
//            System.out.println(a >> i);
//        }
//        System.out.println((-1) >> 1);
//        System.out.println((-12) >> 1);
//        System.out.println((-12) >> 3);
//        System.out.println(-1 | 1);
        int[] A = {2,2,1,1,4,1,4,4,-7,2};
        System.out.println(new SingleNumberII().singleNumber3(A));
    }
}
