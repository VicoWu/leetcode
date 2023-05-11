package leetcode;

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

    public static void main(String[] args) {
        new DiceProbability().dicesProbability(2);
    }
}