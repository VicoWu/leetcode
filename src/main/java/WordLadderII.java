import java.util.*;

/**
 * Created by wuchang at 12/15/17
 * Question 126
 */

public class WordLadderII {
//    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
//
//        List<List<String>> globalResult = new ArrayList<>();
//
//        Set<String> words = new HashSet<>();
//        for (String word : wordList)
//            words.add(word);
//
//        String word = beginWord;
//        List<String> path = new ArrayList<String>();
//        path.add(beginWord);
//        recurse(path, endWord, words, globalResult);
//        return globalResult;
//    }
//
//    public void recurse(List<String> path, String endWord, Set<String> words, List<List<String>> globalResult) {
//        String tail = path.get(path.size() - 1); //路径的尾部元素
//        char[] wc = tail.toCharArray();
//        for (int i = 0; i < wc.length; i++) {
//            for (char c = 'a'; c <= 'z'; c++) {
//                char bak = wc[i];
//                wc[i] = c;
//                String instance = new String(wc);
//
//                if (instance.equals(endWord)) {
//                    path.add(endWord);
//                    globalResult.add(path);
//                    path.remove(endWord);
//                    return;
//                } else if (words.remove(instance)) {
//                    path.add(instance);
//                    recurse(path, endWord, words, globalResult);
//                    words.add(instance);
//                    path.remove(instance);
//                }
//
//                wc[i] = bak;
//            }
//        }
//    }
    public List<List<String>> findLadders2(String b, String e, List<String> wordList) {
        List<List<String>> globalResult = new ArrayList<>();
        if(b.equals(e)) return globalResult;

        Map<String,Boolean> visited =new HashMap<String,Boolean>();
        Set<String> dict = new HashSet<String>();
        for(String d:wordList){
            dict.add(d);
            visited.put(d,false);
        }

        Queue<String> q = new LinkedList<String>();
        q.add(b);
        dict.remove(b);

        int level=2;


        List<String> path = new LinkedList<String>();
        path.add(b);
        char[] ca  = b.toCharArray();

        visited.put(b,true);

        for(int i=0;i<ca.length;i++){
            for(char c='a';c<='z';c++){
                char bak = ca[i];
                ca[i] = c;
                String instance = new String(ca);

                if(instance.equals(e)){
                    path.add(e);

                    globalResult.add(path);

                } else if(dict.contains(instance)){
                    path.add(instance);

                    visited.put(instance,true);
                }
                ca[i] = bak;

            }
        }

    }


    public static void main(String[] args) {
        List<String> wordList = new LinkedList<String>();
        wordList.add("hot");wordList.add("dot");wordList.add("dog");wordList.add("lot");wordList.add("log");wordList.add("cog");
        //new WordLadderII().findLadders("hit","cog",wordList);
    }
}
