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
         width=w;
         height=h;
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
      //left point
         boolean lrX=getX()>=check.getX();
         boolean llX=getX()<=check.getX()+check.getWidth();
      //top
         boolean lrY=getY()>=check.getY();
         boolean llY= getY()<=check.getY()+check.getHeight();
      //right point
         boolean rrX=getX()+getWidth()>=check.getX();
         boolean rlX=getX()+getWidth()<=check.getX()+check.getWidth();
      //bot
         boolean rrY=getY()+getHeight()>=check.getY();
         boolean rlY=getY()+getHeight()<=check.getY()+check.getHeight();
         if(((lrX && llX)|| (rrX&&rlX))&&((lrY&&llY) || (rrY&&rlY)))
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
   	
       public static double distanceFormula(double x, double y, double otherX, double otherY)
      {
         return Math.sqrt(Math.pow((otherX-x), 2)+Math.pow((otherY-y), 2));
      }
   
       public String toString()
      {
         return "("+xPos+", "+yPos+")";
      }
   }