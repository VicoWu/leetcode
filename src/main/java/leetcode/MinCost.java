package leetcode;

public class MinCost {
    public int minCost(int[][] costs) {
        int[] preCost = new int[3];
        int[] curCost = new int[3];
        for(int i = 0;i<costs[0].length;i++){
            preCost[i] = costs[0][i];
        }

        for(int i = 1;i<costs.length;i++){
            for(int j = 0;j<3;j++){
                curCost[j] = costs[i][j] + Math.min(preCost[(j+1)%3], preCost[(j+2)%3]);
            }
            preCost = curCost;
            curCost = new int[3];
        }
        int finalCost = Integer.MAX_VALUE;
        for(int i = 0;i<preCost.length;i++){
            finalCost = Math.min(finalCost, preCost[i]);
        }
        return finalCost;
    }

    public static void main(String[] args) {
        int[][] input = {{3,5,3},{6,17,6},{7,13,18},{9,10,18}};
        new MinCost().minCost(input);
    }
}
