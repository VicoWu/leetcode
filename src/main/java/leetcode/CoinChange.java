package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Question 322
 # 动态规划算法
 这个动态规划非常直观，不再讲解；我的解法跟[这题](https://discuss.leetcode.com/topic/32489/java-both-iterative-and-recursive-solutions-with-explanations)的解法一致。
 需要注意的地方：
 1. 我最开始以为，由于题目中需要求解的是最小的货币数量，因此，在进行动态规划的过程中，先把货币按照币值从大到小排序，每一轮递归，都是按从大到小的顺序尝试货币，只要成功组合，就是最小数量的货币值，但是对于以下输入，提交以后测试失败：
 ```
 int[] coins = {186,419,83,408};
 我的输出：26
 期望输出：20
 ```
 我可以举一个更简单的例子：
 ```
 int[] coins = {15,10,1}; amount=20
 ```
 按照我的算法，先尝试15，剩余5，5只能使用5个1来组合，因此结果是6，即需要6每货币
 但是实际上只需要2枚10元面值的货币，因此，递归的每一层，我们都需要尝试所有货币面值，取最小值。

 2. 缓存中间值加快计算
 这道题的动态规划涉及到很多的重复值，因此使用一个map缓存，来保存成功的以及失败的兑换情况。主要，失败的也要保存，因为我们在递归过程中会遇到很多失败计算，如果使用缓存，将大大加速计算。

 3. 计算前先把面值排序
 这样，当remain已经小于当前面值了，则后面更大的面值就不需要再计算了









 */

public class CoinChange {
    public int coinChange(int[] coins, int amount) {

        Arrays.sort(coins);

        Map<Integer,Integer> cache = new HashMap<Integer,Integer>();
        int result = dp(coins, amount,cache);
        System.out.println(result);
        return result;
    }

    public int dp(int[] coins, int remaining,Map<Integer,Integer> cache){

        if(remaining == -1) return -1;
        if(remaining == 0)
            return 0;
        if(cache.containsKey(remaining))
            return cache.get(remaining);
        int minResult = Integer.MAX_VALUE;
        for(int coin:coins){ //尝试所有货币面值，取最小值。

            if(remaining >= coin){
                int result = dp(coins,remaining-coin,cache);
                if(result>=0 && result+1 < minResult){
                    minResult = result+1;
                }
            }
            else
                break;//由于我们对面值进行了排序，当remaining < coin，后面更大面值的就不需要计算了，这属于一个小小的优化
        }
        cache.put(remaining,minResult==Integer.MAX_VALUE?-1:minResult);
        return cache.get(remaining);
    }




    public static void main(String[] args) {

        //int[] coins = {186,419,83,408};
        int[] coins = {186,419,83,408};
        int[] coins1 = {1,2,5};
        System.out.println(new CoinChange().coinChange(coins,6249));
    }
}
