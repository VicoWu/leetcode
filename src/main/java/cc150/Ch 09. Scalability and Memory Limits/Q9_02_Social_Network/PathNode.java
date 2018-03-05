package Q9_02_Social_Network;

import java.util.LinkedList;

public class PathNode {
	private Person person = null;
	private PathNode previousNode = null;
	public PathNode(Person p, PathNode previous) { //PathNode通过previousNode节点记录在分层遍历过程中自己的前向节点，这样，一旦通过双向广度优先遍历发生了相遇，就可以通过反推PathNode的list来获取路径
		person = p;
		previousNode = previous;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public LinkedList<Person> collapse(boolean startsWithRoot) {
		LinkedList<Person> path = new LinkedList<Person>();
		PathNode node = this;
		while (node != null) {
			if (startsWithRoot) {
				path.addLast(node.person);
			} else {
				path.addFirst(node.person);
			}
			node = node.previousNode;
		}
		return path;
	}
}
