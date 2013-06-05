package de.pokemon;

public class Entity {

	private int posX;
	private int posY;
	private int width;
	private int height;
	private int tileX;
	private int tileY;
	
	Entity (int posX, int posY, int width, int height) throws PlayerNotOnTileException{
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		
		if(tileY % Core.tileSize == 0)
			tileY %= Core.tileSize;
		else
			 throw new PlayerNotOnTileException();
		if(tileX % Core.tileSize == 0)
			tileX %= Core.tileSize;
	}
}

class PlayerNotOnTileException extends Exception {
	PlayerNotOnTileException(){
		super("Player position not on Tile");
	}
}
