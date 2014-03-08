
  
   //Torbert, e-mail: mr@torbert.com, website: www.mr.torbert.com
	//version 7.14.2003

   import javax.swing.*;
   import java.awt.*;
   import java.awt.event.*;
   import java.awt.image.*;
   import java.io.*;
   import java.util.Scanner;
   import java.util.NoSuchElementException;
   import java.awt.event.MouseMotionAdapter;
   
    public class Panel extends JPanel
   {
       private class Key extends KeyAdapter
      {
      
          public void keyReleased(KeyEvent e)
         {
            if(e.getKeyCode() == KeyEvent.VK_LEFT)
            {;}
            
         }
          public void keyPressed(KeyEvent e)
         {
            if(e.getKeyCode() == KeyEvent.VK_LEFT)
            {;}
            
                    
         }
      }
         
        
      static Timer t; 
      static NewScreen screen;
      Menu menu;
      Display display;
          
       public Panel()
      {
               
         addKeyListener(new Key());
         setLayout(new BorderLayout());
      	
      	//status panel on right side of screen
      
         //this.setSize(600,600);
      	
        // display=new Display();
         //add(display,BorderLayout.EAST);
        // display.setSize(100,600);
           /*
         menu=new Menu();
         add(menu, BorderLayout.NORTH);  
         menu.setSize(50,600);
        */
         screen=new NewScreen();
			
         add(screen,BorderLayout.CENTER);
         
      	
         t = new Timer(0, new Updater());
      
      
         t.start();
      	
         setFocusable(true);
        
      }
       class Updater implements ActionListener
      {
          public void actionPerformed(ActionEvent e)
         {
            screen.run();
         }
      }
   
       public static void pauseGame()
      {
         if(t.isRunning())
         {
            t.stop();
         }
         else
         {
            t.start();
         }
      }
   }