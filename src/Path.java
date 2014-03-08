   import java.awt.*;
   import javax.swing.ImageIcon;
   import java.util.Iterator;
/**A Path object is used to take up the spaces inbetween Nodes so that you cannot place a Tower there.**/
    public class Path extends GameObject
   {
   /**Creates a new Path
   @param x the x position
   @param y the y position
   @param width the width of the Path
   @param height the height of the Path
   **/
       public Path(int x, int y, int width, int height)
      {
         super(x,y,width,height);
      }
		/**Draws the Path
		@param g the Graphics object doing the drawing
		**/
       public void draw(Graphics g)
      {
         g.setColor(new Color(226,126,48));
         g.fillRect((int)getX(), (int)getY(), (int)getWidth(),(int)getHeight());
      }
   }