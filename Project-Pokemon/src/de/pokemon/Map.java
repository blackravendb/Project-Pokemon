package de.pokemon;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Map extends TiledMap{

	private TiledMap currentMap;
	private boolean[][] blocked;
	private int tileSize;

	/**
	 * Creates new tile map and builds up a collision map based on a given tmx file.
	 * @param ref Path to .tmx file
	 * @throws SlickException
	 */
	public Map(String ref) throws SlickException{
		super(ref);
		setCurrentMap(this);
		blocked = new boolean[this.getWidth()][this.getHeight()];
		blocked = this.buildCollisionMap();
		tileSize = this.getTileWidth();

	}

	/**
	 * Builds a 2 dimensional collision-map from the specified layer name for the current
	 * map.
	 *
	 * @param layerName
	 * @return array of booleans, true if blocked
	 */
	private boolean[][] buildCollisionMap() {

		int layerIndex = this.getLayerIndex("collision");

		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				int tileID = this.getTileId(x, y, layerIndex);
				blocked[x][y] = this.getTileProperty(tileID, "blocked", "false") == "blocked" ? true : false;
			}
		}

		return blocked;
	}

	/**Checks if the tile specified by the x and y coordinate has the property "blocked" on the current map.
	 *
	 * @param x coordinate
	 * @param y coordinate
	 * @return True, if the tile specified by the coordinates is blocked
	 */
	public boolean isBlocked(float x, float y){
		int xBlock = (int)x / tileSize;	
		int yBlock = (int)y / tileSize;
		return blocked[xBlock][yBlock];
	}

	public TiledMap getCurrentMap() {
		return currentMap;
	}

	public void setCurrentMap(TiledMap currentMap) {
		this.currentMap = currentMap;
	}

	public Map loadMap(String ref) throws SlickException{
		return new Map(ref);
	}

}
