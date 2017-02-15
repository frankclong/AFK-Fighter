
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Menu class for AFK Fighter
 * 
 * @author Austin Tsang, Frank Long, Karim Eltanahy
 * @version June 16, 2016
 */
public class Menu {
 private JFrame menu;
 private DrawPanel menuPanel;
 private JButton start;
 private JButton exit;
 private JLabel buttonsDown;
 private ImageIcon startImg;
 private ImageIcon exitImg;

 /**
  * Create a menu
  */
 Menu() {
  // Create frame
  menu = new JFrame("AFK FIGHTER");
  menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  menu.setSize(1350, 500);
  menu.setIconImage(Toolkit.getDefaultToolkit().getImage("hakyeon_heavyKickOne.png"));
  menuPanel = new DrawPanel();

  // Create label for button
  buttonsDown = new JLabel();
  buttonsDown.setPreferredSize(new Dimension(220, 220));
  buttonsDown.setMinimumSize(new Dimension(220, 220));
  buttonsDown.setMaximumSize(new Dimension(220, 220));
  buttonsDown.setAlignmentX(Component.CENTER_ALIGNMENT);

  // Create buttons with image
  startImg = new ImageIcon("start.png");
  exitImg = new ImageIcon("exit.png");
  start = new JButton(startImg);
  exit = new JButton(exitImg);

  // Start button
  start.setOpaque(false);
  start.setContentAreaFilled(false);
  start.setBorderPainted(false);
  start.setAlignmentX(Component.CENTER_ALIGNMENT);
  start.addActionListener(new StartListener());

  // Exit Button
  exit.setOpaque(false);
  exit.setContentAreaFilled(false);
  exit.setBorderPainted(false);
  exit.setAlignmentX(Component.CENTER_ALIGNMENT);
  exit.addActionListener(new ExitListener());

  // Add buttons to panel
  menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
  menuPanel.add(buttonsDown);
  menuPanel.add(start);
  menuPanel.add(exit);

  // Add panel to frame
  menu.add(menuPanel);
  menu.setVisible(true);
 }

 /**
  * Repaint the drawing panel
  * 
  * @param g
  *            The Graphics context
  */
 public class DrawPanel extends JPanel {
  private Image paintImage = new ImageIcon("afkfighter.png").getImage();

  /**
   * Draw the background
   */
  @Override
  protected void paintComponent(Graphics g) {
   super.paintComponent(g);
   g.drawImage(paintImage, 0, 0, null);
  }

 }

 /**
  * Button listener for the Start button
  *
  */
 class StartListener implements ActionListener {
  public void actionPerformed(ActionEvent event) {
   menu.dispose();
   new Fight().start();
  }
 }

 /**
  * Button listener for the exit button
  *
  */
 class ExitListener implements ActionListener {
  public void actionPerformed(ActionEvent event) {
   menu.dispose(); // close frame & quit
   System.exit(1);
  }
 }
}
