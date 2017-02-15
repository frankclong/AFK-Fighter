import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Runs AFK Fighter, a 2D platform fighting game
 * 
 * @author Austin Tsang, Frank Long, Karim Eltanahy
 * @version June 16, 2016
 */
public class Fight {
  private boolean controls = false;
  private boolean gameOver = false;
  private int winner;
  private static boolean play = false;
  
  Clip clip;
  JFrame frame;
  MyPanel panel;
  
  // Create fighters and hit boxes
  Fighter fighter1 = new Hakyeon(450);
  Fighter fighter2 = new Kage(750);
  Rectangle myBox;
  Rectangle oppBox;
  
  // Buttons
  final Rectangle YES_BUTTON = new Rectangle(600, 300, 30, 20);
  final Rectangle NO_BUTTON = new Rectangle(678, 300, 22, 20);
  
  // General images
  // Random number generator for random background each time
  private Image background;
  
  
  private Image lifebar = new ImageIcon("lifebar.png").getImage();
  
  // Fonts
  final Font LIFE_BAR_FONT = new Font("Impact", Font.PLAIN, 22);
  
  public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    // Start with menu
    new Menu();
    // Continuously check until game starts
    while (true) {
      System.out.println("");
      if (play) {
        new Fight().music();
        new Fight().go();
      }
    }
  }
  
  /**
   * Start playing
   */
  public void start() {
    play = true;
  }
  
  /**
   * Start playing the game music
   * 
   * @throws UnsupportedAudioFileException
   *             if audio file type is unsupported
   * @throws IOException
   *             if file not found
   * @throws LineUnavailableException
   *             if line is unavailable
   */
  public void music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    // specify the sound to play
    // (assuming the sound can be played by the audio system)
    
    File soundFile = new File("In_Game_Music.wav");
    AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
    
    // load the sound into memory (a Clip)
    DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
    clip = (Clip) AudioSystem.getLine(info);
    clip.open(sound);
    
    // due to bug in Java Sound, explicitly exit the VM when
    // the sound has stopped.
    clip.addLineListener(new LineListener() {
      public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP) {
          event.getLine().close();
          System.exit(0);
        }
      }
    });
    
    // play the sound clip
    clip.loop(Clip.LOOP_CONTINUOUSLY);
    
  }
  
  public void go() {
    frame = new JFrame("AFK FIGHTER");
    panel = new MyPanel();
    
    // Background
    int backgroundNumber = (int)(Math.random()*1 + 1);
    if (backgroundNumber == 1)
      background = new ImageIcon("country-platform.png").getImage();
    else if (backgroundNumber == 2)
      background = new ImageIcon("country-platform.png").getImage();
    
    // Start walk/stand animation timers
    fighter1.startWalkStandTimer();
    fighter2.startWalkStandTimer();
    
    KeyListener listener = new MyKeyListener();
    frame.setIconImage(Toolkit.getDefaultToolkit().getImage("hakyeon_heavyKickOne.png"));
    panel.addKeyListener(listener);
    panel.addMouseListener(panel);
    frame.getContentPane().add(BorderLayout.CENTER, panel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.setResizable(false);
    frame.setSize(1350, 530);
    panel.requestFocusInWindow();
    animate();
  }
  
  /**
   * Reset the fighters (position and health)
   */
  public void reset() {
    fighter1 = new Hakyeon(450);
    fighter2 = new Kage(750);
    fighter1.startWalkStandTimer();
    fighter2.startWalkStandTimer();
    gameOver = false;
  }
  
  /**
   * Custom panel class
   */
  class MyPanel extends JPanel implements MouseListener {
    /**
     * Repaint the drawing panel
     * 
     * @param g
     *            The Graphics context
     */
    public void paintComponent(Graphics g) {
      // background
      g.setColor(Color.BLACK);
      g.drawImage(background, 0, 0, this);
      
      // FIGHTERS
      // Player 1
      if (fighter1.getDirection() == 0) {
        g.drawImage(fighter1.currentSprite(), fighter1.getX(), fighter1.getY() - fighter1.getHeight(), this);
        // Hit box
        // g.drawRect(fighter1.getX(),
        // fighter1.getY() - fighter1.getHeight(),
        // fighter1.getWidth() - 10, fighter1.getHeight());
      }
      // Player 1 flipped
      else {
        g.drawImage(fighter1.currentSprite(), fighter1.getX() + fighter1.getStandingWidth(),
                    fighter1.getY() - fighter1.getHeight(), -fighter1.getWidth(), fighter1.getHeight(), null);
        // g.drawRect(fighter1.getX() + fighter1.getStandingWidth()
        // - fighter1.getWidth() + 10,
        // fighter1.getY() - fighter1.getHeight(),
        // fighter1.getWidth() - 10, fighter1.getHeight());
      }
      
      // Player 2
      g.setColor(Color.RED);
      if (fighter2.getDirection() == 0) {
        g.drawImage(fighter2.currentSprite(), fighter2.getX(), fighter2.getY() - fighter2.getHeight(), this);
        // g.drawRect(fighter2.getX(),
        // fighter2.getY() - fighter2.getHeight(),
        // fighter2.getWidth() - 10, fighter2.getHeight());
      }
      // Player 2 flipped
      else {
        g.drawImage(fighter2.currentSprite(), fighter2.getX() + fighter2.getStandingWidth(),
                    fighter2.getY() - fighter2.getHeight(), -fighter2.getWidth(), fighter2.getHeight(), null);
        
        // g.drawRect(fighter2.getX() + fighter2.getStandingWidth()
        // - fighter2.getWidth() + 10,
        // fighter2.getY() - fighter2.getHeight(),
        // fighter2.getWidth() - 10, fighter2.getHeight());
      }
      
      // LIFE BAR
      g.drawImage(lifebar, 50, 25, this);
      g.drawImage(lifebar, 750, 25, this);
      g.setColor(Color.RED);
      g.fillRect(550 - fighter1.getRedWidth(), 28, fighter1.getRedWidth(), 24);
      g.fillRect(750, 28, fighter2.getRedWidth(), 24);
      g.setColor(Color.BLACK);
      g.setFont(LIFE_BAR_FONT);
      g.drawString(fighter1.getName(), 60, 47);
      g.drawString(fighter2.getName(), 1190, 47);
      // Instructions
      g.drawString("C FOR CONTROLS", 580, 50);
      if(controls && !gameOver){
        g.drawString("CONTROLS", 615, 150);
        g.drawString("HAKYEON", 515, 175);
        g.drawString("KAGE", 730, 175);
        // movement
        g.drawString("MOVEMENT",350, 200);
        g.drawString("WASD", 525, 200);
        g.drawString("ARROW KEYS", 700,200);
        
        // light punch
        g.drawString("LIGHT PUNCH",350, 225);
        g.drawString("T", 550, 225);
        g.drawString("O", 750,225);
        
        // heavy punch
        g.drawString("HEAVY PUNCH",350, 250);
        g.drawString("Y", 550, 250);
        g.drawString("P", 750,250);
        // light kick
        g.drawString("LIGHT KICK",350, 275);
        g.drawString("G", 550, 275);
        g.drawString("L", 750,275);
        // heavy kick
        g.drawString("HEAVY KICK",350, 300);
        g.drawString("H", 550, 300);
        g.drawString(";", 750,300);
      }
      // Game Over menu
      if (gameOver) {
        g.setColor(Color.BLACK);
        g.drawString("GAME OVER", 605, 200);
        if (winner == 0)
          g.drawString("DRAW", 625, 225);
        else if (winner == 1)
          g.drawString("HAKYEON WINS", 590, 225);
        else if (winner == 2)
          g.drawString("KAGE WINS", 606, 225);
        
        g.drawString("PLAY AGAIN?", 600, 275);
        g.drawString("YES            NO", 600, 320);
        // g.drawRect(600, 300, 30, 20);
        // g.drawRect(678, 300, 22, 20);
      }
      
    }
    
    /**
     * Responds to a mousePressed event
     * 
     * @param event
     *            information about the mouse pressed event
     */
    public void mousePressed(MouseEvent event) {
      Point pressed = event.getPoint();
      if (gameOver) {
        if (NO_BUTTON.contains(pressed)) {
          frame.dispose(); // close frame & quit
          System.exit(1);
        }
        if (YES_BUTTON.contains(pressed)) {
          // Reset fighter positions and health
          reset();
        }
      }
      frame.repaint();
    }
    
    // Extra methods needed since this panel is a MouseListener
    public void mouseReleased(MouseEvent event) {
    }
    
    public void mouseClicked(MouseEvent event) {
    }
    
    public void mouseEntered(MouseEvent event) {
    }
    
    public void mouseExited(MouseEvent event) {
    }
  }
  
  /**
   * Custom Key Listener class
   *
   */
  public class MyKeyListener implements KeyListener {
    public void keyTyped(KeyEvent e) {
      
    }
    
    /**
     * runs when key is pressed and determines appropriate action
     * 
     * @param e
     *            the key event
     */
    public void keyPressed(KeyEvent e) {
      // System.out.println("keyPressed=" +
      // KeyEvent.getKeyText(e.getKeyCode()));
      
      if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        if (!fighter2.isAttack())
          fighter2.setLeft(true);
        frame.repaint();
      }
      if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        if (!fighter2.isAttack())
          fighter2.setRight(true);
        frame.repaint();
      }
      if (e.getKeyCode() == KeyEvent.VK_UP) {
        // Jump
        if (!fighter2.isAttack())
          if (fighter2.getY() == 430) {
          fighter2.setJump(true);
          fighter2.setCrouch(false);
          
          if (fighter2.isLeft())
          {
            fighter2.setJumpType(1);
          }
          else if (fighter2.isRight())
          {
            fighter2.setJumpType(2);
          }
          else
            fighter2.setJumpType(3);
        }
        frame.repaint();
      }
      if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        // Crouch
        if (!fighter2.isAttack())
          if (!fighter2.isJump()) {
          fighter2.setCrouch(true);
          fighter2.setLeft(false);
          fighter2.setRight(false);
        }
        frame.repaint();
      }
      if (e.getKeyCode() == KeyEvent.VK_A) {
        if (!fighter1.isAttack())
          fighter1.setLeft(true);
        frame.repaint();
      }
      if (e.getKeyCode() == KeyEvent.VK_D) {
        if (!fighter1.isAttack())
          fighter1.setRight(true);
        frame.repaint();
      }
      if (e.getKeyCode() == KeyEvent.VK_W) {
        // Jump
        if (!fighter1.isAttack())
          if (fighter1.getY() == 430) {
          fighter1.setJump(true);
          fighter1.setCrouch(false);
          if (fighter1.isLeft())
          {
            fighter1.setJumpType(1);
          }
          else if (fighter1.isRight())
          {
            fighter1.setJumpType(2);
          }
          else
            fighter1.setJumpType(3);
        }
        frame.repaint();
      }
      if (e.getKeyCode() == KeyEvent.VK_S) {
        // Crouch
        if (!fighter1.isAttack())
          if (!fighter1.isJump()) {
          fighter1.setCrouch(true);
          fighter1.setLeft(false);
          fighter1.setRight(false);
        }
        frame.repaint();
      }
      if (e.getKeyCode() == KeyEvent.VK_O) {
        if (!fighter2.isAttack()) {
          // System.out.println("punch");
          if (!fighter2.isJump()) {
            fighter2.setLeft(false);
            fighter2.setRight(false);
          }
          fighter2.startLightPunchTimer();
          frame.repaint();
        }
      }
      if (e.getKeyCode() == KeyEvent.VK_L) {
        if (!fighter2.isAttack()) {
          // System.out.println("kick");
          if (!fighter2.isJump()) {
            fighter2.setLeft(false);
            fighter2.setRight(false);
          }
          fighter2.startLightKickTimer();
          frame.repaint();
        }
      }
      if (e.getKeyCode() == KeyEvent.VK_P) {
        if (!fighter2.isAttack()) {
          // System.out.println("punch");
          if (!fighter2.isJump()) {
            fighter2.setLeft(false);
            fighter2.setRight(false);
          }
          fighter2.startHeavyPunchTimer();
          frame.repaint();
        }
      }
      if (e.getKeyCode() == KeyEvent.VK_SEMICOLON) {
        if (!fighter2.isAttack()) {
          // System.out.println("kick");
          if (!fighter2.isJump()) {
            fighter2.setLeft(false);
            fighter2.setRight(false);
          }
          fighter2.startHeavyKickTimer();
          frame.repaint();
        }
      }
      if (e.getKeyCode() == KeyEvent.VK_T) {
        if (!fighter1.isAttack()) {
          // System.out.println("punch");
          if (!fighter1.isJump()) {
            fighter1.setLeft(false);
            fighter1.setRight(false);
          }
          fighter1.startLightPunchTimer();
          frame.repaint();
        }
      }
      if (e.getKeyCode() == KeyEvent.VK_G) {
        if (!fighter1.isAttack()) {
          // System.out.println("kick");
          if (!fighter1.isJump()) {
            fighter1.setLeft(false);
            fighter1.setRight(false);
          }
          fighter1.startLightKickTimer();
          frame.repaint();
        }
      }
      if (e.getKeyCode() == KeyEvent.VK_Y) {
        if (!fighter1.isAttack()) {
          // System.out.println("punch");
          if (!fighter1.isJump()) {
            fighter1.setLeft(false);
            fighter1.setRight(false);
          }
          fighter1.startHeavyPunchTimer();
          frame.repaint();
        }
      }
      if (e.getKeyCode() == KeyEvent.VK_H) {
        if (!fighter1.isAttack()) {
          // System.out.println("kick");
          if (!fighter1.isJump()) {
            fighter1.setLeft(false);
            fighter1.setRight(false);
          }
          fighter1.startHeavyKickTimer();
          frame.repaint();
        }
      }
      
      
      if (e.getKeyCode() == KeyEvent.VK_C) {
        if (!gameOver){
          if (controls)
            controls = false;
          else
            controls = true;
        }
      }
      if (e.getKeyCode() == KeyEvent.VK_R) {
        reset();
      }
      frame.repaint();
    }
    
    /**
     * runs when key is released and determines appropriate action
     * 
     * @param e
     *            the key event
     */
    public void keyReleased(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        
        fighter2.setLeft(false);
      }
      if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        
        fighter2.setRight(false);
      }
      if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        // Crouch
        fighter2.setCrouch(false);
      }
      if (e.getKeyCode() == KeyEvent.VK_A) {
        
        fighter1.setLeft(false);
      }
      if (e.getKeyCode() == KeyEvent.VK_D) {
        fighter1.setRight(false);
      }
      if (e.getKeyCode() == KeyEvent.VK_S) {
        // Crouch
        fighter1.setCrouch(false);
      }
      frame.repaint();
    }
  }
  
  /**
   * Prevent fighters from overlapping for extended periods of time
   * 
   * @param f1
   *            fighter one
   * @param f2
   *            fighter two
   */
  private void push(Fighter f1, Fighter f2) {
    // Fighter 1 left
    if (f1.getDirection() == 0) {
      // If overlap; push the person who gets hit back until no more
      // overlap
      if (myBox.intersects(oppBox)) {
        // Push back
        fighter1.setX(fighter1.getX() - 5);
        fighter2.setX(fighter2.getX() + 5);
        
        // Fighter 1 attacking
        if (!fighter2.isInvincible()) {
          if (fighter1.isFullAttack()) {
            // Fighter 2 blocks
            if (fighter1.attackBlocked(fighter2)) {
              fighter2.chipDamaged(fighter1);
              fighter2.setBlocking(true);
            }
            // Fighter 2 does not block
            else {
              fighter2.damaged(fighter1);
              fighter2.startRecoilTimer();
            }
            fighter2.setX(fighter2.getX() + fighter1.knockBack());
          }
        }
        // Fighter 2 attacking
        if (!fighter1.isInvincible()) {
          if (fighter2.isFullAttack()) {
            if (fighter2.attackBlocked(fighter1)) {
              fighter1.chipDamaged(fighter2);
              fighter1.setBlocking(true);
            } else {
              fighter1.damaged(fighter2);
              fighter1.startRecoilTimer();
            }
            fighter1.setX(fighter1.getX() - fighter2.knockBack());
          }
          
        }
      }
    }
    // Fighter 1 Right
    else {
      if (myBox.intersects(oppBox)) {
        fighter1.setX(fighter1.getX() + 5);
        fighter2.setX(fighter2.getX() - 5);
        
        // Fighter 1 attacking
        if (!fighter2.isInvincible()) {
          if (fighter1.isFullAttack()) {
            if (fighter1.attackBlocked(fighter2)) {
              fighter2.chipDamaged(fighter1);
              fighter2.setBlocking(true);
            } else {
              
              fighter2.damaged(fighter1);
              fighter2.startRecoilTimer();
            }
            fighter2.setX(fighter2.getX() - fighter1.knockBack());
          }
        }
        // Fighter 2 Attacking
        if (!fighter1.isInvincible()) {
          if (fighter2.isFullAttack()) {
            if (fighter2.attackBlocked(fighter1)) {
              fighter1.chipDamaged(fighter2);
              fighter1.setBlocking(true);
            } else {
              
              fighter1.damaged(fighter2);
              fighter1.startRecoilTimer();
            }
            fighter1.setX(fighter1.getX() + fighter2.knockBack());
          }
        }
      }
    }
    
  }
  
  /**
   * Runs and updates the position, direction and actions of each fighter
   */
  public void animate() {
    while (true) {
      frame.repaint();
      if (fighter1.getHealth() > 0 && fighter2.getHealth() > 0) {
        // Update direction
        if (fighter1.getX() > fighter2.getX()) {
          if (!fighter1.isAttack())
            fighter1.setDirection(1);
          if (!fighter2.isAttack())
            fighter2.setDirection(0);
        } else {
          if (!fighter1.isAttack())
            fighter1.setDirection(0);
          if (!fighter2.isAttack())
            fighter2.setDirection(1);
        }
        
        // P1 movement
        if (!fighter1.isCrouch()) {  
          if (!fighter1.isJump() && !fighter1.isFall()){
            if (fighter1.isLeft())
              fighter1.setX(fighter1.getX() - 3);
            if (fighter1.isRight())
              fighter1.setX(fighter1.getX() + 3);
          }
        }
        // Jump (going up) (Y DIRECTION)
        if (fighter1.isJump()) {  
          if (fighter1.getJumpType() == 1)
            fighter1.setX(fighter1.getX() - 3);
          else if (fighter1.getJumpType() == 2)
            fighter1.setX(fighter1.getX() + 3);
          
          // Rise
          if (!fighter1.isFall()) {
            fighter1.setY(fighter1.getY() - 7);
            if (fighter1.getY() <= 200)
              fighter1.setFall(true);
          }
          // Fall
          else {
            fighter1.setY(fighter1.getY() + 7);
            if (fighter1.getY() == 430) {
              fighter1.setJump(false);
              fighter1.setFall(false);
              fighter1.setJumpType(0);
            }
          }
        }
        // P2 movement
        if (!fighter2.isCrouch()) {
          if (!fighter2.isJump() && !fighter2.isFall()){
            if (fighter2.isLeft())
              fighter2.setX(fighter2.getX() - 3);
            if (fighter2.isRight())
              fighter2.setX(fighter2.getX() + 3);
          }
        }
        // Jump (going up)
        if (fighter2.isJump()) {
          if (fighter2.getJumpType() == 1)
            fighter2.setX(fighter2.getX() - 3);
          else if (fighter2.getJumpType() == 2)
            fighter2.setX(fighter2.getX() + 3);
          
          // Rise
          if (!fighter2.isFall()) {
            fighter2.setY(fighter2.getY() - 7);
            if (fighter2.getY() <= 200)
              fighter2.setFall(true);
          }
          // Fall
          else {
            fighter2.setY(fighter2.getY() + 7);
            if (fighter2.getY() == 430) {
              fighter2.setJump(false);
              fighter2.setFall(false);
              fighter2.setJumpType(0);
            }
          }
        }
        // Update boxes
        if (fighter1.getDirection() == 0)
          myBox = new Rectangle(fighter1.getX(), fighter1.getY() - fighter1.getHeight(),
                                fighter1.getWidth() - 5, fighter1.getHeight());
        else
          myBox = new Rectangle(fighter1.getX() + fighter1.getStandingWidth() - fighter1.getWidth() + 5,
                                fighter1.getY() - fighter1.getHeight(), fighter1.getWidth() - 5, fighter1.getHeight());
        
        if (fighter2.getDirection() == 0) {
          oppBox = new Rectangle(fighter2.getX(), fighter2.getY() - fighter2.getHeight(),
                                 fighter2.getWidth() - 5, fighter2.getHeight());
        } else {
          oppBox = new Rectangle(fighter2.getX() + fighter2.getStandingWidth() - fighter2.getWidth() + 5,
                                 fighter2.getY() - fighter2.getHeight(), fighter2.getWidth() - 5, fighter2.getHeight());
        }
        push(fighter1, fighter2);
        
        // Keep in screen
        if (fighter1.getX() < 0)
          fighter1.setX(0);
        if (fighter1.getX() > 1350 - fighter1.getStandingWidth())
          fighter1.setX(1350 - fighter1.getStandingWidth());
        if (fighter2.getX() < 0)
          fighter2.setX(0);
        if (fighter2.getX() > 1350 - fighter2.getStandingWidth())
          fighter2.setX(1350 - fighter2.getStandingWidth());
        
        // Unblock/Block
        if (fighter1.getDirection() == 0) {
          if (fighter1.isLeft())
            fighter1.setBlock(true);
          else
            fighter1.setBlock(false);
        }
        if (fighter1.getDirection() == 1) {
          if (fighter1.isRight())
            fighter1.setBlock(true);
          else
            fighter1.setBlock(false);
        }
        if (fighter2.getDirection() == 0) {
          if (fighter2.isLeft())
            fighter2.setBlock(true);
          else
            fighter2.setBlock(false);
        }
        if (fighter2.getDirection() == 1) {
          if (fighter2.isRight())
            fighter2.setBlock(true);
          else
            fighter2.setBlock(false);
        }
        if (!fighter1.isBlock())
          fighter1.setBlocking(false);
        if (!fighter2.isBlock())
          fighter2.setBlocking(false);
        
        // Remove invincibility at end of attack
        if (!fighter1.isAttack())
          fighter2.setInvincible(false);
        if (!fighter2.isAttack())
          fighter1.setInvincible(false);
        
        // Update images
        fighter1.updateSprite();
        fighter2.updateSprite();
      } else {
        gameOver = true;
        // Determine winner
        if (fighter1.getHealth() <= 0 && fighter2.getHealth() <= 0) {
          winner = 0;
        } else if (fighter1.getHealth() <= 0) {
          winner = 2;
        } else if (fighter2.getHealth() <= 0) {
          winner = 1;
        }
      }
      try {
        Thread.sleep(15);
      } catch (Exception exc) {
      }
    }
  }
}
