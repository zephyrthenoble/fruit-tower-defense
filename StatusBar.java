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
   import javax.swing.ImageIcon;
    public class StatusBar extends GameObject implements Clickable
   {
      final int STARTING_CASH=500;
      int cash=STARTING_CASH;
   //ImageIcon image = new ImageIcon(getClass().getResource("Images/Enemies/apple.png"));
   /**A list of TowerButtons**/
      ArrayList<TowerButton> buttons=new ArrayList<TowerButton>();
      /**The index of the selected TowerButton**/
      int selectedIndex=0;
      /**Creates a new StatusBar
   @param x the x position
   @param y the y position
   @param width the width of the StatusBar
   @param height the height of the StatusBar
   **/
       public StatusBar(int x, int y, int width, int height)
      {
         super(x,y,width,height);
         buttons.add(new TowerButton(100,525,50,50,0));
      }
   	/**Unselects the current button**/
       public void unselect()
      {
         buttons.get(selectedIndex).selected=false;
      
      }
   	/**This creates a TowerButton that knows what type of Tower it can make when clicked**/
       private class TowerButton/*<? extends Tower>*/ extends GameObject
      {
      /****/
         int type;
         boolean selected=false;
         Color color;
         /**Creates a new TowerButton
      @param x the x position
      @param y the y position
      @param width the width of the TowerButton
      @param height the height of the TowerButton
      @param type the int value for a type of Tower
      **/
          public TowerButton(int x, int y, int width, int height, int type)
         {
            super(x, y, width, height);
            this.type=type;
            switch(type)
            {
               default:color=Color.BLUE;
            }
         }
      
         ///          public E getType()
         //{
            //return new ();
         //}
          public void draw(Graphics g)
         {
            if(selected)
            {
               g.setColor(Color.green);
               g.fillRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
            
            }
            g.setColor(color);
            g.fillRect((int)getX()+1, (int)getY()+1, (int)getWidth()-2, (int)getHeight()-2);
         
         }
      }
     // public void up
       public void draw(Graphics g)
      {
         g.setColor(Color.WHITE);
         g.fillRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
         for(TowerButton t: buttons)
         {
            t.draw(g);
         }
			g.setColor(new Color(173, 252, 255));
			g.fillRect(0,500, 100, 100);
         g.setColor(Color.black);
         g.drawString("Cash: "+cash, 0,510);
      }
       public boolean isClickedOn(double x, double y)
      {
         for(TowerButton t:buttons)
            if(t.isClickedOn(x,y))
            {
               selectedIndex=buttons.indexOf(t);
               return true;  
            }    
         return false;
      }
       public void click()
      {
         buttons.get(selectedIndex).selected=true;
      }
   }