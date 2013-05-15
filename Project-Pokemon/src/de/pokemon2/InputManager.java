package de.pokemon2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener{

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key){
		case KeyEvent.VK_W:
		//	Core.player.isMoving = true;
			if(!Core.player.isMoving){
				Core.bW = true;
			}
			break;
		case KeyEvent.VK_S:
		//	Core.player.isMoving = true;
			if(!Core.player.isMoving){
				Core.bS = true;
			}
			break;
		case KeyEvent.VK_A:
		//	Core.player.isMoving = true;
			if(!Core.player.isMoving){
				Core.bA = true;
			}
			break;
		case KeyEvent.VK_D:
		//	Core.player.isMoving = true;
			if(!Core.player.isMoving){
				Core.bD = true;
			}
			break;
		}
	}

	public void keyReleased(KeyEvent e) { /*
		int key = e.getKeyCode();
		
		switch(key){
		case KeyEvent.VK_W:
			Core.player.isMoving = false;
			Core.bW = false;
			break;
		case KeyEvent.VK_S:
			Core.player.isMoving = false;
			Core.bS = false;
			break;
		case KeyEvent.VK_A:
			Core.player.isMoving = false;
			Core.bA = false;
			break;
		case KeyEvent.VK_D:
			Core.player.isMoving = false;
			Core.bD = false;
			break;
		}
	*/}

	public void keyTyped(KeyEvent e) {}

}
