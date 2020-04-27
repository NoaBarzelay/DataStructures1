package TreeList;

/**
 *
 * Circular list
 *
 * An implementation of a circular list with  key and info
 *
 */
public class CircularList{

	public Item[] array;
    public int maxLen;
    public int length;
    public int start;

    
    /**
    * public CircularList (int maxLen)
    *
    * constructor initiates fields 
    * (length and start initiated to 0 defaultivly) 
    * 
    */
    public CircularList (int maxLen){
        this.maxLen = maxLen;
        this.array = new Item[maxLen];
    }
    
    
    /**
    * public Item retrieve(int i)
    *
    * returns the item in the ith position if it exists in the list.
    * otherwise, returns null
    * 
    */
    public Item retrieve(int i)
    {
        if (i < 0 || i >= length) {
            return null;
        }
        else {
            return array[(start+i)%maxLen];
        }
    }

    
    /**
    * public int insert(int i, int k, String s)
    *
    * inserts an item to the ith position in list  with key k and  info s.
    * returns -1 if i<0 or i>n  or n=maxLen otherwise return 0.
    * 
    */
    public int insert(int i, int k, String s) {
    	if (length == maxLen || i < 0 || i > length){
            return -1;
        }
        //position is closer to end of list -> move following items right
        if (i > length - i) {
            int j = (start + length + maxLen - 1) % maxLen;
            while (j != (start + i+maxLen - 1) % maxLen) {
                array[(j + 1) % maxLen] = array[j];
                j = (j+maxLen - 1) % maxLen;
            }
        }
        //position is closer to beginning of list -> move preceding items left
        else { 
            int j = start;
            while (j != (start + i) % maxLen) {
                array[(j +maxLen- 1) % maxLen] = array[j];
                j = ((j + 1) % maxLen);
            }
            start = (start+maxLen - 1) % maxLen;
        }
        Item new_item = new Item(k, s);
        array[(start + i) % maxLen] = new_item;
        length += 1;
        return 0;
    }


    /**
    * public int delete(int i)
    *
    * deletes an item in the ith position from the list.
    * returns -1 if i<0 or i>n-1 otherwise returns 0.
    * 
    */
    public int delete (int i) {
    	if (length == 0 || i < 0 || i > length - 1) {
            return -1;
    	}
        int j = (start + i) % maxLen;
        //position is closer to end of list -> move following items left
        if (i > length - i) {
            while (j != (start + length + maxLen - 1) % maxLen){
                array[j] = array[(j + 1) % maxLen];
                j = (j + 1) % maxLen;
            }
        }
      //position is closer to beginning of list -> move preceding items right
        else {
            while (j != start){
            array[j] = array[(j + maxLen - 1) % maxLen];
            j=(j + maxLen - 1) % maxLen;
        }
        start=(start+1)%maxLen;}
        length-=1;
        return (0);
        
    }
 }
 
 
 
