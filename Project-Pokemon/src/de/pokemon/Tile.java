package de.pokemon;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Tile {
	private static final int tileSetWidth = 8;
	
	public static int[] blank = {-1, -1};
	
	//background
/*	public static int[] grass = {0,0};
	public static int[] road = {2,11};
	public static int[] leaves = {0,11};
	public static int[] test = {1, 11};		*/
	
	public static int[] TileImageId (int id) {
		int[] ret = {-1, -1};
		//define x position
		ret[0] = id % tileSetWidth - 1;
		
		//define y position
		ret[1] = id / tileSetWidth;
		
		return ret;
	}
	
	//collisions
	
	
	//items
	
	
	//Characters

	
	
	public static int size = 16; //Tilebreite bzw. laenge
	public static BufferedImage terrain, background, items, characters;
	
	public Tile(){
		
		try {
			Tile.background = ImageIO.read(new File("res/bg.png"));
			Tile.terrain = ImageIO.read(new File("res/bg.png"));
		//	Tile.items = ImageIO.read(new File("res/bg.png"));
			Tile.characters = ImageIO.read(new File("res/characters.png"));
		}catch(Exception e){
			System.err.println("Error while import images");
		}
	}
}
