package client;

import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import world.elements.libs.ImageLib;

public class SpaceBrain extends JFrame {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		ImageLib.init();
		SpaceBrain space = new SpaceBrain();
		try {
			space.getCf().run();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private BufferStrategy buffer;
	private ClientPanel cf;

	public SpaceBrain() {
		super();
		this.setResizable(false);
		this.setVisible(true);

		this.createBufferStrategy(2);

		buffer = this.getBufferStrategy();

		cf = new ClientPanel(buffer);
		this.add(cf);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.addKeyListener(cf.getKeyMap());
	}

	public ClientPanel getCf() {
		return cf;
	}

	public void setCf(ClientPanel cf) {
		this.cf = cf;
	}
}
