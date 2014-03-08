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
   }