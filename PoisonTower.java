   import javax.swing.ImageIcon;  
    public class PoisonTower extends Tower
   {
   /**
   Creates a new Tower object at the given x and y positions on the given NewScreen
   @param x the x position of the Tower
   @param y the y position of the Tower
   @param s the NewScreen that this Tower is in
   
   	**/
       public PoisonTower(double x, double y, NewScreen s)
      {
         super(x,y,s);
         buff=2;
         cooldown=300;
         damage=20;
         cost=200;
         upgradeCost=200;
         image=new ImageIcon(getClass().getResource("Images/Towers/poison.png"));
      }
       public void upgrade()
      {
         if(upgradeNum<5)
         {
            upgradeNum++;
            radius*=1.5;
            damage*=1.5;
            cooldown=(cooldown/4)*3;
            upgradeCost+=50;
         }
      }
   
   }