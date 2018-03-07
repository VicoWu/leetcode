package leetcode;

/**
 * Created by wuchang at 3/7/18
 * Question 223
 我直接复制的[这里](https://leetcode.com/problems/rectangle-area/discuss/62149/Just-another-short-way)的解法。
 这里要计算的是两个矩形总共覆盖的区域，即求并集，而不是交集。
 如果我们直接计算，各种边界情况将会变得非常复杂，就像[这里](https://leetcode.com/problems/rectangle-area/discuss/62142/If-you-want-to-laugh-look-at-my-solution)的解答一样。
 因此，我们可以采用间接的方法，即二者的并集，等于二者的面积和，减去相交的部分的面积，问题转换成如何求相交部分的矩形的面积。
 相交部分的矩形，是由左侧、右侧、上侧和下侧组成。相交部分的左侧边就是两个矩形的左侧边更靠右的那一个，即` int left = Math.max(A,E)`，相交部分的右侧边就是两个矩形的右侧边中更靠左边的那个，即`int right = Math.min(C,G)`，但是，如果两个矩形没有相交，即一个矩形在另外一个矩形的左侧或者右侧，那么在计算相交部分的面积的时候就会得到一个负数： `(right-left)*(top-bottom) <  0` ，因此，我们对这个值进行修正：`Math.max(Math.min(C,G), left)`,即，当我们发现`Math.min(C,G) < left`的时候，直接修正为left，这样计算相交部分的面积的时候直接就等于0，而不会为负数。
 同理，我们计算相交部分的矩形的上侧边和下侧边也是这样计算。
 最终，矩形的面积就是：` (C-A)*(D-B) - (right-left)*(top-bottom) + (G-E)*(H-F)`
 */

public class RectangleArea {

    public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        int left = Math.max(A,E), right = Math.max(Math.min(C,G), left);
        int bottom = Math.max(B,F), top = Math.max(Math.min(D,H), bottom);
        return (C-A)*(D-B) - (right-left)*(top-bottom) + (G-E)*(H-F);
    }
}
