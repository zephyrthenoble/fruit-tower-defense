   import javax.swing.*;
   import java.awt.*;
   import java.awt.event.*;
   import java.awt.image.*;
   import java.util.ArrayList;
   import java.util.Iterator;
   import javax.swing.JOptionPane;
   import java.util.Scanner;
   import java.io.File;
   import java.io.FileNotFoundException;

    public class NewScreen extends JPanel
   {
   /**The timer that updates the game**/
      static Timer t;
   	/**The image used to draw the graphics**/ 
      public BufferedImage myImage;
   	/**The background image**/
      public static ImageIcon background;
   	/**The size of the NewScreen**/
      public static final int N=600;
   	/**The default buffer color of the background**/
      public static final Color BACKGROUND = new Color(204, 204, 204);
   	/**The Graphics object used to draw the graphics**/    
      public Graphics myBuffer;
   	/**The x posision on the component of the mouse**/    
      int xPos=0;
   	/**The y posision on the component of the mouse**/ 	
      int yPos=0;
   	/**The absolute x posision on the component of the mouse**/ 
      int ax=0;
   	/**The absolute y posision on the component of the mouse**/
      int ay=0;
   	/**Counts the number of updates of the Timer object t**/
      int timer=0;
		/**The current wave of enemies**/
		int wave=0;
   	/**The name of the current map**/
      String mapName;
   	/**The StatusBar object placed at the bottom of the NewScreen**/
      StatusBar status=new StatusBar(0,500,600,150);
   	/**Contains all static GameObjects on the board**/
      GameObject[][] grid= new GameObject[12][12];
      /**The y position on the component of the StatusBar status**/
      public static final int TOPMENU=500;
   	/**Determines which GameObject is being placed**/
      int selected=0;
   	/**Determines if an object is being placed**/
      public boolean placing=false;
   	/**The node that all new enemies added to this map will start at**/
      Node first =null;//new Node(50, 50, new Node(50, 450, new Node(200,450,new Node(200,50,new Node(400,50,new Node(400, 450,new Node(550, 450,null))))))); 
   	/**Contains all of the Tower objects that have been placed**/    
      ArrayList<Tower> towers=new ArrayList<Tower>();
   	/**Contains all enemies currently on the map**/
      ArrayList<Enemy> enemies=new ArrayList<Enemy>();
   	/**Contains all bullets currently on the map**/
      ArrayList<Bullet> bullets=new ArrayList<Bullet>();
   /**Instatiates a NewScreen**/    
       public NewScreen()
      {
         myImage =  new BufferedImage(N, N, BufferedImage.TYPE_INT_RGB);
         myBuffer = myImage.getGraphics();
         myBuffer.setColor(BACKGROUND);
         myBuffer.fillRect(0, 0, N, N); 
         Mouse m=new Mouse();
         addMouseListener(m);
         addMouseMotionListener(m);
      
         readInNodes();
         buildPath();
         enemies.add(new Enemy(2,100, first, "Images/Enemies/broccoli.png"));
         t = new Timer(20, new Updater());
      
      
         t.start();
      	
         setFocusable(true);
        
      }
   	/**Updates and pauses/unpauses the game**/
       class Updater implements ActionListener
      {
      /**Updates the NewScreen
      @param e The ActionEvent that triggers this method
      **/
          public void actionPerformed(ActionEvent e)
         {
            run();
         }
      }
   /**If the Timer t is currently running, then this method will stop it, otherwise it will start t again**/
       public static void pauseGame()
      {
         if(t.isRunning())
         {
            t.stop();
         }
         else
         {
            t.start();
         }
      }
   
   
         
         //for(int x=0;x<10; x++)
   		/**Makes the path that the enemies follow.  The player will not be able to place Tower objects on the path or the Nodes**/
       public void buildPath()
      {
         double x=first.getX()+first.getWidth()/2;
         double y=first.getY()+first.getHeight()/2;
         Node next=first;
         double dx=0;
         double dy=0;
         while(next!=null)
         {
            if(next.isClickedOn(x,y))
            {      
               next=next.next();
               if(next==null)
                  return;
               double xLength=(next.getX()+next.getWidth()/2-x);
               double yLength=(next.getY()+next.getHeight()/2-y);
               double angle=Math.atan(yLength/xLength);
               dx=Math.cos(angle)*10;//*speed;
               dy=Math.sin(angle)*10;//*speed;
            }
            x+=dx;
            y+=dy;
            if(access(x,y)==null)
               addToGrid(new Path(convert(x)*50, convert(y)*50, N/grid.length, N/ grid.length));
         }
      	
      }
      /**Converts a double coordinate to a value that is the correct value for the square grid
   	@param num the number being converted
   	
   	@return the coordinate value of the correct value
   	**/
       public int convert(double num)
      {
         return (int)num/(N/grid.length);
      }
      /**Reads in data from the selected map file to create the movement nodes**/
       public void readInNodes()
      {
         Scanner infile=null;      
       
         try{
            infile=new Scanner(new File("Maps/FirstMap"));
         }
             catch(Exception e){}
         mapName=infile.next();
         int numNodes=infile.nextInt();
         Node temp=null;
         for(int x=0;x<numNodes;x++)
         {
            if(x==0)
               first=new Node(infile.nextInt(), infile.nextInt(), null);
            else
            {
               if(x==1)
                  temp=first;
               temp.setNext(new Node(infile.nextInt(), infile.nextInt(), null));
               temp=temp.next();
            }
         }
         temp=first;
         while(temp.next()!=null)
         {
            addToGrid(temp);
            temp=temp.next();
         }
      }
       public void addToGrid(GameObject g)
      {
         int corX=convert(g.getX());
         int corY=convert(g.getY());
         grid[corX][corY]=g;
      }
   	/**Adds a Bullet object to the ArrayList bullets
   	@param b The bullet being added
   	**/
       public void addBullet(Bullet b)
      {
         bullets.add(b);
      }
   	/**Draws the image of the NewScreen
   	@param g The graphics object that draws the image
   	**/
       public void paintComponent(Graphics g)
      {
         
         g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
         
      }
   	/**
   	Returns an ArrayList of enemies
   	@return enemies The ArrayList of enemies in NewScreen
   	**/
       public ArrayList<Enemy> getEnemies()
      {
         return enemies;
      }
   	/**
   	Determines if a coordinate pair on the componentis valid for placing some object
   	
   	@param x the x coordinate to be checked
   	@param y the y coordinate to be checked
   	
   	@return true if the coordinate pair is not in the menu and is not occupied
   	**/
       public boolean valid(double x, double y)
      {
         if(y>TOPMENU)
            return false;
         //int corX=((int)x/(N/grid.length));
         //int corY=((int)y/(N/grid[0].length));
         return access(x,y)==null;
      }
   	/**Updates all of the objects that need to be updated and drawn in the NewScreen**/
       public void run()
      {
         myBuffer.setColor(BACKGROUND);
         myBuffer.fillRect(0, 0, N, N); 
         myBuffer.setColor(Color.RED);
         myBuffer.drawString("("+xPos+", "+yPos+")", 0,50);
         myBuffer.drawString("abs("+ax+", "+ay+")", 0,150);
         updateGrid();
         //updateNodes();    
         //updateTowers();
         updateEnemies();
         updateBullets();
         status.draw(myBuffer);
         repaint();
      }
   	/**Places a tower of the type determined by the int selected at the coordinate given
   		
   	@param x the x coordinate to be checked
   	@param y the y coordinate to be checked
   	
   	@return the new Tower object that was placed
   	**/
       public Tower place(double x, double y)
      {
         int corX=((int)x/50);
         int corY=((int)y/50);
         switch(selected)
         {
         
            default:
               addToGrid(new Tower(corX*50,corY*50, this));
               break;
         }
         status.unselect();
         return  (Tower)grid[corX][corY];
      } 
      /**Returns the GameObject in the grid at the givin coordinates
   	@param x the x position
   	@param y the y position	
   	**/
       public GameObject access(double x, double y)
      {
         int corX=((int)x/50);
         int corY=((int)y/50);
         return  grid[corX][corY];
      }
       public void updateGrid()
      {
         for(int x=0;x<grid.length;x++)
         {
            for(int y=0;y<grid.length;y++)
            {
               GameObject temp=grid[x][y];
               if(temp!=null)
               {
                  temp.draw(myBuffer);
                  temp.update();
                  if(temp.isRemovable())
                     grid[x][y]=null;
               }
            }
         }
      }
   /*
   old
   	//Updates all of the Tower objects in towers      
       public void updateTowers()
      {
         Iterator<Tower> t=towers.iterator();
         while(t.hasNext())
         {
            Tower temp=t.next();
            temp.draw(myBuffer);
            temp.update();
            if(temp.isRemovable())
               t.remove();
         }
      }*/
   	/**Updates all the Enemy objects in enemies**/
       public void updateEnemies()
      {
         timer++;
         if(timer==100)
         {
            enemies.add(new Enemy(3,100, first,"Images/Enemies/apple.png"));
            timer=0;
         }
         Iterator<Enemy> t=enemies.iterator();
         while(t.hasNext())
         {
            Enemy temp=t.next();
            temp.draw(myBuffer);
            temp.update();
            if(temp.isRemovable())
            {
               if(temp.killed)
                  status.cash+= temp.award;
               t.remove();
            }
         }
      }
   	/**Updates and draws all the Bullet objects in bullets**/
       public void updateBullets()
      {
         Iterator<Bullet> t=bullets.iterator();
         while(t.hasNext())
         {
            Bullet temp=t.next();
            temp.draw(myBuffer);
            temp.update(enemies);
            if(temp.isRemovable())
               t.remove();
         }
      }
    /*  
    old
   	//Updates and draws all of the nodes linked to first
       public void updateNodes()
      {
         Node temp=first;
         while(temp.next()!=null)
         {
            temp.draw(myBuffer);
            temp=temp.next();
         }
      }*/
   	/**Updates and draws the StatusBar object status**/
       public void updateStatusPanel()
      {
         status.draw(myBuffer);
         status.update();
      }
   	/**Used to get various mouse input**/
       private class Mouse extends MouseAdapter
      {
      /**Determines if a mouse click is selecting a tower, an enemy, a button somewhere on the component, or nothing
      @param e The MouseEvent that tells where the clicking happens
      **/
          public void mousePressed(MouseEvent e)
         {
            double x=e.getX();
            double y=e.getY();
            
            if(y>TOPMENU)
               if(status.isClickedOn(x,y))
               {  
                  status.click();
                  selected=status.selectedIndex;       
                  placing=true;
               }
         	
            if(placing&&valid(x,y))
            {
               Tower temp=place(x,y);
               if(status.cash-temp.cost<0)
               {
               //GameObject remove=access(x,y);
                  grid[(int)x/50][(int)y/50] =null;
               }
               else
               {
                  status.cash-=temp.cost;            
                  towers.add(temp);
               }
               placing=false;
            }
            //System.out.println("("+x+", "+y+")");
            System.out.println(towers);
         }
      	/**
      	Finds where the mouse currently is on the component
      	@param e The MouseEvent that tells where the mouse is on the component
      	**/
          public void mouseMoved(MouseEvent e)
         {
            xPos=e.getX();
            yPos=e.getY();
            ax=e.getXOnScreen();
            ay=e.getYOnScreen();
            //System.out.println("("+x+", "+y+")");
         }
      }
   
   }