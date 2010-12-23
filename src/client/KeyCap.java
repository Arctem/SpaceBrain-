/*KeyCap.java by Rob K
 *Asynchronous key capturing, so repetition doesn't depend on the WIndows key repetition setting
 *So that's why this is good for you, especially for making games
 *Proper use for this class:
 *
 *	setFocusable(true); // Required for keyboard input
 *	KeyCap getKey = new KeyCap(); // Variable needed to get key states
 *	addKeyListener(keyCap); // Add it to your program
 *
 *Stick it in a panel!
 */

package client;

import java.awt.event.*;

public class KeyCap implements KeyListener // Keyboard input
{
	public boolean key_right, key_left, key_up, key_down, key_z, key_x,
			key_space, key_esc, key_shift, key_1, key_2, key_3, key_4, key_5;

	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP)
			key_up = true;
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			key_down = true;
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			key_left = true;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			key_right = true;
		if (e.getKeyCode() == KeyEvent.VK_Z)
			key_z = true;
		if (e.getKeyCode() == KeyEvent.VK_X)
			key_x = true;
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			key_space = true;
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			key_esc = true;
		if (e.getKeyCode() == KeyEvent.VK_SHIFT)
			key_shift = true;
		if (e.getKeyCode() == KeyEvent.VK_1)
			key_1 = true;
		if (e.getKeyCode() == KeyEvent.VK_2)
			key_2 = true;
		if (e.getKeyCode() == KeyEvent.VK_3)
			key_3 = true;
		if (e.getKeyCode() == KeyEvent.VK_4)
			key_4 = true;
		if (e.getKeyCode() == KeyEvent.VK_5)
			key_5 = true;
		// Add your own if needed, these are just basic keys that will probably
		// be useful in any program you're working on

	}

	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP)
			key_up = false;
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			key_down = false;
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			key_left = false;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			key_right = false;
		if (e.getKeyCode() == KeyEvent.VK_Z)
			key_z = false;
		if (e.getKeyCode() == KeyEvent.VK_X)
			key_x = false;
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			key_space = false;
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			key_esc = false;
		if (e.getKeyCode() == KeyEvent.VK_SHIFT)
			key_shift = false;
		if (e.getKeyCode() == KeyEvent.VK_1)
			key_1 = false;
		if (e.getKeyCode() == KeyEvent.VK_2)
			key_2 = false;
		if (e.getKeyCode() == KeyEvent.VK_3)
			key_3 = false;
		if (e.getKeyCode() == KeyEvent.VK_4)
			key_4 = false;
		if (e.getKeyCode() == KeyEvent.VK_5)
			key_5 = false;

	}

	public void keyTyped(KeyEvent e) {
	}

}
