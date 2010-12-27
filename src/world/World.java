package world;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

import world.elements.Bullet;
import world.elements.Item;
import world.elements.ItemType;
import world.elements.SpaceThing;
import world.elements.Ship.Ship;
import world.elements.physics.Location;
import world.player.Player;

import comm.Comands;

/**
 * @author Izzy Cecil
 * Dec 15, 2010
 * List of all physical objects in the space brain world. A server
 * will have a copy of a World object, which will keep track of all
 * physics on the map. It will then be sent to clients, for drawing.
 */
/**
 * @author Izzy Cecil
 * Dec 15, 2010
 *
 */
/**
 * @author Izzy Cecil
 * Dec 15, 2010
 *
 */
public class World implements Serializable {

	/**
	 * Serial Version ID for Serialization of World objects
	 */
	private static final long serialVersionUID = 1;
	/**
	 * List of all ships in the World
	 */
	private ArrayList<Ship> shipList;
	/**
	 * List of all active Players in the World
	 */
	private ArrayList<Player> players;
	/**
	 * List of all bullets in the World
	 */
	private ArrayList<Bullet> fireList;
	
	/**
	 * List of all other space things in the vers.
	 */
	private ArrayList<SpaceThing> otherList;

	
	/**
	 * Builds a shipList, player List, and fireList. Will also call 
	 * makeDebugWorld()
	 */
	public World() {
		shipList = new ArrayList<Ship>();
		players = new ArrayList<Player>();
		fireList = new ArrayList<Bullet>();
		otherList = new ArrayList<SpaceThing>();
		makeDebugWorld();
	}

	/**
	 * Builds four ships into the world. Used for quick world population
	 */
	private void makeDebugWorld() {
		shipList.add(new Ship(new Location(500, 600)));
		shipList.add(new Ship(new Location(800, 200)));
		shipList.add(new Ship(new Location(300, 400)));
		shipList.add(new Ship(new Location(500, 2000)));
		otherList.add(new Item(new Location(400,400),ItemType.SPEED,1));
		otherList.add(new Item(new Location(450, 400), ItemType.SPEED, 5));
		otherList.add(new Item(new Location(500, 400), ItemType.SPEED, 3));
		otherList.add(new Item(new Location(450, 450), ItemType.SHEILDS, 3));
	}
	
	/**
	 * Steps through physics with ALL items. Hopefully collisions will only be counted once
	 * But double collisions most likely wont hurt.
	 */
	public void step() {
		// Keep in mind that eventually, we won't be checking for collisions
		// with everything.
		ArrayList<SpaceThing> everything = compileList();
		for (SpaceThing thing : everything) {
			thing.physics(everything);
			if (thing.isDead()) {
				thing.die();
				remove(thing);
			}
		}

	}

	/**
	 * @param c Command to be Executed
	 * @param p Player using Command
	 */
	public void playerCommand(Comands c, Player p) {
		switch (c) {
		case E_UP:
			p.getShip().accelY(-1.0*p.getShip().getThrusters());
			break;
		case E_DOWN:
			p.getShip().accelY(p.getShip().getThrusters());
			break;
		case E_LEFT:
			p.getShip().accelX(-1.0*p.getShip().getThrusters());
			break;
		case E_RIGHT:
			p.getShip().accelX(p.getShip().getThrusters());
			break;
		case V_ACELL:
			p.getShip().accelerate(-1.0*p.getShip().getThrusters());
			break;
		case V_DECELL:
			p.getShip().accelerate(p.getShip().getThrusters());
			break;
		case V_CCW:
			p.getShip().turn(-.1);
			break;
		case V_CW:
			p.getShip().turn(.1);
			break;
		case FIRE:
			p.getShip().fire(fireList);
			break;
		case BRAINSWAP:
			if (p.getShip().brainSwap())
				brainSwap();
			break;			
		default:
			System.out.println("Bake sale! Unrecognized command "
					+ c.toString());
		}
	}
	/**
	 * @param g Graphics to draw with
	 * @param originX of the Camera
	 * @param originY of the Camera
	 */
	public void paint(Graphics g, double originX, double originY) {
		// Later on, we may not want to be drawing everything on the map.
		ArrayList<SpaceThing> everything = compileList();
		for (SpaceThing thing : everything)
			thing.paint(g, originX, originY);
	}
	
	/**
	 * Builds a ship, and places it in the world at the default local.
	 * Then builds a player, using that ship. Both are added into the world
	 * list.
	 * @return The new Player
	 */
	public Player newPlayer() {
		Ship s = new Ship(new Location());
		Player p = new Player(s);

		players.add(p);
		shipList.add(s);
		return p;
	}
	/**
	 * @param p
	 * @return If the player is currently in the world
	 */
	public boolean playerExist(Player p) {
		return players.contains(p);
	}
	/**
	 * @return A list of ALL SpaceThings in the World
	 */
	private ArrayList<SpaceThing> compileList() {
		ArrayList<SpaceThing> everything = new ArrayList<SpaceThing>();
		everything.addAll(shipList);
		everything.addAll(fireList);
		everything.addAll(otherList);
		return everything;
	}
	/**
	 * @param thing SpaceThing to add to world
	 * Will add any Space thing into the appropriate ArrayList
	 */
	public void add(SpaceThing thing) {
		if (thing instanceof Ship)
			shipList.add((Ship) thing);
		if (thing instanceof Bullet)
			fireList.add((Bullet) thing);
	}
	/**
	 * @param index Of Player
	 * @return Player with given index number.
	 */
	public Player getPlayer(int index) {
		return players.get(index);
	}

	/**
	 * @param index index of Ship
	 * @return Ship with given Index
	 */
	public Ship getShip(int index) {
		return shipList.get(index);
	}
	/**
	 * @param thing To be Removed
	 * Will remove any SpaceThing from the world
	 */
	public void remove(SpaceThing thing) {
		if (thing instanceof Ship)
			shipList.remove((Ship) thing);
		else if (thing instanceof Bullet)
			fireList.remove((Bullet) thing);
		else
			otherList.remove(thing);
	}

	/**
	 * @param player To be Removed
	 * Will remove any player in the world.
	 */
	public void removePlayer(int player) {
		players.remove(player);
	}

	/**
	 * @param player to be removed
	 */
	public void removePlayer(Player player) {
		shipList.remove(player);
	}

	/**
	 * @param index of Ship to be removed
	 */
	public void removeShip(int index) {
		shipList.remove(index);
	}

	/**
	 * @param ship to be removed
	 */
	public void removeShip(Ship ship) {
		shipList.remove(ship);
	}
	private void brainSwap() {

	}
	
	public boolean canSwap(){return false;}

	@Override
	public String toString() {
		return "World [shipList=" + shipList.get(shipList.size() - 1).getX()
				+ "  " + shipList.get(shipList.size() - 1).getY();
	}

}
