package world.elements.Ship;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;

import world.World;
import world.elements.Bullet;
import world.elements.Item;
import world.elements.SpaceThing;
import world.elements.Weapon;
import world.elements.libs.ImageType;
import world.elements.physics.Location;
import world.elements.physics.Vector;

/**
 * @author Izzy Cecil 
 * 			Dec 18, 2010 
 * 			The most important SpaceThing. Can be
 *         controlled by a player. Has its own set of stats bassed on an
 *         inventory object. Can change its weapon as well.
 */
public class Ship extends SpaceThing {
	/**
	 * UID for serialization
	 */
	private static final long serialVersionUID = 1;

	private Inventory inventory;

	private int swapTimer;
	private int life;

	/**
	 * Builds a ship at the given spawnPoint. Uses the default spriteType for
	 * Ships (SHIP_1).
	 * 
	 * @param spawnPoint
	 */
	public Ship(Location spawnPoint) {
		super();
		location = spawnPoint;
		spriteType = ImageType.SHIP_1; // TODO: In the FUTURE! we could have
										// multiple
										// shiptypes. For now, just the one.
		centerX = 10;
		centerY = 15;

		life = 100; // Shields and cooldown time should be varied based on
					// stats.
		swapTimer = (int) (500 + Math.random() * 1500);
		inventory = new Inventory();
		compile();
	}

	/**
	 * Calls super.physics() Will also affect any timers the ship needs to keep
	 * track of.
	 * 
	 * @see world.elements.SpaceThing#physics(java.util.ArrayList)
	 */
	@Override
	public void physics(ArrayList<SpaceThing> physList) {
		super.physics(physList);
		swapTimer--;
		inventory.reload();
	}

	/**
	 * Calls super.paint(), but will then draw a health bar for the ship.
	 * 
	 * @see world.elements.SpaceThing#paint(java.awt.Graphics, double, double)
	 */
	@Override
	public void paint(Graphics g, double originX, double originY) {
		super.paint(g, originX, originY);

		if (life > 33)
			g.setColor(Color.green);
		else
			g.setColor(Color.red);

		g.fillRect((int) (getX() - originX), (int) (getY() + 27 - originY),
				life / 5 + 1, 3);
	}

	/**
	 * Will build a triangle hit box for the ship.
	 * 
	 * @see world.elements.SpaceThing#compile()
	 */
	public void compile() {
		hitBox = new Polygon();
		hitBox.addPoint((int) getX() + 10, (int) getY());
		hitBox.addPoint((int) getX() + 20, (int) getY() + 20);
		hitBox.addPoint((int) getX(), (int) getY() + 20);
		rotateHitBox(vector.getRadians());
	}

	/**
	 * Will damage the ship.
	 * 
	 * @param damage
	 */
	public void hurt(double damage) {
		damage = (damage - inventory.getShields()) / inventory.getShields();
		if (damage > 0)
			life -= damage;
	}

	/**
	 * If possible, will build a bullet, and ad that bullet into the fireList.
	 * 
	 * @param fireList
	 */
	public void fire(ArrayList<Bullet> fireList) {
			inventory.fire(fireList,this.location,this.vector);
	}

	public boolean canSwap() {
		return (swapTimer <= 0);
	}

	public boolean brainSwap() {
		if (canSwap()) {
			swapTimer = (int) (100 + Math.random() * 200);
			return true;
		} else
			return false;
	}

	@Override
	public void collision(SpaceThing thing) {
		if (thing instanceof Ship) {
			double originalX = vector.getxSpeed();
			double originalY = vector.getySpeed();
			
			this.hurt(Math.abs(thing.getxSpeed() - originalX));
			((Ship) thing).hurt(Math.abs(thing.getxSpeed() - originalX));
			this.hurt(Math.abs(thing.getySpeed() - originalY));
			((Ship) thing).hurt(Math.abs(thing.getySpeed() - originalY));
			
			this.accelX(0.8 * (thing.getxSpeed() - originalX));
			this.accelY(0.8 * (thing.getySpeed() - originalY));
			thing.accelX(0.8 * (originalX - thing.getxSpeed()));
			thing.accelY(0.8 * (originalY - thing.getySpeed()));
			while (isColliding(thing)) {
				displace(thing);
				thing.displace(this);
			}
		}
	}
	
	public double getThrusters(){
		return inventory.getSpeed();
	}
	
	public void pickupItem(Item i){
		inventory.addItem(i);
	}
	
	public void dropItem(int index, World world){
		Item i = inventory.dropItem(index);	//grab item
		Location l = new Location(location.getxLocal()-vector.getxSpeed(),location.getyLocal()-vector.getySpeed());
		Vector v = new Vector(vector.getxSpeed() - vector.getxSpeed()*.5,vector.getySpeed()-vector.getySpeed()*.5);
		i.setLocation(l);
		i.setVector(v);
		i.drop();
		world.add(i);						//add to world.
	}
	
	
	//TODO: Eventually this needs to eject the old weapon.
	public void pickupWeapon(Weapon w){
		inventory.pickupWeapon(w);
	}
	
	public Inventory getInventory(){
		return inventory;
	}

	/**
	 * Checks if the ship has any shields left.
	 * 
	 * @see world.elements.SpaceThing#isDead()
	 */
	@Override
	public boolean isDead() {
		return (life <= 0);
	}

	@Override
	public void die() {
		// TODO: Eject inventory
	}

}
