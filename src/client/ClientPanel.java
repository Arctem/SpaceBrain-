package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JPanel;

import world.World;
import world.player.Player;

import comm.Comands;
import comm.CommHandler;

public class ClientPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Camera camera;

	private CommHandler comms;

	private KeyMap keyMap;

	private BufferStrategy buffer;
	private final Color bgColor = Color.black;

	public ClientPanel(BufferStrategy buff) {
		camera = new Camera(null);

		try {
			comms = new CommHandler(new Socket("127.0.0.1", 6661));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			updateWorld();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		keyMap = new KeyMap();

		this.setPreferredSize(new Dimension(1000, 800));
		buffer = buff;
	}

	public void clrScreen(Graphics g) {
		g.setColor(bgColor);
		g.fillRect(getX(), getY(), getWidth(), getHeight() + 50);
	}

	public void drawHUD(Graphics g) {
		g.setColor(Color.white);
		g.drawString("Player coords: " + camera.getPlayer().getX() + ", "
				+ camera.getPlayer().getY(), 10, 40);
		g.drawString("Player rotation: " + camera.getPlayer().getRadians(), 10,
				100);
		g.drawString("Camera coords: " + camera.getX() + ", " + camera.getY(),
				10, 60);
		g.drawString("Player velocity: " + camera.getPlayer().getxSpeed()
				+ ", " + camera.getPlayer().getySpeed(), 10, 80);

		if (camera.getPlayer().getShip().canSwap()
				&& camera.getWorld().canSwap()) {
			g.setColor(Color.red);
			g.drawString("IT'S BRAINSWAPPIN' TIME!", 10, 810);
		}
	}

	public KeyMap getKeyMap() {
		return keyMap;
	}

	public void run() throws InterruptedException {
		while (true) {
			try {
				sendComands();
				updateWorld();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			Graphics g = buffer.getDrawGraphics();

			clrScreen(g);

			camera.paint(g);
			camera.update();
			drawHUD(g);

			if (!buffer.contentsLost())
				buffer.show();
		}
	}

	public void sendComands() throws IOException {
		
		if (keyMap.euclidean) {
			if (keyMap.up)
					comms.write(Comands.E_UP);
			if (keyMap.down)
					comms.write(Comands.E_DOWN);
			if (keyMap.left)
					comms.write(Comands.E_LEFT);
			if (keyMap.right)
					comms.write(Comands.E_RIGHT);
		} else {
			if (keyMap.up)
					comms.write(Comands.V_ACELL);
			if (keyMap.down)
				comms.write(Comands.V_DECELL);
			if (keyMap.left)
					comms.write(Comands.V_CCW);
			if (keyMap.right)
					comms.write(Comands.V_CW);
		}

		if (keyMap.space)
			comms.write(Comands.FIRE);
		if (keyMap.swap)
			comms.write(Comands.BRAINSWAP);
		if (keyMap.escape) // TODO: This should open up some sort of menu
			System.exit(0);
	}

	public void updateWorld() throws IOException, ClassNotFoundException {
		World w = (World) comms.read();
		camera.setWorld(w);
		Player p = (Player) comms.read();
		camera.setPlayer(p);

	}

}
