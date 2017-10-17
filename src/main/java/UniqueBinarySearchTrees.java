import java.util.HashMap;
import java.util.Map;

/**
 * Question 96
 */

public class UniqueBinarySearchTrees {

    private static Map<Integer,Integer> intermediateResult = new HashMap<Integer,Integer>();
    public int numTrees(int n) {

        return computeBinarySearchTree(n);
    }

    private int computeBinarySearchTree(int n){
        if(n<=1) return 1;
        if(intermediateResult.containsKey(n)) return intermediateResult.get(n);
        int result = 0;
        for(int i=0;i<=n-1;i++){
            int left = i;int right = n-1-i;
            result += (computeBinarySearchTree(left) * computeBinarySearchTree(right) );
            intermediateResult.put(n,result);
        }
        return result;
    }


    public static void main(String[] args){
        System.out.println(new UniqueBinarySearchTrees().computeBinarySearchTree(3));
    }
}
