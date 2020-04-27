package TreeList;

/**
 *
 * AVLTree
 *
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 *
 */
public class AVLTree{

	public IAVLNode root;
	public IAVLNode min;
	public IAVLNode max;
	
    /**
    * public boolean empty()
    *
    * returns true if and only if the tree is empty
    *O(1)
    */
	public boolean empty() 
	{
    return root == null;
	}
  
	
	/**
	* public IAVLNode searchNode(int k)
	*
	* returns node with key k if exists else null
	*O(log(n))
	*/
	public IAVLNode searchNode(int k)
	{
		IAVLNode node = root;
		//if node has key return node
		//else move left if k is smaller then node.key
		//or move right if k is bigger then node.key
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
		//node was not found
		return null;
	}

	
   /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   * O(log(n))
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
	
	
	/**
	* public void RotateLeft(IAVLNode node)
	*
	* rotates subtree of node the left
	* O(1)
	*/
	public void RotateLeft(IAVLNode node) 
	{
		IAVLNode newParent = node.getRight();
		if (node == root) {
			root = newParent;
		}
		node.setRight(newParent.getLeft());
		if (newParent.getLeft() != null) {
			newParent.getLeft().setParent(node);
		}
		newParent.setParent(node.getParent());
		if (node.getParent() == null) {
			this.root = newParent;
		}
		else if (node.getParent().getLeft() == node) {
			node.getParent().setLeft(newParent);
		}
		else {
			node.getParent().setRight(newParent);
		}
		newParent.setLeft(node);
		node.setParent(newParent);
		((AVLNode) node).setHeightAndSize();
		((AVLNode) newParent).setHeightAndSize();
		return ;
	}
	
	
	/**
	* public void RotateRight(IAVLNode node)
	*
	* rotates subtree of node to the right
	* O(1)
	*/
	public void RotateRight(IAVLNode node) 
	{
		IAVLNode newParent = node.getLeft();
		if (node == root) {
			root = newParent;
		}
		node.setLeft(newParent.getRight());
		if (newParent.getRight() != null) {
			newParent.getRight().setParent(node);
		}
		newParent.setParent(node.getParent());
		if (node.getParent() == null) {
			root = newParent;
		}
		else if (node.getParent().getLeft() == node) {
			node.getParent().setLeft(newParent);
		}
		else {
			node.getParent().setRight(newParent);
		}
		newParent.setRight(node);
		node.setParent(newParent);
		((AVLNode) node).setHeightAndSize();
		((AVLNode) newParent).setHeightAndSize();
		return ;
	}
	
	
	/**
	* public int fixTree(IAVLNode node, boolean delete)
	*
	* goes up the tree and updates height and sizes
	* and if BF of node >2 or <-2 then rotates to fix
	* returns number of rotations made
	* O(log(n))
	*/
	public int fixTree(IAVLNode node) 
	{
		int rotations = 0;
		while (node != null) {
			//find BF of node to know if AVLTree needs fix
			int nodeBF = ((AVLNode) node).getBF();
			if (nodeBF == -2) {
				//needs fix. find child BF to choose rotation and rotate
				int rightBF = ((AVLNode) node.getRight()).getBF();
				if(this.getRoot() == node) {
					this.setRoot(node.getRight());
				}
				if (rightBF == -1 || rightBF == 0) {
					this.RotateLeft(node);
					rotations += 1;
				}
				else {
					this.RotateRight(node.getRight());
					this.RotateLeft(node);
					rotations += 2;
				}
			}
			else if (nodeBF == 2) {
				//needs fix. find child BF to choose rotation and rotate
				if(this.getRoot() == node) {
					this.setRoot(node.getLeft());
				}
				int leftBF = ((AVLNode) node.getLeft()).getBF();
				if (leftBF == 1 || leftBF == 0) {
					this.RotateRight(node);
					rotations += 1;
				}
				else {
					this.RotateLeft(node.getLeft());
					this.RotateRight(node);
					rotations += 2;
					}
				}
			//update node size and height then move to parent
			((AVLNode) node).setHeightAndSize();
			node = node.getParent();  
		}
		return rotations;
	}
 
	
	/**
    * public int insert(int k, String i)
    *
    * inserts an item with key k and info i to the AVL tree.
    * the tree must remain valid (keep its invariants).
    * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
    * returns -1 if an item with key k already exists in the tree.
    * O(log(n))
    */
	public int insert(int k, String i) 
	{
		IAVLNode curNode = root;
		IAVLNode parentNode = null;
		//find parent node for new node
		//if node with key exists return -1
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
		//create node to insert
		curNode = new AVLNode(new Item(k, i), parentNode, 0);
		//if k is smaller then tree.min or tree is empty then update min
		if (empty() || min.getKey() > k) {
			this.min = curNode;
		}
		// if k is bigger then tree.max or tree is empty then update max
		if (empty() || max.getKey() < k) {
			max = curNode;
		}
		//connect node to parent if exists else set as root
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
		return fixTree(parentNode);
	}

	
	/**
	* public void byPass(IAVLNode deleteNode, IAVLNode child)
	*
	* given deleteNode and its child (deleteNode only has one child)
	* byPasses deleteNode by making deleteNode.parent -> child.parent
	* and disconnect deleteNode from tree
	*O(1)
	*/
	public void byPass(IAVLNode deleteNode, IAVLNode child) 
	{
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
   
	
	/**
	* public void replaceNode(IAVLNode deleteNode, IAVLNode replacer)
	*
	* replaces deleteNode with replacer in tree
	*O(1)
	*/
	public void replaceNode(IAVLNode deleteNode, IAVLNode replacer) {
		//set replacer's new connections
		if (deleteNode == root) {
			root = replacer;
		}
		replacer.setRight(deleteNode.getRight());
		replacer.setLeft(deleteNode.getLeft());
		if (replacer.getRight() != null){
			replacer.getRight().setParent(replacer);
		}
		if (replacer.getLeft() != null) {
			replacer.getLeft().setParent(replacer);
		}
		replacer.setParent(deleteNode.getParent());
		//set deleteNode's parent as replacer's parent
		if (replacer.getParent() == null) {
			root = replacer;
		}
		else if (replacer.getParent().getLeft() == deleteNode) {
			replacer.getParent().setLeft(replacer);
		}
		else {
			replacer.getParent().setRight(replacer);
		}
		((AVLNode) replacer).setHeightAndSize();
		//disconnect deleteNode
		deleteNode.setLeft(null);
		deleteNode.setRight(null);
		deleteNode.setParent(null);
	   	}
      
	
	/**
   	* public int deleteByNode(IAVLNode deleteNode)
   	*
   	* deletes a given node that is in the tree while keeping tree balanced
   	* returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   	* returns -1 if an item with key k was not found in the tree.
   	* O(log(n))
   	*/
	public int deleteByNode(IAVLNode deleteNode) {
		//if deleteNode is min or max then update
		if (deleteNode == min) {
			if (deleteNode.getRight() != null) {
				//deleteNode is min -> has no left child
				//tree is AVL -> right child has no children
				min = deleteNode.getRight();
			}
			else {
				//deleteNode has no children -> new min is deleteNode.parent
				min = deleteNode.getParent();
			}
		}
		if (deleteNode == max) {
			if ( deleteNode.getLeft() != null) {
				//deleteNode is max -> has no right child
				//tree is AVL -> left child has no children
				max = deleteNode.getLeft();
			}
			else {
				// deleteNode has no children -> new max is deleteNode.parent
				max = deleteNode.getParent();
			}
		}
		IAVLNode parentNode = deleteNode.getParent();
		//if deleteNode has two children then find successor -> remove successor ->
		//replace deleteNode with successor -> fix tree from deleteNode
		if (deleteNode.getLeft() != null && deleteNode.getRight() != null) {
			IAVLNode successor = ((AVLNode) deleteNode).getSuccessor();
			parentNode = successor.getParent();
			this.byPass(successor, successor.getRight());
			this.replaceNode(deleteNode, successor);
			fixTree(successor);
		}
		//if deleteNode is leaf then byPass (if root then remove instead)
		if (deleteNode.getLeft() == null && deleteNode.getRight() == null) {
			if (deleteNode == root) {
				root = null;
				deleteNode.setParent(null);
				deleteNode.setLeft(null);
				deleteNode.setRight(null);
			}
			else{
				this.byPass(deleteNode, null);
			}
		}
		//if deleteNode has one child then byPass with child
		else if (deleteNode.getLeft() == null || deleteNode.getRight() == null) {
			IAVLNode child = (deleteNode.getLeft() == null) ? deleteNode.getRight() : deleteNode.getLeft();
			if (deleteNode == root) {
				root = child;
			}
			this.byPass(deleteNode, child);
		}
		//fix tree from successor if deleteNode has two children
		//or from delteNode otherwise
		return fixTree(parentNode);
	}
	
	
	/**
   	* public int delete(int k)
   	*
   	* deletes an item with key k from the binary tree, if it is there;
   	* the tree must remain valid (keep its invariants).
   	* returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   	* returns -1 if an item with key k was not found in the tree.
   	* O(log(n))
   	*/
	public int delete(int k)
	{
		IAVLNode deleteNode = this.searchNode(k);
		if (deleteNode == null) {
			return -1;
		}
		else {
			return deleteByNode(deleteNode);
		}
	}

	
    /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty
    * O(1)
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
    * O(1)
    */
	public String max()
	{
		return (this.max == null) ? null : this.max.getValue() ;
	}

	
	/**
	* public int keysToArrayRec(int[] arr, IAVLNode node, int index)
	*
	* recursive function which fills given array arr with keys by index going
	* through tree by in-order traversal and returns next index to fill
	* O(n)
	*/
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
	* O(n)
	*/
	public int[] keysToArray()
	{
		int[] arr = new int[this.size()];
		keysToArrayRec(arr, this.root, 0);
		return arr;
	}

	
	/**
	* public int infoToArrayRec(String[] arr, IAVLNode node, int index)
	*
	* recursive function which fills given array arr with info by index going
	* through tree by in-order traversal and returns next index to fill
	* O(n)
	*/
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
	* O(n)
	*/
	public String[] infoToArray()
	{
		String[] arr = new String[this.size()];
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
	* O(1)
	*/
	public int size()
	{	
		if (root == null) {
			return 0;
		}
		else{
			return ((AVLNode) this.root).getSize();
		}
	}
   
	
    /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    *
    * precondition: none
    * postcondition: none
    * O(1)
    */
	public IAVLNode getRoot()
	{
		return root;
	}
   
	
	/**
	    * public void setRoot(IAVLNode node)
	    *
	    * sets the root AVL node
	    *O(1)
	    */
	public void setRoot(IAVLNode node)
	{
		this.root = node;
	}
   
	
	/**
	* public IAVLNode treeSelectRec(IAVLNode node, int rank) 
	*
	* recursive functions which goes down through tree
	* and returns node with rank (== node.left.size + 1)
	* if exists else null
	*O(log(n))
	*/
	public IAVLNode treeSelectRec(IAVLNode node, int rank) 
	{
		//determine node's rank -> if equals given rank return node
		// else move recursivly to child with possible equal rank
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
   
	
	/**
	* public IAVLNode treeSelect(int rank) 
	*
	* return node with given rank if exists in tree else null
	*O(log(n))
	*/
	public IAVLNode treeSelect(int rank) 
	{
		return treeSelectRec(root, rank); 
	}
   
	
	/**
	* public int treeRank(IAVLNode node)
	*
	* return rank of given node
	*O(log(n))
	*/
	public int treeRank(IAVLNode node)
	{
		//compute initial rank
		int rank = 1 + ((AVLNode) node.getLeft()).getSize();
		//add to rank all nodes smaller then node which are in parent subtrees
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
	  
	  /**
	  * O(1)
	  */
	  public AVLNode(Item item, IAVLNode parent, int height) 
	  {
		  this.item = item;
		  this.height = height;
		  this.parent = parent;
		  this.size = 1;
	  }
	  
	  /**
	  * O(1)
	  */
	  public int getKey()
	  {
		  return item.getKey(); 
	  }
	  
	  /**
	  * O(1)
	  */
	  public String getValue()
	  {
		  return item.getInfo(); 
	  }
	  
	  /**
	  * O(1)
	  */
	  public void setLeft(IAVLNode node)
	  {
		  left = node;
		  return ;
	  }
	  
	  /**
	  * O(1)
	  */
	  public IAVLNode getLeft()
	  {
		  return left;
	  }
	  
	  /**
	  * O(1)
	  */
	  public void setRight(IAVLNode node)
	  {
		  right = node;
		  return ; 
	  }
	  
	  /**
	  * O(1)
	  */
	  public IAVLNode getRight()
	  {
		  return right; 
	  }
	  
	  /**
	  * O(1)
      */
	  public void setParent(IAVLNode node)
	  {
		  this.parent = node;
		  return ; 
	  }
	  
	  /**
	  * O(1)
	  */
	  public IAVLNode getParent()
	  {
		  return parent;
	  }
	  
	  /**
	  * O(1)
	  */
	  public void setHeight(int height)
	  {
		  this.height = height;
		  return ; 
	  }
	  
	  /**
	  * O(1)
	  */
	  public int getHeight()
	  {
		  return height; 
	  }
	  
	  /**
	  * O(1)
	  */
	  public void setSize(int size)
	  {
		  this.size = size;
		  return ;
	  }
	  
	  /**
	  * O(1)
	  */
	  public int getSize() 
	  {
		  return size;
	  }
	  
	  /**
	  * O(1)
	  */
	  public Item getItem() 
	  {
		  return item;
	  }
	  
	  /**
	  * O(1)
	  */
	  public int getBF()
	  {
		  //if left == null or right == null then height == -1
		  int leftHeight = (this.left == null) ? -1 : this.left.getHeight();
		  int rightHeight = (this.right == null) ? -1 : this.right.getHeight();
		  return leftHeight - rightHeight;
	  }
	  
	  /**
	  * O(log(n))
	  */
	  public IAVLNode getSuccessor() 
	  {
		  //if node has right subtree return it's leftmost node
		  if (this.getRight() != null) {
			  IAVLNode curNode = this.getRight();
			  while (curNode.getLeft() != null) {
				  curNode = curNode.getLeft();
			  }
			  return curNode;
		  }
		  //else return the first parent from the left
		  //or null if it does not exist
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
	  
	  /**
	  * O(log(n))
	  */
	  public IAVLNode getPredecessor() 
	  {
		  //if node has left subtree return it's rightmost node
		  if (this.getLeft() != null) {
			  IAVLNode curNode = this.getLeft();
			  while (curNode.getRight() != null){
				  curNode = curNode.getRight();
			  }
			  return curNode;
		  }
		  //else return the first parent from the right
		  //or null if it does not exist
		  else {
			  IAVLNode child = this;
			  IAVLNode parent = this.getParent();
			  while (parent != null && parent.getLeft() == child) {
				  child = parent;
				  parent = parent.getParent();
			  }
			  return parent;
		  }
	  }
	  
	  /**
	  * O(1)
	  */
	  public void setHeightAndSize() 
	  {
		  //set height of node as max of childern's height + 1
		  // if child == null then it's height is -1
		  int leftHeight = (left == null) ? -1 : left.getHeight();
		  int rightHeight = (right == null) ? -1 : right.getHeight();
		  setHeight(Math.max(leftHeight, rightHeight) + 1);
		  //set size of node as size of children + 1
		  // if child == null then it's size is 0
		  int leftSize = (left == null) ? 0 : ((AVLNode) left).getSize();
		  int rightSize = (right == null) ? 0 : ((AVLNode) right).getSize();
		  setSize(leftSize + rightSize + 1);
	  }
	  
	  
  }
 }
