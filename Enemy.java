   import java.awt.*;
   import javax.swing.ImageIcon;  
    public class Enemy extends GameObject implements Clickable
   {
      double health;
      double dx;
      double dy;
      double speed=2;
      Node next;
      double dist;
      double distTraveled=0;
      Color color=Color.BLUE;
   	/**Precondition: Node has at least one more node.**/
       public Enemy(double health, Node node)
      {
         super(node.getX(),node.getY());
         this.health=health;
         trail(node);
         dist=distanceFormula(getX()+getWidth(), getY()+getHeight(),next.getX()+next.getWidth(), next.getY()+next.getHeight());
      
      	
      }
       public Enemy(double speed, double health, Node node)
      {
         super(node.getX(),node.getY());
         this.health=health;
         this.speed=speed;
         trail(node);
         dist=distanceFormula(getX()+getWidth(), getY()+getHeight(),next.getX()+next.getWidth(), next.getY()+next.getHeight());
      
      
      }
       public double getHealth()
      {
         return health;
      }
       public void update()
      {
         setX(getX()+dx);
         setY(getY()+dy);
         distTraveled+=speed;
         if(distTraveled>=dist&&next!=null)
         {
            trail(next);
            if(next!=null)
            {
               dist=distanceFormula(getX()+getWidth(), getY()+getHeight(),next.getX()+next.getWidth(), next.getY()+next.getHeight());
               distTraveled=0;
            }
         }  
         if(next==null)
         {
            setRemovable(true);
         //	System.out.println("Removed");
         }
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
         g.setColor(color);
         g.fillRect((int)getX()/**-(int)getWidth()/2**/,(int)getY()/**-(int)getHeight()/2**/,(int)height,(int)width);
         if(!color.equals(Color.RED));
         color=Color.RED;
      }
       public void damage(double damage)
      {
         health-=damage;
         if(health<=0)
            setRemovable(true);
         color=Color.RED.darker();
      }
			public void click()
		{
		
		}
   }