   import javax.swing.*;
   import java.awt.*;
   import javax.swing.JMenu;
   import java.awt.event.*;
   import java.awt.event.KeyEvent;

    public class Menu extends JPanel
   {
      private JMenuBar menuBar;
      private JMenu game, help, options;
      private JMenuItem instructions, about, exit, menuItem, pause,next,last,hs;
      private JCheckBoxMenuItem mute;
   
       public Menu()
      {
      
         setLayout(new GridLayout(1, 4));
         
         menuBar = new JMenuBar();
         add(menuBar);
         game = new JMenu("Game");
         game.setMnemonic(KeyEvent.VK_M);
         menuBar.add(game);
         
         menuItem = new JMenuItem("New Game");
         menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
         menuItem.addActionListener(new Resetter());
         game.add(menuItem);
         
         pause = new JMenuItem("Pause");
         pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
         pause.addActionListener(new Listener1());
         game.add(pause);
      
         game.addSeparator();
         help = new JMenu("Help");
      	
      
         instructions = new JMenuItem("Instructions");
         instructions.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,ActionEvent.CTRL_MASK));
         instructions.addActionListener(new Listener2());
         help.add(instructions);
      
         about = new JMenuItem("About");
         about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
         about.addActionListener(new Listener4());
         help.add(about);
            
         menuBar.add(help);
         options=new JMenu("Options");
      	
         game.add(options);
      	
               	
         exit = new JMenuItem("Exit");
         exit.addActionListener(new Listener3());
         exit.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_F4, ActionEvent.ALT_MASK));
      
         game.add(exit);
      }
       private class Resetter implements ActionListener
      {
          public void actionPerformed(ActionEvent e)
         {
            if(Panel.t.isRunning())
               Panel.pauseGame();
            Panel.screen.run();
           //Panel.screen.gameOver=false;
            Panel.t.start();
         }
      }
       private class Listener1 implements ActionListener
      {
          public void actionPerformed(ActionEvent e)
         {
         
            Panel.pauseGame();
         
         }
      }
       private class Listener2 implements ActionListener
      {
          public void actionPerformed(ActionEvent e)
         {
            Panel.pauseGame();
            //JOptionPane.showMessageDialog(null,"            Instructions:\nYour girlfriend has been stolen by aliens\n and now you go to seek revenge on them.\n Of course you will never actually defeat them all,\n but you can at least do some dammage.\n Bosses come every 5 waves.\n Watch out for those red dots too,\n they may have little health but they can pack a punch.\nArrows:   Move\nSpaceBar:   Shoot\nPause:   Control P\nMute/Unmute:  Control M\n            PowerUps:\nRed:   Health/Shield boost\nBlue:   Faster Shot\nGreen:   Damange Upgrade\nMagenta:   Extra Shots");
         	     	
         	
         }
      }
       private class Listener3 implements ActionListener
      {
          public void actionPerformed(ActionEvent e)
         {
         
            System.exit(0);
         
         }
      }
       private class Listener4 implements ActionListener
      {
          public void actionPerformed(ActionEvent e)
         {
            Panel.pauseGame();
            //JOptionPane.showMessageDialog(null,"            Creators:\nJireh Miaw and Mitchell Smith \n Created in Lab 232A at TJHSST \n              emails:\nalohasnack@yahoo.com\nmitchell.smith@yahoo.com");
         	
         }
      }
   }