/**
 * Question 450
 */
public class DeleteNodeInBST {


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode deleteNode(TreeNode root, int key) {

        TreeNode cursor = root;
        TreeNode father = null; //初始情况下，父亲节点是null
        while (cursor != null && key != cursor.val) {
            father = cursor;
            if (key < cursor.val)
                cursor = cursor.left;
            else
                cursor = cursor.right;
        }

        if (cursor != null && cursor.val == key) //found
        {

            if (cursor.left == null || cursor.right == null) { //存在一个空的左子树或者空的右子树或者左右子树都是空的
                TreeNode subTree = cursor.left == null ? cursor.right : cursor.left;
                if (father == null) //说明被删除的节点是root节点，并且左子树或者右子树有一个为空，或者两个都是空 的
                    return subTree;
                else {
                    if (father.left == cursor)
                        father.left = subTree;
                    else
                        father.right = subTree;
                    return root;
                }

            } else { //左右子树都有节点，因此寻找它的后置节点，并且，它的后置节点nextNode肯定是在其右子树上，并且，后置节点肯定没有左子树的

                father = cursor;//保存后置节点的父节点，注意，父节点不一定刚好是这个待删除的节点
                TreeNode nextNode = cursor.right;
                while (nextNode.left != null) {
                    father = nextNode;
                    nextNode = nextNode.left;
                }

                //用后置节点替换掉当前节点，删除后置节点，用后置节点的右子树的根节点替换后置节点
                cursor.val = nextNode.val;
                if (father.left == nextNode) father.left = nextNode.right;
                else father.right = nextNode.right;
                return root;
            }
        } else //not found
        {
            return root;
        }
    }


    public static void main(String[] args) {
        TreeNode node5 = new TreeNode(5);
        TreeNode node10 = new TreeNode(10);
        TreeNode node6 = new TreeNode(6);
        TreeNode node15 = new TreeNode(15);
        TreeNode node20 = new TreeNode(20);


        node15.left = node6;
        node15.right=node20;
//        node6.left = node5;
        node6.right = node10;

//        node10.left = node5;
        TreeNode cursor = node10.left;

        TreeNode nodeTest = new TreeNode(100);


        TreeNode tn = new DeleteNodeInBST().deleteNode(node15, 10);
        System.out.println(tn == null ? null : tn.val);
    }
}
