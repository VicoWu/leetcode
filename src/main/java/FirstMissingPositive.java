/**
 * Created by wuchang at 12/16/17
 */

public class FirstMissingPositive {
    int firstMissingPositive(int A[])
    {
        int n = A.length;
        for(int i = 0; i < n; ++ i){
            while(A[i] > 0
                    && A[i] <= n
                    && A[A[i] - 1] != A[i]){
                int tmp = A[i];
                A[i] = A[tmp-1];
                A[tmp-1] = tmp;

            }
            printArray(A);
        }


        for(int i = 0; i < n; ++ i)
            if(A[i] != i + 1)
                return i + 1;

        return n + 1;
    }


    public static void main(String[] args) {
        int[] result = {2,1,4};
       System.out.println(new FirstMissingPositive().firstMissingPositive(result)) ;
      //  System.out.println(new FirstMissingPositive().firstMissingPositive1(result)) ;
    }

//    int firstMissingPositive1(int A[]){
//
//        int min=Integer.MAX_VALUE ;int max = -1;
//        int sum = 0;
//        for(int i=0;i<A.length;i++){
//            if(A[i] > 0)
//            {
//                if(A[i] < min) min = A[i];
//                if(A[i] > max) max = A[i];
//                sum+=A[i];
//            }
//        }
//        if(min != 1) return 1;
//
//        int expected = expectedSum(max);
//        if(expected == sum) return max+1;
//        else return expected - sum;
//
//    }
//
//    private int expectedSum(int end){
//        return (1+end)* (end) /2;
//    }

    private void printArray(int[] input)
    {
        String s = "";
        for(int i=0;i<input.length;i++){
            s=s+ input[i] +" ";
        }
        System.out.println(s);
    }
}
