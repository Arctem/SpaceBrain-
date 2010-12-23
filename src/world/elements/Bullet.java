package world.elements;

import java.awt.Polygon;
import java.util.ArrayList;

import world.elements.Ship.Ship;
import world.elements.libs.ImageType;
import world.elements.physics.Location;
import world.elements.physics.Vector;

/**
 * @author Izzy Cecil
 * Dec 15, 2010
 * 
 */
public class Bullet extends SpaceThing{
	private static final long serialVersionUID = 1L;
	
	private int lifespan, damage;
	private  double baseSpeed;
	
	public Bullet(Location loc, Vector originalVec, double speed) {
		super();
		spriteType = ImageType.BULLET_1;
		lifespan = 50;		//TODO: As we implement different weapons, weapon stats such as this should be variable.
		damage = 3;			//Same case here as above.
							//NOTE: Because ships are hurt in all collisions, damage must be -2 to have no effect.
		centerX = centerY = 5;
		baseSpeed = speed;
		
		//TODO: There is a better way to do this.
		location.setxLocal(loc.getxLocal() + centerX);
		location.setyLocal(loc.getyLocal() + centerY);
		vector.setRadians(originalVec.getRadians());
		double c = baseSpeed + Math.sqrt(Math.pow(originalVec.getxSpeed(), 2) + Math.pow(originalVec.getySpeed(), 2));
		vector.setxSpeed(c * Math.sin(originalVec.getRadians()));
		vector.setySpeed(-c * Math.cos(originalVec.getRadians()));
		
		vector.friction = 1;
		
		
		for(int i = 0; i < 3; i++)
			location.move(vector);
		
		
		compile();
	}
	
	public Bullet(Location loc, Vector originalVec, double speed, double angleOfset) {
		super();
		spriteType = ImageType.BULLET_1;
		lifespan = 50;		//TODO: As we implement different weapons, weapon stats such as this should be variable.
		damage = 3;			//Same case here as above.
							//NOTE: Because ships are hurt in all collisions, damage must be -2 to have no effect.
		centerX = centerY = 5;
		baseSpeed = speed;
		
		//TODO: There is a better way to do this.
		location.setxLocal(loc.getxLocal() + centerX);
		location.setyLocal(loc.getyLocal() + centerY);
		vector.setRadians(originalVec.getRadians()+angleOfset);
		double c = baseSpeed + Math.sqrt(Math.pow(originalVec.getxSpeed(), 2) + Math.pow(originalVec.getySpeed(), 2));
		vector.setxSpeed(c * Math.sin(vector.getRadians()));
		vector.setySpeed(-c * Math.cos(vector.getRadians()));
		
		vector.friction = 1;
		
		
		for(int i = 0; i < 3; i++)
			location.move(vector);
		
		
		compile();
	}
	
	@Override
	public void physics(ArrayList<SpaceThing> physList) {
		super.physics(physList);
		
		lifespan--;
	}

	@Override
	public void collision(SpaceThing thing) {
		
		if(!(thing instanceof Bullet))
			lifespan = 0;
		if(thing instanceof Ship) {
			thing.accelX(getxSpeed() / 8);
			thing.accelY(getySpeed() / 8);
			((Ship) thing).hurt(damage);
		}
		else if(thing instanceof Item){
			thing.accelX(getxSpeed() / 10);
			thing.accelY(getySpeed() / 10);
		}
		
	}

	@Override
	public void compile() {
		// TODO: This will also need to be changed when we figure out why library hitboxes aren't working
		
		hitBox = new Polygon();
		hitBox.addPoint((int)getX(), (int)getY());
		hitBox.addPoint((int) getX() + 10, (int) getY());
		hitBox.addPoint((int) getX() + 10, (int) getY() + 10);
		hitBox.addPoint((int) getX(), (int) getY() + 10);
		rotateHitBox(vector.getRadians());
		
	}

	@Override
	public boolean isDead() {
		return (lifespan <= 0);
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

}
