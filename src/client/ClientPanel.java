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

	private final Color bgColor = Color.black;
	private Camera camera;

	public ClientPanel(Camera camera) {
		super(true);
		this.setPreferredSize(new Dimension(1000, 800));
		this.setBackground(bgColor);
		this.camera = camera;
	}
	
	public void paint(Graphics g){
		clrScreen(g);
		drawWorld(g);
//		drawHUD(g);
	}
	
	public void clrScreen(Graphics g) {
		g.setColor(bgColor);
		g.fillRect(0,0,1000,800);
	}
	
	public void drawWorld(Graphics g){
		camera.paint(g);
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
}
