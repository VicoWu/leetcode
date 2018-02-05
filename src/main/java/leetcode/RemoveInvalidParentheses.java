package leetcode;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Question 301
 *
 * ## 我的解法 通过广度优先遍历方法
 通过栈的方式确认一个括号组合串是否合法的方式必须清楚。

 在这一题里面，如果我们遍历这个串，初始化`score=0`，如果遇到`(`，则+1，遇到`)`则减1，如果一个组合是合法的，当且仅仅当我们从左向右遍历的过程中，score始终 >=0，并且，遍历到最后一个当时候`score=0`；

 那么，在这一题中，我们什么时候确定应该删除当前当一个 `)` 或者 `(` 呢？
 如果我们发现某一个组合`score>0`，那么，为了让这个score=0，必须删除至少一个`(`；
 如果我们发现某一个组合`score<0`，那么，我们为了让score=0，必须删除至少一个`)`；

 因此，我在代码`removeInvalidParentheses()`中的解法，是通过分层方式进行遍历。使用分层当好处，是满足题目中当要求：`Remove the **minimum** number of invalid parentheses`,如果某一层已经发现了一个合法组合，
 那么，这一层当下层就不需要再处理了。

 我在代码中使用了`visited`这个HashSet来保存已经遍历过的组合，这样，以后再出现相同组合就直接抛弃，避免大量耗时；
 同时，我计算得分score，也是为了节约时间。因为只要得分不为0，那么这个串一定非法，得分为0，可能合法。并且，我只在方法入口计算了一次得分，以后都不需要计算，因为我们在计算下一层的时候，
 如果去掉`（`,则得分-1，如果去掉的是`) `，则得分+1。这样可以节约时间。


 ## 广度优先遍历
 https://discuss.leetcode.com/topic/28827/share-my-java-bfs-solution/8

 它的方法跟我一样，但是没有引入得分机制。在将下一层都所有可能组合入队列的时候，我会根据得分，排除掉部分组合，比如，如果当前得分`>0`，那么肯定需要删除至少一个(，因此我们只处理删掉(，如果得分`<0`，
 那么肯定需要删除至少一个)，因此我们只处理删掉)，如果得分`=0`，则需要对每一个位置的删除进行处理；


 ## 深度优先遍历
 https://discuss.leetcode.com/topic/34875/easy-short-concise-and-fast-java-dfs-3-ms-solution

 我们从左到右到遍历过程，只处理`)`过多到情况，即`score<0`到情况：
 从左到右遍历过程中，如果到达某一个位置last_j，首次发现`score<0`(此时score肯定`=-1`)，说明目前`[last_i,last_j]`之间肯定需要删除一个`)` )，因此我们遍历`[last_i,last_j]`区间，尝试删除每一处的`)`，
 这时候`[last_i,last_j]`的`score=0`，然后再处理剩下的`[last_j,input.length-1]`区间也使得`score=0`，这样，最终就可以处理完所有的需要删除`)`的情况；同理，我们把字符串翻转，就可以得到需要删除`(`的情况；


 *
 */
public class RemoveInvalidParentheses {
    public List<String> removeInvalidParenthesesByBFS(String s) {
        List<String> res = new ArrayList<>();

        // sanity check
        if (s == null) return res;

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        // initialize
        queue.add(s);
        visited.add(s);

        boolean found = false;

        while (!queue.isEmpty()) {
            s = queue.poll();

            if (isValid(s)) {
                // found an answer, add to the result
                res.add(s);
                System.out.println(s);
                found = true;
            }

            if (found) continue; //如果在某一层发现了匹配，则这一层的遍历就可以全部取消了

            // generate all possible states
            for (int i = 0; i < s.length(); i++) {
                // we only try to remove left or right paren
                if (s.charAt(i) != '(' && s.charAt(i) != ')') continue;

                String t = s.substring(0, i) + s.substring(i + 1);

                if (!visited.contains(t)) {
                    // for each state, if it's not visited, add it to the queue
                    queue.add(t);
                    visited.add(t);
                }
            }
        }

        return res;
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

    public List<String> removeInvalidParenthesesByDFS(String s) {
        List<String> ans = new ArrayList<>();
        remove(s, ans, 0, 0, new char[]{'(', ')'});
        return ans;
    }

    public void remove(String s, List<String> ans, int last_i, int last_j,  char[] par) {
        for (int stack = 0, i = last_i; i < s.length(); ++i) {
            if (s.charAt(i) == par[0]) stack++;
            if (s.charAt(i) == par[1]) stack--;
            if (stack >= 0) continue;
            for (int j = last_j; j <= i; ++j)
                if (s.charAt(j) == par[1] && (j == last_j || s.charAt(j - 1) != par[1]))
                    remove(s.substring(0, j) + s.substring(j + 1, s.length()), ans, i, j, par);
            return;
        } //如果当前的串(递归调用方式下，这有可能是递归过程中的某一个子串)
        String reversed = new StringBuilder(s).reverse().toString();
        if (par[0] == '(') //
            remove(reversed, ans, 0, 0, new char[]{')', '('});
        else // finished right to left
            ans.add(reversed);
    }


    public List<String> removeInvalidParentheses(String input){

        Set<String> result =new HashSet<String>();Set<String> visited =new HashSet<String>();
        int initialScore = getScore(input);
        Deque<InputAndScore> leverController = new LinkedBlockingDeque<InputAndScore>();
        leverController.addLast(new InputAndScore(input,initialScore));
        processAccordingToScore(input, result,visited,leverController);

        return new ArrayList(result);

    }

    private void processAccordingToScore(String input, Set<String> result,Set<String> visited, Deque<InputAndScore> levelQueue) {
        boolean found = false;
        while (!levelQueue.isEmpty()) {
            InputAndScore inputAndScore = levelQueue.pollFirst();
            if(inputAndScore.score==0 && inputAndScore.isValid())
             {
                    result.add(inputAndScore.input);
                    found = true;//只要本层有一个找到了，本层就取消
            }
            if (!found) { //如果当前还没有找到，就可以进行下一层的查找了
                if (inputAndScore.score > 0) {
                    for (int i = 0; i < inputAndScore.input.length(); i++) { //每一个for循环相当于广度优先遍历
                        if ( inputAndScore.input.charAt(i) == '(') { //remove a ')'
                            InputAndScore is = new InputAndScore(inputAndScore.input.substring(0, i) +  inputAndScore.input.substring(i + 1),inputAndScore.score-1);
                            if(!visited.contains(is.input)){
                                levelQueue.addLast( is);

                                visited.add(is.input);
                            }
                        }
                    }
                } else if(inputAndScore.score < 0) {
                    for (int j = 0; j <  inputAndScore.input.length(); j++) { //每一个for循环相当于广度优先遍历
                        if ( inputAndScore.input.charAt(j) == ')') { //remove a ')'
                            InputAndScore is = new InputAndScore(inputAndScore.input.substring(0, j) +  inputAndScore.input.substring(j + 1),inputAndScore.score+1);
                            if(!visited.contains(is.input)){
                                levelQueue.addLast( is);
                                visited.add(is.input);
                            }
                        }
                    }
                }
                else {
                    for (int j = 0; j <  inputAndScore.input.length(); j++) { //每一个for循环相当于广度优先遍历
                        InputAndScore is = new InputAndScore(inputAndScore.input.substring(0, j) +  inputAndScore.input.substring(j + 1),inputAndScore.input.charAt(j) == ')'?inputAndScore.score+1:inputAndScore.score-1);
                        if(!visited.contains(is.input)){
                            visited.add(is.input);
                            levelQueue.addLast( is);
                        }
                    }
                }
            }
        }
    }

    private static class InputAndScore{
        String input;
        int score;

        public InputAndScore(String input, int score) {
            this.input = input;
            this.score = score;
        }

       public boolean isValid() {
            int count = 0;

            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c == '(') count++;
                if (c == ')' && count-- == 0) return false;
            }

            return count == 0;
        }
    }


    public int getScore(String input){
        int score=0;
        for(int i=0;i<input.length();i++){
            if(input.charAt(i) == '(') score++;
            else if(input.charAt(i) == ')' ) score--;
        }
        return score;
    }

    public static void main(String[] args) {

        String input = "())()))";
        System.out.println(input.substring(1));
        String test = "abc";
        for(int i=0;i<test.length();i++)
        System.out.println(test.substring(0, i) + test.substring(i + 1));
        new RemoveInvalidParentheses().removeInvalidParenthesesByDFS(input);
    }
}
