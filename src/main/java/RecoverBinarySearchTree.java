/**
 * Question 99
 */
public class RecoverBinarySearchTree {

  public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }


    TreeNode firstElement = null;
    TreeNode secondElement = null;
    // The reason for this initialization is to avoid null pointer exception in the first comparison when prevElement has not been initialized
    TreeNode prevElement = new TreeNode(Integer.MIN_VALUE);

    public void recoverTree(TreeNode root) {

        // In order traversal to find the two elements
        traverse(root);

        // Swap the values of the two nodes
        int temp = firstElement.val;
        firstElement.val = secondElement.val;
        secondElement.val = temp;
    }

    private void traverse(TreeNode root) {

        if (root == null)
            return;

        traverse(root.left);

        // Start of "do some business",
        // If first element has not been found, assign it to prevElement (refer to 6 in the example above)
        if (firstElement == null && prevElement.val >= root.val) {
            firstElement = prevElement;
        }

        // If first element is found, assign the second element to the root (refer to 2 in the example above)
        if (firstElement != null && prevElement.val >= root.val) {
            secondElement = root;
        }
        prevElement = root;

        // End of "do some business"

        traverse(root.right);
    }


    public static void main(String[] args) {
        TreeNode node5 =new TreeNode(20);
        TreeNode node10 =new TreeNode(10);
        TreeNode node12 =new TreeNode(12);
        TreeNode node15=new TreeNode(15);
        TreeNode node20 =new TreeNode(5);
        node10.left = node5;
        node10.right = node15;
        node15.left = node12;
        node15.right = node20;


        new RecoverBinarySearchTree().recoverTree(node10);
    }
}
