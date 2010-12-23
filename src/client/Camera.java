package client;

import java.awt.Graphics;

import world.World;
import world.player.Player;

/**
 * @author IzzyC This class provides a view of the SpaceBrain! world. It is
 *         designed to contain a player and a world. When the client requests,
 *         the camera will render the world into an image, and place it relative
 *         to the location of the player, that way, the camera will follow the
 *         players ship.
 */
public class Camera {
	/**
	 * X location of the camera (updates based on the location of the player)
	 */
	private int x;
	/**
	 * Y location of the camera (updates based on the location of the player)
	 */
	private int y;
	/**
	 * The Player this camera is following. Provided every refresh cycle by the
	 * server.
	 */
	private Player player;
	/**
	 * World this camera is looking at. Provided every refresh cycle by the
	 * server.
	 */
	private World world;
	/**
	 * The amount of pixels to the edge the player needs to approach, before the
	 * camera will move
	 */
	private static final int grabBox = 200;

	/**
	 * Constructs a new Camera, with a null world and player
	 */
	public Camera() {
		world = null;
		player = null;
		x = 0;
		y = 0;
	}

	/**
	 * @param world
	 *            Constructs a new Camera, with the given world
	 */
	public Camera(World world) {
		this.world = world;
		x = 0;
		y = 0;
	}

	/**
	 * @return player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return world
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * @return x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param g
	 *            Calls world.paint(), providing the graphics, x, and y
	 */
	public void paint(Graphics g) {
		world.paint(g, x, y);
	}

	public void setPlayer(Player p) {
		this.player = p;
	}

	public void setWorld(World w) {
		this.world = w;
//		System.out.println("World set as "+world);
//		System.out.println("Recieved world was " +w);
	}

	/**
	 * Moves the camera if needed, judging by the players proximity to the
	 * grabBox
	 */
	public void update() {
		if (player.getX() < (x + grabBox))				//If the player is too close to the right
			x -= (x + grabBox) - player.getX();
		else if (player.getX() > (x + 1000 - grabBox))	//If the player is too close to the left
			x += (player.getX() - (x + 1000 - grabBox));

		if (player.getY() < (y + grabBox))				//If the player is too close to the top
			y -= (y + grabBox) - player.getY();
		else if (player.getY() > (y + 800 - grabBox))	//If the player is too close to the bottom
			y += (player.getY() - (y + 800 - grabBox));

	}
}
