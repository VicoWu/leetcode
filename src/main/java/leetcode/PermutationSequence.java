package leetcode;

import java.util.*;

/**
 * Created by wuchang at 1/5/18
 * Question 60
 *

 [Permutations](https://leetcode.com/problems/permutations/description/)
 [Permutations II](https://leetcode.com/problems/permutations-ii/description/)
 [Next permutation](https://leetcode.com/problems/next-permutation/description/)
 [Permutation Sequence](https://leetcode.com/problems/permutation-sequence/description/)
 [Subsets](https://leetcode.com/problems/subsets/description/)
 [Subsets II](https://leetcode.com/problems/subsets-ii/description/)
 [Combination Sum](https://leetcode.com/problems/combination-sum/description/)
 [Combination Sum II](https://leetcode.com/problems/combination-sum-ii/description/)
 [Combination Sum III](https://leetcode.com/problems/combination-sum-iii/description/)
 [Combination Sum IV](https://leetcode.com/problems/combination-sum-iv/description/)

 [这里](https://leetcode.com/problems/permutations/discuss/18239)介绍了各种排列问题通过回溯方法进行解答的方案；


 注意，这一题表面也是排列组合问题，但是不能按照[Permutations](https://leetcode.com/problems/permutations/description/)的解法，
 不断交换数字的方式进行，因为不断交换无法保证有序，比如n=3，[Permutations](https://leetcode.com/problems/permutations/description/)的输出是：
 ```
 123
 132
 213
 231
 321 #这里开始乱序
 312
 ```


 以n=4为例
 以1开头，有`3*2*1`中排列:
 ```
 1234
 1243
 1324
 1342
 1423
 1432
 ```
 ，同理，以`2、3、4`开头都有`3*2*1`中排列

 因此，假如 `k>=1且k<=6`，那么第一个数字肯定是1，如果`k>=7且k<=12`，k的第一个数字是2，以此类推，所以，第一个数字是什么，是用(k-1)/6得到的（注意不是k/6）。
 当k=7，我们确定了k的第一个数字是2，那么，剩下`[1,3,4]`，我们要在这个排列中找排`k-6=1`的数字，同理：
 当`k>=1且k<=2`，开头为1
 当`k>=3且k<=4`，开头为3
 当`k>=5且k<=6`   开头为 4
 这样，我们就确定了第2个数字为1，然后，我们依次往后查找，直到拼接完整。

 */

public class PermutationSequence {
     public String getPermutation(int n, int k) {
        Map<Integer,Integer> fm  = new HashMap<Integer,Integer>();

        int r = 1;
        List<Integer> remain = new ArrayList();
        fm.put(0,1);
        for(int i=1;i<=n;i++){
            r *= i;
            fm.put(i,r);
            remain.add(i);
        }
         /**
          * 我们要求第k个数，如果从0排，求的是序号k-1的那个数字
          */
        return getPermutation(n,k-1,n,new StringBuffer(),fm,remain);
    }


    public String getPermutation(int n,int k , int level,StringBuffer current ,Map<Integer,Integer> fm,List<Integer> remain ){

        if(current.toString().length()==n)
            return current.toString();
        for(int i=1;i<=level+1;i++){
            int d =  k / fm.get(level-1); //加入level=4，此时fm.get(level-1)=3*2*1=6，因此，如果k为[0,5]，则d=0，如果k为[6,11]，d=1，以此类推
            int r = k % fm.get(level-1);
            current.append(remain.get(d)); //选出了序号是d的数字，比如，d=0，则从current中取出第一个数字
            remain.remove(d);//这个数字已经选出来了，将它从remain中删掉
            return getPermutation(n,r ,level-1,current,fm,remain);
        }
        return null;
    }


    private void swap(int[] nums,int i,int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public String getPermutation2(int n, int k){
        List<Integer> remain = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        long currentBatch  = 1;
        for(int i = 1;i<=n;i++){
            remain.add(i);
            currentBatch *= i;
        }
        getPermutation2(remain, sb, n, k , currentBatch);
        return sb.toString();
    }

    public void getPermutation2(List<Integer> remain, StringBuffer currentResult, int n, int k, long currentBatch){
         if(remain.size() == 1)
         {
             currentResult.append(remain.get(0));
             return;
         }
         currentBatch = currentBatch / remain.size();
         int index = (int)((long)(k-1)/currentBatch);
         int remainIndex =(int) ((long)(k-1) % currentBatch);
         currentResult.append(remain.remove(index));
         getPermutation2(remain, currentResult, n , remainIndex + 1, currentBatch);
    }


    public String getPermutation3(int n, int k) {
        int step = 1;
        for(int i = 1;i<n;i++){
            step = step * i;
        }
        if(k > step * n){ // k is out of range
            return null;
        }
        char[] res = new char[n];
        boolean[] visit = new boolean[n+1];
        Arrays.fill(visit, false);
        findKthInVisitArray(n, k-1, step, 0, visit, res); // 由于k是从1开始，为了统一，我们还是从0开始，以0为起始点
        return new String(res);
    }

    public void findKthInVisitArray(int n, int remain, int step, int depth, boolean[] visit, char[] res){
        while(depth < n){
            int order = remain / step;
            int num = findKthUnVisit(visit, order);
            visit[num] = true; // 标记为已访问
            res[depth] = (char) ('0' + num); // 第depth位的结果确定了
            remain = remain % step; // 剩余的排序，第一个的序号是0
            if(depth == n - 1){
                return;
            }
            step = step / (n - 1 - depth);
            depth++;
        }
    }

    /**
     找到第k大的还没有访问的元素,第一个未访问的元素序号是0
     */
    public int findKthUnVisit(boolean[] visit, int order){
        for(int i = 1;i<visit.length;i++){
            if(!visit[i]){
                order--;
                if(order < 0){
                    return i;
                }
            }
        }
        return -1;
    }



    public static void main(String[] args) {
         System.out.println(new PermutationSequence().getPermutation3(3, 3));
//       for(int i=1;i<=24;i++)
//         System.out.println(new PermutationSequence().getPermutation(1,1));
    }
}
