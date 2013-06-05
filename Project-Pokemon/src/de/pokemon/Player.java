package de.pokemon;

import org.newdawn.slick.SlickException;

public class Player extends Entity {
	
	Player(int posX, int posY, int width, int height, String imagePath) throws SlickException {
		super(posX, posY, width, height, imagePath);
	}
	
	public void renderPlayer() {
		super.renderEntity();
	}



}
