package leetcode;

import java.util.*;

/**
 * Created by wuchang at 1/3/18
 * Question 49
 * 基本原理：所有的anagrams排序以后是相同的字符串，具体实现比较简单，因此略过。
 */

public class GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<List<String>>();
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String s : strs) {
            char[] ca = s.toCharArray();
            Arrays.sort(ca); //对这个字符串里面的字符进行排序
            String keyStr = String.valueOf(ca);
            if (!map.containsKey(keyStr)) map.put(keyStr, new ArrayList<String>());
            map.get(keyStr).add(s);
        }
        return new ArrayList<List<String>>(map.values());
    }

    public static void main(String[] args) {

    }
}
