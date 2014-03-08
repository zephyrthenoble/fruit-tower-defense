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
      /**The the game over image**/
      public ImageIcon gameOver=new ImageIcon(getClass().getResource("Images/GameOver.png"));
    /**The the paused game image**/  
      public ImageIcon pause=new ImageIcon(getClass().getResource("Images/Paused.png"));
    /**The the start game image**/  
      public ImageIcon start=new ImageIcon(getClass().getResource("Images/startscreen.png"));
   /**Says if the game has started**/  
      public boolean started=false;
      /**If space is true, then you can see all enemy health bars**/
      public boolean space=false;
   	/**The size of the NewScreen**/
      public static final int N=600;
   	/**The default buffer color of the background**/
      public static final Color BACKGROUND = new Color(204, 204, 204);
   	/**The Graphics2D object used to draw the graphics**/    
      public Graphics2D myBuffer;
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
      /**The amount of lives left**/
      int life=50;
      /**The time inbetween new Enemy objects**/  
      int countdown;//=new int[10];
      /**The number of the Enemy objects being created**/  
      int numEnemies=0;
    /**The speed of the Enemy objects being created**/    
      int enSpeed;//=new int[10];
     /**The health of the Enemy objects being created**/   
      int enHealth;
      /**Counts the amount of enemy checks that have been done**/
      int waveTimer=500;
   	/**The max size of the waveTimer**/
      int waveMax=1000;
       /**The file of the Enemy objects being created**/  	
      String enFile="Images/Enemies/apple.png";
   	/**The name of the current map**/
      String mapName;
   	/*The Scanner used to load data from the map files*/
      Scanner infile; 
   	/**The StatusBar object placed at the bottom of the NewScreen**/
      StatusBar status=new StatusBar(0,500,600,150, this);
   	/**Contains all static GameObjects on the board**/
      GameObject[][] grid= new GameObject[12][12];
      /**The y position on the component of the StatusBar status**/
      public static final int TOPMENU=500;
      /**Holds the image of the cursor**/
      ImageIcon image=new ImageIcon(getClass().getResource("Images/cursor.png"));
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
         myBuffer = myImage.createGraphics();
         myBuffer.setColor(BACKGROUND);
         myBuffer.fillRect(0, 0, N, N); 
         Mouse m=new Mouse();
         addMouseListener(m);
         addMouseMotionListener(m);
         Toolkit test=Toolkit.getDefaultToolkit();
         this.setCursor(test.createCustomCursor(image.getImage(), new Point(15,15), "black"));
         //Cursor.systemCustomCursorPropertiesFile();       
         readInNodes();
         buildPath();
         enemies.add(new Enemy(2,100, first, "Images/Enemies/broccoli.png"));
         addKeyListener(new Key());
         myBuffer.drawImage(start.getImage(),0,0,null);
         repaint();
      
      	
         t = new Timer(20, new Updater());
      
      
         //t.start();
      	
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
           
       
         try{
            infile=new Scanner(new File/*getClass().getResource*/("Maps/FirstMap"));
         }
             catch(Exception e){System.out.println("Error reading map file.");System.exit(0);}
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
   /**Reads in data for the enemies**/
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
         myBuffer.setFont(Font.decode(Font.SERIF));
         myBuffer.setFont(myBuffer.getFont().deriveFont(Font.PLAIN));
         myBuffer.setColor(BACKGROUND);
         myBuffer.fillRect(0, 0, N, N); 
         myBuffer.setColor(Color.RED);
         //myBuffer.drawString("("+xPos+", "+yPos+")", 0,10);
         //myBuffer.drawString("abs("+ax+", "+ay+")", 0,150);
         updateGrid();
         //updateNodes();    
         
         updateEnemies();
         updateTowers();
         updateBullets();
         updateStatusPanel();//status.draw(myBuffer);
         myBuffer.setColor(Color.BLACK);
         myBuffer.fillRect(475,0, 100,25);
         myBuffer.setColor(Color.BLUE);
         if(life<=25)
            myBuffer.setColor(Color.GREEN);
         if(life<=10)
            myBuffer.setColor(Color.RED);     
         myBuffer.setFont(Font.decode(Font.SANS_SERIF));
         myBuffer.setFont(myBuffer.getFont().deriveFont(Font.BOLD));
         myBuffer.drawString("Lives: "+life, 500,15);
         if(life<=0)
         {
            pauseGame();
            myBuffer.drawImage(gameOver.getImage(),0,0,null);
         
         }  
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
            case 0:
               addToGrid(new Tower(corX*50,corY*50, this));
               break;
         
            case 1:
               addToGrid(new SlowTower(corX*50,corY*50, this));
               break;
            case 2:  addToGrid(new PoisonTower(corX*50,corY*50, this));
               break;
            default:
               return null;
         
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
   
   
   	/**Updates all of the Tower objects in towers**/      
       public void updateTowers()
      {
         Iterator<Tower> t=towers.iterator();
         while(t.hasNext())
         {
            Tower temp=t.next();
            if(temp==null)
               t.remove();
            else if(temp.drawRadius)
               temp.drawRadius(myBuffer);
            if(temp.isRemovable())
               t.remove();
         }
      }
   	/**Updates all the Enemy objects in enemies**/
       public void updateEnemies()
      {
      
      
         waveTimer--;
         if(waveTimer<=0)
         {
            newWave();
         
         }
        
         timer++;
         if(timer>=countdown&&countdown>0&&numEnemies>0)
         {
            enemies.add(new Enemy(enSpeed,enHealth, first,enFile));
            timer=0;
            numEnemies--;
         }
         Iterator<Enemy> t=enemies.iterator();
         while(t.hasNext())
         {
            Enemy temp=t.next();
            temp.draw(myBuffer);
            temp.update();
            if(!space)
            {
               if(temp.isClickedOn(xPos,yPos))
               
                  temp.drawHealth(myBuffer);
            }
            else
               temp.drawHealth(myBuffer);
         
            if(temp.isRemovable())
            {
               if(temp.killed)
                  status.cash+= temp.award;
               else
                  life--;
               t.remove();
            }
         }
      }
      /**Creates a new wave of Enemy objects**/
       public void newWave()
      {
         wave++;
            
         int mod=wave%10;
         String f="Images/Enemies/";
         switch(mod)
         {
            case 1:
               enFile=f+"apple.png";
               countdown=50/(1+(wave/25));
               numEnemies=10*(1+(wave/25));
               enSpeed=2;
               enHealth=200*(1+(wave/10));
               
               break;
            case 2: enFile=f+"pineapple.png";
               countdown=110/(1+(wave/25));
               numEnemies=5*(1+(wave/25));
               enSpeed=2;
               enHealth=300*(1+(wave/10));
               
               break;
            case 3: enFile=f+"pepper.png";
               countdown=50/(1+(wave/25));
               numEnemies=10*(1+(wave/25));
               enSpeed=4;
               enHealth=50*(1+(wave/10));
               
               break;
            case 4: enFile=f+"lemon.png";
               countdown=200/(1+(wave/25));
               numEnemies=4*(1+(wave/25));
               enSpeed=1;
               enHealth=400*(1+(wave/10));
               
               break;
            case 5: enFile=f+"broccoli.png";
               countdown=20/(1+(wave/25));
               numEnemies=25*(1+(wave/25));
               enSpeed=2;
               enHealth=30*(1+(wave/10));
               
               break;
            case 6: enFile=f+"watermelon.png";
               countdown=50/(1+(wave/25));
               numEnemies=10*(1+(wave/25));
               enSpeed=2;
               enHealth=200*(1+(wave/10));
               
               break;
            case 7:enFile=f+"orange.png";
               countdown=50/(1+(wave/25));
               numEnemies=10*(1+(wave/25));
               enSpeed=3;
               enHealth=300*(1+(wave/10));
               
               break;
            
            case 8:enFile=f+"wrap.png";
               countdown=40/(1+(wave/25));
               numEnemies=15*(1+(wave/25));
               enSpeed=2;
               enHealth=400*(1+(wave/10));
               
               break;
            
            case 9:enFile=f+"strawberry.png";
               countdown=100/(1+(wave/25));
               numEnemies=6*(1+(wave/25));
               enSpeed=3;
               enHealth=300*(1+(wave/10));
               
               break;
            
            case 0:enFile=f+"pumpkin.png";
               countdown=300/(1+(wave/25));
               numEnemies=1*(1+(wave/25));
               enSpeed=1;
               enHealth=1000*(1+(wave/10));
               
               break;
            
            
            default: enFile= f+"apple.png";
               countdown=50/(1+(wave/25));
               numEnemies=10*(1+(wave/25));
               enSpeed=2;
               enHealth=200*(1+(wave/10));
            
         }
         waveTimer=waveMax;
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
    
       	/**Updates and draws the StatusBar object status**/
       public void updateStatusPanel()
      {
         status.time=waveTimer;
         status.wave=wave;
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
            if(t.isRunning())
            {
              
               for(Tower t: towers)
               {
                  if(t.isClickedOn(x,y))
                  {
                     t.click();
                     break;
                  }
               }
               if(y>TOPMENU)
                  if(status.isClickedOn(x,y))
                  {  
                  //status.click();
                     if(status.placing)
                     {
                        selected=status.selectedIndex;       
                        placing=true;
                        status.unclick();
                     }    
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
               //System.out.println(towers);
            }
            else
            {
               GameObject restart= new GameObject(80,352,180,46);
               GameObject quit= new GameObject(353,352,174,46);
               if(restart.isClickedOn(x,y))
                  restartGame();
               else if(quit.isClickedOn(x,y))
               {
                  System.exit(0);
               }
            }
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
      /**Resets the game for a new game**/
       public void restartGame()
      {
         status=new StatusBar(0,500,600,150, this);
         timer=0;
         wave=0;
         life=50;
         numEnemies=0;
         waveTimer=500;
         waveMax=1000;
         enFile="Images/Enemies/apple.png";
         grid= new GameObject[12][12];
         selected=0;
         towers=new ArrayList<Tower>();
         enemies=new ArrayList<Enemy>();
         bullets=new ArrayList<Bullet>();
         readInNodes(); 
         buildPath(); 
         pauseGame();
      }
   	/**Used to deal with KeyEvents**/
       private class Key extends KeyAdapter
      {
      /**Does things based on user input
      @param e the KeyEvent that specifies what should be done
      **/
          public void keyPressed(KeyEvent e)
         {
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
            {
               status.unselect();
               placing=false;
            }
            else if(e.getKeyCode()==KeyEvent.VK_P)
            {
               pauseGame();
               myBuffer.drawImage(pause.getImage(),0,0,null);
               repaint();
            }
            else if(e.getKeyCode()==KeyEvent.VK_SPACE)
            {
               if(space)
                  space=false;
               else
                  space=true;
            }
            else if(e.getKeyCode()==KeyEvent.VK_N)
            {
               if(numEnemies<=0)
                  newWave();
            }
         }
      }
   }