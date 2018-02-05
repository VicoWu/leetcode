package leetcode;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wuchang at 12/29/17
 * Question 306
 * [这里](https://leetcode.com/problems/additive-number/discuss/75567/)讲解了递归和迭代两种方法。

 # 递归方法

 我自己实现了递归方法，与[这里](https://leetcode.com/problems/additive-number/discuss/75567/)的讲解一致。

 注意的地方：

 1. 为了避免两个整形相加发生溢出，我使用BigDicimal来进行运算，BigDicimal使用动态内存实现几乎任意精读、长度的数字的运算；
 2. 题目中规定01是非法的，但是0是合法的，因此，对于每一个数字，我们需要判断这个数字长度大于1并且以0开头，如果是，则肯定是非法的；
 3. 我们在遍历过程中，对于nums需要包含a, b 和 a+b，因此，nums的长度l 必须满足 l>2a，因此，我们在外层遍历的时候`for(int i=0;i<num.length()-2;i++){//这里可以优化，假如num.length=5，那么第一个数字最大只能长度为2`可以控制i的范围进行优化

 # 迭代方法

 迭代方法其实与递归的方式相似，通过遍历来代替递归。

 比如1235813，a=1,b=2，那么我们判断35813是否以3开头，然后,2+3=5,我们再看5813是否以5开头，如此不断向前迭代，最终得出结果。


 */

public class AdditiveNumber {
    /**
     * 这是我自己实现的递归方法
     * @param num
     * @return
     */
    public boolean isAdditiveNumber(String num) {

        AtomicInteger ai;
        ThreadLocal tl;
        ConcurrentHashMap chm;
        ReentrantLock rtl;

        for(int i=0;i<num.length()-2;i++){//这里可以优化，假如num.length=5，那么第一个数字最大只能长度为2
            if(i>0 && num.startsWith("0"))
                break; //由于0是合法的，但是01是非法的，因此，判断是否有以0开头并且长度大于1的非法字符
            for(int j=i+1;j<num.length()-1;j++){
              String a = num.substring(0,i+1); //0到i , 左右包含
              String b = num.substring(i+1,j+1) ;//i+1 ， j  ， 左右包含
                if(b.startsWith("0") && j-i>1) //由于0是合法的，但是01是非法的，因此，判断是否有以0开头并且长度大于1的非法字符
                    break;
              boolean result =    isAdditiveNumber(a,b,num);
              if(result)//如果找到了一个正确结果，可以直接返回了，否则继续查找其它方案
                  return true;
            }
        }
        return false;
    }

    /**
     * 比如1235813，a=1,b=2，那么要判断是不是合法的，则需要判断num是不是以123开头，如果是，则继续递归判断235813是不是以235开头的，判断35813是不是以358开头的。。。
     * @param a
     * @param b
     * @param num
     * @return
     */
    private boolean isAdditiveNumber(String a,String b, String num){
        String added =  (new BigInteger(a).add(new BigInteger(b))).toString();
       return  num.startsWith(a+b+added) && ((a+b+added).length() == num.length()
                                         ?true//如果整个num已经结束了，则直接返回true
                                         :isAdditiveNumber(b,added,num.substring(a.length()))); //递归调用，对num.substring(a.length()）进行判断
    }



    /**
     * 这是[这里](https://leetcode.com/problems/additive-number/discuss/75567/)的迭代算法
     * @param num
     * @return
     */
    public boolean isAdditiveNumberByIterative(String num) {
        int n = num.length();
        for (int i = 1; i <= n / 2; ++i) //优化方法，i必定<n/2
            for (int j = 1; Math.max(j, i) <= n - i - j; ++j)
                if (isValid(i, j, num)) return true;
        return false;
    }
    private boolean isValid(int i, int j, String num) {
        if (num.charAt(0) == '0' && i > 1) return false; //判断是否是以0开头且长度大于1的整数
        if (num.charAt(i) == '0' && j > 1) return false; //判断是否是以0开头且长度大于1的整数
        String sum;
        BigInteger x1 = new BigInteger(num.substring(0, i));
        BigInteger x2 = new BigInteger(num.substring(i, i + j));
        //不断往前滚动迭代字符串判断是否是additive number
        for (int start = i + j; start != num.length(); start += sum.length()) {
            x2 = x2.add(x1); //比如上一轮x1(1),x2(2) 和 sum(3) ， 因此下一轮，x1=1=x2,x2=3=sum,sum=x1+x2=4，这样不断往前
            x1 = x2.subtract(x1);
            sum = x2.toString();
            if (!num.startsWith(sum, start)) return false;
        }
        return true;
    }




    private String add(String a , String b){
        Integer c = new Integer(a) + new Integer(b);
        return c.toString();
    }
    public static void main(String[] args) {

        System.out.println(new AdditiveNumber().isAdditiveNumber("1021"));
    }
}
