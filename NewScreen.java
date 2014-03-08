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
      
      public boolean placing=true;
      ArrayList<Tower> towers=new ArrayList<Tower>();
       public NewScreen()
      {
         myImage =  new BufferedImage(N, N, BufferedImage.TYPE_INT_RGB);
         myBuffer = myImage.getGraphics();
         myBuffer.setColor(BACKGROUND);
         myBuffer.fillRect(0, 0, N, N); 
         Mouse m=new Mouse();
         addMouseListener(m);
         addMouseMotionListener(m);
      
         run();
         setFocusable(true);
         
         //for(int x=0;x<10; x++)
               
      }
       public void paintComponent(Graphics g)
      {
         
         g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
         
      }
       public void run()
      {
         myBuffer.setColor(BACKGROUND);
         myBuffer.fillRect(0, 0, N, N); 
         myBuffer.setColor(Color.RED);
         myBuffer.drawString("("+xPos+", "+yPos+")", 0,50);
       myBuffer.drawString("abs("+ax+", "+ay+")", 0,150);
      updateTowers();
      repaint();
      }
       public Tower place(double x, double y)
      {
         return new Tower(x,y, this);
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
            if(temp.isRemovable())
               t.remove();
         }
      }

   }