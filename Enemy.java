   import java.awt.*;
   import javax.swing.ImageIcon;  
    public class Enemy extends GameObject implements Clickable
   {
   /**The amount of cash awarded from defeating this enemy**/
      int award= 10;
   /**The amount of health this Enemy has**/
      double health;
   	/**The max amount of health this enemy can have**/
      double maxHealth;
   	/**The change in x**/
      double dx;
   	/**The change in y**/
      double dy;
   	/**The movement speed of this Enemy**/
      double speed=2;
   	/**The next node to head to**/
      Node next;
   	/**This distance from the previous node to the current target node**/
      double dist;
   	/**The distance traveled from the previous node**/
      double distTraveled=0;
   	/**The color of the enemy**/
      Color color=Color.BLUE;
   	/**Says if this Enemy was destroyed by the player or not**/
      boolean killed=false;
   		/**Says if this Enemy is slowed or not**/
      boolean slowed=false;
   		/**Says if this Enemy is poisoned or not**/
      boolean poisoned=false;
   	/**
   	Creates a new Enemy
   	Precondition: Node has at least one more node.
   	@param health the max health of the enemy
   	@param node the starting Node
   	**/
   	
       public Enemy(double health, Node node)
      {
         super(node.getX(),node.getY());
         this.health=health;
         maxHealth=health;
         trail(node);
         dist=distanceFormula(getX()+getWidth(), getY()+getHeight(),next.getX()+next.getWidth(), next.getY()+next.getHeight());
      
      	
      }
   	/**
   	Creates a new Enemy with a given speed
   	Precondition: Node has at least one more node.
   	@param health the max health of the enemy
   	@param node the starting Node
   	@param speed the selected speed of the Enemy
   	**/
       public Enemy(double speed, double health, Node node)
      {
         super(node.getX(),node.getY());
         this.health=health;
         this.speed=speed;
         trail(node);
         dist=distanceFormula(getX()+getWidth(), getY()+getHeight(),next.getX()+next.getWidth(), next.getY()+next.getHeight());
      
      
      }
   	/**Gets the health of the Enemy
   	@return the health of this Enemy
   	**/
       public double getHealth()
      {
         return health;
      }
   /**Updates the position of the Enemy**/
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
      /**Calculates the change in x and y of the Enemy towards the next node**/
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
      	/**Draws the Enemy
   	@param g the Graphics object that does the drawing
   	**/
       public void draw(Graphics g)
      {
         g.setColor(color);
         g.fillRect((int)getX()/**-(int)getWidth()/2**/,(int)getY()/**-(int)getHeight()/2**/,(int)height,(int)width);
         if(!color.equals(Color.RED));
         color=Color.RED;
      }
      /**Subtracts the damage dealt by a Bullet object from the current health, and sets itself to be removed if its health is less than or equal to zero
   	@param damage the amount of damage being dealt to this Enemy
   	**/
       public void damage(double damage)
      {
         health-=damage;
         if(health<=0)
         {
            setRemovable(true);
            killed=true;
         }
         color=Color.RED.darker();
      }
      /**Displays the current health over the max health of this enemy**/
       public void click()
      {
      
      }
   }