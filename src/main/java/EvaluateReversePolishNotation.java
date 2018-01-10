import java.util.Stack;

/**
 * Created by wuchang at 1/9/18
 * Question 150
 */

public class EvaluateReversePolishNotation {
    public int evalRPN(String[] tokens) {

        Stack<String> s = new Stack<String>();
        for(int i=0;i<tokens.length;i++){
            if(tokens[i].equals("+") || tokens[i].equals("-") || tokens[i].equals("*") || tokens[i].equals("/")){

                String a = s.pop();
                String b = s.pop();
            }
            else
                s.push(tokens[i]);
        }
        return Integer.valueOf(s.peek());

    }

    public static void main(String[] args) {

    }
}
