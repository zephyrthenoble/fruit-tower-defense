   import java.awt.*;
   import javax.swing.ImageIcon;
    public class GameObject
   {
      double xPos;
      double yPos;
      double width;
      double height;
		boolean removable=false;
       public GameObject(double x, double y)
      {
         xPos=x;
         yPos=y;
			width=50;
			height=50;
      }
       public GameObject(double x, double y, double w, double h)
      {
         xPos=x;
         yPos=y;
         width=x;
         height=y;
      }
		public boolean isRemovable()
		{
		return removable;
		}
       public double getX()
      {
         return xPos;
      }
       public double getY()
      {
         return yPos;
      }
       public double getWidth()
      {
         return width;
      }
       public double getHeight()
      {
         return height;
      }
       public void setX(double x)
      {
         xPos=x;
      }
       public void setY(double y)
      {
         yPos=y;
      }
		public void setRemovable(boolean b)
		{
		removable=b;
		}
       public boolean intersects(GameObject check)
      {
         if(((getX()>=check.getX()&& getX()<=check.getX()+check.getWidth()) || (getX()+getWidth()>=check.getX()&&getX()+getWidth()<=check.getX()+check.getWidth()))
         &&(           (getY()>=check.getY()&& getY()<=check.getY()+check.getWidth()) || (getY()+getHeight()>=check.getY()&&getY()+getHeight()<=check.getY()+check.getHeight())))
            return true;
      
         return false;
      }
   /**Draws a black rectange at the GameObjects current x and y positions, using the width and height of the GameObject.
   @param myBuffer the Graphics object that draws the square.**/
       public void draw(Graphics myBuffer) 
      {
         myBuffer.setColor(Color.black);
         myBuffer.fillRect((int)getX(),(int)getY(),(int)height,(int)width);
      
      }
      /**Updates GameObject data if it is required.**/
       public void update()
      {
      
      }
		public String toString()
		{
		return "("+xPos+", "+yPos+")";
		}
   }