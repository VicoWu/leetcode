/**
 * Question 11
 */
public class ContainerWithMostWater {
    public int maxArea(int[] height) {

        int i=0;
        int j=height.length-1;
        int l = i;
        int r = j;
        int maxArea = 0;
        while(l<r){
            maxArea = Math.max(maxArea,(r-l) * Math.min(height[l] , height[r]));
            if(height[l] < height[r])
               l=l+1;
            else
               r=r-1;
        }
        return maxArea;
    }
}
