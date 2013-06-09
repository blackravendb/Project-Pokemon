package de.pokemon;

import java.io.IOException;

import org.newdawn.slick.SlickException;

public class Npcs extends Entity {

	Npcs(int posX, int posY, int width, int height, String imagePath, int[][]route)
			throws SlickException, IOException {
		super(posX, posY, width, height, imagePath, 1, 1);
	}
	
	public void updateNpc(){
		
	}

}
