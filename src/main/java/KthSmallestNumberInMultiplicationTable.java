/**
 * Question 668
 */
public class KthSmallestNumberInMultiplicationTable {
    public int findKthNumber(int m, int n, int k) {

        int min=1;int max = m*n;

        while(min < max){
            int mid = (min+max)/2;
            int count = lessThan(m,n,mid);
            if(count >= k)
                max = mid;
            else
                min = mid+1;
        }
        return min;
    }

    private int lessThan(int m,int n,int mid){

        int num = 0;
        for(int i=1,j=n;i<=m;i++){

            while(i * j > mid && j>0) j--;
            num += (j);
        }
        return num;
    }

    private int lessThan2(int m, int n,int v) {
        int count = 0;
        for (int i = 1; i <= m; i++) {
            int temp = Math.min(v / i , n);
            count += temp;
        }
        return count;
    }


    public static void main(String[] args) {

        new KthSmallestNumberInMultiplicationTable().findKthNumber(3,3,6);
    }
}
