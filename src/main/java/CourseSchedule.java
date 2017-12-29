import java.util.*;

/**
 * Created by wuchang at 12/27/17
 * Question 207
 *
 * 这里的关键问题就是确认图中是否存在环。

 关于深度优先遍历和广度优先遍历，看[这里](https://leetcode.com/problems/course-schedule/discuss/58509)

 # 深度优先遍历

 使用深度优先遍历方法，我们选择一个点进行深度优先遍历，即访问它的邻接点，然后再访问临界点的邻接点
 如果有环，那么我们在遍历的时候，一定会遇到在当前的遍历过程中访问过的节点。
 因此，我们使用变量path记录当前深度优先遍历遇到的节点。访问到某个节点，就把节点记录到path中，而在深度优先遍历的退栈时(比如路径有分叉，左侧访问完毕以后退栈，开始访问右侧)，从path中删除。
 因此，path中始终记录了本次遍历经历过的路径。
 必须把path和用来记录一个节点是否被访问过的visited区分开，visited标记一个节点是否被访问过，这个访问发生在之前的任何一次遍历。而path只是代表本次遍历的路径

 # 广度优先遍历

 其实与拓扑排序方法一致，都是找到入度为0的点，然后删除
 这里，我们使用pre记录当前还有多少个入度不为0 的点，显然，当pre==0的时候，我们可以确认图中没有环
 这样，只要pre不为0，我们就不断遍历试图找到入度为0的点。如果某一轮pre>0,经过遍历以后我们发现所有的节点入度都不为0，说明已经无法找到一个入度为0 的点

 # 拓扑排序
 拓扑排序看[这里](https://leetcode.com/problems/course-schedule/discuss/58516)
 通过拓扑排序的方式，不断从图中删掉入度为0 的点。如果删除结束以后发现图中还有入度为0的点无法删除，则说明存在环
 即，我们对于图中入度为0的点，删除它们，然后将它们的邻接点的入度减1，这个减1可能产生新的入度为0的点，然后再删除所有入度为0的点，再把他们的邻接点的入度减1，如此不断操作。如果
 我们发现有一些节点永远无法删除，则说明图中存在环
 *
 */

public class CourseSchedule {
    /**
     * 使用深度优先遍历方法，我们选择一个点进行深度优先遍历，即访问它的邻接点，然后再访问临界点的邻接点
     * 如果有环，那么我们在遍历的时候，一定会遇到在当前的遍历过程中访问过的节点。
     * 因此，我们使用变量path记录当前深度优先遍历遇到的节点。访问到某个节点，就把节点记录到path中，而在深度优先遍历的退栈时(比如路径有分叉，左侧访问完毕以后退栈，开始访问右侧)，从path中删除。
     * 因此，path中始终记录了本次遍历经历过的路径。
     * 必须把path和用来记录一个节点是否被访问过的visited区分开，visited标记一个节点是否被访问过，这个访问发生在之前的任何一次遍历。而path只是代表本次遍历的路径
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinishByDFS(int numCourses, int[][] prerequisites) {

        int[] inDegree = new int[numCourses];

        Map<Integer,HashSet<Integer>> graph = new HashMap<Integer,HashSet<Integer>>();
        boolean[] visited = new boolean[numCourses];
        for(int i=0;i<numCourses;i++) visited[i] = false;
        for(int i=0;i<prerequisites.length;i++){
            if(!graph.containsKey(prerequisites[i][1]))
                graph.put(prerequisites[i][1],new HashSet<>());
            graph.get(prerequisites[i][1]).add(prerequisites[i][0]);
            inDegree[prerequisites[i][0]]++;
        }
        for(int i=0;i<numCourses;i++){
            Set<Integer> path = new HashSet<Integer>(); //path保存了本条路径
            if(!dfs(graph,i,path,visited))
                return false;
        }
        return true;
    }

    private boolean dfs(Map<Integer,HashSet<Integer>> graph,Integer node,Set<Integer> path,boolean[] visited){

        if(path.contains(node)) //发现环
            return false;
        if(visited[node] || !graph.containsKey(node)) //如果这个节点在之前已经被访问过，注意，这个不是因为有环，如果有环，在if(path.contains(node)) 处就会被发现，这里visited为true只是代表
            //在以前某次dfs的时候已经访问过，由于访问过，那肯定说明没有环，否则这里不会再被访问了
            return true;
        path.add(node);
        visited[node] = true;
        for(Integer nei:graph.get(node)){
            if(!dfs(graph,nei,path,visited))
                return false; //一旦发现了环，立刻退出，后面的不需要再执行了
        }
        path.remove(node);//访问结束，删除
        return true;
    }


    /**
     * 通过广度优先遍历方法
     * 其实与拓扑排序方法一致，都是找到入度为0的点，然后删除
     * 这里，我们使用pre记录当前还有多少个入度不为0 的点，显然，当pre==0的时候，我们可以确认图中没有环
     * 这样，只要pre不为0，我们就不断遍历试图找到入度为0的点。如果某一轮pre>0,经过遍历以后我们发现所有的节点入度都不为0，说明已经无法找到一个入度为0 的点
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinishByBFS(int numCourses, int[][] prerequisites) {

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


    private boolean bfs(int numCourses, int[] inDegree, Map<Integer, HashSet<Integer>> graph) {
        int pre = numCourses; //记录了入度尚且不为0 的点的个数
        while (pre > 0) { //只要还存在入度不为0的点，就需要继续遍历
            int none  = 0; //记录这一轮遍历发现了多少个入度为0的点，如果没有发现，肯定
            for (int i = 0; i < numCourses; i++) {
                if (inDegree[i] != 0)
                    continue;
                //新找到一个入度为0 的点
                inDegree[i]--; //入度减1，这样下次就不会再被认为入度为0
                none++;
                pre--;
                if (graph.containsKey(i))
                    for (Integer nei : graph.get(i)) {
                        inDegree[nei]--;
                    }
            }
            if(none == 0) //none不为0，并且pre>0，说明虽然还存在入度不为0 的点，但是我们已经无法找到一个入度为0的点，那肯定就是因为有环
                return false;
        }
        return true;
    }


    /**
     * 通过拓扑排序的方式，不断从图中删掉入度为0 的点。如果删除结束以后发现图中还有入度为0的点无法删除，则说明存在环
     * 即，我们对于图中入度为0的点，删除它们，然后将它们的邻接点的入度减1，这个减1可能产生新的入度为0的点，然后再删除所有入度为0的点，再把他们的邻接点的入度减1，如此不断操作。如果
     * 我们发现有一些节点永远无法删除，则说明图中存在环
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinishByTopoSort(int numCourses, int[][] prerequisites) {
        int[][] matrix = new int[numCourses][numCourses]; // 邻接矩阵
        int[] indegree = new int[numCourses]; //入度表

        for (int i=0; i<prerequisites.length; i++) {
            int ready = prerequisites[i][0];
            int pre = prerequisites[i][1];
            if (matrix[pre][ready] == 0)
                indegree[ready]++; //duplicate case
            matrix[pre][ready] = 1; //邻接矩阵
        }

        int count = 0;
        Queue<Integer> queue = new LinkedList(); //先入先出队列，这个队列里面存放的都是入度为0的节点
        for (int i=0; i<indegree.length; i++) {
            if (indegree[i] == 0) queue.offer(i); //初始化FIFO队列，将所有入度为0的点入队列
        }
        while (!queue.isEmpty()) {
            int course = queue.poll(); //弹出一个入队为0 的节点
            count++;
            for (int i=0; i<numCourses; i++) {
                if (matrix[course][i] != 0) { //对于课程course，找到依赖课程course的课程i
                    if (--indegree[i] == 0) //如果入度为0，入队列
                        queue.offer(i);
                }
            }
        }
        return count == numCourses;
    }


    public static void main(String[] args) {
        int[][] course = {{0,1}};
        int[][] course1 = {{1,0},{2,1},{3,2},{1,3}};
        System.out.println(new CourseSchedule().canFinishByBFS(4,course1));

    }
}
