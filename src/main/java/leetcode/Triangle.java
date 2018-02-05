package leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuchang at 1/8/18
 Question 120
采用从下向上的方式，逐个计算每个位置和它下方形成的最小的和，第i层每个位置的最小和计算完毕以后，它的上层就可以直接依赖这个最小和继续计算了，最终，我们计算到了最上层的最小和。
 ```
 [
 [2],
 [3,4],
 [6,5,7],
 [4,1,8,3]
 ]
 假定下层有一层虚拟节点：
 [
 [2],  //最小和[11]
 [3,4], //最小和[9,10]
 [6,5,7], //最小和[7,6,10]
 [4,1,8,3], //最小和[4,1,8,3]
 [0,0,0,0,0]
 ]
 从下网上每层逐渐计算最小和
 ```

 */

public class Triangle {

    /**
     * 采用从下向上的方式，逐个计算每个位置和它下方形成的最小的和，第i层每个位置的最小和计算完毕以后，它的上层就可以直接依赖这个最小和继续计算了，最终，我们计算到了最上层的最小和。
     *
     * @param triangle
     * @return
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        int[] res = new int[triangle.size()+1]; //创建一个数组，存放初始结果为0

        Arrays.fill(res,0);
        for(int i=triangle.size()-1;i>=0;i--){
            List<Integer> row = triangle.get(i);
            for(int j=0;j<row.size();j++){
                res[j] = row.get(j) + Math.min(res[j],res[j+1]); //
            }
        }
       return res[0];
    }

//    public int minimumTotal( Iterator<List<Integer>> ite,int preCol) {


//        if(!ite.hasNext())
//            return 0;
//        List<Integer> row =  ite.next();
//        int left = row.get(preCol);int right = row.get(preCol+1);
//        Iterator<Integer> colIte = row.iterator();
//        while(colIte.hasNext()){
//            minimumTotal(ite,);
//        }
//            int leftValue = minimumTotal(ite,preCol );
//            int rightValue = minimumTotal(ite,preCol+1);
//            return right + leftValue<rightValue?leftValue:rightValue;
//    }

    public static void main(String[] args) {

        Integer[] a = {2};
        Integer[] a1 = {3,4};
        Integer[] a2 = {6,5,7};
        Integer[] a3 = {4,1,8,3};
        List<List<Integer>> triangle = new LinkedList<>();
        triangle.add(Arrays.asList(a));
        triangle.add(Arrays.asList(a1));
        triangle.add(Arrays.asList(a2));
        triangle.add(Arrays.asList(a3));
        System.out.println(new Triangle().minimumTotal(triangle));
    }
}
