package world.player;

import java.io.Serializable;

import world.elements.Ship.Ship;

public class Player implements Serializable {

	private static final long serialVersionUID = 1;
	private long playerUID;
	private Ship ship;
	private boolean alive;

	public Player(Ship sh) {
		ship = sh;
		playerUID = (long) (Math.random() * Long.MAX_VALUE);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (playerUID != other.playerUID)
			return false;
		return true;
	}

	public Ship getShip() {
		return ship;
	}
	
	public void setShip(Ship sh) {
		ship = sh;
	}

	public double getX() {
		return ship.getX();
	}

	public double getY() {
		return ship.getY();
	}
	
	public double getxSpeed() {
		return ship.getxSpeed();
	}

	public double getySpeed() {
		return ship.getySpeed();
	}
	
	public double getRadians() {
		return ship.getRadians();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (playerUID ^ (playerUID >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "Player [playerUID=" + playerUID + "X = " + getX() + " Y="
				+ getY() + "]";
	}

}
