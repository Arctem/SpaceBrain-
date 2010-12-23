package world.elements.physics;

import java.io.Serializable;

public class Vector implements Serializable {
	private static final long serialVersionUID = 1;

	private double xSpeed;
	private double ySpeed;
	private double radians;
	//public static final double friction = .3;
	public double friction;

	public Vector() {
		xSpeed = 0;
		ySpeed = 0;
		radians = 0;
		friction = .95;
	}
	
	public Vector(double xSpeed, double ySpeed){
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		radians = 0;
		friction = .95;
	}

	public void accelerate(double mag) {
		xSpeed += mag * Math.sin(-radians);
		ySpeed += mag * Math.cos(-radians);
	}

	public void friction() {
		//This method of application of friction seems to be causing some subtle glitches
		//I'm going to comment it out for the time being, and write a simplified version from scratch
		/*xSpeed -= (Math.abs(xSpeed) > .1 ? friction
				* Math.sin(Math.atan(xSpeed / ySpeed)
						+ (ySpeed > 0 ? 0 : Math.PI)) : (0));
		ySpeed -= (Math.abs(ySpeed) > .1 ? friction
				* Math.cos(Math.atan(xSpeed / ySpeed)
						+ (ySpeed > 0 ? 0 : Math.PI)) : (0));*/
		
		xSpeed *= (Math.abs(xSpeed) > 0.2 ? friction : 0.0);
		
		ySpeed *= (Math.abs(ySpeed) > 0.2 ? friction : 0.0);
	}

	public double getRadians() {
		return radians;
	}

	public double getxSpeed() {
		return xSpeed;
	}

	public double getySpeed() {
		return ySpeed;
	}

	public void setRadians(double radians) {
		this.radians = radians;
	}

	public void setxSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}

	public void setySpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}

	public void turn(double amount) {
		radians += amount;
		radians %= Math.PI * 2;
	}
}
