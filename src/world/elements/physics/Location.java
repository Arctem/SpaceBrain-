package world.elements.physics;

import java.io.Serializable;

public class Location implements Serializable {
	private static final long serialVersionUID = 1;

	private double xLocal;
	private double yLocal;

	public Location() {
		xLocal = 200;
		yLocal = 200;
	}

	public Location(double x, double y) {
		xLocal = x;
		yLocal = y;
	}

	public double getxLocal() {
		return xLocal;
	}

	public double getyLocal() {
		return yLocal;
	}

	public void move(Vector vector) {
		xLocal += vector.getxSpeed();
		yLocal += vector.getySpeed();
//		vector.friction();	//FRICTION SHOULD BE CALLED IN A PHYSICS METHOD, NOT HERE!
	}

	public void setxLocal(double xLocal) {
		this.xLocal = xLocal;
	}

	public void setyLocal(double yLocal) {
		this.yLocal = yLocal;
	}
	
	public void translate(double dX, double dY) {
		xLocal += dX;
		yLocal += dY;
	}
}
