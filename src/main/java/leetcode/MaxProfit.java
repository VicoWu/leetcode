package leetcode;

/**
 * Question 123. 买卖股票的最佳时机 III
 *
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iii/solutions/552695/mai-mai-gu-piao-de-zui-jia-shi-ji-iii-by-wrnt/
 *
 */
public class MaxProfit {
    /**
     * 这是我自己的解法
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int res = 0;
        // maxProfitFromLeft[i]存放了从第0天到第i天第区间范围内，我们能够通过最多一笔交易获得第最多利润
        int[] maxProfitFromLeft = maxProfitFromLeft(prices);
        // maxProfitFromLeft[i]存放了从第i天到第最后一天的区间范围内，我们能够通过最多一笔交易获得第最多利润
        int[] maxProfitFromRight = maxProfitFromLeft(prices);

        for(int i = 0;i<prices.length;i++){
            res = Math.max(res, maxProfitFromLeft[i] + maxProfitFromRight[i]);
        }
        return res;
    }

    /**
     *
     * @param prices
     * @return
     */
    public int[] maxProfitFromLeft(int prices[]) {
        int[] maxProfitFromLeft = new int[prices.length];
        int minprice = Integer.MAX_VALUE;
        int maxprofit = 0;
        for (int i = 0; i <= prices.length - 1; i++) {
            if (prices[i] < minprice) {
                minprice = prices[i];
            }
            maxprofit = Math.max(prices[i] - minprice, maxprofit);
            maxProfitFromLeft[i] = maxprofit;
        }
        return maxProfitFromLeft;
    }

    public int[] maxProfitFromRight(int prices[]) {
        int[] maxProfitFromRight = new int[prices.length];
        int maxprice = Integer.MIN_VALUE;
        int maxprofit = 0;
        for (int i = prices.length - 1; i >= 0; i--) {
            if (prices[i] > maxprice) {
                maxprice = prices[i];
            }
            maxprofit = Math.max(maxprice - prices[i], maxprofit);
            maxProfitFromRight[i] = maxprofit;
        }
        return maxProfitFromRight;
    }


    public static void main(String[] args) {
        int[] input = {3,3,5,0,0,3,1,4};
        new MaxProfit().maxProfit(input);
    }
}
