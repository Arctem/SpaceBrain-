package world.elements.Ship;

import java.io.Serializable;
import java.util.ArrayList;

import world.elements.Bullet;
import world.elements.Item;
import world.elements.Weapon;
import world.elements.physics.Location;
import world.elements.physics.Vector;

public class Inventory implements Serializable {
	/**
	 * Serial Version ID for Serialization of World objects
	 */
	private static final long serialVersionUID = 1;
	private ArrayList<Item> itemList;
	private Weapon weapon; // TODO: make a weapon class.
	private double shields, speed;

	public Inventory() {
		shields = 1;
		speed = .5;
		itemList = new ArrayList<Item>();
		weapon = new Weapon();
	}

	public void addItem(Item item) {
		if (itemList.size() < 10) {
			switch (item.getType()) {
			case SHEILDS:
				shields += item.getPower();
				break;
			case SPEED:
				speed += item.getPower();
				break;
			default:
				break;
			}
			itemList.add(item);
		}
	}

	public void pickupWeapon(Weapon w) {
		weapon = w;
	}

	public void fire(ArrayList<Bullet> fireList, Location l, Vector v) {
		weapon.fire(fireList, l, v);
	}

	public void reload() {
		weapon.reload();
	}

	public ArrayList<Item> getItemList() {
		return itemList;
	}

	public Item dropItem(int i) {
		Item item = itemList.get(i);
		switch (item.getType()) {
		case SHEILDS:
			shields -= item.getPower();
			break;
		case SPEED:
			speed -= item.getPower();
			break;
		default:
			break;
		}
		return itemList.remove(i);
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public double getShields() {
		return shields;
	}

	public double getSpeed() {
		return speed;
	}
}