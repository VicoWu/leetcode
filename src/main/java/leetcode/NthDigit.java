package leetcode;

/**
 * @author chang.wu
 * @date 12/8/20
 * Question 400
 * 剑指offer第44题
 */
public class NthDigit
{

    public int findNthDigit(int n) {
        int k = 0;
        long currentSum = 0; // 这里的currentSum必须定义成long,否则容易溢出



        while(n > currentSum){
            k++;
            currentSum += totalNumForDigitCount(k);
            System.out.println("n = "  + n + ", current sum is " + currentSum);
        }
        //跳出循环，此时n 已经大于currentSum了，回退一步
        currentSum -= totalNumForDigitCount(k);
        long targetNum = (n - currentSum - 1) / k  + (long)Math.pow(10, (k-1));
        return findKthDigit(k, targetNum,  (int)(n - currentSum - 1) % k);
    }

    /**
     *
     * @param length 数字的位数，
     * @param number 数字本身
     * @param index  数字中第index位的数，比如，对于数字19278, length = 5, index=0的时候返回1，index=1的时候返回9
     * @return
     */
    public int findKthDigit(int length,long number, int index){
        for(int i=0 ; i < length - (index + 1); i++){
            number /= 10;
        }
        return (int)number % 10;
    }

    /**
     * k表示位数，根据位数，返回这个位数下面的数字的长度，比如，1位数，返回9， 二位数返回2 * 90 = 180，代表从10 ~ 99共有180个digit， 以此类推
     * @param k
     * @return
     */
    public long totalNumForDigitCount(int k){
        if(k == 0)
            return 1;
       long ret =  (long)(Math.pow(10,k) - Math.pow(10, k-1)) * k;
       System.out.println("value for " + k + " is " + ret);
       return ret;
    }

    public static void main(String[] args)
    {
        System.out.println(new NthDigit().findNthDigit(1000000000));
        for(int i = 1; i< 200; i++){
            System.out.println(new NthDigit().findNthDigit(i));
        }
        System.out.println(new NthDigit().findNthDigit(190));
        System.out.println(new NthDigit().findNthDigit(191));
        System.out.println(new NthDigit().findNthDigit(192));
    }
}

