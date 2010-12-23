package world.elements;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

import world.elements.libs.ImageLib;
import world.elements.libs.ImageType;
import world.elements.physics.Location;
import world.elements.physics.Vector;

/**
 * @author Izzy Cecil
 * Dec 15, 2010
 * The basic abstract class for all physical objects in the world.
 * Acts as the templates for ships, items, debris, and bullets.
 */
public abstract class SpaceThing implements Serializable {
	/**
	 * UID for Serialization perposes.
	 */
	private static final long serialVersionUID = 1;

	/**
	 * Location of the Space thing
	 */
	protected Location location;
	/**
	 * Velocity of the Space thing
	 */
	protected Vector vector;
	/**
	 * Indicates the appropriate sprite to use when drawing
	 */
	protected ImageType spriteType;
	/**
	 * Hit box used for collitions
	 */
	protected Polygon hitBox;
	/**
	 * Center location of image
	 */
	protected int centerX, centerY;
	

	/**
	 * Builds a location and vector, and sets the center to be (0,0)
	 */
	public SpaceThing() {
		location = new Location();
		vector = new Vector();
		centerX = centerY = 0;
	}

	/**
	 * @param mag 
	 * Accelerate with that magnitude in current direction
	 */
	public void accelerate(double mag) {
		vector.accelerate(mag);
	}

	/**
	 * @param mag 
	 * Accurate on the X-Axis with the current Magnitude
	 */
	public void accelX(double mag) {
		vector.setxSpeed(vector.getxSpeed() + mag);
	}

	/**
	 * @param mag
	 * Accurate on the Y-Axis with the current Magnitude
	 */
	public void accelY(double mag) {
		vector.setySpeed(vector.getySpeed() + mag);
	}

	/**
	 * @return X Local of SpaceThing
	 */
	public double getX() {
		return location.getxLocal();
	}

	/**
	 * @return Y Local of SpaceThing
	 */
	public double getY() {
		return location.getyLocal();
	}
	
	/**
	 * @return X Speed of SpaceThing
	 */
	public double getxSpeed() {
		return vector.getxSpeed();
	}
	
	/**
	 * @return Y Speed of SpaceThing
	 */
	public double getySpeed() {
		return vector.getySpeed();
	}
	
	/**
	 * @return Radians of the SpaceThing
	 */
	public double getRadians() {
		return vector.getRadians();
	}

	/**
	 * @param physList List of everything that can possibly hit things
	 * Will first move the location of the object by the current vector.
	 * Will then check to see if SpaceThing has collided with any other
	 * Space things, and respond appropriately
	 */
	public void physics(ArrayList<SpaceThing> physList) {
		location.move(vector);
//		moveHitBox(vector);
		vector.friction();
		for(SpaceThing thing : physList) {
			if(isColliding(thing) && !thing.equals(this))
				thing.collision(this);
		}
		compile();
	}

	/**
	 * @param g Graphics to draw with
	 * @param originX Origin of Camera
	 * @param originY Origin of Camera
	 * Will draw space thing, grabbing an image from the image lib, which
	 * should have been initialized by the client.
	 */
	public void paint(Graphics g, double originX, double originY) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform xform = g2d.getTransform();
		xform.translate(location.getxLocal() - originX, location.getyLocal()
				- originY);
		xform.rotate(vector.getRadians(), centerX, centerY);

		BufferedImage sprite = ImageLib.getImage(spriteType);
		g2d.drawImage(sprite, xform, null);
		g = g2d;
	}

	/**
	 * Rotates the ships vector
	 * @param mag To Turn
	 */
	public void turn(double mag) {
		vector.turn(mag);
		rotateHitBox(mag);
	}
	
	/**
	 * Rotates the hit box.
	 * @param mag to Rotate
	 */
	protected void rotateHitBox(double mag) {
		int cX = (int) (getX() + centerX);		//Returns the center of object 
		int cY = (int) (getY() + centerY);		//on the map, rather than the relative
												//centerX and centerY
		for(int index = 0; index < hitBox.npoints; index++) {
			int x = hitBox.xpoints[index];
			int y = hitBox.ypoints[index];
			
			hitBox.xpoints[index] = (int) (cX + (x - cX) * Math.cos(mag) - (y - cY) * Math.sin(mag));
			hitBox.ypoints[index] = (int) (cY + (x - cX) * Math.sin(mag) + (y - cY) * Math.cos(mag));
		}
	}
	
	/**
	 * Is SUPPOSED to move the hit box with the ship... doesn't work right now....
	 * @param v
	 */
	protected void moveHitBox(Vector v){
		for (int i = 0; i < hitBox.npoints; i++) {
			hitBox.xpoints[i] = hitBox.xpoints[i] + (int)v.getxSpeed();
			hitBox.ypoints[i] = hitBox.ypoints[i] + (int)v.getySpeed();
		}
	}
	
	/**
	 * @return An Area built out of hitBox
	 */
	public Area buildHull() {
		return new Area(hitBox);
	}
	
	/**
	 * @param thing to check against
	 * @return weather or not the two hitboxes overlap.
	 */
	public boolean isColliding(SpaceThing thing) {
		Area hull = buildHull();
		hull.intersect(thing.buildHull());
		if(!hull.isEmpty()) {
//			compile();
			return true;
		}
		else {
//			compile();
			return false;
		}
	}
	
	/**
	 * CALLED FROM: Collision.
	 * Essentially, when objects collide, they will each move away from each other until
	 * they are no longer colliding. This will move BOTH SpaceThings.
	 * @param thing colliding with
	 */
	public void displace(SpaceThing thing){
		double diffX = getX() - thing.getX();
		double diffY = getY() - thing.getY();
		double xDisplacement = (diffX > 0 ? 1 : -1);
		double yDisplacement = (diffY > 0 ? 1 : -1);
		Vector displacement = new Vector(xDisplacement, yDisplacement);
		location.move(displacement);
		moveHitBox(displacement);
	}
	
	/**
	 * Will rebuild the hitBox for any space thing.
	 */
	public abstract void compile();
	
	/**
	 * Used to handle a collision. Called by physics(), 
	 * it is meant to handle any subclass of Space thing
	 * Appropriately.
	 * @param thing Collided with
	 */
	public abstract void collision(SpaceThing thing);
	
	/**
	 * @return if the SpaceThing has died
	 */
	public abstract boolean isDead();
	
	/**
	 * If any actions need to be preformed beffor SpaceThing
	 * is deleted, die() will handel it.
	 */
	public abstract void die();
}
