import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuchang at 12/29/17
 * Question 57
 */

public class InsertInterval {


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


    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {

        List<Interval> result = new LinkedList<>();
        int right = 0;
        for(int i=0;i<intervals.size();i++){
           Interval inte =  intervals.get(i);
           if(inte.end < newInterval.start)
               result.add(inte);
           else if(!(newInterval.end < inte.start || newInterval.start > inte.end))
            {

                newInterval.start = Math.min(newInterval.start,inte.start);
                newInterval.end = Math.max(newInterval.end,inte.end);
                right=i;
            }
        }

        result.add(newInterval);
        result.addAll(intervals.subList(right+1>intervals.size()?intervals.size():right+1,intervals.size()));
        return result;
    }

    public static void main(String[] args) {
       Interval[] ites = {new  Interval(1,2),new Interval(3,5),new Interval(6,7),new Interval(8,10),new Interval(12,16)};
        Interval[] ites2 = {new  Interval(1,5)};
        Interval[] ites3 = {};
       Interval insert = new  Interval(0,0);
        Interval insert2 = new Interval(2,8);
       new InsertInterval().insert(Arrays.asList(ites2),insert);
    }
}
