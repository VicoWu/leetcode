package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Question 149
 *
 * 由于需要找到含有点最多的一条直线，显然，n个节点形成的`(n-1)n/2`个点对都必须进行处理，因此时间复杂度至少是`O(n^2)`

 比较直观的想法，对于每一个节点，计算它与其它节点的斜率(slope)，即(y1-y0)/(x1-x0)，用map保存该点所在点斜率点直线上的点的数量。比如，
 对于`{p0,p1,p2,p3,p4}`，对于p0，我们计算`(p0,p1),(p0,p2),(p0,p3),(p0,p4)`的斜率，然后计算一个最大值，对于p1,计算`(p1,p2),(p2,p3),(p3,p4)`,以此类推；

 但是需要解决以下问题：
 ##  精度丢失问题
 通过点的斜率需要考虑斜率无限大点特殊情况。同时，还会有问题，就是浮点计算的精度丢失，即，点对`(A,B)`和`(A,C)`本来不在一条直线上，但是由于精度丢失，其斜率相同。
 比如：`A(0,0), B(94911151,94911150), C(94911152,94911151)` ，我们计算斜率AB和AC由于精度丢失，都相同了，即`(94911150-0)/(94911151-0) == (94911151-0)/(94911152-0) == 9999999894638303`，因此，不可以用斜率。
 我们使用双重map：`HashMap<Integer,HashMap<Integer,Integer>> slopeResult`，上层点key是横坐标的差值，下层的key是纵坐标点差值。这样有会有一个问题：
 比如：`A(0,0)，B(1,1)，C(-1,-1)`，显然，此时直线AB的横纵坐标差值是`(1,1)`，而AC之间横纵坐标之间的差值是`(-1,-1)`，但是其实他们在一条直线上。
 再比如,`A(0,0)，B(2,4)，C(8,16)`，显然，此时直线AB的横纵坐标差值是`(2,4)`，而AC之间横纵坐标之间的差值是`(8,16)`，但是其实他们在一条直线上。

 因此，我们需要计算最大公约数，然后将他们最简化。比如`(1,1)`最大公约数是1，通过最大公约数约减以后是`(1,1)`，`(-1,-1)`的最大公约数是-1，最大公约数约减以后是`(1,1)`，现在相同了。
 对于`(2,4)`和`(8,16)`，他们经过最大公约数约减以后都是`(1,2)`，**相同**了。

 ## 点重合问题
 另外一个需要注意点问题，就是点的重合问题。我们使用变量overlap记录与当前点重合的点点数量，然后，在同一条直线上点的个数需要加上overlap的数量。




 */


class Point {
    int x;
    int y;

    Point() {
        x = 0;
        y = 0;
    }

    Point(int a, int b) {
        x = a;
        y = b;
    }
}


public class MaxPointsOnALine {

    public int maxPoints1(Point[] points) {
        if (points==null) return 0;
        if (points.length<=2) return points.length;

        Map<Integer,Map<Integer,Integer>> map = new HashMap<Integer,Map<Integer,Integer>>();
        int result=0;
        for (int i=0;i<points.length;i++){
            map.clear();
            int overlap=0,max=0;
            for (int j=i+1;j<points.length;j++){
                int x=points[j].x-points[i].x;
                int y=points[j].y-points[i].y;
                if (x==0&&y==0){
                    overlap++;
                    continue;
                }
                int gcd=generateGCD(x,y);
                if (gcd!=0){
                    x/=gcd;
                    y/=gcd;
                }

                if (map.containsKey(x)){
                    if (map.get(x).containsKey(y)){
                        map.get(x).put(y, map.get(x).get(y)+1);
                    }else{
                        map.get(x).put(y, 1);
                    }
                }else{
                    Map<Integer,Integer> m = new HashMap<Integer,Integer>();
                    m.put(y, 1);
                    map.put(x, m);
                }
                max=Math.max(max, map.get(x).get(y));
            }
            result=Math.max(result, max+overlap+1);
        }
        return result;
    }


    public int maxPoints(Point[] points){

        int max = 0;
        for(int i=0;i<points.length;i++){
            int thisRoundMax = 1;
            Point pointA = points[i];
            HashMap<Integer,HashMap<Integer,Integer>> slopeResult = new HashMap<Integer,HashMap<Integer,Integer>>();
            int current=1;
            int overlap= 0;
            for(int j=i+1;j<points.length;j++){
                Point pointB = points[j];
                int xRate = pointB.x-pointA.x;
                int yRate = pointB.y-pointA.y;
                int gcd = generateGCD(xRate,yRate);
                if(gcd!=0)
                {
                    xRate/=gcd;
                    yRate/=gcd;
                }
                if(xRate== 0 && yRate == 0)
                    overlap++;
                else
                {
                    HashMap<Integer,Integer> yRateMap = null;
                    if(!slopeResult.containsKey(xRate))
                    {
                        yRateMap = new HashMap<Integer,Integer>();
                        slopeResult.put(xRate,yRateMap);
                    }
                    else
                        yRateMap = slopeResult.get(xRate);
                    if(yRateMap.containsKey(yRate))
                        current = yRateMap.get(yRate)+1;
                    else
                        current =2;
                    yRateMap.put(yRate,current);
                }
                if(current > thisRoundMax)
                    thisRoundMax = current;
            }
            thisRoundMax = thisRoundMax + overlap;
            max = thisRoundMax>max?thisRoundMax:max;
            slopeResult.clear();
        }
        return max;
    }


    private int generateGCD(int a,int b){

        if (b==0) return a;
        else return generateGCD(b,a%b);

    }

    private int[] generateGCD2(int a,int b){

        int gcd = generateGCD(a,b);
        if(gcd!=0)
        {
            a/=gcd;
            b/=gcd;
        }
        int[] result = {a,b};
        return result;

    }

    /**
     *
     *     如果被除数为正数，那么取余的结果也为正数。例如，5 % 3 的结果是 2，因为 5 除以 3 余 2。
     *     如果被除数为负数，那么取余的结果也为负数。例如，-5 % 3 的结果是 -2，因为 -5 除以 3 余 -2。
     *     如果被除数和除数都为负数，那么取余的结果为负数或者 0。例如，-5 % -3 的结果是 -2，因为 -5 除以 -3 余 -2。
     *     System.out.println(0 % 2); // 0
     *     System.out.println(3 % 2); // 1
     *     System.out.println(3 % -2); // 1
     *
     *     System.out.println(3 % -10); // 3
     *     System.out.println(-3 % 2); // -1
     *     System.out.println(-3 % 10); // -3
     *
     *     System.out.println(-3 % -10); // -3
     *
     * @param points
     * @return
     */
    public int maxPoints(int[][] points) {
        int maxInLine = 1;
        for(int i = 0;i<points.length;i++){
            Map<Integer, Integer> stat = new HashMap<Integer, Integer>();
            for(int j = i+1;j<points.length;j++){
                int dividend = points[i][0] - points[j][0];
                int divisor = points[i][1] - points[j][1];
                if(divisor == 0){ // 在一条横线上
                    stat.put(Integer.MAX_VALUE, stat.getOrDefault(Integer.MAX_VALUE, 1) + 1);
                }else if(dividend == 0){ // 在一条竖线上
                    stat.put(Integer.MIN_VALUE, stat.getOrDefault(Integer.MIN_VALUE, 1) + 1);
                }else{
                    int maxGcd = gcd(Math.abs(dividend), Math.abs(divisor));
                    int key = Math.abs(dividend/=maxGcd) + Math.abs(divisor/=maxGcd) * 10000;
                    if(dividend < 0 && divisor > 0 || dividend > 0 && divisor < 0 ){
                        key = -key;
                    }
                    stat.put(key, stat.getOrDefault(key, 1) + 1);
                }
            }
            // 统计这个点所在的直线的最大的点数
            for(Map.Entry<Integer,Integer> entry: stat.entrySet()){
                maxInLine = Math.max(maxInLine, entry.getValue());
            }
        }
        return maxInLine;
    }

    private int gcd(int largest, int smallest){
        return smallest == 0 ? largest : gcd(smallest, largest%smallest);
    }


    public static void main(String[] args) {
        MaxPointsOnALine mp = new MaxPointsOnALine();

  //      System.out.println(mp.gcd(2, 0));
//        System.out.println(mp.gcd(0, 2));
        System.out.println(0 % 2); // 0
        System.out.println(3 % 2); // 1
        System.out.println(3 % -2); // 1

        System.out.println(3 % -10); // 3
        System.out.println(-3 % 2); // -1
        System.out.println(-3 % 10); // -3

        System.out.println(-3 % -10); // -3
        Point p1 = new Point(0,0);
        Point p2 = new Point(0,0);

        int[][]  ps = {{-54,-297},{-36,-222},{3,-2},{30,53},{-5,1},{-36,-222},{0,2},{1,3},{6,-47},{0,4},{2,3},{5,0},{48,128},{24,28},{0,-5},{48,128},{-12,-122},{-54,-297},{-42,-247},{-5,0},{2,4},{0,0},{54,153},{-30,-197},{4,5},{4,3},{-42,-247},{6,-47},{-60,-322},{-4,-2},{-18,-147},{6,-47},{60,178},{30,53},{-5,3},{-42,-247},{2,-2},{12,-22},{24,28},{0,-72},{3,-4},{-60,-322},{48,128},{0,-72},{-5,3},{5,5},{-24,-172},{-48,-272},{36,78},{-3,3}};
        Point[] points = new Point[ps.length];

        for(int i=0;i<ps.length;i++){
            points[i] = new Point(ps[i][0],ps[i][1]);
        }
        System.out.println((double)(94911150-0)/(94911151-0));
        System.out.println((double)(94911151-0)/(94911152-0));

        Point[] points1 = {p1,p2};
        System.out.println(new MaxPointsOnALine().maxPoints(points1));
        System.out.println(new MaxPointsOnALine().generateGCD2(-1,-1)[0]+" "+new MaxPointsOnALine().generateGCD2(-1,-1)[1]);

        System.out.println(new MaxPointsOnALine().generateGCD2(1,1)[0]+" "+new MaxPointsOnALine().generateGCD2(1,1)[1]);
        //new MaxPointsOnALine().maxPoints(points);
    }

}
