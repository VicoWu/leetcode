package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuchang at 1/4/18
 * Question 95
 [Unique Binary Search Trees](https://leetcode.com/problems/unique-binary-search-trees/description/)
 [Unique Binary Search Tree II](https://leetcode.com/problems/unique-binary-search-trees-ii/description/)
 [Different Ways to Add Parentheses](https://leetcode.com/problems/different-ways-to-add-parentheses/description/)这三道题的思想是一致的。
这三道题的思想是一致的。


都是通过左子树和右子树的计算结果来拼凑当前树的计算结果，但是这一题要困难很多，因为需要不断对树进行拷贝。
 # 失败的方法
 由于需要返回所有的BST树，因此，考虑肯定需要一个树的拷贝方法，当我们确定了一个BST的时候，调用这个拷贝方法把这个树拷贝出来保存到结果中。但是这时候应该如何判断一棵树已经确定了呢？我考虑使用一个remaing，代表当前还有多少个节点没有处理，当remaining==0，代表刚刚处理了最后一个节点，树已经确定了，此时把这个树拷贝出来保存到结果的list中。但是这种方法过于麻烦，因此放弃。


 # 成功的方法
 定义一个方法`public List<TreeNode> generateTrees(int lo ,int ri)`,入一个范围[lo,ri]，返回这个[lo,ri]范围内的所有子树的root节点的list。具体方法是：
 我们将[lo,ri]按照某个位置i进行切割，即以i为根节点，切割成左子树[lo,i-1]和右子树[i+1,ri]。我们计算左子树形成的所有BST left 和右子树形成的所有BST right，那么，[lo,ri]形成的所有BST是什么呢？

 ```
 List<TreeNode> left =  generateTrees(lo,i-1);
 List<TreeNode> right = generateTrees(i+1,ri);

 for(TreeNode tl:left) {
 for (TreeNode tr : right) {
 TreeNode root = new TreeNode(i); //创建根节点
 root.left = tl;
 root.right = tr;
 result.add(root);
 }
 }
 ```

 即将左子树[lo,i-1]和右子树[i+1,ri]进行进行排列组合，就得到了[lo,ri]的结果，比如，左子树[lo,i-1]形成的BST一共有3个，右子树一共有4个，那么，[lo,ri]得到的BST就一共有12个。
 最终，我们得到[1,n]形成的BST。




 * # 失败的方法
 由于需要返回所有的BST树，因此，考虑肯定需要一个树的拷贝方法，当我们确定了一个BST的时候，调用这个拷贝方法把这个树拷贝出来保存到结果中。但是这时候应该如何判断一棵树已经确定了呢？我考虑使用一个remaing，代表当前还有多少个节点没有处理，当remaining==0，代表刚刚处理了最后一个节点，树已经确定了，此时把这个树拷贝出来保存到结果的list中。但是这种方法过于麻烦，因此放弃。


 # 成功的方法
 定义一个方法`public List<TreeNode> generateTrees(int lo ,int ri)`,入一个范围[lo,ri]，返回这个[lo,ri]范围内的所有子树的root节点的list。具体方法是：
 我们将[lo,ri]按照某个位置i进行切割，即以i为根节点，切割成左子树[lo,i-1]和右子树[i+1,ri]。我们计算左子树形成的所有BST left 和右子树形成的所有BST right，那么，[lo,ri]形成的所有BST是什么呢？

 ```
 List<TreeNode> left =  generateTrees(lo,i-1);
 List<TreeNode> right = generateTrees(i+1,ri);

 for(TreeNode tl:left) {
 for (TreeNode tr : right) {
 TreeNode root = new TreeNode(i); //创建根节点
 root.left = tl;
 root.right = tr;
 result.add(root);
 }
 }
 ```

 即将左子树[lo,i-1]和右子树[i+1,ri]进行进行排列组合，就得到了[lo,ri]的结果，比如，左子树[lo,i-1]形成的BST一共有3个，右子树一共有4个，那么，[lo,ri]得到的BST就一共有12个。
 最终，我们得到[1,n]形成的BST。


 */

public class UniqueBinarySearchTreesII {


      public static class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
          TreeNode(int x) { val = x; }
      }



    public List<TreeNode> generateTrees(int n) {
          if(n==0)
              return new LinkedList<>();
        return generateTrees(1,n);
    }

    public TreeNode treeCopy(TreeNode root){
          if(root == null)
              return null;
          TreeNode result = new TreeNode(root.val);
          result.left = treeCopy(root.left);
          result.right = treeCopy(root.right);
          return result;

    }


    public List<TreeNode> generateTrees(int lo ,int ri){

        List<TreeNode> result = new LinkedList<>();
        if(lo > ri)
        {
            result.add(null);
            return result;
        }
        if(lo == ri)
        {
            result.add(new TreeNode(lo));
            return result;
        }

        for(int i=lo;i<=ri;i++){ //对当前的[lo,ri]范围进行切割，切割为[lo,i-1]和[i+1,ri]，根节点是i

           List<TreeNode> left =  generateTrees(lo,i-1); //存放了左子树形成的所有树的root节点的list
           List<TreeNode> right = generateTrees(i+1,ri);//存放了右子树形成的所有树的root节点的list

            //加入左子树形成了m个树，右子树形成了n个树，那么此时就可以形成m*n个不同的树
           for(TreeNode tl:left) {
               for (TreeNode tr : right) {
                   TreeNode root = new TreeNode(i); //创建根节点
                   root.left = tl;
                   root.right = tr;
                   result.add(root);
               }
           }
        }
        return result;
    }

    public List<TreeNode> genTrees2 (int start, int end)
    {

        List<TreeNode> list = new ArrayList<TreeNode>();

        if(start>end)
        {
            list.add(null);
            return list;
        }

        if(start == end){
            list.add(new TreeNode(start));
            return list;
        }

        List<TreeNode> left,right;
        for(int i=start;i<=end;i++)
        {

            left = genTrees2(start, i-1);
            right = genTrees2(i+1,end);

            for(TreeNode lnode: left)
            {
                for(TreeNode rnode: right)
                {
                    TreeNode root = new TreeNode(i);
                    root.left = lnode;
                    root.right = rnode;
                    list.add(root);
                }
            }

        }

        return list;
    }


    public static void main(String[] args) {
//        TreeNode tn1 = new TreeNode(1);
//        TreeNode tn2 = new TreeNode(2);
//        TreeNode tn3 = new TreeNode(3);
//        tn1.right = tn2;
//        tn2.right = tn3;
//        new UniqueBinarySearchTreesII().treeCopy(tn1);
        new UniqueBinarySearchTreesII().generateTrees(3);
    }
}
