    public class Node extends GameObject
   {
   
      Node next;
       public Node(double x,double y, Node n)
      {
         super(x,y);
         next=n;
      }
      public Node next()
   	{
   	return next;
   	}
	public String toString()
	{
	return"("+getX()+", "+getY()+")";
	}
   }