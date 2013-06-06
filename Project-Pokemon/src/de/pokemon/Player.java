package de.pokemon;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player extends Entity {
	
	Player(int posX, int posY) throws SlickException {
		super(posX, posY, Core.tileSize, Core.tileSize*2, "res/player2.png", 1, 1);
	}
	
	public void renderPlayer() {
		super.renderEntity();
	}
	
	public void updatePlayer(Input input) {
		super.updateEntity(input);
	}
	
	



}
