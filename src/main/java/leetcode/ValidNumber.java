package leetcode;

/**
 * Created by wuchang at 12/29/17
 * Question 65
 * 剑指offer 20
 */

public class ValidNumber {
    public boolean isNumber(String s) {
        s = s.trim();  //去除前后的空格字符

        boolean pointSeen = false; //是否已经出现了点
        boolean eSeen = false; //是否已经出现了字符e
        boolean numberSeen = false; //是否已经出现了数字
        boolean numberAfterE = true; //在字符e后面是否有数字
        for(int i=0; i<s.length(); i++) {
            if('0' <= s.charAt(i) && s.charAt(i) <= '9') {
                numberSeen = true;
                numberAfterE = true;
            } else if(s.charAt(i) == '.') { //如果碰到了点
                if(eSeen || pointSeen) { //前面不可以出现了e，并且前面不可以有点号
                    return false;
                }
                pointSeen = true;//将见过点的标记位置位，这样，如果下次再遇到点就返回false,注意，"2."或者".2"是合法的。
            } else if(s.charAt(i) == 'e') {
                if(eSeen || !numberSeen) { //如果之前出现了e，或者在e前面还没有任何一个数字，返回false
                    return false;
                }
                numberAfterE = false; //numberAfterE置false，这样，如果在e后面没有出现数字，则方法将会返回false
                eSeen = true; //标记已经出现了e，这样，如果再遇到e，或者，遇到了点(e后面的数字必须是正整数或负整数)，都是非法的
            } else if(s.charAt(i) == '-' || s.charAt(i) == '+') { //加减号只能出现在数字开头，或者紧跟在e的后面
                if(i != 0 && s.charAt(i-1) != 'e') {
                    return false;
                }
            } else { //由于前后的空格已经过滤，所以，除了数字、点、e、+/-z之外的所有字符，一旦遇到就是非法
                return false;
            }
        }

        return numberSeen //防止没有出现任何一个数字，比如，就一个.或者一个e或者一个+/-
                && numberAfterE; //防止e是最后一个字符
    }

    public boolean isNumber2(String s){
        s = s.trim();
        int index = checkSign(0, s);
        if(index == -1)
            return false;
        if(s.charAt(index) != '.'){
            index = checkUnsignedInt(index, s, false);
            if(index == s.length()) // 全部是数字
                return true;
            if(index == -1) //一个数字都没有
                return false;
        }
        if(s.charAt(index) == '.') // 数字检查完了， 开始检查小数部分
        {
            index = checkUnsignedInt(index+1, s, false);
            if(index == -1)  // 非法
                return false;
            if(index == s.length()) // 到字符串的末尾了
                return true;
        }
        if(s.charAt(index) == 'e') // 遇到e了
        {
            index = checkSign(index+1, s); //检查符号
            if(index == -1)
                return false;
            index = checkUnsignedInt(index, s, true); // 检查最后面的一段数字
            if(index == -1 || index != s.length()) //非法数字
                return false;
            else
                return true;
        }
        else
            return false;
    }

    private int checkSign(int startIndex, String s){
        if(startIndex >= s.length())
            return -1;
        return (s.charAt(startIndex) == '+' || s.charAt(startIndex) == '-') ? startIndex + 1 : startIndex;
    }

    /**
     * 检查无符号数字，要求至少有一个数字
     * @param startIndex
     * @param s
     * @return
     */
    private int checkUnsignedInt(int startIndex, String s, boolean atLeastOne){
        int i = startIndex;
        if(startIndex >= s.length())
            return atLeastOne ? -1 : startIndex;
        for(; i < s.length() && s.charAt(i) >= '0' && s.charAt(i) <= '9'; ){
            i++;
        }
        if(i == startIndex)
            return -1;
        return i;
    }




    public static void main(String[] args) {
        System.out.println(new ValidNumber().isNumber2("."));
        System.out.println(new ValidNumber().isNumber2("2e10"));
        System.out.println(new ValidNumber().isNumber2("1e"));
        System.out.println(new ValidNumber().isNumber2("e3"));
        System.out.println(new ValidNumber().isNumber2(".6e-1"));
        System.out.println(new ValidNumber().isNumber2(".99e2.6"));
        System.out.println(new ValidNumber().isNumber2("3."));
        System.out.println(new ValidNumber().isNumber2("--6"));


    }
}
