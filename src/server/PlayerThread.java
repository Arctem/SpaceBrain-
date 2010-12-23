package server;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import world.player.Player;
import world.World;

import comm.Comands;
import comm.CommHandler;

/**
 * @author Izzy Cecil
 * Dec 14, 2010
 * PlayerThread acts as a command stack for all players. When server accepts a new
 * socket, that socket is used to make a player thread. The PlayerThread will then
 * bind a socket and player object. It will then run as an independent thread in 
 * the server. Run will read all commands coming in, building up the stack. The 
 * server may call runComandQueue(), which will then empty that stack.
 */
public class PlayerThread extends Thread {

	/**
	 * Communication package
	 */
	private CommHandler comms;
	/**
	 * Player object
	 */
	private Player player;
	/**
	 * Stack of commands to be executed when runComandQueue() is called.
	 */
	private ArrayList<Comands> comandQueue;

	/**
	 * @param socket New Accepted Socket to be bound to player
	 * @param player New Player to be bound to socket
	 * @throws IOException In the case of comunication error.
	 * Builds new PlayerThread object.
	 */
	public PlayerThread(Socket socket, Player player) throws IOException {
		comms = new CommHandler(socket);
		this.player = player;
		comandQueue = new ArrayList<Comands>();
	}
	
	/**
	 * @see java.lang.Thread#run()
	 * Main read loop. Waits for comand to be sent, then
	 * adds it to the Stack.
	 * 
	 * TODO: Currently has now "End" Condition. This should be
	 * fixed, to have an orderly way to dispose of PlayerThreads.
	 */
	public void run() {
		while (true) {
			Comands comand = null;
			try {
				comand = (Comands) comms.read();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (EOFException e) {
				emergancyDisconnect();		//Called if the Socket is closed.
				break;
			} catch (SocketException e) {
				emergancyDisconnect();		//Called if the Socket has... something strange happen to it
				break;
			} catch (IOException e) {
				e.printStackTrace();		//General Error. Should never be called.
			}
			synchronized (comandQueue) {	//Synchronized to avoid adding while emptying the stack.
				comandQueue.add(comand);
			}
		}
		try {
			comms.close();
		} catch (IOException e) {}
		Server.endPlayer(this);
	}
	
	/**
	 * CALLED BY: Server.run()
	 * Empties the command Stack
	 */
	public void runComandQueue(){
		synchronized (comandQueue) {		//Synchronized to avoid adding while emptying the stack
			for (Comands c : comandQueue) {
				Server.command(player, c);
			}
			comandQueue.clear();
		}
	}

	/**
	 * @param world Update version of the World.
	 * @throws IOException In case of STRANGE communication error
	 * Sends first the world, then the Player to the Client. The client listend for these
	 * two packages in that order.
	 * The updated world will inform the client where all object are physically in the world, and there
	 * Statistics. The Player will tell the client any changes to health, position, or any new features
	 * added to the player class.
	 */
	public void sendWorld(World world) throws IOException {
		comms.write(world);
		comms.write(player);
	}
	
	/**
	 * Will occur upon improper disconnect. Attempts to close communications before
	 * tread is terminated.
	 */
	public void emergancyDisconnect() {
		try {
			comms.close();
		} catch (IOException e1) {}
	}

	/**
	 * @return Player bound to this thread
	 */
	public Player getPlayer() {
		return player;
	}






}