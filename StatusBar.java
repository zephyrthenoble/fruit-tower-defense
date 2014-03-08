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
   /**The starting amount of cash**/
      final int STARTING_CASH=500;
   	/**The amount of cash that you have left**/
      int cash=STARTING_CASH;
   	/**The amount of time until the next wave**/
      int time=0;
   //ImageIcon image = new ImageIcon(getClass().getResource("Images/Enemies/apple.png"));
   /**The current wave**/
      int wave=0;  
   /**A list of TowerButtons**/
      ArrayList<TowerButton> buttons=new ArrayList<TowerButton>();
      /**The index of the selected TowerButton**/
      int selectedIndex=-1;
    /**The Tower whose data is being displayed**/  
      Tower tower=null;
   	/**Displays Tower info and upgrade info**/
      UpgradeStuff stuff=new UpgradeStuff(this);
      /**Says if a Tower is being placed**/
      boolean placing=false;
      /**The NewScreen that this StatusBar is in**/
      NewScreen screen;
      /**Creates a new StatusBar
   @param x the x position
   @param y the y position
   @param width the width of the StatusBar
   @param height the height of the StatusBar
   **/
       public StatusBar(int x, int y, int width, int height, NewScreen screen)
      {
         super(x,y,width,height);
         buttons.add(new TowerButton(100,510,50,50,0));
         buttons.add(new TowerButton(200, 510, 50, 50, 1));
         buttons.add(new TowerButton(300, 510, 50, 50, 2));
         this.screen=screen;
      }
   	/**Unselects the current button**/
       public void unselect()
      {
         buttons.get(selectedIndex).selected=false;
         selectedIndex=-1;
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
               case 1: color=Color.BLUE;
                  break;
               case 2:color=Color.GREEN;
                  break;
               default:color=Color.YELLOW;
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
               g.setColor(Color.red);
               g.fillRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
            
            }
            g.setColor(color);
            g.fillRect((int)getX()+1, (int)getY()+1, (int)getWidth()-2, (int)getHeight()-2);
         
         }
      }
      
     // public void up
       public void draw(Graphics g)
      {
         stuff.drawRadius(g);
         g.setColor(Color.WHITE);
         g.fillRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
         for(TowerButton t: buttons)
         {
            t.draw(g);
         }
         g.setColor(new Color(173, 252, 255));
         g.fillRect(0,500, 100, 100);
         g.setColor(Color.black);
         g.drawString("Sniper", 100,570);
         g.drawString("Slow", 200,570);
         g.drawString("Poison", 300,570);
         g.drawString("Cost: 100", 100, 590);
         g.drawString("Cost: 150", 200, 590);
         g.drawString("Cost: 200", 300, 590);
         g.drawString("Cash: "+cash, 0,510);
         g.drawString("Time: "+time, 0, 530);
         g.drawString("Wave: " +wave, 0, 550);
         stuff.draw(g);
         
      	
      }
       public boolean isClickedOn(double x, double y)
      {
         for(TowerButton t:buttons)
            if(t.isClickedOn(x,y))
            {
               selectedIndex=buttons.indexOf(t);
               click();          
               placing=true;
               return true;
            }
         if(stuff.button.isClickedOn(x,y))
         {
            stuff.click();
            return true;
         }
         return false;
      }
       public void click()
      {
         buttons.get(selectedIndex).selected=true;
      }
       public void unclick()
      {
         placing=false;
         //selectedIndex=-1;
      }
       public void towerData(Tower tr)
      {
         tower=tr;
         stuff.getData(tr);
         stuff.ok=true;
      }
       private class UpgradeStuff extends GameObject implements Clickable
      {
         ImageIcon valid=new ImageIcon(getClass().getResource("Images/upgradeallowed.png"));
         ImageIcon invalid=new ImageIcon(getClass().getResource("Images/upgradeunavailable.png"));
         UpgradeButton button=new UpgradeButton();
         StatusBar s;
         int damage;
         double radius;
         int cooldown; 
         int upgrade;
         int upgradeCost; 
         Tower selectedTower=tower;
         boolean ok=false;
         
          public UpgradeStuff(StatusBar s)
         {
            super(400,500,200,200);
            this.s=s;
         }
          public void draw(Graphics g)
         {
            if(ok)
            {
               if(upgradeCost>s.cash)
                  g.drawImage(invalid.getImage(), 500,500,null);
               else
                  g.drawImage(valid.getImage(), 500,500,null);
               
               g.drawString("Damage: "+damage,500,540);
               g.drawString("Radius: "+radius,500,550);
               g.drawString("Cooldown: "+cooldown,500, 560);
               g.drawString("Upgrade Cost: "+upgradeCost,500,570);
            }
            else
               g.drawImage(invalid.getImage(), 500,500,null);
         }
          public void drawRadius(Graphics g)
         {
            if(tower!=null)
            {
               getData(tower);
               tower.drawRadius(g);
            }
         }
          public void getData(Tower t)
         {
            this.damage=t.damage;
            this.radius=t.radius;
            this.cooldown=t.cooldown;
            this.upgradeCost=t.upgradeCost;
            this.upgrade=t.upgradeNum;
            tower=t;
         }
          public void click()
         {
            if(upgradeCost<=s.cash && upgrade<5)
            {
               s.tower.upgrade();
               s.cash-=upgradeCost;
            }
         }
         
      }
       private class UpgradeButton extends GameObject
      {
          public UpgradeButton()
         {
            super(500,500,50,30);
         }
      }
   	
   }