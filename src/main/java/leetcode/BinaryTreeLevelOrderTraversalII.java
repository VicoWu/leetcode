package leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author chang.wu
 * @date 11/16/20
 *
 * Created by wuchang at 1/6/18
 * Question 107
 *
 */
public class BinaryTreeLevelOrderTraversalII
{
    public static class TreeNode {
        int val;
        BinaryTreeLevelOrderTraversalII.TreeNode left;
        BinaryTreeLevelOrderTraversalII.TreeNode right;
        TreeNode(int x) { val = x; }
    }

    /**
     * 不管结果是从底向上还是从顶向下，算法都是一样的，只是插入到Queue中的顺序不同。如果是从底向上，那么每次行程一行的结果，就插入到wrapList的都一个位置
     * ，即wrapList.add(0, subList), 如果是从顶向下，就插入到wrapList的后面，即wrapList.add(wrapList.size, subList)
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrderBottomBFS(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        List<List<Integer>> wrapList = new LinkedList<List<Integer>>();

        if(root == null) return wrapList;

        queue.offer(root);
        while(!queue.isEmpty()){
            int levelNum = queue.size(); // java里面可以知道元素的个数，如果是C++, 需要有计数器统计当前层剩余元素的个数和下一层元素的总个数
            List<Integer> subList = new LinkedList<Integer>();
            for(int i=0; i<levelNum; i++) {
                if(queue.peek().left != null) queue.offer(queue.peek().left);
                if(queue.peek().right != null) queue.offer(queue.peek().right);
                subList.add(queue.poll().val);
            }
            wrapList.add(0, subList);

        }
        return wrapList;
    }

    /**
     * 之所以可以进行深度优先遍历（对于一个tree来说，深度优先遍历就是后序遍历，广度优先遍历就是分层遍历），是因为任何一个节点我们
     * 都知道这个节点的深度，同时，对于wrapList，我们是可以任意访问它的任意节点的。
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrderBottomDFS(TreeNode root) {
        List<List<Integer>> wrapList = new LinkedList<List<Integer>>();
        levelMaker(wrapList, root, 0);
        return wrapList;
    }

    public void levelMaker(List<List<Integer>> list, TreeNode root, int level) {
        if(root == null) return;
        if(level >= list.size()) { //到达一个新的深度，因此创建一个新的层次的list
            list.add(0, new LinkedList<Integer>());
        }
        levelMaker(list, root.left, level+1);
        levelMaker(list, root.right, level+1);
        list.get(list.size()-level-1).add(root.val);// 第0层的root节点是最后一个list，在list中的序号是list.size()-1，树的最底层的节点在list中的序号是0
    }
}
