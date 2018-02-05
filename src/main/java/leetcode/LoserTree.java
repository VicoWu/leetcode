package leetcode;/*
 * ResultSet.java     0.0.1 2013/04/04
 * Copyright(C) 2013 db-iir RUC. All rights reserved.
 */
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * This Class implements the loser tree algorithm.
 *
 * @version 0.0.1, 2013/04/04
 */

 class Result implements  Comparable<Result>{

     int r ;

     ThreadPoolExecutor te;
    @Override
    public int compareTo(Result o) {
        return this.r - o.r > 0?1:-1;
    }

    public Result(int r) {
        this.r = r;
    }
}

public class LoserTree
{
    private int[] tree = null;// 以顺序存储方式保存所有非叶子结点
    private int size = 0;
    private ArrayList<Result> leaves = null;// 叶子节点

    public LoserTree(ArrayList<Result> initResults)
    {
        this.leaves = initResults;
        this.size = initResults.size();
        this.tree = new int[size];
        for (int i = 0; i < size; ++i)
        {
            tree[i] = -1;
        }
        for (int i = size - 1; i >= 0; --i)
        {
            adjust(i);
        }
    }

    public void del(int s)
    {
        leaves.remove(s);
        size--;
        tree = new int[size];
        for (int i = 0; i < size; ++i)
        {
            tree[i] = -1;
        }
        for (int i = size - 1; i >= 0; --i)
        {
            adjust(i);
        }
    }

    public void add(Result leaf, int s)
    {
        leaves.set(s, leaf);// 调整叶子结点
        adjust(s);// 调整非叶子结点
    }

    public Result getLeaf(int i)
    {
        return leaves.get(i);
    }

    public int getWinner()
    {
        return tree[0];
    }

    private void adjust(int s)
    {
        // s指向当前的值最小的叶子结点（胜者）
        int t = (s + size) / 2;// t是s的双亲

        while (t > 0)
        {
            if (s >= 0
                    && (tree[t] == -1 || leaves.get(s).compareTo(
                    leaves.get(tree[t])) > 0)) //当前节点与其父亲节点进行比对，如果当前节点是败者，那么用当前节点替换父亲节点
            {
                // 将树中的当前结点指向其子树中值最小的叶子
                int tmp = s;
                s = tree[t]; //保存胜者到s中，参加更上层的比较
                tree[t] = tmp; //将败者保存在双亲节点上
            }
            t /= 2; //逐层往上
        }
        tree[0] = s;// 树根指向胜者
    }

    public static void main(String[] args) {
        ArrayList<Result> res = new ArrayList<>();
        res.add(new Result(90)); //序号0
        res.add(new Result(10));//序号1
        res.add(new Result(9));//序号2
        res.add(new Result(20));//序号3
        res.add(new Result(6));//序号4
        res.add(new Result(8));//序号5
        res.add(new Result(12));//序号6
        LoserTree lt = new LoserTree(res);

    }
}
