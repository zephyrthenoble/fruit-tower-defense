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
    public class Screen extends JPanel
   {
      int xPos;
      int yPos;
      public BufferedImage myImage;
      public static ImageIcon background;
      public static final int N=600;
      public static final Color BACKGROUND = new Color(204, 204, 204);
      public Graphics myBuffer;
      public boolean placing=true;
      ArrayList<Tower> towers=new ArrayList<Tower>();
      ArrayList<Enemy> enemies=new ArrayList<Enemy>();
      public Node head=new Node(0,300,new Node(500, 300,new Node(500,0,new Node(0,500,null))));
      public Node last=head.next(); 
      public int count=0;  
       public Screen()
      {
         myImage =  new BufferedImage(N, N, BufferedImage.TYPE_INT_RGB);
         myBuffer = myImage.getGraphics();
         myBuffer.setColor(BACKGROUND);
         myBuffer.fillRect(0, 0, N, N); 
         
         setup();
         setFocusable(true);
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
            //System.out.println("("+x+", "+y+")");
         }
      }
       public Tower place(double x, double y)
      {
         return new Tower(x-25,y-25);
      }
       public void setup()
      {
         Mouse m=new Mouse();
         addMouseListener(m);
         addMouseMotionListener(m);
         enemies.add(new Enemy(500,head));
      	
      }
       public void run()
      {
         myBuffer.setColor(BACKGROUND);
         myBuffer.fillRect(0, 0, N, N); 
         myBuffer.setColor(Color.RED);
         myBuffer.drawString("("+xPos+", "+yPos+")", 0,50);
      
         updateNodes();
         updateTowers();
         updateEnemies();
         count++;
         if(count==100)
         {
            enemies.add(new Enemy(500,head));
            count=0;   
         }
         repaint();
      }
       public void updateNodes()
      {
         Node temp=head;
         while(temp.next()!=null)
         {
            temp.draw(myBuffer);
         }
      
      }
       public void updateTowers()
      {
         Iterator<Tower> t=towers.iterator();
         while(t.hasNext())
         {
            Tower temp=t.next();
            temp.draw(myBuffer);
            if(temp.isRemovable())
               t.remove();
         }
      }
       public void updateEnemies()
      {
         Iterator<Enemy> e=enemies.iterator();
         while(e.hasNext())
         {
            Enemy temp=e.next();
            temp.update();
            temp.draw(myBuffer);
            if(temp.isRemovable())
               e.remove();
         }
      }
   
       public void paintComponent(Graphics g)
      {
         g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);  
      }
   }