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
      public BufferedImage myImage;
      public static ImageIcon background;
      public static final int N=600;
      public static final Color BACKGROUND = new Color(204, 204, 204);
      public Graphics myBuffer;
      int xPos=0;
      int yPos=0;
      int ax=0;
      int ay=0;
      int timer=0;
      String mapName;
      StatusBar status=new StatusBar(0,500,600,150);
      GameObject[][] grid= new GameObject[12][12];
      /**Where the menu starts**/
      public static final int TOPMENU=500;
      int selected=0;
      public boolean placing=false;
      Node first =null;//new Node(50, 50, new Node(50, 450, new Node(200,450,new Node(200,50,new Node(400,50,new Node(400, 450,new Node(550, 450,null))))))); 
      ArrayList<Tower> towers=new ArrayList<Tower>();
      ArrayList<Enemy> enemies=new ArrayList<Enemy>();
      ArrayList<Bullet> bullets=new ArrayList<Bullet>();
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
         enemies.add(new Enemy(100, first));
         run();
         setFocusable(true);
         
         //for(int x=0;x<10; x++)
               
      }
       public void readInNodes()
      {
         Scanner infile=null;      
       
         try{
            infile=new Scanner(new File("FirstMap"));
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
      }
       public void addBullet(Bullet b)
      {
         bullets.add(b);
      }
       public void paintComponent(Graphics g)
      {
         
         g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
         
      }
       public ArrayList<Enemy> getEnemies()
      {
         return enemies;
      }
       public boolean valid(double x, double y)
      {
         if(y>TOPMENU)
            return false;
         int corX=((int)x/(N/grid.length));
         int corY=((int)y/(N/grid[0].length));
         return grid[corX][corY]==null;
      }
       public void run()
      {
         myBuffer.setColor(BACKGROUND);
         myBuffer.fillRect(0, 0, N, N); 
         myBuffer.setColor(Color.RED);
         myBuffer.drawString("("+xPos+", "+yPos+")", 0,50);
         myBuffer.drawString("abs("+ax+", "+ay+")", 0,150);
         updateNodes();    
         updateTowers();
         updateEnemies();
         updateBullets();
         status.draw(myBuffer);
         repaint();
      }
       public Tower place(double x, double y)
      {
         int corX=((int)x/50);
         int corY=((int)y/50);
         switch(selected)
         {
         
            default:
               grid[corX][corY]=new Tower(corX*50,corY*50, this);
               break;
         }
         status.unselect();
         return  (Tower)grid[corX][corY];
      }       
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
      }
       public void updateEnemies()
      {
         timer++;
         if(timer==100)
         {
            enemies.add(new Enemy(100, first));
            timer=0;
         }
         Iterator<Enemy> t=enemies.iterator();
         while(t.hasNext())
         {
            Enemy temp=t.next();
            temp.draw(myBuffer);
            temp.update();
            if(temp.isRemovable())
               t.remove();
         }
      }
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
       public void updateNodes()
      {
         Node temp=first;
         while(temp.next()!=null)
         {
            temp.draw(myBuffer);
            temp=temp.next();
         }
      }
       public void updateStatusPanel()
      {
         status.draw(myBuffer);
         status.update();
      }
       private class Mouse extends MouseAdapter
      {
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
               towers.add(place(x,y));
               placing=false;
            }
            //System.out.println("("+x+", "+y+")");
            System.out.println(towers);
         }
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