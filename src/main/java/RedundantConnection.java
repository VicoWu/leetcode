/**
 * Question 684
 * 对于一个无向无环图，只要增加一条边，就必定会让其变成一个无向有环图。本题目，就是选择一条可以破环对边，而且，题目要求：
 * `If there are multiple answers, return the answer that occurs last in the given 2D-array`。
 ## 深度优先比例确认是否有环(不可行)
 其实，选择一条边进行破除，很简单，可以使用深度优先遍历方法，比如我在代码中findRedundantConnectionByDFS()
 方法使用深度优先遍历，原理是：如果不存在环，那么，某一个点，只可能被其它的一个点访问，如果存在环，则某个点会被两个点都访问到，
 当被第二个点访问到都时候，我们就认为它有环。我在这里使用邻接表来表示图。但是不是很合适，因为返回的结果不一定是题目要求都多个结果则返回2D-array都最后一个。

 ## 使用union find 方法（正确解法）
 因此，这一天非常适合使用union-find
 我在代码中都find使用compresed find来加速查找。在对edges进行遍历对时候，如果没有环，则对这个边的两个
 定点调用find()，肯定返回不同对连通分量值。如果有环，则遍历到环闭合对时候，两个点对find返回结果相同，此时则判断有环。





 */
public class RedundantConnection {
    public int[] findRedundantConnectionByDFS(int[][] edges) {

        //int[] visited = new int[edges.length];

        Node[] linkedTable  = constructLinkedTable(edges);
        dfs(linkedTable,linkedTable[edges[0][0]],linkedTable[edges[0][0]]);
        return null;
    }

    public int[] findRedundantConnectionByUnionFind(int[][] edges) {

        //int[] visited = new int[edges.length];

        int[] parent = new int[edges.length+1];

        for(int i=0;i<parent.length;i++){
            parent[i] =  i;
        }

        int[] result=null;
        for(int i=0;i<edges.length;i++){

            int a = find(parent,edges[i][0]);
            int b = find(parent,edges[i][1]);
            if(a==b){
                result  = edges[i];
            }
            union(parent,a,b);
        }

        return result;
    }


    private int find(int[] parent,int vertex){
        while(parent[vertex]!= vertex)
            vertex = find(parent,parent[vertex]);
        return vertex;
    }

    private void union(int[] parent,int a , int b){
        parent[a] = parent[b];
    }

    /**
     * 构造邻接表
     * @param edges
     * @return
     */
    private Node[] constructLinkedTable(int[][] edges){
        Node[] result = new Node[edges.length+1];
        for(int i=0;i<edges.length;i++){
            if(result[edges[i][0]] == null)
            result[edges[i][0]] = new Node(edges[i][0]);
            if(result[edges[i][1]] == null)
            result[edges[i][1]] = new Node(edges[i][1]);
        }

        for(int i=0;i<edges.length;i++){
            Node n = new Node(edges[i][1]);
            Node head = result[edges[i][0]];
            while(head.next!=null)
                head = head.next;
            head.next = n;
        }
        return result;
    }

    private void dfs(Node[] linkedTable,Node root,Node currentNode){
        if(!visited(linkedTable,currentNode)){
            visit(linkedTable,currentNode);
            System.out.println(currentNode.value);
        }
        else{
            System.out.println("Found a visited edge ["+root.value+","+currentNode.value+"]");

        }
        Node linkedNode = linkedTable[currentNode.value];
        while(linkedNode!=null && linkedNode.next!=null){
            Node next = linkedNode.next;
            dfs(linkedTable,currentNode,next);
            linkedNode = linkedNode.next;
        }
    }

    private boolean visited(Node[] linkedTable,Node currentNode){
        return linkedTable[currentNode.value].visited;
    }

    private void visit(Node[] linkedTable,Node currentNode){

        linkedTable[currentNode.value].visited  = true;
    }




    public static class Node{
        boolean visited = false;
        public int value;
        public Node next = null;
        public Node(int value){
            this.value = value;
        }
    }


    public static void main(String[] args) {

        int[][] edges = {{1,2},{3,4},{1,5},{2,3},{1,4}};
        int[][] edges2 = {{1,2},{1,3},{2,3}};
        new RedundantConnection().findRedundantConnectionByUnionFind(edges2);
    }
}
