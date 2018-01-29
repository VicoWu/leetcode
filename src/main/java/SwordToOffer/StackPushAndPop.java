package SwordToOffer;

import java.util.Stack;

/**
 * Created by wuchang at 1/26/18
 * 这是剑指offer的栈的弹入弹出题目
 */

public class StackPushAndPop {
    public static Boolean IsPopOrder(int[] pushOrder, int[] popOrder )
    {
        Boolean possible = false;

        if (pushOrder != null && popOrder != null && pushOrder.length > 0)
        {
            int nextPush = 0; // 指向下一个要push的元素的index
            int nextPop = 0;  // 指向下一个要pop的元素的index
            int pop = 0;      // 指向popOrder的首个元素的index
            int push = 0;     // 指向pushOrder的首个元素的index

            Stack<Integer> stackData = new Stack<Integer>();
            while (nextPop - pop < pushOrder.length )
            {
                // 当辅助栈的栈顶元素不是要弹出的元素
                // 先压入一些数字入栈
                while (stackData.size() == 0 || stackData.peek() != popOrder[nextPop])
                {
                    // 如果所有数字都压入辅助栈了，退出循环
                    if (nextPush - push == pushOrder.length )
                    {
                        break;
                    }

                    stackData.push(pushOrder[nextPush]);
                    nextPush++;
                }

                // 说明没有匹配成功
                if (stackData.peek() != popOrder[nextPop])
                {
                    break;
                }

                stackData.pop();
                nextPop++;
            }

            if (stackData.size() == 0 && nextPop - pop == pushOrder.length )
            {
                possible = true;
            }
        }

        return possible;
    }

    public static void main(String[] args) {
        int length = 5;
        int[] push = { 1, 2, 3, 4, 5 };
        int[] pop = { 4, 5, 3, 2, 1 };
        IsPopOrder(push,pop);

    }

}
