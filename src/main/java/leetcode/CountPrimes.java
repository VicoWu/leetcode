package leetcode;

/**
 * Created by wuchang at 2/4/18
 * 看这里的[解释](https://www.geeksforgeeks.org/sieve-of-eratosthenes/)
 */

public class CountPrimes {

         public int countPrimes(int n) {
            boolean[] notPrime = new boolean[n];
            int count = 0;
            for (int i = 2; i < n; i++) {
                if (notPrime[i] == false) {
                    count++;
                    System.out.println(i);
                    for (int j = 2; i*j < n; j++) {
                        notPrime[i*j] = true;
                    }
                }
            }

            return count;
        }

    public int countPrimes1(int n) {
        boolean[] notPrime = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (notPrime[i] == false) {
                count++;
                System.out.println(i);
                for (int j = i; i*j < n; ) {
                    notPrime[i*j] = true;
                    j++;
                }
            }
        }

        return count;
    }


    public static void main(String[] args) {
       System.out.println(new CountPrimes().countPrimes(40));
        System.out.println(new CountPrimes().countPrimes1(40));
    }
}
