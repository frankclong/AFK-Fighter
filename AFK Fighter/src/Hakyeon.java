/**
 * Hakyeon (type of fighter) class for AFK Fighter
 * 
 * @author Austin Tsang, Frank Long, Karim Eltanahy
 * @version June 16, 2016
 */
public class Hakyeon extends Fighter {
 /**
  * Constructs a Hakyeon fighter
  */
 Hakyeon(int x) {
  super("Hakyeon", x, 50, 75,75,100);
  // Set damages and health/life
  this.setLightPunchDamage(15);
  this.setHeavyPunchDamage(20);
  this.setLightKickDamage(15);
  this.setHeavyKickDamage(25);
  this.setJumpPunchDamage(15);
  this.setJumpKickDamage(20);
  this.setCrouchPunchDamage(10);
  this.setCrouchKickDamage(15);
  this.setHealth(1000);
  this.setOriginalLife(1000);
 }
}
