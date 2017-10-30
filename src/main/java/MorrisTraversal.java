/**
 * 二叉树的Morris遍历
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
        TreeNode cur = root, prev = null;
        while (cur != null)
        {
            if (cur.left == null) //已经没有左子树了，说明已经遍历到了最左下，这是中序遍历的第一个节点         // 1.
            {
                System.out.println(cur.val);//读取该节点
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
                    cur = cur.right;//已经完成了左子树和跟节点cur的遍历，开始进行右子树进行相同的遍历
                }
            }
        }
    }


    public static void main(String[] args) {
        TreeNode node6 =new TreeNode(6);
        TreeNode node2 =new TreeNode(2);
        TreeNode node1 =new TreeNode(1);
        TreeNode node4=new TreeNode(4);
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

        new MorrisTraversal().inorderMorrisTraversal(node6);
    }
}
