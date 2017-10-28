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

    public static void main(String[] args) {

        int[] height = {0,5,0,5,1,0,1,3,2,1,2,1};
        System.out.println(new TrappingRainWater().trap2(height));
    }
}
