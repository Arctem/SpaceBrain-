package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import world.elements.Item;

public class InventoryPanel extends JPanel {
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;
	private Camera camera;

	public InventoryPanel(Camera camera) {
		super(true);
		this.setPreferredSize(new Dimension(200,800));
		this.setBackground(Color.BLACK);
		this.camera = camera;
	}
	
	public void paint(Graphics g){
		clrScreen(g);
		drawHUD(g);
	}
	
	private void drawHUD(Graphics g) {
		g.setColor(Color.GREEN);
		g.drawString(":-SpaceBrain Controll Panel-:", 10,20);
		g.drawString("Cordinates: " + (int)camera.getPlayer().getX() + ", "
				+ (int)camera.getPlayer().getY(), 10, 80);
		g.drawString("Velocity: " + (int)camera.getPlayer().getxSpeed()
				+ ", " + (int)camera.getPlayer().getySpeed(), 10, 100);
		g.drawString("Baring: " + camera.getPlayer().getRadians(), 10,
				120);
		
		g.drawString("--  --  :-Brain Pod -:  --  --", 10, 160);
		g.drawString("Weapon:", 20, 180);
		g.drawString(camera.getPlayer().getShip().getInventory().getWeapon().getFirepattern().toString(),80, 180);
		g.drawString("Speed:", 20, 200);
		g.fillRect(80, 190, (int)(camera.getPlayer().getShip().getInventory().getSpeed()*30), 10);
		g.drawString("Sheilds:", 20, 220);
		g.fillRect(80, 210, (int)(camera.getPlayer().getShip().getInventory().getShields()*30), 10);
		
		for(int i=0; i<5; i++){
			g.drawRect(40, 230+i*60, 50, 50);
			g.drawRect(100, 230+i*60, 50, 50);
		}
		for(int i=0; i<camera.getPlayer().getShip().getInventory().getItemList().size();i++){
			g.drawImage(camera.getPlayer().getShip().getInventory().getItemList().get(i).getSprite(), (i%2 == 0 ? 40:100),230+(i/2)*60,30,30,null);
		}
	}
	
	
	private void clrScreen(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0,0,200,800);
	}
}
