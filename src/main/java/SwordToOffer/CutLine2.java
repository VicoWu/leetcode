package SwordToOffer;

/**
 *
 * 剑指 Offer 14- II. 剪绳子 II
 */
public class CutLine2 {
    public int cuttingRope(int n) {
        if(n <= 3){
            return n - 1;
        }
        if(n == 4){
            return n;
        }
        int a = n / 3; int b = n % 3;
        if(b == 1){
            a--;
            b = 4;
        }
        if(b == 0){
            b = 1;
        }
        return (int)(pow(a, 1000000007) * b % 1000000007);
    }


    private long pow(int a, int mod){
        int power = 1;
        long baseNum = 3;
        long result = 1;
        while(power <= a){
            if((a & power) == power){
                result = result * baseNum % mod;
            }
            baseNum = baseNum * baseNum % mod;
            System.out.println(baseNum);
            power = power << 1;
        }
        return  result ;
    }


    public long pow(int n) {
        if (n == 0) {
            return 1;
        }
        long power = 1;
        long baseNum = 3;
        while (n != 0) {
            if (n % 2 != 0) {
                power = power * baseNum % 1000000007;
            }
            baseNum = baseNum * baseNum % 1000000007;
            System.out.println(baseNum);
            n /= 2;
        }
        return power;
    }

    public static void main(String[] args) {
        System.out.println(new CutLine2().pow(41));
        System.out.println(new CutLine2().pow(41, 1000000007));

        System.out.println(new CutLine2().cuttingRope(127)) ;
    }

}
