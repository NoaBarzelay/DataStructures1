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
	 
	 AVLTree tree;
	 /**
   * public Item retrieve(int i)
   *
   * returns the item in the ith position if it exists in the list.
   * otherwise, returns null
   */
  public Item retrieve(int i)
  {
	return ((AVLTree.AVLNode) tree.treeSelect(i+1)).getItem();
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
	   else if (i == tree.size()) {
		   if (tree.size() == 0) {
			   tree.setRoot(tree.new AVLNode(new Item(k, s), null, 0));
		   }
		   IAVLNode curNode = tree.getRoot();
		   while (curNode.getRight() != null) {
			   curNode = curNode.getRight();
		   }
		   curNode.setRight(tree.new AVLNode(new Item(k, s), curNode, 0)); 
	   }
	   else {
		   IAVLNode curNode = tree.treeSelect(i+1);
		   if (curNode.getLeft() == null) {
			   curNode.setLeft(tree.new AVLNode(new Item(k, s), curNode, 0));
		   }
		   else {
			   
		   }
	   }
	   
	   
	  return 42;	// to be replaced by student code
   }

  /**
   * public int delete(int i)
   *
   * deletes an item in the ith posittion from the list.
	* returns -1 if i<0 or i>n-1 otherwise returns 0.
   */
   public int delete(int i)
   {
	   return 42;	// to be replaced by student code
   }
	  
 }