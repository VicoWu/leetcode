package leetcode;

import java.util.Arrays;

/**
 * 剑指 Offer 60. n个骰子的点数
 */
class DiceProbability {
    public double[] dicesProbability(int n) {
        double[] res = new double[5 * n + 1]; // 对于n，一共有5*n + 1个结果，比如，n=1， 会有6个结果，n=2， 会有11个结果（点数最小为n，最大为6 * n , 因此为6n - 5n + 1个结果）
        int left = 0 ; int right = 0;
        int[] pre = new int[1]; // 没有任何骰子情况下的初始化，初始状态下，我们认为点数为0出现了1次
        pre[0]=1;
        for(int i = 1; i<=n ; i++){ // 开始掷第n哥骰子
            int[] count = new int[6 * i + 1]; // 掷第n个骰子的时候，最大点数是6*n, 因此创建一个长度为6*i+1的数组，count[x] 代表点数为x出现的次数
            for(int cur = left; cur <= right ; cur++){ // 上一次的结果
                for(int point = 1; point <= 6; point++){ // 当前第n个骰子可能掷出1 到 6，当投出点数为point，那么更新当前的点数结果
                    count[cur + point] = count[cur + point] + pre[cur]; // 根据已经投掷的i-1 个骰子的结果，更新当前的点数结果
                }
            }
            // 更新点数的最小值和最大值范围
            left = i;
            right = 6 * i;
            pre = count;
            if(i == n){ //可以计算最终结果了
                int sum = 0;
                for(int cur = n;cur<=6*n;cur++){
                    sum = sum + count[cur]; // 先计算总数之和
                }
                for(int cur = n;cur<=6*n;cur++){
                    res[cur - n] = (double)count[cur] / sum; // 再计算最终概率的结果
                }
                break;
            }
        }
        return res;
    }

    /**
     *
     作者：Krahets
     链接：https://leetcode.cn/problems/nge-tou-zi-de-dian-shu-lcof/solutions/637778/jian-zhi-offer-60-n-ge-tou-zi-de-dian-sh-z36d/
     来源：力扣（LeetCode）
     著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

     * @param n
     * @return
     */
    public double[] dicesProbability2(int n) {
        double[] dp = new double[6];
        Arrays.fill(dp, 1.0 / 6.0); // 初始化为只有一个骰子的概率结果
        for (int i = 2; i <= n; i++) {
            double[] tmp = new double[5 * i + 1];
            for (int j = 0; j < dp.length; j++) { // 对于上一轮的概率结果dp
                for (int k = 0; k < 6; k++) { // 对于当前的骰子每一种1 ～ 6 的点数可能
                    tmp[j + k] += dp[j] / 6.0; // k = 0, 代表当前骰子点数是1，
                }
            }
            dp = tmp; // 保存当前i=n的概率结果，为下一轮n+1的计算作准备
        }
        return dp;
    }


    public static void main(String[] args) {
        new DiceProbability().dicesProbability(2);
    }
}