   import java.awt.*;
   import javax.swing.ImageIcon;
   import java.util.Iterator;
    public class Tower extends GameObject implements Clickable
   {
	/**The image of the Tower**/
	 ImageIcon image=new ImageIcon(getClass().getResource("Images/Towers/generic.png"));

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
		/**Determines what buff the Tower has**/
		int buff=0;
		/**
		Creates a new Tower object at the given x and y positions on the given NewScreen
@param x the x position of the Tower
@param y the y position of the Tower
@param s the NewScreen that this Tower is in

		**/
       public Tower(double x, double y, NewScreen s)
      {
         super(x,y);
         screen=s;
      }
		
		/**
		Creates a new Bullet moving towards the given point from the middle of this Tower
		@param x the x position that the Bullet will move toward
		@param y the y position that the Bullet will move toward
		
		@return a new Bullet object that is moving towards the points
		**/
       public Bullet shoot(double x, double y)
      {
         return new Bullet(x,y,this.getX()+(width/2),this.getY()+(height/2), 10, buff);
      }
		/**
		Updates the tower, shooting bullets if the Tower is locked on to an Enemy
		**/
       public void update()
      {
      //firingSequence()
         reload++;
         if(reload>=20)
         {
			//ArrayList<Enemy> enemies=new ArrayList<Enemy>();
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
		/**
		Draws this Tower
		@param g the Graphics object that does the drawing on the NewScreen
		
		**/
       public void draw(Graphics g)
      {
         g.setColor(Color.ORANGE);
         g.drawOval((int)getX()-(int)radius+(int)width/2, (int)getY()-(int)radius+(int)height/2, (int)radius*2, (int)radius*2);
         g.setColor(Color.BLUE);
         /*g.fillRect*/g.drawImage(image.getImage(),/*(*/(int)getX()/*+1*/,(int)getY()/*+1*/,(int)height/*-2*/,(int)width/*-2*/, null);
      }
       public String toString()
      {
         return "Tower: ("+getX()+", "+getY()+")";
      }
		/**Brings up the Tower objects info on the StatusBar**/
			public void click()
		{
		
		}
		/**Upgrades the tower, increasing its attack radius, damage, or attack speed**/
		public void upgrade()
		{
		
		}
   }