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
      GameObject[][] grid= new GameObject[12][12];
      
      public boolean placing=true;
      Node first =new Node(200, 100, new Node(200, 500, null)); 
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
      
         enemies.add(new Enemy(100, first));
         run();
         setFocusable(true);
         
         //for(int x=0;x<10; x++)
               
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
       public void run()
      {
         myBuffer.setColor(BACKGROUND);
         myBuffer.fillRect(0, 0, N, N); 
         myBuffer.setColor(Color.RED);
         myBuffer.drawString("("+xPos+", "+yPos+")", 0,50);
         myBuffer.drawString("abs("+ax+", "+ay+")", 0,150);
         updateTowers();
         updateEnemies();
         updateBullets();
         repaint();
      }
       public Tower place(double x, double y)
      {
         int corX=((int)x/50);
         int corY=((int)y/50);
         grid[corX][corY]=new Tower(corX*50,corY*50, this);
      
         return  (Tower)grid[corX][corY];
      }       
       private class Mouse extends MouseAdapter
      {
          public void mousePressed(MouseEvent e)
         {
            double x=e.getX();
            double y=e.getY();
            if(placing)
            {
               towers.add(place(x,y));
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
   
   }