package TreeList;
import TreeList.AVLTree.IAVLNode;

 /**
 *
 * Tree list
 *
 * An implementation of a Tree list with  key and info
 *
 */
 public class TreeList{
	 
	 AVLTree tree = new AVLTree();
	 /**
     * public Item retrieve(int i)
     *
     * returns the item in the ith position if it exists in the list.
     * otherwise, returns null
     */
	 public Item retrieve(int i)
	 {
		 //find node with rank i+1 (==ith position)
		 IAVLNode node = tree.treeSelect(i+1);
		 //if node doesn't exists
		 if (node == null) {
			 return null;
		 }
		 //return item of node
		 else {
			 return ((AVLTree.AVLNode) node).getItem();
		 } 
	 }

	 
	 /**
	  * public int insert(int i, int k, String s) 
	  *
	  * inserts an item to the ith position in list with key k and info s.
	  * returns -1 if i<0 or i>n otherwise return 0.
	  */
	 public int insert(int i, int k, String s) 
	 {
		 if (i < 0 || i > tree.size()) {
			 return -1;
		 }
		 //if new node insert in nth position
		 else if (i == tree.size()) {
			 //new node is root
			 if (tree.size() == 0) {
				 tree.setRoot(tree.new AVLNode(new Item(k, s), null, 0));
			 }
			 else {
				 IAVLNode curNode = tree.getRoot();
				 //find node in current last position and add to its right
				 //then fix tree
				 while (curNode.getRight() != null) {
					 curNode = curNode.getRight();
				 }
				 curNode.setRight(tree.new AVLNode(new Item(k, s), curNode, 0));
				 tree.fixTree(curNode);
			 }
		 }
		 else {
			 //find node in current ith position
			 IAVLNode curNode = tree.treeSelect(i+1);
			 //if no left son then add new node to its left
			 if (curNode.getLeft() == null) {
				 curNode.setLeft(tree.new AVLNode(new Item(k, s), curNode, 0));
				 tree.fixTree(curNode);
			 }
			 //else find predecessor and add to its right
			 else {
				 IAVLNode predecessor = ((AVLTree.AVLNode) curNode).getPredecessor();
				 predecessor.setRight(tree.new AVLNode(new Item(k, s), curNode, 0));
				 predecessor.getRight().setParent(predecessor);
				 tree.fixTree(predecessor);
			 }
			 
		 }
		 return 0;
	 }

	 
	 /**
	 * public int delete(int i)
	 *
	 * deletes an item in the ith position from the list.
	 * returns -1 if i<0 or i>n-1 otherwise returns 0.
	 */
	 public int delete(int i)
	 {
		 if (i < 0 || i >= tree.size()) {
			 return -1;
		 }
		 else {
			 //find node in ith position -> delete node
			 IAVLNode node = tree.treeSelect(i+1);
			 tree.deleteByNode(node);
			 return 0;
		 } 
	 } 
 }