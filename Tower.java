   import java.awt.*;
   import javax.swing.ImageIcon;
   import java.util.Iterator;
    public class Tower extends GameObject implements Clickable
   {
	/**The cost of this Tower**/
	 int cost=100;
	/**The radius of the range of the Tower**/
      double radius=150;
		/**The cooldown time**/
      int cooldown=100;
		/**The amount of damage the Bullet objects that this Tower emits**/
      int damage=10;
		/**The value tracking the reload time**/
      int reload=20;
		/**The NewScreen that this Tower is in**/
      NewScreen screen;
		/**If true, the tower will shoot at the Enemy farthest down the path, else it will shoot at the closest**/
      boolean lockedOnToFront=true;
      /**The Enemy that this Tower is shooting at**/
		Enemy lockedOn=null;
       public Tower(double x, double y, NewScreen s)
      {
         super(x,y);
         screen=s;
      }
       public Bullet shoot(double x, double y)
      {
         return new Bullet(x,y,this.getX()+(width/2),this.getY()+(height/2), 10);
      }
       public void update()
      {
      //firingSequence()
         reload++;
         if(reload>=20)
         {
           // if(lockedOn==null)
            {
               Iterator<Enemy> it=screen.getEnemies().iterator();
               while(it.hasNext())
               {
                  Enemy temp=it.next();
                  double enDistance=distanceFormula(this.getX()-(width/2), this.getY()-(height/2), temp.getX()-(temp.width/2), temp.getY()-(temp.height/2));
                  if(enDistance<radius+temp.getWidth())
                  {              
                     lockedOn=temp;
                     if(lockedOnToFront)
                        break;  
                  }    
               }
            //}
            // else          
            //{
               if(lockedOn!=null)
               {
                  double enDistance=distanceFormula(this.getX()-(width/2), this.getY()-(height/2), lockedOn.getX()-(lockedOn.width/2), lockedOn.getY()-(lockedOn.height/2));
                  if(enDistance>radius+lockedOn.getWidth())
                     lockedOn=null;
                  else
                  {
                     screen.addBullet(shoot(lockedOn.getX()+(int)lockedOn.width/2, lockedOn.getY()+(int)lockedOn.height/2));
                     reload=0;
                  }
               }
            }
         }
         if(lockedOn!=null&&lockedOn.removable)
            lockedOn=null;
         
      }
       public void draw(Graphics g)
      {
         g.setColor(Color.ORANGE);
         g.drawOval((int)getX()-(int)radius+(int)width/2, (int)getY()-(int)radius+(int)height/2, (int)radius*2, (int)radius*2);
         g.setColor(Color.BLUE);
         g.fillRect((int)getX()+1,(int)getY()+1,(int)height-2,(int)width-2);
      }
       public String toString()
      {
         return "Tower: ("+getX()+", "+getY()+")";
      }
			public void click()
		{
		
		}
   }