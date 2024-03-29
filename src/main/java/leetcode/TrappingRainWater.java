package leetcode;

import java.util.Stack;

/**
 * Question 42
 * trap()的实现思路：
 这是我自己的实现思路。从左向右逐渐移动位置，如果发现`height[i]<height[i-1]`,说明是一次下沉，如果发现`height[i]<height[i-1]`,
 说明是一次上升。下沉和上升配对就形成了储水空间。但是如图所示的`[3,7]` 的位置形成的储水空间该怎么计算？通过迭代的方式，每次遇见下沉就将
 下沉位置入栈，遇到上升就把最近的下沉记录出栈然后和这次上升配对计算储水空间。计算完储水空间，需要把对应的水空间填充，然后迭代计算外侧的储水空间。


 trap2的实现思路：
 从两边往中间，对于每一个宽度为1的方格，直接就计算这个方格最终的存放水的量。比如，如图中，i=2,存放1格水，i=4, 存放1格水，i=5，
 存放2格水。对于每一个位置，最终存放多少水呢？显然，一个方格的存水高度，等于这个方格的左侧(不一定相邻)的最大高度和右侧的最大高度(不一定相邻)中的较小值。
 两个指针i和j分别从两侧向中间汇聚，但并不是同时向中间汇聚，而是每次都将较小的一侧向中间汇聚，这就相当于：一个水槽，较高的一侧固定不动，较低的一侧不断升高，
 但是只要升高的过程中较低的一侧始终低于固定的、较高的一侧，那么这个储水量就由较低的一侧决定。


 https://leetcode.cn/problems/trapping-rain-water/solutions/9112/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-w-8/

 */
public class TrappingRainWater {
    public int trap(int[] height) {

        int moreWater = 0; Stack<Integer> sinkLocationStack = new Stack<Integer>(); int bottomHeight =-1; //preHeight = height[0];
        int startIndex = 0;
        //for(;startIndex<height.length && height[startIndex] <= 0;startIndex++);
        for (int i = startIndex; i < height.length; i++) {
            int ch = height[i]; //当前的高度

            if (i>0 && ch > height[i-1]) { //记录并处理一次上升
                if (!sinkLocationStack.isEmpty()) //如果之前有过下沉
                {


                    int lastSinkLocation = sinkLocationStack.pop();

                    int waterHeight = Math.min(height[lastSinkLocation] - bottomHeight, ch - bottomHeight);

                    int water = (waterHeight * (i - 1 - lastSinkLocation));
                    for (int j = lastSinkLocation+1; j < i; j++) {
                        height[j] = height[j] + waterHeight;
                    }
                    moreWater += water;
                    bottomHeight = height[lastSinkLocation+1];
                    i= !sinkLocationStack.isEmpty()?sinkLocationStack.peek()+1:startIndex;
                }
            }
            else if(i>0&& height[i] < height[i-1]){ //使用栈记录一次下沉
                sinkLocationStack.push(i-1);
                bottomHeight = ch;
            }
        }

        return moreWater;
    }


    public int trap2(int[] height){
        int left=0; int right=height.length-1;
        int res=0;
        int maxleft=0, maxright=0;
        while(left<=right){
            if(height[left]<=height[right]){
                if(height[left]>=maxleft)
                    maxleft=height[left];
                else
                    res+=maxleft-height[left];
                left++;
            }
            else{
                if(height[right]>=maxright)
                    maxright= height[right];
                else
                    res+=maxright-height[right];
                right--;
            }
        }
        return res;

    }


    /**
     * 某个位置的
     */
    class Solution {
        public int trap(int[] height) {
            int[] leftWall = new int[height.length];
            int[] rightWall = new int[height.length];
            leftWall[0]=0;
            rightWall[height.length-1]=0;
            int currentLeftWallHeight = 0;
            int currentRightWallHeight = 0;
            for(int i=0;i<height.length;i++){
                //如果当前高度小于左侧墙壁的高度，那么左侧墙壁就是该位置的左侧墙壁
                if(height[i] < currentLeftWallHeight){
                    leftWall[i] = currentLeftWallHeight;
                }
                else {
                    //如果左侧墙壁比当前位置还矮，那么当前位置无法蓄水
                    leftWall[i] = 0;
                    currentLeftWallHeight = height[i]; //同时更新左侧墙壁的高度值，意味着该位置右边的位置以该位置为墙壁
                }
                // 如果当前高度小于当前的右侧墙壁高度（右侧墙壁高度已经在前面的某个位置计算好了），那么更新该位置的右侧墙壁高度
                if(height[i] < currentRightWallHeight){
                    rightWall[i] = currentRightWallHeight;
                }
                else{
                    // 我们需要计算该位置的右侧墙壁高度
                    currentRightWallHeight = 0;
                    for(int j = i+1; j<height.length; j++){
                        // 这个高度比我还高，因此可以作为我右侧的wall
                        if(height[j] > height[i]){ //只要右侧有比我高的，那么就可以作为墙壁
                            currentRightWallHeight = Math.max(height[j], currentRightWallHeight);
                        }
                    }
                    rightWall[i] = currentRightWallHeight;
                }
            }
            int volume = 0;
            // 开始统计总的蓄水量
            // 累加每一个位置的蓄水量
            // 每一个位置的蓄水量取决于左侧高度和右侧高度的较小值
            for(int i = 0; i< height.length; i++){
                if(leftWall[i] > height[i] && rightWall[i] > height[i]){
                    volume = volume + Math.min(leftWall[i], rightWall[i]) - height[i];
                }
            }
            return volume;
        }
    }


    /**
     *     作者：windliang
     *     链接：https://leetcode.cn/problems/trapping-rain-water/solutions/9112/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-w-8/
     *     来源：力扣（LeetCode）
     *     著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param height
     * @return
     */
    public int trap6(int[] height) {
        int sum = 0;
        Stack<Integer> stack = new Stack<>();
        int current = 0;
        while (current < height.length) {
            //如果栈不空并且当前指向的高度大于栈顶高度就一直循环
            while (!stack.empty() && height[current] > height[stack.peek()]) {
                int h = height[stack.peek()]; //取出要出栈的元素
                stack.pop(); //出栈
                if (stack.empty()) { // 栈空就出去
                    break;
                }
                int distance = current - stack.peek() - 1; //两堵墙之前的距离。
                int min = Math.min(height[stack.peek()], height[current]);
                sum = sum + distance * (min - h);
            }
            stack.push(current); //当前指向的墙入栈
            current++; //指针后移
        }
        return sum;
    }

    public int trap3(int[] height) {
        Stack<Integer> decStack = new Stack<Integer>();
        decStack.push(0);
        int sum = 0;
        for(int i = 1;i<height.length;){
            while(i < height.length && !decStack.isEmpty() && height[i] < decStack.peek()){
                decStack.push(i);
                i++;
            }
            while(i < height.length && height[i] == decStack.peek()){
                i++;
            }
            while(i < height.length && !decStack.isEmpty() && height[i] > decStack.peek()){
                int bottom = decStack.pop();
                if(decStack.isEmpty()){
                    continue;
                }
                int leftWall = decStack.peek();
                int rightWall = i;
                int depth = Math.min(height[leftWall], height[rightWall]) - height[bottom];
                sum = sum + (rightWall - leftWall - 1) * depth;
            }
            decStack.push(i++);
        }
        return sum;
    }



    public static void main(String[] args) {

        int[] height = {3,1,1,3};
        System.out.println(new TrappingRainWater().trap3(height));
    }
}
