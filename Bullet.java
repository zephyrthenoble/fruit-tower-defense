   import java.awt.*;
   import javax.swing.ImageIcon;
   import java.util.*;  
   import java.awt.geom.AffineTransform;
    public class Bullet extends GameObject
   {
   /**The angle of the Bullet**/
      double angle;
   /**The change in x**/
      double dx;
   	/**The change in y**/
      double dy;
   	/**The starting x position**/
      double startX;
   	/**The starting y position**/
      double startY;
   	/**The speed of the Bullet**/
      double speed=15;
   	/**The distance traveled**/
      double distance=0;
   	/**The amound of damage done by the bullet**/
      double damage=0;
   	/**The color of the bullet**/
      Color color=Color.GREEN;
   	/**Determines what buff the Bullet has**/
      int buff=0;
   	/**The image of this Bullet**/
      ImageIcon image=new ImageIcon(getClass().getResource("Images/Towers/arrow.png"));
   /**Constructs a new Bullet
   @param x The x position that the Bullet is moving toward
   @param y The y position that the Bullet is moving toward
   @param otherX The starting x position
   @param otherY The starting y position
   @param dmg The damage that the bullet will do
   **/
       public Bullet(double x, double y, double otherX, double otherY, double dmg, int buff)
      {
         super(otherX,otherY, 5,5);
         startX=otherX;
         startY=otherY;
         damage=dmg;
         this.buff=buff;
         if(this.buff==0)
         {
            image=new ImageIcon(getClass().getResource("Images/Towers/genericBullet.png"));
            this.width=14;
            this.height=14;
         }
         if(this.buff==1)
         {
            image=new ImageIcon(getClass().getResource("Images/Towers/iceBullet.png"));
            speed=8;
         }
         else if(this.buff==2)
         {
            image=new ImageIcon(getClass().getResource("Images/Towers/poisonBullet.png"));
            speed=7;
         }  
      	//double xLength=(otherX-getX());
         //double yLength=(otherY-getY());
         double xLength=(x-getX());
         double yLength=(y-getY());
         
         //if(getX()<x)
            //xLength*=-1;
         //if(getY()<y)
            //yLength*=-1;
         angle=(Math.atan(yLength/xLength));
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
       public void draw(Graphics2D g)
      {
         if(buff==0)
            g.setColor(Color.YELLOW);
         else if(buff==1)
            g.setColor(Color.BLUE);
         else
            g.setColor(Color.GREEN);
         AffineTransform affineTransform = new AffineTransform();
      //set the translation to the mid of the component
         
         affineTransform.setToTranslation(getX(),getY());
      //affineTransform.setToTranslation(0,0);
      //rotate with the anchor point as the mid of the image
         boolean flipped=false;
         if(dx<0)//||dy<0)
            //if(!(dx<0&&dy<0))
         {
            angle-=Math.PI;
            flipped=true;
         }
         if(buff==0)
      	angle+=Math.PI/4.0;
        int center=(int)(this.width/2.0);
        if(this.width%2.0!=0.0)
        center++;
        
         affineTransform.rotate(angle+(Math.PI/2.0), center, center);
      //draw the image using the AffineTransform
        
         g.drawImage(image.getImage(), affineTransform, null);
         if(flipped)
            angle+=Math.PI;
      
        // g.fillRect((int)getX(),(int)getY(),5,5);
      }
   	/**
   	Deals damage to an enemy if this Bullet intersects it, and also sets it to be removed. This will also transfer the Bullet's buff to the Enemy it hits.
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
               if(buff==1)
               {
                  temp.slowed=true;
                  temp.slowCounter=50; 
               }
               else if(buff==2)
               {
                  temp.poisoned=true;
                  temp.poisonCounter=100;
               }  
               color=Color.PINK;
               this.setRemovable(true);
               break;
            }
         }
      }
   }