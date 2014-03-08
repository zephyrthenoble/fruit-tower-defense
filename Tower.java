   import java.awt.*;
   import javax.swing.ImageIcon;
   import java.util.Iterator;
    public class Tower extends GameObject
   {
      double radius=150;
      int cooldown=100;
      int damage=10;
      int reload=0;
      NewScreen screen;
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
      if(reload==20)
      {
         Iterator<Enemy> it=screen.getEnemies().iterator();
         while(it.hasNext())
         {
            Enemy temp=it.next();
            double enDistance=distanceFormula(this.getX()-(width/2), this.getY()-(height/2), temp.getX()-(temp.width/2), temp.getY()-(temp.height/2));
            if(enDistance<radius+temp.getWidth())
               screen.addBullet(shoot(temp.getX()+(int)temp.width/2, temp.getY()+(int)temp.height/2));
         
         }
         reload=0;
         }
      }
       public void draw(Graphics g)
      {
         g.setColor(Color.BLUE);
         g.drawOval((int)getX()-(int)radius+(int)width/2, (int)getY()-(int)radius+(int)height/2, (int)radius*2, (int)radius*2);
         g.fillRect((int)getX(),(int)getY(),(int)height,(int)width);
      }
       public String toString()
      {
         return "Tower: ("+getX()+", "+getY()+")";
      }
   }