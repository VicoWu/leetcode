package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by wuchang at 12/27/17
 * Question 210
 *
 * 完全按照[Course Schedule](https://leetcode.com/problems/course-schedule/description/)的拓扑排序方法完成，每次发现一个入度为0 的点就保存在路径上面就行。
 */

public class CourseScheduleII {
    public int[] findOrder(int numCourses, int[][] prerequisites) {


        int[] inDegree = new int[numCourses];//入度表

        Map<Integer,HashSet<Integer>> graph = new HashMap<Integer,HashSet<Integer>>(); //邻接表，
        boolean[] visited = new boolean[numCourses];
        for(int i=0;i<numCourses;i++) visited[i] = false;
        for(int i=0;i<prerequisites.length;i++){
            if(!graph.containsKey(prerequisites[i][1]))
                graph.put(prerequisites[i][1],new HashSet<>());
            graph.get(prerequisites[i][1]).add(prerequisites[i][0]);
            inDegree[prerequisites[i][0]]++;
        }

        return bfs(numCourses,inDegree,graph);

    }



    private int[] bfs(int numCourses, int[] inDegree, Map<Integer, HashSet<Integer>> graph) {
        int[] res = new int[numCourses];
        int index = 0;
        int pre = numCourses; //记录了入度尚且不为0 的点的个数
        while (pre > 0) { //只要还存在入度不为0的点，就需要继续遍历
            int none  = 0; //记录这一轮遍历发现了多少个入度为0的点，如果没有发现，肯定
            for (int i = 0; i < numCourses; i++) {
                if (inDegree[i] != 0)
                    continue;
                //新找到一个入度为0 的点
                res[index++] = i;
                inDegree[i]--; //入度减1，这样下次就不会再被认为入度为0
                none++;
                pre--;
                if (graph.containsKey(i))
                    for (Integer nei : graph.get(i)) {
                        inDegree[nei]--;
                    }
            }
            if(none == 0) //none不为0，并且pre>0，说明虽然还存在入度不为0 的点，但是我们已经无法找到一个入度为0的点，那肯定就是因为有环
                return new int[0];
        }
        return res;
    }


    public static void main(String[] args) {
        int[][] courses = {{1,0},{2,0},{3,1},{3,2}};
        new CourseScheduleII().findOrder(4,courses);
    }
}
