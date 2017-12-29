/**
 * Created by wuchang at 12/28/17
 * Question 278
 *
 * 这一题与[Search For Range](https://leetcode.com/problems/search-for-a-range/description/)非常相似，
 * 都是要找某个目标值在最左侧或者最右侧出现的地方。我的解答是直接使用[Search For Range](https://leetcode.com/problems/search-for-a-range/description/)中
 * 的两轮二分查找法，这样，当二分查找退出的时候，low坐标指向的就是最左侧的值。
 *
 *
 */

public class FirstBadVersion extends VersionControl{
    public int firstBadVersion(int n) {

        long low = 1;long hi = n; //转换成long，防止发生溢出
        while(low < hi){
            long mid = (hi+low)/2;
            if(isBadVersion((int)mid))
                hi = mid;
            else
                low = mid+1;
        }
        return (int)low;
    }


    /**
     * 如果是int类型，可能发生溢出
     * @param n
     * @return
     */
    public int firstBadVersion2(int n) {

        int low = 1;int hi = n;
        while(low < hi){
            int mid = (hi+low)/2;
            if(isBadVersion((int)mid))
                hi = mid;
            else
                low = mid+1;
        }
        return (int)low;
    }


    public static void main(String[] args) {

        System.out.println(new FirstBadVersion().firstBadVersion2(2126753390));
    }

}


class VersionControl{

    boolean isBadVersion(int version){
        if(version < 1702766719)
            return false;
        return true;
    }
}