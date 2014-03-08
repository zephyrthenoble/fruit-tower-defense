   import java.awt.*;
   import javax.swing.ImageIcon;
   import java.util.*;  
    public class Bullet extends GameObject
   {
	/**The change in x**/
      double dx;
		/**The change in y**/
      double dy;
		/**The starting x position**/
      double startX;
		/**The starting y position**/
      double startY;
		/**The speed of the Bullet**/
      double speed=10;
		/**The distance traveled**/
      double distance=0;
		/**The amound of damage done by the bullet**/
      double damage=0;
		/**The color of the bullet**/
      Color color=Color.GREEN;
		/**Determines what buff the Bullet has**/
		int buff=0;
		
   /**Constructs a new Bullet
	@param x The x position that the Bullet is moving toward
	@param y The y position that the Bullet is moving toward
	@param otherX The starting x position
	@param otherY The starting y position
	@param dmg The damage that the bullet will do
	**/
       public Bullet(double x, double y, double otherX, double otherY, double dmg)
      {
         super(otherX,otherY, 5,5);
         startX=otherX;
         startY=otherY;
         damage=dmg;
         //double xLength=(otherX-getX());
         //double yLength=(otherY-getY());
         double xLength=(x-getX());
         double yLength=(y-getY());
         
         //if(getX()<x)
            //xLength*=-1;
         //if(getY()<y)
            //yLength*=-1;
         double angle=(Math.atan(yLength/xLength));
        // System.out.println(""+Math.toDegrees(angle));      
      
      
         dx=Math.cos(angle)*speed; 
         dy=Math.sin(angle)*speed;
         if(x-otherX<0)
         {
            if(dx>0)
               dx*=-1;
         }
         else
         {
            if(dx<0)
               dx*=-1;
         }
         if(y-otherY<0)
         {
            if(dy>0)
               dy*=-1;
         }
         else
         {
            if(dy<0)
               dy*=-1;
         }      	//distance to target
         distance=distanceFormula(x-25, y-25, otherX-25, otherY-25);
      }
		/**Changes the position of the Bullet, and checks if it has intersected an Enemy object
		@param enemies The ArrayList of Enemy objects from NewScreen that are being examined
		**/
       public void update(ArrayList<Enemy> enemies)
      {
      
         setX(getX()+dx);
         setY(getY()+dy);
         damage(enemies);
         //if(distanceFormula(startX, startY,getX(),getY())>=distance)
         if((this.getX()<0||this.getX()>600)&&(this.getY()<0||this.getY()>600))      
         {
         
            setRemovable(true);
         }
      }
		/**Draws the Bullet
		@param g the Graphics object that does the drawing
		**/
       public void draw(Graphics g)
      {
         g.setColor(color);
         g.fillRect((int)getX(),(int)getY(),5,5);
      }
		/**
		Deals damage to an enemy if this Bullet intersects it, and also sets it to be removed
		@param enemies The ArrayList of Enemy objects from NewScreen that are being examined
		**/
       public void damage(ArrayList<Enemy> enemies)
      {
         Iterator<Enemy> it=enemies.iterator();
         while(it.hasNext())
         {
            Enemy temp=it.next();
           // if(tempintersects(this))
             //  temp.intersects(this);
            if(this.intersects(temp))
            {
               temp.damage(this.damage);
               color=Color.PINK;
               this.setRemovable(true);
            }
         }
      }
   }