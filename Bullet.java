   import java.awt.*;
   import javax.swing.ImageIcon;
   import java.util.*;  
    public class Bullet extends GameObject
   {
      double dx;
      double dy;
      double startX;
      double startY;
      double speed=10;
      double distance=0;
      double damage=0;
      Color color=Color.GREEN;
   
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
       public void draw(Graphics g)
      {
         g.setColor(color);
         g.fillRect((int)getX(),(int)getY(),5,5);
      }
       public void damage(ArrayList<Enemy> enemies)
      {
         Iterator<Enemy> it=enemies.iterator();
         while(it.hasNext())
         {
            Enemy temp=it.next();
            if(temp.intersects(this))
               temp.intersects(this);
            if(temp.intersects(this))
            {
               temp.damage(this.damage);
               color=Color.PINK;
               this.setRemovable(true);
            }
         }
      }
   }