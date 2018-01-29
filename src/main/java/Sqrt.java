/**
 * Created by wuchang at 1/18/18
 * Question 69
 */

public class Sqrt {

    public int mySqrt(int x) {

        long left = 0;long right = x;long mid = (left+right)/2;
        while(left < right){
            mid = (left+right)/2;
            System.out.println(left+" "+mid+" "+right);
            long val1 = mid * mid; long val2 = val1+ (mid << 1) +1;
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

     public static void main(String[] args) {

        new Sqrt().mySqrt(2147395599);
    }

}
