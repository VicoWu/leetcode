package SwordToOffer;

/**
 * @author chang.wu
 * @date 11/6/20
 *
 * https://github.com/zhedahht/CodingInterviewChinese2/blob/master/14_CuttingRope/CuttingRope.cpp
 * 剑指offer 第 14题
 */
public class CutLine
{
    // 我的思路是，第一刀可以切在位置1，可以在2，可以在3， 因此n的最优解就是
    // currentMax = Math.max(Math.max(currentMax, j * results[ i - j]), j * (i  - j));
    public int getMax(int n){
        if(n < 2)
            return -1;
        if(n ==2 )
            return 1;
        if(n == 3)
            return 2;
        //results[i]保存了长度为i作为整条绳子的最优解，但是作为整条绳子的最优解，并不一定就是作为其他绳子的一段的情况下的最优解，
        //因为作为整条绳子时，按照题目要求，这条绳子必须裁剪，但是作为其它绳子的一部分时，可以不裁剪。比如，f(4)的最优解是2*2，而不是1*3
        int[] results = new int[n+1];
        results[1] = 1;
        results[2] = 2;
        results[3] = 2;
        for(int i = 4; i <= n;i++){ // try to get the results[i]
            int currentMax = Integer.MIN_VALUE;
            for(int j = 1;j <= i-1; j++){
                //在下面的三个结果中取最大
                //为什么需要考虑j * (i - j)? 因为右端可以整个保留
                currentMax = Math.max(Math.max(currentMax, j * results[ i - j]), j * (i  - j));
            }
            results[i] = currentMax;
        }
        return results[n];

    }

    public int maxProductAfterCutting_solution1(int length)
    {
        if(length < 2)
            return 0;
        if(length == 2)
            return 1;
        if(length == 3)
            return 2;

        int[] products = new int[length + 1];
        products[0] = 0;
        products[1] = 1;
        products[2] = 2;
        products[3] = 3;

        int max = 0;
        for(int i = 4; i <= length; ++i)
        {
            max = 0;
            for(int j = 1; j <= i / 2; ++j)
            {
                int product = products[j] * products[i - j];
                if(max < product)
                    max = product;

                products[i] = max;
            }
        }

        max = products[length];
        return max;
    }

    public static void main(String[] args)
    {
        for (int i = 1; i < 20; i++){
            System.out.println(new CutLine().getMax(i)) ;
            System.out.println(new CutLine().maxProductAfterCutting_solution1(i)) ;
        }
    }
}
