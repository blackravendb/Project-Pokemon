package de.pokemon2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener{

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key){
		case KeyEvent.VK_W:
			Core.player.isMoving = true;
			Core.player.up = true;
			break;
		case KeyEvent.VK_S:
			Core.player.isMoving = true;
			Core.player.down = true;
			break;
		case KeyEvent.VK_A:
			Core.player.isMoving = true;
			Core.player.left = true;
			break;
		case KeyEvent.VK_D:
			Core.player.isMoving = true;
			Core.player.right = true;
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key){
		case KeyEvent.VK_W:
			Core.player.isMoving = false;
			Core.player.up = false;
			break;
		case KeyEvent.VK_S:
			Core.player.isMoving = false;
			Core.player.down = false;
			break;
		case KeyEvent.VK_A:
			Core.player.isMoving = false;
			Core.player.left = false;
			break;
		case KeyEvent.VK_D:
			Core.player.isMoving = false;
			Core.player.right = false;
			break;
		}
	}

	public void keyTyped(KeyEvent e) {}

}
