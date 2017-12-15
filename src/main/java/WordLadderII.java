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

        return null;

    }

    public void recurse(String current,Map<String,Boolean> visited,Set<String> dict ,List<String> path,String endWord,List<List<String>> globalResult ){
        visited.put(current,true);
        char[] ca  = current.toCharArray();
        for(int i=0;i<ca.length;i++){
            for(char c='a';c<='z';c++){
                char bak = ca[i];
                ca[i] = c;
                String instance = new String(ca);

                if(!visited.containsKey(instance) && dict.contains(instance)){ //对于广度优先遍历，这个相当于遍历到一个孩子节点
                    path.add(instance);
                    visited.put(instance,true);
                    if(instance.equals(endWord)){
                        globalResult.add(path);
                    }
                    else
                        recurse(instance,visited,dict,path,endWord,globalResult);
                    visited.put(instance,false);
                    path.remove(instance);
                }

                ca[i] = bak;
            }
        }
    }


    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList){

        List<List<String>> globalResult = new ArrayList<>();


        Set<String> dict = new HashSet<String>();
        for(String d:wordList){
            dict.add(d);
        }

        Map<String,List<String>> preMap = new HashMap<String,List<String>>();
        Map<String,Integer> levelMap = new HashMap<String,Integer>();
        Queue<String> queue = new LinkedList<String>();
        levelMap.put(beginWord,0);
        queue.add(beginWord);
        while(!queue.isEmpty()){
            String current =  queue.poll();
            char[] ca  = current.toCharArray();
            boolean found = false;
            for(int i=0;i<ca.length && !found;i++){
                for(char c='a';c<='z' && !found;c++){
                    char bak = ca[i];
                    ca[i] = c;
                    String instance = new String(ca);
                    if(dict.contains(instance)
                            &&
                            (!levelMap.containsKey(instance)
                                    || levelMap.get(instance) == levelMap.get(current)+1)){ //找到一个孩子
                        addPre(preMap,instance,current); //记录前置节点
                        if(instance.equals(endWord)){
                            found = true;
                        }
                        queue.add(instance); //入队列
                        levelMap.put(instance,levelMap.get(current)+1);
                    }
                    ca[i] = bak;
                }
            }
        }

        List<String> currentPath = new LinkedList<String>();
        computeGlobalResultFromPre(preMap,globalResult,endWord,currentPath);

        return globalResult;
    }

    private  List<List<String>> computeGlobalResultFromPre(Map<String,List<String>> preMap ,List<List<String>> globalResult ,String key,List<String> currentPath){

        currentPath.add(key);
        List<String> myPres = preMap.get(key);
        if(myPres==null){ //只有beginWord的前置节点是空的
            List<String> result = new LinkedList<>(currentPath);
            globalResult.add(result);
        }
        else {
            for(String pre:myPres){
                computeGlobalResultFromPre(preMap,globalResult,pre,currentPath);
            }
        }
        currentPath.remove(key);
        return globalResult;
    }

    private void addPath(List<List<String>> globalResult ,String item){
        for(int i=0;i<globalResult.size();i++){
            globalResult.get(i).add(item);
        }
    }

    private void addPre(Map<String,List<String>> preMap,String current,String pre){
        if(preMap.containsKey(current)){
            preMap.get(current).add(pre);
        }
        else{
            List<String> pres = new LinkedList<String>();
            pres.add(pre);
            preMap.put(current,pres);
        }
    }

    public static void main(String[] args) {
        List<String> wordList = new LinkedList<String>();
        wordList.add("hot");wordList.add("dot");wordList.add("dog");wordList.add("lot");wordList.add("log");wordList.add("cog");
        new WordLadderII().findLadders("hit","cog",wordList);
        //wordList.add("hot");wordList.add("dot");wordList.add("dog");wordList.add("lot");wordList.add("log");wordList.add("cog");
        //new WordLadderII().findLadders("hit","cog",wordList);
    }
}
