import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuchang at 12/26/17
 * Question 133
 */

public class CloneGraph {
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {

        Map<UndirectedGraphNode,UndirectedGraphNode> map  = new HashMap<UndirectedGraphNode,UndirectedGraphNode>();

        UndirectedGraphNode copy =  traverse(map,node);
        return copy;
    }

    /**
     * 对某个节点进行遍历，返回这个节点对应的拷贝节点
     * 如果这个节点之前被访问过，则直接返回其对应的拷贝节点，如果没有被访问过，则创建对应的拷贝节点，保存在map中，然后对节点的邻居节点进行递归traverse
     * @param map
     * @param current
     * @return
     */
    public UndirectedGraphNode traverse(Map<UndirectedGraphNode,UndirectedGraphNode> map, UndirectedGraphNode current){

        if(current == null)
            return null;
        if(map.containsKey(current))
            return map.get(current);
        UndirectedGraphNode copy = new UndirectedGraphNode(current.label); //这个节点还没有被遍历过，则创建新的拷贝节点
        map.put(current,copy); //将拷贝节点保存在缓存中，同时，这也相当于标记current是被访问的节点
        for(UndirectedGraphNode neighbor:current.neighbors){ //对这个节点进行深度优先遍历
            copy.neighbors.add(traverse(map,neighbor));
        }
        return copy;
    }

    public static void main(String[] args) {
        UndirectedGraphNode n0 = new UndirectedGraphNode(0);
        UndirectedGraphNode n1 = new UndirectedGraphNode(1);
        UndirectedGraphNode n2 = new UndirectedGraphNode(2);

        n0.neighbors.add(n1);
        n0.neighbors.add(n2);
        n1.neighbors.add(n2);
        n2.neighbors.add(n2);

        new CloneGraph().cloneGraph(n0);
    }

   static  class UndirectedGraphNode {
        int label;
      List<UndirectedGraphNode> neighbors;
      UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
  }


}
