   import java.awt.*;
   import javax.swing.ImageIcon;
	/**A majority of classes in this game extend GameObject.
	It contains many helpful methods used to find positions and intersections of objects**/
    public class GameObject
   {
	/**The x position of the GameObject**/
      double xPos;
		/**The y position of the GameObject**/
      double yPos;
		/**The width of the GameObject**/
      double width;
		/**The height of the GameObject**/
      double height;
		/**Used to determine if this object needs removing**/
      boolean removable=false;
		/**Makes a new game object at the posistion specified
		@param x the x position of the new GameObject
		@param y the y position of the new GameObject
		**/
       public GameObject(double x, double y)
      {
         xPos=x;
         yPos=y;
         width=50;
         height=50;
      }
			/**Makes a new game object at the posistion specified, with the selected width and height
		@param x the x position of the new GameObject
		@param y the y position of the new GameObject
		@param w the width of the new GameObject
		@param h the height of the new GameObject
		**/
       public GameObject(double x, double y, double w, double h)
      {
         xPos=x;
         yPos=y;
         width=w;
         height=h;
      }
		/**Determines if a GameObject has to be removed
		@return true if the GameObject should be removed
		**/
       public boolean isRemovable()
      {
         return removable;
      }
		/**Gets the x position of the GameObject
		@return the x position
		**/
       public double getX()
      {
         return xPos;
      }
			/**Gets the y position of the GameObject
		@return the y position
		**/
       public double getY()
      {
         return yPos;
      }
			/**Gets the width of the GameObject
		@return the width
		**/
       public double getWidth()
      {
         return width;
      }
			/**Gets the height of the GameObject
		@return the height
		**/
       public double getHeight()
      {
         return height;
      }
		/**Sets the x position
		@param x the new x position
		**/
       public void setX(double x)
      {
         xPos=x;
      }
		
		/**Sets the y position
		@param y the new x position
		**/
       public void setY(double y)
      {
         yPos=y;
      }
		
		/**Sets the removability
		@param b the new boolean value used to determine if the GameObject should be removed
		**/
       public void setRemovable(boolean b)
      {
         removable=b;
      }
   	/**
   	Checks to see if a point is inside the GameObject
   	@param x The x point
   	@param y The y point
   	**/
       public boolean isClickedOn(double x, double y)
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