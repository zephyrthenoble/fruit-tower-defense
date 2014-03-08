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
         if(selectedIndex>=0)
            buttons.get(selectedIndex).selected=false;
         placing=false;
         selectedIndex=-1;
      	
      }
   	/**Removes info about the current Tower**/
       public void unclick()
      {
         stuff.ok=false;
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
         else if(stuff.lock.isClickedOn(x,y))
            stuff.switchTarget();
         else if(stuff.sellbutton.isClickedOn(x,y))
         {
            tower.sell();
            stuff.ok=false;
         }
         return false;
      }
      /**Selects a button**/
       public void click()
      {
         for(TowerButton x: buttons)
            x.selected=false;
         buttons.get(selectedIndex).selected=true;
      }
      /**Gets data about the selected Tower
   	@param tr the selected Tower
   	**/
       public void towerData(Tower tr)
      {
         tower=tr;
         stuff.getData(tr);
         stuff.ok=true;
      }
      /**Contains and displays info about a selected Tower**/
       private class UpgradeStuff extends GameObject implements Clickable
      {
      /**The image of the valid upgrade button**/
         ImageIcon valid=new ImageIcon(getClass().getResource("Images/upgradeallowed.png"));
         /**The image of the invalid upgrade button**/
         ImageIcon invalid=new ImageIcon(getClass().getResource("Images/upgradeunavailable.png"));
         /**The image of the front button**/
         ImageIcon front=new ImageIcon(getClass().getResource("Images/front.png"));
         /**The image of the back button**/
         ImageIcon back=new ImageIcon(getClass().getResource("Images/back.png"));
         /**The image of the sell button**/
         ImageIcon sell=new ImageIcon(getClass().getResource("Images/sell.png"));
        /**Button used to upgrade Towers**/
         UpgradeButton button=new UpgradeButton();
      	/**Button used to change the way the Tower shoots**/
         LockButton lock=new LockButton();
         /**Button used to sell the Tower**/
         SellButton sellbutton=new SellButton();
         /**The StatusBar this UpgradeStuff is in**/
         StatusBar s;
      	/**The damage the Tower is dealing**/
         int damage;
         /**The radius of the Tower**/
         double radius;
         /**The cooldown of the Tower**/
         int cooldown; 
         /**The amount of upgrades of the Tower**/
         int upgrade;
         /**The cost to upgrade the Tower**/
         int upgradeCost;
         /**The selected Tower**/
         Tower selectedTower=tower;
         /**Says if all of the data should be drawn**/
         boolean ok=false;
      	/**Used to determine if the Tower is locked on to Enemy objects in front of it or behind it**/
         boolean lockedOn=true;
         
      	/**Creates a new UpgradeStuff
      	@param s the StatusBar that this is in
      	**/
          public UpgradeStuff(StatusBar s)
         {
            super(400,500,200,200);
            this.s=s;
         }
         /**Draws the UpgradeStuff and all of the different parts of it
      	@param g the Graphics object doing the drawing
      	**/
          public void draw(Graphics g)
         {
            if(ok)
            {
               if(upgradeCost>s.cash||upgrade>=5)
                  g.drawImage(invalid.getImage(), 450,500,null);
               else
                  g.drawImage(valid.getImage(), 450,500,null);
               //if(selectedTower!=null)
               g.drawString("Upgrades: "+upgrade,500,530);
               g.drawString("Damage: "+damage,500,540);
               g.drawString("Radius: "+radius,500,550);
               g.drawString("Cooldown: "+cooldown,500, 560);
               g.drawString("Upgrade Cost: "+upgradeCost,500,570);
               if(lockedOn)
                  g.drawImage(front.getImage(), 450, 530,null);
               else
                  g.drawImage(back.getImage(), 450, 530, null);
               g.drawImage(sell.getImage(), 450,560,null);
            }
            else
               g.drawImage(invalid.getImage(), 450,500,null);
         }
             /**Draws the radius of the selected Tower
      	@param g the Graphics object doing the drawing
      	**/
          public void drawRadius(Graphics g)
         {
            if(tower!=null&&!tower.isRemovable())
            {
               getData(tower);
               tower.drawRadius(g);
            }
         }
         /**Gets data about the selected Tower
      	@param t the selected Tower
      	**/
          public void getData(Tower t)
         {
            this.damage=t.damage;
            this.radius=t.radius;
            this.cooldown=t.cooldown;
            this.upgradeCost=t.upgradeCost;
            this.upgrade=t.upgradeNum;
            lockedOn=t.lockedOnToFront;
            tower=t;
         }
         /**Upgrades the selected Tower**/
          public void click()
         {
         
            if(/*selectedTower!=null&&*/upgradeCost<=s.cash && upgrade<5)
            {
               s.tower.upgrade();
               s.cash-=upgradeCost;
            }
         }
         /**Changes the Tower's targets**/
          public void switchTarget()
         {
            //if(selectedTower!=null)
            {
               if(lockedOn)
                  tower.lockedOnToFront=false;
               else
                  tower.lockedOnToFront=true;
            }
         }
         
      }
      /**Used to have the dimensions of the upgrade button**/
       private class UpgradeButton extends GameObject
      {
      /**Creates a new UpgradeButton at prespecified points**/
          public UpgradeButton()
         {
            super(450,500,50,30);
         }
      }
       /**Used to have the dimensions of the lock button**/
   
       private class LockButton extends GameObject
      {
            /**Creates a new LockButton at prespecified points**/
      
          public LockButton()
         {
            super(450,530,50,30);
         }
      }
       /**Used to have the dimensions of the sell button**/
   
       private class SellButton extends GameObject
      {
            /**Creates a new SellButton at prespecified points**/
      
          public SellButton()
         {
            super(450,560,50,30);
         }
      }
   }