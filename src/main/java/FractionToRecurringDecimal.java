import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuchang at 12/26/17
 * Question 166
 *
 * 通用解法（自己实现的）

 首先必须要了解，什么决定了循环的出现？是余数。相同的余数在下一次补0运算的时候必然得到相同的结果，即出现循环。所以我们使用一个map记录我们在运算的过程中出现过的余数和使用这个余数补0以后除以除数以后的结果的位置。这样，我们下一次遇到了相同的余数，就知道出现了循环并且通过这个map知道了循环点，这样就可以在正确的位置添加括号了。

 同时，int类型有可能溢出，比如，我们在运算开始的时候先确定正负，然后对numerator和denominator取绝对值。如果是Integer.MIN_VALUE, 取绝对值发生溢出，因此，我们直接将分子分母全部转换成long进行运算。

 要正确解答这一题，必须完全了解我们在做分数的除法的运算过程：

 1除以3
 1/3 = 0 所以前面为 0.  1%3余1 ， 补一个0 ， 10/3 =1 ，所以变成0.1
 1%3余1，  由于前面已经有余1了，所以说明从这里开始出现循环，然后退出计算，结果就是0.(3)


 1除以6
 1/6=0 ，所以前面为0. 1%6=1，补一个0，10/6 = 1 ，所以变成0.1，10%6余4,通过map保存余数4对应的1的位置
 4补0变成40，40/6=6，所以变成0.16，余4 ， 由于余数4刚刚出现了，说明出现了循环，循环位置是6, 因此结果就是0.1(6)

 */

public class FractionToRecurringDecimal {
    public String fractionToDecimal(int n, int d) {

        long numerator = (long)n;
        long denominator = (long)d; //必须转换成long，否则有溢出的可能，比如n=Integer.MIN_VALUE,我们在取绝对值的时候就会发生溢出

        String sign = "";
        if(numerator>0 ^ denominator>0)
            sign="-";
        numerator = Math.abs(numerator);denominator = Math.abs(denominator);
        long head = numerator/denominator;
        StringBuffer sb1= new StringBuffer(sign+head); //sb1存放了运算结果
        long remaining = numerator % denominator;
        if(remaining == 0) //可以整除，直接退出，比如50除以10，连小数点都没有
            return sb1.toString();
        sb1 = sb1.append(".");
        /**
         * 这个map记录了每一个余数的循环位置，这个循环的位置用来在发现循环的时候知道循环的位置，比如1/6 前面已经计算得到0.16，然后remaining=4，发现4已经出现过，并且4的循环位置在2，所以退出来
         */
        Map<Long,Integer> locationRecord = new HashMap<Long,Integer>();
        long divided=0;
         while(remaining!=0 ){
             if(locationRecord.containsKey(remaining)){ //发现了循环，没有必要继续计算了
                 break;
             }
            locationRecord.put(remaining,sb1.toString().length()); //记录当前余数的位置
            remaining  = remaining*10; //余数补一个0，除以除数
            divided = remaining/denominator;
            if(divided == 0){ //发现divided为0，说明除尽了，计算结束
                sb1.append(divided);
                continue;
            }
            sb1.append(divided);
            remaining = remaining % denominator;
        }
         if(remaining == 0)//没有任何循环，除尽了，直接返回结果，比如，1除以8等于0.125
            return sb1.toString();
        else
         {
             //根据循环位置来进行组装，比如1除以6，我们得到0.16，6循环，则结果应该是0.1(6)
            return sb1.substring(0,locationRecord.get(remaining)) + "("+sb1.substring(locationRecord.get(remaining)) + ")";
         }
    }

    public static void main(String[] args) {
        StringBuffer sb3 =new StringBuffer( "0.6");
        System.out.println( new FractionToRecurringDecimal().fractionToDecimal(50,10));
        System.out.println( new FractionToRecurringDecimal().fractionToDecimal(-1,-2147483648));
        System.out.println( new FractionToRecurringDecimal().fractionToDecimal(Integer.MIN_VALUE,1));
        System.out.println( new FractionToRecurringDecimal().fractionToDecimal(Integer.MIN_VALUE,-2));
        System.out.println( new FractionToRecurringDecimal().fractionToDecimal(7,-12));
        System.out.println( new FractionToRecurringDecimal().fractionToDecimal(0,8));
        System.out.println( new FractionToRecurringDecimal().fractionToDecimal(-50,8));
        System.out.println( new FractionToRecurringDecimal().fractionToDecimal(1,6));
        System.out.println( new FractionToRecurringDecimal().fractionToDecimal(1,17));
        System.out.println( new FractionToRecurringDecimal().fractionToDecimal(1,90));
        System.out.println( new FractionToRecurringDecimal().fractionToDecimal(1,3));
        System.out.println( new FractionToRecurringDecimal().fractionToDecimal(1,333));
        System.out.println( new FractionToRecurringDecimal().fractionToDecimal(1,2));
        System.out.println( new FractionToRecurringDecimal().fractionToDecimal(2,1));
    }
}
