/**
 * Created by wuchang at 1/8/18
 * Question 114
 # 我的思路
 可以看到，其实这个list就是这棵树先序遍历的结果，但是，如何实现 in-place的转换呢？
 对于一个root节点，先获取左子树的list，再获取右子树的list，然后，将root、左子树的list、右子树的list拼接成一个完整的list。拼接的时候，root节点的right节点指向左子树的head节点，左子树的 tail节点的right指针指向右子树的head节点，右子树的head节点的right指针置为空。
 因此，` private TreeNode[] flatten(TreeNode root,boolean dummy)`方法返回一个数字，存放以root作为根节点的数形成的list的头结点和尾节点。
 */

public class FlattenBinaryTreeToLinkedList {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
    }

    public void flatten(TreeNode root) {
        flatten(root,true);
        System.out.println();
    }

    private TreeNode[] flatten(TreeNode root,boolean dummy){

        if(root == null)
            return null;
        //以root为根节点的尾节点，如果root是一个叶子节点，那么尾节点就是root，如果只有左子树或者只有右子树，那么tail就是左子树/右子树变形的list的最后一个节点，
        // 如果既有左子树也有右子树，那么根据先序遍历的规则，tail就是右子树形成的list的最后一个节点
        TreeNode tail = root;
        TreeNode rb = root.right; //先把root节点的右节点备份一下，因为如果有左子树，那么root.right将会指向左子树形成的list的头结点
        TreeNode lt = root; //如果root有右子树，那么，左子树形成的list的尾部节点就应该指向右子树形成的list节点的头部，因此lt保存了list里面右子树的头结点的前一个节点，这个节点或者为root节点(没有左子树)，或者为左子树形成的list的尾部节点
        if(root.left !=null) //有左子树
        {
           TreeNode[] leftList =  flatten(root.left,true);//左子树形成的list
           root.right = leftList[0]; //root节点的next指针指向左子树形成的list的头部
           tail =  lt = leftList[1];//先不管有没有右子树，此时整个root树的形成的list节点暂时为左子树形成的list的尾节点

        }
        if(rb != null){//有右子树
            TreeNode[] rightList =  flatten(rb,true); //右子树形成的list
            lt.right= rightList[0]; //lt的右指针指向这个右子树形成的list的头结点，这个lt可能是root节点本身(如果root没有左子树)，或者是左子树形成的list的尾节点
            tail = rightList[1];  //整个树形成的list的尾节点就是右子树的尾节点
        }
        tail.right = null;//整棵树的尾节点为null
        root.left = null;//left指针已经没有用了
        TreeNode[] res = {root,tail};
        return res;
    }

    public static void main(String[] args) {
        TreeNode t1= new TreeNode(1);
        TreeNode t2= new TreeNode(2);
        TreeNode t5= new TreeNode(5);
        TreeNode t3= new TreeNode(3);
        TreeNode t4= new TreeNode(4);
        TreeNode t6= new TreeNode(6);

        t1.right = t2;
        t2.left = t3;
//        t1.left = t2;
//        t1.right = t5;
//        t2.left = t3;
//        t2.right = t4;
//        t5.right = t6;

       new FlattenBinaryTreeToLinkedList().flatten(t1);
    }
}
