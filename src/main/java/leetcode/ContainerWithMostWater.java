package leetcode;

/**
 * Question 11
 注意，这里的O(n)算法，整个移动过程中，并不清楚哪一次是最大值，但是，当l>=r因而推出的时候，却可以肯定：必定已经经过了最大值；因此，证明这个算法正确性的方法，就是证明：如果存在一个最大值的情况，那么，这种扫描方法，不可能没有经过这个最大值的情况。看证明：https://discuss.leetcode.com/topic/503/anyone-who-has-a-o-n-algorithm/3

 我的解法的解释在[这里](https://leetcode.com/problems/container-with-most-water/discuss/6091/Easy-Concise-Java-O(N)-Solution-with-Proof-and-Explanation)，是通过反证法来证明的。

 [这里](https://leetcode.com/problems/container-with-most-water/discuss/6099/Yet-another-way-to-see-what-happens-in-the-O(n)-algorithm)的解释是直接证明的。
 假如现在我们求`[a1,a2...,a6]`，开始的时候 ，我们有`a1<a6`，那么，可以看到，`[a1,a2]`、`[a1,a3]`，`[a1,a4]`，`[a1,a5]`形成的面积肯定都小于`[a1,a6]`，因此，
 左侧指针往右移动一个，我们看`[a2,a6]`，假如`a2>a6`，那么，可以知道,`[a3,a6]`,`[a4,a6]`,`[a5,a6]`都是小于`[a2,a6]`的，因此右侧指针向左移动一个，我们开始看`[a2,a5]`，以此类推。
 通过双指针来缩小搜索范围的类似题目：
 https://leetcode.cn/problems/search-a-2d-matrix-ii/
 https://leetcode.cn/problems/two-sum-ii-input-array-is-sorted/description/
 https://leetcode.cn/problems/search-a-2d-matrix-ii/description/
 */
public class ContainerWithMostWater {
    public int maxArea(int[] height) {
        int i=0;
        int j=height.length-1;
        int l = i;
        int r = j;
        int maxArea = 0;
        while(l<r){
            maxArea = Math.max(maxArea,(r-l) * Math.min(height[l] , height[r]));
            if(height[l] < height[r])
               l=l+1;
            else
               r=r-1;
        }
        return maxArea;
    }

    public int maxArea2(int[] height) {
        int currentMax = 0;
        for(int i = 0, j = height.length -1;j > i; ){
            int h = Math.min(height[i], height[j]);
            currentMax = Math.max(currentMax, (j - i) * h);
            if(height[i] < height[j]){
                // 如果左侧比较低，因此我们把左侧向右移动到一个更高的高度
                while(i < height.length && height[i] <= h){
                    i++;
                }
            }else{
                // 如果右侧比较低，因此我们把右侧向左移动到一个更高的高度
                while(j >= 0 && height[j] <= h){
                    j--;
                }
            }
        }
        return currentMax;
    }
}
