import java.util.Stack;

/**
 * Question 84
 *解法归纳：
 ## 暴力方式
 i和j分别设置为第一个柱子，然后：
 ```
 for(int i=0;i<heights.length;i++){
 for(int j=0;j<heights.length;j++){
 computeArea()...
 }
 }
 ```
 暴力方式的 computeArea()最主要的是计算区域[i,j]的最小的height，由于i和j是顺序递增，因此，这一步的minHeight可以从上一步计算而来，而不是每次都需要遍历[i,j]来计算。因此，总体时间复杂度是O(n^2)；


 ## 通过巧妙方式记录最小高度
 (链接)(https://discuss.leetcode.com/topic/39151/5ms-o-n-java-solution-explained-beats-96)
 这个算法的基本逻辑，是遍历每一个柱子，计算以这个柱子的高度为**最小高度**的**最大面积**值。这个算法的关键也是如何为某个柱子快速获取满足条件的左右边界。如果通过蛮力方式获取左右边界，则算法的总体时间复杂度是O(n^2)。这里的算法在最坏情况下的时间复杂度还是(O(n^2))，但是在一般情况下，平均时间复杂度却可以达到O(n)

 ## 通过栈的方式，使时间复杂度降低到O(n)
 这是唯一一个时间复杂度在O(n)的算法
 https://discuss.leetcode.com/topic/7599/o-n-stack-based-java-solution
 可以看我代码的注释。
 如果当前元素的高度大于栈顶元素的高度，那么元素入栈，这样，栈里面的元素stack(i-1)肯定是stack(i)在height中第一个小于stack(i)的元素
 //如果当前元素的高度小于栈顶元素的高度，就对栈进行弹出，开始计算面积。此时i是tp右侧第一个小于tp的元素，因此，计算得到的面积是，以tp为最小元素的面积。

 ## 动态规划，时间复杂度是O(nlogn)
 [动态规划求解](https://discuss.leetcode.com/topic/7491/simple-divide-and-conquer-ac-solution-without-segment-tree)
 通过DC的方式获得的解答是O(nlogn)复杂度的，基本思想，在[i,j]范围内的最优解：中间坐标为k=(i+j)/2，那么，最优解只可能是三选一：
 1. 区域 [i,(i+j)/2-1] ；
 2. 区域 [i,(i+j)/2+1,j] ；
 3. 在[i,j]范围内并且包含(i+j)/2的某个区域；

 第三个直接决定了整个算法的时间复杂度。通过getAreaBetween()的O(n)的时间复杂度，使得整个算法的时间复杂度为O(nlogn)，而如果getAreaBetween()使用的是暴力的方式，则时间复杂度是O(n^2logn)
 *
 */
public class LargestRectangleInHistogram {
    /**
     * 这是我自己的算法，其实现方式是每次找出一个区域[i,j]的最小高度[k]，那么，这个区域的最大面积，就是以下三选一中的最大值: 1.  最小高度*[j-i+1]，2:子区域[i,k]的最小高度*(k-i+1)  3.子区域[k+1,j]的最小高度*(k+1,j)
     * 这个算法的时间复杂度主要用在了查找每个区域的最小高度值上面
     * 这种算法在最坏情况下的栈深度是O(n)，比如，如果数据全部有序，那么深度就是O(n)，此时可能栈溢出。
     * 但是，如果通过DP的方式求解，每次进行二次划分，栈深度是O(logn)
     * @param heights
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        return getMaxAreaRecursive(heights,0,heights.length-1,-1);
    }

    private int getMaxAreaRecursive(int[] heights,int start,int end,int minIndex){

        if(end -start < 0 ) return 0;
        if(end -start ==0 ) return Math.max(heights[start],heights[end]) ;
        if(end-start ==1) return Math.max(Math.min(heights[start],heights[end])*2,Math.max(heights[start],heights[end]));//如果宽度是2，那么这个最大面积，就应该在三项中选择
        int minHeightIndex = minIndex;
        if(minHeightIndex == -1){
            minHeightIndex = start;
            for(int i=start;i<=end;i++){
                if(heights[i] < heights[minHeightIndex])
                    minHeightIndex = i;
            }
        }
        //为了减少查询某个区域的最小高度值的时间，我进行了一定的优化，如果说某个区域[i,j]的最小高度值的位置k就是i或者j，那么进行区域划分以后的最小高度位置就只可能是子区域的边界而不可能在中间
        int minRightIndex = (minHeightIndex == start)?(heights[end]<heights[start+1]?end:start+1):-1;
        int minLeftIndex = (minHeightIndex == end )?(heights[start]<heights[end-1]?start:end-1):-1;
        return Math.max(heights[minHeightIndex]*(end-start+1),Math.max(getMaxAreaRecursive(heights,start,minHeightIndex-1,minLeftIndex),getMaxAreaRecursive(heights,minHeightIndex+1,end,minRightIndex)));
    }


    /**
     * O(n)的时间复杂度，基本原理是一次逐个计算每一个柱子的左侧和右侧值，使得形成的区域的最低的高度是这个柱子的高度，因此根本问题就是为每一个柱子寻找这样一个左边界和有边界。如果通过暴力方式，为每一个
     * 柱子寻找左边界和右边界的时间是O(n)，整体算法的时间复杂度是O(n^2)，但是通过优化和中间结果的重用，为每一个柱子寻找左边界和右边界的时间复杂度降低到O(1)，整个算法的时间复杂度降低到O(n)
     * @param height
     * @return
     */
    public static int largestRectangleArea2(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int[] lessFromLeft = new int[height.length]; // idx of the first bar the left that is lower than current 这个数组记录了对于每一个数字，其左侧第一次出现比它小的柱子的位置
        int[] lessFromRight = new int[height.length]; // idx of the first bar the right that is lower than current 逐个数组记录了对于每一个数字，其右侧第一次出现了比它大的柱子的位置
        lessFromRight[height.length - 1] = height.length;
        lessFromLeft[0] = -1;

        for (int i = 1; i < height.length; i++) {
            int p = i - 1;

            while (p >= 0 && height[p] >= height[i]) {
                p = lessFromLeft[p];
            }
            lessFromLeft[i] = p;
        }

        for (int i = height.length - 2; i >= 0; i--) {
            int p = i + 1;

            while (p < height.length && height[p] >= height[i]) {
                p = lessFromRight[p];
            }
            lessFromRight[i] = p;
        }

        int maxArea = 0;
        for (int i = 0; i < height.length; i++) {
            maxArea = Math.max(maxArea, height[i] * (lessFromRight[i] - lessFromLeft[i] - 1));
        }

        return maxArea;
    }

    public static int divideAndConqure(int[] height){

        return partition(height,0,height.length-1);
    }

    public static int partition(int[] height,int start,int end){
        if(end<start)
            return 0;
        else if(start==end)
            return height[start];
        else if(start+1==end)
            return Math.max(Math.max(height[start],height[end]),Math.min(height[start],height[end]) * 2);
        int middle = (start+end) >> 1;
        return maxInThree(partition(height,start,middle-1),partition(height,middle+1,end),getAreaBetween(height,start,middle,end));

    }

    //这个算法的可以看https://discuss.leetcode.com/topic/7491/simple-divide-and-conquer-ac-solution-without-segment-tree/10
    /*

    below is my provement about the correctness of maxCombineArea(const vector<int> &height, int s, int m, int e) method:
Let us suppose that optimal solution is [op_lower,op_upper] ,so ,from method maxCombineArea()，there must be a step where i meets op_lower or j meets op_uppers. We choose one condition ,that is , j have just meet op_upper and i has not reached op_lower yet. So , the correctness proof of method maxCombineArea() become this: j will stand still until i reached op_lower。I use proof by contradiction:
if , before i reached to op_lower,maxCombineArea() method make j moved forword to j+1, then ,according to the algorithm of maxCombineArea(), the reason of this movement must be height[i-1] <= height[j+1] ; In this condition ,we find that area of range [op_lower,op_upper] is smaller that area [op_lower,j+1],which is contrary to the hypothesis that [op_lower,op_upper] is optimal solution. So , before i reached op_lower,j will stand still at op_upper。
     */
    public static int getAreaBetween(int[] height,int start,int middle,int end){
        int i=middle;int j=middle;
        int maxArea = height[middle];int minHeight=height[middle];
        while(i>=start && j<=end){
            minHeight = minInThree(minHeight,height[i],height[j]);
            maxArea = Math.max(maxArea,(j-i+1) * minHeight);
            if(i==start)
                j++;
            else if(j==end)
                i--;
            else {
                if (height[i-1] > height[j+1]) //我们总是移动较大的一边
                    i--;
                else
                    j++;
            }
        }
        return  maxArea;
    }

    private static int maxInThree(int a, int b ,int c){
       return  Math.max(Math.max(a,b),c);
    }

    private static int minInThree(int a, int b ,int c){
        return  Math.min(Math.min(a,b),c);
    }


    /**
     * 这是基于堆栈的O(n)复杂度的solution
     * @param height
     * @return
     */
    public int largestRectangleAreaByStack(int[] height) {
        int len = height.length;
        Stack<Integer> s = new Stack<Integer>();
        int maxArea = 0;
        for(int i = 0; i <= len; i++){
            int h = (i == len ? 0 : height[i]);
            if(s.isEmpty() || h >= height[s.peek()]){ //如果当前元素的高度大于栈顶元素的高度，那么元素入栈，这样，栈里面的元素stack(i-1)肯定是stack(i)在height中第一个小于stack(i)的元素
                s.push(i);
            }else{ //如果当前元素的高度小于栈顶元素的高度，此时i是tp右侧第一个小于tp的元素
                int tp = s.pop();//出栈，开始计算以tp为最右端的最大的区域面积，即，找到tp左端的
                maxArea = Math.max(maxArea, height[tp] * (s.isEmpty() ? i : i - 1 - s.peek()));//s.peek()是读取但是不弹出栈顶元素，栈顶存放的是tp左侧第一个高度小于tp的元素的索引
                i--;
            }
        }
        return maxArea;
    }



    public static void main(String[] args) {

        int[] heights={2,1,4,5,1,3,3};
        System.out.println( new LargestRectangleInHistogram().largestRectangleAreaByStack(heights));
    }
}
