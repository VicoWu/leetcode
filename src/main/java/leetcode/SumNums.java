package leetcode;

/**
 * 剑指 Offer 64. 求1+2+…+n
 */
public class SumNums {
    public int sumNums(int n) {
        boolean flag =  (n <= 1) || ( (n = n + sumNums(n - 1)) > 0);
        return n;
    }

    public int sumNums2(int n) {
        int res = 0;
        int x = n;
        int y = n + 1;
        for(;y>0;){
            if( (y & 1) == 1){
                res = x + res;
            }
            y = y >> 1;
            x = x << 1;
        }
        return res >> 1;
    }

    public static void main(String[] args) {
        System.out.println(new SumNums().sumNums2(2));
        System.out.println(new SumNums().sumNums2(3));
        System.out.println(new SumNums().sumNums2(9));
    }
}
