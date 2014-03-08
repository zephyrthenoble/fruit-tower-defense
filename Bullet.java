      import java.awt.*;
   import javax.swing.ImageIcon;
   public class Bullet extends GameObject
   {
      double dx;
      double dy;
      double speed=50;
       public Bullet(double x, double y, double otherX, double otherY)
      {
         super(x,y);
         dx=(x-otherX)/speed;
         dy=(y-otherY)/speed;
      }
       public void update()
      {
         setX(getX()+dx);
         setY(getY()+dy);
      }

   }