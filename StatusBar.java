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
      ArrayList<TowerButton> buttons=new ArrayList<TowerButton>();
      int selectedIndex=0;
       public StatusBar(int x, int y, int width, int height)
      {
         super(x,y,width,height);
         buttons.add(new TowerButton(500,500,50,50,0));
      }
      public void unselect()
   	{
   	buttons.get(selectedIndex).selected=false;

   	}
      //LOOK UP GENERICS <3 PATRICK
       private class TowerButton/*<E>*/ extends GameObject
      {
         int type;
         boolean selected=false;
          public TowerButton(int x, int y, int width, int height, int type)
         {
            super(x, y, width, height);
            this.type=type;
         }
      
         ///          public E getType()
         //{
            //return new ();
         //}
          public void draw(Graphics g)
         {
            if(selected)
               g.setColor(Color.MAGENTA);
            else
               g.setColor(Color.BLACK);
            g.fillRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
         
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