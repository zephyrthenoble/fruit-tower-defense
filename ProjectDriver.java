   import javax.swing.JFrame;
	import java.awt.*;
 /**The ProjectDriver starts the whole game.  It instantiates the GamePanel, which
 actually runs the game.
 @author Mitchell Smith**/
    public class ProjectDriver
   {
   /**Sets up the game.**/
       public static void main(String[] args)
      {
         JFrame frame = new JFrame("Tower Defense");
         frame.setSize(600+16, 600+36);
         frame.setLocation(150, 100);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
		   Panel p=new Panel();
				p.setPreferredSize(new Dimension(600, 600));
         frame.setContentPane(p);
 frame.pack();  
	      frame.setVisible(true);
         p.requestFocus();
      }
   }