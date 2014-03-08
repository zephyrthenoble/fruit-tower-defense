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
   	/**
   	Checks to see if a point is inside the GameObject
   	@param x The x point
   	@param y The y point
   	**/
       public boolean isClickedOn(int x, int y)
      {
         boolean XinRight=getX()<x;
         boolean XinLeft=getX()+getWidth()>x;
         boolean YinTop=getY()<y;
         boolean YinBot=getY()+getHeight()>y;
         if(XinRight&&XinLeft&&YinTop&&YinBot)
            return true;
         return false;
      }
   	/**
   	Checks if a GameObject intersects another GameObject
   	@param check The GameObject that you are checking to see if it intersects with this GameObject
   	**/
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
   	/**
   	Finds the distance between two points
   	@param x The first x point
   	@param y The first y point
   	@param otherX The second x point
   	@param otherY The second y point
   	@return dist The distance between the points
   	**/
       public static double distanceFormula(double x, double y, double otherX, double otherY)
      {
         double dist= Math.sqrt(Math.pow((otherX-x), 2)+Math.pow((otherY-y), 2));
      
         return dist;
      }
   /**
   Returns the x and y positions
   @return str The string that contains the x and y points
   **/
       public String toString()
      {
         String str="("+xPos+", "+yPos+")";
         return str;
      }
   }