   import javax.swing.ImageIcon;  
    public class SlowTower extends Tower
   {
   /**
   Creates a new SlowTower object at the given x and y positions on the given NewScreen
   @param x the x position of the Tower
   @param y the y position of the Tower
   @param s the NewScreen that this Tower is in
   
   	**/
       public SlowTower(double x, double y, NewScreen s)
      {
         super(x,y,s);
         buff=1;
         cooldown=200;
         damage=5;
      	cost=150;
      	upgradeCost=150;
         image=new ImageIcon(getClass().getResource("Images/Towers/slow.png"));
      }
   		/**Upgrades the tower, increasing its attack radius, damage, or attack speed**/
       public void upgrade()
      {
         if(upgradeNum<5);
         {
            upgradeNum++;
            radius+=20;
            damage*=2;
            cooldown-=5;
         }
      }
   
   
   }