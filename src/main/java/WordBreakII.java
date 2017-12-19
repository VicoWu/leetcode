import java.util.*;

/**
 * Created by wuchang at 12/18/17
 * Question 140
 *
 * # 深度优先遍历方法
 对于一个输入串catsanddog,我们尝试每一个切割位置:c|atsanddog   ca|tsanddog  cat|sanddog cats|anddog  ..，
 如果这个切割的前半部分在字典中存在，那么就通过递归的方式把切割的后半部分进行进行递归的计算。如果切割的头部在字典中不存在，
 那么后半部分就不需要计算了。
 同样的，为了提高效率，避免重复计算，我们使用一个cache保存已经计算完成的中间结果：
 ```
 Map<String,LinkedList<String>> cache = new HashMap<String,LinkedList<String>>();
 ```
 cache的key是某个字符串，value是这个字符串行程的一个合法的break的list




 */

public class WordBreakII {
    public List<String> wordBreak(String s, List<String> wordDict) {

        Map<String,LinkedList<String>> cache = new HashMap<String,LinkedList<String>>();
        Set<String> dict = new HashSet<String>(wordDict);

        return dfs(s,dict,cache);

    }

    /**
    * 输入一个字符串，返回这个字符串的合法切割的list
     */
    private List<String> dfs(String input ,Set<String> dict,  Map<String,LinkedList<String>> cache){
        if(input == null)
            return null ;
        LinkedList<String> found = new LinkedList<String>();
        if(cache.containsKey(input))
            return cache.get(input); //避免重复计算，已经计算过了，直接返回结果

        if(dict.contains(input))
            found.add(input);//如果整个串就是字典中的某一个单词，那么它本身就是一个成功的切割，但是，不能直接return，因为它有可能被切割的更小

        for(int i=1;i<input.length();i++)
        {
            String head = input.substring(0,i);
            if(dict.contains(head)){//如果切割的前半部分在字典中不存在，那么就没有必要对后半部分进行递归的运算了
                List<String> results = dfs(input.substring(i),dict,cache);
                for(String result:results){ //找到了一个合法的切割
                    found.add(head+" "+result);
                }
            }
        }
        cache.put(input,found);
        return found;
    }

    public static void main(String[] args) {

        String[] dictArray =  {"cat", "cats", "and", "sand", "dog"};
        List<String> dict = Arrays.asList(dictArray);
        List<String> results = new WordBreakII().wordBreak("catsanddog",dict);
        for(String result:results)
            System.out.println(result);
    }

}
