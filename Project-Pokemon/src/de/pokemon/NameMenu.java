package de.pokemon;

import java.awt.Font;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.gui.*;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

public class NameMenu {
	
	public boolean showMenu;
	public boolean showTextField;
	public boolean update;
	
	public TextField textField;
	public Name namePlayer;
	
	public String name;
	/** Position of the Rect*/
	public int x;
	public int y;
	
	int sub; // wenn ein neuer Name eingegeben wird, muss von textBox Variable im IntroState 1 abgezogen werden!
	
	public int cursorx;
	public int cursory;
	/** width and height of the Rect.*/
	private int width;
	private int height;
	/** the game, needed to enter states*/
	private StateBasedGame sbg;
	/** the container holding the game*/
	private GameContainer gc;
	/** the main menu items */
	/** the main-menu background*/
	private Rectangle background;
	
	/** height of one item in the menu*/
	private final int itemHeight = 32;
	
	private String[] mainItems = new String[5];
	/** the debug submenu items */
	private Polygon cursor;
	/** the points for the triangle in x,y order*/
	private final float[] cursorPoints = new float[]{0,0,6,6,0,12};
	
	public NameMenu(GameContainer gc,StateBasedGame game, String name1, String name2, String name3){
		//super("Pokemon");
		this.gc = gc;
		this.sbg = game;
		x = gc.getWidth()/8;
		y = gc.getHeight()/7;
		width = gc.getWidth()/4;
		height = gc.getHeight()/3;
		cursor = new Polygon(cursorPoints);
		cursorx = x+16;
		cursory = 80;
		cursor.setLocation(cursorx , cursory);
		showMenu = false;
		showTextField = false;
		update = true;
		namePlayer = new Name();
		sub = 0;
		
		/*textField.setBackgroundColor(Color.white);
		textField.setTextColor(Color.black);*/
		
		mainItems[0] = "NAME";
		mainItems[1] = "NEUER NAME";
		mainItems[2] = name1;
		mainItems[3] = name2;
		mainItems[4] = name3;
		background = new Rectangle(x,y,width,mainItems.length*32);
		name = null;
		
	}
	
	/*public void init(GameContainer container) throws SlickException {
		
		textField = new TextField(gc, gc.getDefaultFont(), 250, 80, 100, 20);
		textField.addListener(this);
		textField.setMaxLength(20);
		
	}*/
	
	public void update(Input input, int delta) throws SlickException {
		
		if(namePlayer.stringFilled == true){
			name = namePlayer.string;
			input.clearKeyPressedRecord();
			System.out.println(name);
		}
		
		if(update){
		if(showMenu){
			background.setHeight(mainItems.length*32);
		}
		if(input.isKeyPressed(Input.KEY_W)){ // nach oben
			if(cursor.getCenterY() > 86){
				cursor.setY(cursor.getY() - 32);
			}
			else{
				cursor.setCenterY(182);
			}
		}else if(input.isKeyPressed(Input.KEY_S)){ // nach unten
			if(cursor.getCenterY() < background.getMaxY()- 64){
				cursor.setY(cursor.getY() + 32);
			}
			else{
				resetCursor();
			}
		}
		
		if(input.isKeyPressed(Input.KEY_ENTER)){
		if(showMenu){
			if(cursor.getCenterY() == 86){ //neuer Name
				Sound.audioTextBox.playAsSoundEffect(1.0f, 3.0f, false);
				showTextField = true;
				sub = 1;
				resetCursor();
				//update = false;
			}else if(cursor.getCenterY() == 118){ //Rot
				Sound.audioTextBox.playAsSoundEffect(1.0f, 3.0f, false);
				name = mainItems[2];
				resetCursor();
			}else if(cursor.getCenterY() == 150){ //Ash
				Sound.audioTextBox.playAsSoundEffect(1.0f, 3.0f, false);
				name = mainItems[3];
				resetCursor();
			}else if(cursor.getCenterY() == 182){ //Jack
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
	}
	
	public void render(Graphics g) {
	
		if(showMenu){
			g.setColor(Color.white);
			//g.drawString("Cursor: X = " + cursor.getCenterX(), 50, 400);
			//g.drawString("Cursor: Y = " + cursor.getCenterY(), 50, 450);
			g.drawRect(x, y, width, height);
			for(int i = 0, j = y - 15; i < mainItems.length; i++, j += 32){
				g.drawString(mainItems[i], background.getCenterX()-g.getFont().getWidth(mainItems[i])/2, j-g.getFont().getHeight(mainItems[i])/2);
			}
		}
		if(showTextField){
			//textField.render(gc, g);
			namePlayer.render(gc, g);
		}
		g.setColor(Color.white);
		g.fill(cursor);
	}

/*public void componentActivated(AbstractComponent source) {
	name = textField.getText();
	//showTextField = false;
	//update = true;
}*/
	
private void resetCursor(){
	cursor.setLocation(x+16, 80);
	}

/*@Override
public void componentActivated(AbstractComponent source) {
	// TODO Auto-generated method stub
	
}*/
}
