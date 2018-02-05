package leetcode;

import java.util.*;

/**
 * Created by wuchang at 12/29/17
 * Question 56
 * 先将多个interval按照开始时间排序，然后我们一个一个进行归并
 1. 如果发现相邻并且没有连接的，比如[1,2],[3,4]，就把[1,2]加入到结果中，[3,4]继续和后面的间隙进行归并。
 由于我们是按照开始时间排序的，因此后面的间隙的开始时间不可能比3还早，即，既然[3,4]跟[1,2]没有交集，那么[3,4]后面的就和[1,2]不可能产生交集了
 2. 如果发现交集，则进行归并,分两种情况，相交或者相包含，[1,3]跟[2,4]，或者[1,4]跟[2,3]，
 因此这时候合并以后的interval是什么需要根据两个间隙的end，合并以后的end是两个interval中较大的end
 */

public class MergeIntervals {

    public static class Interval {
        int start;
        int end;

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }

    Comparator com  = new Comparator<Interval>() { //按照开始时间进行排序

        @Override
        public int compare(Interval o1, Interval o2) {
            return o1.start - o2.start;
        }
    };
    public List<Interval> merge(List<Interval> intervals) {

        if(intervals == null || intervals.size() == 0)
            return intervals;
        Collections.sort(intervals,com);
        List<Interval> result = new LinkedList<Interval>();
        Interval current =  intervals.get(0);result.add(current);
        for(Interval i:intervals){
                if(i.start <= current.end){//两个区间有交集，我们对二者进行合并
                    current.end = i.end < current.end?current.end:i.end; //合并以后的结束时间是两个结束时间中的较大者
                }
                else{
                    current = i; //没有产生交集，因此，新增加了一个区间
                    result.add(current);
                }
            }
        return result;
    }

    public static void main(String[] args) {
        Interval[] ites = {new Interval(1,3),new Interval(2,6),new Interval(8,10),new Interval(15,18)};

        Interval[] ites2 = {};
        Interval[] ites3 = {new Interval(1,4),new Interval(2,3)};
        List<Interval> intervals  = Arrays.asList(ites3);

        new MergeIntervals().merge(intervals);
    }
}
