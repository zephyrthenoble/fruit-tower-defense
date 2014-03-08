   import javax.swing.*;
   import java.awt.*;
   import javax.swing.JMenu;
   import java.awt.event.*;
   import java.awt.event.KeyEvent;
    public class Display extends JPanel
   {
      JButton[] buttons=new JButton[10];
       public Display()
      {
         setLayout(new GridLayout(2,1));
         JPanel buttonPanel=new JPanel();
         buttonPanel.setLayout(new GridLayout(2,5));
         for(int x=0;x<buttons.length;x++)
         {
            buttons[x]=new JButton(x+".jpg");
            buttonPanel.add(buttons[x]);
         }
         add(buttonPanel);
         JPanel statusPanel=new JPanel();
         statusPanel.setLayout(new GridLayout(2,1));
         statusPanel.add(new Label("Test"));
         add(statusPanel);
      }
   
   }