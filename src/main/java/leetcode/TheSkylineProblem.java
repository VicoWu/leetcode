package leetcode;

import java.util.*;

/**
 * Question 218
 *
 * ## 最简单的解法：
 https://discuss.leetcode.com/topic/22482/short-java-solution
 我们用一个大顶堆保存建筑的高度，当建筑开始的时候，保存，当建筑结束的时候删除；
 那么，什么时候可以认为遇到了一个critical point:就是这个大顶堆的值与前一个critical point相比发生变化的时候，可
 能是增加，比如[[1,2,3],[2,3,4]]，也可能是减少，比如[[1,2,3],[2,3,1]]，当这个关键点发生的时候，这个关键点是多少呢？
 横坐标比较容易确定，就是当前遇到的点的横坐标，高度，就是当前大顶堆的最大元素。
 对每一个点进行排序，排序对规则是：按照横坐标从小到大进行排序，如果横坐标相同，则按照高度进行排序，如果横向坐标相同，
 则哪个高度小哪个排在前面，由于**头部点的高度是取负数获取的，因此，如果位置相同，头部点都排在尾部点的前面，同时，对于头部点，高度越高越靠前，对于尾部点，高度越低越靠前**；

 比如，对于[1,2,3] ,[2,3,1]这个序列，经过排序就是[1,3],[2,1],[2,3],[3,1],
 1.首先0入堆，
 2.然后读取[1,3]将3入堆，发现高度变化，因此将[1，3]加入结果
 3.然后读取[2,1]，将1入堆，高度无变化
 4.然后读取[2,3]，3出堆，此时堆的最大值从3降低为1，发生变化，因此将[2,1]加入到堆
 5.然后读取到[3,1],1出堆，此时堆的最大值从1降低为0，发生变化，因此将[3,0]加入到堆

 对于[1,2,1],[1,2,2],[1,2,3]：
 1.首先0入堆
 2.然后读取到[1,3] ，将3入堆，高度发生变化(注意，通过以上讲到的排序规则，是先读到[1,3],然后[1,2]，然后[1,1])，因此将[1,3]加入结果
 3.随后读取到[1,2],[1,3]并将两个3入堆，高度都没有发生变化，因此没有新的结果产生
 4.随后读取到[2,1]和[2,2]并将1和2出堆，堆的最大值都是3，没有发生变化，因此没有新的结果产生
 5.最后读取到[2,3],将3出堆，此时堆中最大元素是0，相比于上一个元素发生了变化，因此将[2,0]加入到结果中














 比如对于[[1,2,3],[2,3,1]]:



 */
public class TheSkylineProblem {


    private static Comparator<ComparableRectangle> heightComparator = new Comparator<ComparableRectangle>() {
        public int compare(ComparableRectangle o1, ComparableRectangle o2) {
            return o2.value[2] - o1.value[2];
        }
    };
    PriorityQueue<ComparableRectangle>  rectangleSortedByHeight = new  PriorityQueue<ComparableRectangle>(heightComparator);


    public List<int[]> getSkyline1(int[][] buildings) {
        List<int[]> results = new LinkedList<int[]>();

        //Stack<ComparableRectangle> heightStack = new Stack<ComparableRectangle>();
        List<CriticalPoint> sortedCriticalPoints = getSortedCriticalPoint(buildings);


        CriticalPoint lastCp = null;
        int from = 0;
        int to = 0;

        CriticalPoint lastCP = null;
       // Integer cpX = null;
        for (CriticalPoint cp : sortedCriticalPoints) {
          if (cp.leftSide) {

                if(rectangleSortedByHeight.isEmpty() || cp.rectangle.value[2]> lastCP.rectangle.value[2]){
                    lastCP = cp;
                }
                if (lastCP!=null &&!rectangleSortedByHeight.isEmpty() && cp.rectangle.value[2] > rectangleSortedByHeight.peek().value[2]) {
                    int[] r = {lastCP.rectangle.value[0],rectangleSortedByHeight.peek().value[2]};
                    results.add(r);
                    lastCP = cp;
                }
                rectangleSortedByHeight.add(cp.rectangle);
            } else {
                rectangleSortedByHeight.remove(cp.rectangle);
                if(!rectangleSortedByHeight.isEmpty() && lastCP.rectangle.value[2] == cp.rectangle.value[2] && cp.rectangle.value[2] >  rectangleSortedByHeight.peek().value[2]){
                    int[] r = {lastCP.rectangle.value[0],cp.rectangle.value[2]};
                    results.add(r);
                }
//                if(rectangleSortedByHeight.isEmpty() || cp.rectangle.value[2] > rectangleSortedByHeight.peek().value[2])
//                    lastCP = cp;
            }
        }

        int[] r = {lastCP.rectangle.value[1],0};
        results.add(r);
        return results;

    }

    public List<int[]> getSkyline(int[][] buildings) {
        List<int[]> results = new LinkedList<int[]>();

        //Stack<ComparableRectangle> heightStack = new Stack<ComparableRectangle>();
        List<CriticalPoint> sortedCriticalPoints = getSortedCriticalPoint(buildings);
        PriorityQueue<Integer>  sortedHeight = new  PriorityQueue<Integer>();
        sortedHeight.add(0);
        for(int i=0;i<sortedCriticalPoints.size();){
            CriticalPoint currentCp = sortedCriticalPoints.get(i);
            if(currentCp.leftSide){
                CriticalPoint highestLeftCP = currentCp;
                while(i<sortedCriticalPoints.size() &&sortedCriticalPoints.get(i).rectangle.value[0] == highestLeftCP.rectangle.value[0] && sortedCriticalPoints.get(i).leftSide) {
                    sortedHeight.add(sortedCriticalPoints.get(i++).rectangle.value[2]);
                }
                CriticalPoint highestRightCP = null;
                while(i<sortedCriticalPoints.size() && sortedCriticalPoints.get(i).rectangle.value[1] == highestLeftCP.rectangle.value[0]) {
                    highestRightCP = sortedCriticalPoints.get(i++);
                    sortedHeight.remove(highestRightCP.rectangle.value[2]);
                }

                if(highestRightCP==null){

                    if(highestLeftCP.rectangle.value[2] > sortedHeight.peek()){
                        int[] r = {highestLeftCP.getCoordinator(),highestLeftCP.rectangle.value[2]};
                        results.add(r);
                    }
                }else{
                    if(highestLeftCP.rectangle.value[2] != highestRightCP.rectangle.value[2]){
                        int[] r = {highestLeftCP.getCoordinator(),highestLeftCP.rectangle.value[2]};
                        results.add(r);
                    }
                }
            }
            else {

                CriticalPoint highestRightCP = currentCp;
                while (i<sortedCriticalPoints.size() && sortedCriticalPoints.get(i).rectangle.value[0] == highestRightCP.rectangle.value[0]) {
                    sortedHeight.remove(sortedCriticalPoints.get(i++).rectangle.value[2]);
                }
                if ( sortedHeight.peek() < highestRightCP.getCoordinator()) {
                    int[] r = {highestRightCP.getCoordinator(), sortedHeight.peek()};
                    results.add(r);
                }

            }
        }
        int[] last = {sortedCriticalPoints.get(sortedCriticalPoints.size()-1).getCoordinator(),0};
        results.add(last);
        return results;

    }



    /**
     * 获得关键节点的从左到右的排序
     * @param buildings
     * @return
     */
    private List<CriticalPoint> getSortedCriticalPoint(int[][] buildings){
        List<CriticalPoint> sortedCP = new ArrayList<CriticalPoint>();
        for(int i=0;i<buildings.length;i++){
            int[] building = buildings[i];
            ComparableRectangle rectangle = new ComparableRectangle(building);
            sortedCP.add(new CriticalPoint(rectangle,true));
            sortedCP.add(new CriticalPoint(rectangle,false));
        }
        Comparator<CriticalPoint> com = new Comparator<CriticalPoint>() {

            public int compare(CriticalPoint o1, CriticalPoint o2) {

                if(o1.getCoordinator() == o2.getCoordinator()){
                    if(o1.leftSide && !o2.leftSide)
                        return -1;
                    if(!o1.leftSide && o2.leftSide)
                        return 1;
                    return
                            o2.rectangle.value[2] - o1.rectangle.value[2];
                }
                else
                    return o1.getCoordinator() - o2.getCoordinator();
            }
        };
        Collections.sort(sortedCP,com);
        return sortedCP;
    }


    private static class ComparableRectangle implements Comparable<ComparableRectangle>{

        int[] value;

        public ComparableRectangle(int[] value) {
            this.value = value;
        }

        public int compareTo(ComparableRectangle o) {
            return this.value[2] - o.value[2];
        }
    }


    private static class CriticalPoint implements Comparable<CriticalPoint>{

        ComparableRectangle rectangle;
        boolean leftSide;

        public CriticalPoint(ComparableRectangle rectangle,boolean leftSide) {
            this.rectangle = rectangle;
            this.leftSide = leftSide;
        }


        public int getCoordinator(){
            return leftSide?rectangle.value[0]:rectangle.value[1];
        }

        public int compareTo(CriticalPoint o) {
            return this.getCoordinator() - o.getCoordinator();
        }
    }


    /**
     * 这个算法的基本思路：
     * 首先对每一个点进行排序，排序对规则是：按照横坐标从小到大进行排序，如果横坐标相同，则按照高度进行排序(不管是方形的左侧还是右侧)
     * 遍历排序的点，
     * @param buildings
     * @return
     */
    public List<int[]> getSkyline2(int[][] buildings) {
        List<int[]> result = new ArrayList<int[]>();
        List<int[]> height = new ArrayList<int[] >();
        for(int[] b:buildings) {
            height.add(new int[]{b[0], -b[2]}); //由于头部是负，尾部是正数，因此，排序以后，如果坐标相同，则方形的头部肯定排在尾部前面
            height.add(new int[]{b[1], b[2]});
        }

        Comparator<int[]> t1 = new Comparator<int[]>() {
            public int compare(int[] o1, int[] o2) {
                if(o1[0] != o2[0]) //如果横向坐标不同，就按照横向坐标排序
                    return o1[0] - o2[0];
                return o1[1] - o2[1];//如果横向坐标相同，则哪个高度小哪个排在前面，由于头部点的高度是取负数获取的，因此，
                // 如果位置相同，头部点都排在尾部点的前面，同时，对于头部点，高度越高越靠前，对于尾部点，高度越低越靠前
            }
        };
        Collections.sort(height, t1);

        Comparator<Integer> t2 = new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                 return o2-o1;
            }
        };
        //Collections.sort(height, t1);


        Queue<Integer> pq = new PriorityQueue<Integer>(t2);
        pq.offer(0); //在堆中插入一个元素0，以实现代码统一，pq保存了当前所有建筑的高度值，通过peek()方法可以获取当前最高的建筑高度
        int prev = 0;
        for(int[] h:height) { //由于height是通过位置和高度进行了排序，如果位置相同，则肯定是先遍历到最高高度的点
            if(h[1] < 0) { //如果这是一个开始点，则入栈
                pq.offer(-h[1]);
            } else {  //如果这是一个结束点，则出栈
                pq.remove(h[1]);
            }
            int cur = pq.peek(); //注意，cur代表当前高度最高的元素，而不是当前元素的高度，取最大的元素，也就是高度最高的元素；如果发现当前**高度最高的元素**的高度没有发生变化，那么就什么也不做
            if(prev != cur) { //如果发现当前高度最高的元素与上一个元素有变化，那么说明有一个新的关键点了
                result.add(new int[]{h[0], cur});
                prev = cur;
            }
        }
        return result;
    }




    public static void main(String[] args) {

        int[][] result = {{1,2,3},{4,5,6},{7,8,9},{7,8,9},{7,8,9}};
//        System.out.println(result.length+" "+result[0].length);
//        TreeMap<Integer, Integer> heightMap = new TreeMap<Integer, Integer>();
//
//        System.out.println(heightMap.firstKey());
//        PriorityQueue<ComparableRectangle>  rectangleSortedByHeight = new  PriorityQueue<ComparableRectangle>();
//
//        int[] reactal = {2,3,4};
//        ComparableRectangle c = new ComparableRectangle(reactal);
//        rectangleSortedByHeight.add(c);
//        rectangleSortedByHeight.remove(c);
//        System.out.println(rectangleSortedByHeight.size());

        PriorityQueue<ComparableRectangle>  rectangleSortedByHeight = new  PriorityQueue<ComparableRectangle>(heightComparator);
        int[] r1 = {2,3,20};
        int[] r2={4,5,6};
        int[] r3={7,9,10};
        rectangleSortedByHeight.add(new ComparableRectangle(r1));
        rectangleSortedByHeight.add(new ComparableRectangle(r2));

        rectangleSortedByHeight.add(new ComparableRectangle(r3));



       // int[][] input = {{1,2,1},{1,2,2},{1,2,3}};
        //int[][] input = {{2,9,10},{3,7,15},{5,12,12},{15,20,10},{20,24,8}};
        int[][] input = {{1,2,3},{2,3,1}};
        new TheSkylineProblem().getSkyline2(input);
    }
}
