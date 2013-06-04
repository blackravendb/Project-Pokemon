package de.pokemon;

import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class InGameMenu {

	/** set to true if should be activated*/
	public boolean showMenu;
	/** true if FPS display is activated*/
	public boolean showFps;
	/** true if Grid display is activated*/
	public boolean showGrid;
	/** true if Blocked display is activated*/
	public boolean showBlocked;
	/** true if Position display is activated*/
	public boolean showPosition;
	/**	true if Debug Menu is open*/
	private boolean showDebug;
	/** true if Main Menu is open*/
	private boolean showMain;
	/** the game, needed to enter states*/
	private StateBasedGame sbg;
	/** the container holding the game*/
	private GameContainer gc;
	/** the menu background*/
	private Rectangle background;
	/** the main menu items */
	private final String[] mainItems = {"Menu", "Resume", "Debug", "Save", "Exit"};
	/** the debug submenu items */
	private final String[] debugItems = {"Debug", "FPS", "Grid", "Blocked", "Position"};
	/** the triangle cursor*/
	private Polygon cursor;
	/** the points for the triangle in x,y order*/
	private final float[] cursorPoints = new float[]{0,0,16,16,0,32};


	/**Initialises the InGameMenu
	 * 
	 * @param gc the container holding the game
	 * @param sbg the game 
	 */
	public InGameMenu(GameContainer gc,StateBasedGame sbg){
		this.gc = gc;
		this.sbg = sbg;
		cursor = new Polygon(cursorPoints); 
		resetCursor();
		background = new Rectangle(gc.getWidth()-gc.getWidth()/5,0,gc.getWidth()/5,mainItems.length*32);
		showMenu = false;
		showDebug = false;
		showFps = false;
		showGrid = false;
		showBlocked = false;
		showPosition = false;
		showMain = false;
	}

	/** Updates the menu, e.g. cursor position
	 * 
	 * @param input Input of the PlayState
	 */
	public void update(Input input){

		if(showMenu && !showDebug){
			showMain = true;
		}

		if(input.isKeyPressed(Input.KEY_W)){
			if(cursor.getMinY() > 32){
				cursor.setY(cursor.getY() - 32);
			}
		}else if(input.isKeyPressed(Input.KEY_S)){
			if(cursor.getMaxY() < background.getMaxY()){
				cursor.setY(cursor.getY() + 32);
			}	
		}

		if(input.isKeyPressed(Input.KEY_ENTER)){
			if(showMain){
				if(cursor.getY() == 32){ //RESUME
					showMenu = false;
				}else if(cursor.getY() == 64){ //DEBUG
					showMain = false;
					resetCursor();
					showDebug = true;
				}else if(cursor.getY() == 96){ //SAVE
					//TODO Saving the game
				}else if(cursor.getY() == 128){ //EXIT
					//TODO Dialog for exit
					resetCursor();
					showMenu = false;
					sbg.enterState(Core.menu);
				}	
			}else if(showDebug){
				if(cursor.getY() == 32){ // FPS
					if(!showFps){
						showFps = true;
					}else{
						showFps = false;
					}	
				}else if(cursor.getY() == 64){ //GRID
					if(!showGrid){
						showGrid = true;
					}else{
						showGrid = false;
					}
				}else if(cursor.getY() == 96){ // BLOCKED
					if(!showBlocked){
						showBlocked = true;
					}else{
						showBlocked = false;
					}
				}else if(cursor.getY() == 128){ //POSITION
					if(!showPosition){
						showPosition = true;
					}else{
						showPosition = false;
					}
				}	
			}
			
		}

		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			if(showMain){
				showMain = false;
				showMenu = false;
				resetCursor();
			}else if(showDebug){
				showDebug = false;
				showMain = true;
				cursor.setLocation(gc.getWidth()-gc.getWidth()/5, Arrays.binarySearch(mainItems, "Debug")*32);
			}
			
		}


	}

	/** Renders the InGameMenu
	 * 
	 * @param g the current graphis context
	 */
	public void render(Graphics g){
		if(showMenu){
			g.setColor(Color.white);
			g.fill(background);
			g.setColor(Color.black);
			g.draw(background);
			if(showMain){
				g.setColor(Color.black);
				for(int i = 0, j = 16; i < mainItems.length; i++, j += 32){
					g.drawString(mainItems[i], background.getCenterX()-g.getFont().getWidth(mainItems[i])/2, j-g.getFont().getHeight(mainItems[i])/2);
				}
			}else if(showDebug){
				for(int i = 1; i < debugItems.length; i++){
					if((i == 1 && showFps == true) || (i == 2 && showGrid == true) || (i == 3 && showBlocked == true) || (i == 4 && showPosition == true))
						g.setColor(Color.green);
					else
						g.setColor(Color.red);
					g.fillRect(background.getMinX(),i*32, background.getWidth(), 32);
				}
				g.setColor(Color.black);
				for(int i = 0, j = 16; i < debugItems.length; i++, j += 32){
					g.drawString(debugItems[i], background.getCenterX()-g.getFont().getWidth(debugItems[i])/2, j-g.getFont().getHeight(debugItems[i])/2);
				}
			}
			g.setColor(Color.black);
			g.fill(cursor);
		}
	}
	/**
	 * Reset the cursor the its original position
	 */
	private void resetCursor(){
		cursor.setLocation(gc.getWidth()-gc.getWidth()/5, 32);
	}
}
