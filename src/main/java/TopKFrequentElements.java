import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by wuchang at 1/1/18
 * Question 347
 *
 [Kth Largest Element in an Array](https://leetcode.com/problems/kth-largest-element-in-an-array/description/)
 [Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/description/)
 [Sort Characters By Frequency](https://leetcode.com/problems/sort-characters-by-frequency/description/)
 [Split Array into Consecutive Subsequences](https://leetcode.com/problems/split-array-into-consecutive-subsequences/description/)
 同样，这四题其实是一个系列的。

 # 我的思路
 联想到这一题与[Kth Largest Element in an Array](https://leetcode.com/problems/kth-largest-element-in-an-array/description/)相似，也是要求top k，因此，
 我最初的想法是，先统计每一个字符的频率，然后在这些频率上进行类似快排的partition算法，直到找到这个我们需要的k。
 但是这一题不一样的地方，是这些单词的频率是有上界的，即所有单词的频率之和不会超过nums的长度，即，nums中任何一个字符的频率，都在`[1,nums.length]`之间，而[Kth Largest Element in an Array](https://leetcode.com/problems/kth-largest-element-in-an-array/description/)中是直接对一个无序上组求top k，数组里面的数字的值是没有具体范围的。因此，我们使用一个bucket数组，数组的索引即代表频率，数组每一个索引位置的值是一个list，记载了对应频率的所有字符。当bucket填充完毕以后，我们从后往前遍历数组，就可以得到我们要求的频率最高的k个元素了。
 这是典型的空间换时间。

 */

public class TopKFrequentElements {
    public List<Integer> topKFrequent(int[] nums, int k) {

        LinkedList<Integer>[] bucket = new LinkedList[nums.length + 1];//bucket数组的每个位置对应了一个频率，这个位置的值对应了这个频率上所有的字符
        Map<Integer,Integer> freq = new HashMap<Integer,Integer>(); //记录每一个字符的频率
        for(int item:nums){
            if(!freq.containsKey(item))
                freq.put(item,0);
            freq.put(item,freq.get(item)+1);
        }
        for(Map.Entry<Integer,Integer> entry:freq.entrySet()){

            if(bucket[entry.getValue()] == null)
                bucket[entry.getValue()] = new LinkedList<>();
            bucket[entry.getValue()].add(entry.getKey());
        }


        List<Integer> result = new LinkedList<>();

        for(int i=bucket.length-1;i>=0 && result.size()<k;i--) //在bucket上从大到小遍历，看看从大到小是否有一个或者一个以上的字符
        {
            while(bucket[i]!=null && bucket[i].size()>0 && result.size() < k)
                result.add(bucket[i].remove());//将bucket上对应频率的字符取出来
        }
        return result;
    }

    public static void main(String[] args) {

        int[] nums = {1,1,1,2,2,3};
        int[] nums2 = {1,2};
        System.out.println(new TopKFrequentElements().topKFrequent(nums,2));
    }
}
