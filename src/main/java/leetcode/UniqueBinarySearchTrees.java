package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Question 96
 * [Unique Binary Search Trees](https://leetcode.com/problems/unique-binary-search-trees/description/)
 [Unique Binary Search Tree II](https://leetcode.com/problems/unique-binary-search-trees-ii/description/)
 [Different Ways to Add Parentheses](https://leetcode.com/problems/different-ways-to-add-parentheses/description/)这三道题的思想是一致的。

 使用动态规划的方式非常明显。具有最优字结构，即对于n，选出一个root节点，有n中选择方式，则n的BST数量是这n中选择形成的BST的数量之和，
 即具备DP的最优字结构的特点。而bottom case是n=0和n=1;

 是否可以尝试使用自底向上的方式，即，给出一个n，从i=0开始往上计算，一直计算到n呢？
 */

public class UniqueBinarySearchTrees {

    private static Map<Integer,Integer> intermediateResult = new HashMap<Integer,Integer>();
    public int numTrees(int n) {

        return computeBinarySearchTree(n);
    }

    private int computeBinarySearchTree(int n){
        if(n<=1) return 1;
        if(intermediateResult.containsKey(n)) return intermediateResult.get(n);
        int result = 0;
        for(int i=0;i<=n-1;i++){
            int left = i;int right = n-1-i;
            result += (computeBinarySearchTree(left) * computeBinarySearchTree(right) );
        }
        intermediateResult.put(n,result);
        return result;
    }


    public static void main(String[] args){
        System.out.println(new UniqueBinarySearchTrees().computeBinarySearchTree(3));
    }
}
