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
	private Animation[] water = null;
	/** indicates if the current map has water to render*/
	public boolean hasWater;
	/** contains the npcs of the current map*/
	private ArrayList<Npc> npcs = new ArrayList<Npc>();

	/**
	 * Creates new tile map and builds up a collision map based on a given tmx file.
	 * @param ref Path to .tmx file
	 * @throws SlickException
	 */
	public Map(String ref) throws SlickException{
		super("res/world/" + ref + ".tmx");
		name = getMapProperty("name", "unknown name");
		blocked = new boolean[getWidth()][getHeight()];
		blocked = buildCollisionMap();
		tileSize = getTileWidth();
		hasWater = createWater();
		createNpcs();
		//System.out.println(this.getObjectGroup("object layer").getObjectsOfType("q").isEmpty());
		//System.out.println(blocked[24].length);
		//System.out.println(npcs);
	}

	/**
	 * Builds a 2 dimensional collision-map from the specified layer name for the current
	 * map.
	 *
	 * @param layerName
	 * @return array of booleans, true if blocked
	 */
	private boolean[][] buildCollisionMap() {

		int layerIndex = getLayerIndex("collision");

		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				int tileID = getTileId(x, y, layerIndex);
				blocked[x][y] = getTileProperty(tileID, "blocked", "false").equals("true");
			}
		}

		return blocked;
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
	public  void showGrid(Graphics g){
		g.setColor(Color.black);
		g.setLineWidth(1);
		//rows
		for(int i =	(int)Math.floor(Camera.cameraY/32)*32; i < Camera.cameraY+Core.height; i = i + tileSize){
			g.drawLine(Camera.cameraX, i, Camera.cameraX+Core.width, i);

		}
		//coloumns
		for(int i =	(int)Math.floor(Camera.cameraX/32)*32; i < Camera.cameraX+Core.width; i = i + tileSize){
			g.drawLine(i, Camera.cameraY, i, Camera.cameraY+Core.height);
		}
	}

	/**
	 * Displays the blocked array as rectangles. Green Color if true, else red.
	 * The grid is only drawn for the part which is focused by the camera.
	 * @param g Graphics
	 */
	public void showBlocked(Graphics g){
		for(int i =	(int)Math.floor(Camera.cameraX/32); i < (Camera.cameraX+Core.width)/tileSize; i++)
			for(int j =	(int)Math.floor(Camera.cameraY/32); j < (Camera.cameraY+Core.height)/tileSize; j++)
			{
				if(blocked[i][j]){
					g.setColor(new Color(1.0f, 0.0f, 0.0f, 0.5f));	
				}else{
					g.setColor(new Color(0.0f, 1.0f, 0.0f, 0.5f));
				}
				g.fillRect(i*tileSize, j*tileSize, tileSize, tileSize);
			}

		//		for(int i = 0; i < getWidth(); i++)
		//			for(int j = 0; j < getHeight(); j++){
		//				if(blocked[i][j]){
		//					g.setColor(new Color(1.0f, 0.0f, 0.0f, 0.5f));	
		//				}else{
		//					g.setColor(new Color(0.0f, 1.0f, 0.0f, 0.5f));
		//				}
		//				//g.setDrawMode(Graphics.MODE_ADD_ALPHA);
		//				
		//				g.fillRect(i*tileSize, j*tileSize, tileSize, tileSize);
		//			}
		//		g.setDrawMode(Graphics.MODE_NORMAL);
		//			
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

		water = new Animation[getObjectGroup("object layer").getObjectsOfType("water").size()];
		int counter = 0;
		while(itr.hasNext()){
			GroupObject element = itr.next();
			//			water[counter] = new Animation(true);
			//			for(int i = 0; i < 2; i++){
			//				int x = ((element.gid - 1 + i) % (getTileSetByGID(1).tiles.getWidth()/32)) * 32;
			//				int y = ((element.gid - 1 + i) / (getTileSetByGID(1).tiles.getHeight()/32))* 32;
			//				water[counter].addFrame(getTileSet(0).tiles.getSubImage(x, y, 32,32), 1000);
			//			}
			water[counter] = new Animation(getTileSet(0).tiles,
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
			for(int i = 0; i < water.length; i++){
				water[i].draw(element.x, element.y-element.height);
			}
		}
	}

	private void createNpcs() {
		//System.out.println(getObjectGroup("object layer").getObjectsOfType("npc").size());
		if(getObjectGroup("object layer").getObjectsOfType("npc") != null){
			for(GroupObject go: getObjectGroup("object layer").getObjectsOfType("npc")){
				npcs.add(new Npc(go.x, go.y, go.name));
				//	System.out.println(go.x + go.y + go.name);
			}
		}
	}

	public void renderNpcs(){
		for(Npc npc: npcs){
			npc.renderNpcHead();
			npc.renderNpcBody();
		}
	}

	public void updateNpcs(int delta){
		for(Npc npc: npcs){
			npc.updateNpc(delta);
		}
	}


	public String getCoordinates(double x, double y, int tileX, int tileY){		
		return new String((int)x + "," + (int)y + "\n" + tileX + "," + tileY + "\n" + name);
	}

	public void showPlayerPosition(Graphics g, double x, double y, int tileX, int tileY){
		g.setColor(Color.white);
		g.drawString((int)x + "," + (int)y + "\n" + tileX + "," + tileY + "\n" + name, 10, 30);
	}


	//this method is gonna be big
	public Map update(Player player) throws SlickException{
		Map tmp = this;
		if(getName().equals("Home")){
			if(getEntrance("house").x == player.getPosX() && getEntrance("house").y == player.getPosY()){
				tmp = new Map("House");	
				player.setPosition(tmp.getEntrance("house").x, tmp.getEntrance("house").y);
			}else if( (getEntrance("town").x == player.getPosX() || getEntrance("town").x + 32 == player.getPosX() || getEntrance("town").x +64 == player.getPosX())
					&& getEntrance("town").y == player.getPosY()){
				tmp = new Map("Town");
				player.setPosition(tmp.getEntrance("town").x, tmp.getEntrance("town").y);
			}
		}else if(getName().equals("House")){
			if(getExit("house").x == player.getPosX() && getExit("house").y == player.getPosY()){
				tmp = new Map("Home");
				player.setPosition(tmp.getExit("house").x, tmp.getExit("house").y);
			}
		}else if(getName().equals("Town")){
			if((getExit("town").x == player.getPosX() || getExit("town").x + 32 == player.getPosX() || getExit("town").x + 64 == player.getPosX()) && getExit("town").y == player.getPosY()){
				tmp = new Map("Home");
				player.setPosition(tmp.getExit("town").x, tmp.getExit("town").y);
			}else if(getEntrance("townHouse1").x == player.getPosX() && getEntrance("townHouse1").y == player.getPosY()){
				tmp = new Map("townHouse1");
				player.setPosition(tmp.getEntrance("townHouse1").x, tmp.getEntrance("townHouse1").y);
			}
		}else if(getName().equals("townHouse1")){
			if(getExit("townFromTownHouse1").x == player.getPosX() && getExit("townFromTownHouse1").y == player.getPosY()){
				tmp = new Map("Town");
				player.setPosition(tmp.getExit("townFromTownHouse1").x, tmp.getExit("townFromTownHouse1").y);
			}
		}

		return tmp;
	}


	public int getTileSize() {
		return tileSize;
	}

	public String getName(){
		return name;
	}

}
