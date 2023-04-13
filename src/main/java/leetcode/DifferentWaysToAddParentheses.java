package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuchang at 1/4/18
 * Question 241
 *
 * [Unique Binary Search Trees](https://leetcode.com/problems/unique-binary-search-trees/description/)
 [Unique Binary Search Tree II](https://leetcode.com/problems/unique-binary-search-trees-ii/description/)
 [Different Ways to Add Parentheses](https://leetcode.com/problems/different-ways-to-add-parentheses/description/)这三道题的思想是一致的。


 * 这一题的基本思路非常明确，就是我们对于一个输入串，比如`2*3-4*5`，我们需要按照运算符的位置对字符串进行划分，比如将`2*3-4*5`划分成`[2, 3-4*5]` ,`[2*3，4*5]` 以及`[2*3-4，5]` 三个，
 每个划分将原来的字符串分成两部分，两部分各计算得到了一个结果的List，为A1 A2 ， 那么，整体的结果就是A1和A2中的结果进行排列组合的结果。
 这一题的先划分然后再将左右两侧的结果进行组合的思想，与[Unique Binary Search Tree II](https://leetcode.com/problems/unique-binary-search-trees-ii/description/)的思想是一致的。

 代码里面我的解法和别人的解法思想都是一样的，都是试图找到运算符然后进行划分，但是别人的更加简洁。

 */

public class DifferentWaysToAddParentheses {

    /**
     * 这是我的解法
     * @param input
     * @return
     */
    public List<Integer> diffWaysToCompute1(String input) {
        List<Integer> result = compute(input.toCharArray(), 0, input.length() - 1);
        return new LinkedList<>(result);
    }

    public List<Integer> compute(char[] input, int lo, int ri) {
        List<Integer> result = new LinkedList<>();
        int index;
        for (index = lo; index <= ri && input[index] >= '0' && input[index] <= '9'; index++) ;//我们从lo开始往右开始找运算符，看看能不能找到。如果无法找到，说明当前的数组就是一个数字
        if (index > ri) //这个输入里面只含有数字，直接返回这个数字
        {
            result.add(Integer.valueOf(new String(Arrays.copyOfRange(input, lo, ri + 1))));
            return result;
        }

        for (int i = lo, k = i; i < ri; i = k + 1) {
            for (k = i; k <= ri && input[k] >= '0' && input[k] <= '9'; k++) ;//从左到右找到一个运算符
            if (k > ri)//计算完了
                break;
            char op = input[k]; //运算符存放在位置k
            List<Integer> leftResult = compute(input, lo, k - 1);
            List<Integer> rightResult = compute(input, k + 1, ri);
            for (Integer l : leftResult) { //对左右两侧的结果进行排列组合
                for (Integer r : rightResult) {
                    if (l != null && r != null)
                        result.add(compute(op, l, r));
                }
            }
        }
        return result;
    }

    public Integer compute(char op, Integer ai, Integer bi) {
        if (op == '*') {
            return (ai) * (bi);
        } else if (op == '-')
            return ai - bi;
        else
            return (ai) + (bi);
    }


    /**
     * 这是别人的解法，也是试图找到运算符的位置，对输入进行拆分
     * @param input
     * @return
     */
    public List<Integer> diffWaysToCompute(String input) {
        List<Integer> ret = new LinkedList<Integer>();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '-' ||
                    input.charAt(i) == '*' ||
                    input.charAt(i) == '+') {
                String part1 = input.substring(0, i); //运算符左侧的字符串
                String part2 = input.substring(i + 1);//运算符右侧的字符串
                List<Integer> part1Ret = diffWaysToCompute(part1); //左侧的计算结果
                List<Integer> part2Ret = diffWaysToCompute(part2); //右侧的计算结果
                for (Integer p1 : part1Ret) {
                    for (Integer p2 : part2Ret) {
                        int c = 0;
                        switch (input.charAt(i)) {
                            case '+':
                                c = p1 + p2;
                                break;
                            case '-':
                                c = p1 - p2;
                                break;
                            case '*':
                                c = p1 * p2;
                                break;
                        }
                        ret.add(c);
                    }
                }
            }
        }
        if (ret.size() == 0) {
            ret.add(Integer.valueOf(input));
        }
        return ret;
    }


    public List<Integer> diffWaysToCompute3(String expression) throws Exception {
        String[] expressionArray = split(expression);
        return computeSubExpression(expressionArray, 0, expressionArray.length-1);
    }

    public List<Integer> computeSubExpression(String[] expressionArray, int start, int end) throws Exception {
        List<Integer> results = new ArrayList<Integer>();
        if(start == end){
            results.add(Integer.valueOf(expressionArray[start]));
            return results;
        }
        for(int i = start;i<=end;i++){
            if(isOp(expressionArray[i])){
                List<Integer> leftVs = computeSubExpression(expressionArray, start, i-1);
                List<Integer> rightVs = computeSubExpression(expressionArray,i+1, end);
                for(Integer leftV: leftVs){
                    for(Integer rightV: rightVs){
                        Integer value = compute(leftV, rightV, expressionArray[i]);
                        results.add(value);
                    }
                }
            }
        }
        return results;
    }

    private Integer compute(Integer leftV, Integer rightV, String op) throws Exception {
        switch(op){
            case "+":
                return leftV + rightV;
            case "-":
                return leftV - rightV;
            case "*":
                return leftV * rightV;
            default:
                throw new Exception("Unsupported operator");
        }
    }


    private Integer compute(String left, String right, String op) throws Exception {
        Integer leftV = Integer.valueOf(left);
        Integer rightV = Integer.valueOf(right);
        switch(op){
            case "+":
                return leftV + rightV;
            case "-":
                return leftV - rightV;
            case "*":
                return leftV * rightV;
            default:
                throw new Exception("Unsupported operator");
        }
    }

    private boolean isOp(String c){
        return c.equals("*") || c.equals("+") || c.equals("-");
    }


    private String[] split(String expression) {
        int i = 0;
        List<String> splits = new ArrayList<>();

        while(i < expression.length()){
            char c = expression.charAt(i);
            if(c < '0' || c > '9'){
                splits.add(String.valueOf(c));
                i++;
            }else{
                int j = i;
                while(j < expression.length() &&  expression.charAt(j) >= '0' && expression.charAt(j)<= '9'){
                    j++;
                }
                splits.add(expression.substring(i, j));
                i=j;
            }
        }
        return splits.toArray(new String[0]);
    }


    public static void main(String[] args) {

        try {
            new DifferentWaysToAddParentheses().diffWaysToCompute3("20*20-10");
        } catch (Exception e) {
            e.printStackTrace();
        }
        new DifferentWaysToAddParentheses().diffWaysToCompute1("20*20-10");
    }
}
