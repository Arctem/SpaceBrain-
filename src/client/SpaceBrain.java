package client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;

import world.World;
import world.elements.libs.ImageLib;
import world.player.Player;

import comm.Comands;
import comm.CommHandler;

public class SpaceBrain extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Camera camera;
	private CommHandler comms;
	private KeyMap keyMap;
	private BufferStrategy buffer;
	private ClientPanel cp;
	private InventoryPanel ip;
	private Color bgColor = Color.green;

	public static void main(String[] args) {
		ImageLib.init();
		SpaceBrain space = new SpaceBrain();
		try {
			space.run();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public SpaceBrain() {
		super();
		this.setBackground(bgColor);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.createBufferStrategy(2);
		keyMap = new KeyMap();
		
		camera = new Camera();
		buffer = this.getBufferStrategy();
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
		
		cp = new ClientPanel(camera);
		ip = new InventoryPanel(camera);
		this.setLayout(new FlowLayout());
		this.add(cp);
		this.add(ip);
		this.pack();
//		this.setSize(1000,800);
		this.setSize(1210,800);
		this.addKeyListener(keyMap);
		

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
			camera.update();
			cp.repaint();
			ip.repaint();
		}
	}
	
	public void updateWorld() throws IOException, ClassNotFoundException {
		World w = (World) comms.read();
		camera.setWorld(w);
		Player p = (Player) comms.read();
		camera.setPlayer(p);

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
}
