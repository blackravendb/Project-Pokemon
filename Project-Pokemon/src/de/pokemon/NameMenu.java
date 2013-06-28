package de.pokemon;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class NameMenu {

	/** set to true if should be activated*/
	public boolean showMenu;
	/** true if the TextField should be shown*/
	public boolean showTextField;
	/** true if NameMenu should update*/
	public boolean update;

	private boolean newName;
	/** creates an object with which you can choose any name*/
	private Name namePlayer;

	/** the name of the player*/
	public String name;
	/** x-coordinate from the background of the NameMenu*/
	private int backgroundX;
	/** y-coordinate form the background of the NameMenu*/
	private int backgroundY;
	/** width of the background*/
	private int backgroundWidth;
	/** height of the background*/
	private int backgroundHeight;

	/** cursor in the NameMenu*/
	private Polygon cursor;
	/** x-coordinate of the cursor*/
	private int cursorX;
	/** y-coordinate of the cursor*/
	private int cursorY;
	/** the points for the triangle in x,y order*/
	private final float[] cursorPoints = new float[]{0,0,6,6,0,12};

	/** the container holding the game*/
	private GameContainer gc;

	/** the menu background*/
	private Rectangle background;

	/** different names and title of the NameMenu*/
	private String[] mainItems;

	/**Constructor of the NameMenu
	 * 
	 * @param gc the container holding the game
	 * @param game the game 
	 * @param name1, name2, name3 different names for the character
	 * @param colorFont the color of the font
	 */
	public NameMenu(GameContainer gc,StateBasedGame game, boolean newName, String name1, String name2, String name3){
		this.gc = gc;
		backgroundX = gc.getWidth()/8;
		backgroundY = gc.getHeight()/7;
		backgroundWidth = gc.getWidth()/4;
		backgroundHeight = gc.getHeight()/3;
		cursor = new Polygon(cursorPoints);
		cursorX = backgroundX+16;
		cursorY = 80;
		cursor.setLocation(cursorX , cursorY);
		showMenu = false;
		showTextField = false;
		update = true;

		this.newName = newName;
		if(newName == true){
			namePlayer = new Name(); 
		}
		if(newName == true){
			mainItems =	new String[5];
			mainItems[1] = "NEUER NAME";
			mainItems[2] = name1;
			mainItems[3] = name2;
			mainItems[4] = name3;
		}else{
			mainItems = new String[4];
			mainItems[1] = name1;
			mainItems[2] = name2;
			mainItems[3] = name3;
		}
		mainItems[0] = "NAME";
		background = new Rectangle(backgroundX,backgroundY,backgroundWidth,mainItems.length*32);
		name = null;

	}

	/** Updates the NameMenu, e.g. processes Input
	 * 
	 * @param input Input of the IntroState
	 * @param delta the number of milliseconds between frames
	 * 
	 */
	public void update(Input input, int delta) throws SlickException {

		if(newName == true && namePlayer.stringFilled == true){
			name = namePlayer.string;
			input.clearKeyPressedRecord();
			System.out.println(name);
		}

		if(update){
			if(showMenu){
				background.setHeight(mainItems.length*32);
			}
			if(input.isKeyPressed(Input.KEY_W)){ //cursor up
				if(cursor.getCenterY() > 86){
					cursor.setY(cursor.getY() - 32);
				}
				else{
					if(newName == true){
						cursor.setCenterY(182);
					}else{
						cursor.setCenterY(182 - 32);
					}
				}
			}else if(input.isKeyPressed(Input.KEY_S)){ // cursor down
				if(cursor.getCenterY() < background.getMaxY()- 64){
					cursor.setY(cursor.getY() + 32);
				}
				else{
					resetCursor();
				}
			}

			if(input.isKeyPressed(Input.KEY_ENTER)){
				if(showMenu){
					if(cursor.getCenterY() == 86){ //new name
						Sound.audioTextBox.playAsSoundEffect(1.0f, 3.0f, false);
						if(newName == true){
							showTextField = true;
						}else{
							name = mainItems[1];
						}
						resetCursor();
					}else if(cursor.getCenterY() == 118){ //name1
						Sound.audioTextBox.playAsSoundEffect(1.0f, 3.0f, false);
						name = mainItems[2];
						resetCursor();
					}else if(cursor.getCenterY() == 150){ //name2
						Sound.audioTextBox.playAsSoundEffect(1.0f, 3.0f, false);
						name = mainItems[3];
						resetCursor();
					}else if(cursor.getCenterY() == 182 && newName == true){ //name3
						Sound.audioTextBox.playAsSoundEffect(1.0f, 3.0f, false);
						name = mainItems[4];
						resetCursor();
					}
				}
			}

			if(showTextField){
				namePlayer.init(gc); 
				namePlayer.update(gc, delta);
			}
		}
		if(input.isKeyPressed(Input.KEY_ESCAPE) && namePlayer.showTextField == true){
			showTextField = false;
			update = true;

		}
	}

	/** Renders the NameMenu
	 * 
	 * @param g the current graphics context
	 */

	public void render(Graphics g) {

		if(showMenu){
			g.setColor(Color.white);
			g.drawRect(backgroundX, backgroundY, backgroundWidth, backgroundHeight);
			for(int i = 0, j = backgroundY - 15; i < mainItems.length; i++, j += 32){// renders the different names at the right position
				g.drawString(mainItems[i], background.getCenterX()-g.getFont().getWidth(mainItems[i])/2, j-g.getFont().getHeight(mainItems[i])/2);
			}
		}
		if(showTextField){
			namePlayer.render(gc, g);
		}
		g.setColor(Color.white);
		g.fill(cursor);
	}

	/**resets the cursor in the GameMenu to the first item
	 * 
	 */
	private void resetCursor(){
		cursor.setLocation(backgroundX+16, 80);
	}
}
