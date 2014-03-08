   import javax.swing.ImageIcon;  
    public class PoisonTower extends Tower
   {
   /**
   Creates a new PoisonTower object at the given x and y positions on the given NewScreen
   @param x the x position of the PoisonTower
   @param y the y position of the PoisonTower
   @param s the NewScreen that this PoisonTower is in
   
   	**/
       public PoisonTower(double x, double y, NewScreen s)
      {
         super(x,y,s);
         buff=2;
         cooldown=100;
         damage=20;
         cost=200;
			moneySpent=200;
         upgradeCost=200;
			radius=75;
         image=new ImageIcon(getClass().getResource("Images/Towers/poison.png"));
      }
		/**Upgrades the PoisonTower**/
       public void upgrade()
      {
         if(upgradeNum<5)
         {
            upgradeNum++;
            radius*=1.25;
            damage*=1.5;
            cooldown=(cooldown/4)*3;
				moneySpent+=upgradeCost;
            upgradeCost+=50;
         }
      }
   
   }