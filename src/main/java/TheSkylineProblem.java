import java.util.*;

/**
 * Question 218
 */
public class TheSkylineProblem {



    private static  Comparator<ComparableRectangle> heightComparator = new Comparator<ComparableRectangle>() {
        public int compare(ComparableRectangle o1, ComparableRectangle o2) {
            return o2.value[2] - o1.value[2];
        }
    };



    PriorityQueue<ComparableRectangle>  rectangleSortedByHeight = new  PriorityQueue<ComparableRectangle>(heightComparator);
    public List<int[]> getSkyline(int[][] buildings) {

        List<int[]> results = new LinkedList<int[]>();

        List<CriticalPoint> sortedCriticalPoints = getSortedCriticalPoint(buildings);


        CriticalPoint lastCp= null;int from = 0;int to = 0;


        for(CriticalPoint cp:sortedCriticalPoints){


            if(cp.leftSide){ //这个关键点是矩形的左侧节点


                if(rectangleSortedByHeight.isEmpty()
                        || cp.rectangle.value[2] > rectangleSortedByHeight.peek().value[2]) //这个关键节点会形成天际线
                {

                    if(lastCp==null ||  cp.getCoordinator()!= lastCp.getCoordinator()){
                        int[] r={cp.getCoordinator(),cp.rectangle.value[2]};
                        results.add(r);
                     }
                }
                rectangleSortedByHeight.add(cp.rectangle);

            }
            else{ //这个关键点是矩形的右侧节点

                int height = cp.rectangle.value[2];
                int maxHeight = rectangleSortedByHeight.peek().value[2];
                rectangleSortedByHeight.remove(cp.rectangle); //这个rectangle的任务彻底完成
                if(rectangleSortedByHeight.isEmpty()){
                    int[] r={cp.getCoordinator(),0};
                    results.add(r);
                }
//                if(height == maxHeight){ //这个关键节点会形成天际线
//                    if(lastCp== null || cp.getCoordinator()!= lastCp.getCoordinator()){
//                        int[] r = {cp.getCoordinator(),rectangleSortedByHeight.isEmpty()?0:rectangleSortedByHeight.peek().value[2]};
//                        results.add(r);
//                    }
//                }
            }
            lastCp = cp;

        }
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
                if (o1.getCoordinator() != o2.getCoordinator())
                    return o1.getCoordinator() - o2.getCoordinator();
                return o2.rectangle.value[2] - o1.rectangle.value[2];
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


    public List<int[]> getSkyline2(int[][] buildings) {
        List<int[]> result = new ArrayList<int[]>();
        List<int[]> height = new ArrayList<int[] >();
        for(int[] b:buildings) {
            height.add(new int[]{b[0], -b[2]});
            height.add(new int[]{b[1], b[2]});
        }

        Comparator<int[]> t1 = new Comparator<int[]>() {
            public int compare(int[] o1, int[] o2) {
                if(o1[0] != o2[0])
                    return o1[0] - o2[0];
                return o1[1] - o2[1];
            }
        };
        Collections.sort(height, t1);

        Comparator<Integer> t2 = new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                 return o2-o1;
            }
        };
        Collections.sort(height, t1);


        Queue<Integer> pq = new PriorityQueue<Integer>(t2);
        pq.offer(0);
        int prev = 0;
        for(int[] h:height) {
            if(h[1] < 0) {
                pq.offer(-h[1]);
            } else {
                pq.remove(h[1]);
            }
            int cur = pq.peek();
            if(prev != cur) {
                result.add(new int[]{h[0], cur});
                prev = cur;
            }
        }
        return result;
    }


    public static void main(String[] args) {

//        int[][] result = {{1,2,3},{4,5,6},{7,8,9},{7,8,9},{7,8,9}};
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



        //int[][] input = {{2,9,10},{3,7,15},{5,12,12},{15,20,10},{20,24,8}};
        int[][] input = {{13,20,12},{15,20,10},{20,24,8}};
        new TheSkylineProblem().getSkyline2(input);
    }
}
