package leetcode;

/**
 * Created by wuchang at 12/25/17
 * Question 8
 * 字符串转整数的计算方式不必细说
 主要需要考虑的问题：
 1. 空字符串的剔除
 2. 符号判断：如果是负数，肯定有符号，如果是整数，有可能没有+号。因此，我们的计算方式是：
 默认是正数
 当发现了符号，则获取对应的符号并且i++

 ```
 if(input[index] == '-' || input[index] == '+')
 sign = input[index++] == '-'?-1:1;  //当发现了符号，则获取对应的符号并且i++，i++的目的是为了让index指向第一个数字，而不是一个符号
 ```
 3. 溢出判断
 ```
 if(result>Integer.MAX_VALUE/10 || result == Integer.MAX_VALUE/10 && input[i] - '0' > 7) //由于Integer的运算范围是-2147483648,2147483647 ，因此,在这里需要判断溢出，
 return sign==1?Integer.MAX_VALUE:Integer.MIN_VALUE;
 ```

 显然，由于result待会儿要乘以10，因此result必须小于或者等于`Integer.MAX_VALUE/10`。如果小于`Integer.MAX_VALUE/10`，则`result*10 + input[i] - '0'`;肯定不会溢出，而如果`result==Integer.MAX_VALUE/10`，则还必须保证` input[i] - '0' > 7`才不会使得`result*10 + input[i] - '0'`发生溢出。

 4.非法字符：如果在数字以前出现非法字符，直接就不会计算。如果在数字以后有非法字符，则计算截止到非数字以前的位置，比如`+-10`，直接返回`0`，`+10x`,返回`10`
 */

public class StringToInteger {
    public int myAtoi(String str) {

        if(str==null||str.isEmpty())
            return 0;
        Integer result=0;
        char[]   input = str.toCharArray();
        int index=0; int sign = 1;
        while(str.charAt(index) == ' ') index++; //抛弃空格
        if(input[index] == '-' || input[index] == '+')
            sign = input[index++] == '-'?-1:1;  //当发现了符号，则获取对应的符号并且i++，i++的目的是为了让index指向第一个数字，而不是一个符号
        for(int i=index;i<input.length && isValidIntegerChar(input[i]);i++){ //isValidIntegerChar(input[i])保证必须是数字，因此，如果遇到了非数字，循环退出，计算结果就截止到非法字符前面的数字
            if(result>Integer.MAX_VALUE/10 || result == Integer.MAX_VALUE/10 && input[i] - '0' > 7) //由于Integer的运算范围是-2147483648,2147483647 ，因此,在这里需要判断溢出，
                return sign==1?Integer.MAX_VALUE:Integer.MIN_VALUE;
            result = result*10 + input[i] - '0';

        }
        return sign * result;
    }

    private boolean isValidIntegerChar(char a ){
        return a-'0'>=0 && a-'0'<=9;
    }

    public static int myAtoi1(String str) {
        if (str.isEmpty()) return 0;
        int sign = 1, base = 0, i = 0;
        while (str.charAt(i) == ' ')
            i++;
        if (str.charAt(i) == '-' || str.charAt(i) == '+')
            sign = str.charAt(i++) == '-' ? -1 : 1;
        while (i < str.length() && str.charAt(i) >= '0' && str.charAt(i) <= '9') {
            if (base > Integer.MAX_VALUE / 10 || (base == Integer.MAX_VALUE / 10 && str.charAt(i) - '0' > 7)) {
                return (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            base = 10 * base + (str.charAt(i++) - '0');
        }
        return base * sign;
    }

    public int myAtoi2(String s) {
        char[] input = s.toCharArray();
        int i = 0;
        int res = 0;
        boolean positive = true;
        while(i < input.length && input[i] == ' ')  i++;
        // Skip preceeding + or -
        if(i < input.length && (input[i]== '-' || input[i] == '+')){
            if(input[i] == '-')
                positive = false;
            i++;
        }
        // skip preceeding 0
        while(i < input.length && (input[i] == '0')){
            i++;
        }
        while(i < input.length && input[i] >= '0' && input[i] <= '9'){
            int dif = positive ? (input[i] - '0') : ('0' - input[i]);
            if((res > Integer.MAX_VALUE/10 || res == Integer.MAX_VALUE/10 && dif > 7)){
                return Integer.MAX_VALUE;
            }
            if(res < Integer.MIN_VALUE/10 || res == Integer.MIN_VALUE/10 && dif < -8){
                return Integer.MIN_VALUE;
            }
            res = res * 10 + dif;
            i++;
        }
        return res;

    }

    public static void main(String[] args) {

        //System.out.println(new StringToInteger().getValidString("-0012a42"));
        System.out.println(new StringToInteger().myAtoi1("-2147483648"));
        System.out.println(new StringToInteger().myAtoi("-2147483648"));

    }
}
