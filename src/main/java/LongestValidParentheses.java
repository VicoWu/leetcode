import java.util.Stack;

public class LongestValidParentheses {

    public int getScore(String input){
        int score=0;
        for(int i=0;i<input.length();i++){
            if(input.charAt(i) == '(') score++;
            else if(input.charAt(i) == ')' ) score--;
        }
        return score;
    }


    // helper function checks if string s contains valid parantheses
    boolean isValid(String s) {
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') count++;
            if (c == ')' && count-- == 0) return false;
        }

        return count == 0;
    }


    public int longestValidParentheses(String s) {

        int score = getScore(s);
        for(int i=0,j=s.length()-1;i<j;){

            while(s.charAt(i)==')' && i<j){
                i++;
                score++;
            }

            while(s.charAt(j)== '(' && j>i){
                j--;
                score--;
            }




        }

        return 1;
    }

    public int longestValidParentheses2(String s) {
        char[] S = s.toCharArray();
        int[] V = new int[S.length];
        int open = 0;
        int max = 0;
        for (int i=0; i<S.length; i++) {
            if (S[i] == '(') open++;
            if (S[i] == ')' && open > 0) {
                // matches found
                V[i] = 2+ V[i-1];
                // add matches from previous
                if(i-V[i]>0)
                    V[i] += V[i-V[i]];
                open--;
            }
            if (V[i] > max) max = V[i];
        }
        return max;
    }



    public int longestValidParentheses1(String s) {
        int max = Integer.MIN_VALUE;
        s += "x";
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == ')' && !stack.empty() && s.charAt(stack.peek())== '(')
                stack.pop();
            else{
                if(stack.empty()){
                    max = Math.max(max, i);
                }
                else
                    max = Math.max(max, i-stack.peek()-1);
                stack.push(i);
            }
        }
        return stack.empty() ? s.length() : max;
    }


    public static void main(String[] args) {

        String s = ")(()))";
        new LongestValidParentheses().longestValidParentheses1(s);
    }
}
