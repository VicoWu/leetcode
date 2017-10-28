import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Question 315
 */
public class CountOfSmallerNumbersAfterSelf {

    public List<Integer> countSmaller(int[] nums) {

        LinkedList<Integer> result = new LinkedList<Integer>();
        for(int i=nums.length-1;i>=0;i--){
            result.addFirst(countSmallerRightBaseOnSortedRight(nums,i));
        }

        //result.addFirst(countSmallerRightBaseOnSortedRight1(nums,5));

        return result;
    }

    /**
     * 输入一个右侧已经按照从小到达排序的数组
     * @param sortedRight
     * @param start
     * @param end
     * @return
     */
    private int countSmallerRightBaseOnSortedRight(int[] sortedRight,int numIndex){

        int start = numIndex+1;int end = sortedRight.length-1;
        int cur = sortedRight[numIndex];
        int middle = (start+end)/2;
        while(start<=end){
            middle = (start+end)/2;

            if(cur < sortedRight[middle]){
                end = middle-1;
            }
            else if(cur > sortedRight[middle]){
                start = middle+1;
            }
            else{
                while(middle  > numIndex+1 && sortedRight[middle] == sortedRight[middle-1] ) middle--;

                for(int i=numIndex;i<middle-1;i++){
                    sortedRight[i]=sortedRight[i+1];
                }
                sortedRight[middle-1] = cur;
                return middle-1-numIndex;
            }
        }
        int i= numIndex;

        int position = sortedRight[middle]>cur?middle-1:middle;

        while (i < position && i + 1 < sortedRight.length) {
            sortedRight[i] = sortedRight[i + 1];
            i++;
        }


        sortedRight[i] = cur;
        return i-numIndex;

    }



    public static void main(String[] args) {

        Properties properties = new Properties();
//        try {
//            InputStream inputStream = new FileInputStream(new File("/Users/wuchang/leetcodedata"));
//            properties.load(inputStream);
//            String a = properties.getProperty("key");
//            String output = properties.getProperty("output");
//            String answer = properties.getProperty("answer");
//
//            String[] arrayStr = a.split(",");
//            String[] arrayOutput = output.split(",");
//            String[] arrayAnswer = answer.split(",");
//            for(int i=0;i<arrayAnswer.length;i++){
//                if(!arrayAnswer[i].equals(arrayOutput[i]))
//                    System.out.println("index "+i+" not equal.output is "+arrayOutput[i]+", answer is "+arrayAnswer[i]);
//            }
//            int[] input = new int[arrayStr.length];
//            for(int i=0;i<arrayStr.length;i++)
//                input[i] = Integer.valueOf(arrayStr[i]);
//
//            for(int i=0;i<input.length;i++)
//                if(input[i] == 737)
//                    System.out.println("found 737");
            int[] input = {-1,-1};

           List result =  new CountOfSmallerNumbersAfterSelf().countSmaller(input);

           System.out.println("");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



    }


}
