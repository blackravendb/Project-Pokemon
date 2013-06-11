package de.pokemon;

import java.io.IOException;

import org.newdawn.slick.SlickException;

public class Npcs extends Entity {

	Npcs(int posX, int posY, int width, int height, String imagePath, int[][]route)
			throws SlickException {
		super(posX, posY, width, height, imagePath);
	}
	
	Npcs (int posX, int posY, int width, int height, String imagePath, boolean random) throws SlickException{
		super(posX, posY, width, height, imagePath);

	}
	
	public void updateNpc(){
		
	}

}
