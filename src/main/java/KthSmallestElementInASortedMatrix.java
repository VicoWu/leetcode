/**
 * Question 378
 */
public class KthSmallestElementInASortedMatrix {

    public int kthSmallest(int[][] matrix, int k) {

        int min=matrix[0][0];int max = matrix[matrix.length-1][matrix[0].length-1];

        while(min < max){
            int mid = (min+max)/2;
            int count = lessThan(matrix,mid);
            if(count >= k)
                max = mid;
            else
                min = mid+1;
        }
        return min;
    }

    private int lessThan(int[][] matrix,int mid){

        int num = 0;
        for(int i=1,j=matrix[0].length;i<=matrix.length;i++){
            while(j> 0 && matrix[i-1][j-1] > mid ) j--;
            num += (j);
        }
        return num;
    }

    public static void main(String[] args) {
        int[][] matrix = {{-5}};
        new KthSmallestElementInASortedMatrix().kthSmallest(matrix,1);
    }

}
