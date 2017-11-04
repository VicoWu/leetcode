import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Question 282
 */
public class ExpressionAddOperators {


    /**
     * 这是讨论区的代码
     * https://discuss.leetcode.com/topic/24523/java-standard-backtrace-ac-solutoin-short-and-clear/2
     *
     for example, if you have a sequence of 12345 and you have proceeded to 1 + 2 + 3, now your eval is 6 right?
     If you want to add a * between 3 and 4, you would take 3 as the digit to be multiplied, so you want to take
     it out from the existing eval. You have 1 + 2 + 3 * 4 and the eval now is (1 + 2 + 3) - 3 + (3 * 4). Hope this could help : )
     * @param num
     * @param target
     * @return
     */
    public List<String> addOperators1(String num, int target) {
        List<String> rst = new ArrayList<String>();
        if(num == null || num.length() == 0) return rst;
        helper(rst, "", num, target, 0, 0, 0);
        return rst;
    }

    /**
     *
     * @param rst 已经确定的合法结果
     * @param path 当前已经确定的字符串形式
     * @param num 初始化的输入字符串
     * @param target 目标
     * @param pos 当前准备处理的字符串的位置
     * @param eval 当前已经计算得到的结果
     * @param multed
     */
    public void helper(List<String> rst, String path, String num, int target, int pos, long eval, long multed){
        if(pos == num.length()){
            if(target == eval){
                System.out.println(path);
                rst.add(path);
            }
            return;
        }
        for(int i = pos; i < num.length(); i++){
            if(i != pos && num.charAt(pos) == '0') break;
            long cur = Long.parseLong(num.substring(pos, i + 1));
            if(pos == 0){
                helper(rst, path + cur, num, target, i + 1, cur, cur);
            }
            else{
                helper(rst, path + "+" + cur, num, target, i + 1, eval + cur , cur);

                helper(rst, path + "-" + cur, num, target, i + 1, eval -cur, -cur);

                helper(rst, path + "*" + cur, num, target, i + 1, eval - multed + multed * cur, multed * cur );
            }
        }
    }

    public List<String> addOperators(String num, int target) {
        List<String> result = new LinkedList<String>();

        findResultRecursively(num,target,"",0,0,0,result);
        return result;
    }

    /**
     * 这是我自己模拟addOperators1()方法写的算法
     * 该算法没有捷径，需要从第一个字符开始，暴力方式尝试每一个位置添加三种运算符的情况，但是并不是每一种组合都完全需要重新计算，我们可以通过前面的一段结果，推算添加了一个字符以后的结果
     * 由于最终需要运算结果，因此，关键问题是，如果我们已经为前面的某一段确定了形式并有了运算结果，怎么得到添加了一段字符以后的计算结果，比如，已经确定了前面的形式是"1+2+3"，后面来了一个4，我们必须
     * 推算得到"1+2+3+4"、"1+2+3-4"、"1+2+3*4"的结果，因此，对于每一步，我们需要有的中间结果为：当前的前缀样式("1+2+3")，前缀样式的最后一个结果("3")，前缀的计算结果(6,即1+2+3的结果)，才能推算
     * 得到"1+2+3+4"、"1+2+3-4"、"1+2+3*4"的结果
     * 需要注意的地方：
     * 1.防止溢出，必须用long，比如输入为"8787878787",target=435,即，我们需要的形式是"87+87+87+87+87"，虽然这五个数字都没有溢出，但是我们在计算过程中会尝试"87*87*87*87*87"，可能发生溢出；
     * 2.数字是可能存在多位的，比如上面的87，因此，在代码中，我们使用for循环来确定所有可能，"8","87","878","8787","87878"等等
     * 3.0：数字0开头只能单独才合法，00，02都是非法的数字，因此如果我们发现数字的开头是0，则到了第二个位置就应该终止；
     * @param num 常量，输入的字符串
     * @param target 常量，目标结果
     * @param pre 前面已经完成的拼接结果
     * @param currentComputingResult 前面的计算结果
     * @param currentPosition 当前需要进行处理的位置
     * @param lastMulti 前面的pre的最后一个字符的值，我们需要这个值，因为如果我们在后面添加一个*，必须通过pre的计算值和这个lastMulti值获取当前结果
     * @param result 保存合法结果
     */
    public void findResultRecursively(String num,int target,String pre,long currentComputingResult,int currentPosition,long lastMulti,List<String> result){

        if(currentPosition==num.length() && currentComputingResult == target){ //已经遍历完了
            result.add(pre);
            System.out.println("pre"+pre);
            return;
        }

        /**
         * 比如,num="1234",target=10，当前currentPosition=2,即准备处理num.charAt(2)=='3'，但是后面极有可能是3，也有可能是34，因此，我们通过这个循环来处理所有的可能，包括"3"，"34"
         */
        for(int i=currentPosition;i<num.length();i++){
            String s = num.substring(currentPosition,i+1);
            if(i!=currentPosition && num.charAt(currentPosition) == '0') //如果第一个字符是0，那么，从第二个字符开始，都不可以是0，因为不能出现00
                break;
            if(currentPosition == 0 )//currentPosition==0代表这是num的第一个数字，前面没有任何运算符，即pre==""，因此，不存在拼接，直接pre就设置成当前的字符
            {
                findResultRecursively(num,target,s, Long.valueOf(s),i+1,Long.valueOf(s),result);

            }
            else{


                /**
                 *    开始全部列举后面是+或者-或者*的全部情况
                 *    比如,num="1234",target=15,即我们希望的形式是"1+2+3*4"，现在i=4，pre="1+2+3"，此时currentComputingResult=1+2+3=6,此时lastMulti = 3, 由于*的存在改变了运算顺序，因此，如果添加*，则和-或者+不同
                 *    1+2+3*4 = (1+2)+3*4 = ((1+2+3)-3 + 3*4) = currentComputingResult - lastMulti + lastMulti * Integer.valueOf(s)
                 */


                    findResultRecursively(num,target,pre+"+"+s, currentComputingResult + Long.valueOf(s),i+1,Long.valueOf(s),result);
                    findResultRecursively(num,target,pre+"-"+s, currentComputingResult - Long.valueOf(s),i+1,-Long.valueOf(s),result);//注意，如果是减去一个数，那么这里的lastMulti是负数，不是整数，比如1-5 ，则lastMulti是-5而不是5，搞错了就会在以后添加* 的时候出现错误结果
                    findResultRecursively(num,target,pre+"*"+s, currentComputingResult - lastMulti + lastMulti * Integer.valueOf(s),i+1, lastMulti * Integer.valueOf(s),result);

            }


        }
    }



    public static void main(String[] args) {
        List<String> result = new ExpressionAddOperators().addOperators("02",-2);
    }

}
