package leetcode;

/**
 * Question 134
 * 我们从节点x 能够 到达 y，但是无法到达y+1， 说明，对于[x,y]之间的任意节点z，都无法到达y+1, 因此此时都无需再考虑[x,y]（左闭右闭）之间的任何节点了
 * 怎么证明：由于x能够到达y, 因此x肯定能到达z,说明从x开到z的时候，到达z之前，肯定是有一些油的(也可能没有油，但是不会缺油)。
 * 在有一些油的情况下，都无法从z出发到达y+1,那么，直接把z作为起点,在没有油的情况下，肯定更加到不了y+1
 */
public class GasStation {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int i = 0; // i可以从0 到 n - 1
        while(i<=gas.length-1){
            int start = i;
            int sum = 0; //从i出发
            while(sum + gas[start % gas.length] - cost[start % gas.length]>= 0){ // 前一站剩余油量，加上当前站加油，以及路上耗油量,可以到达下一站
                sum += (gas[start % gas.length] - cost[start % gas.length]); // 下一站的油量更新
                start++;  // 到达下一站
                if((start % gas.length) == i){ // 当前站就是之前的起点站，返回成功
                    return i;
                }
            }

            // 当前从i可以到达start，但是无法到达start + 1, 这说明从[i, start]之间的节点都不能环绕一周。那么，我们从start+1开始尝试
            // 当前start的值代表可以到达的加油站，但是无法到达start + 1;
            i = (start + 1);
        }
        return -1;
    }
}
