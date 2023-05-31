package leetcode;

import java.util.Random;

/**
 * https://leetcode.cn/problems/cuyjEf/solutions/1036717/an-quan-zhong-sheng-cheng-sui-ji-shu-by-bosxd/
 *
 */
public class PickIndex {
    int[] pre;
    Random r;
    public PickIndex(int[] w) {
        pre = new int[w.length];
        int sum = 0;
        for(int i = 0;i<w.length;i++){
            sum = sum + w[i];
            pre[i] = sum;
        }
        r = new Random();
    }

    public int pickIndex() {
        // 生成[1, pre[pre.length - 1]] 之间的随机数
        int random = r.nextInt(pre[pre.length - 1]) + 1;
        int start = 0; int end = pre.length-1;

        //通过二分查找，找到值大于或者等于random的最小的index
        while(start < end){
            int mid = (start + end)/2;
            if(random <= pre[mid]){
                end = mid;
            }else{
                start = mid + 1;
            }
        }
        return start;
    }

    public static void main(String[] args) {
        int[] input = {3,14,1,7};
        PickIndex pi = new PickIndex(input);
    }
}
