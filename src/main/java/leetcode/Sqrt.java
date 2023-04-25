package leetcode;

import static java.lang.Math.pow;

/**
 * Created by wuchang at 1/18/18
 * Question 69
 这里只是求的近似值，比如sqrt(10)=3
 如果这里要求的精确值，这里有讲解 http://blog.csdn.net/ycf74514/article/details/48996383
 Refer Question 35: 搜索插入位置，也是相似的原理，都是通过在一个有序数组中进行二分查找：Question 35是寻找一个目标位置，它是数组从左到右第一个大于等于 target的下标， 而本文是寻找一个
 目标值，它是从小到大，满足 k2 ≤x 的最大 k 值
 */

public class Sqrt {

    public int mySqrt(int x) {

        long left = 0;long right = x;long mid = (left+right)/2;
        while(left < right){
            mid = (left+right)/2;
            System.out.println(left+" "+mid+" "+right);
            long val1 = mid * mid;
            long val2 = val1+ (mid << 1) +1; //val2 == (val+1)^1;
            if(val2 <= x){
                left = mid+1;
            }
            else if(val1>x){
                right = mid-1;
            }
            else
                return (int)mid;
        }
        return (int)left;
    }

    /**
     * x 平方根的整数部分 ans\textit{ans}ans 是满足 k2≤xk^2 \leq xk
     * 2
     *  ≤x 的最大 kkk 值，因此我们可以对 kkk 进行二分查找，从而得到答案。
     *
     *
     *
     * 作者：力扣官方题解
     * 链接：https://leetcode.cn/problems/sqrtx/solutions/238553/x-de-ping-fang-gen-by-leetcode-solution/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param x
     * @return
     */
    public int mySqrt2(int x) {
        int left = 0;
        int right = x;
        int mid = (left + right ) /2 ;
        int result = mid;
        while(left <= right){
            //System.out.println(left + " " + right);
            long d = (long)mid * mid;
            if(d <= x){ //不断寻找满足mid * mid <= x 的最大值
                result = mid;
                left = mid + 1;
            }else if(d > x){
                right = mid - 1;
            }
            mid = (int)(left + right) / 2;
        }
        return result;
    }

     public static void main(String[] args) {
        //System.out.println(pow(2, 31) + ", " + Integer.MAX_VALUE);
         int mid = 2147395599 / 2;
         //System.out.println((long)mid * mid );
         System.out.println(new Sqrt().mySqrt2(2147395599));
    }

}
