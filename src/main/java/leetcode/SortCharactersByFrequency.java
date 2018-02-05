package leetcode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by wuchang at 1/1/18
 * Question 451
 * [Kth Largest Element in an Array](https://leetcode.com/problems/kth-largest-element-in-an-array/description/)
 [Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/description/)
 [Sort Characters By Frequency](https://leetcode.com/problems/sort-characters-by-frequency/description/)
 [Split Array into Consecutive Subsequences](https://leetcode.com/problems/split-array-into-consecutive-subsequences/description/)
 同样，这四题其实是一个系列的。

 # 我的思路
 这一题与[Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/description/)几乎是一样的，都是对字符串中字符的频率按照从高到低的排序，
 只不过[Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/description/)是要输出top k，而这题是要输出全部。因此不再赘述。
 */

public class SortCharactersByFrequency {
    public String frequencySort(String s) {

        LinkedList<Character>[] bucket = new LinkedList[s.length() + 1];//bucket数组的每个位置对应了一个频率，这个位置的值对应了这个频率上所有的字符
        Map<Character,Integer> freq = new HashMap<Character,Integer>(); //记录每一个字符的频率
        for(char item:s.toCharArray()){
            if(!freq.containsKey(item))
                freq.put(item,0);
            freq.put(item,freq.get(item)+1);
        }
        for(Map.Entry<Character,Integer> entry:freq.entrySet()){
            if(bucket[entry.getValue()] == null)
                bucket[entry.getValue()] = new LinkedList<>();
            bucket[entry.getValue()].add(entry.getKey());
        }
        StringBuilder sb = new StringBuilder();
        for(int i=s.length();i>=0;i--){ //频率从高到低，遍历这个bucket
            if(bucket[i]==null) //这个频率上没有字符
                continue;
            for(char c:bucket[i]){ //这个频率上有一个或者一个以上的字符
                for(int j=0;j<i;j++) //这个字符出现了i次，因此重复append i次
                    sb.append(c);
            }
        }
        return sb.toString();

    }

    public static void main(String[] args) {

     System.out.println(new SortCharactersByFrequency().frequencySort("cacaca"));
    }
}
