   import java.awt.*;
   import javax.swing.ImageIcon;  
    public class Enemy extends GameObject implements Clickable
   {
   /**The image of the enemy**/
      ImageIcon image=new ImageIcon(getClass().getResource("Images/Enemies/apple.png"));
   	/**The image shown when the enemy is damaged**/
      ImageIcon damage=new ImageIcon(getClass().getResource("Images/Enemies/damage.png"));
   	/**The image shown when the enemy is damaged**/
      ImageIcon slow=new ImageIcon(getClass().getResource("Images/Enemies/slow.png"));
   /**The image shown when the enemy is damaged**/
      ImageIcon poison=new ImageIcon(getClass().getResource("Images/Enemies/poison.png"));
      /**The amount of cash awarded from defeating this enemy**/
      int award= 10;
   	/**Used to determine how long poison lasts**/
      int poisonCounter=10;
      	/**Used to determine how long slow lasts**/
      int slowCounter=100;
   
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
      /**The unslowed movement speed of this Enemy**/
      double maxSpeed=2;
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
   	/**Used to determine if the enemy has been damaged resently**/
      boolean damaged=false;
   //old	
       /*public Enemy(double health, Node node)
      {
         super(node.getX(),node.getY());
         this.health=health;
         maxHealth=health;
         trail(node);
         dist=distanceFormula(getX()+getWidth(), getY()+getHeight(),next.getX()+next.getWidth(), next.getY()+next.getHeight());
         image=new ImageIcon(getClass().getResource("Images/Enemies/broccoli.png"));
      
      	
      }*/
      
   	   	/**
   	Creates a new Enemy
   	Precondition: Node has at least one more node.
   	@param speed the speed of the Enemy
   	@param health the max health of the enemy
   	@param node the starting Node
   	@param file the string filename of the picture that this enemy will draw
   	**/
       public Enemy(double speed,double health, Node node, String file)
      {
         super(node.getX(),node.getY());
         this.health=health;
         this.speed=speed;
         maxSpeed=speed;
         maxHealth=health;
         trail(node);
         dist=distanceFormula(getX()+getWidth(), getY()+getHeight(),next.getX()+next.getWidth(), next.getY()+next.getHeight());
         image=new ImageIcon(getClass().getResource(file));
      
      	
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
         if(poisoned&&poisonCounter>0)
         {
            damage(maxHealth/100);
            poisonCounter--;
         }
         if(poisonCounter<=0)
         {
            poisoned=false;
            poisonCounter=10;
         }
         if(slowed&&speed!=maxSpeed)
         {
            slowCounter--;
         }
         else if(slowed&&slowCounter==100)
         {
            changeSpeed(1.0/3.0);
            speed/=3.0;
            slowCounter--;
         }
         
      	
         if(slowCounter<=0)
         {
            changeSpeed(3.0);
            speed*=3.0;
            slowCounter=100;
            slowed=false;
         
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
         updateMove(speed);
      }
       public void updateMove( double spd)
      {
         if(next==null)
            return;
         double xLength=(next.getX()-getX());
         double yLength=(next.getY()-getY());
         double angle=Math.atan(yLength/xLength);
         double xang=Math.cos(angle);
         double yang=Math.sin(angle);
       
         dx=xang*speed;
         dy=yang*speed;
         if((xLength<0&&dx>0)|| (xLength>0&&dx<0))
            dx*=-1;
        
      	
         if((yLength<0&&dy>0)||(yLength>0&&dy<0))
            dy*=-1;
      	
      }
      /**Changes the speed of the Enemy
   	
   	@param spd the new speed of the Ebemy**/
       public void changeSpeed(double spd)
      {
         dx=dx*spd;
         dy=dy*spd;
      }
      	/**Draws the Enemy
   	@param g the Graphics object that does the drawing
   	**/
       public void draw(Graphics g)
      {
         //g.setColor(color);
         //g.fillRect((int)getX()/**-(int)getWidth()/2**/,(int)getY()/**-(int)getHeight()/2**/,(int)height,(int)width);
         //if(!color.equals(Color.RED));
         //color=Color.RED;
         g.drawImage(image.getImage(), (int)getX(), (int)getY(),(int)getWidth(), (int)getHeight(), null);
         if(damaged)
         {
            g.drawImage(damage.getImage(), (int)getX(), (int)getY(),(int)getWidth(), (int)getHeight(), null);
            damaged=false;
         }
         if(poisoned)
         {
            g.drawImage(poison.getImage(), (int)getX(), (int)getY(),(int)getWidth(), (int)getHeight(), null);
          
         }
         if(slowed)
            g.drawImage(slow.getImage(), (int)getX(), (int)getY(),(int)getWidth(), (int)getHeight(), null);
          
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
         damaged=true;
      }
      /**Displays the current health over the max health of this enemy**/
       public void click()
      {
      
      }
   }