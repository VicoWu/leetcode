package leetcode;

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

    public static void main(String[] args){
       System.out.println(new Zigzag().convert("abc",4)) ;
    }
}
