package leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuchang at 1/3/18
 * Question 77
 * 对于n，我们先从1开始，看看有没有以1开头的合理组合，所有以1开头的组合都找完以后，再找以2开头的，再找以3开头的组合。
 假如说我们现在要找k个数字组成的组合，范围是[1...n],加入现在1定下来，我们现在找以1开头的组合，我们需要在[2...n]范围内查找由k-1个数字的组合，
 对于[2...n]中的每一个数字t，我们需要再在[t+1,n]的范围内查找由k-2个数字组成的组合，依次类推。
 在不断递归的过程中，我们都会通过当前current的大小来判断current.size()是否已经等于k了，如果是，则保存这个结果，返回。
 */

public class Combines {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result  = new LinkedList<>();
        this.combine(n,k,1,result,new LinkedList<Integer>());
        return result;
    }


    /**
     *
     * @param n
     * @param k
     * @param lo
     * @param result
     * @param current
     */
    public void  combine(int n,int k,int lo,List<List<Integer>> result,List<Integer> current){

        if(current.size()==k) //数量已经等于k了，因此可以保存结果了
        {
            result.add(new LinkedList<>(current));
            return;
        }
        for(int i=lo;i<=n;i++){ //对以[lo..n]范围捏的每一个数字开头都进行一次尝试和遍历，比如，先尝试1开头的，再尝试2开头，再尝试3开头
            current.add(i);
            combine(n,k,i+1,result,current);//在剩余的[lo+1,n]进行相同方法的遍历
            current.remove(current.size()-1);

        }
    }

    /**
     *
     * 我的这种方法的思想是，从n一直考虑到n-1，n-2,...., 1, 那么result(n, k ) = result(n-1, k-1) + result(n-1, k)
     * result(n-1, k-1) 代表假如排列中有n这个数字，那么有result(n-1,k-1)种组合，如果排列中没有n这个数字，那么有result(n-1, k)种可能性
      */

    public void combine_solution_2(int n, int k, List<List<Integer>> result, List<Integer> current){
        if(k == 1) { //任何(n, 1)的排列组合都是n种
            for (int i = n; i >= 1; i--) {
                current.add(i);
                result.add(new LinkedList<>(current));
                current.remove(current.size() - 1);
            }
            return;
        }
        // 任何(x,x) 的排列组合都是有x种
        if(n == k) // for example, n = 2, k =2 , then 只有一种排列方式
        {
            LinkedList res = new LinkedList<>(current);
            for(int i = n; i >= 1; i--){
                res.add(i);
            }
            result.add(res);
            return;
        }
        current.add(n);
        combine_solution_2(n-1, k-1, result, current);
        current.remove(current.size() - 1);
        combine_solution_2(n-1, k, result, current);
    }

    public static void main(String[] args) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> curr = new LinkedList<>();
        //new Combines().combine(4,2);
        new Combines().combine_solution_2(4 , 2, res, curr);
        System.out.println(res);
    }
}
