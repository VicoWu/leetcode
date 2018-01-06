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
 132
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



    public static void main(String[] args) {

       for(int i=1;i<=24;i++)
         System.out.println(new PermutationSequence().getPermutation(1,1));
    }
}
