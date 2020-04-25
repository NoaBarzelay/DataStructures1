package TreeList;
/**
 *
 * AVLTree
 *
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 *
 */

public class AVLTree {

	IAVLNode root;
	int size;
	IAVLNode min;
	IAVLNode max;
	
  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   */
  public boolean empty() {
    return root == null;
  }
  
  public IAVLNode searchNode(int k)
  {
	  IAVLNode node = root;
	  while (node != null) {
		  if (node.getKey() == k) {
			  return node;
		  }
		  else if (node.getKey() < k) {
			  node = node.getRight();
		  }
		  else {
			  node = node.getLeft();
		  }	  
	  }	   
	  return null;
  }

 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   */
  public String search(int k)
  {
	  IAVLNode node = searchNode(k);
	  if (node != null) {
		  return node.getValue();
	  }
	  else {
		  return null;
	  }
  }
  
  public void RotateLeft(IAVLNode node) {
	  IAVLNode newParent = node.getRight();
	  node.setRight(newParent.getLeft());
	  if (node.getRight() != null) {
		  node.getRight().setParent(node);
	  }
	  newParent.setParent(node.getParent());
	  if (newParent.getParent() == null) {
		  this.root = newParent;
	  }
	  else if (newParent.getParent().getLeft() == node) {
		  newParent.getParent().setLeft(newParent);
	  }
	  else {
		  newParent.getParent().setRight(newParent);
	  }
	  newParent.setLeft(node);
	  node.setParent(newParent);
	  int nodeLeftHeight = (node.getLeft() == null) ? -1 : node.getLeft().getHeight();
	  int nodeRightHeight = (node.getRight() == null) ? -1 : node.getRight().getHeight();
	  node.setHeight(Math.max(nodeLeftHeight, nodeRightHeight)+1);
	  int newParentRightHeight = (newParent.getRight() == null) ? -1 : newParent.getRight().getHeight();
	  newParent.setHeight(Math.max(node.getHeight(), newParentRightHeight)+1);
	  ((AVLNode) node).setSize(((AVLNode) newParent).getSize());
	  int parentLeftSize = (newParent.getLeft() == null) ? 0 : ((AVLNode) newParent.getLeft()).getSize();
	  int parentRightSize = (newParent.getRight() == null) ? 0 : ((AVLNode) newParent.getRight()).getSize();
	  ((AVLNode) newParent).setSize(parentLeftSize + parentRightSize);
	  return ;
  }
  
  public void RotateRight(IAVLNode node) {
	  IAVLNode newParent = node.getLeft();
	  node.setLeft(newParent.getRight());
	  if (node.getLeft() != null) {
		  node.getLeft().setParent(node);
	  }
	  newParent.setParent(node.getParent());
	  if (newParent.getParent() == null) {
		  this.root = newParent;
	  }
	  else if (newParent.getParent().getLeft() == node) {
		  newParent.getParent().setLeft(newParent);
	  }
	  else {
		  newParent.getParent().setRight(newParent);
	  }
	  newParent.setRight(node);
	  node.setParent(newParent);
	  int nodeLeftHeight = (node.getLeft() == null) ? -1 : node.getLeft().getHeight();
	  int nodeRightHeight = (node.getRight() == null) ? -1 : node.getRight().getHeight();
	  node.setHeight(Math.max(nodeLeftHeight, nodeRightHeight)+1);
	  int newParentLeftHeight = (newParent.getLeft() == null) ? -1 : newParent.getLeft().getHeight();
	  newParent.setHeight(Math.max(node.getHeight(), newParentLeftHeight)+1);
	  ((AVLNode) node).setSize(((AVLNode) newParent).getSize());
	  int parentLeftSize = (newParent.getLeft() == null) ? 0 : ((AVLNode) newParent.getLeft()).getSize();
	  int parentRightSize = (newParent.getRight() == null) ? 0 : ((AVLNode) newParent.getRight()).getSize();
	  ((AVLNode) newParent).setSize(parentLeftSize + parentRightSize);
	  return ;
  }

  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the AVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * returns -1 if an item with key k already exists in the tree.
   */
   public int insert(int k, String i) {
	   IAVLNode curNode = root;
	   IAVLNode parentNode = null;
	   while (curNode != null) {
		   parentNode = curNode;
		   if (curNode.getKey() == k){
			   return -1;
		   }
		   else if (curNode.getKey() < k) {
			   curNode = curNode.getRight();
		   }
		   else {
			   curNode = curNode.getLeft();
		   }
	   }
	   curNode = new AVLNode(new Item(k, i), parentNode, 0);
	   this.size += 1;
	   if (this.min == null || this.min.getKey() > k) {
		   this.min = curNode;
	   }
	   if (this.max == null || this.max.getKey() < k) {
		   this.max = curNode;
	   }
	   if (parentNode == null) {
		   this.root = curNode;
		   return 0;
	   }
	   else if (parentNode.getKey() < k) {
		   parentNode.setRight(curNode);
	   }
	   else {
		   parentNode.setLeft(curNode);
	   }
	   int rotations = 0;
	   while (parentNode != null) {
		   int parentBF = ((AVLNode) parentNode).getBF();
		   ((AVLNode) parentNode).setSize(((AVLNode) parentNode).getSize() + 1);
		   if (parentBF == -2) {
			   int rightBF = ((AVLNode) parentNode.getRight()).getBF();
			   if (rightBF == -1) {
				   this.RotateLeft(parentNode);
				   rotations = 1;
			   }
			   else {
				   this.RotateRight(parentNode.getRight());
				   this.RotateLeft(parentNode);
				   rotations = 2;
			   }
		   }
		   else if (parentBF == 2) {
			   int leftBF = ((AVLNode) parentNode.getLeft()).getBF();
			   if (leftBF == 1) {
				   this.RotateRight(parentNode);
				   rotations = 1;
			   }
			   else {
				   this.RotateLeft(parentNode.getLeft());
				   this.RotateRight(parentNode);
				   rotations = 2;
			   }
		   }
		   else {
			   int leftHeight = (parentNode.getLeft() == null) ? -1 : parentNode.getLeft().getHeight();
			   int rightHeight = (parentNode.getRight() == null) ? -1 : parentNode.getRight().getHeight();
			   int newParentHeight = 1 + Math.max(leftHeight, rightHeight);
			   parentNode.setHeight(newParentHeight);
			   parentNode = parentNode.getParent();  
		   }  
	   }
	   return rotations;
   }

   public void byPass(IAVLNode deleteNode, IAVLNode child) {
	   if (deleteNode.getParent() != null && deleteNode.getParent().getLeft() == deleteNode) {
		   deleteNode.getParent().setLeft(child);
	   }
	   else {
		   if (deleteNode.getParent() != null) {
			   deleteNode.getParent().setRight(child);
		   }
	   }
	   if (child != null) {
		   child.setParent(deleteNode.getParent());
	   }
	   deleteNode.setParent(null);
	   deleteNode.setRight(null);
	   deleteNode.setLeft(null);
   }
   
   public void replaceNode(IAVLNode deleteNode, IAVLNode replacer) {
	   replacer.setRight(deleteNode.getRight());
	   replacer.setLeft(deleteNode.getLeft());
	   if (replacer.getRight() != null){
		   replacer.getRight().setParent(replacer);
	   }
	   if (replacer.getLeft() != null) {
		   replacer.getLeft().setParent(replacer);
	   }
	   replacer.setParent(deleteNode.getParent());
	   if (replacer.getParent() == null) {
		   this.root = replacer;
	   }
	   else if (replacer.getParent().getLeft() == deleteNode) {
		   replacer.getParent().setLeft(replacer);
	   }
	   else {
		   replacer.getParent().setRight(replacer);
	   }
	   deleteNode.setLeft(null);
	   deleteNode.setRight(null);
	   deleteNode.setParent(null);
   }
      
   
  /**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   * returns -1 if an item with key k was not found in the tree.
   */
   public int delete(int k)
   {
	   IAVLNode deleteNode = this.searchNode(k);
	   if (deleteNode == null) {
		   return -1;
	   }
	   this.size -= 1;
	   if (deleteNode == this.min) {
		   if (deleteNode.getRight() != null) {
			   this.min = deleteNode.getRight();
		   }
		   else {
			   this.min = deleteNode.getParent();
		   }
	   }
	   if (deleteNode == this.max) {
		   if ( deleteNode.getLeft() != null) {
			   this.max = deleteNode.getLeft();
		   }
		   else {
			   this.max = deleteNode.getParent();
		   }
	   }
	   IAVLNode parentNode = deleteNode.getParent();
	   if (deleteNode.getLeft() == null && deleteNode.getRight() == null) {
		   if (deleteNode == this.root) {
			   this.root = null;
		   }
		   else{
			   this.byPass(deleteNode, null);
		   }
	   }
	   else if (deleteNode.getLeft() == null || deleteNode.getRight() == null) {
		   IAVLNode child = (deleteNode.getLeft() == null) ? deleteNode.getRight() : deleteNode.getLeft();
		   if (deleteNode == this.root) {
			   this.root = child;
		   }
		   this.byPass(deleteNode, child);
	   }
	   else {
		   IAVLNode successor = ((AVLNode) deleteNode).getSuccessor();
		   this.byPass(successor, successor.getRight());
		   this.replaceNode(deleteNode, successor);
	   }
	   int rotations = 0;
	   while (parentNode != null) {
		   int parentBF = ((AVLNode) parentNode).getBF();
		   ((AVLNode) parentNode).setSize(((AVLNode) parentNode).getSize() - 1);
		   if (parentBF == -2) {
			   int rightBF = ((AVLNode) parentNode.getRight()).getBF();
			   if (rightBF == -1 || rightBF == 0) {
				   this.RotateLeft(parentNode);
				   rotations += 1;
			   }
			   else {
				   this.RotateRight(parentNode.getRight());
				   this.RotateLeft(parentNode);
				   rotations += 2;
			   }
			   parentNode = parentNode.getParent();
		   }
		   else if (parentBF == 2) {
			   int leftBF = ((AVLNode) parentNode.getLeft()).getBF();
			   if (leftBF == 1 || leftBF == 0) {
				   this.RotateRight(parentNode);
				   rotations += 1;
			   }
			   else {
				   this.RotateLeft(parentNode.getLeft());
				   this.RotateRight(parentNode);
				   rotations += 2;
			   }
			   parentNode = parentNode.getParent();
		   }
		   else {
			   int leftHeight = (parentNode.getLeft() == null) ? -1 : parentNode.getLeft().getHeight();
			   int rightHeight = (parentNode.getRight() == null) ? -1 : parentNode.getRight().getHeight();
			   int newParentHeight = 1 + Math.max(leftHeight, rightHeight);
			   parentNode.setHeight(newParentHeight);
			   parentNode = parentNode.getParent();
		   }  
	   }
	   return rotations;
   }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty
    */
   public String min()
   {
	   return (this.min == null) ? null : this.min.getValue() ; 
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    */
   public String max()
   {
	   return (this.max == null) ? null : this.max.getValue() ;
   }

   public int keysToArrayRec(int[] arr, IAVLNode node, int index)
   {
	   if (node != null) {
		   index = keysToArrayRec(arr, node.getLeft(), index);
		   arr[index++] = node.getKey();
		   index = keysToArrayRec(arr, node.getRight(), index);
	   }
	   return index;
   }
  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray()
  {
	  int[] arr = new int[this.size];
	  keysToArrayRec(arr, this.root, 0);
	  return arr;
  }

  public int infoToArrayRec(String[] arr, IAVLNode node, int index)
  {
	   if (node != null) {
		   index = infoToArrayRec(arr, node.getLeft(), index);
		   arr[index++] = node.getValue();
		   index = infoToArrayRec(arr, node.getRight(), index);
	   }
	   return index;
  }
  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray()
  {
	  String[] arr = new String[this.size];
	  infoToArrayRec(arr, this.root, 0);
	  return arr;
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    * precondition: none
    * postcondition: none
    */
   public int size()
   {
	   return size;
   }
   
     /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    *
    * precondition: none
    * postcondition: none
    */
   public IAVLNode getRoot()
   {
	   return root;
   }
   
   public void setRoot(IAVLNode node)
   {
	   this.root = node;
   }
   
   public IAVLNode treeSelectRec(IAVLNode node, int rank) 
   {
	   int curRank = (node.getLeft() == null) ? 1 : (((AVLNode) node.getLeft()).getSize() + 1);
	   if (curRank == rank) {
		   return node;
	   }
	   else if (curRank > rank) {
		   return treeSelectRec(node.getLeft(), rank);
	   }
	   else if (node.getRight() != null) {
		   return treeSelectRec(node.getRight(), rank - curRank);
	   }
	   else {
		   return null;
	   }
   }
   
   public IAVLNode treeSelect(int rank) 
   {
	   return treeSelectRec(root, rank);
	   
   }
   
   public int treeRank(IAVLNode node)
   {
	   int rank = 1 + ((AVLNode) node.getLeft()).getSize();
	   while (node != null) {
		   if (node.getParent() != null && node.getParent().getRight() == node) {
			   rank += ((AVLNode) node.getParent().getLeft()).getSize() + 1;
		   }
		   node = node.getParent();
	   }
	   return rank;
		   
   }

	/**
	   * public interface IAVLNode
	   * ! Do not delete or modify this - otherwise all tests will fail !
	   */
	public interface IAVLNode{	
		public int getKey(); //returns node's key 
		public String getValue(); //returns node's value [info]
		public void setLeft(IAVLNode node); //sets left child
		public IAVLNode getLeft(); //returns left child (if there is no left child return null)
		public void setRight(IAVLNode node); //sets right child
		public IAVLNode getRight(); //returns right child (if there is no right child return null)
		public void setParent(IAVLNode node); //sets parent
		public IAVLNode getParent(); //returns the parent (if there is no parent return null)
    	public void setHeight(int height); // sets the height of the node
    	public int getHeight(); // Returns the height of the node 
	}

   /**
   * public class AVLNode
   *
   * If you wish to implement classes other than AVLTree
   * (for example AVLNode), do it in this file, not in 
   * another file.
   * This class can and must be modified.
   * (It must implement IAVLNode)
   */
  public class AVLNode implements IAVLNode{
	  Item item;
	  IAVLNode parent;
	  IAVLNode left;
	  IAVLNode right;
	  int height;
	  int size = 1;
	  
	  public AVLNode(Item item, IAVLNode parent, int height) 
	  {
		  this.item = item;
		  this.height = height;
		  this.parent = parent;
	  }
	  
	  
		public int getKey()
		{
			return item.getKey(); 
		}
		public String getValue()
		{
			return item.getInfo(); 
		}
		public void setLeft(IAVLNode node)
		{
			this.left = node;
			return ;
		}
		public IAVLNode getLeft()
		{
			return left;
		}
		public void setRight(IAVLNode node)
		{
			this.right = node;
			return ; 
		}
		public IAVLNode getRight()
		{
			return right; 
		}
		public void setParent(IAVLNode node)
		{
			this.parent = node;
			return ; 
		}
		public IAVLNode getParent()
		{
			return parent;
		}

	    public void setHeight(int height)
	    {
	    	this.height = height;
	    	return ; 
	    }
	    public int getHeight()
	    {
	    	return height; 
	    }
	    public int getBF()
	    {
	    	int leftHeight = (this.left == null) ? -1 : this.left.getHeight();
	    	int rightHeight = (this.right == null) ? -1 : this.right.getHeight();
	    	return leftHeight - rightHeight;
	    }
	    public IAVLNode getSuccessor() {
	    	if (this.getRight() != null) {
	    		IAVLNode curNode = this.getRight();
	    		while (curNode.getLeft() != null) {
	    			curNode = curNode.getLeft();
	    		}
	    		return curNode;
	    	}
	    	else {
	    		IAVLNode child = this;
	    		IAVLNode parent = this.getParent();
	    		while (parent != null && parent.getRight() == child) {
	    			child = parent;
	    			parent = parent.getParent();
	    		}
	    		return parent;
	    	}
	    }
	    
	    public IAVLNode getPredecessor() {
	    	if (this.getLeft() != null) {
	    		IAVLNode curNode = this.getLeft();
	    		while (curNode.getRight() != null){
	    			curNode = curNode.getRight();
	    		}
	    		return curNode;
	    	}
	    	else {
	    		
	    	}
	    }
	    
	    public int getSize() 
	    {
	    	return size;
	    }
	    
	    public void setSize(int size) {
	    	this.size = size;
	    }
	    
	    public Item getItem() 
	    {
	    	return item;
	    }
  }
}



