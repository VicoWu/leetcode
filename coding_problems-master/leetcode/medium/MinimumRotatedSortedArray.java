/**
 * Problem: A sorted array is rotated, find minimum element
 *
 * Solution: The first element such that arr[i-1]>arr[i] is the min element.
 */

public class MinimumRotatedSortedArray{
	public int findMin(int[] num){
		if(num==null || num.length==0){
			return -1;
		}
		int len = num.length;
		for(int i=0; i<len-1; i++){
			if(num[i]>num[i+1]){
				return num[i+1];
			}
		}
		return num[0];
	}
}