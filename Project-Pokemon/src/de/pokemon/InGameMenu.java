package de.pokemon;

import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
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
	/** x-Position of the menu*/
	private int x;
	/** y-Position of the menu*/
	private int y;
	/** width of the menu.*/
	private int width;
	/**	true if Debug Menu is open*/
	private boolean showDebug;
	/** true if Main Menu is open*/
	private boolean showMain;
	/** the game, needed to enter states*/
	private StateBasedGame sbg;
	/** the container holding the game*/
	private GameContainer gc;
	/** the main-menu background*/
	private Rectangle background;
	/** the main menu items */
	private final String[] mainItems = {"Menu", "Resume", "Debug", "Save", "Exit"};
	/** the debug submenu items */
	private final String[] debugItems = {"Debug", "FPS", "Grid", "Blocked", "Position", "Back"};
	/** the triangle cursor*/
	private Polygon cursor;
	/** the points for the triangle in x,y order*/
	private final float[] cursorPoints = new float[]{0,0,8,8,0,16};
	//TODO USE SUPER COOL FONT HERE

	/**Initialises the InGameMenu
	 * 
	 * @param gc the container holding the game
	 * @param sbg the game 
	 */
	public InGameMenu(GameContainer gc,StateBasedGame sbg){
		this.gc = gc;
		this.sbg = sbg;
		x = gc.getWidth()-gc.getWidth()/4;
		y = 0;
		width = gc.getWidth()/4;
		cursor = new Polygon(cursorPoints); 
		resetCursor();
		background = new Rectangle(x,y,width,mainItems.length*32); 
		showMenu = false;
		showDebug = false;
		showFps = false;
		showGrid = false;
		showBlocked = false;
		showPosition = false;
		showMain = false;
	}

	/** Updates the menu, e.g. cursor position, processes Input, adjusts the background of the menu to fit the items
	 * 
	 * @param input Input of the PlayState
	 */
	public void update(Input input){

		if(showMenu && !showDebug){
			showMain = true;
		}
		
		if(showMain){
			background.setHeight(mainItems.length*32);
		}else if(showDebug){
			background.setHeight(debugItems.length*32);
		}

		if(input.isKeyPressed(Input.KEY_W)){
			if(cursor.getCenterY() > 48){
				cursor.setY(cursor.getY() - 32);
			}
		}else if(input.isKeyPressed(Input.KEY_S)){
			if(cursor.getCenterY() < background.getMaxY()-16){
				cursor.setY(cursor.getY() + 32);
			}	
		}

		if(input.isKeyPressed(Input.KEY_ENTER)){
			if(showMain){
				if(cursor.getCenterY() == 48){ //RESUME
					showMenu = false;
				}else if(cursor.getCenterY() == 80){ //DEBUG
					showMain = false;
					resetCursor();
					showDebug = true;
				}else if(cursor.getCenterY() == 112){ //SAVE
					//TODO Saving the game
				}else if(cursor.getCenterY() == 144){ //EXIT
					//TODO Dialog for exit
					resetCursor();
					showMenu = false;
					sbg.enterState(Core.menu);
				}	
			}else if(showDebug){
				if(cursor.getCenterY() == 48){ // FPS
					if(!showFps){
						showFps = true;
					}else{
						showFps = false;
					}	
				}else if(cursor.getCenterY() == 80){ //GRID
					if(!showGrid){
						showGrid = true;
					}else{
						showGrid = false;
					}
				}else if(cursor.getCenterY() == 112){ // BLOCKED
					if(!showBlocked){
						showBlocked = true;
					}else{
						showBlocked = false;
					}
				}else if(cursor.getCenterY() == 144){ //POSITION
					if(!showPosition){
						showPosition = true;
					}else{
						showPosition = false;
					}
				}else if(cursor.getCenterY() == 176){ // BACK
					showDebug = false;
					showMain = true;
					cursor.setLocation(x+16, Arrays.binarySearch(mainItems, "Debug")*32+8);
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
				cursor.setLocation(x+16, Arrays.binarySearch(mainItems, "Debug")*32+8);
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
			if(showMain){
				g.setColor(Color.black);
				for(int i = 0, j = 16; i < mainItems.length; i++, j += 32){
					g.drawString(mainItems[i], background.getCenterX()-g.getFont().getWidth(mainItems[i])/2, j-g.getFont().getHeight(mainItems[i])/2);
				}
			}else if(showDebug){
				for(int i = 1; i < debugItems.length - 1; i++){
					if((i == 1 && showFps == true) || (i == 2 && showGrid == true) || (i == 3 && showBlocked == true) || (i == 4 && showPosition == true))
						g.setColor(Color.green);
					else 
						g.setColor(Color.red);
					g.fillRect(background.getMinX(),i*32, background.getWidth(), 32);
				}
				g.setColor(Color.black);
				for(int i = 0, j = 16; i < debugItems.length; i++, j += 32){
					g.drawString(debugItems[i], background.getCenterX()-g.getFont().getWidth(debugItems[i])/2, j-g.getFont().getHeight(debugItems[i])/2);
					//g.drawRect(x, j-16, width, 32); // rectangles for debug purposes
				}
			}
			g.setColor(Color.black);
			g.fill(cursor);
			g.draw(background);
			g.drawLine(x, 32, gc.getWidth(), 32);
			g.drawRect(x+1, y+2, width-4, 28);
			
			
		}
	}
	
	/**
	 * Reset the cursor the its original position
	 */
	private void resetCursor(){
		cursor.setLocation(x+16, 40);
	}
	/**The shape slides
	 * 
	 * @param shape
	 * @param startX
	 * @param startY
	 */
	public void slideIn(Shape shape, int startX, int startY) {
		shape.setLocation(startX, startY - shape.getMaxY()); 
		shape.setLocation(startX, startY + shape.getHeight()); // x location is constant

	}
}
