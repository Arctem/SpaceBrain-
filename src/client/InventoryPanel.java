package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;

public class InventoryPanel extends Panel {
	/**
	 * Searial ID
	 */
	private static final long serialVersionUID = 1L;

	public InventoryPanel() {
		super();
		this.setPreferredSize(new Dimension(400,400));
		this.setBackground(Color.CYAN);
	}

}
