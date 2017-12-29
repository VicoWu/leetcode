import java.util.*;

/**
 * Created by wuchang at 12/28/17
 * Question 139
 */

public class WordBreak {
    public boolean wordBreak(String s, List<String> wordDict) {

        Map<String,Boolean> cache = new HashMap<String,Boolean>();
        return dfs(s,new HashSet<>(wordDict),cache);
    }

    public boolean dfs(String s,Set<String> wordDict, Map<String,Boolean> cache ){
        if("".equals(s))
            return true;
        if(cache.containsKey(s))
            return cache.get(s);
        for(int i=1;i<=s.length();i++){
            if(wordDict.contains(s.substring(0,i)) && dfs(s.substring(i),wordDict,cache)){
                cache.put(s,true);
                return true;
            }
        }
        cache.put(s,false);
        return false;
    }

    public static void main(String[] args) {
        String s = "leetcode";
        s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
        //s = "aaaaaaaaaaaaaaaaaab";
        String[] dict = {"leet","co2e"};
        String[]  dict2 = {"a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"};
        System.out.println(new WordBreak().wordBreak(s, Arrays.asList(dict2)));
    }
}
