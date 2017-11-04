/**
 * Question 224
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
