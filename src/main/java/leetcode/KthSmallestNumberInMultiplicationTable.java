package leetcode;

/**
 * Question 668
 *
 * 这道题与[Find K-th Smallest Pair Distance](https://leetcode.com/problems/find-k-th-smallest-pair-distance/description/)是同类型的题目，他们的共同点是：

 1. 全局无序，但是却是由有限个有序的数列组成。比如[Find K-th Smallest Pair Distance](https://leetcode.com/problems/find-k-th-smallest-pair-distance/description/)中，
 虽然nums排序以后点之间的距离无法做到全局有序，但是，以某一个点开头的点对，却是有序的；对于本题，虽然这些数字不是全局有序的，但是每一行却都是有序的；
 2. 都是要查找第k小的元素
 3. 确定一个值，我们可以在很短的时间内知道有多少个数据小于等于这个值，这有助于我们通过二分法逐渐逼近k-th 元素

 因此，和[Find K-th Smallest Pair Distance](https://leetcode.com/problems/find-k-th-smallest-pair-distance/description/)一样，
 我们使用二分法逐渐逼近第k-th个元素；关键问题是，对于这一题，知道了一个数字M,如何计算有多少个数字小于等于M:
 这是我的解法：
 ```
 private int lessThan(int m,int n,int mid){
 int num = 0;
 for(int i=1,j=n;i<=m;i++){
 while(i * j > mid && j>0) j--;
 num += (j);
 }
 return num;
 }
 ```

 这里是计算小于mid的数据的数量。虽然我们遍历每一行，但是却不用重复计算，这是因为：加入第i行小于等于mid的最后一个数字的坐标是(i,j)，那么，
 由于下一行的每一个数字都大于上一行正头顶的数字，因此，下一行查找的时候，只需要从j往左查找就行；

 同时，还有别人的实现方法：
 ```
 private int lessThan2(int m, int n,int v) {
 int count = 0;
 for (int i = 1; i <= m; i++) {
 int temp = Math.min(v / i , n);
 count += temp;
 }
 return count;
 }
 ```
 对于每一行，小于等于v的数字的个数是`Math.min(v/i,n)`,很简单，不做解释。








 */
public class KthSmallestNumberInMultiplicationTable {
    public int findKthNumber(int m, int n, int k) {

        int min=1;int max = m*n;

        while(min < max){
            int mid = (min+max)/2;
            int count = lessThan(m,n,mid);
            if(count >= k)
                max = mid;
            else
                min = mid+1;
        }
        return min;
    }

    private int lessThan(int m,int n,int mid){

        int num = 0;
        for(int i=1,j=n;i<=m;i++){

            while(i * j > mid && j>0) j--;
            num += (j);
        }
        return num;
    }

    private int lessThan2(int m, int n,int v) {
        int count = 0;
        for (int i = 1; i <= m; i++) {
            int temp = Math.min(v / i , n);
            count += temp;
        }
        return count;
    }


    public static void main(String[] args) {

        new KthSmallestNumberInMultiplicationTable().findKthNumber(3,3,6);
    }
}
