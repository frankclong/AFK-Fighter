import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
/**
 * Fighter Class for AFK fighter
 * @author Austin Tsang, Frank Long, Karim Eltanahy
 * @version June 16, 2016
 */
public abstract class Fighter {
 // Counter for attacks
 private int count = 0;

 private boolean invincible;
 // SPRITES
 private Image standOne;
 private Image standTwo;
 private Image walkOne;
 private Image walkTwo;
 private Image walkThree;
 private Image walkFour;
 private Image lightPunchTwo;
 private Image lightPunchOne;
 private Image heavyPunchTwo;
 private Image heavyPunchOne;
 private Image crouchSprite;
 private Image jumping;
 private Image jumpKick;
 private Image jumpPunch;
 private Image crouchBlock;
 private Image blockSprite;
 private Image crouchKick;
 private Image crouchPunch;
 private Image lightKickOne;
 private Image lightKickTwo;
 private Image heavyKickOne;
 private Image heavyKickTwo;
 private Image hitSprite;
 private Image currentSprite;

 // Unique variables
 private String name;
 private int lightPunchDamage;
 private int heavyPunchDamage;
 private int lightKickDamage;
 private int heavyKickDamage;
 private int jumpPunchDamage;
 private int jumpKickDamage;
 private int crouchPunchDamage;
 private int crouchKickDamage;
 private int health;

 // Action variables
 private boolean jump = false;
 private boolean fall = false;
 private boolean crouch = false;
 private boolean left = false;
 private boolean right = false;
 private boolean halfLightPunch = false;
 private boolean lightPunch = false;
 private boolean lightKick = false;
 private boolean halfLightKick = false;
 private boolean block = false;
 private boolean blocking = false;
 private boolean heavyHalfPunch = false;
 private boolean heavyPunch = false;
 private boolean heavyHalfKick = false;
 private boolean heavyKick = false;
 private boolean hit = false;

 // Position
 private int y = 430;
 private int x = 100;
 private int walkStyle = 1;
 private int standStyle = 0;
 // 0 - face left; 1- face right
 private int direction = 0;
 private int jumpType = 0; 
 private int standingWidth;

 // Timers
 private Timer lightPunchTimer;
 private Timer heavyPunchTimer;
 private Timer lightKickTimer;
 private Timer heavyKickTimer;
 private Timer walkTimer;
 private Timer standTimer;
 private Timer recoilTimer;

 // Life bar variables
 private int originalLife;
 private int lifeLost;
 private int redWidth;

 /**
  * Create a fighter
  * 
  * @param name
  *            name
  * @param x
  *            x position
  */
 Fighter(String name, int x, int lPunch, int hPunch, int lKick, int hKick) {
  this.name = name;
  this.setX(x);
  // Initialize images
  standOne = new ImageIcon(name + "_standingOne.png").getImage();
 standTwo = new ImageIcon(name + "_standingTwo.png").getImage();
  walkOne = new ImageIcon(name + "_walkOne.png").getImage();
  walkTwo = new ImageIcon(name + "_walkTwo.png").getImage();
  walkThree = new ImageIcon(name + "_walkThree.png").getImage();
  walkFour = new ImageIcon(name + "_walkFour.png").getImage();
  lightPunchTwo = new ImageIcon(name + "_lightPunchTwo.png").getImage();
  lightPunchOne = new ImageIcon(name + "_lightPunchOne.png").getImage();
  heavyPunchTwo = new ImageIcon(name + "_heavyPunchTwo.png").getImage();
  heavyPunchOne = new ImageIcon(name + "_heavyPunchOne.png").getImage();
  crouchSprite = new ImageIcon(name + "_crouch.png").getImage();
  jumping = new ImageIcon(name + "_jump.png").getImage();
  jumpKick = new ImageIcon(name + "_jumpKick.png").getImage();
  jumpPunch = new ImageIcon(name + "_jumpPunch.png").getImage();
  crouchBlock = new ImageIcon(name + "_crouchBlock.png").getImage();
  blockSprite = new ImageIcon(name + "_block.png").getImage();
  crouchKick = new ImageIcon(name + "_crouchKick.png").getImage();
  crouchPunch = new ImageIcon(name + "_crouchPunch.png").getImage();
  lightKickOne = new ImageIcon(name + "_lightKickOne.png").getImage();
  lightKickTwo = new ImageIcon(name + "_lightKickTwo.png").getImage();
  heavyKickOne = new ImageIcon(name + "_heavyKickOne.png").getImage();
  heavyKickTwo = new ImageIcon(name + "_heavyKickTwo.png").getImage();
  hitSprite = new ImageIcon(name + "_hitRecoil.png").getImage();
  currentSprite = standOne;
  setStandingWidth(standOne.getWidth(null));

  // Initialize Timers
  lightPunchTimer = new Timer(lPunch, new TimerEventHandler());
  heavyPunchTimer = new Timer(hPunch, new TimerEventHandler());
  lightKickTimer = new Timer(lKick, new TimerEventHandler());
  heavyKickTimer = new Timer(hKick, new TimerEventHandler());
  walkTimer = new Timer(175, new TimerEventHandler());
  standTimer = new Timer(400, new TimerEventHandler());
  recoilTimer = new Timer(250, new TimerEventHandler());
 }

 /**
  * Start the walk and stand timers
  */
 public void startWalkStandTimer() {
  this.walkTimer.start();
  this.standTimer.start();
 }

 /**
  * Start the light punch timer
  */
 public void startLightPunchTimer() {
  lightPunchTimer.start();
 }

 /**
  * Start the heavy punch timer
  */
 public void startHeavyPunchTimer() {
  heavyPunchTimer.start();
 }

 /**
  * Start the light kick timer
  */
 public void startLightKickTimer() {
  lightKickTimer.start();
 }

 /**
  * Start the heavy kick timer
  */
 public void startHeavyKickTimer() {
  heavyKickTimer.start();
 }

 /**
  * Start the recoil timer
  */
 public void startRecoilTimer() {
  recoilTimer.start();
 }

 /**
  * @return the current sprite
  */
 public Image currentSprite() {
  return currentSprite;
 }

 /**
  * @return the width of the current sprite
  */
 public int getWidth() {
  return this.currentSprite.getWidth(null);
 }

 /**
  * @return the height of the current sprite
  */
 public int getHeight() {
  return this.currentSprite.getHeight(null);
 }

 /**
  * 
  * @return whether or not the fighter is jumping
  */
 public boolean isJump() {
  return jump;
 }

 /**
  * 
  * @param jump
  *            whether or not the fighter should be jumping
  */
 public void setJump(boolean jump) {
  this.jump = jump;
 }

 /**
  * 
  * @return whether or not the fighter is crouching
  */
 public boolean isCrouch() {
  return crouch;
 }

 /**
  * 
  * @param jump
  *            whether or not the fighter should be crouching
  */
 public void setCrouch(boolean crouch) {
  this.crouch = crouch;
 }

 /**
  * 
  * @return whether or not the fighter is moving left
  */
 public boolean isLeft() {
  return left;
 }

 /**
  * 
  * @param jump
  *            whether or not the fighter should be moving left
  */
 public void setLeft(boolean left) {
  this.left = left;
 }

 /**
  * 
  * @return whether or not the fighter is moving right
  */
 public boolean isRight() {
  return right;
 }

 /**
  * 
  * @param jump
  *            whether or not the fighter should be moving right
  */
 public void setRight(boolean right) {
  this.right = right;
 }

 /**
  * 
  * @return the x position
  */
 public int getX() {
  return x;
 }

 /**
  * 
  * @param x
  *            the desired x position
  */
 public void setX(int x) {
  this.x = x;
 }

 /**
  * 
  * @return the y position
  */
 public int getY() {
  return y;
 }

 /**
  * 
  * @param y
  *            the desired y position
  */
 public void setY(int y) {
  this.y = y;
 }

 /**
  * 
  * @return whether or not the character is light punching
  */
 public boolean isPunch() {
  return lightPunch;
 }

 /**
  * 
  * @param punch
  *            whether or not the fighter should be light punching
  */
 public void setPunch(boolean punch) {
  this.lightPunch = punch;
 }

 /**
  * 
  * @return whether or not the character is light kicking
  */
 public boolean isKick() {
  return lightKick;
 }

 /**
  * 
  * @param punch
  *            whether or not the fighter should be light kicking
  */
 public void setKick(boolean kick) {
  this.lightKick = kick;
 }

 /**
  * 
  * @return the direction the character is facing (0-right, 1-left)
  */
 public int getDirection() {
  return direction;
 }

 /**
  * 
  * @param direction
  *            the direction the character is facing (0-right, 1-left)
  */
 public void setDirection(int direction) {
  this.direction = direction;
 }

 /**
  * 
  * @return the fighter's current health
  */
 public int getHealth() {
  return health;
 }

 /**
  * 
  * @param health
  *            the fighter's health
  */
 public void setHealth(int health) {
  this.health = health;
 }

 /**
  * Deal damage if fighter is not blocking
  * 
  * @param opp
  *            the opponent
  */
 public void damaged(Fighter opp) {
  health -= opp.currentAttack();
  lifeLost += opp.currentAttack();
  if (lifeLost > originalLife) {
   lifeLost = originalLife;
  }
  setRedWidth((500 * this.lifeLost) / originalLife);

  this.setInvincible(true);
  this.setHit(true);
 }

 /**
  * Deal reduced damage if fighter is blocking
  * 
  * @param opp
  *            the opponent
  */
 public void chipDamaged(Fighter opp) {
  health -= opp.currentAttack() / 5;
  lifeLost += opp.currentAttack() / 5;
  if (lifeLost > originalLife) {
   lifeLost = originalLife;
  }
  setRedWidth((500 * this.lifeLost) / originalLife);
  this.setInvincible(true);
 }

 /**
  * Update the current sprite based on actions
  */
 public void updateSprite() {
  // Hit
  if (isHit()) {
   this.currentSprite = hitSprite;
  }
  // Crouch
  else if (this.isCrouch()) {
   // System.out.println("crouching");
   if (this.isPunch() || this.isHeavyPunch())
    this.currentSprite = crouchPunch;
   else if (this.isKick() || this.isHeavyKick())
    this.currentSprite = crouchKick;
   else if (this.isBlock())
    this.currentSprite = crouchBlock;
   else
    this.currentSprite = crouchSprite;
   return;
  }
  // Jump
  else if (this.isJump()) {
   // System.out.println("jumping");
   if (this.isPunch() || this.isHeavyPunch())
    this.currentSprite = jumpPunch;
   else if (this.isKick() || this.isHeavyKick())
    this.currentSprite = jumpKick;
   else
    this.currentSprite = jumping;
   return;
  } else {
   // System.out.println("standing");
   // Attacking
   if (this.isHalfPunch())
    this.currentSprite = lightPunchOne;
   else if (this.isPunch())
    this.currentSprite = lightPunchTwo;

   else if (this.isHeavyHalfPunch())
    this.currentSprite = heavyPunchOne;
   else if (this.isHeavyPunch())
    this.currentSprite = heavyPunchTwo;
   else if (this.isHalfKick())
    this.currentSprite = lightKickOne;
   else if (this.isKick())
    this.currentSprite = lightKickTwo;
   else if (this.isHeavyHalfKick())
    this.currentSprite = heavyKickOne;
   else if (this.isHeavyKick())
    this.currentSprite = heavyKickTwo;

   // Walk
   else if (this.isLeft() || this.isRight()) {
    if (this.walkStyle == 1)
     this.currentSprite = walkOne;
    else if (this.walkStyle == 2)
     this.currentSprite = walkTwo;
    else if (this.walkStyle == 3)
     this.currentSprite = walkThree;
    else
     this.currentSprite = walkFour;
   }
   // Stand
   else {
    if (standStyle == 0)
     this.currentSprite = standOne;
    else
     this.currentSprite = standTwo;
   }
   // Block
   if (this.isBlocking())
    this.currentSprite = blockSprite;
   return;
  }

 }

 /**
  * 
  * @return whether or not the fighter is in middle of light punch
  */
 public boolean isHalfPunch() {
  return halfLightPunch;
 }

 /**
  * 
  * @param halfPunch
  *            whether or not the fighter should be in the middle of light
  *            punch
  */
 public void setHalfPunch(boolean halfPunch) {
  this.halfLightPunch = halfPunch;
 }

 /**
  * Cycle through walk sprites
  */
 public void nextWalk() {
  if (walkStyle < 4)
   this.walkStyle++;
  else
   this.walkStyle = 1;
 }

 /**
  * Cycle through stand sprites
  */
 public void changeStand() {
  if (standStyle == 0)
   standStyle = 1;
  else
   standStyle = 0;
 }

 /**
  * 
  * @return whether or not the fighter is falling
  */
 public boolean isFall() {
  return fall;
 }

 /**
  * 
  * @param fall
  *            whether or not the fighter should be falling
  */
 public void setFall(boolean fall) {
  this.fall = fall;
 }

 /**
  * 
  * @return whether or not the fighter is blocking
  */
 public boolean isBlock() {
  return block;
 }

 /**
  * 
  * @param block
  *            whether or not the fighter should be blocking
  */
 public void setBlock(boolean block) {
  this.block = block;
 }

 /**
  * 
  * @return whether or not fighter is in middle of light kick
  */
 public boolean isHalfKick() {
  return halfLightKick;
 }

 /**
  * 
  * @param halfKick
  *            whether or not fighter should be in the middle of light kick
  */
 public void setHalfKick(boolean halfKick) {
  this.halfLightKick = halfKick;
 }

 /**
  * @return whether or not fighter is heavy kicking
  */
 public boolean isHeavyKick() {
  return heavyKick;
 }

 /**
  * 
  * @param heavyKick
  *            whether or not fighter should be heavy kicking
  */
 public void setHeavyKick(boolean heavyKick) {
  this.heavyKick = heavyKick;
 }

 /**
  * @return whether or not fighter is in middle of heavy kicking
  */
 public boolean isHeavyHalfKick() {
  return heavyHalfKick;
 }

 /**
  * 
  * @param heavyHalfKick
  *            whether or not fighter should be in the middle of heavy
  *            kicking
  */
 public void setHeavyHalfKick(boolean heavyHalfKick) {
  this.heavyHalfKick = heavyHalfKick;
 }

 /**
  * 
  * @return whether or not fighter is heavy punching
  */
 public boolean isHeavyPunch() {
  return heavyPunch;
 }

 /**
  * 
  * @param heavyPunch
  *            whether or not fighter should be heavy punching
  */
 public void setHeavyPunch(boolean heavyPunch) {
  this.heavyPunch = heavyPunch;
 }

 /**
  * 
  * @return whether or not fighter should be in the middle of heavy punching
  */
 public boolean isHeavyHalfPunch() {
  return heavyHalfPunch;
 }

 /**
  * 
  * @param heavyHalfPunch
  *            whether or not fighter should be in the middle of heavy
  *            punching
  */
 public void setHeavyHalfPunch(boolean heavyHalfPunch) {
  this.heavyHalfPunch = heavyHalfPunch;
 }

 /**
  * Determine whether or not the fighter is attacking
  * 
  * @return whether or not the fighter is in the middle of an attack
  */
 public boolean isAttack() {
  if (this.lightPunchTimer.isRunning() || this.lightKickTimer.isRunning() || this.heavyKickTimer.isRunning()
    || this.heavyPunchTimer.isRunning())
   return true;
  return false;
 }

 /**
  * Determine whether or not the fighter is attacking
  * 
  * @return whether or not the fighter is at the end of an attack
  */
 public boolean isFullAttack() {
  if (this.isPunch() || this.isKick() || this.isHeavyKick() || this.isHeavyPunch())
   return true;
  return false;
 }

 /**
  * @return whether or not fighter is hit
  */
 public boolean isHit() {
  return hit;
 }

 /**
  * 
  * @param hit
  *            whether or not fighter should be hit
  */
 public void setHit(boolean hit) {
  this.hit = hit;
 }

 /**
  * 
  * @return whether or not fighter is visually blocking
  */
 public boolean isBlocking() {
  return blocking;
 }

 /**
  * 
  * @param blocking
  *            whether or not fighter should be visually blocking
  */
 public void setBlocking(boolean blocking) {
  this.blocking = blocking;
 }

 /**
  * 
  * @return the name of the fighter
  */
 public String getName() {
  return name;
 }

 /**
  * 
  * @param name
  *            the name of the fighter
  */
 public void setName(String name) {
  this.name = name;
 }

 /**
  * 
  * @return fighter's light punch damage
  */
 public int getLightPunchDamage() {
  return lightPunchDamage;
 }

 /**
  * 
  * @param lightPunchDamage
  *            fighter's light punch damage
  */
 public void setLightPunchDamage(int lightPunchDamage) {
  this.lightPunchDamage = lightPunchDamage;
 }

 /**
  * 
  * @return fighter's heavy punch damage
  */
 public int getHeavyPunchDamage() {
  return heavyPunchDamage;
 }

 /**
  * 
  * @param heavyPunchDamage
  *            fighter's heavy punch damage
  */
 public void setHeavyPunchDamage(int heavyPunchDamage) {
  this.heavyPunchDamage = heavyPunchDamage;
 }

 /**
  * 
  * @return fighter's light kick damage
  */
 public int getLightKickDamage() {
  return lightKickDamage;
 }

 /**
  * 
  * @param lightKickDamage
  *            fighter's light kick damage
  */
 public void setLightKickDamage(int lightKickDamage) {
  this.lightKickDamage = lightKickDamage;
 }

 /**
  * 
  * @return fighter's heavy kick damage
  */
 public int getHeavyKickDamage() {
  return heavyKickDamage;
 }

 /**
  * 
  * @param heavyKickDamage
  *            fighter's heavy kick damage
  */
 public void setHeavyKickDamage(int heavyKickDamage) {
  this.heavyKickDamage = heavyKickDamage;
 }

 /**
  * 
  * @return fighter's jump punch damage
  */
 public int getJumpPunchDamage() {
  return jumpPunchDamage;
 }

 /**
  * 
  * @param jumpPunchDamage
  *            fighter's jump punch damage
  */
 public void setJumpPunchDamage(int jumpPunchDamage) {
  this.jumpPunchDamage = jumpPunchDamage;
 }

 /**
  * 
  * @return fighter's jump kick damage
  */
 public int getJumpKickDamage() {
  return jumpKickDamage;
 }

 /**
  * 
  * @param jumpKickDamage
  *            fighter's jump kick damage
  */
 public void setJumpKickDamage(int jumpKickDamage) {
  this.jumpKickDamage = jumpKickDamage;
 }

 /**
  * 
  * @return fighter's crouch punch damage
  */
 public int getCrouchPunchDamage() {
  return crouchPunchDamage;
 }

 /**
  * 
  * @param crouchPunchDamage
  *            fighter's crouch punch damage
  */
 public void setCrouchPunchDamage(int crouchPunchDamage) {
  this.crouchPunchDamage = crouchPunchDamage;
 }

 /**
  * 
  * @return the fighter's crouch kick damage
  */
 public int getCrouchKickDamage() {
  return crouchKickDamage;
 }

 /**
  *
  * @param crouchKickDamage
  *            the fighter's crouch kick damage
  */
 public void setCrouchKickDamage(int crouchKickDamage) {
  this.crouchKickDamage = crouchKickDamage;
 }

 /**
  * 
  * @return the width of the fighter's standing sprite
  */
 public int getStandingWidth() {
  return standingWidth;
 }

 /**
  * 
  * @param standingWidth
  *            the width of the fighter's standing sprite
  */
 public void setStandingWidth(int standingWidth) {
  this.standingWidth = standingWidth;
 }

 /**
  * Determine the damage of the current attack
  */
 public int currentAttack() {
  if (this.isCrouch()) {
   if (this.isPunch() || this.isHeavyPunch())
    return getCrouchPunchDamage();
   else if (this.isKick() || this.isHeavyKick())
    return getCrouchKickDamage();
  } else if (this.isJump()) {
   if (this.isPunch() || this.isHeavyPunch())
    return getJumpPunchDamage();
   else if (this.isKick() || this.isHeavyKick())
    return getJumpKickDamage();
  } else if (this.isPunch())
   return getLightPunchDamage();
  else if (this.isHeavyPunch())
   return getHeavyPunchDamage();
  else if (this.isKick())
   return getLightKickDamage();
  else if (this.isHeavyKick())
   return getHeavyKickDamage();
  return 0;
 }

 /**
  * Determine if opponent blocked the attack
  * 
  * @param opp
  *            the opponent
  */
 public boolean attackBlocked(Fighter opp) {
  // No block
  if (!opp.isBlock()) {
   return false;
  } else {
   // Stand block
   if (!opp.isCrouch() && this.isCrouch()) {
    return false;
   }
   // Crouch block
   else if (opp.isCrouch() && this.isJump()) {
    return false;
   } else
    return true;
  }
 }

 /**
  * 
  * @return whether or not the character is invincible
  */
 public boolean isInvincible() {
  return invincible;
 }

 /**
  * 
  * @param invincible
  *            whether or not the character should be invincible
  */
 public void setInvincible(boolean invincible) {
  this.invincible = invincible;
 }

 /**
  * Determine number of pixels opponent is knocked back after an attack
  */
 public int knockBack() {
  if (this.isJump()) {
   if (this.isPunch() || this.isHeavyPunch()) {
    return 15;
   }
   if (this.isKick() || this.isHeavyKick()) {
    return 25;
   }
  } else if (this.isCrouch()) {
   if (this.isPunch() || this.isHeavyPunch()) {
    return 5;
   }
   if (this.isKick() || this.isHeavyKick()) {
    return 15;
   }
  } else {
   if (this.isPunch()) {
    return 10;
   }
   if (this.isHeavyPunch()) {
    return 15;
   }
   if (this.isKick()) {
    return 20;
   }
   if (this.isHeavyKick()) {
    return 25;
   }
  }
  return 0;
 }

 /**
  * 
  * @return the width of the red rectangle
  */
 public int getRedWidth() {
  return redWidth;
 }

 /**
  * 
  * @param redWidth
  *            the width of the red rectangle
  */
 public void setRedWidth(int redWidth) {
  this.redWidth = redWidth;
 }

 /**
  * 
  * @param originalLife
  *            the fighter's starting life
  */
 public void setOriginalLife(int originalLife) {
  this.originalLife = originalLife;
 }
 
 public void setJumpType(int jumpType){
   this.jumpType = jumpType;
 }
 
 public int getJumpType(){
   return jumpType;
 }

 /**
  * An inner class to deal with the timer events
  */
 private class TimerEventHandler implements ActionListener {

  /**
   * Handles all timer events
   *
   * @param event
   *            the Timer event
   */
  public void actionPerformed(ActionEvent event) {
   if (event.getSource() == lightPunchTimer) {
    if (count == 0) {
     setHalfPunch(true);
     count++;
    } else if (count == 1 || count == 2 || count == 3) {
     setPunch(true);
     setHalfPunch(false);
     count++;
    } else if (count == 4) {
     setHalfPunch(true);
     setPunch(false);
     count++;
    } else {

     count = 0;
     lightPunchTimer.stop();
     setHalfPunch(false);

    }
   }
   if (event.getSource() == heavyPunchTimer) {
    if (count == 0) {
     setHeavyHalfPunch(true);
     count++;
    } else if (count == 1 || count == 2 || count == 3) {
     setHeavyPunch(true);
     setHeavyHalfPunch(false);
     count++;
    } else if (count == 4) {
     setHeavyHalfPunch(true);
     setHeavyPunch(false);
     count++;
    } else {
     count = 0;
     heavyPunchTimer.stop();
     setHeavyHalfPunch(false);
    }
   }

   // Kick timer (same as punch except different delays)
   if (event.getSource() == lightKickTimer) {
    if (count == 0) {
     setHalfKick(true);
     count++;
    } else if (count == 1 || count == 2 || count == 3) {
     setKick(true);
     setHalfKick(false);
     count++;
    } else if (count == 4) {
     setHalfKick(true);
     setKick(false);
     count++;
    } else {
     count = 0;
     lightKickTimer.stop();
     setHalfKick(false);
    }
   }

   if (event.getSource() == heavyKickTimer) {
    if (count == 0) {
     setHeavyHalfKick(true);
     count++;
    } else if (count == 1 || count == 2 || count == 3) {
     setHeavyKick(true);
     setHeavyHalfKick(false);
     count++;
    } else if (count == 4) {
     setHeavyHalfKick(true);
     setHeavyKick(false);
     count++;
    } else {
     count = 0;
     heavyKickTimer.stop();
     setHeavyHalfKick(false);

    }
   }

   // Walk
   if (event.getSource() == walkTimer) {
    if (isLeft() || isRight()) {
     // Change walk sprite
     nextWalk();
    }
   }

   // Stand
   if (event.getSource() == standTimer) {
    if (!isLeft() || !isRight()) {
     changeStand();
    }
   }

   if (event.getSource() == recoilTimer) {
    setHit(false);
   }

  }
 }

}
