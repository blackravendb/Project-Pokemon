package de.pokemon2;

import java.awt.Graphics;
import java.awt.Rectangle;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Level {
	
	public int width = 50, height = 50;
	
	public Background[][] bg = new Background [width][height];
	public Solid[][] solid = new Solid [width][height];
	public Item [][] items = new Item [width][height];
	
	public final String DPath = "res/world/Level_";
	public String path = DPath;
	
	public TiledMap map = null;
	
	public Level(int id) {
		path = DPath + Integer.toString(id) + ".tmx";
		System.out.println(path);
		
		try {
			map = new TiledMap(path,false);
		} catch (SlickException e){
			System.err.println("Error while loading map");
		}
		
		for(int x = 0; x < bg.length; x++) {
			for (int y = 0; y < bg[0].length; y++) {
				bg[x][y] = new Background(new Rectangle(x * Tile.size, y * Tile.size, Tile.size, Tile.size), Tile.blank);
				solid[x][y] = new Solid(new Rectangle(x * Tile.size, y * Tile.size, Tile.size, Tile.size), Tile.blank);
				items[x][y] = new Item(new Rectangle(x * Tile.size, y * Tile.size, Tile.size, Tile.size), Tile.blank);
				
				
				//Mehr Layers hier
			}
		}
		loadWorld();
	}
	
	
	public void loadWorld() {
		int background = map.getLayerIndex("background");
		int solids = map.getLayerIndex("collision");
		int item = map.getLayerIndex("object");
				
		//Weitere Layers
		
		for(int x = 0; x < bg.length; x++) {
			for (int y = 0; y < bg[0].length; y++) {
				//background
				bg[x][y].id = Tile.TileImageId(map.getTileId(x, y, background));
				
				//solid
				solid[x][y].id = Tile.TileImageId(map.getTileId(x, y, solids));
				
				//items
				items[x][y].id = Tile.TileImageId(map.getTileId(x, y, item));
				
			/*	if(map.getTileId(x, y, background) == 1){
					bg[x][y].id = Tile.grass;					
				}
				else if(map.getTileId(x, y, background) == 89){
					bg[x][y].id = Tile.leaves;					
				}
				else if(map.getTileId(x, y, background) == 91){
					bg[x][y].id = Tile.road;					
				}
				else if(map.getTileId(x, y, background) == 90){
					bg[x][y].id = Tile.test;					
				}	*/
				
				//solids
				
				//items
			}
		}
	}
	
	public void tick(double delta) {
		
	}
	
	public void render(Graphics g, int camX, int camY, int renX, int renY) {
		for(int x = (camX / Tile.size); x < (camX / Tile.size) + renX; x++) {
			for (int y = (camY / Tile.size); y < (camY / Tile.size) + renY; y++){
				if(x >= 0 && y >= 0 && x < width && y < height) {
					bg[x][y].render(g);
					solid[x][y].render(g);
				}
			}
		}
	}

}
