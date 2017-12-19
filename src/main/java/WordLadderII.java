import java.util.*;

/**
 * Created by wuchang at 12/15/17
 * Question 126
 *
 * 经过这道题我是完全按照我的思路完成的。

 1.  深度优先遍历和广度优先遍历的区别
 首先必须明白，如果通过深度优先遍历，其实也可以找到解，但是，当我们通过深度优先遍历从beginWord到达endWord，这个路径不一定是最短的，
 因此，这时候我们需要保存所有的 遍历结果，等全部遍历完成，我们选出路径最短的那些路径。
 如果是广度优先遍历，当我们从beginWord开始，当我们遇到了endWord，则这个路径一定是最短的路径。并且，不需要从endWord再往下了。

 2. 和[word ladder](https://leetcode.com/problems/word-ladder/)的区别
 [word ladder](https://leetcode.com/problems/word-ladder/)问题只需要求最小距离，因此当我们通过FIFO队列进行广度优先遍历，
 第一次遇到endWord的深度，就是最小深度。可是现在要获取路径，并且是全部路径，因此我使用一个preMap保存遍历过程中每个节点的前向节点，
 如果找到了endWord，那么从endWord开始，顺着前向节点，就可以到达beginWord。

 3.  环带来的问题
 树的分层遍历很简单，无需标记某个节点是否被访问，因为没有环。可是这里每个节点形成的无向图可能存在环。在分层遍历过程中，
 如果不标记已经被访问的层，那么可能入队列、出队列无限循环下去了。我在代码里面是这样判断的：

 ```
 (!levelMap.containsKey(instance)
 || levelMap.get(instance) == levelMap.get(current)+1))
 ```
 用一个levelMap保存每一个节点的层。beginWord的层号为0，与它直接相连的节点的层号为1，以此类推。当我们通过队列弹出一个节点current，
 然后获取它的全部直接相邻的节点，对于每一个节点instance，如果levelMap中没有保存这个instance，说明它第一次被遍历到，当然可以访问，或者，
 虽然它之前被遍历到了，但是，instance的层数==current的层数+1,这也是可以的，说明前面有一条不经过current到达instance的路径，
 这是两条不同的路径，这就是环带来的可能结果

 ​

 4. 算法及时停止，提高运行效率
 我在代码中通过一个变量found优化代码，避免不必要的遍历
 即，当我们从队列中弹出current，通过current遇到了endWord（即current与endWord直接相连），这时候，
 我们就不需要再访问current的其他直接相邻的节点了，因为经过current与endWord直接相邻的路径肯定只有这一个。但是，
 后续的出队列的操作还需要继续，因此可能存在经过其他节点到达endWord的最短路径的选择。




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

        Map<String,Set<String>> preMap = new HashMap<String,Set<String>>();
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

        if(!preMap.containsKey(endWord))
            return globalResult;
        LinkedList<String> currentPath = new LinkedList<String>();
        computeGlobalResultFromPre(preMap,globalResult,endWord,currentPath);

        return globalResult;
    }

    private  List<List<String>> computeGlobalResultFromPre(Map<String,Set<String>> preMap ,List<List<String>> globalResult ,String key,LinkedList<String> currentPath){

        currentPath.addFirst(key);
        Set<String> myPres = preMap.get(key);
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

    private void addPre(Map<String,Set<String>> preMap,String current,String pre){
        if(preMap.containsKey(current)){
            preMap.get(current).add(pre);
        }
        else{
            Set<String> pres = new HashSet<String>();
            pres.add(pre);
            preMap.put(current,pres);
        }
    }

    public static void main(String[] args) {
        List<String> wordList = new LinkedList<String>();
        wordList.add("red");wordList.add("ted");wordList.add("tex");wordList.add("tax");wordList.add("tad");wordList.add("rex");
        new WordLadderII().findLadders("red","tax",wordList);
        //wordList.add("hot");wordList.add("dot");wordList.add("dog");wordList.add("lot");wordList.add("log");wordList.add("cog");
        //new WordLadderII().findLadders("hit","cog",wordList);
    }
}
