/**
 * Question 22
 */
import java.util.*;
public class GenerateParentheses {
    List<String> result =new LinkedList<String>();

    public List<String> generateParenthesis(int n) {

        char[] parentheses=new char[n*2];
        appendClousure(parentheses,n*2,0,0);
        return result;
    }

    private void appendClousure(char[] parenthese,int totalLength,int left,int right){

        if(left+right == totalLength){ //已经填充完毕
            result.add(new String(parenthese));
            System.out.println("adding "+new String(parenthese));
            return;
        }

        if(left < totalLength/2){//允许填充左括号

            parenthese[left+right] = '(';
            appendClousure(parenthese,totalLength,left+1,right);
        }
        if(right < left)//允许填充右括号
        {

            parenthese[left+right] = ')';
            appendClousure(parenthese,totalLength,left,right+1);
        }
    }

    public static void main(String[] args){
        new GenerateParentheses().generateParenthesis(3);
    }
}
