/**
 * Created by wuchang at 12/29/17
 * Question 65
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

    public static void main(String[] args) {
        System.out.println(new ValidNumber().isNumber(".2"));
    }
}
