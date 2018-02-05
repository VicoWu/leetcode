package leetcode;

import java.util.Arrays;

/**
 * Created by wuchang at 12/22/17
 * Question 493
 *
 * 这一题与[Count of Smaller Numbers After Self](https://leetcode.com/problems/count-of-smaller-numbers-after-self/description/)是几乎相同的问题，只是比较的标准不一样。

 关于一下三种方法[这里](https://discuss.leetcode.com/topic/79227/general-principles-behind-problems-similar-to-reverse-pairs) 的解释非常详细。
 # 归并法
 我自己写的归并方法。其基本思想是将nums进行拆分，然后对两个有序的数组，分别计算他们内部的reverse pair ， 以及两个有序数组之间的reverse pair。计算两个有序的数组(长度分别为m和n)之间形成的reverse pair的时间复杂度是m+n，不再是m\*n，比如：
 左数组是A=[1，2，3，4，5]，右数组是B=[1，2]  ，对于B[0]=1，找到大于等于 2\*B[0]的元素是A[1-4]，对于B[1]，大于等于2\*B[1]的元素是A[4-5]，因此A和B之间的逆序对有6对

 # BST搜索方法
 我们为nums中的数字构建一个二叉搜索树，但是，树的节点中除了通过记载左右指针（left、right）和节点的数值val外，还需要通过一个变量cnt计算右子树中节点的数量，即，大于等于自己的节点的数量；
 因此。每次，我们往树种插入一个数字的时候，除了将其插入到指定的位置，而且，在遍历寻找指定位置的过程中，需要更新cnt的值，如下：
 ```
 private Node insert(Node root, int val) {
 if (root == null) {
 root = new Node(val);
 } else if (val == root.val) {
 root.cnt++;
 } else if (val < root.val) {
 root.left = insert(root.left, val);
 } else {
 root.cnt++;
 root.right = insert(root.right, val);
 }

 return root;
 }

 private int search(Node root, long val) {
 if (root == null) {
 return 0;
 } else if (val == root.val) {
 return root.cnt;
 } else if (val < root.val) {
 return root.cnt + search(root.left, val);
 } else {
 return search(root.right, val);
 }
 }
 ```
 reverse pairs的过程就变成：
 遍历nums，对于当前的这个值ele，在树种查找出 `>=2L*ele+1`的元素的个数，然后将这个元素插入到树种，继续遍历nums的下一个元素。

 # BIT搜索方法
 [这里](http://m.blog.csdn.net/a1323933782/article/details/53698473)有BIT搜索树的详细讲解。
 我在BIT.java中对BIT进行了简单地实现。虽然是BIT树，但是这个树是被表示在一个数组c中的。BIT树的基本使用方法是：
 有一个数组a，初始化状态下数组中所有元素是0，
 我们可以往数组a的任何位置进行数据的增加(add)，并且不断地获取某个位置i之前的元素的和s[i]=a[0]+a[1]+a[2]+...+a[i]

 如何使用BIT树解答Reverse Pairs问题：
 对于输入nums，我们首先需要得到一份排序以后的nums的拷贝，这样，对于任何一个数字，我们都可以通过二分查找方法，知道这个数字在nums中的位置。
 比如:nums = {7,9,2}，排序以后， copy={2,7,9}，这样，对于1,2,3,4,5,6,7,8,9,10这些数字，我们可以立刻通过二分查找在log(n)的时间内知道他们在数组中的大小位置分别是
 ```
 1 2 3 4 5 6 7 8 9
 1 1 2 2 2 2 2 3 3
 ```
 ，因此，对于nums中的任何一个数字i，我们就可以知道2i+1和i本身在数组中的位置，所以，基于BIT的搜索算法就变成了：
 ```
 遍历nums，对于每一个nums[i]，获取2*nums[i]+1的位置，通过BIT树我们可以轻易知道在2*nums[i]+1这个位置之前的元素之和(由于插入的时候插入1，因此元素之和就是个数之和)，这就是元素i的逆序数，即，排在i之前，并且大于2*nums[i]+1的元素个数
 然后，我们把nums[i]的位置插入到BIT树种
 ```
 可见，BIT树中保存的是元素的位置，而不是元素本身。遍历nums的过程中，遍历到元素A[i]，此时BIT中已经存放了位置在i之前的所有元素在nums中的排序以后的位置。因此，我们可以通过目前的BIT树知道`2*A[i]+1`
 在nums排序后的位置前面有多少个元素,从而计算这个数字A[i]对应的逆序数量，即，i之前但是大于`2*A[i]+1`的元素数量。举例：
 ```
 nums={4,1,2}
 排序后，copy={1,2,4}
 对于元素4：由于2*4+1=9，目前的BIT树是空的，显然没有逆序存在。然后把4对应的位置（4最大，排在第3，然后将元素4的排名3插入到BIT中）
 对于元素1：由于2*1+1=3，3在copy中排第2，BIT中现在已经有了一个3，通过BIT搜索就可以得到<=2的元素为0，因此，>2的元素个数是1-0=1，然后将元素1的排名1插入到BIT中
 对于元素2，由于2*2+1 = 5, 5在copy中排第4，BIT中现在已经有了3和1，都小于4，显然，这说明元素2的前面没有大于2*2+1=5 的元素，因此为0
 遍历完毕，总共的逆序数量是1，是由元素4和元素1带来的逆序数量
 ```
 */

public class ReversePairs {
    public int reversePairs(int[] nums) {

       return this.mergeSort(nums,0,nums.length-1);
    }

    private int mergeSort(int[] nums,int i,int j){
        if(j<=i)
            return 0;
        int middle = (i+j)/2;
        int result = mergeSort(nums,i,middle) + mergeSort(nums,middle+1,j);
        for(int left=i,right=middle+1;left<=middle && right <= j;right++){
            while(left <= middle && nums[left] <= (((long)nums[right]) << 1)) //右移操作之前先把数字转换成长整型，防止整形溢出，否则" {2147483647,2147483647,2147483647,2147483647,2147483647,2147483647};"这个输入过不了
                left++;
            result+=(middle-left+1);
        }
        Arrays.sort(nums,i,j+1);
        return result;
    }

    class Node {
        int val, cnt;
        Node left, right;

        Node(int val) {
            this.val = val;
            this.cnt = 1;
        }
    }

    private int searchByBST(Node root, long val) {
        if (root == null) {
            return 0;
        } else if (val == root.val) {
            return root.cnt;
        } else if (val < root.val) {
            return root.cnt + searchByBST(root.left, val);
        } else {
            return searchByBST(root.right, val);
        }
    }

    private Node insertByBST(Node root, int val) {
        if (root == null) {
            root = new Node(val);
        } else if (val == root.val) {
            root.cnt++;
        } else if (val < root.val) {
            root.left = insertByBST(root.left, val);
        } else {
            root.cnt++;
            root.right = insertByBST(root.right, val);
        }

        return root;
    }

    public int reversePairsByBST(int[] nums) {
        int res = 0;
        Node root = null;

        for (int ele : nums) {
            res += searchByBST(root, 2L * ele + 1); //搜索在这个ele前面的且大于2*ele+1的数字
            root = insertByBST(root, ele);
        }

        return res;
    }


    public int reversePairsByBIT(int[] nums) {
        int res = 0;
        int[] copy = Arrays.copyOf(nums, nums.length);
        int[] bit = new int[copy.length + 1];

        Arrays.sort(copy); //copy存放了对nums进行排序后的数组

        for (int ele : nums) {
            // index(copy, 2L * ele + 1)返回了整个nums中大于2L*ele的元素个数
            res += searchByBIT(bit, index(copy, 2L * ele + 1));
            insertByBIT(bit, index(copy, ele));//index(copy,ele) 返回了整个nums中小于ele的元素个数
        }

        return res;
    }

    /*
    对于已经从小到达排序的数组arr，返回val前面的元素的个数，即小于等于val的元素个数
     */
    private int index(int[] arr, long val) {
        int l = 0, r = arr.length - 1, m = 0;

        while (l <= r) {
            m = l + ((r - l) >> 1);

            if (arr[m] >= val) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        return l + 1;
    }

    private int searchByBIT(int[] bit, int i) {
        int sum = 0;

        while (i < bit.length) {
            sum += bit[i];
            i += i & -i; //沿着bit树不断往右上方查找并递增
        }

        return sum;
    }

    private void insertByBIT(int[] bit, int i) {
        while (i > 0) {
            bit[i] += 1;
            i -= i & -i;
        }
    }


    public static void main(String[] args) {
        int[] input = {3,1,2};
        int[] sorted = {2,7,9};
        for(int i=0;i<10;i++)
            System.out.println(new ReversePairs().index(sorted,i));
        System.out.println(new ReversePairs().reversePairsByBIT(input));
    }
}
