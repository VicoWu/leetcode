package leetcode;

/**
 * Question 6
 */
public class Zigzag {
    public String convert(String s, int numRows) {

        if(numRows == 1)
            return s;
        char[] result = new char[s.length()];
        int length = s.length();
        int jump = (numRows-1)<<  1 ;
        int lastIndexNum = 0; //记录当前处在输入序列的索引位置
        for(int i = 0,k=0;i<s.length();i=i+jump){//将zigzag 的第一行数据放入指定位置
            result[k++] = s.charAt(i);
            lastIndexNum++;

        }


        int bottomNum = (1 + (length-numRows)/(2*numRows-2));
        int bottomStart = length - bottomNum;

        for(int i = numRows-1,k=bottomStart;i<s.length();i=i+jump){ ////将zigzag 的最后一行数据放入指定位置
            result[k++] = s.charAt(i);
        }


        //开始计算中间数据

        int indexInRow = lastIndexNum;
        for(int row=1;row < numRows-1; row++){ //对于ZigZag 每一行 (除去第一行和最后一行)
            int slice1 = jump - (row << 1);

            int i = row;

            while(i < length){

               result[indexInRow++] = s.charAt(i);

               if(i+slice1 < length)
                   result[indexInRow++] = s.charAt(i+slice1);
               i=i+jump;
            }
        }

        return new String(result);

    }

    public String convert3(String s, int numRows) {
        // 下面的代码在行数为1的时候无法处理，因此做特殊处理
        if(numRows == 1){
            return s;
        }
        StringBuilder sb = new StringBuilder();
        // 将zig-zag分成一个一个的block，每个block的元素数量是blockSize
        int blockSize = 2 * numRows - 2;
        int blockNum = (s.length()-1) / blockSize + 1; // 最多有多少个block，限制循环次数
        for(int row = 0; row < numRows; row++){ // 一行一行处理
            for(int blockIndex = 0; blockIndex < blockNum; blockIndex++){
                if(row == 0 || row == numRows - 1){ // 第一行或者最后一行，一个block里面只有一个元素
                    int location = blockIndex * blockSize + row;
                    if(location < s.length()){
                        sb.append(s.charAt(location));
                    }
                }else{ // 既不是第一行也不是最后一行，每一行、每一个block里面有两个元素
                    int location = blockIndex * blockSize + row;
                    if(location < s.length()){
                        sb.append(s.charAt(blockIndex * blockSize + row));
                    }
                    int nextLocation = blockIndex * blockSize + 2 * (numRows - 1) - row;
                    if(nextLocation < s.length()){
                        sb.append(s.charAt(nextLocation));
                    }
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args){
        System.out.println((-124/10) + ",  " + (-123%10));
       System.out.println(new Zigzag().convert("abc",4)) ;
    }
}
