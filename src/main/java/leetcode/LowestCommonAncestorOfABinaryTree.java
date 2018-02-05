package leetcode;

/**
 * Created by wuchang at 12/28/17
 */

public class LowestCommonAncestorOfABinaryTree {



    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    public static class Result{
        TreeNode myParent;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        return this.search(root,p,q,new Result());
        //return search2(root,p,q);
    }

    /**
     * 这是我自己的实现，search方法的返回结果如果不为空就代表已经找到了最小公共节点
     * 我使用result来携带当前找到的p或者q的父节点，如果没有找到p或者q，则result为空，否则，result就携带对应的p或者q的父亲节点。因此，如果我们在某个root的时候，发现左子树调用以后的result
     * 和右子树调用以后的result都不为空，那肯定是左子树和右子树分别都找到了p和q，因此，当前节点就是最低公共节点。
     * @param root
     * @param p
     * @param q
     * @param result
     * @return 如果还没有找到最低公共子树，则返回null，否则，返回最低公共子树
     */
    public TreeNode search(TreeNode root, TreeNode p, TreeNode q, Result result) {

        if (root == null)
            return null;


        if (root == p || root == q)
            result.myParent = root;


        Result leftP = new Result();Result rightP = new Result();

        TreeNode left =  search(root.left, p, q,leftP);
        if(left!=null) //返回值不为空，说明从左子树中找到了p和q的最小公共父节点，因此，没有必要再看右子树了，直接返回结果了
            return left;
        if(result.myParent !=null && leftP.myParent!=null)
            return root; ///如果当前节点是p或者q中的某个，并且，左子树中找到了另外一个，则说明找到了公共节点，公共节点就是当前节点，即p和q中的一个

        TreeNode right = search(root.right, p, q,rightP);
        if(right !=null)
            return right;//返回值不为空，说明从左子树中找到了p和q的最小公共父节点，因此，没有必要再看右子树了，直接返回结果了
        if(result.myParent !=null && rightP.myParent!=null)
            return root; //如果当前节点是p或者q中的某个，并且，右子树中找到了另外一个，则说明找到了公共节点，公共节点就是当前节点，即p和q中的一个

        if(leftP.myParent!=null && rightP.myParent != null ) //左子树和右子树都找到了祖先，那么，这个root节点就是最低的公共祖先
            return root;

        if(result.myParent == null)
            result.myParent = (leftP.myParent == null ? rightP.myParent : leftP.myParent); //通过result向上层返回当前结果
        return null;
    }

    public TreeNode search2(TreeNode root,TreeNode p , TreeNode q){

        if(root==null || root == p || root==q) //找到了其中某一个节点
            return root;
       TreeNode left =  search2(root.left,p,q);
       TreeNode right = search2(root.right,p,q);
       return left==null
               ?right
               :(right==null
                   ?left
                   :root);//如果左右子树都不是空的，则说明左右子树分别都找到了p和q，因此返回root
    }

    public static  void test(TreeNode n){
        n.val=5;
    }

    public static void main(String[] args) {
        TreeNode t6 = new TreeNode(6);
        TreeNode t2 = new TreeNode(2);
        TreeNode t8 = new TreeNode(8);
        TreeNode t0 = new TreeNode(0);
        TreeNode t4 = new TreeNode(4);
        TreeNode t7 = new TreeNode(7);
        TreeNode t9 = new TreeNode(9);
        TreeNode t3 = new TreeNode(3);
        TreeNode t5 = new TreeNode(5);
        t6.left = t2;
        t6.right = t8;
        t2.left = t0;
        t2.right = t4;
        t8.left = t7;
        t8.right = t9;
        t4.left = t3;
        t4.right = t5;
        //new LowestCommonAncestorOfABinaryTree().test(t6);

        System.out.println(new LowestCommonAncestorOfABinaryTree().lowestCommonAncestor(t6,t6,t4).val);
    }
}
