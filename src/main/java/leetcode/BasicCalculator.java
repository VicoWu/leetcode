package leetcode; /**
 * Question 224
 *
 * 由于有括号的存在，不能直接顺序读取，因此需要栈的协助；
 基本逻辑是：
 1.**遇到左括号、+或者-运算符**，直接进栈保存，因为左括号需要与后来出现的右括号进行匹配，而运算符需要与后面出现的数字进行匹配；
 2.**遇到数字**：数字可能不只一位，因此需要通过循环读取完整数字；读取到数字以后，数字的前面可能什么都没有(整个表达式的第一个数字)，可能是左括号，
 可能是运算符，**只有当数字前面是运算符**（即栈顶元素为运算符），才需要从栈顶弹出运算符，然后再弹出栈顶的数字即运算符左侧的数字，然后运算，将运算结果入栈；
 3.**遇到右括号**：如果遇到右括号，此时栈顶元素肯定是括号内的计算结果，因此先弹出栈顶保存的运算结果，再弹出左括号，然后再仿照步骤2遇到数字的情况，
 在栈顶弹出运算符和数字进行一次运算。




 */
import java.util.Stack;
public class BasicCalculator {


    public int calculate(String s) {

        Stack<String> stack = new Stack<String>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case ')':
                    String resultInParent = stack.pop();
                    stack.pop();
                    String calculated = calculateOnce(stack,resultInParent);
                    stack.push(calculated);
                    break;
                case '('://遇到左括号，入栈
                case '+': //遇到加号
                case '-': //遇到减号
                    stack.push(String.valueOf(c));
                    break;
                default:

                    if (Character.isDigit(c)) {
                        int start = i;
                        while (i<s.length() && Character.isDigit(s.charAt(i))) i++;
                        String result = s.substring(start, i);
                        result =  calculateOnce(stack,result);
                        stack.push(result);
                        i--;
                    }
                    break;
            }
        }
        if(stack.isEmpty())
            return 0;
        else{
            String value  = stack.pop();
            return  Integer.valueOf(value);
        }
    }

    private boolean isInteger(String s){
        try {
            Integer.valueOf(s);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    private String calculateOnce( Stack<String> stack,String current){
        if (!stack.isEmpty() && (stack.peek().equals("-") || stack.peek().equals("+"))) {
            String calculator = stack.pop(); //弹出算子 +／-
            String pre = !stack.isEmpty() && isInteger(stack.peek())?stack.pop():"0";
            Integer added = calculator.equals("-")?Integer.valueOf(pre) - Integer.valueOf(current):Integer.valueOf(pre) + Integer.valueOf(current);
            current = added.toString();
        }
        return current;
    }

    public static void main(String[] args) {

       System.out.println(new BasicCalculator().calculate("0")) ;

    }
}
