package world.elements;

import java.awt.Polygon;
import java.util.ArrayList;

import world.elements.Ship.Ship;
import world.elements.physics.Location;
import world.elements.physics.Vector;

public class Weapon extends SpaceThing {

	private double maxShotTime;
	private double shotTimer;
	private double attack;
	private double bulletSpeed;
	private WeaponType firepattern;
	private boolean pickedUp;

	public Weapon() {
		maxShotTime=shotTimer = 5;
		attack = 3;
		bulletSpeed = 10;
		firepattern = WeaponType.TRIPLE;
		centerX = centerY = 5;
		pickedUp = false;
	}
	
	public void fire(ArrayList<Bullet> fireList, Location l, Vector v){
		if(shotTimer <= 0){
			switch(firepattern){
			case SINGLE: fireList.add(new Bullet(l, v, bulletSpeed)); break;
			case TRIPLE: fireList.add(new Bullet(l,v,bulletSpeed)); fireList.add(new Bullet(l,v,bulletSpeed,Math.PI/6.0));fireList.add(new Bullet(l,v,bulletSpeed,Math.PI/-6.0)); break;
			default: break;
			}
			shotTimer = maxShotTime;
		}
	}
	
	public void reload(){
		if(shotTimer>0)
			shotTimer --;
	}

	@Override
	public void compile() {
		// TODO Auto-generated method stub
		hitBox = new Polygon();
		hitBox.addPoint((int)getX(), (int)getY());
		hitBox.addPoint((int) getX() + 10, (int) getY());
		hitBox.addPoint((int) getX() + 10, (int) getY() + 10);
		hitBox.addPoint((int) getX(), (int) getY() + 10);
		rotateHitBox(vector.getRadians());
	}

	@Override
	public void collision(SpaceThing thing) {
		if(thing instanceof Item || thing instanceof Weapon){
			double originalX = vector.getxSpeed();
			double originalY = vector.getySpeed();
			this.accelX(0.8*(thing.getxSpeed()-originalX));
			this.accelY(0.8*(thing.getySpeed()-originalY));
			thing.accelX(0.8*(originalX-thing.getxSpeed()));
			thing.accelY(0.8*(originalY-thing.getySpeed()));
			while (isColliding(thing)) {
				displace(thing);
				thing.displace(this);
			}
		} else if(thing instanceof Ship){
			pickedUp = true;
			((Ship) thing).pickupWeapon(this);
		}		
	}

	@Override
	public boolean isDead() {
		return pickedUp;
	}
	@Override
	public void die() {}
	
	public WeaponType getFirepattern(){
		return firepattern;
	}
}
