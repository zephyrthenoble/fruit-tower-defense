   import java.awt.*;
   import javax.swing.ImageIcon;  
    public class Enemy extends GameObject
   {
      double health;
      double dx;
      double dy;
      double speed=3;
      Node next;
   	/**Precondition: Node has at least one more node.**/
       public Enemy(double health, Node node)
      {
         super(node.getX(),node.getY());
         this.health=health;
         trail(node);
      	
      }
       public double getHealth()
      {
         return health;
      }
       public void update()
      {
         setX(getX()+dx);
         setY(getY()+dy);
         if(this.intersects(next))
            trail(next);
         if(next==null)
            setRemovable(true);
      	
      }
       public void trail(Node current)
      {
         next=current.next();
         if(next==null)
            return;
         double xLength=(next.getX()-getX());
         double yLength=(next.getY()-getY());
         double angle=Math.atan(yLength/xLength);
         dx=Math.cos(angle)*speed;
         dy=Math.sin(angle)*speed;
      	
      }
       public void draw(Graphics g)
      {
         g.setColor(Color.RED);
         g.fillRect((int)getX(),(int)getY(),(int)height,(int)width);
      
      
      }
   
   }