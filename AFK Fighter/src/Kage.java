/**
 * Kage (type of fighter) class for AFK Fighter
 * 
 * @author Austin Tsang, Frank Long, Karim Eltanahy
 * @version June 16, 2016
 */
public class Kage extends Fighter {
 /**
  * Construct a Kage Fighter
  */
 Kage(int x) {
  super("Kage", x,35,50,50,75);
  // Set damages and health/life
  this.setLightPunchDamage(10);
  this.setHeavyPunchDamage(15);
  this.setLightKickDamage(10);
  this.setHeavyKickDamage(20);
  this.setJumpPunchDamage(15);
  this.setJumpKickDamage(15);
  this.setCrouchPunchDamage(10);
  this.setCrouchKickDamage(15);
  this.setHealth(1000);
  this.setOriginalLife(1000);
 }
}
