   import java.awt.*;
   import javax.swing.ImageIcon;
    public class Tower extends GameObject
   {
      double radius=600;
      int cooldown=100;
      int damage=10;
		NewScreen screen;
       public Tower(double x, double y, NewScreen s)
      {
         super(x,y);
			screen=s;
      }
       public Bullet shoot(double x, double y)
      {
         return new Bullet(x,y,this.getX(),this.getY());
      }
       public void update()
      {
      
      }
       public void draw(Graphics g)
      {
         g.setColor(Color.BLUE);
         g.fillRect((int)getX(),(int)getY(),(int)height,(int)width);
      }
       public String toString()
      {
         return "Tower: ("+getX()+", "+getY()+")";
      }
   }