package de.pokemon;

import java.util.ArrayList;
import java.util.Iterator;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.GroupObject;
import org.newdawn.slick.tiled.TiledMapPlus;

public class Map extends TiledMapPlus{

	/** 2D boolean array, true if Tile is blocked*/
	private static boolean[][] blocked;
	
	/** size of a quadratic tile*/
	private int tileSize;
	
	/** name of the map*/
	private String name;
	
	/** Array of Animation holding the */
	private Animation[] waterAnimation = null;
	
	/** indicates if the current map has water to render*/
	private boolean water;
	
	/** contains the npcs of the current map*/
	private ArrayList<Npc> npcs = new ArrayList<Npc>();

	/**
	 * Creates a new map based on a given tmx file.
	 * @param ref Path to .tmx file
	 * @throws SlickException
	 */
	public Map(String ref,EventManager event) throws SlickException{
		super("res/world/" + ref + ".tmx");
		name = getMapProperty("name", "unknown name");
		buildCollisionMap();
		tileSize = getTileWidth(); // since our tiles are quadratic we just use getTileWidth() to determine the tileSize.
		water = createWater();
		createNpcs(event);
		Sound.changeMapSound(ref);
	}

	/**
	 * Builds a 2 dimensional collision-map from the specified layer name for the current
	 * map.
	 *
	 */
	private void buildCollisionMap() {
		int layerIndex = getLayerIndex("collision");
		int tileID = -1;
		blocked = new boolean[getWidth()][getHeight()];
		
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				tileID = getTileId(x, y, layerIndex);
				blocked[x][y] = getTileProperty(tileID, "blocked", "false").equals("true");
			}
		}
	}

	/**Checks if the tile specified by the x and y coordinate is blocked on the current map.
	 *
	 * @param x tile coordinate
	 * @param y tile coordinate
	 * @return True, if the tile specified by the coordinates is blocked or out of bounds
	 */
	public static boolean isBlocked(int x, int y){
		if( (x < 0) || (x >= blocked.length) || (y < 0) || (y >= blocked[0].length) ){
			return true;
		}
		return blocked[x][y];
	}
	
	/**Tries to set a value in the blocked array. If out of bounds nothing will be set.
	 * 
	 * @param x tile coordinate
	 * @param y tile coordinate
	 * @param value to be set. true if tile should be blocked.
	 */
	public static void setBlocked(int x, int y, boolean value){
		if( (x < 0) || (x >= blocked.length) || (y < 0) || (y >= blocked[0].length) ){
			return;
		}
		blocked[x][y] = value;
	}

	/**
	 * Draws a black Grid on the current map. The grid is only drawn for the part which is focused by the camera.
	 * @param g Graphics
	 */
	public void showGrid(Graphics g) {
		g.setColor(Color.black);
		// rows
		int start = (int) Math.floor(Camera.cameraY / tileSize) * tileSize;
		int end = (int) Camera.cameraY + Core.height;
		for (int y = start; y < end; y += tileSize) {
			g.drawLine(Camera.cameraX, y, Camera.cameraX + Core.width, y);
		}

		// coloumns
		start = (int) Math.floor(Camera.cameraX / tileSize) * tileSize;
		end = (int) Camera.cameraX + Core.width;
		for (int x = start; x < end; x += tileSize) {
			g.drawLine(x, Camera.cameraY, x, Camera.cameraY + Core.height);
		}
	}

	/**
	 * Displays the blocked array as rectangles. Green Color if true, else red.
	 * The grid is only drawn for the part which is focused by the camera.
	 * @param g Graphics
	 */
	public void showBlocked(Graphics g) {
//		int startX = (int)(Camera.cameraX / tileSize);
//		double endX = (Camera.cameraX+Core.width)/tileSize; //using a double value seems to eliminates the annoying clipping problem. 
//		int startY = (int) (Camera.cameraY / tileSize);
//		double endY = (Camera.cameraY+Core.height)/tileSize;
//		for (int x = startX; x < endX; x++)
//			for (int y = startY; y < endY; y++) {
//				if (blocked[x][y]) {
//					g.setColor(new Color(1.0f, 0.0f, 0.0f, 0.5f));//red
//				} else {
//					g.setColor(new Color(0.0f, 1.0f, 0.0f, 0.5f));//green
//				}
//				g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
//			}
		
		//cleaner way without double values, but rendering an extra row/coloumn to avoid clipping problems
		//and using an extra method isBlocked() to avoid out of bounds 
		int startX = (int)(Camera.cameraX / tileSize);
		int endX = (int)(Camera.cameraX+Core.width)/tileSize + 1;
		int startY = (int) (Camera.cameraY / tileSize);
		int endY = (int) (Camera.cameraY+Core.height)/tileSize + 1;
		for (int x = startX; x < endX; x++)
			for (int y = startY; y < endY; y++) {
				if (isBlocked(x, y)) {
					g.setColor(new Color(1.0f, 0.0f, 0.0f, 0.5f));//red
				} else {
					g.setColor(new Color(0.0f, 1.0f, 0.0f, 0.5f));//green
				}
				g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
			}		
	}

	/**
	 * Tries to get an entrance by its name
	 * @param name of the object to retrieve
	 * @return the object or null
	 */ 
	public GroupObject getEntrance(String name){
		Iterator<GroupObject> itr = getObjectGroup("object layer").getObjectsOfType("entrance").iterator();
		while(itr.hasNext()){
			GroupObject element = itr.next();
			if(element.name.equals(name))
				return element;
		}
		return null;	
	}

	/**
	 * Tries to get an exit by its name
	 * @param name of the object to retrieve
	 * @return the object or null
	 */ 
	public GroupObject getExit(String name){
		Iterator<GroupObject> itr = getObjectGroup("object layer").getObjectsOfType("exit").iterator();
		while(itr.hasNext()){
			GroupObject element = itr.next();
			if(element.name.equals(name))
				return element;
		}
		return null;	
	}

	/**
	 * Tries to get a spawn by its name
	 * @param name of the object to retrieve
	 * @return the object or null
	 */ 
	public GroupObject getSpawn(String name){
		Iterator<GroupObject> itr = getObjectGroup("object layer").getObjectsOfType("spawn").iterator();
		while(itr.hasNext()){
			GroupObject element = itr.next();
			if(element.name.equals(name))
				return element;
		}
		return null;	
	}

	/**
	 * creates the Water Animations by reading from the map. Objects are from the type "water" stored within the "object layer".
	 */
	private boolean createWater() throws SlickException{
		if(getObjectGroup("object layer").getObjectsOfType("water").isEmpty())
			return false;

		Iterator<GroupObject> itr = getObjectGroup("object layer").getObjectsOfType("water").iterator();

		waterAnimation = new Animation[getObjectGroup("object layer").getObjectsOfType("water").size()];
		int counter = 0;
		while(itr.hasNext()){
			GroupObject element = itr.next();
			//			water[counter] = new Animation(true);
			//			for(int i = 0; i < 2; i++){
			//				int x = ((element.gid - 1 + i) % (getTileSetByGID(1).tiles.getWidth()/32)) * 32;
			//				int y = ((element.gid - 1 + i) / (getTileSetByGID(1).tiles.getHeight()/32))* 32;
			//				water[counter].addFrame(getTileSet(0).tiles.getSubImage(x, y, 32,32), 1000);
			//			}
			waterAnimation[counter] = new Animation(getTileSet(0).tiles,
					((element.gid - 1 ) % (getTileSetByGID(1).tiles.getWidth()/32)), //start row
					((element.gid - 1 ) / (getTileSetByGID(1).tiles.getHeight()/32)), //start coloumn
					((element.gid) % (getTileSetByGID(1).tiles.getWidth()/32)), //end row 
					((element.gid) / (getTileSetByGID(1).tiles.getHeight()/32)), //end coloumn
					true, //horizontal
					1000, //updatetime
					true); //autoupdate
			counter++;

		}
		return true;
	}

	/**
	 * renders the water animations for the map
	 */
	public void renderWater(){
		Iterator<GroupObject> itr = getObjectGroup("object layer").getObjectsOfType("water").iterator();
		while(itr.hasNext()){
			GroupObject element = itr.next();
			for(int i = 0; i < waterAnimation.length; i++){
				waterAnimation[i].draw(element.x, element.y-element.height); // subtract element.height because image objects have their anchorpoints on the bottom left
			}
		}
	}

	/**
	 * Creates the NPCs for the Map by reading from the object layer.
	 * @param event
	 */
	private void createNpcs(EventManager event) {
		if(getObjectGroup("object layer").getObjectsOfType("npc") != null){
			for(GroupObject go: getObjectGroup("object layer").getObjectsOfType("npc")){
				npcs.add(new Npc(go.x, go.y, go.name, event));
			}
		}
	}

	/**
	 * Renders the NPCs on the current map
	 */
	public void renderNpcs(){
		for(Npc npc: npcs){
			npc.renderNpcHead();
			npc.renderNpcBody();
		}
	}
	
	/**Updates the NPCs on the map
	 * 
	 * @param delta time passed since last update
	 * @param update, true if render should stop at current frame 
	 */
	public void updateNpcs(int delta, boolean update) {
		for (Npc npc : npcs) {
			if (update) {
				npc.currentAnimationBody.setAutoUpdate(true); // using update as an argument seems not to work properly
				npc.currentAnimationHead.setAutoUpdate(true);
				npc.updateNpc(delta);
			} else {
				npc.currentAnimationBody.setAutoUpdate(false);
				npc.currentAnimationHead.setAutoUpdate(false);	
			}
		}
	}


	/** Displays the Player coordinates on the screen
	 * 
	 * @param g graphics context
	 * @param x position of the player
	 * @param y position of the player
	 * @param tileX position of the player
	 * @param tileY position of the player
	 */
	public void showPlayerPosition(Graphics g, int x, int y, int tileX, int tileY){
		g.setColor(Color.white);
		g.drawString(x + "," + y + "\n" + tileX + "," + tileY + "\n" + name, 10, 30);
		g.drawLine(0, Core.height/2, Core.width, Core.height/2);
		g.drawLine(Core.width/2,0, Core.width/2,Core.height);
	}


	/**This methods handles map updates, e.g. map changes. Probably a mad way of doing it, but it seems to work.
	 * 
	 * @param player 
	 * @param event
	 * @return this or a new map 
	 * @throws SlickException
	 */
	public Map update(Player player, EventManager event) throws SlickException{
		Map tmp = this;
		if(getName().equals("Home")){
			if(getEntrance("house").x == player.getPosX() && getEntrance("house").y == player.getPosY()){
				tmp = new Map("House", event);	
				player.setPosition(tmp.getEntrance("house").x, tmp.getEntrance("house").y);
			}else if( (getEntrance("town").x == player.getPosX() || getEntrance("town").x + 32 == player.getPosX() || getEntrance("town").x +64 == player.getPosX())
					&& getEntrance("town").y == player.getPosY()){
				tmp = new Map("Town", event);
				player.setPosition(tmp.getEntrance("town").x, tmp.getEntrance("town").y);
			}
		}else if(getName().equals("House")){
			if(getExit("house").x == player.getPosX() && getExit("house").y == player.getPosY()){
				tmp = new Map("Home", event);
				player.setPosition(tmp.getExit("house").x, tmp.getExit("house").y);
			}
		}else if(getName().equals("Town")){
			if((getExit("town").x == player.getPosX() || getExit("town").x + 32 == player.getPosX() || getExit("town").x + 64 == player.getPosX()) && getExit("town").y == player.getPosY()){
				tmp = new Map("Home", event);
				player.setPosition(tmp.getExit("town").x, tmp.getExit("town").y);
			}else if(getEntrance("townHouse1").x == player.getPosX() && getEntrance("townHouse1").y == player.getPosY()){
				tmp = new Map("townHouse1", event);
				player.setPosition(tmp.getEntrance("townHouse1").x, tmp.getEntrance("townHouse1").y);
			}else if(getEntrance("university").x == player.getPosX() && getEntrance("university").y == player.getPosY()){
				tmp = new Map("University", event);
				player.setPosition(tmp.getEntrance("university").x, tmp.getEntrance("university").y);
			}
		}else if(getName().equals("townHouse1")){
			if(getExit("townFromTownHouse1").x == player.getPosX() && getExit("townFromTownHouse1").y == player.getPosY()){
				tmp = new Map("Town", event);
				player.setPosition(tmp.getExit("townFromTownHouse1").x, tmp.getExit("townFromTownHouse1").y);
			}
		}else if(getName().equals("university")){
			if(getExit("university").x == player.getPosX() && getExit("university").y == player.getPosY()){
				tmp = new Map("Town", event);
				player.setPosition(tmp.getExit("university").x, tmp.getExit("university").y);
			}
		}

		return tmp;
	}

	/**
	 * 
	 * @return tileSize of the map
	 */
	public int getTileSize() {
		return tileSize;
	}
	/**
	 * 
	 * @return name of the map
	 */
	public String getName(){
		return name;
	}

	/**
	 * 
	 * @return if there is water on the map
	 */
	public boolean hasWater(){
		return water;
	}
}
