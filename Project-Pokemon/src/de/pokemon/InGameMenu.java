package de.pokemon;

import java.awt.Font;
import java.util.Arrays;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
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
	/** width of the menu, is 1/4 of the game canvas*/
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
	/** the font used for the menu*/
	private SimpleFont font;
	/** the triangle cursor*/
	private Polygon cursor;
	/** the main menu items */
	private final List<String> mainItems  = Arrays.asList("Menu", "Resume", "Debug", "Save", "Exit");
	//private final String[] mainItems = {"Menu", "Resume", "Debug", "Save", "Exit"}; // TODO make ArrayLists
	/** the debug submenu items */
	private final List<String> debugItems  = Arrays.asList("Debug", "FPS", "Grid", "Blocked", "Position", "Back");
	//private final String[] debugItems = {"Debug", "FPS", "Grid", "Blocked", "Position", "Back"};
	/** the points for the triangle in x,y order*/
	private final float[] cursorPoints = new float[]{0,0,8,8,0,16};
	/** height of one item in the menu*/
	private final int itemHeight = 32;
	/** if menu currently sliding in or out */
	public boolean sliding;

	/**Initialises the InGameMenu
	 * 
	 * @param gc the container holding the game
	 * @param sbg the game 
	 */
	public InGameMenu(GameContainer gc,StateBasedGame sbg){
		this.gc = gc;
		this.sbg = sbg;
		width = gc.getWidth()/4;
		x = gc.getWidth()-width;
		y = 0;
		cursor = new Polygon(cursorPoints);
		cursor.setLocation(gc.getWidth(), y+40);
		background = new Rectangle(gc.getWidth(), y, width, mainItems.size() * itemHeight); 
		showMenu = false;
		showDebug = false;
		showFps = false;
		showGrid = false;
		showBlocked = false;
		showPosition = false;
		showMain = false;
		sliding = false;
		try {
			font = new SimpleFont("Cambria", Font.BOLD, 16);
		} catch (SlickException e) {
			e.printStackTrace();
		}


	}

	/** Updates the menu, e.g. cursor position, processes Input, adjusts the background of the menu to fit the items
	 * 
	 * @param input Input of the PlayState
	 */
	public void update(Input input){
		if(!showMenu && !sliding)
			return;

		if(!sliding){

			//			if(showMenu && !showDebug){
			//				showMain = true;
			//			}

			if(showMain){
				background.setHeight(mainItems.size()*itemHeight);
			}else if(showDebug){
				background.setHeight(debugItems.size()*itemHeight);
			}

			if(input.isKeyPressed(Input.KEY_W)){
				if(cursor.getCenterY() > 48){
					cursor.setY(cursor.getY() - itemHeight);
				}else{  //jump to exit
					cursor.setCenterY(background.getMaxY()-16);
				}
			}else if(input.isKeyPressed(Input.KEY_S)){
				if(cursor.getCenterY() < background.getMaxY()-16){
					cursor.setY(cursor.getY() + itemHeight);
				}else{ //jump to first item
					resetCursor();
				}
			}

			if(input.isKeyPressed(Input.KEY_ENTER)){
				if(showMain){
					if(cursor.getCenterY() == 48){ //RESUME
						//showMenu = false;
						//showMain = false;
						sliding = true;
						//background.setX(gc.getWidth());
						//cursor.setX(gc.getWidth());
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
						showMain = false;
						sliding = false;
						background.setX(gc.getWidth());
						cursor.setX(gc.getWidth());
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
						//cursor.setLocation(x+16, mainItems.indexOf("Debug")*itemHeight+8);
						cursor.setCenterY(mainItems.indexOf("Debug")*itemHeight+16);
					}

				}

			}

			if(input.isKeyPressed(Input.KEY_ESCAPE)){
				if(showMain){
					//showMain = false;
					//showMenu = false;
					sliding = true;
					//resetCursor();
					//cursor.setX(gc.getWidth());
					//background.setX(gc.getWidth());
					//TODO perhaps slide out
				}else if(showDebug){
					showDebug = false;
					showMain = true;
					cursor.setCenterY(mainItems.indexOf("Debug")*itemHeight+16);
				}

			}
		} else {

			if(showMenu){ // slide out
				if(background.getX() < gc.getWidth()){ //not at end position
					background.setX(background.getX() + 16);
					cursor.setX(background.getX() + 16);
				}else{ //end position
					sliding = false;
					showMain = false;
					showMenu = false;
					resetCursor();
					cursor.setX(gc.getWidth());
					background.setX(gc.getWidth());
				}
			}else{ // slide in 
				if(background.getX() > x){ //not at end position
					background.setX(background.getX() - 16);
					cursor.setX(background.getX() + 16);
				}else{ //end position
					sliding = false;
					showMenu = true;
					showMain = true;
					resetCursor();
				}
			}



		}
	}



	/** Renders the InGameMenu
	 * 
	 * @param g the current graphis context
	 */
	public void render(Graphics g){
		g.setFont(font.get());
		if(showMenu || sliding){
			g.setColor(Color.white);
			g.fill(background);
			if(showMain || sliding){
				g.setColor(Color.black);
				for(int i = 0, j = 16; i < mainItems.size(); i++, j += 32){
					g.drawString(mainItems.get(i), background.getCenterX()-font.get().getWidth(mainItems.get(i))/2, j-font.get().getHeight(mainItems.get(i))/2);
				}
			}else if(showDebug){
				for(int i = 1; i < debugItems.size() - 1; i++){
					if((i == 1 && showFps == true) || (i == 2 && showGrid == true) || (i == 3 && showBlocked == true) || (i == 4 && showPosition == true))
						g.setColor(Color.green);
					else 
						g.setColor(Color.red);
					g.fillRect(background.getMinX(),i*itemHeight, background.getWidth(), itemHeight);
				}
				g.setColor(Color.black);
				for(int i = 0, j = 16; i < debugItems.size(); i++, j += 32){
					g.drawString(debugItems.get(i), background.getCenterX()-g.getFont().getWidth(debugItems.get(i))/2, j-g.getFont().getHeight(debugItems.get(i))/2);
					//g.drawRect(x, j-16, width, 32); // rectangles for debug purposes
				}
			}
			g.setColor(Color.black);
			g.fill(cursor);
			g.draw(background);
			g.drawLine(background.getX(), 32, gc.getWidth(), 32);
			g.drawRect(background.getX()+1, y+2, width-4, 28);


		}
		g.resetFont();
	}

	/**
	 * Reset the cursor the first item of the menu
	 */
	private void resetCursor(){
		//cursor.setLocation(x+16, 40);
		cursor.setX(x + 16);
		cursor.setCenterY(itemHeight*3/2);
	}

	/**The shape slides in from the right until it is fully visible.
	 * 
	 * @param shape
	 * @param startX
	 * @param startY
	 */
	public void slideFromRight(Shape shape) {
		shape.setLocation(gc.getWidth(), 0); 
		shape.setLocation(gc.getWidth() - shape.getWidth(), 0); 
	}
}
