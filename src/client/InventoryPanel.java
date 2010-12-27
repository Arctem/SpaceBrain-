package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JPanel;

import comm.Comands;
import comm.CommHandler;

public class InventoryPanel extends JPanel {
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;
	private Camera camera;
	private int selected;
	private CommHandler comms;

	public InventoryPanel(Camera camera, CommHandler comms) {
		super(true);
		this.setPreferredSize(new Dimension(200, 800));
		this.setBackground(Color.BLACK);
		this.camera = camera;
		this.comms = comms;
		this.addMouseMotionListener(new movement());
		this.addMouseListener(new clicker());
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
			
			if (e.getX() > 39 && e.getX() < 151 && e.getY() > 230 && e.getY() < 530) {
				selected = ((e.getY() - 230) / 60) * 2;
				if (e.getX() > 100)
					selected++;
			}
			else
				selected = -1;
		}
	}
	
	private class clicker implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			//DO you detected the amount of stupid that is in this code?
			//I need to change the way comands work, but havn't dessided what rout to take
			//I can live with this for now, but If I ever want to have locked orbitals, We 
			//are going to have to be able to accept different types of command objects...
			Comands comand = null;
			switch(selected){
			case 0: comand = Comands.DROP_ITEM_0; break;
			case 1: comand = Comands.DROP_ITEM_1; break;
			case 2: comand = Comands.DROP_ITEM_2; break;
			case 3: comand = Comands.DROP_ITEM_3; break;
			case 4: comand = Comands.DROP_ITEM_4; break;
			case 5: comand = Comands.DROP_ITEM_5; break;
			case 6: comand = Comands.DROP_ITEM_6; break;
			case 7: comand = Comands.DROP_ITEM_7; break;
			case 8: comand = Comands.DROP_ITEM_8; break;
			case 9: comand = Comands.DROP_ITEM_9; break;
			default: return;
			}
			try{
				comms.write(comand);
			}
			catch(IOException err){
				System.out.println(err);
			}
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