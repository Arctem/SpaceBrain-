package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import world.World;
import world.elements.libs.GeomLib;
import world.player.Player;

import comm.Comands;


public class Server {

	/**
	 * Execution time in milis
	 */
	private static final long frameCap = 38; 
	/**
	 * Waits for incoming connections
	 */
	private static ServerSocket serverSocket;
	/**
	 * ArrayList of PlayerThreads. Essentially a user list.
	 */
	private static ArrayList<PlayerThread> players;
	/**
	 * Physical world of SpaceBrain!
	 */
	private static World world;
	
	/**
	 * @param args System Arguments (not used)
	 * Main method of Server. First Initializes server, then calls the
	 * run() function.
	 */
	public static void main(String[] args) {

		System.out.println(" ____ ___  ____ ____ ____   ___  ____ ____ _ __ _ /");
		System.out.println(" ==== |--' |--| |___ |===   |==] |--< |--| | | \\|. ");
		System.out.println("       ____ ____ ____ ____ _  _ ____ ____ /");
		System.out.println("      |--- [__] |--< |===  \\/  |=== |--<. ");
		System.out.println("\n             SpaceBrain Server");

//		ServerConsole.startGroup("Init Server");
		init();
//		ServerConsole.endGroup();
//		ServerConsole.startGroup("Starting ServerSocket Scanner");
		startListening();
//		ServerConsole.endGroup();
//		ServerConsole.message("Starting Main loop");
		run();

	}

	/**
	 * CALLED FROM: main()
	 * Initializes all static components of the Server. Uses ServerConsole for regular output
	 * of progress.
	 */
	public static void init() {
//		ServerConsole.startTask("Establishing Connection");
		try {
			serverSocket = new ServerSocket(6661);
		} catch (IOException e) {
//			ServerConsole.message("THE SERVER HAS ENCOUNTERED AN ERROR");
//			ServerConsole.message("Most likely, the server has found an error with this connection...");
//			ServerConsole.message("Ending program now!");
			System.exit(0);
		}
//		ServerConsole.endTask();

//		ServerConsole.startTask("Constructing physical geometry");
		try {
			GeomLib.init();
		} catch (IOException e) {
//			ServerConsole.message("An IO Exception occured constructing Geometry");
//			ServerConsole.message("Ending program now!");
			System.exit(0);
		} catch (ClassNotFoundException e) {
//			ServerConsole.message("A Class Not Found Exception occured constructing Geometry");
//			ServerConsole.message("Ending program now!");
			System.exit(0);
		}
//		ServerConsole.endTask();
//		ServerConsole.startTask("Building Player List");
		players = new ArrayList<PlayerThread>();
//		ServerConsole.endTask();
//		ServerConsole.startTask("Building World");
		world = new World();
//		ServerConsole.endTask();
	}

	/**
	 * CALLED FROM: main()
	 * Opens a thread dedicated to waiting for new Players. Will then promptly add them to
	 * Player list, when lock is acquired.
	 */
	public static void startListening() {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Socket s = serverSocket.accept();
						System.out
								.println("\nNew Player Detected! Thread alocated...");
						System.out.print("Acuiring player lock...");
						synchronized (players) {
							System.out.println("Lock Acquired!");
							players.add(new PlayerThread(s, world.newPlayer()));
							System.out.println("Player Added!");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.print("Starting player thread...");
					players.get(players.size() - 1).start();
					System.out.println("Player thread Started!");
					System.out.println();
				}
			}
		}).start();
	}

	/**
	 * CALLED FROM: main()
	 * Main Server execution loop. Currently offers no way to end.
	 * First starts the fame timers. Then tells all playerThreads to
	 * first send the world, then runComandQueue(). Then calls world.step().
	 * Then waits for timer to run out.
	 */
	public static void run() {
		while (true) {
			long start = System.currentTimeMillis();
			long totalTime;
			synchronized (players) {
				for (PlayerThread p : players)
					try {
						p.sendWorld(world);
						p.runComandQueue();
					} catch (IOException e) {
					}
			}
			world.step();
			totalTime = System.currentTimeMillis() - start;
			if (totalTime < frameCap)
				try {
					Thread.sleep(frameCap - totalTime);
				} catch (InterruptedException e){}
		}
	}
	
	/**
	 * @param p Player using command
	 * @param c Comand being used
	 * CALLED FROM: PlayerThread.runComandQueue()
	 * Tells the world that a player has made a command.
	 */
	public static void command(Player p, Comands c) {
		world.playerCommand(c, p);
	}

	/**
	 * @param p PlayerThread to be closed
	 * CALLED FROM: PlayerThread.run()
	 * Will remove player from players, as well as inform the World to remove that player.
	 */
	public static void endPlayer(PlayerThread p) {
		synchronized (players) {
			players.remove(p);
		}
		world.removePlayer(p.getPlayer());
	}

}