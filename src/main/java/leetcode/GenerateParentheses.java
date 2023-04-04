package leetcode; /**
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

    class Solution {
        public List<String> generateParenthesis(int n) {
            int leftN=0, rightN=0;
            List<String> results = new LinkedList<String>();
            char[] result = new char[2 * n];
            generate(results, 0, 0, result, 0);
            return results;
        }

        public void generate2(List<String> results, int leftN, int rightN, char[] result, int cur){
            if(cur == result.length){
                results.add(new String(result));
                return;
            }else{
                // 如果当前右括号的数量小于左括号，那么这时候添加右括号就是合法的
                if(rightN < leftN){
                    result[cur] = ')';
                    generate(results, leftN, rightN+1, result, cur+1);
                }
                // 只要当前左括号的数量小于n, 那么添加左括号就是合法的
                if(leftN < result.length/2){
                    result[cur] = '(';
                    generate(results, leftN+1, rightN, result, cur+1);
                }
            }
        }
    }

    public static void main(String[] args){
        new GenerateParentheses().generateParenthesis(3);
    }
}
