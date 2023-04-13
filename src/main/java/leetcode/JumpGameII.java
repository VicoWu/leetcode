package leetcode;

/**
 * Created by wuchang at 12/21/17
 * Question 45
 [《这篇文章》](https://www.sigmainfy.com/blog/leetcode-jump-game-ii.html)详细地讲解了分别通过BFS、贪心、动态规划解答这一题的方法，但是我感觉它描述的DP有问题。
 # 贪心算法
 其实数组中每个位置相当于一个图的节点，然后如果位置i能够一步到达位置j(j>i)，则i和j相连
 因此，这个数组就转化成了一个有向无环图。求最小的step就是求一个单源点最短路径。由于单源点最短路径满足最有子结构性质，因此，可以使用贪心算法。
 如果从某个位置到某个位置可以一步到达，则连接起来
 这一题除了贪心算法以外，其它算法都会超时的。
 以A=[2,4,1,1,1,1]为例：
 我们遍历A，通过maxNextAvailableDist记录每一个位置可以到达的最远距离。
 同时，我们通过maxReachableDistance记录当前已经到达的最远距离
 我们用step记录走到当前的i已经经过的步数。
 每一步往前走的时候，都尝试更新maxNextAvailableDist，并且，当我们发现maxReachableDistance小于maxNextAvailableDist时，更新maxReachableDistance为maxNextAvailableDist
 因此，经过一轮i从0到length-1的扫描，我们就可以得到最终的步数：
 ```
 序号  0  1  2  3  4  5
 输入  2  4  1  1  1  1
 step 0  1  1  2  2  2   # 到达当前节点需要的最小步数
 mr   0  2  2  5  5  5   # 当前以最小步数可以到达的最远距离
 mn   2  5  5  5  5  5   # 以前（包括）的节点作为出发点可以到达的最远距离

 mr: maxReachableDistance ,以当前的最小步骤，最远可以到达的位置，超过了这个位置，步骤数就必须加1，比如，step=1，maxReachableDistance=1或者2，表示通过1步，可以到达i=1或者i=2，但是无法到达i=3
 mn: maxNextAvailableDist 不在乎步数最小，当前可以到达的最远的位置，

 ```

 显然，step的值就是从i=0到达后面的每一个位置的最小步数值，随着i逐渐增加，最终我们得到从i=0到i=5的最小步数值是2；
 我们看一下step从1变成2发生的时机，即，什么时候，必须增加一步才能到达下一个节点，从上文的例子中，就是为什么到达i=2只需要1步，而到达i=3就必须要加一步了？
 这是因为当i为1或者或者2的时候，由于A[0]=2，因此，maxReachableDistance都是2，到了i=3，越过了这个位置，因此必须增加一步。那为什么我们确定只增加一步就一定能到i=3呢？因为我们以最小步数step到了了i=2，
 就从i=2跳跃一次，到达i=3，因此step加1
 ```
 if (i > maxReachableDistance) {
     if (maxNextAvailableDist > maxReachableDistance) {
         maxReachableDistance = maxNextAvailableDist;
         ++minSteps;
 } else
     return -1;
 }
 ```

 注意，这里的greedy算法，不是简单的每次走得更远，比如`[2,4,1,1,1,1]`
 如果每次走到最远，比如，从i=0直接1步调到i=2，然后经过3步调到i=5，一共5步
 但是其实最近的 步骤是从i=01步跳转到i=1,然后直接经过1步调到i=5，总共2步
 这里的贪心是：如果到达某个位置i可以经过最少的n步到达，那么到达某个位置j(j>i)，如果需要从i经过，则到达i就用这个最短路径，然后再使用从i到j的最短路径；

 # 动态规划
 我们用minDis(k)代表从位置到达终点的最短距离。某个节点A[1]=3，则可以通过1步直接到达位置2，3，4 ，如果它的终点是i=10，
 它到终点i=10的最短距离minDis(1) = Math.min(minDis(i=2)+1，minDis(i=3)+1,minDis(i=4)+1)，然后递归计算minDis(2)、minDis(3)、minDis(4)。
 同样的，我使用一个cache来记录中间结果避免重复计算。cache[i]代表已经计算好的到达终点的距离值。
 但是我的动态规划算法会出现栈溢出，比如，很长的输入数据[1,1,1,1,1,1.....]

 # BFS
 其实这个问题可以规约为一个有向图的最短路径问题，最短路径使用BFS进行查找，第一次遇到的时候，经过的step肯定就是最短的step。代码没有实现。


# 关于Dijkstras 算法，看这篇文章： https://www.freecodecamp.org/chinese/news/dijkstras-shortest-path-algorithm-visual-introduction/
Dijkstras的代码看 https://houbb.github.io/2020/01/23/data-struct-learn-03-graph-dijkstra

 */

public class JumpGameII {


    /**
     * 通过动态规划的方式计算最短步数。我这里是通过递归的方式，从前往后递归、然后从后往前回溯进行。是否可以直接从后往前一次计算？
     * @param A
     * @return
     */
    public int jumpByDP(int[] A){
        Long[] cache = new Long[A.length]; //缓存，避免重复计算，cache[index]记录了从index到终点的最短的step

        cache[A.length-1] = 0L; //终点自己到自己到最小step是0
        long result =  dp(A,0,cache);
        return (int)result;
    }

    public long dp(int[] A,int index,Long[] cache){

        if(cache[index]!=null)
            return cache[index];

        if(index +  A[index] >= A.length -1 ) //如果从index可以一步跳跃到终点，那么显然cache[index]=1，只需要一步到终点
        {
            cache[index] = 1L;
            return 1;
        }

        long minStep = Integer.MAX_VALUE;
        for(int i=index+1;i<=(index+A[index]);i++){
            long step = dp(A,i,cache)+1; //注意，这里有可能溢出，比如，一个节点到终点不可达， 那么minstep就是MAX_VALUE, 加1，就溢出了
            if(step < minStep)
                minStep = step;
        }
        cache[index] = minStep;

        return minStep;
    }


    /**
     * 通过从后往前的方式，采用动态规划，来获得nums[0]到终点的距离
     * @param nums
     * @return
     */
    public int jumpByDP2(int[] nums) {
        Long[] cache = new Long[nums.length];
        cache[nums.length-1]=0L; //终点到终点的虽少steps肯定是0
        // 从倒数第二个节点开始，依次往前计算所有节点到终点的最短路径，直到计算到nums[0]
        for(int i = nums.length-2; i>=0; i--){
            long step = (long)Integer.MAX_VALUE;
            for(int j = i+1;j<=i+nums[i] && j < nums.length;j++){ // 节点i可以通过1步到达[i+1, i+nums[i]]
                long minStepThroughJ = 1 + cache[j]; //经过节点j到达终点的最小步数是1+cache[j]
                step = Math.min(step, minStepThroughJ); // 对于nums[i]的全局step最小值
            }
            cache[i] = step; //保存中间结果，继续往前面的节点算
        }
        return (int)(long)cache[0];
    }


    public int jumpByGreedy(int[] A){
        if (A.length <= 1) return 0;
        int maxReachableDistance = 0;
        int maxNextAvailableDist = 0;
        int minSteps = 0; // 不断维护最小步数

        for (int i = 0; i < A.length; ++i) {
            if (i > maxReachableDistance) { //当前的序号已经超过了经过minStep可以到达的最远位置，这时候minStep就必须加1了
                if (maxNextAvailableDist > maxReachableDistance) {
                    maxReachableDistance = maxNextAvailableDist;
                    ++minSteps;//到达这个节点的步数比到达上一个节点的步数加1
                } else
                    // 发现maxNextAvailableDist到不了这里，说明我们根本到不了终点
                    return -1;
            }
            //  更新最远可到达的距离
            maxNextAvailableDist = Math.max(maxNextAvailableDist, A[i] + i);

            // 已经可以cover到终点了
            // 由于当前已经知道到达A[i]所需要的最小步骤是minSteps, 我们突然发现maxNextAvailableDist已经可以cover到终点了，因此，只需要再多一步就肯定能到终点（但是需要注意，这个最短路径并不一定
            // 经过A[i]）
            if (maxNextAvailableDist >= A.length - 1)
                return minSteps + 1;
        }
        return minSteps +1;
    }

    public static void main(String[] args) {
        int[] input = {2,3,0,1,4};
        System.out.println(new JumpGameII().jumpByDP(input));
    }
}
