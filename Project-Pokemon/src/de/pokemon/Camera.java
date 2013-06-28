package de.pokemon;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Camera {

	/** the map used for our scene */
	protected Map map;

	/** the amount of tiles in x-direction (width) */
	private int numTilesX;

	/** the amount of tiles in y-direction (height) */
	private int numTilesY;

	/** the height of the map in pixel */
	private int mapHeight;

	/** the width of the map in pixel */
	private int mapWidth;

	/** the width of one tile of the map in pixel */
	private int tileWidth;

	/** the height of one tile of the map in pixel */
	private int tileHeight;

	/** the GameContainer, used for getting the size of the game canvas */
	private GameContainer gc;

	/** the x-position of the camera in pixel */
	public static float cameraX;

	/** the y-position of the camera in pixel */
	public static float cameraY;

	/**
	 * Create a new camera
	 * 
	 * @param gc
	 *            the GameContainer, used for getting the size of the GameCanvas
	 * @param map
	 *            the current map
	 */
	public Camera(GameContainer gc, Map map) {
		this.map = map;

		this.numTilesX = map.getWidth();
		this.numTilesY = map.getHeight();

		this.tileWidth = map.getTileWidth();
		this.tileHeight = map.getTileHeight();

		this.mapWidth = this.numTilesX * this.tileWidth;
		this.mapHeight = this.numTilesY * this.tileHeight;

		this.gc = gc;
	}

	/**
	 * "locks" the camera on the given coordinates. The camera tries to keep the
	 * location in it's center.
	 * 
	 * @param x
	 *            the real x-coordinate (in pixel) which should be centered on
	 *            the screen
	 * @param y
	 *            the real y-coordinate (in pixel) which should be centered on
	 *            the screen
	 */
	public void centerOn(float x, float y) {
		// try to set the given position as center of the camera by default
		cameraX = x - gc.getWidth() / 2;
		cameraY = y - gc.getHeight() / 2;

		// if the camera is at the right or left edge lock it to prevent a blackbar
		if (cameraX < 0)
			cameraX = 0;
		if (cameraX + gc.getWidth() > mapWidth)
			cameraX = mapWidth - gc.getWidth();

		// if the camera is at the top or bottom edge lock it to prevent a blackbar
		if (cameraY < 0)
			cameraY = 0;
		if (cameraY + gc.getHeight() > mapHeight)
			cameraY = mapHeight - gc.getHeight();
	}

	/**
	 * Centers the camera on the player.
	 * 
	 * @param player
	 *            the player
	 */
	public void centerOn(Player player) {
		this.centerOn(player.getPosX() + player.getWidth() / 2,
				player.getPosY() + player.getHeight() / 2);
	}

	/**
	 * Has to be called before Camera.translateGraphics() ! Draws the part of
	 * the map which is currently focussed by the camera on the screen
	 */
	public void drawMap() {
		// calculate the offset to the next tile (needed by TiledMap.render())
		int tileOffsetX = (int) -(cameraX % tileWidth);
		int tileOffsetY = (int) -(cameraY % tileHeight);

		// calculate the index of the leftmost tile that is being displayed
		int tileIndexX = (int) (cameraX / tileWidth);
		int tileIndexY = (int) (cameraY / tileHeight);

		map.render(tileOffsetX, tileOffsetY, tileIndexX, tileIndexY,
				(gc.getWidth() - tileOffsetX) / tileWidth + 1,
				(gc.getHeight() - tileOffsetY) / tileHeight + 1,
				map.getLayerID("background"), false);
		map.render(tileOffsetX, tileOffsetY, tileIndexX, tileIndexY,
				(gc.getWidth() - tileOffsetX) / tileWidth + 1,
				(gc.getHeight() - tileOffsetY) / tileHeight + 1,
				map.getLayerID("objects"), false);
	}

	/**
	 * Draws the Foreground of the map. The layer named "foreground" will be
	 * rendered.
	 */
	public void drawForeground() {
		// calculate the offset to the next tile (needed by TiledMap.render())
		int tileOffsetX = (int) -(cameraX % tileWidth);
		int tileOffsetY = (int) -(cameraY % tileHeight);

		// calculate the index of the leftmost tile that is being displayed
		int tileIndexX = (int) (cameraX / tileWidth);
		int tileIndexY = (int) (cameraY / tileHeight);

		map.render(tileOffsetX, tileOffsetY, tileIndexX, tileIndexY,
				(gc.getWidth() - tileOffsetX) / tileWidth + 1,
				(gc.getHeight() - tileOffsetY) / tileHeight + 1,
				map.getLayerIndex("foreground"), false);

	}

	/**
	 * Translates the Graphics-context to the coordinates of the map 
	 * everything can now be drawn with map coordinates.
	 * @param g Graphics context
	 */
	public void translateGraphics(Graphics g) {
		g.translate(-cameraX, -cameraY);
	}

	/**
	 * Reverses the Graphics-translation of Camera.translatesGraphics(). Call
	 * this before drawing Menu and HUD text.
	 * @param g Graphics context
	 */
	public void untranslateGraphics(Graphics g) {
		g.translate(cameraX, cameraY);
	}
}