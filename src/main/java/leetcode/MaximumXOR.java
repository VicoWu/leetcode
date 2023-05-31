package leetcode;

import java.util.ArrayList;
import java.util.List;

public class MaximumXOR {
    public int findMaximumXOR(int[] nums) {
        List<Integer> zero = new ArrayList();
        for(int i = 0 ; i< nums.length; i++ ){
            zero.add(nums[i]);
        }

        return findMaximumXOR(new ArrayList<>(zero), zero, 30);
    }

    /**
     * @param a
     * @param b
     * @param depth
     * @return
     */
    public int findMaximumXOR(List<Integer> a, List<Integer> b, int depth) {
        if(depth < 0 )
            return 0;
        Math.pow(10, 2);
        int curBit = 1 << depth; //准备查看当前1 << depth的这一个bit的1 和 0 的情况
        List<Integer> a_0 = new ArrayList(); // a中当前位位0的数字集合
        List<Integer> a_1 = new ArrayList(); // a中当前位位1的数字集合
        List<Integer> b_0 = new ArrayList(); // b中当前位位0的数字集合
        List<Integer> b_1 = new ArrayList(); // b中当前位位1的数字集合
        for(int i = 0;i<a.size();i++){
            if((a.get(i) & curBit) == curBit){
                a_1.add(a.get(i));
            }else{
                a_0.add(a.get(i));
            }
        }

        for(int i = 0;i<b.size();i++){
            if((b.get(i) & curBit) == curBit){
                b_1.add(b.get(i));
            }else{
                b_0.add(b.get(i));
            }
        }

        // 当前位在两拨数字中分别出现了1和0，那么结果的当前位肯定异或位1
        if(a_0.size() > 0 && b_1.size() > 0 || a_1.size() > 0 && b_0.size() > 0){
            int res = 0;
            if(a_0.size() > 0 && b_1.size() > 0){
                res = Math.max(res, findMaximumXOR(a_0, b_1, depth-1)); // 递归计算下一位结果
            }
            if(a_1.size() > 0 && b_0.size() > 0){
                res = Math.max(res, findMaximumXOR(a_1, b_0, depth-1)); // 递归计算下一位结果
            }
            return curBit | res; // 当前位的结果，或上后面的位的结果
        }else{ // 当前位在两拨数字中没有分别出现1和0，即全位0，或者全为1， 因此，结果的当前位为0，开始计算下一位
            return findMaximumXOR(a, b , depth-1 );
        }
    }

    public static void main(String[] args) {
        int[] array = {2, 20, 3};
        System.out.println(new MaximumXOR().findMaximumXOR(array));
    }
}
