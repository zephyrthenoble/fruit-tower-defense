    public class Node extends GameObject
   {
   /**The next Node linked to this Node**/
      Node next;
   	/**Instantiates a new Node
   	@param x the x position
   	@param y the y position
   	@param n the next Node that this Node will be linked to
   	**/
       public Node(double x,double y, Node n)
      {
         super(x,y);
         next=n;
      }
   	/**Gets the next Node
   	@return the next Node linked to this Node
   	**/
       public Node next()
      {
         return next;
      }
   	/**Gets a String representation of the position of the Node
   	@return a String with the coordinates of the Node inside of it
   	**/
       public String toString()
      {
         return"("+getX()+", "+getY()+")";
      }
   /**Sets the next Node to a different Node
   @param node the new next Node**/
       public void setNext(Node node)
      {
         next=node;
      }
   	/**States if a Node is the same as another Node, based upon its position
   	@param node the Node being compared to this Node
   	
   	@return true if this Node is the same as node, otherwise returns false
   	**/
       public boolean equals(Node node)
      {
         if(node.getX()==this.getX()&&node.getY()==this.getY())
            return true;
         return false;
      }
   }