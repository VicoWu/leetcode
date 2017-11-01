/**

 99. Recover Binary Search Tree
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
public class RecoverBinarySearchTree {

  public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }


    TreeNode firstElement = null; //第一个发现的错误点
    TreeNode secondElement = null; //第二个发现的错误点
    // The reason for this initialization is to avoid null pointer exception in the first comparison when prevElement has not been initialized
    TreeNode prevElement = new TreeNode(Integer.MIN_VALUE); //保存中序遍历的前一个节点值，用来进行错误判断，如果当前值小于前一个值，说明当前值是一个错误值

    public void recoverTree(TreeNode root) {

        // In order traversal to find the two elements
        traverse(root);

        // Swap the values of the two nodes
        int temp = firstElement.val;
        firstElement.val = secondElement.val;
        secondElement.val = temp;
    }

    private void traverse(TreeNode root) {

        if (root == null)
            return;

        traverse(root.left);

        // Start of "do some business",
        // If first element has not been found, assign it to prevElement (refer to 6 in the example above)
        if (firstElement == null && prevElement.val >= root.val) {  //判断是否找到了第一个错误值，即，当前值大于前一个值，那么当前值就是一个错误点
            firstElement = prevElement;
        }

        // If first element is found, assign the second element to the root (refer to 2 in the example above)
        if (firstElement != null && prevElement.val >= root.val) { //判断是否找到了第二个错误值，即，当前值大于前一个值，并且第一个错误点已经找到，那么当前值就是第二个错误点
            secondElement = root;
        }
        prevElement = root;

        // End of "do some business"

        traverse(root.right);
    }


    public static void main(String[] args) {
        TreeNode node5 =new TreeNode(20);
        TreeNode node10 =new TreeNode(10);
        TreeNode node12 =new TreeNode(12);
        TreeNode node15=new TreeNode(15);
        TreeNode node20 =new TreeNode(5);
        node10.left = node5;
        node10.right = node15;
        node15.left = node12;
        node15.right = node20;


        new RecoverBinarySearchTree().recoverTree(node10);
    }
}
