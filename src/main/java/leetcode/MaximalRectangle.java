package leetcode;

import java.util.ArrayList;

/**
 * Question 85
 * 剑指 Offer II 040. 矩阵中最大的矩形
 *
 ## 根据分层的方式，使用动态规划
 https://discuss.leetcode.com/topic/6650/share-my-dp-solution/20
 对于每一个节点[i,j]，其高度定义为它上面的1的数量，例如:
 ```
 1 0 1 0 0
 1 0 1 1 1
 1 1 1 1 1
 1 0 0 1 0
 ```
 第一层高度分别是1,0,1,0,0,第二层高度分别是2,0,2,1,1，第三次高度分别是3,1,3,2,2
 那么，每一个节点对应的矩形就定位为：以这个为高度并且包含这个节点的矩形。

 显然，题目中获取的最大矩形，会通过节点[2,3]和节点[2,4]获取到。它们的高度都是2，在此高度下的宽度都是3；
 那么，如何根据上一层每个点的高度、矩形左边界和矩形右边界，获得这一层的高度、矩形左边界和矩形右边界呢？对于节点[i,j]，它头顶上的节点是[i,j-1]，如果matrix[i][j]==0,则高度为0，否则，高度为matrix[i][j-1]+1。左边界，是取[i,j-1]计算得到的左边界和[i,j]最左侧的连续为1的节点的边界的靠右（较大）值，相反，右边界，是取[i,j-1]计算得到的右边界和[i,j]最右侧的连续为1的节点的边界的靠左（较小）值；
 注意，对于遇到`0` , 显然，面积是0，我们将它的高度设置为0就行，那么左边界和右边界呢，我们设置左边界为0，右边界为n，这样，下一层节点的左边界就直接由下一层节点决定，而不受本层的0的影响，右边界的原理也是如此。


 ## 通过Largest Rectangle in Histogram问题衍生
 https://discuss.leetcode.com/topic/1634/a-o-n-2-solution-based-on-largest-rectangle-in-histogram
 Largest Rectangle in Histogram问题通过栈的方式，计算最大矩形。本问题与之相似，只是，我们需要从第一层开始逐层往下计算。比如，我们先计算第一层matrix[i,0]的最大矩形，同时，我们还需要记录这一层每个节点的高度，然后，到每一层，我们都通过上一层的高度和这一层的节点值计算这一层的每一列的高度，然后同样通过栈的方式计算最大矩形，一直计算到matrix的最底层，获取最大矩形面积；

 ## 我的错误解法
 看我的方法`maximalRectangle`
 我本以为可以通过动态规划的方式，一个节点a[i,j]所形成的最大矩形(即以该节点为右下角节点)的矩形，它的面积可以从其左侧节点、上侧节点和左上侧节点的信息推导出来，这些信息包括：`data[2]最大面积的左部坐标，data[3]最大面积的上部坐标，data[4]最左侧连续为1的最左侧坐标 ,data[5]最上侧连续为1的最上侧坐标`，但是后来发现其实无法实现，某个节点形成的矩形的最大面积不可以从：1.以这个节点为最下方节点、宽度为1的矩形、2.以这个节点为最右方节点、高度为1的矩形、3.包含左上方节点、宽度是左上方节点的宽度和自己左侧连续为1的宽度的较小值，高度是左上方节点的高度和自己左侧连续高度为1的高度的较小值，但是后来发现不对：
 ```
 0 0 0 0 1 1 1 0 1
 0 0 1 1 1 1 1 0 1
 0 0 0 1 1 1 1 1 0
 ```
 比如，计算matrix[2][6]的时候，通过这种方式计算得到的是8，但是其实最大矩形面积是9，这是因为matrix[1,5]形成了两个面积相同的矩形，从两个矩形推倒matrix[2][6]有8和9两个结果。
 */
public class MaximalRectangle {
    NodeInfo[] previousRoundData ;
    NodeInfo[] currentRoundData;
    int maxArea = 0;
    public int maximalRectangle(char[][] matrix) {

        int height = matrix.length;
        if(height==0)
            return 0;
        int width = matrix[0].length;

        previousRoundData =  new NodeInfo[height+width+1];
        int roundNum = height>width?width:height;
        for(int round=0;round<roundNum;round++){
            currentRoundData = new NodeInfo[height+width+1];
            NodeInfo center = computeNodeInfo(matrix,round,round,previousRoundData[1+height],previousRoundData[round-round+height],previousRoundData[height-1],height);
            NodeInfo myUp = center;
            for(int i=round+1;i<height;i++){ //保持j的值为round不变，依次往下求值,因此，当前坐标为[i,round]
                try {
                    myUp = computeNodeInfo(matrix, i, round, myUp, previousRoundData[round - i + height], round == 0 ? null : previousRoundData[round - 1 - i + height], height);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            NodeInfo myLeft = center;
            for(int j=round+1;j<width;j++){//保持i的值为round不变，依次往右求值,因此，当前坐标为[round,j]
                myLeft =  computeNodeInfo(matrix,round,j,round==0?null:previousRoundData[j-(round-1)+height],previousRoundData[j-(round)+height],myLeft,height);

            }
            previousRoundData = currentRoundData;
        }
        return maxArea;
    }

    NodeInfo computeNodeInfo(char[][] matrix,int cI,int cJ,NodeInfo up,NodeInfo upLeft,NodeInfo left,int height){

        int currentMaxArea = 0;
        if(matrix[cI][cJ] == '0') //如果这个点是0，不用计算
        {
            int[] data = {cI,cJ,-1,-1,-1,-1};
            currentRoundData[cJ-cI+height] = new NodeInfo(data);
            return currentRoundData[cJ-cI+height];
        }
        //这个点的数据是1，则根据左侧、上侧和左上侧的节点值进行计算
        int[] data = new int[6];
        data[0]=cI;
        data[1]=cJ;
        data[4] = (left==null||left.data[4]==-1)
                ?cJ
                :left.data[4];
        data[5] = (up==null||up.data[5]==-1)
                ?cI
                :up.data[5];
        int upArea =cI-data[5]+1;//上侧宽度为1的面积
        int leftArea=cJ-data[4]+1;//左侧高度为1的面积
        if(upArea>leftArea){
            data[2] = cJ;
            data[3] = data[5];
            currentMaxArea = upArea;
        }else{
            data[2] = data[4];
            data[3] = cI;
            currentMaxArea = leftArea;
        }
        if(upLeft!=null && upLeft.data[5]!=-1){
            int maxLeftUpUp= data[5]<upLeft.data[3] ?upLeft.data[3] :data[5];
            int maxLeftUpLeft=data[4] < upLeft.data[2]?upLeft.data[2]:data[4];
            int maxAreaIncludingLeftUpNode = (cI-maxLeftUpUp+1)*(cJ-maxLeftUpLeft+1);
            if(maxAreaIncludingLeftUpNode > currentMaxArea){
                data[2] = maxLeftUpLeft;
                data[3] = maxLeftUpUp;
                currentMaxArea = maxAreaIncludingLeftUpNode;

            }
        }

        if(currentMaxArea > maxArea) maxArea = currentMaxArea;
        NodeInfo ni = new NodeInfo(data);
        currentRoundData[cJ-cI+height] = ni;
        return ni;
    }



    public static class NodeInfo{
        int[] data; //data[0]横坐标，data[1]纵坐标，data[2]最大面积的左部坐标，data[3]最大面积的上部坐标，data[4]最左侧连续为1的最左侧坐标 ,data[5]最上侧连续为1的最上侧坐标

        public NodeInfo(int[] data) {
            this.data = data;
        }


    }


    /**
     * 这是别人的通过动态规划的方式实现的
     * https://discuss.leetcode.com/topic/6650/share-my-dp-solution/20
     对于每一个节点[i,j]，其高度定义为它上面的1的数量，例如:
     ```
     1 0 1 0 0
     1 0 1 1 1
     1 1 1 1 1
     1 0 0 1 0
     ```
     第一层高度分别是1,0,1,0,0,第二层高度分别是2,0,2,1,1，第三次高度分别是3,1,3,2,2
     那么，每一个节点对应的矩形就定位为：以这个为高度并且包含这个节点的矩形。

     显然，题目中获取的最大矩形，会通过节点[2,3]和节点[2,4]获取到。它们的高度都是2，在此高度下的宽度都是3；
     那么，如何根据上一层每个点的高度、矩形左边界和矩形右边界，或者这一层的高度、矩形左边界和矩形右边界呢？对于节点[i,j]，
     它头顶上的节点是[i,j-1]，如果matrix[i][j]==0,则高度为0，否则，高度为matrix[i][j-1]+1。左边界，是取[i,j-1]计算得到的左边界和[i,j]最
     左侧的连续为1的节点的边界的靠右（较大）值，相反，右边界，是取[i,j-1]计算得到的右边界和[i,j]最右侧的连续为1的节点的边界的靠左（较小）值；

     注意，对于遇到`0` , 显然，面积是0，我们将它的高度设置为0就行，那么左边界和有边界呢，我们设置左边界为0，右边界为n，这样，下一层节点的左边界就直接由下一层
     节点决定，而不受本层的0的影响，右边界的原理也是如此。
     * @param matrix
     * @return
     */
    public int maximalRectangle1(char[][] matrix) {
        if (matrix.length == 0) return 0;
        int m = matrix.length;
        int n = matrix[0].length;
        int[] left = new int[n];
        int[] right = new int[n];
        int[] height = new int[n];
        for (int i = 0; i < n; i++) {
            left[i] = 0;
            right[i] = n;
            height[i] = 0;
        }
        int maxA = 0;
        for (int i = 0; i < m; i++) {
            int cur_left = 0, cur_right = n;
            for (int j = 0; j < n; j++) { // compute height (can do this from either side)
                if (matrix[i][j] == '1')
                    height[j]++;//如果如果这个字符是'1'，那么高度等于上一层的高度+1
                else
                    height[j] = 0; ////如果这个字符是'0',那么高度直接置为0，从而，凡是'0'字符，它对应的面积直接就是0了
            }
            for (int j = 0; j < n; j++) { // compute left (from left to right)
                if (matrix[i][j] == '1')
                    left[j] = Math.max(left[j], cur_left);
                else {
                    left[j] = 0;//如果这个字符是'0',则left值直接置为0，这样，这个值对下一行的数据的left值不会发生影响
                    cur_left = j + 1;
                }
            }
            // compute right (from right to left)
            for (int j = n - 1; j >= 0; j--) {
                if (matrix[i][j] == '1') //如果这个字符是'1'，则需要考虑上一行的right值和自己当前的right值，取较小的值
                    right[j] = Math.min(right[j], cur_right);
                else {
                    right[j] = n;//如果这个字符是'0'，则right设置为n，因为它对下一行对right值没有影响
                    cur_right = j;
                }
            }
            // compute the area of rectangle (can do this from either side)
            for (int j = 0; j < n; j++)
                maxA = Math.max(maxA, (right[j] - left[j]) * height[j]);
        }
        return maxA;
    }

    public int maximalRectangle(String[] matrix) {
        ArrayList<Integer[]> cand = new ArrayList<Integer[]>();
        int maxArea = Integer.MIN_VALUE;
        for(int i=0;i<matrix.length;i++){
            String row = matrix[i];
            Integer[] initial = new Integer[2];
            initial[0] = 0; initial[1] = row.length()-1;
            cand.add(initial);
            for(int j = i;j<matrix.length;j++){
                cand = findCandidate(matrix[j], cand);
                maxArea = Math.max(maxArea, computeMaxArea(cand, j - i + 1)) ;
            }
        }
        return maxArea;
    }

    private ArrayList<Integer[]> findCandidate(String row, ArrayList<Integer[]> pre){
        ArrayList<Integer[]> cand = new ArrayList<Integer[]>();
        for(int i = 0; i<pre.size(); i++){
            Integer[] range = pre.get(i);
            int start = range[0];
            int end = start;
            while(start <= range[1]){
                while(start <= range[1] && row.charAt(start) == '0'){
                    start++;
                }
                end = start;
                while(end <= range[1] && row.charAt(end) == '1'){
                    end++;
                }
                end--;
                if(end >= start){
                    Integer[] r ={start, end};
                    cand.add(r);
                }
                start=end+1;
            }
        }
        return cand;
    }

    private int computeMaxArea(ArrayList<Integer[]> cands, int height){
        int max = Integer.MIN_VALUE;
        for(int i = 0;i<cands.size();i++){
            Integer[] cand = cands.get(i);
            max = Math.max(max, (cand[1] - cand[0] + 1) * height);
        }
        return max;
    }
    public static void main(String[] args) {

        //char[][] matrix = {{'0','0','1','0'},{'1','1','1','1'},{'1','1','1','1'},{'1','1','1','0'},{'1','1','0','0'},{'1','1','1','1'},{'1','1','1','0'}};  //}
        char[][] matrix = {{'1','0','1','1','1'},{'1','1','1','1','1'},{'1','0','0','1','0'}};
       // char[][] matrix = {{'0','1'},{'0','1'}};
        //char[][] matrix = {{'0','0','0','0','1','1','1','0','1'},{'0','0','1','1','1','1','1','0','1'},{'0','0','0','1','1','1','1','1','0'}};
        String[] m = {"10111","11111","10010"};
        System.out.println(new MaximalRectangle().maximalRectangle(m)); ;
    }

}
