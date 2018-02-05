package leetcode;

/**
 * Created by wuchang at 1/31/18
 * Question 264
 *
 */

public class UglyNumberII {
    //这是我的实现
    public int nthUglyNumber(int n) {

        int[] res = new int[n]; res[0] = 1;
        int p1=0;int p2=0;int p3=0;
        for(int i=1;i<res.length;){
            int min = Math.min(2*res[p1],Math.min(3*res[p2],5*res[p3])); //取出最小值
            if(min == 2*res[p1]) //查看最小值是由哪个产生的，注意，这里
                p1++;
            if(min == 3*res[p2])
                p2++;
            if(min == 5 * res[p3])
                p3++;
            res[i++] = min;
        }
        return res[n-1];
    }


    public  int nthUglyNumber2(int n) {
        if(n <= 0) return -1; // get rid of corner cases
        if(n == 1) return 1; // base case
        int t2 = 0, t3 = 0, t5 = 0; //pointers for 2, 3, 5
        int[] k = new int[n];
        k[0] = 1;
        for(int i  = 1; i < n ; i ++)
        {
            k[i] = Math.min(k[t2]*2,Math.min(k[t3]*3,k[t5]*5));
            if(k[i] == k[t2]*2)
                t2++;
            if(k[i] == k[t3]*3)
                t3++;
            if(k[i] == k[t5]*5)
                t5++;
        }
        return k[n-1];
    }


    public static void main(String[] args) {
        new UglyNumberII().nthUglyNumber(10);
    }
}
