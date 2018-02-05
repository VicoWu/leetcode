package leetcode;

/**
 * 二叉树的Morris遍历
 *
 * 99. Recover Binary Search Tree
 * ## 通过中序遍历
 https://discuss.leetcode.com/topic/3988/no-fancy-algorithm-just-simple-and-powerful-in-order-traversal/2
 注意，递归方式的中序遍历的空间复杂度最坏情况下是O(n)，这个空间复杂度是由递归栈引起的：
 中序遍历的基本模式是：
 ```
 private void traverse (TreeNode root) {
 if (root == null)
 return;
 traverse(root.left);
 // Do some business
 traverse(root.right);
 }
 ```
 // Do some business会判断是否遇到了一个错误位置点
 比如，一个正确的二叉排序树的 中序遍历结果是数组：
 int[] a = {1,3,5,7,9}
 假如第一个和第五个数字发生调换，变成 {9,3,5,7,1} ，此时我们遍历，会发现两个位置i和j，有：`a[i]<a[i-1],a[j]<a[j-1]`，发生错误的节点就应该是`a[i-1](注意，不是a[i])`和`a[j]`。我们通过中序遍历也会发现这两个TreeNode，只需要把他们进行调换就行了。
 **注意，如果发现的是第一个错误节点，发生错误的是前向指针，如果是第二个错误节点，发生错误的应该是当前指针；**
 **注意，当我们发现了第一个错误节点，这个错误节点有可能也是第二个节点的位置，比如，我们把13579变换成31579，这时候只有一个错误位置i=1，将i=0的位置是firstWrong，i=1的位置是i=1，因此不可以用 else if**



 ## 通过空间复杂度为O(1)的Morris Traversal算法
 [Morris Traversal(http://www.cnblogs.com/AnnieKim/archive/2013/06/15/morristraversal.html)
 我们通过Morris Traversal算法实现空间复杂度为O(1)的二叉排序树的中序遍历，有了中序遍历结果，我们就可以发现这两个错误点了。类MorrisTraversal模拟了Morris Traversal的中序遍历过程。

 Morris Traversal的基本思想，就是得到一个root节点，找到中序遍历的前置节点，让这个前置节点的后孩子指向root节点。这样，我们遍历完前置节点，直接就通过右孩子到达了后置节点；这个操作在普通的递归方式和非递归方式中都是通过栈完成的；

 通过Morris Traversal算法，每次准备print一个节点的时候就开始比较了。





 http://www.cnblogs.com/AnnieKim/archive/2013/06/15/morristraversal.html
 */
public class MorrisTraversal {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }


    /**
     * 中序遍历

     步骤：

     1. 如果当前节点的左孩子为空，则输出当前节点并将其右孩子作为当前节点。

     2. 如果当前节点的左孩子不为空，在当前节点的左子树中找到当前节点在中序遍历下的前驱节点。

     a) 如果前驱节点的右孩子为空，将它的右孩子设置为当前节点。当前节点更新为当前节点的左孩子。

     b) 如果前驱节点的右孩子为当前节点，将它的右孩子重新设为空（恢复树的形状）。输出当前节点。当前节点更新为当前节点的右孩子。

     3. 重复以上1、2直到当前节点为空。
     */
    void inorderMorrisTraversal(TreeNode root) {
        TreeNode firstWrong = null;TreeNode secondWrong = null;TreeNode pre= new TreeNode(Integer.MIN_VALUE);
        TreeNode cur = root, prev = null;

        while (cur != null)
        {
            if (cur.left == null) //已经没有左子树了，说明已经遍历到了最左下，这是中序遍历的第一个节点         // 1.
            {
                System.out.println(cur.val);//读取该节点
                if (cur.val < pre.val) {
                    if (firstWrong == null)
                        firstWrong = pre;
                    if(firstWrong != null)
                        secondWrong = cur;
                }
                pre = cur;
                cur = cur.right; //通过刚刚新建的指针指向了自己的后继节点
            }
            else
            {
                // find predecessor
                prev = cur.left;
                while (prev.right != null && prev.right != cur)
                    prev = prev.right; //找到root的前驱节点，即root的左子树的最靠右的节点

                //找到了一个pre节点，它的右子树是空的，或者，它的右子树指向了cur节点
                if (prev.right == null)   // 2.a)
                {
                    prev.right = cur;//如果这个右子树是空的，那么可以把这个右子树指针指向中序遍历的后继节点
                    cur = cur.left;
                }
                else                       // 2.b) //它的右子树指向了cur节点
                {
                    prev.right = null; //之前修改的空指针，现在修改回来
                    System.out.println(cur.val);
                    if (cur.val < pre.val) {
                        if (firstWrong == null)
                            firstWrong = pre;
                        if(firstWrong != null)
                            secondWrong = cur;
                    }
                    pre = cur;
                    cur = cur.right;//已经完成了左子树和跟节点cur的遍历，开始进行右子树进行相同的遍历
                }
            }
        }
        if(firstWrong!=null && secondWrong!=null){
            int temp = firstWrong.val;
            firstWrong.val = secondWrong.val;
            secondWrong.val=temp;
        }


    }


    public static void main(String[] args) {
        TreeNode node6 =new TreeNode(6);
        TreeNode node2 =new TreeNode(2);
        TreeNode node1 =new TreeNode(4);
        TreeNode node4=new TreeNode(1);
        TreeNode node3 =new TreeNode(3);
        TreeNode node5 =new TreeNode(5);
         TreeNode node7 =new TreeNode(7);
        TreeNode node9=new TreeNode(9);
        TreeNode node8 =new TreeNode(8);
        node6.left=node2;
        node2.left=node1;
        node2.right = node4;
        node4.left=node3;
        node4.right=node5;
        node6.right=node7;
        node7.right=node9;
        node9.left=node8;


        TreeNode node02 =new TreeNode(2);
        TreeNode node03 =new TreeNode(3);
        TreeNode node01 =new TreeNode(1);

        node02.left = node03;
        node02.right= node01;

        new MorrisTraversal().inorderMorrisTraversal(node02);
        new MorrisTraversal().inorderMorrisTraversal(node02);

    }
}
