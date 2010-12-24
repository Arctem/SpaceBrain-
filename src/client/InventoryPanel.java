package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class InventoryPanel extends JPanel {
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;
	private Camera camera;
	private int selected;

	public InventoryPanel(Camera camera) {
		super(true);
		this.setPreferredSize(new Dimension(200, 800));
		this.setBackground(Color.BLACK);
		this.camera = camera;
		this.addMouseMotionListener(new movement());
		selected = -1;
	}

	public void paint(Graphics g) {
		clrScreen(g);
		drawHUD(g);
	}

	private void drawHUD(Graphics g) {
		g.setColor(Color.GREEN);
		g.drawString(":-SpaceBrain Controll Panel-:", 10, 20);
		g.drawString("Cordinates: " + (int) camera.getPlayer().getX() + ", "
				+ (int) camera.getPlayer().getY(), 10, 80);
		g.drawString("Velocity: " + (int) camera.getPlayer().getxSpeed() + ", "
				+ (int) camera.getPlayer().getySpeed(), 10, 100);
		g.drawString("Baring: " + camera.getPlayer().getRadians(), 10, 120);

		g.drawString("--  --  :-Brain Pod -:  --  --", 10, 160);
		g.drawString("Weapon:", 20, 180);
		g.drawString(camera.getPlayer().getShip().getInventory().getWeapon()
				.getFirepattern().toString(), 80, 180);
		g.drawString("Speed:", 20, 200);
		g.fillRect(80, 190, (int) (camera.getPlayer().getShip().getInventory()
				.getSpeed() * 30), 10);
		g.drawString("Sheilds:", 20, 220);
		g.fillRect(80, 210, (int) (camera.getPlayer().getShip().getInventory()
				.getShields() * 30), 10);

		for (int i = 0; i < 5; i++) {
			g.drawRect(40, 230 + i * 60, 50, 50);
			g.drawRect(100, 230 + i * 60, 50, 50);
		}
		
		if(selected > -1 && selected < 10){
			g.setColor(Color.RED);
			g.drawRect((selected % 2 == 0 ? 40 : 100), 230+(selected/2)*60, 50,50);
			g.setColor(Color.GREEN);
		}
		
		for (int i = 0; i < camera.getPlayer().getShip().getInventory()
				.getItemList().size(); i++) {
			g.setFont(new Font("Synchro LET", Font.PLAIN, 10));
			g.drawImage(camera.getPlayer().getShip().getInventory()
					.getItemList().get(i).getSprite(), (i % 2 == 0 ? 53 : 113),
					240 + (i / 2) * 60, 25, 25, null);
			g.drawString(camera.getPlayer().getShip().getInventory()
					.getItemList().get(i).getType().toString(),
					(i % 2 == 0 ? 50 : 110), 240 + (i / 2) * 60);
			// TODO: Strings should decide how far over they should be drawn,
			// for centering... I just don't care enough right now....
			g.fillRect((i % 2 == 0 ? 45 : 105), 230 + 60 * (i / 2) + 40,
					(int) camera.getPlayer().getShip().getInventory()
							.getItemList().get(i).getPower() * 5, 5);
		}
	}

	private void clrScreen(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 200, 800);
	}

	private class movement implements MouseMotionListener{
		@Override
		public void mouseDragged(MouseEvent arg0) {}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			selected = ((e.getY() - 230) / 60)*2;
			if(e.getX() > 100)
				selected ++;
			System.out.println(selected);
		}
	}
	
	private class clicker implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println(selected);
		}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
	}
}