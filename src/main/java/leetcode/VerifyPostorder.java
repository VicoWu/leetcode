package leetcode;

/**
 * 剑指 Offer 33. 二叉搜索树的后序遍历序列
 * 当我们知道左子树和右子树都是二叉树的合法后序遍历，怎么才能确定当前树是合法后序遍历呢？唯一的方式还是需要遍历左子树的所有节点，确定左子树所有节点值小于跟节点，以及遍历右子树的所有节点，确定右子树所有节点值小于跟节点
 */
public class VerifyPostorder {

    public boolean verifyPostorder(int[] postorder) {
        return verifyPostorder(postorder, 0, postorder.length - 1);
    }

    public boolean verifyPostorder(int[] postorder, int i, int j) {
        if(i >=j){
            return true;
        }
        int cur = i;

        while(cur < j && postorder[cur] < postorder[j]){
            cur++;
        }
        int mid = cur;
        while(cur < j && postorder[cur] > postorder[j]){
            cur++;
        }
        if(cur < j){
            return false;
        }
        return verifyPostorder(postorder, i, mid - 1) && verifyPostorder(postorder, mid, j-1);
    }

    public static void main(String[] args) {
        int[] postorder = {1,2,5,10,6,9,4,3};
        new VerifyPostorder().verifyPostorder(postorder);
    }
}
