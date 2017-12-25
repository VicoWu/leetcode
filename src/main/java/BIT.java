/**
 * Created by wuchang at 12/24/17
 * 这个类用来实现BIT搜索树算法
 * 比如，[Reverse Pairs](https://leetcode.com/problems/reverse-pairs/description/)问题
 * 这个博客讲解了BIT树http://m.blog.csdn.net/a1323933782/article/details/53698473
 */

public class BIT {
    int sum(int[] c,int x){ //求x前缀和
        int ans = 0;
        while(x>0){ //迭代到虚拟结点
            ans+=c[x];
            x -= lowbit(x); //向左上方移动，一直移动到虚拟节点
        }
        return ans;
    }
    void add(int[] c,int x, int d){ //修改操作
        while(x<c.length){ //判断是否超出边界
            c[x]+=d;
            x+=lowbit(x); //向右上方走，一直移动到根节点
        }
        return ;
    }

    int lowbit ( int x){
        return x&-x;
    }


    public static void main(String[] args) {

        BIT bit = new BIT();

        int[] c = {0,0,0,0,0,0,0,0};

//        bit.add(c,1,1);
//        bit.add(c,2,1);
        bit.add(c,3,1);
        bit.add(c,5,1);
        bit.sum(c,5);
    }
}
