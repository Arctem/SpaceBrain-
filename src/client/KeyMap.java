package client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyMap implements KeyListener {

	public boolean euclidean, up, down, left, right, swap, space, escape;

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SHIFT:
			euclidean = true;
			break;
		case KeyEvent.VK_W:
			up = true;
			break;
		case KeyEvent.VK_S:
			down = true;
			break;
		case KeyEvent.VK_A:
			left = true;
			break;
		case KeyEvent.VK_D:
			right = true;
			break;
		case KeyEvent.VK_SPACE:
			space = true;
			break;
		case KeyEvent.VK_Q:
			swap = true;
			break;
		case KeyEvent.VK_ESCAPE:
			escape = true;
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SHIFT:
			euclidean = false;
			break;
		case KeyEvent.VK_W:
			up = false;
			break;
		case KeyEvent.VK_S:
			down = false;
			break;
		case KeyEvent.VK_A:
			left = false;
			break;
		case KeyEvent.VK_D:
			right = false;
			break;
		case KeyEvent.VK_SPACE:
			space = false;
			break;
		case KeyEvent.VK_Q:
			swap = false;
			break;
		case KeyEvent.VK_ESCAPE:
			escape = false;
			break;
		}
	}

	public void keyTyped(KeyEvent e) {

	}

}
