import java.util.Arrays;
import java.util.PriorityQueue;


/**
 * Created by wuchang at 12/27/17
 * Question 630
 *
 * # 贪心算法
 过程：先将所有的课程按照deadline进行排序，然后，每次选择当前deadline最小的课程进行调度。如果，我们发现，我们这样调度，到了课程k，
 它的完成时间已经晚于它的deadline，显然，我们必须要删除一个课程(注意，这时候课程k已经加入进来了)，删除哪个课程呢？删除目前已经进行了调度的课程里面持续时间最长的课程。

 证明：
 [这里](https://leetcode.com/problems/course-schedule-iii/discuss/104847)的回复里面有比较详细的证明

 1. 证明我们每次尝试选择截止日期最近的课程，得到的解释最优解
 假如我们现在已经有了一个最优解，[t1,d1],[t2,d2]..[tk,dk]. 并且这个最优解不是按照截止时间从近到远排序的，那么，
 肯定存在两个相邻的课程[ti,di],[tj,dj]，有dj<di，即课程j的截止时间dj比课程i的截止时间di要早，但是课程j排在了后面。那么，
 如果我们调换[ti,di],[tj,dj]顺序，将j排在i的前面，显然，之前课程i前面的课程和课程j后面的课程都没有受到影响，同时，由于dj<di，所以，调换以后课程i和j的截止时间都依然可以满足。所以，我们可以像冒泡排序一样，将所有不满足截止时间从小到大的相邻节点调换顺序，直到全部的课程都满足了要求。这样，所有的课程肯定都没有受到影响，得到的依然是一个最优解。因此，我们可以知道，按照截止时间从小到达安排课程得到的解，是一个不比最优解差的解，这与之前我们假设某个解是最有的假设矛盾。
 2. 如果按照截止时间排序后选择的某个课程的截止时间超了，该放弃哪个课程
 假如我们按照截止时间排序规则，准备将课程k [tk,dk]进行调度，但是发现如果进行调度，课程k完成的时间将超过课程k的截止时间dk。
 显然，这时候我们应该淘汰一门课程，并且，如果淘汰的课程能够给后面的课程节省更多的时间，那么这个淘汰策略就是最好的淘汰策略。
 显然，我们应该淘汰课程时间ti最长的课程。由于课程i的持续时间最长，因此，淘汰以后，包括课程k在内的所有可能顺序往前移动课程i的时间，就都可以被成功调度而都不延期，同时，由于课程i的持续时间最长，这种淘汰策略给后面的课程留下了最长的时间，因此，这个淘汰策略自然就是最好的。


 */

public class CourseScheduleIII {
    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses,(a, b)->a[1]-b[1]); //Sort the courses by their deadlines (Greedy! We have to deal with courses with early deadlines first)
        PriorityQueue<Integer> pq=new PriorityQueue<>((a, b)->b-a); //构建一个大顶堆
        int time=0; //time保存了当前为止耗费的时间
        for (int[] c:courses) //对于每一个课程，注意，这些课程已经按照截止时间排序，截止时间越晚越靠后
        {
            time+=c[0]; //  计算当前课程结束需要消耗的时间
            pq.add(c[0]);
            if (time>c[1])  //如果我们发现这门课程k结束的时间大于其截止时间，就需要删除一个课程
                time-=pq.poll(); //删除堆顶的课程，即持续时间最长的课程a，由于a的持续时间大于k的持续时间，这样删除以后课程k的截止时间限制肯定就可以满足了。
        }
        return pq.size();
    }

    public static void main(String[] args) {

    }
}
